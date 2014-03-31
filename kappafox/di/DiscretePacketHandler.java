package kappafox.di;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class DiscretePacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager_, Packet250CustomPayload packet_, Player player_) 
	{

	      
		//"DD_FLAG_SYNC"
		if(packet_.channel.equals("DD_FLAG_SYNC"))
		{
			this.handleLoomSync(manager_, packet_, player_);
		}
		//System.out.println("Got Custom Packet!");
	}
	
	private void handleLoomSync(INetworkManager manager_, Packet250CustomPayload packet_, Player player_)
	{
	    DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet_.data));
		byte type = 15;
		int colour = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		int d = 0;
		
		try
		{
			type = inputStream.readByte();
			colour = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			d = inputStream.readInt();
			
		}
		catch(Exception e_)
		{
			System.out.println(e_);
		}
		
		//System.out.println(type + "/" + colour + "/(" + x + "," + y + "," + z + ")" + "D:" + d);
		//Primary colour updated
		if(type == 0)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{
				World w = MinecraftServer.getServer().worldServerForDimension(d);
				
				TileEntityLoomBlock tile = (TileEntityLoomBlock)w.getBlockTileEntity(x, y, z);
				tile.setPrimaryPaintColour(colour);
				w.markBlockForUpdate(x, y, z);
			}
		}		
	}

}
