package swtor.parser.constant;

import java.util.EnumSet;

public enum EffectType {
	INTERNAL(836045448940876L), ENERGY(836045448940874L), KINETIC(836045448940873L), ELEMENTAL(836045448940875L), NONE(
			0L), UNKNOWN(0L);

	private long id;
	private String name;

	private EffectType(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static EffectType valueOfId(long id, String name) {
		for (final EffectType type : EnumSet.allOf(EffectType.class)) {
			if (type.id == id) {
				type.id = id;
				type.name = name;
				return type;
			}
		}
		// TODO add exception if a new entry is found ?
		EffectType type = UNKNOWN;
		type.id = id;
		return type;
	}

	@Override
	public String toString() {
		return name;
	}

}