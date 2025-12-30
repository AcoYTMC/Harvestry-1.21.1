package kindling.harvestry.impl;

import com.mojang.logging.LogUtils;
import kindling.harvestry.impl.index.HarvestryItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class Harvestry implements ModInitializer {
    public static final String MOD_ID = "harvestry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public void onInitialize() {
        HarvestryItems.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
