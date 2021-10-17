package com.github.arkeid0s;

import com.github.arkeid0s.Listeners.Impl.CallWebListener;
import com.github.arkeid0s.Listeners.Impl.SetServerListener;
import org.javacord.api.entity.activity.ActivityType;

import java.util.Timer;
import java.util.TimerTask;

public class Main
{
	public static void main(String[] args)
	{
		ConfigSingleton configSingleton = ConfigSingleton.getInstance();
		
		configSingleton.GetApi().updateActivity(ActivityType.WATCHING, "Use " + configSingleton.GetPrefix() + "setserver to set a server");
		
		// --- STATUS COMMAND ---
		configSingleton.GetApi().addMessageCreateListener(new CallWebListener());
		// --- SET SERVER COMMAND ---
		configSingleton.GetApi().addMessageCreateListener(new SetServerListener());
		
		CallWebListener.DownloadDataTimer();
		
		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + configSingleton.GetApi().createBotInvite());
	}
}
