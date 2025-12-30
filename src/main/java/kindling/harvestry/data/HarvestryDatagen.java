package kindling.harvestry.data;

import kindling.harvestry.data.provider.resources.HarvestryLangGen;
import kindling.harvestry.data.provider.resources.HarvestryModelGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class HarvestryDatagen implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(HarvestryLangGen::new);
        pack.addProvider(HarvestryModelGen::new);
	}
}
