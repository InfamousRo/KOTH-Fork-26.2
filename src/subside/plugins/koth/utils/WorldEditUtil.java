package subside.plugins.koth.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;

/**
 * Thin wrapper around the WorldEdit 7.x API (the old WorldEditPlugin#getSelection(Player) is gone).
 */
public final class WorldEditUtil {

    private WorldEditUtil() {}

    /**
     * Get the player's current WorldEdit selection.
     *
     * @param player the player
     * @return {min, max} locations of the selection, or null if there is no complete selection
     */
    public static Location[] getSelection(Player player) {
        try {
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
            com.sk89q.worldedit.world.World weWorld = session.getSelectionWorld();
            if (weWorld == null) {
                return null;
            }

            Region region = session.getSelection(weWorld);
            org.bukkit.World world = BukkitAdapter.adapt(weWorld);
            BlockVector3 min = region.getMinimumPoint();
            BlockVector3 max = region.getMaximumPoint();

            return new Location[] {
                new Location(world, min.x(), min.y(), min.z()),
                new Location(world, max.x(), max.y(), max.z())
            };
        } catch (IncompleteRegionException e) {
            return null;
        }
    }
}
