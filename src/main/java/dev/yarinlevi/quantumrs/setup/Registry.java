package dev.yarinlevi.quantumrs.setup;

import com.mojang.serialization.Codec;
import dev.yarinlevi.quantumrs.QuantumRS;
import dev.yarinlevi.quantumrs.items.ExtendedDiskItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QuantumRS.MODID);
    public static final Map<ItemStorageType, RegistryObject<Item>> ITEM_DISK = new HashMap<>();

    static {
        for (var type : ItemStorageType.values()) {
            ITEM_DISK.put(type,
                    ITEMS.register("disk_" + type.getName(), () -> new ExtendedDiskItem(type)));
        }
    }
}
