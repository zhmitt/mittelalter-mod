package de.mittelalter.registry;

import de.mittelalter.MittelalterMod;
import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.camelot.KnightEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    private static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(MittelalterMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<SoldierEntity.Foot>> FOOT_SOLDIER = ENTITIES.registerEntityType(
            "foot_soldier", SoldierEntity.Foot::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<SoldierEntity.Archer>> ARCHER_SOLDIER = ENTITIES.registerEntityType(
            "archer_soldier", SoldierEntity.Archer::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<KnightEntity.Bedivere>> SIR_BEDIVERE = ENTITIES.registerEntityType(
            "sir_bedivere", KnightEntity.Bedivere::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<KnightEntity.Gawain>> SIR_GAWAIN = ENTITIES.registerEntityType(
            "sir_gawain", KnightEntity.Gawain::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<KnightEntity.Lancelot>> SIR_LANCELOT = ENTITIES.registerEntityType(
            "sir_lancelot", KnightEntity.Lancelot::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(10));

    private ModEntities() {}

    public static void register(IEventBus bus) { ENTITIES.register(bus); }
}
