package com.resourcefulbees.resourcefulbees.registry;

import com.resourcefulbees.resourcefulbees.ResourcefulBees;
import com.resourcefulbees.resourcefulbees.block.*;
import com.resourcefulbees.resourcefulbees.block.multiblocks.apiary.ApiaryBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.apiary.ApiaryBreederBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.apiary.ApiaryStorageBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.centrifuge.CentrifugeCasingBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.centrifuge.CentrifugeControllerBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.centrifuge.EliteCentrifugeCasingBlock;
import com.resourcefulbees.resourcefulbees.block.multiblocks.centrifuge.EliteCentrifugeControllerBlock;
import com.resourcefulbees.resourcefulbees.lib.ModConstants;
import com.resourcefulbees.resourcefulbees.tileentity.HoneyTankTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    private ModBlocks() {
        throw new IllegalStateException(ModConstants.UTILITY_CLASS);
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ResourcefulBees.MOD_ID);


    private static final AbstractBlock.Properties CENTRIFUGE_PROPERTIES = AbstractBlock.Properties.of(Material.METAL).strength(2).sound(SoundType.METAL);
    private static final AbstractBlock.Properties HIVE_PROPERTIES = AbstractBlock.Properties.of(Material.WOOD).strength(2).sound(SoundType.WOOD);
    private static final AbstractBlock.Properties NEST_PROPERTIES = AbstractBlock.Properties.of(Material.WOOD).strength(0.3F).sound(SoundType.WOOD);

    //TODO 1.17 remove Tiered hives in favor of upgradeable nests
    public static final RegistryObject<Block> T1_BEEHIVE = BLOCKS.register("t1_beehive", () -> new TieredBeehiveBlock(1, 1.0F, HIVE_PROPERTIES));
    public static final RegistryObject<Block> T2_BEEHIVE = BLOCKS.register("t2_beehive", () -> new TieredBeehiveBlock(2, 1.5F, HIVE_PROPERTIES));
    public static final RegistryObject<Block> T3_BEEHIVE = BLOCKS.register("t3_beehive", () -> new TieredBeehiveBlock(3, 2.0F, HIVE_PROPERTIES));
    public static final RegistryObject<Block> T4_BEEHIVE = BLOCKS.register("t4_beehive", () -> new TieredBeehiveBlock(4, 4.0F, HIVE_PROPERTIES));
    public static final RegistryObject<Block> CENTRIFUGE = BLOCKS.register("centrifuge", () -> new CentrifugeBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> WAX_BLOCK = BLOCKS.register("wax_block", () -> new Block(AbstractBlock.Properties.of(Material.CLAY).sound(SoundType.SNOW).strength(0.3F)));
    public static final RegistryObject<Block> PREVIEW_BLOCK = BLOCKS.register("preview_block", () -> new Block(AbstractBlock.Properties.of(Material.DECORATION).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> ERRORED_PREVIEW_BLOCK = BLOCKS.register("error_preview_block", () -> new Block(AbstractBlock.Properties.of(Material.DECORATION).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> GOLD_FLOWER = BLOCKS.register("gold_flower", () -> new FlowerBlock(Effects.INVISIBILITY, 10, AbstractBlock.Properties.of(Material.PLANT).noCollission().strength(0).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> OAK_BEE_NEST = BLOCKS.register("bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> BIRCH_BEE_NEST = BLOCKS.register("birch_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> BROWN_MUSHROOM_BEE_NEST = BLOCKS.register("brown_mushroom_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> CRIMSON_BEE_NEST = BLOCKS.register("crimson_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> CRIMSON_NYLIUM_BEE_NEST = BLOCKS.register("crimson_nylium_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> DARK_OAK_BEE_NEST = BLOCKS.register("dark_oak_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> RED_MUSHROOM_BEE_NEST = BLOCKS.register("red_mushroom_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> SPRUCE_BEE_NEST = BLOCKS.register("spruce_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> WARPED_BEE_NEST = BLOCKS.register("warped_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> WARPED_NYLIUM_BEE_NEST = BLOCKS.register("warped_nylium_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> ACACIA_BEE_NEST = BLOCKS.register("acacia_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> GRASS_BEE_NEST = BLOCKS.register("grass_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> JUNGLE_BEE_NEST = BLOCKS.register("jungle_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> NETHER_BEE_NEST = BLOCKS.register("nether_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> PRISMARINE_BEE_NEST = BLOCKS.register("prismarine_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> PURPUR_BEE_NEST = BLOCKS.register("purpur_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> WITHER_BEE_NEST = BLOCKS.register("wither_bee_nest", () -> new TieredBeehiveBlock(0, 0.5F, NEST_PROPERTIES));
    public static final RegistryObject<Block> T1_APIARY_BLOCK = BLOCKS.register("t1_apiary", () -> new ApiaryBlock(5, 5, 6));
    public static final RegistryObject<Block> T2_APIARY_BLOCK = BLOCKS.register("t2_apiary", () -> new ApiaryBlock(6, 5, 6));
    public static final RegistryObject<Block> T3_APIARY_BLOCK = BLOCKS.register("t3_apiary", () -> new ApiaryBlock(7, 6, 8));
    public static final RegistryObject<Block> T4_APIARY_BLOCK = BLOCKS.register("t4_apiary", () -> new ApiaryBlock(8, 6, 8));
    public static final RegistryObject<Block> APIARY_BREEDER_BLOCK = BLOCKS.register("apiary_breeder", () -> new ApiaryBreederBlock(NEST_PROPERTIES));
    public static final RegistryObject<Block> APIARY_STORAGE_BLOCK = BLOCKS.register("apiary_storage", () -> new ApiaryStorageBlock(NEST_PROPERTIES));
    public static final RegistryObject<Block> MECHANICAL_CENTRIFUGE = BLOCKS.register("mechanical_centrifuge", () -> new MechanicalCentrifugeBlock(CENTRIFUGE_PROPERTIES.noOcclusion()));
    public static final RegistryObject<Block> HONEY_GENERATOR = BLOCKS.register("honey_generator", () -> new HoneyGenerator(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<FlowingFluidBlock> HONEY_FLUID_BLOCK = BLOCKS.register("honey_fluid_block", () -> new FlowingFluidBlock(ModFluids.HONEY_STILL, AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> CATNIP_HONEY_FLUID_BLOCK = BLOCKS.register("catnip_honey_fluid_block", () -> new FlowingFluidBlock(ModFluids.CATNIP_HONEY_STILL, AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> CENTRIFUGE_CONTROLLER = BLOCKS.register("centrifuge_controller", () -> new CentrifugeControllerBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> CENTRIFUGE_CASING = BLOCKS.register("centrifuge_casing", () -> new CentrifugeCasingBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> CREATIVE_GEN = BLOCKS.register("creative_gen", () -> new CreativeGen(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> ACCELERATOR = BLOCKS.register("accelerator", () -> new AcceleratorBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> ELITE_CENTRIFUGE_CONTROLLER = BLOCKS.register("elite_centrifuge_controller", () -> new EliteCentrifugeControllerBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> ELITE_CENTRIFUGE_CASING = BLOCKS.register("elite_centrifuge_casing", () -> new EliteCentrifugeCasingBlock(CENTRIFUGE_PROPERTIES));
    public static final RegistryObject<Block> ENDER_BEECON = BLOCKS.register("ender_beecon", () -> new EnderBeecon(EnderBeecon.PROPERTIES));
    public static final RegistryObject<Block> PURPUR_HONEY_TANK = BLOCKS.register("purpur_honey_tank", () -> new HoneyTank(HoneyTank.PROPERTIES, HoneyTankTileEntity.TankTier.PURPUR));
    public static final RegistryObject<Block> NETHER_HONEY_TANK = BLOCKS.register("nether_honey_tank", () -> new HoneyTank(HoneyTank.PROPERTIES, HoneyTankTileEntity.TankTier.NETHER));
    public static final RegistryObject<Block> WOODEN_HONEY_TANK = BLOCKS.register("wooden_honey_tank", () -> new HoneyTank(HoneyTank.PROPERTIES, HoneyTankTileEntity.TankTier.WOODEN));
    public static final RegistryObject<Block> CATNIP_HONEYCOMB_BLOCK = BLOCKS.register("catnip_honeycomb_block", () -> new Block(AbstractBlock.Properties.of(Material.CLAY).sound(SoundType.SNOW).strength(0.3F)));
    public static final RegistryObject<Block> CATNIP_HONEY_BLOCK = BLOCKS.register("catnip_honey_block", CatnipHoneyBlock::new);

}
