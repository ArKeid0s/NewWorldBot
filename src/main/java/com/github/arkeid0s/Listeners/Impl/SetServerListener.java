package com.github.arkeid0s.Listeners.Impl;

import com.github.arkeid0s.ConfigSingleton;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;

public class SetServerListener implements MessageCreateListener
{
	ConfigSingleton configSingleton = ConfigSingleton.getInstance();
	
	// === SET SERVER COMMAND ===
	@Override
	public void onMessageCreate(MessageCreateEvent event)
	{
		String[] args = event.getMessage().getReadableContent().split(" ");
		if (args[0].equals(configSingleton.GetPrefix() + "setserver"))
		{
			if (args.length < 2)
			{
				event.getChannel().sendMessage("You need to specify a server name !");
			}
			configSingleton.SetSelectedServer(args[1]);
			event.getChannel().sendMessage("Server set to : " + configSingleton.GetSelectedServer());
			event.getApi().updateActivity(ActivityType.WATCHING, configSingleton.GetSelectedServer() + " server");
		}
	}
}
