package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.Iterator;

public class MoltenPickaxeEvent implements Listener {

    NamespacedKey key = new NamespacedKey(Main.plugin, "Molten-Pickaxe-Key");

    /**
     *
     * @param bdi This checks to see if the player is breaking a block using a Molten Pickaxe
     *            It also checks to see if the item drops of the broken block can be smelted
     *            if so it creates a new items stack and drops it on the ground.
     */
    @EventHandler
    public void onDropItem(BlockDropItemEvent bdi) {
        Player player = bdi.getPlayer();
        Block block = bdi.getBlock();
        Location blockLocation = block.getLocation();

        Iterator<Recipe> recipes = Bukkit.recipeIterator();


        if (!player.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

            while (recipes.hasNext()) {
                Recipe recipe = recipes.next();

                if (recipe instanceof FurnaceRecipe) {
                    FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
                    for (int i = 0; i < bdi.getItems().size(); i++) {
                        ItemStack drops = bdi.getItems().get(i).getItemStack();
                        if (furnaceRecipe.getInputChoice().test(drops)) {
                            ItemStack groundItem = furnaceRecipe.getResult();
                            groundItem.setAmount(drops.getAmount());
                            bdi.getItems().remove(bdi.getItems().get(i));
                            block.getWorld().dropItemNaturally(blockLocation, groundItem);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param ice Checks to see if the Molten Pickaxe has been placed inside an anvil
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
