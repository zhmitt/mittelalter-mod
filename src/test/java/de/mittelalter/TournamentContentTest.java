package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class TournamentContentTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");

    @Test
    void venueContentHasClientDataLootRecipeAndTranslations() throws Exception {
        for (String path : new String[] {
                "assets/mittelalter/blockstates/tournament_standard.json",
                "assets/mittelalter/models/block/tournament_standard.json",
                "assets/mittelalter/items/tournament_standard.json",
                "assets/mittelalter/items/tournament_grounds_deed.json",
                "data/mittelalter/loot_table/blocks/tournament_standard.json",
                "data/mittelalter/recipe/tournament_standard.json",
                "data/mittelalter/recipe/tournament_grounds_deed.json"}) {
            assertTrue(Files.isRegularFile(RESOURCES.resolve(path)), () -> "Missing " + path);
        }
        String english = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/en_us.json"));
        assertTrue(english.contains("block.mittelalter.tournament_standard"));
        assertTrue(english.contains("item.mittelalter.tournament_grounds_deed"));
        assertTrue(Files.readString(RESOURCES.resolve("data/mittelalter/recipe/tournament_standard.json"))
                .contains("mittelalter:arthurian_enabled"));
    }
}
