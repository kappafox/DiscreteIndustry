package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityDiscreteBlock extends TileEntitySubtype
{

	

	private int facadeBlockID = 810;					//the current block we look like
	private int facadeMeta = 0;						//the damage value of the meta based facade
	private short facing;
	private int colour = 16777215;
	private int var1 = 0;
	//private int subType = 0;
	private boolean fullColour = false;
	private int textureOrienation = 0;
	private short direction = 0;
	
	private int[] blockIDs = new int[6];
	private int[] blockMetas = new int[6];
	private int[] blockSides = new int[6];
	
	private int originalID = 0;
	private int originalMeta = 0;
	
	public TileEntityDiscreteBlock( )
	{
		this(0, 0);
	}
	
	
	public TileEntityDiscreteBlock(int id, int meta)
	{
		for(int i = 0; i < 6; i++)
		{
			blockIDs[i] = id;
			blockMetas[i] = meta;
			blockSides[i] = i;
		}
		
		setSubtype(0);
	}
	
	public TileEntityDiscreteBlock(int id, int meta, int sub)
	{
		this(id, meta);
		setSubtype(sub);
	}
	

	
	public void setAllTextureSources(int id_, int meta_, int side_)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(id_, meta_, side_, i);
		}
	}
	
	public void setAllTexturesFromSource(int id_, int meta_)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(id_, meta_, i, i);
		}
	}
		
	//target ID, target Meta, target Side, Side actually clicked of this block
	public void setTextureSource(int id_, int meta_, int side_, int hitside_)
	{
		if(side_ >= 0 || side_ < blockIDs.length)
		{
			blockIDs[hitside_] = id_;
			blockMetas[hitside_] = meta_;
			blockSides[hitside_] = side_;
		}
	}
	
	public int getTextureSource(int side_)
	{
		if(side_ < 0 || side_ >= blockIDs.length)
		{
			return 0;
		}

		return blockIDs[side_];


	}

	
	public void setTextureSourceMeta(int meta_, int side_)
	{
		blockMetas[side_] = meta_;
	}
	
	public int getTextureSourceMeta(int side_)
	{
		if(side_ < 0 || side_ >= blockMetas.length)
		{
			return 0;
		}
		

		return blockMetas[side_];	


		//return facadeMeta;		
	}
	
	//side of this block to get from
	public int getTextureSourceSide(int side_)
	{
		if(side_ < 0 || side_ >= blockSides.length)
		{
			return 0;
		}
		
		return blockSides[side_];
	}

	public int getBlockColor( )
	{
		return colour;
	}
	
	public void setBlockColor(int col_)
	{
		colour = col_;
	}

	
	
	private boolean changeFace = true;


	public short getFace( )
	{
		return facing;
	}

	public void setFace(short facing_)
	{		
		
		//System.out.println("setFacing:" + facing_);
		if(changeFace == true )
		{
			facing = facing_;	
		}
		else
		{
			changeFace = true;
		}
			
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getVariable( )
	{
		return var1;
	}
	
	public void setVariable(int var_)
	{
		var1 = var_;
	}
	
	public int getTextureOrientation( )
	{
		return textureOrienation;
	}
	
	public void setTextureOrientation(int orient_)
	{
		textureOrienation = orient_;
	}
	
	public short getDirection( )
	{
		return direction;
	}
	
	public void setDirection(short dir_)
	{
		direction = dir_;
	}
	
	public void setOriginalID(int id_)
	{
		originalID = id_;
	}
	
	public int getOriginalID( )
	{
		return originalID;
	}
	
	public int getOriginalMeta( )
	{
		return originalMeta;
	}
	

		
	public void writeToNBT(NBTTagCompound nbt_)
	{
		
		super.writeToNBT(nbt_);
		
		nbt_.setInteger("facadeBlockID", facadeBlockID);
		nbt_.setInteger("facadeMeta", facadeMeta);
		nbt_.setShort("facing", facing);
		nbt_.setInteger("colour", colour);
		nbt_.setInteger("var1", var1);
		//nbt_.setInteger("subType", subType);
		nbt_.setInteger("torient", textureOrienation);
		nbt_.setShort("direction", direction);
		
		nbt_.setIntArray("blockIDs", blockIDs);
		nbt_.setIntArray("blockMetas", blockMetas);
		nbt_.setIntArray("blockSides", blockSides);
		
		nbt_.setBoolean("fullColour", fullColour);
		
		nbt_.setInteger("originalID", originalID);
		nbt_.setInteger("originalMeta", originalMeta);
		
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
		
		facadeBlockID = nbt_.getInteger("facadeBlockID");
		facadeMeta = nbt_.getInteger("facadeMeta");
		facing = nbt_.getShort("facing");
		colour = nbt_.getInteger("colour");
		var1 = nbt_.getInteger("var1");
		//subType = nbt_.getInteger("subType");
		textureOrienation = nbt_.getInteger("torient");
		
		blockIDs = nbt_.getIntArray("blockIDs");
		blockMetas = nbt_.getIntArray("blockMetas");
		blockSides = nbt_.getIntArray("blockSides");
		
		fullColour = nbt_.getBoolean("fullColour");
		direction = nbt_.getShort("direction");
		
		originalID = nbt_.getInteger("originalID");
		originalMeta = nbt_.getInteger("originalMeta");
		
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
	
	
	
	public boolean isFullColour( )
	{
		return fullColour;
	}
	
	public void setFullColour(boolean b_)
	{
		fullColour = b_;
	}
	




	
	protected int directionToSide(ForgeDirection dir_)
	{
		
		if(dir_ == ForgeDirection.DOWN)
		{
			return 0;
		}
		
		if(dir_ == ForgeDirection.UP)
		{
			return 1;
		}
		
		if(dir_ == ForgeDirection.NORTH)
		{
			return 2;
		}
		
		if(dir_ == ForgeDirection.SOUTH)
		{
			return 3;
		}
		
		if(dir_ == ForgeDirection.WEST)
		{
			return 4;
		}
		
		if(dir_ == ForgeDirection.EAST)
		{
			return 5;
		}
		

		
		return 0;
	}

}
