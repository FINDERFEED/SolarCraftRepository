package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.capabilities.solar_lexicon.LexiconInventory;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SCAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, SolarCraft.MOD_ID);


    public static final DeferredHolder<AttachmentType<?>,AttachmentType<LexiconInventory>> LEXICON_INVENTORY =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new LexiconInventory()).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_1 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(1)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_2 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(2)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_3 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(3)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_4 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(4)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_5 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(5)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_6 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(6)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_7 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(7)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_8 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(8)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_9 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(9)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_10 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(10)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_11 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(11)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_12 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(12)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_13 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(13)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_14 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(14)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_15 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(15)).build());
    public static final DeferredHolder<AttachmentType<?>,AttachmentType<ItemStackHandler>> INVENTORY_16 =
            ATTACHMENT_TYPES.register("inventory",()->AttachmentType.serializable(()->new ItemStackHandler(16)).build());

}
