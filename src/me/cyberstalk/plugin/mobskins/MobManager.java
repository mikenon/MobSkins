package me.cyberstalk.plugin.mobskins;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.Spout;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MobManager {

	static HashMap<UUID,Integer> activeZombies;
	
	public MobManager(){
		activeZombies = new HashMap<UUID,Integer>();
	}
	
	public static void mobSet(UUID id){
		if(isMob(id))
			mobClear(id);
		activeZombies.put(id, getTime());
	}
	
	public static void mobClear(UUID id){
		if(isMob(id))
			activeZombies.remove(id);
	}
	
	public static int mobGet(UUID id){
		if(isMob(id))
			return activeZombies.get(id);
		return 0;
	}
	
	public static boolean isMob(UUID id){
		if(activeZombies.containsKey(id))
			return true;
		else
			return false;
	}
	
	public static int getTime(){
		return (int)System.currentTimeMillis()/1000;
	}
	
	public static void setMobSkin(LivingEntity target, String url){
//		Spout.getServer().setEntitySkin(target, url, EntitySkinType.DEFAULT);
//		MobSkins.getInstance().SS.setEntitySkin((LivingEntity) target, url, EntitySkinType.DEFAULT);
//		lggr.Debug("Entity: "+target.getType().name().toLowerCase()+" Skin: "+url);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			final SpoutPlayer splayer = SpoutManager.getPlayer(player);
			if (!splayer.isSpoutCraftEnabled()) {
				continue;
			}
//			try {
				splayer.setEntitySkin(target, url, EntitySkinType.DEFAULT);
//			} catch (Exception ioe) {
//				lggr.Debug("Couldn't apply skin: " + url + ". Skipping texture application.");
//			}
		}
	}
	
	public static void resetMobSkin(LivingEntity target){
		Spout.getServer().resetEntitySkin(target);
	}
}
