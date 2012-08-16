package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class DDelightChunkGenerator extends ChunkGenerator
{
	Random seed;
	
	SimplexOctaveGenerator gen;
	
    //Overrides the default chunk generation code.
    @Override
    public byte[] generate(World world, Random rand, int chunkX, int chunkZ)
    {
		seed = new Random(world.getSeed());
		gen =  new SimplexOctaveGenerator(seed, 8);
		gen.setScale(1/64.0);
		
    	//define new chunk
	    byte[] chunkResult = new byte[32768];

	    //stone
	    /*for(int x=0; x<16; x++)
	    {
	    	for(int y=1; y<122; y++)
		    {
			    for(int z=0; z<16; z++)
			    {
			    	chunkResult[pointToByte(x,y,z)] = (byte) Material.STONE.getId();
			    }
		    }
	    }*/
	    
	    //
	    chunkResult = testHills(chunkResult, chunkX, chunkZ);
	   
	    //No dwarfs in the void please.
	    chunkResult = bedrockBottom(chunkResult);
	    
	    //return new chunk
	    return chunkResult;
    }
    
    //Overrides the default spawning check.
    @Override
    public boolean canSpawn(World world, int x, int z)
    {
        return true;
    }
    
    //Overrides spawn location
    @Override
    public Location getFixedSpawnLocation(World world, Random random)
    {
    	//if that chunk is not loaded, load it
        if(!world.isChunkLoaded(0, 0))
        {
            world.loadChunk(0, 0);
        }
        
        //set spawn
        return new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
    }
    
    //This converts relative chunk locations to bytes that can be written to the chunk
    private int pointToByte(int x, int y, int z)
    {
    	return (x * 16 + z) * 128 + y;
    }
    
    //add bedrock to the bottom
    private byte[] bedrockBottom(byte[] returnValue)
    {
    	int y = 0;
	    for(int x=0; x<16; x++)
	    {
		    for(int z=0; z<16; z++)
		    {
		    	returnValue[pointToByte(x,y,z)] = (byte) Material.BEDROCK.getId();
		    }
	    }
    	
		return returnValue;
    }
    
    //test; code from codename_B (Won't be in final release)
    private byte[] testHills(byte[] returnValue, int chunkX, int chunkZ)
    {
    	for (int x=0; x<16; x++)
    	{
            for (int z=0; z<16; z++)
            {
                // This generates noise based on the absolute x and z (between -1 and 1) - it will be smooth noise if all goes well - multiply it by the desired value - 16 is good for hilly terrain.
                double noise = gen.noise(x+chunkX*16, z+chunkZ*16, 0.5, 0.5)*16;
                
                for(int y=0; y<32+noise; y++)
                {
                    // Obviously sets that byte[] corresponding to the chunk x,y,z to stone - you can use your preferred way of doing this.
                	returnValue[pointToByte(x,y,z)] = (byte)  (Material.STONE).getId();
                }
            }
        }
    	
		return returnValue;
    }
}
