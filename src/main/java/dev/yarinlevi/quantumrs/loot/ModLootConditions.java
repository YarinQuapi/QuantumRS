package dev.yarinlevi.quantumrs.loot;

import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registry.LOOT_ITEM_REGISTRY, QuantumRS.MODID);

    public static final RegistryObject<LootItemConditionType> CONFIGURABLE_CHANCE = LOOT_CONDITIONS.register("configurable_random_chance", () -> new LootItemConditionType(new ConfigurableRandomChance.Serializer()));
}
