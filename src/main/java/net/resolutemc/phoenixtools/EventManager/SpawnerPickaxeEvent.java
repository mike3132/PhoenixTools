package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.HashMapManager.SpawnerPickaxeMap;
import net.resolutemc.phoenixtools.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnerPickaxeEvent implements Listener {

    NamespacedKey key = new NamespacedKey(Main.plugin, "Spawner-Pickaxe-Key");

    /**
     *
     * @param bbe This checks to see if the player is breaking a block using a Spawner Pickaxe
     *            It also removes the spawner pick 1 tick after the break has been performed.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();

        if (!(block.getState() instanceof CreatureSpawner)) return;

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            SpawnerPickaxeMap.onEnabled(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    SpawnerPickaxeMap.onDisabled(player);
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() -1);
                }
            }.runTaskLaterAsynchronously(Main.plugin, 1L);
        }
    }

    /**
     *
     * @param ice Checks to see if the Spawner Pickaxe has been placed inside an anvil
     *            And if so the event is canceled and the player is sent a message.
     */
    @EventHandler
    public void playerClickAnvil(InventoryClickEvent ice) {
        Player player = (Player) ice.getWhoClicked();
        Inventory blockInventory = ice.getClickedInventory();
        ItemStack item = ice.getCurrentItem();

        if (blockInventory == null) return;
        if (blockInventory.getType().equals(InventoryType.ANVIL)) {
            if (ice.getSlot() == 2) {
                if (item.getItemMeta() == null) return;
                if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    ice.setCancelled(true);
                    ChatMessage.sendPlayerMessage(player, "Anvil-disabled-message");
                    player.playSound(player, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                }
            }
        }
    }
}
