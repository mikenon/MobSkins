package me.cyberstalk.plugin.mobskins;

import java.util.HashMap;

import me.cyberstalk.plugin.mobskins.util.lggr;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.getspout.spoutapi.player.EntitySkinType;

public class MS_PlayerListener implements Listener{
	public static MobSkins plugin;
	
	public HashMap<Location,Long> timeout = new HashMap<Location,Long>();
	
	public MS_PlayerListener(MobSkins instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player p = event.getPlayer();
		Entity e = event.getRightClicked();
//		if(!e.getType().equals(EntityType.ZOMBIE))
//			return;
		if(p.getItemInHand().getTypeId()!=MobSkins.conf.getManualItem())
			return;
		Location l = e.getLocation();
		
		if(plugin.SS.getEntitySkin((LivingEntity)e, EntitySkinType.DEFAULT) != null){
			if(MobSkins.hasPermission(event.getPlayer(), "mobskins.texture.remove")){
				String skin = plugin.SS.getEntitySkin((LivingEntity)e, EntitySkinType.DEFAULT);
				MobManager.resetMobSkin((LivingEntity) e);
				MobManager.mobClear(e.getUniqueId());
				lggr.chat(p,
					new String[] {"Removed skin ",trimUrl(skin)},
					new ChatColor[] {ChatColor.GRAY,ChatColor.DARK_AQUA});
			}
		} else {
			if(MobSkins.hasPermission(event.getPlayer(), "mobskins.texture.add")){
				String skin = MobSkins.getUtil().buildSkinUrl(l);
				MobManager.setMobSkin((LivingEntity)e, skin);
				MobManager.mobSet(e.getUniqueId());
				lggr.chat(p,
						new String[] {"Set skin to ",trimUrl(skin)},
						new ChatColor[] {ChatColor.GRAY,ChatColor.DARK_AQUA});
			}
		}
	}
	
	private String trimUrl(String url){
		String baseurl = MobSkins.getConf().getBaseUrl();
		if(baseurl.charAt(baseurl.length()-1)!='/')
			baseurl += "/";
		String ret = url.replace(baseurl, "");
		ret = ret.replace(".png", "");
		return ret;
	}
}