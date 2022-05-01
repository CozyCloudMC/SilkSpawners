package me.CozyCloud.SilkSpawners;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerUtils {

    public static boolean isValidPickaxe(ItemStack item) {
        if (item == null || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.STONE_PICKAXE) return false;
        return item.getType().toString().contains("PICKAXE") && item.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    public static boolean isValidSpawner(ItemStack item) {
        if (item == null || item.getType() != Material.SPAWNER || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        return item.getItemMeta().getDisplayName().endsWith("Spawner");
    }

    public static EntityType getEntityType(ItemStack item) {

        if (!isValidSpawner(item)) return null;
        String entityName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace(" Spawner", "").replace(" ", "_").toUpperCase();

        try {
            return EntityType.valueOf(entityName);
        } catch (IllegalArgumentException e) {
            return null; //Entity type does not exist
        }

    }

    public static String getSpawnerName(EntityType type) {
        return ChatColor.YELLOW + WordUtils.capitalize(type.toString().replace("_", " ")) + " Spawner";
    }

    public static ItemStack getSpawner(EntityType type) {

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getSpawnerName(type));
        item.setItemMeta(itemMeta);
        return item;

    }

}
