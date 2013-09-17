package io.github.barandc.loginalert;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
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

		getServer().getPluginManager().registerEvents(this, this);

		handlePlayersOnServer(getServer().getOnlinePlayers().length);
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("LoginAlert is disabled.");

		HandlerList.unregisterAll((Listener)this);

		handlePlayersOnServer(0);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		//Player player = event.getPlayer();
		//player.sendMessage("Hello Sir!");
		handlePlayersOnServer(getServer().getOnlinePlayers().length);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		/* For some odd reason, the PlayerQuitEvent seems to be thrown while the player 
		 * still is in the list of online players. I found no event thrown after a player 
		 * logged out. I might tackle it otherwise in a future implementation, but for now 
		 * I simply pass "the LoginHandler" the number of players excluding the one logging
		 * out. */
		Player[] players = getServer().getOnlinePlayers();
		int numberOfPlayersInList = players.length;
		int numberOfPlayersLeft = 0; 
		for (int i = 0; i < numberOfPlayersInList; i++)
		{
			// Count all those players whose name differs from the quitter's.
			if (players[i].getName() != event.getPlayer().getName())
			{
				numberOfPlayersLeft++;
			}
		}
		handlePlayersOnServer(numberOfPlayersLeft);
	}

	
	@Override
	public void handlePlayersOnServer(int numberOfPlayers) 
	{
		if (numberOfPlayers > 0)
		{
			getLogger().info("There are players online!");
			playPlayerOnlineSound();
		}
		else
		{
			getLogger().info("There are NO players online!");
			// TODO: Play "crickets" sound? 
		}
	}

	private void playPlayerOnlineSound() 
	{
		try
		{
			URL soundURL = getClass().getClassLoader().getResource("doorbell2.wav");

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
			Clip clip = AudioSystem.getClip();
			clip.setFramePosition(0);
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception ex)
		{
			getLogger().info("ERROR: failed to play login sound 2: " + ex.getMessage());
		}
	}
}
