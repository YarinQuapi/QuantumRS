package dev.yarinlevi.quantumrs.items;

import com.refinedmods.refinedstorage.api.storage.StorageType;
import dev.yarinlevi.quantumrs.setup.ItemStorageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ExtendedDiskItem extends ExtendedDisk {

    private final ItemStorageType type;

    public ExtendedDiskItem(ItemStorageType type) {
        super();
        this.type = type;
    }

    @Override
    protected Item getPart() {
        return Items.NETHER_STAR;
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return this.type.getCapacity();
    }

    @Override
    public StorageType getType() {
        return StorageType.ITEM;
    }
}
