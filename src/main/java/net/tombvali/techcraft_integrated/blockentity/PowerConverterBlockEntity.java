package net.tombvali.techcraft_integrated.blockentity;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.tombvali.techcraft_integrated.init.ModBlockEntities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class PowerConverterBlockEntity extends BlockEntity implements IEnergySink {

    private static final Logger LOGGER = LogManager.getLogger();

    private int internalEnergyBuffer = 0;
    private final int MAX_ENERGY = 10000;


    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (!level.isClientSide) {
            EnergyNet.INSTANCE.removeTile(this);
        }
    }



    public PowerConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POWER_CONVERTER.get(), pos, state);
        LOGGER.info("PowerConverterBlockEntity created at {}", pos);

    }

    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() ->
            new IEnergyStorage() {
                @Override
                public int receiveEnergy(int maxReceive, boolean simulate) {
                    return 0;
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    int extracted = Math.min(maxExtract, internalEnergyBuffer * 4); // 1 EU = 4 RF
                    if (!simulate) {
                        internalEnergyBuffer -= extracted / 4;
                    }
                    return extracted;
                }

                @Override
                public int getEnergyStored() {
                    return internalEnergyBuffer * 4;
                }

                @Override
                public int getMaxEnergyStored() {
                    return MAX_ENERGY * 4;
                }

                @Override
                public boolean canExtract() {
                    return true;
                }

                @Override
                public boolean canReceive() {
                    return false;
                }
            }
    );



    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == getOutputSide()) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }




    public static void tick(Level level, BlockPos pos, BlockState state, PowerConverterBlockEntity be) {

        if (level.isClientSide || be.internalEnergyBuffer <= 0) return;

        Direction outputDir = be.getOutputSide();
        BlockEntity neighbor = level.getBlockEntity(pos.relative(outputDir));

        if (neighbor != null) {
            LazyOptional<IEnergyStorage> energyCap = neighbor.getCapability(ForgeCapabilities.ENERGY, outputDir.getOpposite());
            energyCap.ifPresent(handler -> {
                final double EU_TO_RF_RATIO = 0.25; // 1 EU = 0.25 RF

                int maxEUToSend = be.internalEnergyBuffer;
                int rfToSend = (int)(maxEUToSend * EU_TO_RF_RATIO);

                int rfAccepted = handler.receiveEnergy(rfToSend, false);

                int euUsed = (int)Math.ceil(rfAccepted / EU_TO_RF_RATIO);

                if (euUsed > 0) {
                    be.internalEnergyBuffer -= euUsed;
                    LOGGER.info("Sent {} RF ({} EU used), buffer now = {}", rfAccepted, euUsed, be.internalEnergyBuffer);
                }


            });
        }

    }




    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public int getRequestedEnergy() {
        BlockEntity neighbor = level.getBlockEntity(worldPosition.relative(getOutputSide()));
        if (neighbor != null) {
            LazyOptional<IEnergyStorage> energyCap = neighbor.getCapability(ForgeCapabilities.ENERGY, getOutputSide().getOpposite());
            IEnergyStorage storage = energyCap.orElse(null);
            if (storage != null) {
                final double EU_TO_RF_RATIO = 0.25;
                int rfSpace = storage.getMaxEnergyStored() - storage.getEnergyStored();
                return Math.min((int)(rfSpace / EU_TO_RF_RATIO), MAX_ENERGY - internalEnergyBuffer);

            }
        }
        return 0;
    }

    @Override
    public int acceptEnergy(Direction direction, int amount, int voltage) {
        int space = MAX_ENERGY - internalEnergyBuffer;
        int toAccept = Math.min(space, amount);

        if (toAccept > 0) {
            internalEnergyBuffer += toAccept;
        }

        int leftover = amount - toAccept;

        LOGGER.info("Accepted {} EU from direction {} ({} left over), buffer now {}", toAccept, direction, leftover, internalEnergyBuffer);
        return leftover;
    }




    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction direction) {
        return direction == getInputSide();
    }


    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("InternalEnergyBuffer", internalEnergyBuffer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        internalEnergyBuffer = tag.getInt("InternalEnergyBuffer");
    }

    public Direction getInputSide() {
        return getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
    }

    public Direction getOutputSide() {
        return getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }


}
