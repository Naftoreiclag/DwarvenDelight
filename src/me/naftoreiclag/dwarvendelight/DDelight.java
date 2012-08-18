package me.naftoreiclag.dwarvendelight;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class DDelight extends JavaPlugin
{
	//define logger
	private Logger consoleLog;
	private DDelightChunkGenerator chunkGenerator;
	
    public void onEnable()
    {
    	//get the logger
    	consoleLog = this.getLogger();
    	
    	//log something
    	consoleLog.info("Dwarven Delight enabled.");
    }
    
	private ArrayList<BlockPopulator> loadPopulators()
	{
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		populators.add(new PopulatorTallgrass());
		populators.add(new PopulatorSurfaceShrub());
		populators.add(new PopulatorSurfaceRocks());
		populators.add(new PopulatorSilverfishNest());
		populators.add(new PopulatorAirPockets());
		populators.add(new PopulatorOreCoal());
		populators.add(new PopulatorOreIron());
		populators.add(new PopulatorOreGold());
		populators.add(new PopulatorBedrockNoise());
		populators.add(new PopulatorBedrockLava());
		
		return populators;
	}
    
    public void onDisable()
    {
    	//log something
    	consoleLog.info("Dwarven Delight disabled.");
    }
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
    {
		//if there isn't already a generator, make one.
		if(chunkGenerator == null)
		{
			chunkGenerator = new DDelightChunkGenerator(loadPopulators());
		}
		
		//return it
        return chunkGenerator;
    }
}
