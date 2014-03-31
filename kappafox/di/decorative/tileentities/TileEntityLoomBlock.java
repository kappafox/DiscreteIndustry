package kappafox.di.decorative.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityLoomBlock extends TileEntity
{
	private int primaryPaintColour = 0;
	private int secondaryPaintColour = 0;
	
	
	
	
	public TileEntityLoomBlock( )
	{
		
	}
	
	public int getPrimaryPaintColour( )
	{
		return primaryPaintColour;
	}
	
	public int getSecondaryPaintColour( )
	{
		return secondaryPaintColour;
	}
	
	public void setPrimaryPaintColour(int col_)
	{
		primaryPaintColour = col_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	public void setSecondaryPaintColour(int col_)
	{
		secondaryPaintColour = col_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt_)
	{		
		super.writeToNBT(nbt_);		
		nbt_.setInteger("primaryPaintColour", primaryPaintColour);
		nbt_.setInteger("secondaryPaintColour", secondaryPaintColour);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);

		primaryPaintColour = nbt_.getInteger("primaryPaintColour");
		secondaryPaintColour = nbt_.getInteger("secondaryPaintColour");

		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
    	NBTTagCompound tag = packet.data;
    	readFromNBT(tag);
    }
}
