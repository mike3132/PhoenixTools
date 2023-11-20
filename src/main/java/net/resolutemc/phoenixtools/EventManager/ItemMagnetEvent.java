package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.PhoenixTools;
import net.resolutemc.phoenixtools.UtilManager.ItemMagnetUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemMagnetEvent implements Listener {

    NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "ItemMagnet-Key");


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
        Player player = pme.getPlayer();
        ItemMagnetUtil.onMagnetPull(player);
    }

    @EventHandler
    public void onPlayerCrouch(PlayerToggleSneakEvent pts) {
        Player player = pts.getPlayer();

        if (!player.isSneaking()) {
            if (ItemMagnetUtil.getMagnetPlayers().contains(player.getUniqueId())) {
                ItemMagnetUtil.removeMagnetPlayers(player.getUniqueId());
            }
            return;
        }
        ItemMagnetUtil.addMagnetPlayers(player.getUniqueId());
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent psi) {
        Player player = psi.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        if (mainHandItem.getType().equals(Material.DIAMOND)) {
            if (mainHandItem.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                if (!ItemMagnetUtil.getMagnetPlayers().contains(player.getUniqueId())) {
                    ItemMagnetUtil.addMagnetPlayers(player.getUniqueId());
                }
            }
        }

        if (offHandItem.getType().equals(Material.DIAMOND)) {
            if (offHandItem.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                if (ItemMagnetUtil.getMagnetPlayers().contains(player.getUniqueId())) {
                    ItemMagnetUtil.removeMagnetPlayers(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent ice) {
        Player player = (Player) ice.getWhoClicked();
        ItemStack item = ice.getCurrentItem();
        ItemStack cursor = ice.getCursor();
        int slotNumber = ice.getSlot();

        if (slotNumber != 40) return;
        if (cursor.getType().equals(Material.DIAMOND)) {
            if (!item.getType().equals(Material.DIAMOND)) return;
            if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                if (ItemMagnetUtil.getMagnetPlayers().contains(player.getUniqueId())) {
                    ItemMagnetUtil.removeMagnetPlayers(player.getUniqueId());
                }
            }
            return;
        }
        if (cursor.getType().equals(Material.DIAMOND)) {
            if (cursor.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                if (!ItemMagnetUtil.getMagnetPlayers().contains(player.getUniqueId())) {
                    ItemMagnetUtil.addMagnetPlayers(player.getUniqueId());
                }
            }
        }
    }

    /**
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
