package de.mittelalter.registry;

import de.mittelalter.MittelalterMod;
import de.mittelalter.item.CommandBatonItem;
import de.mittelalter.item.RecruitmentContractItem;
import de.mittelalter.item.TournamentGroundsDeedItem;
import de.mittelalter.item.CamelotCharterItem;
import de.mittelalter.item.RoleTokenItem;
import de.mittelalter.role.ArthurianRole;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MittelalterMod.MODID);

    // Damage bonus and attack-speed modifier deliberately encode a distinct role for each weapon.
    public static final DeferredItem<Item> LONGSWORD = ITEMS.registerSimpleItem(
            "longsword", properties -> properties.sword(ToolMaterial.IRON, 4.0F, -2.6F));
    public static final DeferredItem<Item> POLEAXE = ITEMS.registerSimpleItem(
            "poleaxe", properties -> properties.sword(ToolMaterial.IRON, 6.0F, -3.2F));
    public static final DeferredItem<Item> HALBERD = ITEMS.registerSimpleItem(
            "halberd", properties -> properties.sword(ToolMaterial.IRON, 5.0F, -3.0F));

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerSimpleItem("raw_silver");
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot");
    public static final DeferredItem<Item> RUBY = ITEMS.registerSimpleItem("ruby");
    public static final DeferredItem<Item> OFFICER_SIGNET = ITEMS.registerSimpleItem("officer_signet");
    public static final DeferredItem<Item> LION_SURCOAT = ITEMS.registerSimpleItem(
            "lion_surcoat", properties -> properties.humanoidArmor(ModArmorMaterials.LION, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> STAG_SURCOAT = ITEMS.registerSimpleItem(
            "stag_surcoat", properties -> properties.humanoidArmor(ModArmorMaterials.STAG, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> RAVEN_SURCOAT = ITEMS.registerSimpleItem(
            "raven_surcoat", properties -> properties.humanoidArmor(ModArmorMaterials.RAVEN, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> GRAIL_SURCOAT = ITEMS.registerSimpleItem(
            "grail_surcoat", properties -> properties.humanoidArmor(ModArmorMaterials.GRAIL, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> TOURNAMENT_TOKEN = ITEMS.registerSimpleItem("tournament_token");
    public static final DeferredItem<Item> CHAMPION_LAUREL = ITEMS.registerSimpleItem(
            "champion_laurel", properties -> properties.stacksTo(1));
    public static final DeferredItem<RecruitmentContractItem> FOOT_SOLDIER_CONTRACT = ITEMS.registerItem(
            "foot_soldier_contract", properties -> new RecruitmentContractItem(properties.stacksTo(16), ModEntities.FOOT_SOLDIER::get));
    public static final DeferredItem<RecruitmentContractItem> ARCHER_CONTRACT = ITEMS.registerItem(
            "archer_contract", properties -> new RecruitmentContractItem(properties.stacksTo(16), ModEntities.ARCHER_SOLDIER::get));
    public static final DeferredItem<CommandBatonItem> COMMAND_BATON = ITEMS.registerItem(
            "command_baton", properties -> new CommandBatonItem(properties.stacksTo(1)));
    public static final DeferredItem<TournamentGroundsDeedItem> TOURNAMENT_GROUNDS_DEED = ITEMS.registerItem(
            "tournament_grounds_deed", properties -> new TournamentGroundsDeedItem(properties.stacksTo(1)));
    public static final DeferredItem<CamelotCharterItem> CAMELOT_CHARTER = ITEMS.registerItem(
            "camelot_charter", properties -> new CamelotCharterItem(properties.stacksTo(1)));
    public static final DeferredItem<RoleTokenItem> ARTHUR_SIGNET = ITEMS.registerItem(
            "arthur_signet", properties -> new RoleTokenItem(properties.stacksTo(1), ArthurianRole.ARTHUR));
    public static final DeferredItem<RoleTokenItem> MERLIN_GRIMOIRE = ITEMS.registerItem(
            "merlin_grimoire", properties -> new RoleTokenItem(properties.stacksTo(1), ArthurianRole.MERLIN));

    public static final DeferredItem<BlockItem> SILVER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<BlockItem> RAW_SILVER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<BlockItem> SILVER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_BLOCK);
    public static final DeferredItem<BlockItem> RUBY_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.RUBY_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_RUBY_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_RUBY_ORE);
    public static final DeferredItem<BlockItem> RUBY_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RUBY_BLOCK);
    public static final DeferredItem<BlockItem> OAK_PALISADE = ITEMS.registerSimpleBlockItem(ModBlocks.OAK_PALISADE);
    public static final DeferredItem<BlockItem> REINFORCED_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_STONE);
    public static final DeferredItem<BlockItem> ARROW_SLIT = ITEMS.registerSimpleBlockItem(ModBlocks.ARROW_SLIT);
    public static final DeferredItem<BlockItem> TOURNAMENT_STANDARD = ITEMS.registerSimpleBlockItem(ModBlocks.TOURNAMENT_STANDARD);

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
