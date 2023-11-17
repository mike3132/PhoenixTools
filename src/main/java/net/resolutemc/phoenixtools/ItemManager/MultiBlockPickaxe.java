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

public class MultiBlockPickaxe {


    /**
     *
     * @return This returns the ItemStack of the MultiBlock Pickaxe
     */
    public static ItemStack getMbTool() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String realLore : PhoenixTools.getInstance().getConfig().getStringList("MultiBlockPickaxe-Lore")) {
            lore.add(ColorManager.chatColor("" + realLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("MultiBlockPickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "MultiBlockPickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "MultiBlockPickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        item.setItemMeta(meta);
        return item;
    }
}
