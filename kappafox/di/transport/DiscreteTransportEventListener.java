package kappafox.di.transport;

import ic2.api.event.RetextureEvent;

import java.text.DecimalFormat;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;




public class DiscreteTransportEventListener 
{
	@ForgeSubscribe
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			//System.out.println(event.action);
			this.StorageRackOnClick(event);
		}
	}
	
	
	private void StorageRackOnClick(PlayerInteractEvent event)
	{
		//this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d0, par1);
		EntityPlayer player = event.entityPlayer;
		
		MovingObjectPosition clickedOn = this.getMovingObjectPositionFromPlayer(player.worldObj, player, true);
		
		if(clickedOn != null && clickedOn.hitVec != null)
		{
			//System.out.println((clickedOn.hitVec.xCoord - event.x) + " " + (clickedOn.hitVec.yCoord - event.y) + " " + (clickedOn.hitVec.zCoord - event.z));
			switch(event.action)
			{
				case RIGHT_CLICK_BLOCK:
				{
					break;
				}
				
				case LEFT_CLICK_BLOCK:
				{
					Block target = Block.blocksList[player.worldObj.getBlockId(event.x, event.y, event.z)];
					
					if(target != null && target instanceof BlockDiscreteTransport)
					{
						BlockDiscreteTransport bdt = (BlockDiscreteTransport)target;
						
						float hitx = (float)clickedOn.hitVec.xCoord - event.x;
						float hity = (float)clickedOn.hitVec.yCoord - event.y;
						float hitz = (float)clickedOn.hitVec.zCoord - event.z;
									
						bdt.onBlockClicked(player.worldObj, event.x, event.y, event.z, player, event.face, hitx, hity, hitz);
					}
					break;
				}
				
				default:
				{
					
				}
			}
		}
	}
	
	//from Item
    private MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float f = 1.0F;
        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + (double)(par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (par2EntityPlayer instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return par1World.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
    }
}


