package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySubtype extends TileEntity
{
	protected int subtype;
	
	public TileEntitySubtype( )
	{
		this(0);
	}
	
	public TileEntitySubtype(int type)
	{
		subtype = type;
	}
	
	
	public int getSubtype( )
	{
		return subtype;
	}
	
	public void setSubtype(int type)
	{
		subtype = type;
	}
	
	
	
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{	
		super.writeToNBT(nbt);
		
		nbt.setInteger("subtype", getSubtype());
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		setSubtype(nbt.getInteger("subtype"));
		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	//do not call super() for these methods, let writeToNBT & readFromNBT handle it
	
	@Override
    public Packet getDescriptionPacket( )
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
    	NBTTagCompound tag = packet.data;
    	this.readFromNBT(tag);
    }
}
