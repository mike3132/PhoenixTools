package net.resolutemc.phoenixtools.EventManager;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class MultiBlockEvent extends BlockBreakEvent {

    /**
     *
     * @param block The block the player is interacting with.
     * @param player The player who interacts with the block.
     */
    public MultiBlockEvent(Block block, Player player) {
        super(block, player);
    }
}
