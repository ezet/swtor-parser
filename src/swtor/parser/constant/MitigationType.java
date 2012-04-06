package swtor.parser.constant;

import java.util.EnumSet;

public enum MitigationType {
	MISS(836045448945502L), GLANCE(836045448945509L), DODGE(836045448945505L), DEFLECT(836045448945508L), PARRY(
			836045448945503L), IMMUNE(836045448945506L), RESIST(836045448945507L), NONE(0L), UNKNOWN(0L);

	private long id;
	private String localName;

	private MitigationType(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getLocalName() {
		return localName;
	}

	public static MitigationType valueOfId(long id, String name) {
		for (final MitigationType type : EnumSet.allOf(MitigationType.class)) {
			if (type.id == id) {
				type.localName = name;
				return type;
			}
		}
		// TODO add exception if a new entry is found ?
		MitigationType type = UNKNOWN;
		type.id = id;
		return type;
	}
	
	public String toLocalString() {
		return localName;
	}
}