package swtor.parser.model;

public class CombatEvent {

	public enum EventType {
		REMOVE_EFFECT, APPLY_EFFECT, SPEND, RESTORE, OTHER;
		
		public static EventType valueOfString(String string) {
			EventType type = OTHER;
			if (string.equals("RemoveEffect")) {
				type = REMOVE_EFFECT;
			} else if (string.equals("ApplyEffect")) {
				type = APPLY_EFFECT;
			} else if (string.equals("Spend")) {
				type = SPEND;
			} else if (string.equals("RESTORE")) {
				type = RESTORE;
			}
			return type;
		}
	}

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
