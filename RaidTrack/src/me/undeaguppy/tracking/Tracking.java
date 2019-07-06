package me.undeaguppy.tracking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Tracking {
	mainTracking plugin;
	int mx;
	int my;
	int mz;

	public Tracking(mainTracking plugin) {
		this.plugin = plugin;
	}

	public void setLoc(int x, int y, int z) {
		this.mx = x;
		this.my = y;
		this.mz = z;
	}

	public boolean checkPlayer(Player pl, int x, int z) {
		int num = 0;
		if (x == 0) {
			int plz = pl.getLocation().getBlockZ();
			num = Math.abs(z);
			if (Math.abs(this.mz - plz) < num && (z < 0 ? plz < this.mz : plz > this.mz)) {
				return true;
			}
		} else if (z == 0) {
			int plz = pl.getLocation().getBlockX();
			num = Math.abs(x);
			if (Math.abs(this.mx - plz) < num && (x < 0 ? plz < this.mx : plz > this.mx)) {
				return true;
			}
		}
		return false;
	}

	public void TrackDir(Player player, int x, int z, Player player2) {
		String compass = "North";
		List<Player> plist = UtilTracking.Who();
		ArrayList<Player> in = new ArrayList<Player>();
		int num = Math.abs(x) + Math.abs(z);
		if (player2 == null) {
			int i = 0;
			while (i < plist.size()) {
				Player pl = plist.get(i);
				boolean can = this.checkPlayer(pl, x, z);
				if (can) {
					in.add(pl);
				}
				++i;
			}
		} else {
			boolean can = this.checkPlayer(player2, x, z);
			if (can) {
				in.add(player2);
			}
		}
		if (z < 0) {
			compass = "East";
		} else if (z > 0) {
			compass = "West";
		}
		if (x < 0) {
			compass = "North";
		} else if (x > 0) {
			compass = "South";
		}
		String str = "";
		int i = 0;
		while (i < in.size()) {
			str = String.valueOf(str) + ((Player) in.get(i)).getName() + ",";
			++i;
		}
		player.sendMessage((Object) ChatColor.BLUE + compass + "<" + num + ">:  " + (Object) ChatColor.GRAY + str);
	}

	public int findBlock(World world, int x, int z, Material mat1, Material mat2) {
		boolean hasmat = true;
		int length = 0;
		int i = 1;
		while (i < 10000) {
			Block block = world.getBlockAt(this.mx + x * i, this.my, this.mz + z * i);
			Material bmat = block.getType();
			if (hasmat) {
				if (bmat == mat1) {
					++length;
					if (mat1 == Material.COBBLESTONE) {
						block.setType(Material.AIR);
					}
				} else {
					if (bmat == mat2) {
						hasmat = false;
						++length;
						if (mat1 != Material.COBBLESTONE)
							break;
						block.setType(Material.AIR);
						break;
					}
					return 0;
				}
			}
			++i;
		}
		if (length > 0 && !hasmat) {
			return length;
		}
		return 0;
	}

	public void Track(Material mat1, Material mat2, Player player, Player player2) {
		Block block = new Location(player.getWorld(), (double) player.getLocation().getBlockX(),
				(double) (player.getLocation().getBlockY() - 1), (double) player.getLocation().getBlockZ()).getBlock();
		int northDist = this.findBlock(player.getWorld(), -1, 0, mat1, mat2);
		int southDist = this.findBlock(player.getWorld(), 1, 0, mat1, mat2);
		int eastDist = this.findBlock(player.getWorld(), 0, -1, mat1, mat2);
		int westDist = this.findBlock(player.getWorld(), 0, 1, mat1, mat2);
		player.sendMessage(
				(Object) ChatColor.GRAY + "*---[" + ChatColor.BLUE + "Tracking Results" + ChatColor.GRAY + "]---*");
		if (northDist > 0) {
			this.TrackDir(player, (-northDist) * 25, 0, player2);
		}
		if (southDist > 0) {
			this.TrackDir(player, southDist * 25, 0, player2);
		}
		if (eastDist > 0) {
			this.TrackDir(player, 0, (-eastDist) * 25, player2);
		}
		if (westDist > 0) {
			this.TrackDir(player, 0, westDist * 25, player2);
		}
		if (block.getType() == Material.OBSIDIAN && northDist + southDist + eastDist + westDist >= 25) {
			block.setType(Material.AIR);
		}
	}

	public void Track(Player player, Player player2) {
		Block block = new Location(player.getWorld(), (double) player.getLocation().getBlockX(),
				(double) (player.getLocation().getBlockY() - 1), (double) player.getLocation().getBlockZ()).getBlock();
		if (block.getType() == Material.DIAMOND_BLOCK) {
			this.Track(Material.OBSIDIAN, Material.GOLD_BLOCK, player, player2);
		} else if (block.getType() == Material.OBSIDIAN) {
			this.Track(Material.COBBLESTONE, Material.STONE, player, player2);
		} else {
			player.sendMessage((Object) ChatColor.GRAY + "You need to be on a tracking block!");
		}
	}
}
