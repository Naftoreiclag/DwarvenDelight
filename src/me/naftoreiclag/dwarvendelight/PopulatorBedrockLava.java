package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class PopulatorBedrockLava extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk baseChunk)
	{
		for (int x = 0; x < 16; x ++)
		{
			for (int z = 0; z < 16; z ++)
			{
				for (int y = 1; y < 16; y ++)
				{
					Block targetBlock = world.getBlockAt(x + (baseChunk.getX() * 16), y, z + (baseChunk.getZ() * 16));
					
					if(targetBlock.getTypeId() == Material.STONE.getId())
					{
						if(random.nextInt(32) >= y)
						{
							targetBlock.setTypeIdAndData(Material.STATIONARY_LAVA.getId(), (byte) 0, true);
						}
					}
				}
			}
		}
	}
}
