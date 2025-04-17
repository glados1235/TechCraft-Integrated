package net.tombvali.techcraft_integrated.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tombvali.techcraft_integrated.blockentity.PumpJackBlockEntity;

public class PumpJackRenderer implements BlockEntityRenderer<PumpJackBlockEntity> {

    public PumpJackRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PumpJackBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        //if (!tile.isPumping()) return;


        Level level = tile.getLevel();
        if (level == null) return;

        BlockPos pos = tile.getBlockPos();
        int y = pos.getY() - 1;
        int pipeLength = 0;

        // Find how far to extend the pipe
        while (y >= level.getMinBuildHeight()) {
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState state = level.getBlockState(checkPos);
            if (!state.isAir() && !state.getMaterial().isLiquid()) {
                break;
            }
            pipeLength++;
            y--;
        }

        if (pipeLength == 0) return;

        poseStack.pushPose();

        // Translate to the center of the pump block
        poseStack.translate(0.5, 0, 0.5);

        ResourceLocation PIPE_TEXTURE = new ResourceLocation("techcraft_integrated", "textures/block/pipe_texture.png");


        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(PIPE_TEXTURE));




        float pipeWidth = 0.2f;
        float minX = -pipeWidth;
        float maxX = pipeWidth;
        float minZ = -pipeWidth;
        float maxZ = pipeWidth;
        float minY = -pipeLength;
        float maxY = 0;

        BlockPos pipeTip = new BlockPos(pos.getX(), pos.getY() - pipeLength, pos.getZ());
        int realLight = LevelRenderer.getLightColor(level, pipeTip);

        renderBox(poseStack, vertexConsumer, minX, minY, minZ, maxX, maxY, maxZ, realLight);


        poseStack.popPose();
    }

    private void renderBox(PoseStack poseStack, VertexConsumer consumer,
                           float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int light) {
        Matrix4f matrix = poseStack.last().pose();

        float height = maxY - minY;

        // Front face
        renderQuad(matrix, consumer, minX, minY, minZ, maxX, maxY, minZ,
                0.0f, 0.0f, 1.0f, height, light);

        // Back face
        renderQuad(matrix, consumer, maxX, minY, maxZ, minX, maxY, maxZ,
                0.0f, 0.0f, 1.0f, height, light);

        // Left face
        renderQuad(matrix, consumer, minX, minY, maxZ, minX, maxY, minZ,
                0.0f, 0.0f, 1.0f, height, light);

        // Right face
        renderQuad(matrix, consumer, maxX, minY, minZ, maxX, maxY, maxZ,
                0.0f, 0.0f, 1.0f, height, light);
    }


    private void renderQuad(Matrix4f matrix, VertexConsumer consumer,
                            float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float u1, float v1, float u2, float v2,
                            int light) {
        float normalX = 0.0f;
        float normalY = 1.0f;
        float normalZ = 0.0f;

        consumer.vertex(matrix, x1, y1, z1)
                .color(255, 255, 255, 255)
                .uv(u1, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalX, normalY, normalZ)
                .endVertex();

        consumer.vertex(matrix, x1, y2, z1)
                .color(255, 255, 255, 255)
                .uv(u1, v2)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalX, normalY, normalZ)
                .endVertex();

        consumer.vertex(matrix, x2, y2, z2)
                .color(255, 255, 255, 255)
                .uv(u2, v2)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalX, normalY, normalZ)
                .endVertex();

        consumer.vertex(matrix, x2, y1, z2)
                .color(255, 255, 255, 255)
                .uv(u2, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalX, normalY, normalZ)
                .endVertex();
    }


    @Override
    public boolean shouldRenderOffScreen(PumpJackBlockEntity tile) {
        return true;
    }
}
