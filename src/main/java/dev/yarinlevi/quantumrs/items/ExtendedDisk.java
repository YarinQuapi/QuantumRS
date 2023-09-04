package dev.yarinlevi.quantumrs.items;

import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.api.storage.StorageType;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDiskProvider;
import com.refinedmods.refinedstorage.api.storage.disk.StorageDiskSyncData;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.render.Styles;
import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.UUID;

import static com.refinedmods.refinedstorage.RS.ID;

public abstract class ExtendedDisk extends Item implements IStorageDiskProvider {
    private static final String NBT_ID = "Id";

    public ExtendedDisk() {
        super(new Properties().tab(QuantumRS.QUANTUM_CREATIVE_TAB)
                .stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot,
                              boolean isSelected) {
        if (!level.isClientSide && !stack.hasTag() && entity instanceof Player player) {
            UUID id = UUID.randomUUID();
            var serverLevel = (ServerLevel) level;
            if (getType() == StorageType.ITEM) {
                API.instance().getStorageDiskManager(serverLevel).set(id,
                        API.instance().createDefaultItemDisk(serverLevel, this.getCapacity(stack), player));
            } else if (getType() == StorageType.FLUID) {
                API.instance().getStorageDiskManager(serverLevel).set(id,
                        API.instance().createDefaultFluidDisk(serverLevel, this.getCapacity(stack), player));
            }
            API.instance().getStorageDiskManager(serverLevel).markForSaving();
            setId(stack, id);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack diskStack = player.getItemInHand(hand);
        if (!level.isClientSide && player.isCrouching()) {
            ServerLevel serverLevel = (ServerLevel) level;
            IStorageDisk disk = API.instance().getStorageDiskManager(serverLevel).getByStack(diskStack);
            if (disk != null && disk.getStored() == 0) {
                ItemStack part = new ItemStack(getPart(), diskStack.getCount());

                if (!player.getInventory().add(part.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), part);
                }

                API.instance().getStorageDiskManager(serverLevel).remove(this.getId(diskStack));
                API.instance().getStorageDiskManager(serverLevel).markForSaving();

                return new InteractionResultHolder<>(InteractionResult.SUCCESS,
                        new ItemStack(RSItems.STORAGE_HOUSING.get()));
            }

        }
        return new InteractionResultHolder<>(InteractionResult.PASS, diskStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (isValid(stack)) {
            UUID id = this.getId(stack);
            API.instance().getStorageDiskSync().sendRequest(id);
            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);
            if (data != null) {
                tooltip.add(new TextComponent("A legendary disk that for some reason can contain ungodly amount of matter").setStyle(Styles.BLUE));

                if (data.getCapacity() == -1) {
                    tooltip.add(new TranslatableComponent("misc.refinedstorage.storage.stored",
                                    API.instance().getQuantityFormatter().format(data.getStored()))
                            .setStyle(Styles.GRAY));
                } else {
                    tooltip.add(new TranslatableComponent("misc.refinedstorage.storage.stored_capacity",
                                    API.instance().getQuantityFormatter().format(data.getStored()),
                                    API.instance().getQuantityFormatter().format(data.getCapacity()))
                            .setStyle(Styles.GRAY));
                }
            }
            if (flag.isAdvanced()) {
                tooltip.add(new TextComponent(id.toString()).setStyle(Styles.GRAY));
            }
        }
    }

    @Override
    public int getEntityLifespan(ItemStack stack, Level level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public UUID getId(ItemStack disk) {
        return disk.getTag().getUUID(ID);
    }

    @Override
    public void setId(ItemStack disk, UUID id) {
        disk.getOrCreateTag().putUUID(ID, id);
    }

    @Override
    public boolean isValid(ItemStack disk) {
        return disk.hasTag() && disk.getTag().hasUUID(ID);
    }

    protected abstract Item getPart();
}
