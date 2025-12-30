package kindling.harvestry.impl.item;

import kindling.harvestry.api.item.DoubleWieldedItem;
import kindling.harvestry.impl.Harvestry;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ShearDaggersItem extends SwordItem implements DoubleWieldedItem, ModelVaryingItem {
    public ShearDaggersItem(Settings settings) {
        super(ToolMaterials.NETHERITE, settings);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return MiscUtils.isGui(renderMode) ? Harvestry.id("shear_daggers") : Harvestry.id("shear_daggers_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Harvestry.id("shear_daggers"),
                Harvestry.id("shear_daggers_in_hand")
        );
    }

    public BipedEntityModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayerEntity player) {
        return BipedEntityModel.ArmPose.ITEM;
    }
}
