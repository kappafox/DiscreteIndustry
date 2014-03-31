package kappafox.di.decorative.tileentities;

import ic2.api.tile.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityHazardBlock extends TileEntity implements IWrenchable
{
	
	private int facing = 0;
	
	public TileEntityHazardBlock( )
	{
		
	}
	
	public void writeToNBT(NBTTagCompound nbt_)
	{
		
		super.writeToNBT(nbt_);
		
		nbt_.setInteger("facing", facing);

		
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);

		facing = nbt_.getInteger("facing");

		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}

	
	@Override
	public short getFacing( )
	{
		return (short)facing;
	}
	
	public void setFace(int face_)
	{
		//System.out.println("Facing:" + face_);
		facing = face_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			//setting true to play the sound
		}
	}
	
	@Override
	//@SideOnly(Side.SERVER)
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
	//@SideOnly(Side.CLIENT)
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
		//System.out.println("got packet!");
    	NBTTagCompound tag = packet.data;
    	readFromNBT(tag);
    }

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer ply_, int side_) 
	{
		if(ply_.isSneaking() == true)
		{
			return true;
		}
		return false;
	}	


	@Override
	public void setFacing(short facing) 
	{
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if(meta == 0 || meta == 2)
		{
			System.out.println("2");
			this.setFace(1 - this.getFacing());
		}
		
		if(meta == 1 || meta == 3)
		{			
			if(this.getFacing() == 3)
			{
				this.setFace(0);
			}
			else
			{
				this.setFace(this.getFacing() + 1);
			}
		}
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) 
	{
		return false;
	}

	@Override
	public float getWrenchDropRate() 
	{
		return 0;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) 
	{
		return null;
	}
	

}
