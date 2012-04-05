package swtor.parser.model;

import swtor.parser.constant.EventType;

public class CombatEvent implements Model {

	private EventType type;
	private long typeId;
	private String name;
	private long gameId;

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String toString() {
		return String.format("%s, %s", type, name);
	}

}
