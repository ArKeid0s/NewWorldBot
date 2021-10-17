package com.github.arkeid0s.Listeners.Impl;

import com.github.arkeid0s.ConfigSingleton;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CallWebListener implements MessageCreateListener
{
	private static String status = "";
	static ConfigSingleton configSingleton = ConfigSingleton.getInstance();
	private static MessageCreateEvent _event;
	
	// === STATUS COMMAND ===
	@Override
	public void onMessageCreate(MessageCreateEvent event)
	{
		_event = event;
		if (event.getMessageContent().equals(configSingleton.GetPrefix() + "status"))
		{
			if (configSingleton.GetSelectedServer() == null || configSingleton.GetSelectedServer().length() == 0)
			{
				event.getChannel().sendMessage("You need to decided which server to look at, use the command " + configSingleton.GetPrefix() + "setserver [ServerName] to define your server");
			}
			else
			{
				CallWeb();
			}
		}
	}
	
	// === SCRAP DATA ===
	public static void CallWeb()
	{
		Document doc = DownloadData();
		
		if (doc == null) return;
		
		Elements section = doc.select(".ags-ServerStatus-content-responses-response-server");
		ArrayList<Element> sections = new ArrayList<>(section);
		
		for (Element el : sections)
		{
			if (el.text().equals(configSingleton.GetSelectedServer()))
			{
				String title = el.select("div").attr("title");
				if (!status.equals(title))
				{
					status = title;
					System.out.println(title);
					_event.getChannel().sendMessage("@everyone");
					_event.getChannel().sendMessage(ChangeStatus(status));
				}
			}
		}
	}
	
	public static EmbedBuilder ChangeStatus(String status)
	{
		EmbedBuilder embed = null;
		if (status.toLowerCase().contains("online"))
		{
			embed = new EmbedBuilder().setTitle("Server Name").setDescription(configSingleton.GetSelectedServer()).addField("Server Status", status + " :white_check_mark:").setColor(Color.GREEN);
		}
		else if (status.toLowerCase().contains("full"))
		{
			embed = new EmbedBuilder().setTitle("Server Name").setDescription(configSingleton.GetSelectedServer()).addField("Server Status", status + " :thermometer:").setColor(Color.CYAN);
		}
		else if (status.toLowerCase().contains("down"))
		{
			embed = new EmbedBuilder().setTitle("Server Name").setDescription(configSingleton.GetSelectedServer()).addField("Server Status", status + " :x:").setColor(Color.RED);
		}
		else if (status.toLowerCase().contains("maintenance"))
		{
			embed = new EmbedBuilder().setTitle("Server Name").setDescription(configSingleton.GetSelectedServer()).addField("Server Status", status + " :wrench:").setColor(Color.YELLOW);
		}
		return embed;
	}
	
	public static Document DownloadData()
	{
		Document doc = null;
		try
		{
			doc = Jsoup.connect("https://www.newworld.com/en-us/support/server-status").get();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return doc;
	}
	
	public static void DownloadDataTimer()
	{
		// === UPDATE INFO ===
		Timer timer = new Timer();
		int begin = 10000; //timer starts after 10 second.
		int timeinterval = 300000; //timer executes every 5 minutes (300 000).
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				System.out.println("DownloadData !");
				DownloadData();
				CallWeb();
			}
		}, begin, timeinterval);
	}
}
