package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

class ContentManifestTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");

    private static final List<String> ITEMS = List.of(
            "longsword", "poleaxe", "halberd",
            "raw_silver", "silver_ingot", "silver_ore", "deepslate_silver_ore",
            "raw_silver_block", "silver_block",
            "ruby", "ruby_ore", "deepslate_ruby_ore", "ruby_block", "officer_signet");

    private static final List<String> NON_BLOCK_ITEMS = List.of(
            "longsword", "poleaxe", "halberd", "raw_silver", "silver_ingot", "ruby", "officer_signet");

    private static final List<String> BLOCKS = List.of(
            "silver_ore", "deepslate_silver_ore", "raw_silver_block", "silver_block",
            "ruby_ore", "deepslate_ruby_ore", "ruby_block");

    @Test
    void allRegisteredContentHasClientDefinitionsAndTranslations() throws Exception {
        for (String item : ITEMS) {
            assertResource("assets/mittelalter/items/" + item + ".json");
        }

        String english = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/en_us.json"));
        String german = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/de_de.json"));
        for (String item : NON_BLOCK_ITEMS) {
            String key = "item.mittelalter." + item;
            assertTrue(english.contains('"' + key + '"'), () -> "Missing English translation: " + key);
            assertTrue(german.contains('"' + key + '"'), () -> "Missing German translation: " + key);
        }
        for (String block : BLOCKS) {
            String key = "block.mittelalter." + block;
            assertTrue(english.contains('"' + key + '"'), () -> "Missing English translation: " + key);
            assertTrue(german.contains('"' + key + '"'), () -> "Missing German translation: " + key);
        }
    }

    @Test
    void everyResourceBlockHasStateModelLootAndMiningTagCoverage() throws Exception {
        String pickaxeTag = Files.readString(RESOURCES.resolve("data/minecraft/tags/block/mineable/pickaxe.json"));
        for (String block : BLOCKS) {
            assertResource("assets/mittelalter/blockstates/" + block + ".json");
            assertResource("assets/mittelalter/models/block/" + block + ".json");
            assertResource("data/mittelalter/loot_table/blocks/" + block + ".json");
            assertTrue(pickaxeTag.contains("mittelalter:" + block), () -> "Missing pickaxe tag entry: " + block);
        }
    }

    @Test
    void weaponsAndPrestigeItemAreCraftableAndSilverIsProcessable() {
        for (String recipe : List.of("longsword", "poleaxe", "halberd", "officer_signet")) {
            assertResource("data/mittelalter/recipe/" + recipe + ".json");
        }
        assertResource("data/mittelalter/recipe/silver_ingot_from_smelting_raw_silver.json");
        assertResource("data/mittelalter/recipe/silver_ingot_from_blasting_raw_silver.json");
    }

    private static void assertResource(String relativePath) {
        assertTrue(Files.isRegularFile(RESOURCES.resolve(relativePath)), () -> "Missing resource: " + relativePath);
    }
}
