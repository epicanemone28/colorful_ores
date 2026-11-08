package asof.colorful_ore.datagen;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static asof.colorful_ore.ColorfulOres.*;

public class ColorfulBlockStateProvider extends BlockStateProvider {
    // Parameter values are provided by GatherDataEvent.
    public ColorfulBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        // Replace "examplemod" with your own mod id.
        super(output, ColorfulOres.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        Block colorfulPlate = COLORFUL_PLATE.get();
        Block colorfulMetalBlock = COLORFUL_METAL_BLOCK.get();

        //simpleBlock(colorfulPlate);
        //simpleBlock(colorfulMetalBlock);




    }
}