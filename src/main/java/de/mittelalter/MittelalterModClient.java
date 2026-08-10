package de.mittelalter;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import de.mittelalter.registry.ModEntities;
import de.mittelalter.client.SoldierRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MittelalterMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MittelalterMod.MODID, value = Dist.CLIENT)
public class MittelalterModClient {
    public MittelalterModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MittelalterMod.LOGGER.info("Mittelalter Mod: client setup complete");
    }

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.FOOT_SOLDIER.get(), SoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.ARCHER_SOLDIER.get(), SoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.SIR_BEDIVERE.get(), SoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.SIR_GAWAIN.get(), SoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.SIR_LANCELOT.get(), SoldierRenderer::new);
    }
}
