package user11681.limitless.config;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.lang.reflect.Field;
import java.util.List;
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.api.GuiProvider;
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import user11681.limitless.asm.access.EnchantmentAccess;

public class MapTypeProvider implements GuiProvider {
    @Override
    public List<AbstractConfigListEntry> get(final String i13n, final Field field, final Object config, final Object defaults, final GuiRegistryAccess guiRegistry) {
        try {
            final ReferenceArrayList<ConfigEnchantmentEntry> levels = (ReferenceArrayList<ConfigEnchantmentEntry>) field.get(config);
            final ReferenceArrayList<AbstractConfigListEntry> entries = ReferenceArrayList.wrap(new AbstractConfigListEntry[levels.size()], 0);
            final TranslatableText resetKey = new TranslatableText("text.cloth-config.reset_value");
            final Registry<Enchantment> registry = Registry.ENCHANTMENT;
            int nextIndex;

            final SubCategoryBuilder listBuilder = new SubCategoryBuilder(resetKey, new TranslatableText("config.limitless.enchantments"));

            for (final ConfigEnchantmentEntry entry : levels) {
                final Enchantment enchantment = registry.get(entry.identifier);

                if (enchantment != null) {
                    final SubCategoryBuilder builder = new SubCategoryBuilder(resetKey, new TranslatableText(enchantment.getTranslationKey()));

                    builder.add(0, new IntFieldBuilder(resetKey, new TranslatableText("config.limitless.maxLevel"), entry.maxLevel)
                        .setDefaultValue(((EnchantmentAccess) enchantment).limitless_getOriginalMaxLevel())
                        .setSaveConsumer((final Integer level) -> {
                            ((EnchantmentAccess) enchantment).limitless_setMaxLevel(level);
                            entry.maxLevel = level;
                        }).build()
                    );

                    builder.add(1, new BooleanToggleBuilder(resetKey, new TranslatableText("config.limitless.useGlobalMaxLevel"), entry.useGlobalMaxLevel)
                        .setDefaultValue(false)
                        .setSaveConsumer((final Boolean useGlobalMaxLevel) -> {
                            ((EnchantmentAccess) enchantment).limitless_setUseGlobalMaxLevel(useGlobalMaxLevel);
                            entry.useGlobalMaxLevel = useGlobalMaxLevel;
                        }).build()
                    );

                    listBuilder.add(builder.build());
                }
            }

            entries.add(listBuilder.build());

            return entries;
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
