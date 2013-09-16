package io.github.barandc.loginalert;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoginAlert extends JavaPlugin implements ILoginHandler, Listener 
{
	/* NOTE: The initial idea is to make it possible to have different 
	 * classes implementing this interface, to enable different 
	 * notifications, e.g. sound, LED, IM, ... 
	 * For now, I implement it in this class, keeping it simple. */
	//ILoginHandler loginHandler;
	
	@Override
	public void onEnable()
	{
		getLogger().info("LoginAlert is enabled.");
		handlePlayersOnServer(getServer().getOnlinePlayers().length);
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("LoginAlert is disabled.");
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event) {
		//Player player = event.getPlayer();
		//player.sendMessage("Hello Sir!");
		handlePlayersOnServer(getServer().getOnlinePlayers().length);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) {
		handlePlayersOnServer(getServer().getOnlinePlayers().length);
	}

	
	@Override
	public void handlePlayersOnServer(int numberOfPlayers) {
		if (numberOfPlayers > 0)
		{
			getLogger().info("There are players online!");
		}
		else
		{
			getLogger().info("There are NO players online!");
		}
	}
}
