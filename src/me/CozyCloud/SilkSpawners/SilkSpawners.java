package me.CozyCloud.SilkSpawners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for SilkSpawners.
 */
public class SilkSpawners extends JavaPlugin {

    /**
     * Registers the plugin's events and commands.
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        getCommand("spawner").setExecutor(new SpawnerCommand());
    }

}
