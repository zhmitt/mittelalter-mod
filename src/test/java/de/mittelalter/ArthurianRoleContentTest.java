package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ArthurianRoleContentTest {
    private static final Path RESOURCES = Path.of("src", "main", "resources");

    @Test void roleTokensHaveModelsAndEnglishGermanPresentation() throws Exception {
        String english = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/en_us.json"));
        String german = Files.readString(RESOURCES.resolve("assets/mittelalter/lang/de_de.json"));
        for (String item : new String[]{"arthur_signet", "merlin_grimoire"}) {
            assertTrue(Files.isRegularFile(RESOURCES.resolve("assets/mittelalter/items/" + item + ".json")));
            assertTrue(Files.isRegularFile(RESOURCES.resolve("assets/mittelalter/models/item/" + item + ".json")));
            assertTrue(english.contains("item.mittelalter." + item));
            assertTrue(german.contains("item.mittelalter." + item));
        }
        assertTrue(english.contains("message.mittelalter.role.denied.arthur"));
        assertTrue(german.contains("message.mittelalter.role.denied.merlin"));
    }
}
