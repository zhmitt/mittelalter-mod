package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

class ArthurianHeraldryTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");
    private static final List<String> SURCOATS = List.of(
            "lion_surcoat", "stag_surcoat", "raven_surcoat", "grail_surcoat");

    @Test
    void arthurianLayerDefaultsToDisabled() {
        assertFalse(Config.DEFAULT_ARTHURIAN_ENABLED);
    }

    @Test
    void surcoatMaterialsHaveIronProtectionAndDurability() {
        String source = read(Path.of("src/main/java/de/mittelalter/registry/ModArmorMaterials.java"));
        assertTrue(source.contains("ArmorMaterials.IRON"));
        assertTrue(source.contains("iron.durability()"));
        assertTrue(source.contains("iron.defense()"));
        assertTrue(source.contains("iron.toughness()"));
        assertTrue(source.contains("iron.knockbackResistance()"));
    }

    @Test
    void allHeraldicItemsHaveCompleteClientAndRecipeResources() {
        for (String surcoat : SURCOATS) {
            assertFile("assets/mittelalter/items/" + surcoat + ".json");
            assertFile("assets/mittelalter/models/item/" + surcoat + ".json");
            assertFile("assets/mittelalter/textures/item/" + surcoat + ".png");
            assertFile("assets/mittelalter/equipment/" + surcoat + ".json");
            assertFile("data/mittelalter/recipe/" + surcoat + ".json");
            assertTrue(read(RESOURCES.resolve("data/mittelalter/recipe/" + surcoat + ".json"))
                    .contains("mittelalter:arthurian_enabled"));
        }
        for (String reward : List.of("tournament_token", "champion_laurel")) {
            assertFile("assets/mittelalter/items/" + reward + ".json");
            assertFile("assets/mittelalter/models/item/" + reward + ".json");
            assertFile("assets/mittelalter/textures/item/" + reward + ".png");
        }
    }

    @Test
    void wornSurcoatsHaveFourDistinctDefaultColors() {
        long colors = SURCOATS.stream()
                .map(name -> read(RESOURCES.resolve("assets/mittelalter/equipment/" + name + ".json")))
                .map(text -> text.replaceAll(".*color_when_undyed\\\":(-?[0-9]+).*", "$1"))
                .distinct().count();
        assertEquals(4, colors);
    }

    @Test
    void localizationsCoverAllSixItems() {
        String english = read(RESOURCES.resolve("assets/mittelalter/lang/en_us.json"));
        String german = read(RESOURCES.resolve("assets/mittelalter/lang/de_de.json"));
        for (String item : List.of("lion_surcoat", "stag_surcoat", "raven_surcoat", "grail_surcoat",
                "tournament_token", "champion_laurel")) {
            assertTrue(english.contains("item.mittelalter." + item));
            assertTrue(german.contains("item.mittelalter." + item));
        }
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (Exception exception) {
            throw new AssertionError("Cannot read " + path, exception);
        }
    }

    private static void assertFile(String relative) {
        assertTrue(Files.isRegularFile(RESOURCES.resolve(relative)), () -> "Missing resource: " + relative);
    }
}
