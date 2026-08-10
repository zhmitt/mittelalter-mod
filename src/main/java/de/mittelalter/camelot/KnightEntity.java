package de.mittelalter.camelot;

import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.soldier.SoldierRole;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class KnightEntity extends SoldierEntity {
    private final KnightDisposition disposition;

    protected KnightEntity(EntityType<? extends SoldierEntity> type, Level level, KnightArchetype archetype) {
        super(type, level, SoldierRole.FOOT);
        this.disposition = archetype.disposition();
        setCustomName(Component.literal(archetype.displayName()));
        setCustomNameVisible(true);
    }

    public KnightDisposition disposition() { return disposition; }

    public static final class Bedivere extends KnightEntity {
        public Bedivere(EntityType<? extends SoldierEntity> type, Level level) { super(type, level, KnightArchetype.BEDIVERE); }
    }
    public static final class Gawain extends KnightEntity {
        public Gawain(EntityType<? extends SoldierEntity> type, Level level) { super(type, level, KnightArchetype.GAWAIN); }
    }
    public static final class Lancelot extends KnightEntity {
        public Lancelot(EntityType<? extends SoldierEntity> type, Level level) { super(type, level, KnightArchetype.LANCELOT); }
    }
}
