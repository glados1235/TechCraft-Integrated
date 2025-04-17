package net.tombvali.techcraft_integrated.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.tombvali.techcraft_integrated.TechCraftIntegrated;
import net.tombvali.techcraft_integrated.block.renderer.PumpJackRenderer;
import net.tombvali.techcraft_integrated.init.ModBlockEntities;
import net.tombvali.techcraft_integrated.item.ModItems;
import net.tombvali.techcraft_integrated.item.UnstableIngotItem;

@Mod.EventBusSubscriber(modid = TechCraftIntegrated.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, index) -> {
            if (index != 0) return 0xFFFFFFFF;

            if (stack.getItem() == ModItems.UNSTABLE_INGOT.get()) {
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("UnstableAge")) {
                    int age = tag.getInt("UnstableAge");
                    float ratio = Math.min(age / (float) UnstableIngotItem.getMaxAge(), 1.0f);
                    int red = 255;
                    int green = (int) (255 * (1 - ratio));
                    int blue = (int) (255 * (1 - ratio));
                    return (red << 16) | (green << 8) | blue;
                }
            }

            return 0xFFFFFFFF;
        }, ModItems.UNSTABLE_INGOT.get());
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(ModBlockEntities.PUMP_JACK_BLOCK_ENTITY.get(), PumpJackRenderer::new);
    }

}
