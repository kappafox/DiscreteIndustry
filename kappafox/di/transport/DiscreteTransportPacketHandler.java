package kappafox.di.transport;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;



public class DiscreteTransportPacketHandler implements IPacketHandler
{

	private static final short TYPE_BUTTON_HOPPER_EXTRACT = 0;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		PacketDiscreteSync pack = (PacketDiscreteSync)packet;
		
		switch(pack.type)
		{
			case TYPE_BUTTON_HOPPER_EXTRACT:
			{
				toggleHopperExtract(pack, player);
				break;
			}
		}
	}
	private void toggleHopperExtract(PacketDiscreteSync packet, Player player)
	{
	    DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		int x = 0;
		int y = 0;
		int z = 0;
		int d = 0;
		
		try
		{
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			d = inputStream.readInt();
			
		}
		catch(Exception e_)
		{
			System.out.println(e_);
		}

		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			World w = MinecraftServer.getServer().worldServerForDimension(d);
			
			TileEntityDiscreteHopper tile = (TileEntityDiscreteHopper)w.getBlockTileEntity(x, y, z);
			tile.toggleExtractFromAbove();
			w.markBlockForUpdate(x, y, z);
		}	
	}

}
