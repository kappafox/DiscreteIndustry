package kappafox.di.base.network;

import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketDiscreteSync extends Packet250CustomPayload
{
	public short module = 0;
	public short type = 0;
	
}
