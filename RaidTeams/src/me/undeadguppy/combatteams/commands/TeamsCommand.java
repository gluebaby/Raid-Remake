/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package me.undeadguppy.combatteams.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Messages;

import me.undeadguppy.combatteams.Teams;
import me.undeadguppy.combatteams.TeamsPlugin;
import me.undeadguppy.combatteams.utils.CombatUtil;

public class TeamsCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			for (String msg : this.getHelpMessage()) {
				player.sendMessage(msg);
			}
		} else if (args[0].equalsIgnoreCase("help")) {
			for (String msg : this.getHelpMessage()) {
				player.sendMessage(msg);
			}
		} else if (args[0].equalsIgnoreCase("create")) {
			if (args.length == 3) {
				if (!Teams.getPlayerTeam().containsKey(player.getName())
						&& !Teams.getPlayerList().contains(player.getName())) {
					if (!TeamsPlugin.getInstance().getTeams().containsKey(args[1])) {
						Teams.getPlayerTeam().put(player.getName(), args[1]);
						Teams.getPlayerList().add(player.getName());
						Teams team = new Teams(args[1], args[2], player.getName(), 0, 0, false);
						team.getMembers().add(player.getName());
						team.getManagers().add(player.getName());
						TeamsPlugin.getInstance().getTeams().put(args[1], team);
						player.sendMessage(
								"\u00a77\u00a7k||\u00a79Teams\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You successfully created team: \u00a7f"
										+ args[1] + "\u00a79!");
						Bukkit.broadcastMessage((String) Messages.getCreateMessage().replaceAll("#teamname", args[1])
								.replaceAll("#player", player.getName()));
					} else {
						player.sendMessage("\u00a74Error: \u00a7cThat team already exists!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou are already in a team!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team create <teamname> <password>.");
			}
		} else if (args[0].equalsIgnoreCase("join")) {
			if (args.length == 3) {
				if (TeamsPlugin.getInstance().getTeams().containsKey(args[1])) {
					if (!Teams.getPlayerTeam().containsKey(player.getName())
							&& !Teams.getPlayerList().contains(player.getName())) {
						Teams team = TeamsPlugin.getInstance().getTeams().get(args[1]);
						if (args[2].equals(team.getPassword())) {
							team.joinTeam(player);
						} else {
							player.sendMessage("\u00a74Error: \u00a7cIncorrect password.");
						}
					} else {
						player.sendMessage("\u00a74Error: \u00a7cYou are already in a team.");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cThat team doesn't exist!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team join <teamname> <password>.");
			}
		} else if (args[0].equalsIgnoreCase("leave")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (!player.getName().equalsIgnoreCase(team.getLeader())) {
					team.leaveTeam(player);
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou must disband your team because you are leader!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("info")) {
			if (args.length == 2) {
				if (TeamsPlugin.getInstance().getTeams().containsKey(args[1])) {
					Teams team = TeamsPlugin.getInstance().getTeams().get(args[1]);
					ArrayList<String> list = new ArrayList<String>();
					list.add(
							"\u00a77Oo\u00a7m-----------------------\u00a77oOo\u00a7m-----------------------\u00a77oO");
					list.add("\u00a79Team: \u00a7b" + team.getName());
					list.add("\u00a79Leader: \u00a7b" + team.getLeader());
					String ma = "\u00a79Managers: \u00a7b";
					for (String managers : team.getManagers()) {
						ma = String.valueOf(ma) + managers + "\u00a7f, \u00a7b";
					}
					list.add(ma);
					String m = "\u00a79Members: \u00a7b";
					for (String members : team.getMembers()) {
						m = String.valueOf(m) + members + "\u00a7f, \u00a7b";
					}
					list.add(m);
					list.add("\u00a79Kills: \u00a7b" + team.getKills());
					list.add("\u00a79Deaths: \u00a7b" + team.getDeaths());
					list.add(
							"\u00a77Oo\u00a7m-----------------------\u00a77oOo\u00a7m-----------------------\u00a77oO");
					for (String msg : list) {
						player.sendMessage(msg);
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cThat team doesn't exist.");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team info <teamname>.");
			}
		} else if (args[0].equalsIgnoreCase("friendlyfire") || args[0].equalsIgnoreCase("ff")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (team.getManagers().contains(player.getName())) {
					team.toggleFriendlyFire();
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou need to be a team manager to do this.");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team.");
			}
		} else if (args[0].equalsIgnoreCase("kick")) {
			if (args.length == 2) {
				if (Teams.getPlayerTeam().containsKey(player.getName())
						&& Teams.getPlayerList().contains(player.getName())) {
					Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
					if (team.getManagers().contains(player.getName())) {
						Player kickee = Bukkit.getPlayer((String) args[1]);
						if (kickee != null) {
							if (team.getMembers().contains(kickee.getName())) {
								if (!team.getManagers().contains(kickee.getName())) {
									if (!kickee.getName().equalsIgnoreCase(team.getLeader())) {
										team.kickPlayer(player, kickee);
									} else {
										player.sendMessage("\u00a74Error: \u00a7cYou cannot kick the team leader!");
									}
								} else {
									player.sendMessage("\u00a74Error: \u00a7cYou cannot kick a team manager!");
								}
							} else {
								player.sendMessage("\u00a74Error: \u00a7cThat player is not on your team.");
							}
						} else {
							player.sendMessage("\u00a74Error: \u00a7cThat player is not online!");
						}
					} else {
						player.sendMessage("\u00a74Error: \u00a7cYou are not a team manager!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team kick <player>.");
			}
		} else if (args[0].equalsIgnoreCase("promote")) {
			if (args.length == 2) {
				if (Teams.getPlayerTeam().containsKey(player.getName())
						&& Teams.getPlayerList().contains(player.getName())) {
					Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
					if (team.getManagers().contains(player.getName())) {
						Player promotee = Bukkit.getPlayer((String) args[1]);
						if (promotee != null) {
							if (team.getMembers().contains(promotee.getName())) {
								team.promotePlayer(player, promotee);
							} else {
								player.sendMessage("\u00a74Error: \u00a7cThat player is not on your team!");
							}
						} else {
							player.sendMessage("\u00a74Error: \u00a7cThat player is not online!");
						}
					} else {
						player.sendMessage("\u00a74Error: \u00a7cYou are not a team manager!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team promote <player>.");
			}
		} else if (args[0].equalsIgnoreCase("demote")) {
			if (args.length == 2) {
				if (Teams.getPlayerTeam().containsKey(player.getName())
						&& Teams.getPlayerList().contains(player.getName())) {
					Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
					if (team.getManagers().contains(player.getName())) {
						Player demotee = Bukkit.getPlayer((String) args[1]);
						if (demotee != null) {
							if (team.getMembers().contains(demotee.getName())) {
								if (team.getManagers().contains(demotee.getName())) {
									if (!demotee.getName().equalsIgnoreCase(team.getLeader())) {
										team.demotePlayer(player, demotee);
									} else {
										player.sendMessage("\u00a74Error: \u00a7cYou cannot demote the team leader!");
									}
								} else {
									player.sendMessage("\u00a74Error: \u00a7cThat player is not a team manager!");
								}
							} else {
								player.sendMessage("\u00a74Error: \u00a7cThat player is not on your team!");
							}
						} else {
							player.sendMessage("\u00a74Error: \u00a7cThat player is not online!");
						}
					} else {
						player.sendMessage("\u00a74Error: \u00a7cYou are not a team manager!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team demote <player>.");
			}
		} else if (args[0].equalsIgnoreCase("disband")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (player.getName().equalsIgnoreCase(team.getLeader())) {
					Bukkit.broadcastMessage((String) Messages.getDisbandMessage()
							.replaceAll("#teamname", team.getName()).replaceAll("#player", player.getName()));
					TeamsPlugin.getInstance().getTeams().remove(team.getName());
					TeamsPlugin.getInstance().deleteTeam = team.getName();
					TeamsPlugin.getInstance().deleteTeam();
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou must be team leader to do this!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("pass")) {
			if (args.length == 2) {
				if (Teams.getPlayerTeam().containsKey(player.getName())
						&& Teams.getPlayerList().contains(player.getName())) {
					Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
					if (player.getName().equalsIgnoreCase(team.getLeader())) {
						team.setPassword(args[1]);
						player.sendMessage(
								"\u00a77\u00a7k||\u00a79Teams\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You changed the password to: '\u00a77"
										+ args[1] + "\u00a79'!");
						for (String members : team.getMembers()) {
							Player p = Bukkit.getPlayer((String) members);
							p.sendMessage(
									"\u00a77\u00a7k||\u00a79Teams\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79The team password has been changed!");
						}
					} else {
						player.sendMessage("\u00a74Error: \u00a7cYou must be team leader to do this!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cUsage: /team pass <password>.");
			}
		} else if (args[0].equalsIgnoreCase("sethq")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (team.getManagers().contains(player.getName())) {
					team.setHq(player.getLocation());
					for (String members : team.getMembers()) {
						Player p = Bukkit.getPlayer((String) members);
						p.sendMessage(
								"\u00a79The team's HQ has been updated by \u00a77" + player.getName() + "\u00a79!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou must be a team manager to do this!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("setrally")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (team.getManagers().contains(player.getName())) {
					team.setRally(player.getLocation());
					for (String members : team.getMembers()) {
						Player p = Bukkit.getPlayer((String) members);
						p.sendMessage(
								"\u00a79The team's Rally has been updated by \u00a77" + player.getName() + "\u00a79!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou must be a team manager to do this!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("hq")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (!CombatUtil.getCombatTag().containsKey(player.getName())) {
					if (team.getHq() != null) {
						player.teleport(team.getHq());
						player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 0.5f, 1.0f);
					} else {
						player.sendMessage("\u00a74Error: \u00a7cThe team HQ hasn't been updated!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou cannot teleport while PvPing!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("rally")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
				if (!CombatUtil.getCombatTag().containsKey(player.getName())) {
					if (team.getRally() != null) {
						player.teleport(team.getRally());
						player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 0.5f, 1.0f);
					} else {
						player.sendMessage("\u00a74Error: \u00a7cThe team Rally hasn't been updated!");
					}
				} else {
					player.sendMessage("\u00a74Error: \u00a7cYou cannot teleport while PvPing!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else if (args[0].equalsIgnoreCase("chat")) {
			if (Teams.getPlayerTeam().containsKey(player.getName())
					&& Teams.getPlayerList().contains(player.getName())) {
				if (TeamsPlugin.getInstance().getUserdataConfig().getBoolean("Players." + player.getName() + ".Chat")) {
					TeamsPlugin.getInstance().getUserdataConfig().set("Players." + player.getName() + ".Chat",
							(Object) false);
					try {
						TeamsPlugin.getInstance().getUserdataConfig().save(TeamsPlugin.getInstance().getUserdataFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						TeamsPlugin.getInstance().getUserdataConfig().load(TeamsPlugin.getInstance().getUserdataFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
					player.sendMessage("\u00a77\u00a7l\u00bb \u00a79You toggled team chat off!");
				} else {
					TeamsPlugin.getInstance().getUserdataConfig().set("Players." + player.getName() + ".Chat",
							(Object) true);
					try {
						TeamsPlugin.getInstance().getUserdataConfig().save(TeamsPlugin.getInstance().getUserdataFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						TeamsPlugin.getInstance().getUserdataConfig().load(TeamsPlugin.getInstance().getUserdataFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
					player.sendMessage("\u00a77\u00a7l\u00bb \u00a79You toggled team chat on!");
				}
			} else {
				player.sendMessage("\u00a74Error: \u00a7cYou are not in a team!");
			}
		} else {
			player.sendMessage("\u00a74Error: \u00a7cInvalid sub-command; Type '/team help' for help!");
		}
		return true;
	}

	private List<String> getHelpMessage() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("\u00a77Oo\u00a7m-----------------------\u00a77oOo\u00a7m-----------------------\u00a77oO");
		list.add("\u00a7b/Team Help \u00a79- Returns this help page;");
		list.add("\u00a7b/Team Create <TeamName> <Password> \u00a79- Create a team with password.");
		list.add("\u00a7b/Team Pass <Password> \u00a79- Set your team password.");
		list.add("\u00a7b/Team Promote <Player> \u00a79- Promote player in your team to manager.");
		list.add("\u00a7b/Team Demote <Player> \u00a79- Demote player in your team from team manager.");
		list.add("\u00a7b/Team SetHQ \u00a79- Set team HQ warp!");
		list.add("\u00a7b/Team SetRally \u00a79- Set team Rally warp!");
		list.add("\u00a7b/Team HQ \u00a79- Teleport to the team HQ!");
		list.add("\u00a7b/Team Rally \u00a79- Teleport to the team Rally!");
		list.add("\u00a7b/Team Join <TeamName> <Password> \u00a79- Join a team!");
		list.add("\u00a7b/Team Leave \u00a79- Leave your team!");
		list.add("\u00a7b/Team Chat \u00a79- Toggle team chat on/off!");
		list.add("\u00a7b/Team Info <TeamName> \u00a79- Show info of team.");
		list.add("\u00a7b/Team FriendlyFire \u00a79- Toggle team friendly fire.");
		list.add("\u00a7b/Team Kick <Player> \u00a79- Kick player from your team.");
		list.add("\u00a7b/Team Disband \u00a79- Disband your team.");
		list.add("\u00a77Oo\u00a7m-----------------------\u00a77oOo\u00a7m-----------------------\u00a77oO");
		return list;
	}
}
