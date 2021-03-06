package kappafox.di;


import kappafox.di.base.lib.DiscreteID;
import kappafox.di.base.lib.IC2Data;
import kappafox.di.base.lib.Library;
import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.base.tileentities.TileEntitySingleVariable;
import kappafox.di.decorative.DiscreteDecorative;
import kappafox.di.electrics.DiscreteElectrics;
import kappafox.di.transport.DiscreteTransport;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;





@Mod(modid = DiscreteIndustry.MODID, name = DiscreteIndustry.NAME, version = DiscreteIndustry.VERSION, dependencies = DiscreteIndustry.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"DD_FLAG_SYNC", "DI_GENERIC_SYNC"}, packetHandler = DiscretePacketHandler.class)

public class DiscreteIndustry
{
	
	@Instance("DiscreteIndustry")
	public static DiscreteIndustry instance;
	
	//Globals
	public static final String MODID = "DiscreteIndustry";
	public static final String NAME = "Discrete Industry";
	public static final String VERSION = "1.0.1g";
	public static final String DEPENDENCIES = "required-after:IC2";
	
	
	//Helper classes
	public static final Library librarian = new Library();
	
	
	public static Class<?> GTWrench = null;
	public static Class<?> BCWrench = null;
	

	
	//Public references
	//public static int renderID;
	
	//Items
	
	
	//Modules
	private DiscreteElectrics electrics = new DiscreteElectrics();
	private DiscreteTransport transport = new DiscreteTransport();
	private DiscreteDecorative decorative = new DiscreteDecorative();
	
	
	@EventHandler
	public void preInitialisation(FMLPreInitializationEvent event_)
	{
		//grab the id database
		librarian.preInit();
		//DiscreteID ids = DiscreteIndustry.librarian.dibi;
		
		
		
		Configuration config = new Configuration(event_.getSuggestedConfigurationFile());	//grab the config file or create it
		config.load();
	
		Packet.addIdClassMapping(88, true, true, PacketDiscreteSync.class);
		

		electrics.preInitialisation(event_, config);
		transport.preInitialisation(event_, config);
		decorative.preInitialisation(event_, config);
		
		config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event_ )
	{
		
		librarian.init();
		registerBlocks();

		
		electrics.load(event_);
		transport.load(event_);
		decorative.load(event_);
		
		//Gui Stuff
		NetworkRegistry.instance().registerGuiHandler(this, new DiscreteGuiHandler());
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntitySingleVariable.class, "SVTE");
		
		FMLInterModComms.sendMessage("Waila", "register", "kappafox.di.base.compat.DiscreteWailaProvider.callbackRegister");
		
	}
	
	private void registerBlocks( ){}
	
	@EventHandler
	public void postInitialisation(FMLPostInitializationEvent event_)
	{
		librarian.postInit();
		this.reflection();
	}
	
	private void reflection( )
	{
		try
		{
			GTWrench = Class.forName("gregtechmod.api.items.GT_WrenchIC_Item");
		}
		catch(Exception e_)
		{
			
		}
		
		try
		{
			BCWrench = Class.forName("buildcraft.api.tools.IToolWrench");
		}
		catch(Exception e_)
		{
			
		}
	}
	
	

}
