package dev.yarinlevi.quantumrs.data;

import dev.yarinlevi.quantumrs.QuantumRS;
import dev.yarinlevi.quantumrs.setup.ItemStorageType;
import dev.yarinlevi.quantumrs.setup.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LangGenerator extends LanguageProvider {
    public LangGenerator(DataGenerator gen) {
        super(gen, QuantumRS.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Registry.ITEM_DISK.get(ItemStorageType.QUANTUM).get(), "Quantum Storage Disk");
        add("itemGroup.quantum_group", "Quantum Storage Disks");
    }
}
