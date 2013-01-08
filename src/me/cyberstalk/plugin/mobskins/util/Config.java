package me.cyberstalk.plugin.mobskins.util;

import java.util.HashMap;

import me.cyberstalk.plugin.mobskins.MobSkins;
import me.cyberstalk.plugin.mobskins.SkinType;

public class Config {

	boolean debug;
	int manualitem;
	int chance;
	String url_base;
	String sound;
	HashMap<String,Integer> ranges;
	
	public Config(){
		MobSkins.getInstance().getConfig().options().header(getHeader());
		MobSkins.getInstance().getConfig().options().copyDefaults(true);
		MobSkins.getInstance().saveConfig();
		MobSkins.getInstance().reloadConfig();
		ranges = new HashMap<String,Integer>();
		readConfig();
	}

	private void readConfig() {
		debug = MobSkins.getInstance().getConfig().getBoolean("debug",false);
		manualitem = MobSkins.getInstance().getConfig().getInt("manualitem",369);
		chance = MobSkins.getInstance().getConfig().getInt("chance",30);
		url_base = MobSkins.getInstance().getConfig().getString("url_base","http://mcdefiance.com/skins/zombie/");
		sound = MobSkins.getInstance().getConfig().getString("sound","http://mcalerts.com/assets/plugins/MobSkins/crack.wav");

		ranges.put("desert", MobSkins.getInstance().getConfig().getInt("range_desert",1));
		ranges.put("forest", MobSkins.getInstance().getConfig().getInt("range_forest",1));
		ranges.put("iceland", MobSkins.getInstance().getConfig().getInt("range_iceland",1));
		ranges.put("mountain", MobSkins.getInstance().getConfig().getInt("range_mountain",1));
		ranges.put("mushroom", MobSkins.getInstance().getConfig().getInt("range_mushroom",1));
		ranges.put("plains", MobSkins.getInstance().getConfig().getInt("range_plains",1));
		ranges.put("water", MobSkins.getInstance().getConfig().getInt("range_water",1));
	}

	public void printConfig(){
		if(isDebug()){
			lggr.Info("[Config] manualitem: "+manualitem);
			lggr.Info("[Config] chance: "+chance);
			lggr.Info("[Config] base url: "+url_base);
			lggr.Info("[Config] sound: "+sound);
			
			lggr.Info("[Config] range_desert: "+getBiomeRange(SkinType.DESERT));
			lggr.Info("[Config] range_forest: "+getBiomeRange(SkinType.FOREST));
			lggr.Info("[Config] range_iceland: "+getBiomeRange(SkinType.ICELAND));
			lggr.Info("[Config] range_mountain: "+getBiomeRange(SkinType.MOUNTAIN));
			lggr.Info("[Config] range_mushroom: "+getBiomeRange(SkinType.MUSHROOM));
			lggr.Info("[Config] range_plains: "+getBiomeRange(SkinType.PLAINS));
			lggr.Info("[Config] range_water: "+getBiomeRange(SkinType.WATER));
		} else {
			lggr.Info("Config Read");
		}
	}
		
	public boolean isDebug() { 
		return debug; 
	}
	
	public int getManualItem(){
		return manualitem;
	}
	
	public int getChance(){
		return chance;
	}

	public String getBaseUrl(){
		return url_base;
	}
	
	public String getSound(){
		return sound;
	}

	public int getBiomeRange(SkinType type) {
		return getBiomeRange(type.name().toLowerCase());
	}
	
	public int getBiomeRange(String name) {
		if(ranges.containsKey(name))
			return ranges.get(name);
		return 0;
	}
	
	public HashMap<String,Integer> getRangeMap(){
		return ranges;
	}

	public void save() {
		MobSkins.getInstance().saveConfig();
	}

	public void reload() {
		MobSkins.getInstance().reloadConfig();
		readConfig();
	}

	private String getHeader() {
		String header = "Developer: mikenon\n"
				+ "Created: December 8th, 2012\n"
				+ "This plugin was created for McDefiance.com.\n"
				+ "-------------------------------------------\n"
				+ "Settings:\n"
				+ "debug - [true|false] - Whether or not to show debug info\n"
				+ "manualitem - [integer] - The item id to use to manually add or remove a texture\n"
				+ "chance - [integer] - The percent chance [0-100] that a spawning zombie will be textured\n"
				+ "base_url - [url] - The beginning of the skin url, common to all biomes\n"
				+ "sound - [url] - The url of the sound to play when a textured zombie targets a player\n"
//				Desert, Forest, Iceland, Mountain, Mushroom, Plains, Water
				+ "range_desert - [int] the number of skins available at base_url/desert/\n"
				+ "range_forest - [int] ..\n"
				+ "range_iceland - [int] ..\n"
				+ "range_mountain - [int] ..\n"
				+ "range_mushroom - [int] ..\n"
				+ "range_plains - [int] ..\n"
				+ "range_water - [int] ..\n"
				+ "-------------------------------------------\n";
		return header;
	}
}