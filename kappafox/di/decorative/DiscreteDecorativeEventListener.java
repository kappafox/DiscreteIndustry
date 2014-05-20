package kappafox.di.decorative;

import ic2.api.event.RetextureEvent;

import java.text.DecimalFormat;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;




public class DiscreteDecorativeEventListener 
{
	@ForgeSubscribe
	public void onRetextureEvent(RetextureEvent e_)
	{
		World world = e_.world;
		TileEntity tile = world.getBlockTileEntity(e_.x, e_.y, e_.z);

		if(tile != null && tile instanceof TileEntityDiscreteBlock)
		{
			TileEntityDiscreteBlock disc = (TileEntityDiscreteBlock)tile;
			int id = world.getBlockId(e_.x, e_.y, e_.z);
			int meta = world.getBlockMetadata(e_.x, e_.y, e_.z);
			
			
			//Ladders, Fixtures/Struts, Weapon Racks, anything that uses a single texture for all sides
			if(disc.isFullColour() == true)
			{
				disc.setAllTextureSources(e_.referencedBlockId, e_.referencedMeta, e_.referencedSide);
			}
			else
			{
				disc.setTextureSource(e_.referencedBlockId, e_.referencedMeta, e_.referencedSide, e_.side);
			}
			world.markBlockForUpdate(e_.x, e_.y, e_.z);
			
		}
	}
	
	@ForgeSubscribe
	public void onEntityInteractEvent(EntityInteractEvent e_)
	{
		Entity h = e_.target;
		
		EntityPlayer p = e_.entityPlayer;
		
		if(p != null)
		{
			ItemStack e = p.getCurrentEquippedItem();
			
			if(e != null)
			{
				if((e.itemID - 256) == DiscreteElectrics.discreteElectricItemID)
				{
					if(h != null && h instanceof EntityHorse)
					{
						EntityHorse horse = (EntityHorse)h;

						AttributeInstance ai = horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
						AttributeInstance hp = horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
						
						double jumpRange = 0.6;
						double speedRange = 0.225;
						
						double j = ((horse.getHorseJumpStrength() - 0.4) / jumpRange) * 100;
						double s = ((ai.getAttributeValue() - 0.1125) / speedRange) * 100;
						
						//System.out.println("J:" + horse.getHorseJumpStrength());
						//System.out.println("S:" + ai.getAttributeValue());
						DecimalFormat df = new DecimalFormat("##.#");
						String jump = df.format(j) + "%";
						String speed = df.format(s) + "%";
						
						//double jump = Math.round(horse.getHorseJumpStrength() * 1000.0) / 1000.0;
						//double speed = Math.round(ai.getAttributeValue() * 1000.0) / 1000.0;
						
						
						if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
						{
							p.sendChatToPlayer(ChatMessageComponent.createFromText("Hearts:" + df.format(hp.getAttributeValue()) + "   Jump:" + jump + "   Speed:" + speed));

						}
						
						e_.setCanceled(true);
					}
				}
			}
		}
		

	}
}


