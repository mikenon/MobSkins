package me.cyberstalk.plugin.mobskins;

import java.util.UUID;

//import org.bukkit.Location;
//import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
//import org.bukkit.event.world.ChunkLoadEvent;
//import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.getspout.spoutapi.SpoutManager;
//import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MS_EntityListener implements Listener{

	public static MobSkins plugin;
	
	public MS_EntityListener(MobSkins instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if(event.getEntity().isDead())
			return;
		if(!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		int r = (int)(Math.random() * (100));
		if(r > MobSkins.conf.getChance())
			return;
		MobManager.setMobSkin((LivingEntity) event.getEntity(), MobSkins.getUtil().buildSkinUrl(event.getLocation()));
		MobManager.mobSet(event.getEntity().getUniqueId());
	}
	
//	@EventHandler
//	public void onChunkLoad(ChunkLoadEvent event){
//		for(Entity e : event.getChunk().getEntities()){
//			if(!e.getType().equals(EntityType.ZOMBIE))
//				continue;
//			int r = (int)(Math.random() * (100));
//			if(r > MobSkins.conf.getChance())
//				continue;
//			Location l = e.getLocation();
//			MobManager.setMobSkin((LivingEntity) e, MobSkins.getUtil().buildSkinUrl(l));
//			MobManager.mobSet(e.getUniqueId());
//		}
//	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if(!MobManager.isMob(event.getEntity().getUniqueId())){
			return;
		}
		MobManager.mobClear(event.getEntity().getUniqueId());
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event){
		if(event.isCancelled())
			return;
		for(Entity e : event.getChunk().getEntities()){
			if(!MobManager.isMob(e.getUniqueId()))
				continue;
			MobManager.resetMobSkin(((LivingEntity) e));
			MobManager.mobClear(e.getUniqueId());
		}
	}
	
	@EventHandler
	public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event){
		UUID id = event.getEntity().getUniqueId();
		if(!MobManager.isMob(id)){
			return;
		}
		if(event.getTarget() instanceof Player){
			if(!debounce(id))
				return;
			try{
				SpoutPlayer sp = (SpoutPlayer)event.getTarget();
				playCustomSound(sp,MobSkins.conf.getSound(),10,100);
			} catch (Exception e){ }
		}
	}
	
	private boolean debounce(UUID id){
		if(!MobManager.isMob(id)){
			return false;
		}
		int oldtime = MobManager.mobGet(id);
		int curtime = MobManager.getTime();
		int diftime = curtime - oldtime;
		if(diftime<=1){
			return false;
		}
		MobManager.mobSet(id);
		return true;
	}
	
	private void playCustomSound(SpoutPlayer player, String url, int distance, int volume){
		try{
			SpoutManager.getSoundManager().playCustomSoundEffect(
				plugin, player, url, false,
				player.getLocation(), distance, volume);
		} catch (Exception e){ 
			if(MobSkins.isDebug()){
				e.printStackTrace();
			}
		}
	}
}
