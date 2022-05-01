package me.CozyCloud.SilkSpawners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SilkSpawners extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("spawner") && sender instanceof Player player) {

            if (args.length >= 2) {

                if (args[0].equalsIgnoreCase("create")) {

                    String types = "";
                    for (EntityType type : EntityType.values()) types = types + type.toString() + ", ";
                    if (!types.isEmpty()) types = types.substring(0, types.length()-2);

                    try {
                        EntityType type = EntityType.valueOf(args[1].toUpperCase());
                        player.getInventory().addItem(SpawnerUtils.getSpawner(type));
                        sender.sendMessage("Given " + SpawnerUtils.getSpawnerName(type));
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Valid spawner types: " + types);
                    }

                }

                else sender.sendMessage(ChatColor.RED + "Usage: /spawner create <name>");

            }

            else sender.sendMessage(ChatColor.RED + "Usage: /spawner create <name>");

        }

        return true;
        
    }

}
