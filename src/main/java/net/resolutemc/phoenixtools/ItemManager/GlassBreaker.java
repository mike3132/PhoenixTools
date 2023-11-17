package net.resolutemc.phoenixtools.ItemManager;

import net.resolutemc.phoenixtools.ChatManager.ColorManager;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GlassBreaker {

    /**
     *
     * @return Returns the itemStack of the glass breaker
     */
    public static ItemStack getGlassBreaker() {
        ItemStack item = new ItemStack(Material.SHEARS, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : PhoenixTools.getInstance().getConfig().getStringList("GlassBreaker-Lore")) {
            lore.add(ColorManager.chatColor("" + relLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("GlassBreaker-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "GlassBreaker-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "GlassBreaker");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("GlassBreaker-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }
}
