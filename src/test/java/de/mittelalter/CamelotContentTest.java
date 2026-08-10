package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class CamelotContentTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");
    @Test void charterHasGatedRecipeClientDefinitionAndTranslations() throws Exception {
        assertTrue(Files.isRegularFile(RESOURCES.resolve("assets/mittelalter/items/camelot_charter.json")));
        assertTrue(Files.isRegularFile(RESOURCES.resolve("assets/mittelalter/models/item/camelot_charter.json")));
        String recipe = Files.readString(RESOURCES.resolve("data/mittelalter/recipe/camelot_charter.json"));
        assertTrue(recipe.contains("mittelalter:arthurian_enabled"));
        for (String language : new String[]{"en_us.json", "de_de.json"}) {
            String text = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/" + language));
            assertTrue(text.contains("item.mittelalter.camelot_charter"));
            assertTrue(text.contains("message.mittelalter.renown.awarded"));
        }
    }
}
