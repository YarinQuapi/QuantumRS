package dev.yarinlevi.quantumrs;

import dev.yarinlevi.quantumrs.loot.ModLootConditions;
import dev.yarinlevi.quantumrs.loot.ModLootModifiers;
import dev.yarinlevi.quantumrs.setup.ItemStorageType;
import dev.yarinlevi.quantumrs.setup.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(QuantumRS.MODID)
public class QuantumRS {
    public static final String MODID = "quantumrs";

    public QuantumRS() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        ModLootConditions.LOOT_CONDITIONS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        Registry.ITEMS.register(modEventBus);
    }

    public static CreativeModeTab QUANTUM_CREATIVE_TAB = new CreativeModeTab("quantum_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        }
    };
}
