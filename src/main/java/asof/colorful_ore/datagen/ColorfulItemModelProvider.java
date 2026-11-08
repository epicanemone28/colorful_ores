package asof.colorful_ore.datagen;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static asof.colorful_ore.registries.RegisterItems.COLORFUL_PICKAXE;

@SuppressWarnings({"UnusedDeclaration"})
public class ColorfulItemModelProvider extends ItemModelProvider {
    // Parameter values are provided by GatherDataEvent.
    @SuppressWarnings({"UnusedDeclaration"})
    public ColorfulItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        // Replace "examplemod" with your own mod id.
        super(output, ColorfulOres.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(COLORFUL_PICKAXE.get())
                .texture("layer0","colorful_ore:item/colorful_pickaxe_overlay")
                .texture("layer1","colorful_ore:item/colorful_pickaxe_handle")
        ;
    }
}