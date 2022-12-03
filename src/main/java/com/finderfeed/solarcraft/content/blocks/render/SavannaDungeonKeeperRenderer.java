package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.content.blocks.blockentities.SavannaDungeonKeeperTile;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SavannaDungeonKeeperRenderer extends TileEntityRenderer<SavannaDungeonKeeperTile> {

    public static final AABB BOX = new AABB(-3.5,-4.5,-3.5,4.5,4.5,4.5);

    public SavannaDungeonKeeperRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SavannaDungeonKeeperTile tile, float idk, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        RenderingTools.renderBox(matrices,src,BOX,1f, 228/255f, 138/255f,0.3f);
        if (tile.isActive()){
            this.renderEffects(tile,idk,matrices,src,light,overlay);
        }
    }

    private void renderEffects(SavannaDungeonKeeperTile tile, float idk, PoseStack matrices, MultiBufferSource src, int light, int overlay){
        RenderingTools.renderBox(matrices,src,BOX,1f, 228/255f, 138/255f,0.3f);
        int t = tile.getActiveTime() % 200;

        Random random = new Random(tile.getLevel().getGameTime());
        if (t >= 0 && t <= 10){
            Vec3 base = new Vec3(0.5,0.5,0.5);
            for (BlockPos offs : SavannaDungeonKeeperTile.MONSTER_OFFSETS){
                Vec3 f = base.add(offs.getX(),offs.getY(),offs.getZ());
                RenderingTools.Lightning2DRenderer.renderLightning(matrices,
                        src,3,0.25f,0.15f,base,f,random,1f, 228/255f, 138/255f);
            }
            matrices.pushPose();
//            matrices.mulPose(Vector3f.XN.rotationDegrees(90));
            for (Player pl : tile.playersInRange()){
                Vec3 offs = pl.position().subtract(Helpers.posToVec(tile.getBlockPos()).add(base));
                Vec3 f = base.add(offs.x,offs.y + pl.getBbHeight()/2f,offs.z);

                RenderingTools.Lightning2DRenderer.renderLightning(matrices,
                        src,3,0.25f,0.15f,base,f,random,1f, 228/255f, 138/255f);
            }
            matrices.popPose();
        }

    }

    @Override
    public boolean shouldRenderOffScreen(SavannaDungeonKeeperTile p_112306_) {
        return true;
    }

    @Override
    public boolean shouldRender(SavannaDungeonKeeperTile p_173568_, Vec3 p_173569_) {
        return true;
    }
}
