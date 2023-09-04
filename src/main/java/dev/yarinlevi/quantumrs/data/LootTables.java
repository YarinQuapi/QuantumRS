package dev.yarinlevi.quantumrs.data;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();
    private final ExistingFileHelper existingFileHelper;
    private final LootTableModifier lootModifiers;

    public LootTables(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, LootTableModifier lootModifiers) {
        super(dataGenerator);
        this.existingFileHelper = existingFileHelper;
        this.lootModifiers = lootModifiers;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        tables.clear();

        for (LootTableModifier.Builder builder : lootModifiers.lootBuilders) {
            addLootTable(builder.getName(), builder.createLootTable(), builder.getParameterSet());
        }

        return tables;
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    protected static LootPoolSingletonContainer.Builder<?> item(Item item, int weight, int min, int max) {
        return LootItem.lootTableItem(item).setWeight(weight).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    private void addLootTable(String location, LootTable.Builder lootTable, LootContextParamSet lootParameterSet) {
        if (location.startsWith("inject/")) {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), PackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
        }
        tables.add(Pair.of(() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(QuantumRS.MODID, location), lootTable), lootParameterSet));
    }

    private void addLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootContextParamSets.ALL_PARAMS);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
        map.forEach((location, lootTable) -> net.minecraft.world.level.storage.loot.LootTables.validate(validationTracker, location, lootTable));
    }
}
