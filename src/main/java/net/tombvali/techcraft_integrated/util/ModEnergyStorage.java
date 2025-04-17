package net.tombvali.techcraft_integrated.util;

import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage {
    private final Runnable onChange;

    public ModEnergyStorage(int capacity, int maxTransfer, int maxReceive, Runnable onChange) {
        super(capacity, maxTransfer, maxReceive);
        this.onChange = onChange;
    }

    public ModEnergyStorage(int capacity, int maxTransfer, Runnable onChange) {
        super(capacity, maxTransfer);
        this.onChange = onChange;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted > 0 && !simulate) {
            onChange.run();
        }
        return extracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received > 0 && !simulate) {
            onChange.run();
        }
        return received;
    }
}
