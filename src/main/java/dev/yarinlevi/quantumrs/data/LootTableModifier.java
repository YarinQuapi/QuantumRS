package dev.yarinlevi.quantumrs.data;

import dev.yarinlevi.quantumrs.QuantumRS;
import dev.yarinlevi.quantumrs.loot.ConfigurableRandomChance;
import dev.yarinlevi.quantumrs.loot.RollLootTableModifier;
import dev.yarinlevi.quantumrs.setup.ItemStorageType;
import dev.yarinlevi.quantumrs.setup.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.ArrayList;
import java.util.List;

public class LootTableModifier extends GlobalLootModifierProvider {

    protected List<Builder> lootBuilders = new ArrayList<>();

    public LootTableModifier(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    private void addLoot() {
        builder("chests/desert_pyramid", 0.03f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/buried_treasure", 0.05f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/igloo_chest", 0.05f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/end_city_treasure", 0.05f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/stronghold_library", 1f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/village/village_mason", 0.03f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/village/village_temple", 0.03f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/village/village_toolsmith", 0.03f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/woodland_mansion", 0.075f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/shipwreck_supply", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/nether_bridge", 0.02f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/abandoned_mineshaft", 0.02f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("gameplay/fishing/treasure", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/bastion_bridge", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/bastion_treasure", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/bastion_hoglin_stable", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
        builder("chests/bastion_other", 0.04f)
                .item(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get());
    }


    @Override
    protected void start() {
        addLoot();

        for (Builder lootBuilder : lootBuilders) {
            add(lootBuilder.lootTable, lootBuilder.build());
        }
    }

    protected Builder builder(String lootTable, float baseChance) {
        Builder builder = new Builder(lootTable);
        builder.lootModifierCondition(LootTableIdCondition.builder(new ResourceLocation(lootTable)));
        builder.lootModifierCondition(ConfigurableRandomChance.configurableRandomChance(baseChance));
        lootBuilders.add(builder);
        return builder;
    }

    @SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
    protected static class Builder {

        private final String lootTable;
        private final LootPool.Builder lootPool = LootPool.lootPool();
        private final List<LootItemCondition> conditions;

        private LootContextParamSet paramSet = LootContextParamSets.CHEST;

        private Builder(String lootTable) {
            this.lootTable = lootTable;
            this.conditions = new ArrayList<>();
        }

        private RollLootTableModifier build() {
            return new RollLootTableModifier(conditions.toArray(new LootItemCondition[]{}), new ResourceLocation(QuantumRS.MODID, "inject/" + lootTable));
        }

        protected LootTable.Builder createLootTable() {
            return new LootTable.Builder().withPool(lootPool);
        }

        public LootContextParamSet getParameterSet() {
            return paramSet;
        }

        protected String getName() {
            return lootTable;
        }

        private Builder parameterSet(LootContextParamSet paramSet) {
            this.paramSet = paramSet;
            return this;
        }

        private Builder lootPoolCondition(LootItemCondition.Builder condition) {
            lootPool.when(condition);
            return this;
        }

        private Builder lootModifierCondition(LootItemCondition.Builder condition) {
            conditions.add(condition.build());
            return this;
        }

        private Builder item(Item item, int weight) {
            lootPool.add(LootTables.item(item, weight));
            return this;
        }

        private Builder item(Item item) {
            return item(item, 1);
        }
    }
}
