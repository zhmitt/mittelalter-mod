package de.mittelalter.camelot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.mittelalter.MittelalterMod;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public final class RenownSavedData extends SavedData {
    public static final Codec<RenownSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.INT).optionalFieldOf("players", Map.of()).forGetter(data -> data.serialized())
    ).apply(instance, RenownSavedData::fromSerialized));
    public static final SavedDataType<RenownSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(MittelalterMod.MODID, "renown"), RenownSavedData::new, CODEC);
    private final RenownLedger ledger;

    public RenownSavedData() { this.ledger = new RenownLedger(); }
    private RenownSavedData(RenownLedger ledger) { this.ledger = ledger; }

    public static RenownSavedData get(ServerLevel level) { return level.getServer().overworld().getDataStorage().computeIfAbsent(TYPE); }
    public int points(UUID player) { return ledger.points(player); }
    public RenownTier tier(UUID player) { return ledger.tier(player); }
    public int award(UUID player, int amount) {
        if (amount < 0) throw new IllegalArgumentException("Renown award cannot be negative");
        int total = ledger.award(player, amount);
        setDirty();
        return total;
    }
    private Map<String, Integer> serialized() {
        Map<String, Integer> result = new HashMap<>();
        ledger.snapshot().forEach((id, value) -> result.put(id.toString(), value));
        return result;
    }
    private static RenownSavedData fromSerialized(Map<String, Integer> values) {
        Map<UUID, Integer> parsed = new HashMap<>();
        values.forEach((key, value) -> { try { parsed.put(UUID.fromString(key), value); } catch (IllegalArgumentException ignored) {} });
        return new RenownSavedData(new RenownLedger(parsed));
    }
}
