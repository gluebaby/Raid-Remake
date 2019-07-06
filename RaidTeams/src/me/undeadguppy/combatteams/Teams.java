
package me.undeadguppy.combatteams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.Location;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Messages;

public class Teams {
	private static Map<String, String> playerTeam = new HashMap<String, String>();
	private static List<String> playerList = new ArrayList<String>();
	private String name;
	private String leader;
	private String password;
	private Integer kills;
	private Integer deaths;
	private Boolean friendlyFire;
	private List<String> members = new ArrayList<String>();
	private List<String> managers = new ArrayList<String>();
	private Location rally;
	private Location hq;

	public Teams(String name, String password, String leader, Integer kills, Integer deaths, Boolean friendlyFire) {
		this.name = name;
		this.password = password;
		this.leader = leader;
		this.kills = kills;
		this.deaths = deaths;
		this.friendlyFire = friendlyFire;
	}

	public void joinTeam(Player player) {
		playerTeam.put(player.getName(), this.name);
		playerList.add(player.getName());
		this.members.add(player.getName());
		for (String m : this.members) {
			Player p = Bukkit.getPlayer((String) m);
			p.sendMessage(Messages.getJoinMessage().replaceAll("#player", player.getName()));
		}
	}

	public void leaveTeam(Player player) {
		playerTeam.remove(player.getName());
		playerList.remove(player.getName());
		for (String m : this.members) {
			Player p = Bukkit.getPlayer((String) m);
			p.sendMessage(Messages.getLeaveMessage().replaceAll("#player", player.getName()));
		}
		this.members.remove(player.getName());
		if (this.managers.contains(player.getName())) {
			this.managers.remove(player.getName());
		}
	}

	public void kickPlayer(Player kicker, Player player) {
		playerTeam.remove(player.getName());
		playerList.remove(player.getName());
		this.members.remove(player.getName());
		player.sendMessage("\u00a79You have been kicked from your team by \u00a77" + kicker.getName() + "\u00a79!");
		for (String members : this.members) {
			Player p = Bukkit.getPlayer((String) members);
			p.sendMessage("\u00a77" + player.getName() + " \u00a79has been kicked out of the team by \u00a77"
					+ kicker.getName());
		}
	}

	public void promotePlayer(Player promoter, Player player) {
		if (!this.members.contains(player.getName())) {
			return;
		}
		if (this.managers.contains(player.getName())) {
			player.sendMessage("\u00a74Error: \u00a7cYou are already a manager of this team.");
			return;
		}
		this.managers.add(player.getName());
		for (String members : this.members) {
			Player p = Bukkit.getPlayer((String) members);
			p.sendMessage("\u00a77" + player.getName() + " \u00a79has been promoted to team manager by \u00a77 "
					+ promoter.getName() + "\u00a79!");
		}
	}

	public void demotePlayer(Player demoter, Player player) {
		if (!this.members.contains(player.getName())) {
			return;
		}
		if (!this.managers.contains(player.getName())) {
			return;
		}
		if (player.getName().equalsIgnoreCase(this.leader)) {
			return;
		}
		this.managers.remove(player.getName());
		for (String members : this.members) {
			Player p = Bukkit.getPlayer((String) members);
			p.sendMessage("\u00a77" + player.getName() + " \u00a79has been demoted by \u00a77 " + demoter.getName()
					+ "\u00a79!");
		}
	}

	public void toggleFriendlyFire() {
		if (this.friendlyFire.booleanValue()) {
			this.friendlyFire = false;
			for (String m : this.members) {
				Player p = Bukkit.getPlayer((String) m);
				p.sendMessage("\u00a79Friendly fire has been toggled off!");
			}
		} else {
			this.friendlyFire = true;
			for (String m : this.members) {
				Player p = Bukkit.getPlayer((String) m);
				p.sendMessage("\u00a79Friendly fire has been toggled on!");
			}
		}
	}

	public void addKills() {
		this.kills = this.kills + 1;
	}

	public void addDeaths() {
		this.deaths = this.deaths + 1;
	}

	public static Map<String, String> getPlayerTeam() {
		return playerTeam;
	}

	public static void setPlayerTeam(Map<String, String> playerTeam) {
		Teams.playerTeam = playerTeam;
	}

	public static List<String> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(List<String> playerList) {
		Teams.playerList = playerList;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Integer getKills() {
		return this.kills;
	}

	public void setKills(Integer kills) {
		this.kills = kills;
	}

	public Integer getDeaths() {
		return this.deaths;
	}

	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getMembers() {
		return this.members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public Boolean getFriendlyFire() {
		return this.friendlyFire;
	}

	public void setFriendlyFire(Boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
	}

	public List<String> getManagers() {
		return this.managers;
	}

	public void setManagers(List<String> managers) {
		this.managers = managers;
	}

	public Location getRally() {
		return this.rally;
	}

	public void setRally(Location rally) {
		this.rally = rally;
	}

	public Location getHq() {
		return this.hq;
	}

	public void setHq(Location hq) {
		this.hq = hq;
	}
}
