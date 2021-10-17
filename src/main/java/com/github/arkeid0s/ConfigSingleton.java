package com.github.arkeid0s;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public final class ConfigSingleton
{
	private static ConfigSingleton INSTANCE;
	private String selectedServer = "";
	private final String prefix = "$";
	private final String token = "";
	private DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
	
	private ConfigSingleton()
	{
	}
	
	public static ConfigSingleton getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ConfigSingleton();
		}
		
		return INSTANCE;
	}
	
	// getters and setters
	public DiscordApi GetApi()
	{
		return api;
	}
	
	public String GetSelectedServer()
	{
		return selectedServer;
	}
	
	public void SetSelectedServer(String _serverName)
	{
		selectedServer = _serverName;
	}
	
	public String GetPrefix()
	{
		return prefix;
	}
}
