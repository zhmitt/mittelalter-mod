package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FortificationContentTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");
    private static final List<String> BLOCKS = List.of("oak_palisade", "reinforced_stone", "arrow_slit");

    @Test
    void everyFortificationHasCompleteClientAndSurvivalResources() throws Exception {
        String english = read("assets/mittelalter/lang/en_us.json");
        String german = read("assets/mittelalter/lang/de_de.json");
        for (String block : BLOCKS) {
            assertResource("assets/mittelalter/blockstates/" + block + ".json");
            assertResource("assets/mittelalter/models/block/" + block + ".json");
            assertResource("assets/mittelalter/items/" + block + ".json");
            assertResource("assets/mittelalter/textures/block/" + block + ".png");
            assertResource("data/mittelalter/recipe/" + block + ".json");
            assertResource("data/mittelalter/loot_table/blocks/" + block + ".json");
            assertTrue(english.contains("\"block.mittelalter." + block + "\""));
            assertTrue(german.contains("\"block.mittelalter." + block + "\""));
        }
    }

    @Test
    void arrowSlitUsesMasonryForFacesAndParticles() throws Exception {
        String model = read("assets/mittelalter/models/block/arrow_slit.json");
        assertTrue(model.contains("\"all\": \"minecraft:block/stone_bricks\""),
                "Arrow-slit frame must render masonry, not the incorrectly copied sword sprite");
        assertTrue(model.contains("\"particle\": \"minecraft:block/stone_bricks\""),
                "Arrow-slit breaking particles must match the masonry frame");
    }

    @Test
    void miningTagsKeepWoodAndIronGatedStoneScoped() throws Exception {
        String axe = read("data/minecraft/tags/block/mineable/axe.json");
        String pickaxe = read("data/minecraft/tags/block/mineable/pickaxe.json");
        String iron = read("data/minecraft/tags/block/needs_iron_tool.json");
        assertTrue(axe.contains("mittelalter:oak_palisade"));
        for (String block : List.of("reinforced_stone", "arrow_slit")) {
            assertTrue(pickaxe.contains("mittelalter:" + block));
            assertTrue(iron.contains("mittelalter:" + block));
        }
        assertTrue(!iron.contains("minecraft:stone") && !iron.contains("minecraft:stone_bricks"),
                "Fortification progression must not alter vanilla stone tags");
    }

    private static String read(String relativePath) throws Exception {
        return Files.readString(RESOURCES.resolve(relativePath));
    }

    private static void assertResource(String relativePath) {
        assertTrue(Files.isRegularFile(RESOURCES.resolve(relativePath)), () -> "Missing resource: " + relativePath);
    }
}
