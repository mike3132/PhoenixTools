package net.resolutemc.phoenixtools.UtilManager;

import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

public class ItemMagnetUtil {

    static NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "ItemMagnet-Key");
    static boolean particlesEnabled = PhoenixTools.getInstance().getConfig().getBoolean("Attraction-Particles");

    private final static HashSet<UUID> magnetPlayers = new HashSet<>();

    public static HashSet<UUID> getMagnetPlayers() {
        return magnetPlayers;
    }

    public static void addMagnetPlayers(UUID player) {
        getMagnetPlayers().add(player);
    }

    public static void removeMagnetPlayers(UUID player) {
        getMagnetPlayers().remove(player);
    }

    public static void onMagnetPull(Player player) {
        Location playerLocation = player.getLocation();
        int range = PhoenixTools.getInstance().getConfig().getInt("Attraction-Distance");
        ItemStack heldItem = player.getInventory().getItemInOffHand();

        if (!magnetPlayers.contains(player.getUniqueId())) return;
        if (!heldItem.getType().equals(Material.DIAMOND)) return;
        if (heldItem.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            for (Entity entity : playerLocation.getWorld().getNearbyEntities(playerLocation, range, range, range)) {
                if (entity instanceof Item || entity instanceof ExperienceOrb) {
                    Location start = entity.getLocation();
                    Location endLocation = start.clone();
                    Vector direction = playerLocation.getDirection().normalize();

                    Location entityLocation = entity.getLocation();
                    double x = playerLocation.getX() - entityLocation.getX();
                    double y = playerLocation.getY() - entityLocation.getY();
                    double z = playerLocation.getZ() - entityLocation.getZ();
                    entity.setVelocity(new Vector(x, y, z).normalize().multiply(1));
                    if (particlesEnabled) {
                        endLocation.getWorld().spawnParticle(Particle.REDSTONE, endLocation.add(direction), 1, new Particle.DustOptions(Color.RED, 1));
                        endLocation.getWorld().spawnParticle(Particle.REDSTONE, endLocation.add(direction), 1, new Particle.DustOptions(Color.BLUE, 1));
                        endLocation.getWorld().spawnParticle(Particle.REDSTONE, endLocation.add(direction), 1, new Particle.DustOptions(Color.GREEN, 1));
                    }
                }
            }
        }
    }

}
