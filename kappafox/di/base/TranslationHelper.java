package kappafox.di.base;

import net.minecraftforge.common.ForgeDirection;
import kappafox.di.base.util.PointSet;

public class TranslationHelper
{
	
	
	public PointSet translate(ForgeDirection from, ForgeDirection to, PointSet points)
	{
		switch(from)
		{
			case NORTH:
			{
				return translateNorthTo(to, points);
			}
			
			default:
			{
				return points;
			}
		}
	}
	
	private PointSet translateNorthTo(ForgeDirection to, PointSet points)
	{
		switch(to)
		{
			
			case UNKNOWN:
			{
				break;
			}
			
			case UP:
			{
				break;
			}
			
			case DOWN:
			{
				break;
			}
			
			case NORTH:
			{
				break;
			}
			

			
			case SOUTH:
			{
				float myz1 = points.z1;
				
				points.z1 = 1 - points.z2;
				points.z2 = 1 - myz1;
				break;
			}
			
			case EAST:
			{
				float myx1 = points.x1;
				float myx2 = points.x2;
				
				points.x1 = 1 - points.z2;
				points.x2 = 1 - points.z1;
				points.z1 = myx1;
				points.z2 = myx2;
				break;
			}
			
			case WEST:
			{
				float myx1 = points.x1;
				float myx2 = points.x2;
				
				points.x1 = points.z1;
				points.x2 = points.z2;
				points.z1 = myx1;
				points.z2 = myx2;
				break;
			}
		}
		
		return points;
	}
}
