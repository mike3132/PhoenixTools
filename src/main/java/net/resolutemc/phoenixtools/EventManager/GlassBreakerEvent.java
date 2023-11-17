package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GlassBreakerEvent implements Listener {

    NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "GlassBreaker-Key");

    /**
     *
     * @param bde The even that checks if the player is holding a glass breaker and if so there is
     *            a check to see if the block is a type of glass. It also adds damage to the glass breaker item.
     */
    @EventHandler
    public void onPlayerInteract(BlockDamageEvent bde) {
        Player player = bde.getPlayer();
        Block block = bde.getBlock();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = heldItem.getItemMeta();

        if (!heldItem.getType().equals(Material.SHEARS)) return;
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            if (!block.getType().toString().contains("GLASS")) {
                bde.setCancelled(true);
                return;
            }
            block.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
            block.setType(Material.AIR);
            Damageable damage = (Damageable) meta;
            damage.setDamage(damage.getDamage() +1);
            heldItem.setItemMeta(meta);
            if (damage.getDamage() >= 238) {
                player.getInventory().remove(heldItem);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1, 1);
            }
        }
    }


    /**
     *
     * @param ss Stops players from being able to use the glass breaker as normal shears
     */
    @EventHandler
    public void onSheepShear(PlayerShearEntityEvent ss) {
        Player player = ss.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = heldItem.getItemMeta();

        if (!heldItem.getType().equals(Material.SHEARS)) return;
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            ss.setCancelled(true);
        }
    }

    /**
     *
     * @param ice Checks to see if the Glass Breaker has been placed inside an anvil
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
