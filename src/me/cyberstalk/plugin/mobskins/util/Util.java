package me.cyberstalk.plugin.mobskins.util;

import org.bukkit.Location;
import org.bukkit.block.Biome;

import me.cyberstalk.plugin.mobskins.MobSkins;
import me.cyberstalk.plugin.mobskins.SkinType;

public class Util {

	MobSkins plugin;
	
	public Util(MobSkins instance){
		plugin = instance;
	}
	
	public String buildSkinUrl(Location loc){
		Biome bio = loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ());
		return buildSkinUrl(bio);
	}
	
	public String buildSkinUrl(Location loc, int id){
		Biome bio = loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ());
		return buildSkinUrl(bio, id);
	}
	
	public String buildSkinUrl(Biome biome){
		return buildSkinUrl(BiomeToSkinType(biome));
	}
	
	public String buildSkinUrl(Biome biome, int id){
		return buildSkinUrl(BiomeToSkinType(biome), id);
	}
	
	public String buildSkinUrl(SkinType type){
		int max = MobSkins.getConf().getBiomeRange(type);
		int rnd = (int)(Math.random() * (max + 1));
		return buildSkinUrl(type,rnd);
	}
	
	public String buildSkinUrl(SkinType type, int id){
		String baseurl = MobSkins.getConf().getBaseUrl();
		if(baseurl.charAt(baseurl.length()-1)!='/')
			baseurl += "/";
		String typestr = type.name().toLowerCase();
		String url = baseurl+typestr+id+".png";
		return url;
	}
	
	public SkinType BiomeToSkinType(Biome biome){
		switch(biome){
			case DESERT: 		
			case DESERT_HILLS: 	return SkinType.DESERT;
			case PLAINS:		return SkinType.PLAINS;
			case EXTREME_HILLS: return SkinType.MOUNTAIN;
			case FROZEN_OCEAN:
			case FROZEN_RIVER:
			case ICE_PLAINS:
			case ICE_MOUNTAINS: return SkinType.ICELAND;
			case SWAMPLAND:
			case OCEAN:
			case RIVER:
			case BEACH:			return SkinType.WATER;
			case FOREST:
			case TAIGA:
			case FOREST_HILLS:
			case TAIGA_HILLS:
			case SMALL_MOUNTAINS:
			case JUNGLE:
			case JUNGLE_HILLS:	return SkinType.FOREST;
			case HELL:
			case SKY:
			case MUSHROOM_ISLAND:
			case MUSHROOM_SHORE: return SkinType.MUSHROOM;
			default: 			return SkinType.PLAINS;
		}
	}
}