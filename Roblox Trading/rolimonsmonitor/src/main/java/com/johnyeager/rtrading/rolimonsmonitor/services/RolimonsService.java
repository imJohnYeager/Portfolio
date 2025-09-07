package com.johnyeager.rtrading.rolimonsmonitor.services;

import com.johnyeager.rtrading.rolimonsmonitor.model.DiscordNotifier;
import com.johnyeager.rtrading.rolimonsmonitor.model.FiltrosAnuncio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolimonsService {

    private final Set<String> anunciados = new HashSet<>();
    private final FiltrosAnuncio filtros = new FiltrosAnuncio();

    private final Map<String, String> tagNames = Map.of(
            "any", "Any",
            "demand", "Demand",
            "rares", "Rares",
            "wishlist", "Wishlist",
            "robux", "Robux",
            "upgrade", "Upgrade",
            "downgrade", "Downgrade",
            "adds", "Adds",
            "projecteds", "Projecteds"
    );

    @Scheduled(fixedRate = 15000) // roda a cada 15 segundos
    public void verificarAnuncios() {
        System.out.println("Executando verificação...");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.rolimons.com/trades");
            Thread.sleep(3000);

            aplicarFiltrosSite(driver);

            List<WebElement> anunciosHtml = driver.findElements(By.cssSelector("div.shadow_md_15.mix_item"));
            System.out.println("Encontrados " + anunciosHtml.size() + " anúncios");

            for (WebElement anuncio : anunciosHtml) {
                // Nome do usuário
                String userName;
                try {
                    userName = anuncio.findElement(By.cssSelector("a.ad_creator_name")).getText().trim();
                    System.out.println("Usuário encontrado: " + userName);
                } catch (Exception e) {
                    System.out.println("Não foi possível encontrar o nome do usuário");
                    continue;
                }

                // Valor do anúncio
                int valueNumeric = -1;
                try {
                    List<WebElement> valueElements = anuncio.findElements(By.xpath(".//div[contains(@class, 'stat_value') and not(contains(text(), '-'))]"));
                    for (WebElement valueEl : valueElements) {
                        String val = valueEl.getText().replace(".", "").replace(",", "").trim();
                        if (!val.isEmpty() && !val.equals("-")) {
                            try {
                                valueNumeric = Integer.parseInt(val);
                                System.out.println("Valor encontrado: " + valueNumeric + " para usuário: " + userName);
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Não foi possível converter o valor: " + val);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao buscar valor para usuário: " + userName);
                    continue;
                }

                // Filtra pelo range do FiltrosAnuncio
                if (valueNumeric >= filtros.getMinValue() && valueNumeric <= filtros.getMaxValue()) {
                    String key = userName + "-" + valueNumeric;
                    if (!anunciados.contains(key)) {
                        System.out.println("✅ ANÚNCIO DENTRO DO RANGE: " + userName + " - " + valueNumeric);

                        // Tags do anúncio
                        List<String> activeTags = new ArrayList<>();
                        List<WebElement> tagElements = anuncio.findElements(By.cssSelector("div[class^='system_item_tag_container']"));
                        for (WebElement tagEl : tagElements) {
                            String dataClass = tagEl.getAttribute("class");
                            tagNames.forEach((k, v) -> {
                                Boolean filtro = filtros.getTags().get(k);
                                if (dataClass != null && dataClass.toLowerCase().contains(k.toLowerCase()) && filtro != Boolean.FALSE && !activeTags.contains(v)) {
                                    activeTags.add(v);
                                }
                            });
                        }

                        // Offering
                        List<WebElement> offeringElements = anuncio.findElements(By.cssSelector(".ad_side_left .ad_item_img"));
                        List<String> offeringItems = new ArrayList<>();
                        for (WebElement itemEl : offeringElements) {
                            String tooltip = itemEl.getAttribute("data-original-title");
                            if (tooltip != null && !tooltip.isEmpty()) {
                                String[] parts = tooltip.split("<br>");
                                String name = parts[0].trim();
                                String value = parts.length > 1 ? parts[1].replace("Value", "").trim() : "";
                                String rap = parts.length > 2 ? parts[2].replace("RAP", "").trim() : "";
                                offeringItems.add(name + " | Value: " + value + " | RAP: " + rap);
                            }
                        }

                        // Link do botão Send Trade
                        String sendTradeLink = "";
                        try {
                            WebElement sendBtn = anuncio.findElement(By.cssSelector("a.send_trade_button"));
                            sendTradeLink = sendBtn.getAttribute("href");
                        } catch (Exception e) {
                            sendTradeLink = "https://www.rolimons.com/trades";
                        }

                        // Monta a mensagem
                        StringBuilder msg = new StringBuilder();
                        msg.append("🎉 Novo anúncio dentro do range!\\n\\n");
                        msg.append("👤 Usuário: ").append(userName).append("\\n");
                        msg.append("💰 Value: ").append(valueNumeric).append("\\n\\n");

                        if (!activeTags.isEmpty()) {
                            msg.append("🏷️ Tags:\\n");
                            for (String t : activeTags) {
                                msg.append("- ").append(t).append("\\n");
                            }
                            msg.append("\\n");
                        }

                        if (!offeringItems.isEmpty()) {
                            msg.append("🛒 Offering:\\n");
                            for (String o : offeringItems) {
                                msg.append("- ").append(o).append("\\n");
                            }
                            msg.append("\\n");
                        }

                        msg.append("🔗 Send Trade: ").append(sendTradeLink);

                        DiscordNotifier.sendMessage(msg.toString());
                        anunciados.add(key);
                    }
                } else {
                    System.out.println("❌ Fora do range: " + userName + " - " + valueNumeric);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // --------------------------
    // Aplica filtros no site pelo texto visível
    // --------------------------
    private void aplicarFiltrosSite(WebDriver driver) {
        System.out.println("🔧 Aplicando filtros diretamente no site...");

        try {
            filtros.getTags().forEach((tag, estado) -> {
                if (Boolean.TRUE.equals(estado)) {
                    try {
                        WebElement filtroBotao = driver.findElement(By.xpath(
                                "//div[contains(@class,'filter_button')][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                                        + tag.toLowerCase() + "')]"
                        ));
                        filtroBotao.click();
                        Thread.sleep(500); // pequena pausa entre cliques
                        System.out.println("✅ Filtro aplicado no site: " + tag);
                    } catch (Exception e) {
                        System.out.println("⚠ Não foi possível aplicar o filtro: " + tag);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("❌ Erro ao tentar aplicar filtros no site: " + e.getMessage());
        }
    }
}
