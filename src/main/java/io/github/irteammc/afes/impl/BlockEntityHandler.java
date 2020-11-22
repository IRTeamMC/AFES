package io.github.irteammc.afes.impl;

import io.github.irteammc.afes.api.IEnergyProvider;
import io.github.irteammc.afes.api.IEnergyReceiver;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;

public class BlockEntityHandler extends BlockEntity implements IEnergyProvider, IEnergyReceiver {
    protected EnergyStorage storage = new EnergyStorage(32000);

    public BlockEntityHandler(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        storage.toTag(tag);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        storage.fromTag(tag);
    }

    /* IEnergyConnection */
    @Override
    public boolean canConnectEnergy(Direction from) {
        return true;
    }

    /* IEnergyHandler */
    @Override
    public long getEnergyStored(Direction from) {
        return storage.getEnergyStored();
    }

    @Override
    public long getMaxEnergyStored(Direction form) {
        return storage.getMaxEnergyStored();
    }

    /* IEnergyReceiver */
    @Override
    public long receiveEnergy(Direction from, long maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    /* IEnergyProvider */
    @Override
    public long extractEnergy(Direction from, long maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }
}
