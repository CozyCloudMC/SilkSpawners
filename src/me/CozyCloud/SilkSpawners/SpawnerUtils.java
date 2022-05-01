package me.CozyCloud.SilkSpawners;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

/**
 * Includes various utility methods for SilkSpawners.
 */
public class SpawnerUtils {

    /**
     * Checks if an item is a pickaxe, if it is not wooden or stone, and if it has the Silk Touch enchantment.
     * @param item the item to validate
     * @return true if the item should be able to mine and drop a spawner
     */
    public static boolean isValidPickaxe(ItemStack item) {
        if (item == null || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.STONE_PICKAXE) return false;
        return item.getType().toString().contains("PICKAXE") && item.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    /**
     * Checks if a spawner is one provided by the SilkSpawners plugin by checking the name.
     * @param item the item to validate
     * @return true if the item is a spawner provided by the SilkSpawners plugin
     */
    public static boolean isValidSpawner(ItemStack item) {
        if (item == null || item.getType() != Material.SPAWNER || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        return item.getItemMeta().getDisplayName().endsWith("Spawner");
    }

    /**
     * Gets the corresponding EntityType of a spawner provided by the SilkSpawners plugin.
     * Returns null if the item is not a valid spawner, or it does not have a valid entity type.
     * @param spawner the spawner to get the type of
     * @return the EntityType of the spawner provided
     */
    @Nullable
    public static EntityType getEntityType(ItemStack spawner) {

        if (!isValidSpawner(spawner)) return null;
        String entityName = ChatColor.stripColor(spawner.getItemMeta().getDisplayName()).replace(" Spawner", "").replace(" ", "_").toUpperCase();

        try {
            return EntityType.valueOf(entityName);
        } catch (IllegalArgumentException e) {
            return null; //Entity type does not exist
        }

    }

    /**
     * Gets a colorized, user-friendly name for a spawner.
     * @param type the EntityType used to derive the spawner name
     * @return a properly formatted spawner name
     */
    public static String getSpawnerName(EntityType type) {
        return ChatColor.YELLOW + WordUtils.capitalize(type.toString().toLowerCase().replace("_", " ")) + " Spawner";
    }

    /**
     * Gets a valid spawner with formatted name.
     * @param type used to set the spawner's type
     * @return a valid spawner
     */
    public static ItemStack getSpawner(EntityType type) {

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getSpawnerName(type));
        item.setItemMeta(itemMeta);
        return item;

    }

    /**
     * Adds an item to a player's inventory. If there is no room for the item, it is dropped at the player's feet.
     * @param player the player to give an item
     * @param item the item to be given
     */
    public static void giveItemSafely(Player player, ItemStack item) {
        for (ItemStack remaining : player.getInventory().addItem(item).values()) player.getWorld().dropItem(player.getLocation(), remaining);
    }

}
