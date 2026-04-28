package io.github.jack1424.realTimeWeather.placeholders;

import io.github.jack1424.realTimeWeather.ConfigManager;
import io.github.jack1424.realTimeWeather.RealTimeWeather;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class RealTimeWeatherExpansion extends PlaceholderExpansion {
	private final RealTimeWeather rtw;
	private final ConfigManager config;

	public RealTimeWeatherExpansion(RealTimeWeather rtw) {
		this.rtw = rtw;
		this.config = rtw.getConfigManager();
	}

	@Override
	public String getIdentifier() {
		return "realtimeweather";
	}

	@Override
	public String getAuthor() {
		return "Jack1424";
	}

	@Override
	public String getVersion() {
		return rtw.getPluginMeta().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {
		if (!config.isTimeEnabled())
			return "";

		if (params.equalsIgnoreCase("time"))
			return TimePlaceholderFormatter.formatTime(config.getTimeZone(), config.getTimePlaceholderFormat());

		return null;
	}
}
