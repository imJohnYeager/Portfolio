package com.johnyeager.rtrading.rolimonsmonitor.model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordNotifier {

    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1408937106214227969/KP21of07hOuADfEuwYsjaTkOwjQyXg6eegRpsXCL9x9HuHqn8wmZB1wF6Xe95IFDO43n";

    public static void sendMessage(String content) {
        try {
            URL url = new URL(WEBHOOK_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = String.format("{\"content\":\"%s\"}", content);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes());
            }

            conn.getResponseCode(); // 204 = success
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
