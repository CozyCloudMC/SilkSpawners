package me.CozyCloud.SilkSpawners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SilkSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        getCommand("spawner").setExecutor(new SpawnerCommand());
    }

}
