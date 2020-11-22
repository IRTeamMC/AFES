package io.github.irteammc.afes.api;

import net.minecraft.util.math.Direction;

public interface IEnergyReceiver extends IEnergyHandler {
    /**
     * Add energy to the receiver, internal distribution is left entirely to the receiver
     *
     * @param from       Direction the energy is received from
     * @param maxReceive Maximum amount of energy to receive
     * @param simulate   If true, the charge will only be simulated
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    long receiveEnergy(Direction from, long maxReceive, boolean simulate);
}
