package io.github.jack1424.realTimeWeather;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RealTimeWeatherCommand implements CommandExecutor, TabCompleter {
	private final RealTimeWeather plugin;

	public RealTimeWeatherCommand(RealTimeWeather plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("realtimeweather.admin")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
				return true;
			}

			sender.sendMessage(ChatColor.GREEN + "Reloading RealTimeWeather configuration...");
			try {
				plugin.reloadPlugin();
				sender.sendMessage(ChatColor.GREEN + "RealTimeWeather successfully reloaded!");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Failed to reload plugin. Check the console for errors.");
				plugin.getLogger().severe("Error reloading plugin: " + e.getMessage());
				e.printStackTrace();
			}
			return true;
		}

		sender.sendMessage(ChatColor.GOLD + "=== RealTimeWeather ===");
		sender.sendMessage(ChatColor.YELLOW + "/rtw reload " + ChatColor.GRAY + "- Reloads the configuration.");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<>();
		if (args.length == 1 && sender.hasPermission("realtimeweather.admin")) {
			if ("reload".startsWith(args[0].toLowerCase())) {
				completions.add("reload");
			}
		}
		return completions;
	}
}
