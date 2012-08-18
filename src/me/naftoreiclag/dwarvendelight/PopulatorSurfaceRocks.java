package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class PopulatorSurfaceRocks extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk baseChunk)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				//get a random number between 0 and 100if (handle.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
				int chance = random.nextInt(200000);
				
				//target a block
				Block targetBlock = world.getHighestBlockAt(x + (baseChunk.getX() * 16), z + (baseChunk.getZ() * 16));
				
				if(targetBlock.getRelative(BlockFace.DOWN, 1).getType().equals(Material.GRASS))
				{
					//if it's greater than 180
					if (chance > 199980)
					{
						//set to mossy rock
						targetBlock.setTypeIdAndData(Material.MOSSY_COBBLESTONE.getId(), (byte) 0, true);
					}
					else if (chance < 10)
					{
						//set to cobblestone slab
						targetBlock.setTypeIdAndData(Material.STEP.getId(), (byte) 3, true);
					}
				}
			}
		}
	}
}
