package swtor.parser.filter;

import swtor.parser.model.LogEntry;

public class NullAbilityFilter implements InputFilter {

	@Override
	public boolean process(LogEntry entry) {
		if (entry.getAbility() == null) {
			entry.setAbility(entry.getEventName());
		} else if (entry.getAbility().isEmpty()) {
			entry.setAbility("Unknown");
		}
		return true;
	}

}
