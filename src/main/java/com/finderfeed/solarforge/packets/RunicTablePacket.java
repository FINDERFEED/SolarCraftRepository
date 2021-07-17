package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

public class RunicTablePacket extends AbstractPacket {

    private final BlockPos pos;

    public RunicTablePacket(BlockPos pos){
        this.pos = pos;
    }

    public RunicTablePacket(PacketBuffer buffer){
        pos = buffer.readBlockPos();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayerEntity playerEntity = ctx.get().getSender();
            World world = playerEntity.getLevel();
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof RunicTableTileEntity){
                RunicTableTileEntity tile = (RunicTableTileEntity) tileEntity;
                boolean a = true;
                if (ProgressionHelper.doPlayerAlreadyHasPattern(playerEntity)){
                    for (int i = 0;i< 6;i++){
                        if (tile.getItem(i).getItem() != ProgressionHelper.RUNES[ProgressionHelper.getPlayerPattern(playerEntity)[i]]){
                            a = false;
                        }
                    }
                }else{
                    a = false;
                }


                if (a){

                    if (ProgressionHelper.getAllUnlockableFragments(playerEntity) != null) {
                        ProgressionHelper.applyTagToFragment(tile.getItem(6),playerEntity);
                        AncientFragment fragment = AncientFragment.getFragmentByID(tile.getItem(6).getTagElement(ProgressionHelper.TAG_ELEMENT).getString(ProgressionHelper.FRAG_ID));
                        ProgressionHelper.givePlayerFragment(fragment,playerEntity);
                        ProgressionHelper.resetPattern(playerEntity);
                        ProgressionHelper.generateRandomPatternForPlayer(playerEntity);
                        for (int i = 0; i < 6; i++) {
                            tile.getItem(i).grow(-1);
                        }
                        SolarForgePacketHandler.INSTANCE.sendTo(new UpdatePatternOnScreen(ProgressionHelper.getPlayerPattern(playerEntity)),playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);

                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
