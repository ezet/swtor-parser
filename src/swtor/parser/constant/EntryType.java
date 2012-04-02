package swtor.parser.constant;

public enum EntryType {
	ENTER_COMBAT, EXIT_COMBAT, DAMAGE, HEAL, OTHER;

	public static EntryType valueOfString(String string) {
		EntryType type = OTHER;
		if (string.equals("EnterCombat")) {
			type = ENTER_COMBAT;
		} else if (string.equals("ExitCombat")) {
			type = EXIT_COMBAT;
		} else if (string.equals("Damage")) {
			type = DAMAGE;
		} else if (string.equals("Heal")) {
			type = HEAL;
		}
		return type;
	}
}