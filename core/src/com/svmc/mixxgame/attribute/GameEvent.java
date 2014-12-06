package com.svmc.mixxgame.attribute;

import com.svmc.mixxgame.attribute.EventType;

public interface GameEvent {
	public void broadcastEvent(EventType type, float x, float y);
}
