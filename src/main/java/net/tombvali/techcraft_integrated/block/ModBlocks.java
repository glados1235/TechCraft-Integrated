package net.tombvali.techcraft_integrated.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tombvali.techcraft_integrated.TechCraftIntegrated;
import net.tombvali.techcraft_integrated.item.ModCreativemodeTab;
import net.tombvali.techcraft_integrated.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechCraftIntegrated.MOD_ID);

    public static final RegistryObject<Block> DICK_BLOCK = registerBlock("dick_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST).noCollission().strength(0.6f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> POWER_CONVERTER = registerBlock(
            "power_converter",
            () -> new PowerConverterBlock(),
            ModCreativemodeTab.TECHCRAFT_INTEGRATED
    );
    public static final RegistryObject<Block> PUMP_JACK = registerBlock(
            "pump_jack",
            () -> new PumpJackBlock(),
            ModCreativemodeTab.TECHCRAFT_INTEGRATED
    );



    public static final RegistryObject<Block> COMPRESSED_COBBLESTONE = registerBlock("compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> DOUBLE_COMPRESSED_COBBLESTONE = registerBlock("double_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.6f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> TRIPLE_COMPRESSED_COBBLESTONE = registerBlock("triple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.7f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUADRUPLE_COMPRESSED_COBBLESTONE = registerBlock("quadruple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.8f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUINTUPLE_COMPRESSED_COBBLESTONE = registerBlock("quintuple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.9f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEXTUPLE_COMPRESSED_COBBLESTONE = registerBlock("sextuple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEPTUPLE_COMPRESSED_COBBLESTONE = registerBlock("septuple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.1f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> OCTUPLE_COMPRESSED_COBBLESTONE = registerBlock("octuple_compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.2f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);


    //Compressed Deepslate
    public static final RegistryObject<Block> COMPRESSED_DEEPSLATE = registerBlock("compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.8f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> DOUBLE_COMPRESSED_DEEPSLATE = registerBlock("double_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.9f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> TRIPLE_COMPRESSED_DEEPSLATE = registerBlock("triple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUADRUPLE_COMPRESSED_DEEPSLATE = registerBlock("quadruple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.1f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUINTUPLE_COMPRESSED_DEEPSLATE = registerBlock("quintuple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.2f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEXTUPLE_COMPRESSED_DEEPSLATE = registerBlock("sextuple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.3f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEPTUPLE_COMPRESSED_DEEPSLATE = registerBlock("septuple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.4f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> OCTUPLE_COMPRESSED_DEEPSLATE = registerBlock("octuple_compressed_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.4f)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    //Compressed Dirts
    public static final RegistryObject<Block> COMPRESSED_DIRT = registerBlock("compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.5f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> DOUBLE_COMPRESSED_DIRT = registerBlock("double_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.6f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> TRIPLE_COMPRESSED_DIRT = registerBlock("triple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.7f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUADRUPLE_COMPRESSED_DIRT = registerBlock("quadruple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.8f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUINTUPLE_COMPRESSED_DIRT = registerBlock("quintuple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.9f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEXTUPLE_COMPRESSED_DIRT = registerBlock("sextuple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(1f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEPTUPLE_COMPRESSED_DIRT = registerBlock("septuple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(1.1f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> OCTUPLE_COMPRESSED_DIRT = registerBlock("octuple_compressed_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(1.2f).sound(SoundType.GRAVEL)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    //Compressed Andesite
    public static final RegistryObject<Block> COMPRESSED_ANDESITE = registerBlock("compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> DOUBLE_COMPRESSED_ANDESITE = registerBlock("double_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.6f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> TRIPLE_COMPRESSED_ANDESITE = registerBlock("triple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.7f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUADRUPLE_COMPRESSED_ANDESITE = registerBlock("quadruple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.8f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> QUINTUPLE_COMPRESSED_ANDESITE = registerBlock("quintuple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.9f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEXTUPLE_COMPRESSED_ANDESITE = registerBlock("sextuple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> SEPTUPLE_COMPRESSED_ANDESITE = registerBlock("septuple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.1f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);

    public static final RegistryObject<Block> OCTUPLE_COMPRESSED_ANDESITE = registerBlock("octuple_compressed_andesite",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.1f).sound(SoundType.STONE)), ModCreativemodeTab.TECHCRAFT_INTEGRATED);


    private static  <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private  static  <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public  static void Register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
