package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.SolarEnergyFurnaceTile;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class RunicTableContainer extends AbstractContainer<RunicTableTileEntity>{
    public RunicTableContainer(int windowId, PlayerInventory playerInv, RunicTableTileEntity te, IIntArray array) {
        super(Containers.RUNIC_TABLE_CONTAINER.get(), windowId, playerInv, te, array);


        this.addSlot(new RuneSlot(te,0,45,-12));
        this.addSlot(new RuneSlot(te,1,34,23));
        this.addSlot(new RuneSlot(te,2,45,58));
        this.addSlot(new RuneSlot(te,3,115,-12));
        this.addSlot(new RuneSlot(te,4,126,23));
        this.addSlot(new RuneSlot(te,5,115,58));

        this.addSlot(new FragmentSlot(te,6,80,23));


        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18, 161 -19));
        }
        addDataSlots(arr);

    }


    @Override
    public void broadcastChanges() {

        super.broadcastChanges();
    }

    public int[] getPlayerPattern(){
        return new int[]{
                arr.get(0),
                arr.get(1),
                arr.get(2),
                arr.get(3),
                arr.get(4),
                arr.get(5)
        };
    }


    public RunicTableContainer(int windowId, PlayerInventory playerInv,PacketBuffer buf) {
        this(windowId, playerInv, getTileEntity(playerInv,buf.readBlockPos()), new IntArray(6));
        this.arr.set(0,buf.readInt());
        this.arr.set(1,buf.readInt());
        this.arr.set(2,buf.readInt());
        this.arr.set(3,buf.readInt());
        this.arr.set(4,buf.readInt());
        this.arr.set(5,buf.readInt());
    }

    private static RunicTableTileEntity getTileEntity(final PlayerInventory playerInv, final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final TileEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof RunicTableTileEntity) {
            return (RunicTableTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }



}
class RuneSlot extends Slot{

    public RuneSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }


    @Override
    public boolean mayPlace(ItemStack stack) {
        Item item = stack.getItem();
        return (item == ItemsRegister.SOLAR_RUNE_ARDO.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_ZETA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_TERA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_URBA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_KELDA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_FIRA.get());
    }
}
class FragmentSlot extends Slot{

    public FragmentSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }


    @Override
    public boolean mayPlace(ItemStack stack) {
        Item item = stack.getItem();
        return (item == ItemsRegister.INFO_FRAGMENT.get()) && (stack.getTagElement(ProgressionHelper.TAG_ELEMENT) == null);
    }
}
