package de.mittelalter.registry;

import de.mittelalter.MittelalterMod;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/** Armor materials that preserve iron gameplay values while selecting herald-specific client assets. */
public final class ModArmorMaterials {
    public static final ArmorMaterial LION = ironWithAsset("lion_surcoat");
    public static final ArmorMaterial STAG = ironWithAsset("stag_surcoat");
    public static final ArmorMaterial RAVEN = ironWithAsset("raven_surcoat");
    public static final ArmorMaterial GRAIL = ironWithAsset("grail_surcoat");

    private ModArmorMaterials() {
    }

    private static ArmorMaterial ironWithAsset(String name) {
        ArmorMaterial iron = ArmorMaterials.IRON;
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(
                EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(MittelalterMod.MODID, name));
        return new ArmorMaterial(
                iron.durability(), iron.defense(), iron.enchantmentValue(), iron.equipSound(),
                iron.toughness(), iron.knockbackResistance(), iron.repairIngredient(), asset);
    }
}
