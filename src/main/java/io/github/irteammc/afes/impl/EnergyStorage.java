package io.github.irteammc.afes.impl;

import io.github.irteammc.afes.api.IEnergyStorage;
import net.minecraft.nbt.CompoundTag;

/**
 * Implementation of {@link IEnergyStorage}. Use/extend this or implement your own.
 */
public class EnergyStorage implements IEnergyStorage {
    public static final String ENERGY_TAG = "Energy";

    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    public EnergyStorage(long capacity) {
        this(capacity, capacity, capacity);
    }

    public EnergyStorage(long capacity, long maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public EnergyStorage(long capacity, long maxReceive, long maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage fromTag(CompoundTag tag) {
        this.energy = tag.getLong(ENERGY_TAG);

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public CompoundTag toTag(CompoundTag tag) {
        if (energy < 0) {
            energy = 0;
        }

        tag.putLong(ENERGY_TAG, energy);
        return tag;
    }

    public EnergyStorage setCapacity(long capacity) {
        this.capacity = capacity;

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public EnergyStorage setMaxTransfer(long maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public long getMaxReceive() {

        return maxReceive;
    }

    public EnergyStorage setMaxReceive(long maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public long getMaxExtract() {
        return maxExtract;
    }

    public EnergyStorage setMaxExtract(long maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    /**
     * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
     */
    public void modifyEnergyStored(long energy) {
        this.energy += energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    /* IEnergyStorage */
    @Override
    public long receiveEnergy(long maxReceive, boolean simulate) {
        long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public long extractEnergy(long maxExtract, boolean simulate) {
        long energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public long getEnergyStored() {
        return energy;
    }

    /**
     * This function is included to allow for server to client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers are guaranteed to have it.
     */
    public void setEnergyStored(long energy) {
        this.energy = energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    @Override
    public long getMaxEnergyStored() {
        return capacity;
    }
}