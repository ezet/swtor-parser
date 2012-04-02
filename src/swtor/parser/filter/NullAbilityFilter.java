package swtor.parser.filter;

import swtor.parser.model.Ability;
import swtor.parser.model.LogEntry;

public class NullAbilityFilter implements InputFilter {

	@Override
	public boolean process(LogEntry entry) {
		Ability ability = entry.getAbility();
		if (ability.getName() == null) {
			ability.setName(entry.getEvent().getName());
		} else if (ability.getName().isEmpty()) {
			ability.setName("Unknown");
		}
		return true;
	}
	
	
	

}
