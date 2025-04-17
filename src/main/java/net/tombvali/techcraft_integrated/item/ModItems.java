package net.tombvali.techcraft_integrated.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tombvali.techcraft_integrated.TechCraftIntegrated;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechCraftIntegrated.MOD_ID);

    public static final RegistryObject<Item> DICK_STICK = ITEMS.register("dick_stick", () -> new Item(new Item.Properties().tab(ModCreativemodeTab.TECHCRAFT_INTEGRATED).stacksTo(1)));

    public static final RegistryObject<Item> SEMISTABLE_NUGGET = ITEMS.register("semistable_nugget", () -> new Item(new Item.Properties().tab(ModCreativemodeTab.TECHCRAFT_INTEGRATED).stacksTo(64)));

    public static final RegistryObject<UnstableIngotItem> UNSTABLE_INGOT = ITEMS.register("unstable_ingot", () -> new UnstableIngotItem(new Item.Properties().tab(ModCreativemodeTab.TECHCRAFT_INTEGRATED).stacksTo(64)));

    public static void Register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
