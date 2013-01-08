package me.cyberstalk.plugin.mobskins;

import java.util.HashMap;
import java.util.UUID;

import me.cyberstalk.plugin.mobskins.util.lggr;

import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.Spout;
import org.getspout.spoutapi.player.EntitySkinType;

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
		MobSkins.getInstance().SS.setEntitySkin((LivingEntity) target, url, EntitySkinType.DEFAULT);
		lggr.Debug("Entity: "+target.getType().name().toLowerCase()+" Skin: "+url);
	}
	
	public static void resetMobSkin(LivingEntity target){
		Spout.getServer().resetEntitySkin(target);
	}
}
