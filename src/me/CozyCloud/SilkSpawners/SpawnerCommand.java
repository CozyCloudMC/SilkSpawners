package me.CozyCloud.SilkSpawners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to give admins a valid spawner.
 */
public class SpawnerCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("spawner") && sender instanceof Player player) {

            String usage = ChatColor.RED + "Usage: /spawner create <name>";

            if (args.length >= 2) {

                if (args[0].equalsIgnoreCase("create")) {

                    try {
                        EntityType type = EntityType.valueOf(args[1].toUpperCase());
                        player.getInventory().addItem(SpawnerUtils.getSpawner(type));
                        sender.sendMessage("Given " + SpawnerUtils.getSpawnerName(type));
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.DARK_RED + args[1].toUpperCase() + ChatColor.RED + " is an invalid type!");
                    }

                }

                else sender.sendMessage(usage);

            }

            else sender.sendMessage(usage);

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> result = new ArrayList<String>();

        if (cmd.getName().equalsIgnoreCase("spawner")) {

            switch (args.length) {

                case 1:
                    result.add("create");
                    break;

                case 2:
                    if (args[0].equalsIgnoreCase("create")) for (EntityType type : EntityType.values()) {
                        String name = type.toString();
                        if (name.toLowerCase().startsWith(args[1].toLowerCase())) result.add(name); //Only shows types that start with the user's entry
                    }
                    break;

            }

        }

        return result;

    }

}
