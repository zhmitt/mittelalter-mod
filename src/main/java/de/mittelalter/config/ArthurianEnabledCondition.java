package de.mittelalter.config;

import com.mojang.serialization.MapCodec;

import de.mittelalter.Config;
import net.neoforged.neoforge.common.conditions.ICondition;

/** Datapack condition used to omit Arthurian recipes while the optional layer is disabled. */
public final class ArthurianEnabledCondition implements ICondition {
    public static final ArthurianEnabledCondition INSTANCE = new ArthurianEnabledCondition();
    public static final MapCodec<ArthurianEnabledCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    private ArthurianEnabledCondition() {
    }

    @Override
    public boolean test(IContext context) {
        return Config.ARTHURIAN_ENABLED.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
