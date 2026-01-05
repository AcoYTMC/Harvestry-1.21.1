package kindling.harvestry.impl;

import com.mojang.logging.LogUtils;
import kindling.harvestry.impl.event.ShearDaggersDropsHandler;
import kindling.harvestry.impl.index.HarvestryBlocks;
import kindling.harvestry.impl.index.HarvestryItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class Harvestry implements ModInitializer {
    public static final String MOD_ID = "harvestry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryKey<ItemGroup> HARVESTRY_MAIN_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "harvestry-main"));
    public static final ItemGroup HARVESTRY_MAIN = FabricItemGroup.builder()
            .icon(() -> new ItemStack(HarvestryItems.ALCHEMICAL_DAGGER))
            .displayName(Text.translatable("itemGroup.harvestry"))
            .build();

    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, HARVESTRY_MAIN_KEY, HARVESTRY_MAIN);

        HarvestryItems.init();
        HarvestryBlocks.init();

        ShearDaggersDropsHandler.register();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
