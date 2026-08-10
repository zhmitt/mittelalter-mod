package de.mittelalter.camelot;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.mittelalter.MittelalterMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public final class CamelotSavedData extends SavedData {
    public static final Codec<CamelotSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.optionalFieldOf("location").forGetter(CamelotSavedData::location)
    ).apply(instance, CamelotSavedData::new));
    public static final SavedDataType<CamelotSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(MittelalterMod.MODID, "camelot"), CamelotSavedData::new, CODEC);
    private final CamelotUniqueness uniqueness;

    public CamelotSavedData() { this(Optional.empty()); }
    private CamelotSavedData(Optional<BlockPos> location) { this.uniqueness = new CamelotUniqueness(location.map(p -> new CamelotUniqueness.Location(p.getX(), p.getY(), p.getZ()))); }
    public static CamelotSavedData get(ServerLevel level) { return level.getServer().overworld().getDataStorage().computeIfAbsent(TYPE); }
    public Optional<BlockPos> location() { return uniqueness.location().map(p -> new BlockPos(p.x(), p.y(), p.z())); }
    public boolean claim(BlockPos pos) {
        if (!uniqueness.claim(new CamelotUniqueness.Location(pos.getX(), pos.getY(), pos.getZ()))) return false;
        setDirty();
        return true;
    }
}
