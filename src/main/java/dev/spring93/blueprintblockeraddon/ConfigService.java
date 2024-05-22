package dev.spring93.blueprintblockeraddon;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigService {
    private static ConfigService instance;
    private static FileConfiguration config;

    public ConfigService() {
        BlueprintBlockerAddon plugin = JavaPlugin.getPlugin(BlueprintBlockerAddon.class);
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public static ConfigService getInstance() {
        if(instance == null) {
            instance = new ConfigService();
        }
        return instance;
    }

    private String getColorCodedString(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    public String getHitterMessage(String blueprintName, int time) {
        return getColorCodedString("language.player-hitter-message")
                .replace("%blueprint_name%", blueprintName)
                .replace("%time%", String.valueOf(time));
    }

    public String getBlueprintOwnerMessage(String playerName, String blueprintName, int time) {
        return getColorCodedString("language.blueprint-owner-message")
                .replace("%hitter_name%", playerName)
                .replace("%blueprint_name%", blueprintName)
                .replace("%time%", String.valueOf(time));
    }

    public String getMode() {
        return config.getString("reset-mode");
    }

    public int getTime() {
        return config.getInt("time");
    }

    public boolean isPreventOverflow() {
        return config.getBoolean("prevent-max-timer-overflow");
    }

}
