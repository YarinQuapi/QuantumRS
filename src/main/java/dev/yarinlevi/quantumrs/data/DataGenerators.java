package dev.yarinlevi.quantumrs.data;

import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class DataGenerators {
    @Mod.EventBusSubscriber(modid = QuantumRS.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Generator {
        @SubscribeEvent
        public static void runGenerator(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            LootTableModifier lootTableModifier = new LootTableModifier(generator, QuantumRS.MODID);

            if (event.includeServer()) {
                generator.addProvider(new LangGenerator(generator));
                generator.addProvider(lootTableModifier);
                generator.addProvider(new LootTables(generator, existingFileHelper, lootTableModifier));
            }
            if (event.includeClient()) {
                generator.addProvider(new ItemsProvider(generator, existingFileHelper));
            }
        }
    }
}
