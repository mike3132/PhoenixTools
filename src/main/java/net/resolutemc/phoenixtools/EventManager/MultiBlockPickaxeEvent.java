package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.*;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockPickaxeEvent implements Listener {

    List<Block> blocks = new ArrayList<>();
    NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "MultiBlockPickaxe-Key");
    BlockFace blockFace = null;

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent bie) {
        if (bie.getAction() == Action.LEFT_CLICK_BLOCK) {
            blockFace = bie.getBlockFace();
        }
    }

    /**
     * @param bbe The block break event where I check to make sure the player has the correct tool in their hand.
     *            Gets the HashMap that includes the player and the block face they have clicked.
     *            Loop through all the block faces and make sure they exist and then break the blocks in the HashMap.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();

        if (bbe instanceof  MultiBlockEvent) return;

        if (player.getInventory().getItemInMainHand().getType() != Material.NETHERITE_PICKAXE) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            if (blockFace.equals(BlockFace.UP) || blockFace.equals(BlockFace.DOWN)) {
                blocks.add(block.getRelative(BlockFace.EAST));
                blocks.add(block.getRelative(BlockFace.WEST));
                blocks.add(block.getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.SOUTH));
                blocks.add(block.getRelative(BlockFace.SOUTH_WEST));
                blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
                blocks.add(block.getRelative(BlockFace.NORTH_WEST));
                blocks.add(block.getRelative(BlockFace.NORTH_EAST));
            }
            if (blockFace.equals(BlockFace.EAST) || blockFace.equals(BlockFace.WEST)) {
                blocks.add(block.getRelative(BlockFace.UP));
                blocks.add(block.getRelative(BlockFace.DOWN));
                blocks.add(block.getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.SOUTH));
                blocks.add(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
                blocks.add(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH));
            }
            if (blockFace.equals(BlockFace.NORTH) || blockFace.equals(BlockFace.SOUTH)) {
                blocks.add(block.getRelative(BlockFace.UP));
                blocks.add(block.getRelative(BlockFace.DOWN));
                blocks.add(block.getRelative(BlockFace.EAST));
                blocks.add(block.getRelative(BlockFace.WEST));
                blocks.add(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
                blocks.add(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
                blocks.add(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST));
                blocks.add(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST));
            }
            boolean particlesEnabled = PhoenixTools.getInstance().getConfig().getBoolean("MultiBlock-Particles");
            MultiBlockEvent multiBlockEvent = new MultiBlockEvent(block, player);
            Bukkit.getPluginManager().callEvent(multiBlockEvent);

            for (Block b : blocks) {
                if (multiBlockEvent.isCancelled()) continue;
                b.breakNaturally(player.getInventory().getItemInMainHand());
                if (particlesEnabled) {
                    b.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, b.getLocation(), 1);
                }
            }
            blocks.clear();
        }
    }


    /**
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
