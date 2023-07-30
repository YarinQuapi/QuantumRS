package dev.yarinlevi.quantumrs.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record ConfigurableRandomChance(float defaultProbability) implements LootItemCondition {
    public LootItemConditionType getType() {
        return ModLootConditions.CONFIGURABLE_CHANCE.get();
    }

    public boolean test(LootContext context) {
        return context.getRandom().nextFloat() < defaultProbability;
    }

    public static LootItemCondition.Builder configurableRandomChance(float probability) {
        return () -> new ConfigurableRandomChance(probability);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConfigurableRandomChance> {

        public void serialize(JsonObject object, ConfigurableRandomChance condition, JsonSerializationContext context) {
            object.addProperty("default_probability", condition.defaultProbability);
        }

        public ConfigurableRandomChance deserialize(JsonObject object, JsonDeserializationContext context) {
            return new ConfigurableRandomChance(GsonHelper.getAsFloat(object, "default_probability"));
        }
    }
}
