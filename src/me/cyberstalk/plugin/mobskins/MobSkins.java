package me.cyberstalk.plugin.mobskins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.cyberstalk.plugin.mobskins.util.Config;
import me.cyberstalk.plugin.mobskins.util.Util;
import me.cyberstalk.plugin.mobskins.util.lggr;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.SpoutServer;

public class MobSkins extends JavaPlugin{
	
	static MobSkins plugin;
	static Config conf;
	static Util util;
	SpoutServer SS;
	public static Permission perms = null;
	static HashMap<UUID,Integer> activeMobs;
	
	@Override
	public void onEnable() {
		plugin = this;
		SS = new SpoutServer();
		conf = new Config();
		util = new Util(this);
		cacheSkins();
		new MobManager();
		setupPermissions();
		getServer().getPluginManager().registerEvents(new MS_PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new MS_EntityListener(this), this);
		PluginDescriptionFile pdfFile = this.getDescription();
		lggr.Info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	@Override
	public void onDisable() {
		lggr.Info("Disabled");
	}
	
	public static MobSkins getInstance(){
		return plugin;
	}

	public static boolean isDebug() {
		return conf.isDebug();
	}
	
	private void cacheSkins(){
		List<String> urls = new ArrayList<String>();
		String baseurl = conf.getBaseUrl();
		if(baseurl.charAt(baseurl.length()-1)!='/')
			baseurl += "/";
		for(String type : conf.getRangeMap().keySet()){
			String u = baseurl+type;
			for(int i=0; i<=conf.getRangeMap().get(type); i++){
				String url = u+i+".png";
				lggr.Debug("Adding URL "+url);
				urls.add(url);
				SpoutManager.getFileManager().addToCache(this, url);
			}
		}
//		SpoutManager.getFileManager().addToCache(this, urls);
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
	
	public static Boolean hasPermission(Player player, String s) {
		return perms.has(player,s);
	}
	
	public static Config getConf(){
		return conf;
	}
	
	public static Util getUtil(){
		return util;
	}
}