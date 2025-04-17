package net.tombvali.techcraft_integrated.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.tombvali.techcraft_integrated.init.ModBlockEntities;
import net.tombvali.techcraft_integrated.util.ModEnergyStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class PumpJackBlockEntity extends BlockEntity implements MenuProvider {

    // Comments done by good ol GPT since i am lazy, even knowing i have removed most of them lol

    private static final Logger LOGGER = LogManager.getLogger();

    private final int pumpSpeed = 200;
    private final int powerUsage = 4000;
    private int pumpCooldown = 0;
    private boolean activelyPumping = false;
    private int lastPumpedY = -1;


    private final ModEnergyStorage energyStorage = new ModEnergyStorage(5000, 100, this::setChanged);

    private final FluidTank fluidTank = new FluidTank(1000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    private final LazyOptional<ModEnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);

    private final LazyOptional<FluidTank> fluidCap = LazyOptional.of(() -> fluidTank);


    public PumpJackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PUMP_JACK_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PumpJackBlockEntity blockEntity) {
        if (!level.isClientSide) {


            if (blockEntity.pumpCooldown > 0) {
                blockEntity.pumpCooldown--;
            }

            // Try pumping only if cooldown is 0 and conditions are met
            if (blockEntity.pumpCooldown <= 0 &&
                    blockEntity.hasFluidPoolBelow() &&
                    blockEntity.energyStorage.getEnergyStored() >= blockEntity.powerUsage) {

                blockEntity.energyStorage.extractEnergy(blockEntity.powerUsage, false);
                blockEntity.pumpFluids();
                blockEntity.pumpCooldown = blockEntity.pumpSpeed;
                blockEntity.activelyPumping = true;
            } else {
                blockEntity.activelyPumping = false;
            }

        }
    }



    private boolean hasFluidPoolBelow() {
        Level level = getLevel();
        if (level == null) return false;

        BlockPos posBelow = this.getBlockPos().below();

        while (posBelow.getY() >= level.getMinBuildHeight()) {
            BlockState blockState = level.getBlockState(posBelow);
            FluidState fluidState = level.getFluidState(posBelow);

            if (!fluidState.isEmpty()) {
                return true;
            }

            if (!blockState.isAir() && !blockState.getMaterial().isLiquid()) {
                break;
            }
            posBelow = posBelow.below();
        }
        return false;
    }


    private void pumpFluids() {
        Level level = getLevel();
        if (level == null) return;

        BlockPos pumpBase = this.getBlockPos().below();
        BlockPos startPos = null;

        // Try to scan *upwards* from last pumped Y to catch missed sources.
        int scanTopY = pumpBase.getY();
        int scanBottomY = lastPumpedY >= 0 ? lastPumpedY + 1 : scanTopY - 5; // Scan a few layers up if never pumped yet
        scanBottomY = Math.max(scanBottomY, level.getMinBuildHeight());

        outer:
        for (int y = scanBottomY; y <= scanTopY; y++) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos checkPos = new BlockPos(pumpBase.getX() + dx, y, pumpBase.getZ() + dz);
                    FluidState fluidState = level.getFluidState(checkPos);
                    if (!fluidState.isEmpty() && fluidState.isSource()) {
                        startPos = checkPos;
                        break outer;
                    }
                }
            }
        }

        // If none found above, fall back to original "first fluid below" logic
        if (startPos == null) {
            BlockPos fallback = pumpBase;
            while (fallback.getY() >= level.getMinBuildHeight()) {
                FluidState fluidState = level.getFluidState(fallback);
                if (!fluidState.isEmpty()) {
                    startPos = fallback;
                    break;
                }
                fallback = fallback.below();
            }
        }

        if (startPos == null || startPos.getY() < level.getMinBuildHeight()) return; // No source fluid found.




        // Flood-fill to collect all connected fluid blocks.
        // Limit the total number of blocks processed to avoid performance issues.
        final int MAX_BLOCKS = 1000;
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        List<BlockPos> fluidBlocks = new ArrayList<>();

        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty() && visited.size() < MAX_BLOCKS) {
            BlockPos current = queue.poll();
            FluidState currentFluid = level.getFluidState(current);

            if (currentFluid.isSource()) {
                fluidBlocks.add(current);
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.relative(direction);
                if (!visited.contains(neighbor)) {
                    FluidState neighborFluid = level.getFluidState(neighbor);
                    if (!neighborFluid.isEmpty()) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }


        // Identify the "edge" blocks.
        // An edge block is one that is adjacent (any direction) to a non-fluid block.
        List<BlockPos> edgeBlocks = new ArrayList<>();
        for (BlockPos pos : fluidBlocks) {
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = pos.relative(dir);
                if (level.getFluidState(neighbor).isEmpty()) {
                    edgeBlocks.add(pos);
                    break; // Found at least one non-fluid neighbor. Consider it an edge block.
                }
            }
        }

        // Remove one fluid block from the pool.
        // Choose from the edgeBlocks (e.g., remove the block farthest from the pump)
        if (!edgeBlocks.isEmpty()) {
            BlockPos pumpPos = this.getBlockPos();

            edgeBlocks.sort((a, b) -> {
                int yCompare = Integer.compare(b.getY(), a.getY());
                if (yCompare != 0) return yCompare;
                double distA = a.distSqr(pumpPos);
                double distB = b.distSqr(pumpPos);
                return Double.compare(distB, distA);
            });

            BlockPos removalPos = edgeBlocks.get(0);
            FluidState fluidState = level.getFluidState(removalPos);

            if (!fluidState.isEmpty() && fluidState.isSource()) {
                FluidStack fluidStack = new FluidStack(fluidState.getType(), 1000); // 1 bucket

                // Try to insert into internal tank
                int filled = fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

                if (filled > 0) {
                    // Only remove the fluid block if the tank accepted it
                    level.setBlock(removalPos, Blocks.AIR.defaultBlockState(), 3);
                    lastPumpedY = removalPos.getY();

                }
            }
        }

    }

    public boolean isPumping() {
        return activelyPumping;
    }



    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == Direction.UP) {
            return energyCap.cast(); // Only allow energy input from the top
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER &&
                side != null &&
                side != Direction.UP &&
                side != Direction.DOWN) {
            return fluidCap.cast(); // Only allow fluid output from the sides
        }

        return super.getCapability(cap, side);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
        fluidCap.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fluid Pump");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity) {
        return null;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(this.worldPosition).expandTowards(0, -20, 0);
    }
}
