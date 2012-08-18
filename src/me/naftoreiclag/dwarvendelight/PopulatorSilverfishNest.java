package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class PopulatorSilverfishNest extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk baseChunk)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				//get a random number between 0 and 1000
				int chance = random.nextInt(2000);
				int depth = random.nextInt(120);
				
				//target a block
				Block targetBlock;
				
				if(chance <= 1)
				{
					for(int a = 0; a < 5; a ++)
					{
						for(int b = 0; b < 5; b ++)
						{
							for(int c = 0; c < 5; c ++)
							{
								int typeChance = random.nextInt(7);
								
								targetBlock = world.getBlockAt(x + (baseChunk.getX() * 16) + a, depth + b, z + (baseChunk.getZ() * 16) + c);
								if(targetBlock.getTypeId() == Material.STONE.getId())
								{
									if(typeChance <= 1)
									{
										targetBlock.setTypeIdAndData(97, (byte) 1, false);
									}
									else if(typeChance <= 2)
									{
										targetBlock.setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) 2, false);
									}
									else if(typeChance <= 3)
									{
										targetBlock.setTypeIdAndData(Material.COBBLESTONE_STAIRS.getId(), (byte) random.nextInt(3), false);
									}
									else if(typeChance <= 4)
									{
										targetBlock.setTypeIdAndData(Material.GRAVEL.getId(), (byte) 0, false);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
