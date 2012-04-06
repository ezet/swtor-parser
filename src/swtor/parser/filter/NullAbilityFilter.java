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

	@Override
	public void onLogStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogEnd() {
		// TODO Auto-generated method stub
		
	}

}
