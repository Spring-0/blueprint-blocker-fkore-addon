package dev.spring93.blueprintblockeraddon;

import org.bukkit.plugin.java.JavaPlugin;

public final class BlueprintBlockerAddon extends JavaPlugin {
    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("FactionsKore") == null) {
            getLogger().severe("[-] No FactionsKore plugin found, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerListeners();
        getLogger().info("[+] Successfully enabled Blueprint Blocker Addon.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[-] Disabled Blueprint Blocker Addon");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlueprintClickListener(), this);
    }

}
