package swtor.parser.model;

import swtor.parser.constant.EventType;

public class CombatEvent implements Model {

	private EventType type;
	private long typeId;
	private String name;
	private long gameId;

	EventType getType() {
		return type;
	}

	void setType(EventType type) {
		this.type = type;
	}

	long getTypeId() {
		return typeId;
	}

	void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	long getId() {
		return gameId;
	}

	void setId(long gameId) {
		this.gameId = gameId;
	}

	public String toString() {
		return String.format("%s, %s", type, name);
	}

}
