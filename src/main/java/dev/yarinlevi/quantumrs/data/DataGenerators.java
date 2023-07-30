package dev.yarinlevi.quantumrs.data;

import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DataGenerators {
    @Mod.EventBusSubscriber(modid = QuantumRS.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Generator {
        @SubscribeEvent
        public static void runGenerator(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            LootTableModifier lootTableModifier = new LootTableModifier(generator, QuantumRS.MODID);

            generator.addProvider(event.includeServer(), new ItemsProvider(generator, existingFileHelper));
            generator.addProvider(event.includeServer(), new LangGenerator(generator));
            generator.addProvider(event.includeServer(), lootTableModifier);
            generator.addProvider(event.includeServer(), new LootTables(generator, existingFileHelper, lootTableModifier));
        }
    }
}
