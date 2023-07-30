package dev.yarinlevi.quantumrs.loot;

import com.mojang.serialization.Codec;
import dev.yarinlevi.quantumrs.QuantumRS;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, QuantumRS.MODID);

    public static final RegistryObject<Codec<RollLootTableModifier>> ROLL_LOOT_TABLE = LOOT_MODIFIERS.register("roll_loot_table", RollLootTableModifier.CODEC);
}
