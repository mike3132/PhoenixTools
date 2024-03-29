package net.resolutemc.phoenixtools.UtilManager;

import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class SpawnerPickaxeMap {

    private static final HashMap<UUID, PermissionAttachment> playersWithPermission = new HashMap<>();


    /**
     *
     * @param player Adds a permission to the specified player
     */
    public static void onActivate(Player player) {
        String permissionString = "rosestacker.silktouch.*";
        PermissionAttachment attachment = player.addAttachment(PhoenixTools.getInstance());
        playersWithPermission.put(player.getUniqueId(), attachment);
        PermissionAttachment permission = playersWithPermission.get(player.getUniqueId());
        permission.setPermission(permissionString, true);
    }

    /**
     *
     * @param player Removes a permission from the specified player
     */
    public static void onDeactivate(Player player) {
        String permissionString = "rosestacker.silktouch.*";
        PermissionAttachment permission = playersWithPermission.get(player.getUniqueId());
        if (playersWithPermission.containsKey(player.getUniqueId())) {
            permission.unsetPermission(permissionString);
            playersWithPermission.remove(player.getUniqueId());
        }
    }
}
