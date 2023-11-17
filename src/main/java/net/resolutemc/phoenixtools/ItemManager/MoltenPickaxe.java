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

public class MoltenPickaxe {

    /**
     *
     * @return This returns the itemStack of the Molten Pickaxe
     */
    public static ItemStack getMoltenPickaxe() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String realLore : PhoenixTools.getInstance().getConfig().getStringList("MoltenPickaxe-Lore")) {
            lore.add(ColorManager.chatColor("" + realLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("MoltenPickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "MoltenPickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "MoltenPickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("MoltenPickaxe-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }
}
