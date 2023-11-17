package net.resolutemc.phoenixtools.EventManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
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

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoeEvent implements Listener {

    NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "HarvesterHoe-Key");

    @EventHandler
    public void onClick(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();
        Material cropType = null;

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

            List<Material> cropsEnabled = new ArrayList<>();
            for (String string : PhoenixTools.getInstance().getConfig().getStringList("CropsToHarvest")) {
                cropsEnabled.add(Material.valueOf(string));
            }

            if (block.getType() != Material.AIR && cropsEnabled.contains(block.getType())) {
                cropType = block.getType();
            }

            if (cropType != null && isGrown(block)) {
                Material seedType = getSeedType(cropType);
                if (!hasSeeds(player, cropType)) {
                    ChatMessage.sendPlayerMessage(player, "Player-no-seed-placeholder");
                    return;
                }
                removeSeed(player, seedType);
                replantCrop(block.getLocation(), cropType);
            }
        }
    }

    boolean particlesEnabled = PhoenixTools.getInstance().getConfig().getBoolean("Replant-Particles");
    boolean soundsEnabled = PhoenixTools.getInstance().getConfig().getBoolean("Replant-Sound");
    private void replantCrop(Location location, Material cropType) {
        Block block = location.getBlock();
        new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(cropType);
                if (particlesEnabled) {
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(1,0,0), 10);
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0,0,0), 10);
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0,0,1), 10);
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(1,0,1), 10);
                }
                if (soundsEnabled) {
                    location.getWorld().playSound(location, Sound.ITEM_CROP_PLANT,1,1);
                }
            }
        }.runTaskLater(PhoenixTools.getInstance(), 20L);
    }

    private Material getSeedType(Material cropType) {
        switch (cropType) {
            case WHEAT:
                return Material.WHEAT_SEEDS;
            case CARROTS:
                return Material.CARROT;
            case POTATOES:
                return Material.POTATO;
            case NETHER_WART:
                return Material.NETHER_WART;
            case BEETROOTS:
                return Material.BEETROOT_SEEDS;
        }
        return null;
    }

    private boolean isGrown(Block block) {
        Ageable age = (Ageable) block.getBlockData();
        int maxAge = age.getMaximumAge();
        return age.getAge() == maxAge;
    }

    private boolean hasSeeds(Player player, Material seedType) {
        if (seedType == Material.WHEAT) {
            return player.getInventory().contains(Material.WHEAT_SEEDS);
        }
        if (seedType == Material.CARROTS) {
            return player.getInventory().contains(Material.CARROT);
        }
        if (seedType == Material.POTATOES) {
            return player.getInventory().contains(Material.POTATO);
        }
        if (seedType == Material.NETHER_WART) {
            return player.getInventory().contains(Material.NETHER_WART);
        }
        if (seedType == Material.BEETROOTS) {
            return player.getInventory().contains(Material.BEETROOT_SEEDS);
        }
        return false;
    }

    private void removeSeed(Player player, Material seedType) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack item = player.getInventory().getContents()[i];
            if (item != null && item.getType() == seedType) {
                item.setAmount(item.getAmount() - 1);
                player.getInventory().setItem(i, item);
                break;
            }
        }
    }

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
