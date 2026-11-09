package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import asof.colorful_ore.worldgen.ConfigPlacementFilter;
import asof.colorful_ore.worldgen.ColorfulOreFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class RegisterMiscWorldgen {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ColorfulOres.MODID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_FILTERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, ColorfulOres.MODID);


    @SuppressWarnings({"UnusedDeclaration"})
    public static final DeferredHolder<Feature<?>, ?> COLORFUL_ORE_FEATURE = FEATURES.register("colorful_ore_feature", ()->new ColorfulOreFeature(OreConfiguration.CODEC));

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigPlacementFilter>> CONFIG_PLACEMENT_FILTER = PLACEMENT_FILTERS.register("config_placement_filter", ()->()->ConfigPlacementFilter.CODEC);

    public static void register(IEventBus eventBus){
        FEATURES.register(eventBus);
        PLACEMENT_FILTERS.register(eventBus);
    }
}
