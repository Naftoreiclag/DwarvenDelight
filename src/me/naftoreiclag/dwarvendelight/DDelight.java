package me.naftoreiclag.dwarvendelight;

import java.util.logging.Logger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class DDelight extends JavaPlugin
{
	//define logger
	Logger ConsoleLog;
	
    public void onEnable()
    {
    	//get the logger
    	ConsoleLog = this.getLogger();
    	
    	//log something
    	ConsoleLog.info("Dwarven Delight 0.1 enabled.");
    }
    
    public void onDisable()
    {
    	//log something
    	ConsoleLog.info("Dwarven Delight 0.1 disabled.");
    }
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
    {
        return new DDelightChunkGenerator();
    }
}
