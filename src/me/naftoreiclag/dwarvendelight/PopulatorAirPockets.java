package me.naftoreiclag.dwarvendelight;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class PopulatorAirPockets extends BlockPopulator
{
	//average of occurrance in a chunk = (256 / 100) = 2.56 times
	
	@Override
	public void populate(World world, Random random, Chunk baseChunk)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				//get a random number between 0 and 1000
				int chance = random.nextInt(100);
				int xPos = random.nextInt(16) + (baseChunk.getX() * 16);
				int zPos = random.nextInt(16) + (baseChunk.getZ() * 16);
				int yPos = random.nextInt(240);
				int size = 5;
				int halfLife = 20;
				int maxLife = halfLife * 2;
				int lean = random.nextInt(2);
				
				//target a block
				Block targetBlock;
				
				if(chance == 1)
				{
					//what should we do first, boss?
					//average length = (1 / n) * x < 0.5 => x < 0.5n
					//average lenght = 15 times
					//last number = stop growing
					//zero = grow one time
					//anything else = shrink once, grow.
					int next = 0;
					
					for(int count = 0; count < maxLife; count ++)
					{
						//if the next value was not to terminate,
						if(next != halfLife - 1)
						{
							//If the action is to shrink and it can shrink,
							if(next != 0 && size > 1)
							{
								//shrink
								size --;
							}

							//offset it
							xPos = (xPos - size) + random.nextInt(size * 2);
							yPos = (yPos - size) + random.nextInt(size * 2);
							zPos = (zPos - size) + random.nextInt(size * 2);
							
							//will it decay?
							boolean willDecay = false;
							
							//Make a cube of air with side length size.
							for(int a = 0; a < size; a ++)
							{
								for(int b = 0; b < size; b ++)
								{
									for(int c = 0; c < size; c ++)
									{
										//if it's an edge,
										if((a == 0 || a == size - 1) || (b == 0 || b == size - 1) || (c == 0 || c == size - 1))
										{
											//random chance of decaying
											willDecay = random.nextBoolean();
										}
										else
										{
											willDecay = false;
										}
										
										//find a block
										targetBlock = world.getBlockAt(xPos + a, yPos + b, zPos + c);
										if(targetBlock.getTypeId() == Material.STONE.getId())
										{
											if(!willDecay)
											{
												targetBlock.setTypeIdAndData(Material.AIR.getId(), (byte) 0, false);
											}
										}
									}
								}
							}
							
							//lean slightly
							switch(lean)
							{
								case 0:
								{
									//do nothing
									break;
								}
								case 1:
								{
									//go up
									yPos += size;
								}
								case 2:
								{
									//go down
									yPos += -size;
								}
								default:
								{
									//How did this happen!?
								}
							}
							
							//Make sure we don't have any impossible sizes.
							if(yPos < 3)
							{
								yPos = 3;
							}
							
							//Get next action.
							next = random.nextInt(halfLife);
						}
						else
						{
							//You have been terminated.
							break;
						}
					}
				}
			}
		}
	}
}
