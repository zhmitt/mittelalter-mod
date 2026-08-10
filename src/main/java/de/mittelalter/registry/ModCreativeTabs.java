package de.mittelalter.registry;

import de.mittelalter.Config;
import de.mittelalter.MittelalterMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, MittelalterMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MITTELALTER = TABS.register(
            "mittelalter_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mittelalter"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.LONGSWORD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.LONGSWORD.get());
                        output.accept(ModItems.POLEAXE.get());
                        output.accept(ModItems.HALBERD.get());
                        output.accept(ModItems.RAW_SILVER.get());
                        output.accept(ModItems.SILVER_INGOT.get());
                        output.accept(ModItems.SILVER_ORE.get());
                        output.accept(ModItems.DEEPSLATE_SILVER_ORE.get());
                        output.accept(ModItems.RAW_SILVER_BLOCK.get());
                        output.accept(ModItems.SILVER_BLOCK.get());
                        output.accept(ModItems.RUBY.get());
                        output.accept(ModItems.OFFICER_SIGNET.get());
                        output.accept(ModItems.FOOT_SOLDIER_CONTRACT.get());
                        output.accept(ModItems.ARCHER_CONTRACT.get());
                        output.accept(ModItems.COMMAND_BATON.get());
                        output.accept(ModItems.RUBY_ORE.get());
                        output.accept(ModItems.DEEPSLATE_RUBY_ORE.get());
                        output.accept(ModItems.RUBY_BLOCK.get());
                        output.accept(ModItems.OAK_PALISADE.get());
                        output.accept(ModItems.REINFORCED_STONE.get());
                        output.accept(ModItems.ARROW_SLIT.get());
                        if (Config.ARTHURIAN_ENABLED.get()) {
                            output.accept(ModItems.LION_SURCOAT.get());
                            output.accept(ModItems.STAG_SURCOAT.get());
                            output.accept(ModItems.RAVEN_SURCOAT.get());
                            output.accept(ModItems.GRAIL_SURCOAT.get());
                            output.accept(ModItems.TOURNAMENT_TOKEN.get());
                            output.accept(ModItems.CHAMPION_LAUREL.get());
                            output.accept(ModItems.TOURNAMENT_GROUNDS_DEED.get());
                            output.accept(ModItems.CAMELOT_CHARTER.get());
                            output.accept(ModItems.TOURNAMENT_STANDARD.get());
                            if (Config.SPECIAL_ROLES_ENABLED.get()) {
                                output.accept(ModItems.ARTHUR_SIGNET.get());
                                if (Config.MYTHIC_ENABLED.get()) output.accept(ModItems.MERLIN_GRIMOIRE.get());
                            }
                        }
                    })
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
