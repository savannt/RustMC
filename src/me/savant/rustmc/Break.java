package me.savant.rustmc;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Break implements Listener
{
	private Random r;
	
	public Break()
	{
		r = new Random();
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Material type = e.getBlock().getType();
		if(type == Material.IRON_ORE || type == Material.GOLD_ORE || type == Material.DIAMOND_ORE)
		{
			Player p = e.getPlayer();
			if(chance(12.5f))
			{
				e.getBlock().setType(Material.AIR);
				e.getBlock().getLocation().subtract(0, 1, 0).getBlock().setType(Material.GRASS);
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 15);
			}
			if(type == Material.IRON_ORE)
			{
				if(chance(40))
				{
					int amount = 15;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Stone", 15);
					ItemIndex.giveItem(ItemType.STONE, p, amount);
				}
				else if(chance(50))
				{
					int amount = 5;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Metal Ore", 15);
					ItemIndex.giveItem(ItemType.METAL_ORE, p, amount);
				}
				else
				{
					int amount = 1;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " High Quality Metal Ore", 15);
					ItemIndex.giveItem(ItemType.HIGH_QUALITY_METAL_ORE, p, amount);
				}
			}
			else if(type == Material.GOLD_ORE)
			{
				if(chance(40))
				{
					int amount = 15;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Stone", 15);
					ItemIndex.giveItem(ItemType.STONE, p, amount);
				}
				else if(chance(50))
				{
					int amount = 5;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Sulfur Ore", 15);
					ItemIndex.giveItem(ItemType.SULFUR_ORE, p, amount);
				}
				else
				{
					int amount = 1;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " High Quality Metal Ore", 15);
					ItemIndex.giveItem(ItemType.HIGH_QUALITY_METAL_ORE, p, amount);
				}
			}
			else if(type == Material.DIAMOND_ORE)
			{
				if(chance(40))
				{
					int amount = 15;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Stone", 15);
					ItemIndex.giveItem(ItemType.STONE, p, amount);
				}
				else if(chance(50))
				{
					int amount = 1;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " High Quality Metal Ore", 15);
					ItemIndex.giveItem(ItemType.HIGH_QUALITY_METAL_ORE, p, amount);
				}
				else
				{
					int amount = 5;
					amount *= getPickBonus(p.getItemInHand());
					ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Metal Ore", 15);
					ItemIndex.giveItem(ItemType.METAL_ORE, p, amount);
				}
			}
			e.setCancelled(true);
		}
		
		if(type == Material.LOG)
		{
			Player p = e.getPlayer();
			if(chance(12.5f))
			{
				Location loc = e.getBlock().getLocation();
				int y = 0;
				for(int y1 = loc.getBlockY(); new Location(loc.getWorld(), loc.getX(), y1, loc.getZ()).getBlock().getType() == Material.LOG; y1--)
				{
					y = y1;
				}
				breakTree(e.getBlock());
				new Location(loc.getWorld(), loc.getX(), y - 1, loc.getZ()).getBlock().setType(Material.GRASS);
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 15);
			}
			int amount = 15;
			amount *= getAxeBonus(p.getItemInHand());
			ActionBarAPI.sendActionBar(p, ChatColor.GOLD + "+" + amount + " Wood", 15);
			ItemIndex.giveItem(ItemType.WOOD, p, amount);
			e.setCancelled(true);
		}
	}
	
	public void breakTree(Block tree)
	{
		if(tree.getType() != Material.LOG && tree.getType() != Material.LEAVES)
			return;
		tree.setType(Material.AIR);
		for(BlockFace face: BlockFace.values())
			breakTree(tree.getRelative(face));
	}
	
	private int getPickBonus(ItemStack item)
	{
		if(item.getType() == Material.STONE_PICKAXE)
		{
			return 2;
		}
		else if(item.getType() == Material.IRON_PICKAXE)
		{
			return 3;
		}
		else if(item.getType() == Material.DIAMOND_PICKAXE)
		{
			return 4;
		}
		return 1;
	}
	
	private int getAxeBonus(ItemStack item)
	{
		if(item.getType() == Material.STONE_AXE)
		{
			return 2;
		}
		else if(item.getType() == Material.IRON_AXE)
		{
			return 3;
		}
		else if(item.getType() == Material.DIAMOND_AXE)
		{
			return 4;
		}
		return 1;
	}
	
	private boolean chance(float chance)
	{
		float f = r.nextFloat();
		return f <= (chance / 100);
	}
}