package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CobblePickaxeEvent implements Listener {

    NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "CobblePickaxe-Key");
    boolean particlesEnabled = PhoenixTools.getInstance().getConfig().getBoolean("Break-Particles");

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        Block block = pie.getClickedBlock();

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            if (!player.isFlying()) return;
            if (block == null) return;
            if (!block.getType().equals(Material.STONE) && !block.getType().equals(Material.COBBLESTONE)) return;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1, 9, false, false, false));
            if (particlesEnabled) {
                block.getWorld().spawnParticle(Particle.CLOUD, block.getLocation(), 1);
            }
        }
    }

    @EventHandler
    public void onDropItem(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            if (!block.getType().equals(Material.STONE) && !block.getType().equals(Material.COBBLESTONE)) {
                bbe.setCancelled(true);
            }
        }

    }

    /**
     *
     * @param ice Checks to see if the Cobble Pickaxe has been placed inside an anvil
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
