package kappafox.di.base.compat;

import java.util.List;

import kappafox.di.transport.DiscreteTransport;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class DiscreteWailaProvider implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if(accessor.getTileEntity() instanceof TileEntityStorageRack)
		{
			TileEntityStorageRack tile = (TileEntityStorageRack)accessor.getTileEntity();
			
			for(int i = 0; i < tile.getSize(); i++)
			{
				String result = "";
				
				ItemStack content = tile.getContainerContent(i);
				
				if(content != null)
				{
					result += content.getDisplayName();
				}
				else
				{
					result += "Empty";
				}
				
				currenttip.add(result);
				currenttip.add(" " + tile.getContainerContentCount(i) + "/" + tile.getMax(i) + " Units");
				
			}
		}
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}
	
	public static void callbackRegister(IWailaRegistrar registrar)
	{
		registrar.addConfig("WailaDemo", "wailademo.showbody", "Show Body");
		//registrar.registerHeadProvider(new DiscreteWailaProvider(), BlockDiscreteTransport.class);
		registrar.registerBodyProvider(new DiscreteWailaProvider(), BlockDiscreteTransport.class);
	}

}
