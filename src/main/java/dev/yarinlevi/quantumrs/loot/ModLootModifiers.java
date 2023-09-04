package dev.yarinlevi.quantumrs.loot;

import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, QuantumRS.MODID);

    public static final RegistryObject<GlobalLootModifierSerializer<RollLootTableModifier>> ROLL_LOOT_TABLE = LOOT_MODIFIERS.register("roll_loot_table", RollLootTableModifier.Serializer::new);
}
