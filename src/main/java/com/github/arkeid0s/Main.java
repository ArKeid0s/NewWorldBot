package com.github.arkeid0s;

import com.github.arkeid0s.Listeners.Impl.CallWebListener;
import com.github.arkeid0s.Listeners.Impl.SetServerListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

public class Main
{
	public static void main(String[] args)
	{
		ConfigSingleton configSingleton = ConfigSingleton.getInstance();
		
		DiscordApi api = new DiscordApiBuilder().setToken(System.getenv().get("TOKEN")).login().join();
		
		api.updateActivity(ActivityType.WATCHING, "Use " + configSingleton.GetPrefix() + "setserver to set a server");
		
		// --- STATUS COMMAND ---
		api.addMessageCreateListener(new CallWebListener());
		// --- SET SERVER COMMAND ---
		api.addMessageCreateListener(new SetServerListener());
		
		CallWebListener.DownloadDataTimer();
		
		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
	}
}
