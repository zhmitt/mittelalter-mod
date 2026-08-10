package de.mittelalter.role;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.mittelalter.MittelalterMod;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public final class RoleSavedData extends SavedData {
    public static final Codec<RoleSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("players", Map.of())
                    .forGetter(data -> data.ledger.serialized())
    ).apply(instance, RoleSavedData::new));
    public static final SavedDataType<RoleSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(MittelalterMod.MODID, "arthurian_roles"), RoleSavedData::new, CODEC);

    private final RoleLedger ledger;
    public RoleSavedData() { this.ledger = new RoleLedger(); }
    private RoleSavedData(Map<String, String> serialized) { this.ledger = new RoleLedger(serialized); }
    public static RoleSavedData get(ServerLevel level) { return level.getServer().overworld().getDataStorage().computeIfAbsent(TYPE); }
    public Optional<ArthurianRole> find(UUID player) { return ledger.find(player); }
    public ArthurianRole getOrAssignDefault(UUID player) {
        boolean absent = ledger.find(player).isEmpty();
        ArthurianRole role = ledger.getOrAssignDefault(player);
        if (absent) setDirty();
        return role;
    }
    public void set(UUID player, ArthurianRole role) { ledger.set(player, role); setDirty(); }
}
