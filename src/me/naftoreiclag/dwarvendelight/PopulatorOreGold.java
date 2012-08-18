package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class PopulatorOreGold extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk baseChunk)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				//get a random number between 0 and 1000
				int chance = random.nextInt(5);
				int depth = random.nextInt(180);
				
				//target a block
				Block targetBlock;
				
				if(chance == 0)
				{
					for(int a = 0; a < 2; a ++)
					{
						for(int b = 0; b < 2; b ++)
						{
							for(int c = 0; c < 2; c ++)
							{
								targetBlock = world.getBlockAt(x + (baseChunk.getX() * 16) + a, depth + b, z + (baseChunk.getZ() * 16) + c);
								if(targetBlock.getTypeId() == Material.STONE.getId())
								{
									targetBlock.setTypeIdAndData(Material.GOLD_ORE.getId(), (byte) 3, false);
								}
							}
						}
					}
				}
			}
		}
	}
}
