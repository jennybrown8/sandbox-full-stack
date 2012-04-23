package net.simsa.fullstack.model;

import javax.enterprise.context.ApplicationScoped;

import org.apache.wicket.util.time.Time;

@ApplicationScoped
public class Clock {
	public String getTime()
	{
		return Time.now().toString("HH:mm:ss");
	}
}