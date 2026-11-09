package asof.colorful_ore.worldgen;

import asof.colorful_ore.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static asof.colorful_ore.registries.RegisterMiscWorldgen.CONFIG_PLACEMENT_FILTER;

public class ConfigPlacementFilter extends PlacementFilter {
    public final String configSettingName;

    public String getConfigSettingName() {
        return configSettingName;
    }

    public static final MapCodec<ConfigPlacementFilter> CODEC = RecordCodecBuilder.mapCodec(p-> p.group(
            Codec.STRING.fieldOf("config_flag").forGetter(ConfigPlacementFilter::getConfigSettingName)
    ).apply(p,ConfigPlacementFilter::new));

    public ConfigPlacementFilter(String configSettingName){
        this.configSettingName = configSettingName;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if(!Config.worldgenConfigFlags.containsKey(configSettingName)){
            String featureName = "unknown";
            if(context.topFeature().isPresent()){
                featureName = context.topFeature().get().feature().getRegisteredName();
            }
            throw new IllegalArgumentException(String.format("Issue in feature '%s' tried to access flag '%s' despite it not existing. This placement modifier is intended for internal use only, so if you get this error it is worth reporting it to the developer of the Colorful Ores mod.",featureName, configSettingName));
        }
        return Config.worldgenConfigFlags.get(configSettingName);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return CONFIG_PLACEMENT_FILTER.get();
    }
}
