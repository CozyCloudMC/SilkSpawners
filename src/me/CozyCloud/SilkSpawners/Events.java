package me.CozyCloud.SilkSpawners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * All events for SilkSpawners.
 */
public class Events implements Listener {

    /**
     * Handles the functionality of spawners dropping when mined with a valid pickaxe.
     * Spawner cannot be broken at all unless their pickaxe is valid. Experience orbs are never dropped.
     * @param event BlockBreakEvent
     */
    @EventHandler
    public void onMine(BlockBreakEvent event) {

        Block block = event.getBlock();
        World world = block.getWorld();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (block.getState() instanceof CreatureSpawner spawner) {

            if (SpawnerUtils.isValidPickaxe(item) || player.getGameMode() == GameMode.CREATIVE) {
                world.dropItemNaturally(block.getLocation().clone().add(0.5, 0.5, 0.5), SpawnerUtils.getSpawner(spawner.getSpawnedType()));
                event.setExpToDrop(0);
            }

            else {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You need a Silk Touch enchanted iron, gold, diamond, or netherite pickaxe to mine spawners!");
            }

        }
    }

    /**
     * Handles the setting of a spawner's type when a valid spawner is placed.
     * @param event BlockPlaceEvent
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();

        if (SpawnerUtils.isValidSpawner(item) && block.getState() instanceof CreatureSpawner spawner) {

            EntityType type = SpawnerUtils.getEntityType(item);

            if (type != null) {
                spawner.setSpawnedType(type);
                spawner.update();
            }

        }

    }

}
