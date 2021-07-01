package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.AuraHealerTile;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.sound.midi.MidiChannel;
import java.util.function.Supplier;

public class SpawnHealParticles extends AbstractPacket {
    public final BlockPos pos1;
    public final float x;
    public final float y;
    public final float z;
    public SpawnHealParticles(PacketBuffer buf) {
        pos1 = buf.readBlockPos();
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();

    }
    public SpawnHealParticles(BlockPos pos,float x,float y ,float z){
        this.pos1 = pos;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos1);
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
                TileEntity tile = Minecraft.getInstance().level.getBlockEntity(pos1);
                if (tile instanceof AuraHealerTile){

                    Vector3f vec = new Vector3f(x,y+1.5f,z);
                    //((AuraHealerTile) tile).spawnParticles(vec);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
