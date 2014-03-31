





package kappafox.di.electrics.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.electrics.tileentities.TileEntityDiscreteCable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteCable extends BlockDiscreteBlock
{
	//private Icon faceTexture;
	
	// 0 = no cable
	// 1 = tin
	// 2 = copper
	// 3 = gold
	// 4 = glass
	// 5 = HV
	


	public BlockDiscreteCable(int id_, Material mat_, int renderID_)
	{
		super(id_, mat_, renderID_);
		this.setUnlocalizedName("discreteCable");
		this.setResistance(150);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ireg_)
	{	
		Icon defaultIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		this.blockIcon = defaultIcon;
		
		icons[0] = defaultIcon;
		icons[1] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableTin");
		icons[2] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableCopper");
		icons[3] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableGold");
		icons[4] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableGlass");
		icons[5] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableIron");
	
		for(int i = 0; i < 6; i++)
		{
			textures[i] = defaultIcon;
		}
		

		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id_, CreativeTabs tabs_, List list_)
	{
		// block id, count, meta	
		
		for(int i = 0; i < 6; i++)
		{
			list_.add(new ItemStack(id_, 1, i));
		}		
	}
	   
    @Override
    public TileEntity createTileEntity(World world_, int meta_)
    {
    	
        if(this.isTileProvider == true)
        {
        	TileEntityDiscreteCable tile = new TileEntityDiscreteCable(meta_);
        	tile.setMeta(meta_);
            return tile;
        }
        return null;
    }

}
