package io.github.barandc.loginalert;

// TODO: Consider a method for "player login", to be able to differentiate the alert depending on e.g. player name. 

public interface ILoginHandler {
	public void handlePlayersOnServer(int numberOfPlayers);
}
