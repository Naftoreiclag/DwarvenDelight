package me.naftoreiclag.dwarvendelight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
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
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biomeGrid) 
    {
    	//seeds are cool
		seed = new Random(world.getSeed());
		
		//define new chunk
		byte[][] chunkResult = new byte[16][];
		
		//Simplex's amazing octave generator (Magic bullet!)
		gen =  new SimplexOctaveGenerator(seed, 8);
		gen.setScale(1 / 64.0);
		
	    //base stone
	    addBaseStone(chunkResult);
	    
	    //add hills
	    addSurfaceHills(chunkResult, chunkX, chunkZ, 250);
	    
	    //No dwarfs in the void please. (add bedrock)
	    addBedrockBottom(chunkResult);
	    
	    //add surface grass
	    addSurfaceDecor(chunkResult, 245);
	    
	    //add stater house
	    addStarterHouse(chunkResult, chunkX, chunkZ, 250);
	    
	    //remove animals
    	for (int x = 0; x < 16; x++)
    	{
			for (int z = 0; z < 16; z++)
			{
				biomeGrid.setBiome(x, z, Biome.DESERT);
			}
    	}
	    
	    //return new chunk
	    return chunkResult;
    }
    
    //copied from javadocs
    private void setBlockAt(byte[][] result, int x, int y, int z, byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
    private byte getBlockAt(byte[][] result, int x, int y, int z) {
        if (result[y >> 4] == null) {
            return (byte)0;
        }
        return result[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
    }
    
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
    
    //add base stone
    private void addBaseStone(byte[][] result)
    {
    	//stone stuff
    	for(int x=0; x<16; x++)
	    {
	    	for(int y=1; y<250; y++)
		    {
			    for(int z=0; z<16; z++)
			    {
			    	//new
			    	setBlockAt(result, x, y, z, (byte) Material.STONE.getId());
			    }
		    }
	    }
    }
    
	//add starter house
    private void addStarterHouse(byte[][] result, int chunkX, int chunkZ, int altitude)
    {
    	if(chunkX == 0 && chunkZ == 0)
    	{
    		//define y
	    	int y = altitude;
	    	
	    	//find point to start building at
	    	for(int i = altitude; i <= 250; i ++)
	    	{
	    		if(getBlockAt(result, 3, i, 3) == (byte) Material.AIR.getId())
	    		{
	    			y = i - 1;
	    			break;
	    		}
	    	}
	    	
	    	//material of the house
	    	byte houseMat = (byte) Material.WOOD.getId();
	    	
	    	//wooden floor
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	setBlockAt(result, x, y, z, houseMat);
			    }
		    }
		    
		    //wall 1
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	setBlockAt(result, x, y, z, houseMat);
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	setBlockAt(result, x, y, z, (byte) Material.AIR.getId());
			    }
		    }
		    //door 1
		    setBlockAt(result, 3, y, 6, (byte) Material.AIR.getId());
		    
		    //wall 2
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	setBlockAt(result, x, y, z, houseMat);
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	setBlockAt(result, x, y, z, (byte) Material.AIR.getId());
			    }
		    }
		    //door 2
		    setBlockAt(result, 3,y,6, (byte) Material.AIR.getId());
		    
		    //windows
		    setBlockAt(result, 5,y,3, (byte) Material.AIR.getId());
		    setBlockAt(result, 7,y,3, (byte) Material.AIR.getId());
		    setBlockAt(result, 9,y,5, (byte) Material.AIR.getId());
		    setBlockAt(result, 9,y,7, (byte) Material.AIR.getId());
		    setBlockAt(result, 7,y,9, (byte) Material.AIR.getId());
		    setBlockAt(result, 5,y,9, (byte) Material.AIR.getId());
		    
		    //wall 3
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	setBlockAt(result, x, y, z, houseMat);
			    }
		    }
		    for(int x=4; x<9; x++)
		    {
			    for(int z=4; z<9; z++)
			    {
			    	setBlockAt(result, x, y, z, (byte) Material.AIR.getId());
			    }
		    }
		    
		    //ceiling
		    y ++;
		    for(int x=3; x<10; x++)
		    {
			    for(int z=3; z<10; z++)
			    {
			    	setBlockAt(result, x, y, z, houseMat);
			    }
		    }
    	}
    }
    
    //add bedrock to the bottom
    private void addBedrockBottom(byte[][] result)
    {
    	//always at 0
    	int y = 0;
    	
    	//256 blocks
	    for(int x=0; x<16; x++)
	    {
		    for(int z=0; z<16; z++)
		    {
		    	setBlockAt(result, x, y, z, (byte) Material.BEDROCK.getId());
		    }
	    }
    }
    
    //add surface decorations
    private void addSurfaceDecor(byte[][] result, int altitude)
    {
    	//dirt-ify
	    for(int x=0; x<16; x++)
	    {
		    for(int z=0; z<16; z++)
		    {
		    	for(int y = altitude - 1; y < 256; y ++)
                {
		    		if(getBlockAt(result, x, y, z) == (byte) Material.STONE.getId())
		    		{
		    			setBlockAt(result, x, y, z, (byte) Material.DIRT.getId());
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
		    	for(int y = 255; y > altitude - 2; y --)
                {
		    		if(getBlockAt(result, x, y, z) == (byte) Material.DIRT.getId() && !potato)
		    		{
		    			setBlockAt(result, x, y, z, (byte) Material.GRASS.getId());
		    			potato = true;
		    		}
                }
		    }
	    }
    }
    
    //code is a modified version of codename_B's example code
    private void addSurfaceHills(byte[][] result, int chunkX, int chunkZ, int altitude)
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
                	setBlockAt(result, x, y, z, (byte) Material.STONE.getId());
                }
            }
        }
    }
}
