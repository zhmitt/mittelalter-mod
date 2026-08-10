package de.mittelalter.registry;

import de.mittelalter.MittelalterMod;
import de.mittelalter.block.ArrowSlitBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MittelalterMod.MODID);

    public static final DeferredBlock<DropExperienceBlock> SILVER_ORE = BLOCKS.registerBlock(
            "silver_ore",
            properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SILVER_ORE = BLOCKS.registerBlock(
            "deepslate_silver_ore",
            properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = BLOCKS.registerSimpleBlock(
            "raw_silver_block", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK));
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.registerSimpleBlock(
            "silver_block", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    public static final DeferredBlock<DropExperienceBlock> RUBY_ORE = BLOCKS.registerBlock(
            "ruby_ore",
            properties -> new DropExperienceBlock(UniformInt.of(3, 7), properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE));
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_RUBY_ORE = BLOCKS.registerBlock(
            "deepslate_ruby_ore",
            properties -> new DropExperienceBlock(UniformInt.of(3, 7), properties),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE));
    public static final DeferredBlock<Block> RUBY_BLOCK = BLOCKS.registerSimpleBlock(
            "ruby_block", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));

    public static final DeferredBlock<Block> OAK_PALISADE = BLOCKS.registerSimpleBlock(
            "oak_palisade", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(3.0F, 4.0F));
    public static final DeferredBlock<Block> REINFORCED_STONE = BLOCKS.registerSimpleBlock(
            "reinforced_stone", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).strength(6.0F, 18.0F));
    public static final DeferredBlock<ArrowSlitBlock> ARROW_SLIT = BLOCKS.registerBlock(
            "arrow_slit", ArrowSlitBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).strength(6.0F, 18.0F).noOcclusion());
    public static final DeferredBlock<Block> TOURNAMENT_STANDARD = BLOCKS.registerSimpleBlock(
            "tournament_standard", () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).noOcclusion());

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
