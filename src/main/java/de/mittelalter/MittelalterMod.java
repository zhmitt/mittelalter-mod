package de.mittelalter;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import de.mittelalter.registry.ModBlocks;
import de.mittelalter.registry.ModCreativeTabs;
import de.mittelalter.registry.ModConditions;
import de.mittelalter.registry.ModItems;
import de.mittelalter.registry.ModEntities;
import de.mittelalter.soldier.SoldierEntity;
import de.mittelalter.tournament.TournamentManager;
import de.mittelalter.role.RoleEvents;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MittelalterMod.MODID)
public class MittelalterMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "mittelalter";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MittelalterMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModConditions.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new TournamentManager());
        NeoForge.EVENT_BUS.register(new RoleEvents());

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::registerAttributes);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FOOT_SOLDIER.get(), SoldierEntity.createAttributes().build());
        event.put(ModEntities.ARCHER_SOLDIER.get(), SoldierEntity.createAttributes().build());
        event.put(ModEntities.SIR_BEDIVERE.get(), SoldierEntity.createAttributes().build());
        event.put(ModEntities.SIR_GAWAIN.get(), SoldierEntity.createAttributes().build());
        event.put(ModEntities.SIR_LANCELOT.get(), SoldierEntity.createAttributes().build());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Mittelalter Mod: common setup complete");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Mittelalter Mod: server starting");
    }
}
