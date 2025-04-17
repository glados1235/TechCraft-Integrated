package net.tombvali.techcraft_integrated.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class UnstableIngotItem extends Item {

    private static final String TAG_AGE = "UnstableAge";
    private static final int MAX_AGE = 20 * 15; // 15 seconds in ticks

    public UnstableIngotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            CompoundTag tag = stack.getOrCreateTag();
            int age = tag.getInt(TAG_AGE);
            age++;
            tag.putInt(TAG_AGE, age);

            // Warn the player at 10 seconds (5s before boom)
            if (age == MAX_AGE - (20 * 5)) {
                if (living instanceof net.minecraft.server.level.ServerPlayer player) {
                    // Pick a random warning
                    String[] warnings = new String[] {
                            "This thing is getting hot!",
                            "It's starting to glow...",
                            "Feels like it's vibrating...",
                            "I think it’s heating up!",
                            "Why is it pulsing?!",
                            "Uh-oh...",
                            "This can’t be good.",
                            "Something’s wrong.",
                            "That’s not normal...",
                            "Why is it ticking?!",
                            "Maybe crafting this was a mistake.",
                            "I should probably drop this...",
                            "Probably fine. Probably.",
                            "Totally stable, right? Right??",
                            "If I explode, tell my furnace I loved it!",
                            "This seems... unstable."
                    };
                    String warning = warnings[level.getRandom().nextInt(warnings.length)];

                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(warning));
                }
            }

            if (age >= MAX_AGE) {
                // BOOM!
                Vec3 pos = entity.position();
                level.explode(null, pos.x, pos.y, pos.z, 3.0F, Explosion.BlockInteraction.DESTROY);
                stack.shrink(1); // Remove the ingot from inventory
            }
        }

        super.inventoryTick(stack, level, entity, slot, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, net.minecraft.world.entity.item.ItemEntity entity) {
        CompoundTag tag = stack.getOrCreateTag();

        // === SERVER SIDE ===
        if (!entity.level.isClientSide) {
            int age = tag.getInt(TAG_AGE);
            age++;
            tag.putInt(TAG_AGE, age); // <-- This ensures age is written to NBT every tick

            if (age >= MAX_AGE) {
                entity.level.explode(null, entity.getX(), entity.getY(), entity.getZ(), 3.0F, Explosion.BlockInteraction.DESTROY);
                entity.discard();
            }
        }

        // === CLIENT SIDE PARTICLES ===
        if (entity.level.isClientSide) {
            int age = tag.getInt(TAG_AGE); // <-- Read synced age from the item NBT
            float progress = Math.min((float) age / MAX_AGE, 1.0f);

            int particles = (int) (1 + progress * 8); // 1–9 particles
            for (int i = 0; i < particles; i++) {
                double dx = entity.getX() + (entity.level.random.nextDouble() - 0.5) * 0.2;
                double dy = entity.getY() + 0.1 + (entity.level.random.nextDouble() * 0.2);
                double dz = entity.getZ() + (entity.level.random.nextDouble() - 0.5) * 0.2;

                entity.level.addParticle(
                        net.minecraft.core.particles.ParticleTypes.SMOKE,
                        dx, dy, dz,
                        0, 0.01 + progress * 0.02, 0
                );
            }
        }

        return false;
    }


    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        return false;
    }


    public static int getMaxAge() {
        return MAX_AGE;
    }

}
