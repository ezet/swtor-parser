package swtor.parser.constant;

import java.util.EnumSet;

public enum EventType {
	REMOVE_EFFECT(836045448945478L), APPLY_EFFECT(836045448945477L), SPEND(836045448945473L), RESTORE(836045448945476L), EVENT(
			836045448945472L), UNKNOWN(0L);

	private long id;
	private String name;

	private EventType(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static EventType valueOfId(long id, String name) {
		for (final EventType type : EnumSet.allOf(EventType.class)) {
			if (type.id == id) {
				type.id = id;
				type.name = name;
				return type;
			}
		}
		// TODO add exception if new entry is found?
		EventType type = UNKNOWN;
		type.id = id;
		return type;
	}

	public String toString() {
		return name;
	}
}