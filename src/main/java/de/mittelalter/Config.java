package de.mittelalter;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
public class Config {
    public static final boolean DEFAULT_ARTHURIAN_ENABLED = false;
    public static final boolean DEFAULT_SPECIAL_ROLES_ENABLED = false;
    public static final boolean DEFAULT_MYTHIC_ENABLED = false;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    public static final ModConfigSpec.IntValue COMMAND_RADIUS = BUILDER
            .comment("Maximum distance in blocks at which owned soldiers accept commands")
            .defineInRange("soldiers.commandRadius", 32, 4, 128);

    public static final ModConfigSpec.IntValue SOLDIER_CAP = BUILDER
            .comment("Maximum number of living recruited soldiers per player")
            .defineInRange("soldiers.perPlayerCap", 16, 1, 64);

    public static final ModConfigSpec.BooleanValue ARTHURIAN_ENABLED = BUILDER
            .comment("Whether optional Arthurian heraldry and courtly content is available")
            .define("arthurian.enabled", DEFAULT_ARTHURIAN_ENABLED);

    public static final ModConfigSpec.BooleanValue SPECIAL_ROLES_ENABLED = BUILDER
            .comment("Whether administrators may grant the optional Arthur and Merlin roles")
            .define("arthurian.specialRolesEnabled", DEFAULT_SPECIAL_ROLES_ENABLED);

    public static final ModConfigSpec.BooleanValue MYTHIC_ENABLED = BUILDER
            .comment("Whether optional mythic Arthurian events and the Merlin role are available")
            .define("arthurian.mythicEnabled", DEFAULT_MYTHIC_ENABLED);

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }
}
