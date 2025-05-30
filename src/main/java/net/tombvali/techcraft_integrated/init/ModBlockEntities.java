package net.tombvali.techcraft_integrated.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tombvali.techcraft_integrated.TechCraftIntegrated;
import net.tombvali.techcraft_integrated.block.ModBlocks;
import net.tombvali.techcraft_integrated.blockentity.PowerConverterBlockEntity;
import net.tombvali.techcraft_integrated.blockentity.PumpJackBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TechCraftIntegrated.MOD_ID);

    public static final RegistryObject<BlockEntityType<PowerConverterBlockEntity>> POWER_CONVERTER =
            BLOCK_ENTITIES.register("power_converter",
                    () -> BlockEntityType.Builder.of(PowerConverterBlockEntity::new, ModBlocks.POWER_CONVERTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<PumpJackBlockEntity>> PUMP_JACK_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("pump_jack_block_entity",
                    () -> BlockEntityType.Builder.of(PumpJackBlockEntity::new, ModBlocks.PUMP_JACK.get()).build(null));

}