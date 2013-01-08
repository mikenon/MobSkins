package me.cyberstalk.plugin.mobskins.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.cyberstalk.plugin.mobskins.MobSkins;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class lggr {
	
	public static final Logger l = Logger.getLogger("Minecraft");
	
	private static String PLUGIN_NAME = "MobSkins";
	
	public static void Info(String message) {
		l.log(Level.INFO, "[" + PLUGIN_NAME + "] " + message);
	}
	
	public static void Warning(String message) {
		l.log(Level.WARNING, "[" + PLUGIN_NAME + "] " + message);
	}
	
	public static void Error(String message) {
		l.log(Level.SEVERE, "[" + PLUGIN_NAME + "] " + message);
	}

	public static void Debug(String message) {
		if(MobSkins.isDebug()){
			l.log(Level.INFO, "[DEBUG] [" + PLUGIN_NAME + "] " + message);
		}
	}
	
	public static void Debug(Throwable t) {
		if(MobSkins.isDebug()){
			l.log(Level.SEVERE, t.getMessage(), t);
		}
	}
	
	public static void StackTrace(Throwable t) {
		l.log(Level.SEVERE, t.getMessage(), t);
	}
	
	public static void chat(Player player, String message){
		player.sendMessage(message);
	}
	
	public static void chat(Player player, String[] lines, ChatColor[] colors){
		String message = ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"MobSkins"+ChatColor.DARK_GRAY+"] ";
		ChatColor color = ChatColor.WHITE;
		int i = 0;
		for(String s : lines){
			if(colors.length>(i-1))
				color = colors[i];
			message += color + s;
			i++;
		}
		chat(player,message);
	}
	
	public static void sendNotification(SpoutPlayer sPlayer, String string) {
		if (sPlayer.isSpoutCraftEnabled() && (sPlayer instanceof SpoutPlayer)) {
			if (string.length() < 25) {
				sPlayer.sendNotification(sPlayer.getName(), string, Material.LOCKED_CHEST);
			} else {
				sPlayer.sendNotification(sPlayer.getName(), string.substring(0, 25), Material.LOCKED_CHEST);
			}
		} else {
			sPlayer.sendMessage(string);
		}
	}
	
	public static void sendNotification(SpoutPlayer sPlayer, String string, Material mat) {
		if (sPlayer.isSpoutCraftEnabled() && (sPlayer instanceof SpoutPlayer)) {
			if (string.length() < 25) {
				sPlayer.sendNotification(sPlayer.getName(), string, mat);
			} else {
				sPlayer.sendNotification(sPlayer.getName(), string.substring(0, 25), mat);
			}
		} else {
			sPlayer.sendMessage(string);
		}
	}
}
