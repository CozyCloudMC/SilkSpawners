package me.CozyCloud.SilkSpawners;

import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SilkSpawners extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("spawner") && sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length >= 2) {

                if (args[0].equalsIgnoreCase("create")) {

                    String types = "";
                    for (EntityType type : EntityType.values()) types = types + type.toString() + ", ";
                    if (!types.isEmpty()) types = types.substring(0, types.length()-2);

                    try {
                        EntityType type = EntityType.valueOf(args[1].toUpperCase());
                        player.getInventory().addItem(getSpawner(type));
                        sender.sendMessage("Given 1 " + ChatColor.YELLOW + WordUtils.capitalize(type.toString().replace("_", " ")) + ChatColor.WHITE + " spawner");
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

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        Block block = event.getBlock();
        World world = block.getWorld();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (block.getState() instanceof CreatureSpawner) {

            if (isValidPickaxe(item) || player.getGameMode() == GameMode.CREATIVE) {
                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                world.dropItemNaturally(block.getLocation().clone().add(0.5, 0.5, 0.5), getSpawner(spawner.getSpawnedType()));
                event.setExpToDrop(0);
            }

            else {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You need a Silk Touch enchanted iron, gold, diamond, or netherite pickaxe to mine spawners!");
            }

        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();

        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().endsWith("Spawner") &&
                block.getState() instanceof CreatureSpawner) {

            String entityName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace(" Spawner", "").replace(" ", "_").toUpperCase();
            EntityType type = EntityType.valueOf(entityName);

            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            spawner.setSpawnedType(type);
            spawner.update();

        }

    }

    public static boolean isValidPickaxe(ItemStack item) {
        if (item == null || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.STONE_PICKAXE) return false;
        return item.getType().toString().contains("PICKAXE") && item.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    public static ItemStack getSpawner(EntityType type) {

        String entityName = WordUtils.capitalize(type.toString().toLowerCase().replace("_", " "));

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + entityName + " Spawner");
        item.setItemMeta(itemMeta);
        return item;

    }

}
