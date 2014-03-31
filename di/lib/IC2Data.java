package kappafox.di.lib;

public class IC2Data
{
	

	public final double[] cableLoss;
	public final int[] cableMeltEnergy;
	public final int[] cableInsulationMeltEnergy;
	public final int[] cableInsulationAbsorbEnergy;
	public final String[] cableName;
	
	public IC2Data( )
	{
		//the cabletype values here do not correspond to those in IC2
		cableLoss = new double[6];
		cableLoss[0] = Double.MAX_VALUE;		//no cable
		cableLoss[1] = 0.025D;					//tin
		cableLoss[2] = 0.2D;					//Ins copper
		cableLoss[3] = 0.4D;					//2xIns gold
		cableLoss[4] = 0.025D;					//glass
		cableLoss[5] = 0.8D;					//4xIns HV
		
		
		cableMeltEnergy = new int[6];
		cableMeltEnergy[0] = Integer.MAX_VALUE;
		cableMeltEnergy[1] = 6;
		cableMeltEnergy[2] = 33;
		cableMeltEnergy[3] = 129;
		cableMeltEnergy[4] = 513;
		cableMeltEnergy[5] = 2049;
		
		cableInsulationMeltEnergy = new int[6];
		
		for(int i = 0; i < 6; i++)
		{
			cableInsulationMeltEnergy[i] = 9001;
		}
		
		cableInsulationAbsorbEnergy = new int[6];
		cableInsulationAbsorbEnergy[0] = Integer.MAX_VALUE;
		cableInsulationAbsorbEnergy[1] = 3;
		cableInsulationAbsorbEnergy[2] = 32;
		cableInsulationAbsorbEnergy[3] = 128;
		cableInsulationAbsorbEnergy[4] = 9001;
		cableInsulationAbsorbEnergy[5] = 9001;
		
		cableName = new String[6];
		cableName[0] = "Block";
		cableName[1] = "Ultra-Low-Current Cable";
		cableName[2] = "Copper Cable";
		cableName[3] = "2xIns. Gold Cable";
		cableName[4] = "Glass Fibre Cable";
		cableName[5] = "4xIns. HV Cable";
		
		
	}
}




