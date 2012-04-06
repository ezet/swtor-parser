package swtor.parser.constant;

import java.util.EnumSet;

public enum EntryType {
	ENTER_COMBAT(836045448945489L), EXIT_COMBAT(836045448945490L), DAMAGE(836045448945501L), HEAL(836045448945500L), OTHER(
			0L);

	private long id;
	private String localName;

	private EntryType(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getLocalName() {
		return localName;
	}

	public static EntryType valueOfId(long id, String name) {
		for (final EntryType type : EnumSet.allOf(EntryType.class)) {
			if (type.id == id) {
				type.localName = name;
				return type;
			}
		}
		// TODO add exception if a new entry is found ?
		EntryType type = OTHER;
		type.id = id;
		return type;
	}
	
	public String toLocalString() {
		return localName;
	}
}