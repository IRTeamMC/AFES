package io.github.irteammc.afes.api;

import net.minecraft.util.math.Direction;

public interface IEnergyConnection {
    /**
     * Returns true if the BlockEntity can connect on a given side.
     */
    boolean canConnectEnergy(Direction from);
}
