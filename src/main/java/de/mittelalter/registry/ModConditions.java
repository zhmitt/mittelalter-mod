package de.mittelalter.registry;

import com.mojang.serialization.MapCodec;

import de.mittelalter.MittelalterMod;
import de.mittelalter.config.ArthurianEnabledCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModConditions {
    private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(
            NeoForgeRegistries.Keys.CONDITION_CODECS, MittelalterMod.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ArthurianEnabledCondition>> ARTHURIAN_ENABLED =
            CONDITIONS.register("arthurian_enabled", () -> ArthurianEnabledCondition.CODEC);

    private ModConditions() {
    }

    public static void register(IEventBus modEventBus) {
        CONDITIONS.register(modEventBus);
    }
}
