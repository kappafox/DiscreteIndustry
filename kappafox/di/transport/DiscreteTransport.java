package kappafox.di.transport;

import ic2.api.item.Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.lib.DiscreteID;
import kappafox.di.decorative.DiscreteDecorativeEventListener;
import kappafox.di.decorative.renderers.TileEntitySwordRackRenderer;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import kappafox.di.electrics.items.ItemHorseInspector;
import kappafox.di.transport.blocks.BlockDiscreteHopper;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.blocks.items.ItemDiscreteHopperBlock;
import kappafox.di.transport.blocks.items.ItemDiscreteTransportBlock;
import kappafox.di.transport.items.ItemDiscreteTransport;
import kappafox.di.transport.recipies.RecipeUpgradeStorageUnit;
import kappafox.di.transport.renderers.DiscreteTransportRenderManager;
import kappafox.di.transport.renderers.TileEntityStorageRackRenderer;
import kappafox.di.transport.tileentities.TileEntityDiscreteDuct;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DiscreteTransport
{
		
	//blocks
	//public static Block discreteCableBlock;
	public static Block discreteHopperBlock;
	public static Block discreteTransportBlock;
	
	//items
	//public static Item discreteTransportItem;
	
	//ids
	//private int discreteCableID;
	public static int discreteHopperID;
	public static int discreteTransportMetaBlockID;
	public static int discreteTransportItemID;

	
	//Listeners
	private static DiscreteTransportEventListener events;
	
	//Render IDS
	private int renderID;
	
	//Model IDS
	public static int hopperRenderID;
	public static int transportBlockRenderID;
	
	//Items
	public static ItemDiscreteTransport discreteTransportItem;
	//public static Item storageBoxStone;
	//public static Item storageBoxIron;
	//public static Item storageBoxRedstone;
	//public static Item storageBoxGold;
	//public static Item storageBoxDiamond;
	//public static Item storageBoxEmerald;
	//public static Item storageBoxIridium;
	
	
	public DiscreteTransport( )
	{
		events = new DiscreteTransportEventListener();
	}
	
	public void preInitialisation(FMLPreInitializationEvent event, Configuration config)
	{

		//grab the id database
		DiscreteID ids = DiscreteIndustry.librarian.dibi;
		
		
		discreteTransportItemID = config.getItem("DiscreteTransportItem", ids.itemDiscreteTransport).getInt(ids.itemDiscreteTransport);
		discreteHopperID = config.getBlock("DiscreteHoppers", ids.discreteHopper).getInt(ids.discreteHopper);
		discreteTransportMetaBlockID = config.getBlock("DiscreteTransportBlock", ids.discreteTransportMetaBlock).getInt(ids.discreteTransportMetaBlock);
	}
	
	public void load(FMLInitializationEvent event)
	{
		//Renderers
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			registerRenderers();
		}
		
		//Blocks
		registerBlocks();
		
		//Items
		registerItems();
		
		//Recipies
		registerRecipies();
		
		//Event Handler
		MinecraftForge.EVENT_BUS.register(events);
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityDiscreteHopper.class, "Discrete Hopper");
		GameRegistry.registerTileEntity(TileEntityStorageRack.class, "Discrete Storage Rack");
		GameRegistry.registerTileEntity(TileEntityDiscreteDuct.class, "Discrete Duct");
	}



	
	private void registerItems( )
	{
		/*
		horseInspector = new ItemHorseInspector(discreteElectricItemID, "Horse Inspector", 1);
		LanguageRegistry.addName(horseInspector, "Horse Inspector");
		*/
		discreteTransportItem = new ItemDiscreteTransport(discreteTransportItemID);
		//LanguageRegistry.addName(discreteTransportItem, "Base Item");
		
		
		for(int i = 0; i <= 16; i += 2)
		{
			LanguageRegistry.addName(new ItemStack(discreteTransportItem, 1, i), discreteTransportItem.getName(i));
		}
	}

	private void registerBlocks( )
	{
		discreteHopperBlock = new BlockDiscreteHopper(discreteHopperID, Material.rock, hopperRenderID);
		discreteTransportBlock = new BlockDiscreteTransport(discreteTransportMetaBlockID, Material.rock, transportBlockRenderID);
		//discreteTransportBlock = new BlockDiscreteTransport(discreteTransportMetaBlockID, Material.wood);
		
		GameRegistry.registerBlock(discreteHopperBlock, ItemDiscreteHopperBlock.class, DiscreteIndustry.MODID + "hopperBlock");
		GameRegistry.registerBlock(discreteTransportBlock, ItemDiscreteTransportBlock.class, DiscreteIndustry.MODID + "discreteTransportBlock");
		
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 0), "Discrete Hopper");
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 1), "Discrete Hopper Half");
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 2), "Discrete Hopper Quarter");
		
		LanguageRegistry.addName(new ItemStack(discreteTransportBlock, 1, 100), "Storage Rack");
		LanguageRegistry.addName(new ItemStack(discreteTransportBlock, 1, 101), "Dual Storage Rack");
		LanguageRegistry.addName(new ItemStack(discreteTransportBlock, 1, 102), "Quad Storage Rack");
	}
	
	@SideOnly(Side.CLIENT)
	private void registerRenderers( )
	{
		
		renderID = RenderingRegistry.getNextAvailableRenderId();
		hopperRenderID = RenderingRegistry.getNextAvailableRenderId();
		transportBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		DiscreteTransportRenderManager manager = new DiscreteTransportRenderManager(renderID);
		RenderingRegistry.registerBlockHandler(hopperRenderID, manager);
		RenderingRegistry.registerBlockHandler(transportBlockRenderID, manager);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageRack.class, new TileEntityStorageRackRenderer());

	}


	private void registerRecipies( )
	{
		
		
		ItemStack bcircuit = Items.getItem("electronicCircuit");
		ItemStack hop = new ItemStack(Block.hopperBlock);
		
		ItemStack discreteHopper = new ItemStack(discreteHopperBlock, 1, 0);
		ItemStack halfHopper = new ItemStack(discreteHopperBlock, 2, 1);
		ItemStack halfHopperSingle = new ItemStack(discreteHopperBlock, 1, 1);
		ItemStack quarterHopper = new ItemStack(discreteHopperBlock, 2, 2);
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(discreteHopper, bcircuit, hop));
		
		//Hoppers compressing down
		Recipes.compressor.addRecipe(new RecipeInputItemStack(discreteHopper), null, halfHopper);
		Recipes.compressor.addRecipe(new RecipeInputItemStack(halfHopperSingle), null, quarterHopper);
		
		//Hoppers extracting up
		Recipes.extractor.addRecipe(new RecipeInputItemStack(quarterHopper), null, halfHopperSingle);
		Recipes.extractor.addRecipe(new RecipeInputItemStack(halfHopper), null, discreteHopper);
		
		//Storage Racks + Upgrades
		ItemStack woodStorageUnit = new ItemStack(discreteTransportItem, 1, 0);
		ItemStack stoneStorageUnit = new ItemStack(discreteTransportItem, 1, 2);
		ItemStack ironStorageUnit = new ItemStack(discreteTransportItem, 1, 4);
		ItemStack redstoneStorageUnit = new ItemStack(discreteTransportItem, 1, 6);
		ItemStack obsidianStorageUnit = new ItemStack(discreteTransportItem, 1, 8);
		ItemStack goldStorageUnit = new ItemStack(discreteTransportItem, 1, 10);
		ItemStack diamondStorageUnit = new ItemStack(discreteTransportItem, 1, 12);
		ItemStack emeraldStorageUnit = new ItemStack(discreteTransportItem, 1, 14);
		ItemStack iridiumStorageUnit = new ItemStack(discreteTransportItem, 1, 16);
		
		ItemStack singleStorageRack = new ItemStack(discreteTransportBlock, 1, 100);
		ItemStack dualStorageRack = new ItemStack(discreteTransportBlock, 1, 101);
		ItemStack quadStorageRack = new ItemStack(discreteTransportBlock, 1, 102);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(singleStorageRack, new Object[]{ "XXX", "XYX", "XXX", 'Y', "itemRubber", 'X', "plankWood"}));
		//GameRegistry.addRecipe(new ShapedOreRecipe(frameDiscreteCable32, new Object[]{ "X X", " Y ", "X X", 'Y', Item.ingotGold, 'X', "itemRubber"}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(dualStorageRack, new Object[]{singleStorageRack, "plateIron"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(quadStorageRack, new Object[]{dualStorageRack, "plateIron"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(quadStorageRack, new Object[]{singleStorageRack, "plateIron", "plateIron"}));
		
		//base recipies
		GameRegistry.addRecipe(new ShapedOreRecipe(woodStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', "plankWood", 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(stoneStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Block.stone, 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ironStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Item.ingotIron, 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redstoneStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Item.redstone, 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(obsidianStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Block.obsidian, 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(goldStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', "ingotGold", 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(diamondStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', "gemDiamond", 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(emeraldStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Item.emerald, 'Z', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(iridiumStorageUnit, new Object[]{ "XYX", "X X", "ZZZ", 'X', Block.stoneSingleSlab, 'Y', Items.getItem("iridiumOre"), 'Z', "itemRubber"}));

		//these are all upgrade recipies and will not show in NEI
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(woodStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "plankWood", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(stoneStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "smoothStone", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(ironStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "ingotIron", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(redstoneStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "blockRedstone", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(obsidianStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "blockObsidian", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(goldStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "ingotGold", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(diamondStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "gemDiamond", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(emeraldStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "gemEmerald", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
		CraftingManager.getInstance().getRecipeList().add(new RecipeUpgradeStorageUnit(iridiumStorageUnit, 4, new Object[]{Block.stoneSingleSlab, "ingotIridium", Block.stoneSingleSlab, Block.stoneSingleSlab, "", Block.stoneSingleSlab, "itemRubber", "itemRubber", "itemRubber"} ));
	}
}
