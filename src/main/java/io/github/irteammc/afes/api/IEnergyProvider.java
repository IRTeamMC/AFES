package io.github.irteammc.afes.api;

import net.minecraft.util.math.Direction;

public interface IEnergyProvider extends IEnergyHandler {
    /**
     * Extract energy from the provider, internal distribution is left entirely to the provider.
     *
     * @param from       Direction the energy is extracted from
     * @param maxExtract Maximum amount of energy to extract
     * @param simulate   If true, the extraction will only be simulated
     * @return Amount of energy that was (or would have been, if simulated) extracted
     */
    long extractEnergy(Direction from, long maxExtract, boolean simulate);
}
