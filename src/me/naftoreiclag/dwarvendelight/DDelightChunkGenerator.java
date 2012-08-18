package me.naftoreiclag.dwarvendelight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class DDelightChunkGenerator extends ChunkGenerator
{
	private Random seed;
	
	private SimplexOctaveGenerator gen;

	private ArrayList<BlockPopulator> populators;
	
	//constructor
	public DDelightChunkGenerator(ArrayList<BlockPopulator> a_populators)
	{
		this.populators = a_populators;
	}
	
	
    //the default chunk generation code.
    @Override
    public byte[] generate(World world, Random rand, int chunkX, int chunkZ)
    {
		seed = new Random(world.getSeed());
		gen =  new SimplexOctaveGenerator(seed, 8);
		gen.setScale(1/64.0);
		
    	//define new chunk
	    byte[] chunkResult = new byte[32768];

	    //base stone
	    for(int x=0; x<16; x++)
	    {
	    	for(int y=1; y<120; y++)
		    {
			    for(int z=0; z<16; z++)
			    {
			    	chunkResult[pointToByte(x,y,z)] = (byte) Material.STONE.getId();
			    }
		    }
	    }
	    
	    //add hills
	    chunkResult = addSurfaceHills(chunkResult, chunkX, chunkZ,120);
	    
	    //No dwarfs in the void please. (add bedrock)
	    chunkResult = addBedrockBottom(chunkResult);
	    
	    //add surface grass
	    chunkResult = addSurfaceDecor(chunkResult, 115);
	    
	    //add stater house
	    chunkResult = addStarterHouse(chunkResult, chunkX, chunkZ, 120);
	    
	    //return new chunk
	    return chunkResult;
    }
    
    
    //does not work *sadface*
    /*public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
    {
    	byte[][] returnValue = new byte[16][];
    	
    	//tonight dwarves, we dine in hell!
    	for (int x = 0; x < 16; x++)
    	{
			for (int z = 0; z < 16; z++)
			{
				biomeGrid.setBiome(x, z, Biome.HELL);
			}
    	}
    	
    	return returnValue;
    }*/
    
    //Overrides populators
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world)
	{
		return populators;
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
        return new Location(world, 6.5, world.getHighestBlockYAt(6, 6) - 4, 6.5);
    }
    
    //This converts relative chunk locations to bytes that can be written to the chunk
    private int pointToByte(int x, int y, int z)
    {
    	return (x * 16 + z) * 128 + y;
    }
    
	//add starter house
    private byte[] addStarterHouse(byte[] returnValue, int chunkX, int chunkZ, int altitude)
    {
    	if(chunkX == 0 && chunkZ == 0)
    	{
	    	int y = altitude;
	    	
	    	for(int i = altitude; i <= 123; i ++)
	    	{
	    		if(returnValue[pointToByte(3,i,3)] == (byte) Material.AIR.getId())
	    		{
	    			y = i - 1;
	    			break;
	    		}
	    	}
	    	
	    	byte houseMat = (byte) Material.WOOD.getId();
	    	
	    	//wooden floor
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = houseMat;
			    }
		    }
		    
		    //wall 1
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = houseMat;
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = (byte) Material.AIR.getId();
			    }
		    }
		    //door 1
		    returnValue[pointToByte(3,y,6)] = (byte) Material.AIR.getId();
		    
		    //wall 2
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = houseMat;
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = (byte) Material.AIR.getId();
			    }
		    }
		    //door 2
		    returnValue[pointToByte(3,y,6)] = (byte) Material.AIR.getId();
		    //windows
		    returnValue[pointToByte(5,y,3)] = (byte) Material.AIR.getId();
		    returnValue[pointToByte(7,y,3)] = (byte) Material.AIR.getId();
		    returnValue[pointToByte(9,y,5)] = (byte) Material.AIR.getId();
		    returnValue[pointToByte(9,y,7)] = (byte) Material.AIR.getId();
		    returnValue[pointToByte(7,y,9)] = (byte) Material.AIR.getId();
		    returnValue[pointToByte(5,y,9)] = (byte) Material.AIR.getId();
		    
		    //wall 3
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = houseMat;
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = (byte) Material.AIR.getId();
			    }
		    }
		    
		    //ceiling
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	returnValue[pointToByte(x,y,z)] = houseMat;
			    }
		    }
    	}
    	
		return returnValue;
    }
    
    //add bedrock to the bottom
    private byte[] addBedrockBottom(byte[] returnValue)
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
    
    //add bedrock to the bottom
    private byte[] addSurfaceDecor(byte[] returnValue, int altitude)
    {
    	//dirt-ify
	    for(int x=0; x<16; x++)
	    {
		    for(int z=0; z<16; z++)
		    {
		    	for(int y = altitude - 1; y < 127; y ++)
                {
		    		if(returnValue[pointToByte(x,y,z)] == (byte) Material.STONE.getId())
		    		{
		    			returnValue[pointToByte(x,y,z)] = (byte) Material.DIRT.getId();
		    		}
                }
		    }
	    }
    	
	    //grass-ify
	    boolean potato;
	    for(int x=0; x<16; x++)
	    {
		    for(int z=0; z<16; z++)
		    {
		    	potato = false;
		    	for(int y = 127; y > altitude - 2; y --)
                {
		    		if(returnValue[pointToByte(x,y,z)] == (byte) Material.DIRT.getId() && !potato)
		    		{
		    			returnValue[pointToByte(x,y,z)] = (byte) Material.GRASS.getId();
		    			potato = true;
		    		}
                }
		    }
	    }
	    
		return returnValue;
    }
    
    //code is a modified version of codename_B's example code
    private byte[] addSurfaceHills(byte[] returnValue, int chunkX, int chunkZ, int altitude)
    {
    	//get the changes
    	for (int x = 0; x < 16; x ++)
    	{
            for (int z = 0; z < 16; z ++)
            {
                // This generates noise based on the absolute x and z (between -1 and 1) - it will be smooth noise if all goes well - multiply it by the desired value - 16 is good for hilly terrain.
                double noise = gen.noise(x + (chunkX * 16), z + (chunkZ * 16), 0.5, 0.5) * 3;
                
                for(int y = altitude - 1; y < altitude + noise; y ++)
                {
                	//sets that block
                	returnValue[pointToByte(x,y,z)] = (byte) Material.STONE.getId();
                }
            }
        }
    	
    	//return the changes
		return returnValue;
    }
}
