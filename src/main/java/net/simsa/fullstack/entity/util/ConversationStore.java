package net.simsa.fullstack.entity.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ConversationScoped;

/**
 * Storage for conversation-scope objects. See https://www.42lines.net/2011/11/29/leveraging-conversations/
 */
@ConversationScoped
public class ConversationStore implements Serializable {
	private Map<UUID, Object> store = new HashMap<UUID, Object>();

	public UUID put(Object value)
	{
		UUID key = UUID.randomUUID();
		store.put(key, value);
		return key;
	}

	public Object get(UUID key)
	{
		return store.get(key);
	}

	public Object remove(UUID key)
	{
		return store.remove(key);
	}
}