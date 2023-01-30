package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class MultiBlockPickaxeEvent implements Listener {

    private final Map<UUID, BlockFace> lastInteractedFaces = new HashMap<>();
    NamespacedKey key = new NamespacedKey(Main.plugin, "MultiBlock-Pickaxe-Key");

    /**
     *
     * @param bie The event when the player left-clicks on a block, They are put into the HashMap
     */
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent bie) {
        Player player = bie.getPlayer();
        if (bie.getAction() == Action.LEFT_CLICK_BLOCK) {
            lastInteractedFaces.put(player.getUniqueId(), bie.getBlockFace());
        }
    }

    /**
     *
     * @param pqe If the player is inside the HashMap and logs out they will be removed.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent pqe) {
        Player player = pqe.getPlayer();
        lastInteractedFaces.remove(player.getUniqueId());
    }

    /**
     *
     * @param bbe The block break event where I check to make sure the player has the correct tool in their hand.
     *            Gets the HashMap that includes the player and the block face they have clicked.
     *            Loop through all the block faces and make sure they exist and then break the blocks in the HashMap.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();
        if (bbe instanceof MultiBlockEvent) return;

        if (player.getInventory().getItemInMainHand().getType() != Material.NETHERITE_PICKAXE) return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            BlockFace interactedFace = lastInteractedFaces.get(player.getUniqueId());

            if (interactedFace == null) return;

            block = block.getRelative(interactedFace.getOppositeFace());

            List<Block> relativeBlocks = new ArrayList<>();

            for (int i = -1; i <= 1; i++) {
                for (int j =-1; j <= 1; j++) {
                    if (interactedFace.getModX() != 0) {
                        relativeBlocks.add(block.getRelative(interactedFace.getModX(), i, j));
                    } else if (interactedFace.getModY() != 0) {
                        relativeBlocks.add(block.getRelative(i, block.getY(), j));
                    } else if (interactedFace.getModZ() != 0) {
                        relativeBlocks.add(block.getRelative(i, j, interactedFace.getModZ()));
                    }
                }
            }
            for (Block relative : relativeBlocks) {
                if (relative.getType().getBlastResistance() > 5000) continue;
                MultiBlockEvent multiBlockEvent = new MultiBlockEvent(relative, player);
                Bukkit.getPluginManager().callEvent(multiBlockEvent);
                if (multiBlockEvent.isCancelled()) continue;
                relative.breakNaturally(player.getInventory().getItemInMainHand());
            }
        }
    }

    /**
     *
     * @param ice Checks to see if the MultiBlock Pickaxe has been placed inside an anvil
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
