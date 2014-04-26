package kappafox.di.transport;

import ic2.api.item.Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.lib.DiscreteID;
import kappafox.di.transport.blocks.BlockDiscreteHopper;
import kappafox.di.transport.blocks.items.ItemDiscreteHopperBlock;
import kappafox.di.transport.renderers.DiscreteTransportRenderManager;
import kappafox.di.transport.tileentities.TileEntityDiscreteDuct;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class DiscreteTransport
{
		
	//blocks
	//public static Block discreteCableBlock;
	public static Block discreteHopperBlock;
	public static Block discreteTransportBlock;
	
	
	//ids
	//private int discreteCableID;
	private int discreteHopperID;
	private int discreteTransportMetaBlockID;

	
	//Render IDS
	private int renderID;
	
	//Model IDS
	public static int hopperRenderID;
	public static int transportBlockRenderID;
	
	//public static int discreteCableRenderID;
	
	
	public DiscreteTransport( )
	{
		
	}
	
	public void preInitialisation(FMLPreInitializationEvent event, Configuration config)
	{

		//grab the id database
		DiscreteID ids = DiscreteIndustry.librarian.dibi;
		
		discreteHopperID = config.getBlock("DiscreteHoppers", ids.discreteHopper).getInt(ids.discreteHopper);
		discreteTransportMetaBlockID = config.getBlock("DiscreteTransportBlock", ids.discreteTransportMetaBlock).getInt(ids.discreteTransportMetaBlock);
	}
	
	public void load(FMLInitializationEvent event)
	{
		//Renderers
		registerRenderers();
		
		//Blocks
		registerBlocks();
		
		//Recipies
		registerRecipies();
		

		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityDiscreteHopper.class, "Discrete Hopper");
		GameRegistry.registerTileEntity(TileEntityDiscreteDuct.class, "Discrete Duct");
	}



	
	private void registerBlocks( )
	{
		
		discreteHopperBlock = new BlockDiscreteHopper(discreteHopperID, Material.rock, hopperRenderID);
		//discreteTransportBlock = new BlockDiscreteTransport(discreteTransportMetaBlockID, Material.wood);
		
		GameRegistry.registerBlock(discreteHopperBlock, ItemDiscreteHopperBlock.class, DiscreteIndustry.MODID + "hopperBlock");
		
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 0), "Discrete Hopper");
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 1), "Discrete Hopper Half");
		LanguageRegistry.addName(new ItemStack(discreteHopperBlock, 1, 2), "Discrete Hopper Quarter");
	}
	
	private void registerRenderers( )
	{

		renderID = RenderingRegistry.getNextAvailableRenderId();
		hopperRenderID = RenderingRegistry.getNextAvailableRenderId();
		transportBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		DiscreteTransportRenderManager manager = new DiscreteTransportRenderManager(renderID);
		RenderingRegistry.registerBlockHandler(hopperRenderID, manager);
		RenderingRegistry.registerBlockHandler(transportBlockRenderID, manager);

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
	}
}
