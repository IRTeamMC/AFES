package io.github.irteammc.afes.impl;

import io.github.irteammc.afes.api.IEnergyContainerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ItemEnergyContainer extends Item implements IEnergyContainerItem {
    public static final String ENERGY_TAG = "Energy";

    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    private ItemEnergyContainer(Settings settings) {
        super(settings);
    }

    public ItemEnergyContainer(long capacity, Settings settings) {
        this(capacity, capacity, capacity, settings);
    }

    public ItemEnergyContainer(long capacity, long maxTransfer, Settings settings) {
        this(capacity, maxTransfer, maxTransfer, settings);
    }

    public ItemEnergyContainer(long capacity, long maxReceive, long maxExtract, Settings settings) {
        this(settings);

        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public ItemEnergyContainer setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    public ItemEnergyContainer setMaxTransfer(long maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public ItemEnergyContainer setMaxReceive(long maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public ItemEnergyContainer setMaxExtract(long maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    /* IEnergyContainerItem */
    @SuppressWarnings("ConstantConditions")
    @Override
    public long receiveEnergy(ItemStack container, long maxReceive, boolean simulate) {
        if (!container.hasTag())
            container.setTag(new CompoundTag());

        long stored = Math.min(container.getTag().getLong(ENERGY_TAG), getMaxEnergyStored(container));
        long energyReceived = Math.min(capacity - stored, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            stored += energyReceived;
            container.getTag().putLong(ENERGY_TAG, stored);
        }
        return energyReceived;
    }

    @Override
    public long extractEnergy(ItemStack container, long maxExtract, boolean simulate) {
        if (container.getTag() == null || !container.getTag().contains(ENERGY_TAG))
            return 0;

        long stored = Math.min(container.getTag().getLong(ENERGY_TAG), getMaxEnergyStored(container));
        long energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            stored -= energyExtracted;
            container.getTag().putLong(ENERGY_TAG, stored);
        }

        return energyExtracted;
    }

    @Override
    public long getEnergyStored(ItemStack container) {
        if (container.getTag() == null || !container.getTag().contains(ENERGY_TAG))
            return 0;

        return Math.min(container.getTag().getLong(ENERGY_TAG), getMaxEnergyStored(container));
    }

    @Override
    public long getMaxEnergyStored(ItemStack container) {
        return capacity;
    }
}
