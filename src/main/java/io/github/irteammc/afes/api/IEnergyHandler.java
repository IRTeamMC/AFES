package io.github.irteammc.afes.api;

import net.minecraft.util.math.Direction;

public interface IEnergyHandler extends IEnergyConnection {
    /**
     * Returns the amount of energy currently stored
     */
    long getEnergyStored(Direction from);

    /**
     * Returns the maximum amount of energy that can be stored
     */
    long getMaxEnergyStored(Direction form);
}
