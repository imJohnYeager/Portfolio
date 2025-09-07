package com.johnyeager.rtrading.rolimonsmonitor.model;

import java.util.HashMap;
import java.util.Map;

public class FiltrosAnuncio {

    private int minValue = 3000;
    private int maxValue = 3500;

    // Map de tags: chave = nome da tag, valor = true/false/null (true = obrigatório, false = proibido, null = tanto faz)
    private final Map<String, Boolean> tags = new HashMap<>();

    public FiltrosAnuncio() {
        // Inicializa tags como neutras
        tags.put("any", null);
        tags.put("demand", null);
        tags.put("rares", null);
        tags.put("wishlist", null);
        tags.put("robux", false);
        tags.put("upgrade", null);
        tags.put("downgrade", true);
        tags.put("adds", null);
        tags.put("projecteds", false);
    }

    // Getters e setters
    public int getMinValue() { return minValue; }
    public void setMinValue(int minValue) { this.minValue = minValue; }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }

    public Map<String, Boolean> getTags() { return tags; }

    public void setTag(String tag, Boolean estado) {
        tags.put(tag, estado); // true, false ou null
    }
}
