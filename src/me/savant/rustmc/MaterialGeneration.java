package me.savant.rustmc;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

public class MaterialGeneration
{
	private int size = 500;
	private int level = 32;
	
	private World world;
	
	private long seed = 123456789L;
	private Random r;
	
	public MaterialGeneration()
	{
		world = Bukkit.getWorld("flat");
	}
	
	public long update()
	{
		r = new Random();
		r.setSeed(seed);
		
		long startTime = System.currentTimeMillis();
		
		oreGeneration();
		treeGeneration();
		
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	public long reset()
	{
		long startTime = System.currentTimeMillis();
		
		for(int x = 0; x < size; x++)
		{
			for(int z = 0; z < size; z++)
			{
				for(int y = level; y < 256; y++)
				{
					new Location(world, x, y, z).getBlock().setType(Material.AIR);
				}
			}
		}
		
		for(int x = 0; x < size; x++)
		{
			for(int z = 0; z < size; z++)
			{
				new Location(world, x, level - 1, z).getBlock().setType(Material.GRASS);
			}
		}
		
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	private void oreGeneration()
	{
		for(int x = 0; x < size; x++)
		{
			for(int z = 0; z < size; z++)
			{
				if(chance(0.085f))
				{
					new Location(world, x, level, z).getBlock().setType(randomOre());
				}
			}
		}
	}
	
	private void treeGeneration()
	{
		for(int x = 0; x < size; x++)
		{
			for(int z = 0; z < size; z++)
			{
				if(chance(0.175f))
				{
					Location loc = new Location(world, x, level, z);
					if(loc.getBlock().getType() == Material.AIR)
						world.generateTree(new Location(world, x, level, z), TreeType.TREE);
				}
			}
		}
	}
	
	private Material randomOre()
	{
		if(chance(50))
		{
			return Material.IRON_ORE;
		}
		else if(chance(70))
		{
			return Material.GOLD_ORE;
		}
		else
		{
			return Material.DIAMOND_ORE;
		}
	}
	
	private boolean chance(float chance)
	{
		float f = r.nextFloat();
		return f <= (chance / 100);
	}
}