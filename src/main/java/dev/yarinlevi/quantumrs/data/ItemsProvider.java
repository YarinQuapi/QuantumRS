package dev.yarinlevi.quantumrs.data;

import dev.yarinlevi.quantumrs.QuantumRS;
import dev.yarinlevi.quantumrs.setup.ItemStorageType;
import dev.yarinlevi.quantumrs.setup.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsProvider extends ItemModelProvider {
    public ItemsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, QuantumRS.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        for (var type : ItemStorageType.values()) {
            singleTexture(getPath(Registry.ITEM_DISK.get(type).get()), mcLoc("item/generated"), "layer0", modLoc("item/disk_" + type.getName()));
            //basicItem(QUItems.ITEM_DISK.get(type).get()).texture("layer0", modLoc("quantumrsunit/textures/items/" + type.getName()));
        }
    }

    private String getPath(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
