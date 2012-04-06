package swtor.parser.model;

import java.text.DateFormat;
import java.util.Calendar;

import swtor.parser.constant.EffectType;
import swtor.parser.constant.EntryType;
import swtor.parser.constant.EventType;
import swtor.parser.constant.MitigationType;

public class LogEntry implements Model {

	private EntryType type;
	private final long lineNumber;
	private Calendar time = Calendar.getInstance();
	private final Actor source = new Actor();
	private final Actor target = new Actor();
	private final Ability ability = new Ability();
	private final CombatEvent event = new CombatEvent();
	private final Result result = new Result();

	public LogEntry(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public long getLineNumber() {
		return lineNumber;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public long getAbilityId() {
		return ability.getGameId();
	}

	public void setAbilityId(long id) {
		ability.setId(id);
	}

	public String getAbility() {
		return ability.getName();
	}

	public void setAbility(String name) {
		ability.setName(name);
	}

	public EventType getEventType() {
		return event.getType();
	}

	public void setEventType(EventType type) {
		event.setType(type);
	}

	public long getEventTypeId() {
		return event.getTypeId();
	}

	public void setEventTypeId(long typeId) {
		event.setTypeId(typeId);
	}

	public String getEventName() {
		return event.getName();
	}

	public void setEventName(String name) {
		event.setName(name);
	}

	public long getEventId() {
		return event.getId();
	}

	public void setEventId(long gameId) {
		event.setId(gameId);
	}

	public int getValue() {
		return result.getValue();
	}

	public void setValue(int value) {
		result.setValue(value);
	}

	public void setCritical(boolean critical) {
		result.setCritical(critical);
	}

	public EffectType getEffectType() {
		return result.getEffectType();
	}

	public void setEffectType(EffectType effectType) {
		result.setEffectType(effectType);
	}

	public long getEffectId() {
		return result.getEffectId();
	}

	public void setEffectId(long effectId) {
		result.setEffectId(effectId);
	}

	public MitigationType getMitigationType() {
		return result.getMitigationType();
	}

	public void setMitigationType(MitigationType mitigationType) {
		result.setMitigationType(mitigationType);
	}

	public long getMitigationId() {
		return result.getMitigationId();
	}

	public void setMitigationId(long mitigationId) {
		result.setMitigationId(mitigationId);
	}

	public void setAbsorb(boolean absorb) {
		result.setAbsorb(absorb);
	}

	public int getAbsorbValue() {
		return result.getAbsorbValue();
	}

	public void setAbsorbValue(int absorbValue) {
		result.setAbsorbValue(absorbValue);
	}

	public long getAbsorbId() {
		return result.getAbsorbId();
	}

	public void setAbsorbId(long absorbId) {
		result.setAbsorbId(absorbId);
	}

	public int getThreatDelta() {
		return result.getThreatDelta();
	}

	public void setThreatDelta(int threatDelta) {
		result.setThreatDelta(threatDelta);
	}

	public boolean isCritical() {
		return result.isCritical();
	}

	public boolean isAbsorb() {
		return result.isAbsorb();
	}

	public boolean isMitigate() {
		return result.isMitigate();
	}

	public long getSourceId() {
		return source.getId();
	}

	public void setSourceId(long id) {
		source.setId(id);
	}

	public String getSource() {
		return source.getName();
	}

	public void setSource(String name) {
		source.setName(name);
	}

	public boolean sourceIsPlayer() {
		return source.isPlayer();
	}

	public void setSourceIsPlayer(boolean player) {
		source.setPlayer(player);
	}

	public boolean sourceIsCompanion() {
		return source.isCompanion();
	}

	public void setSourceIsCompanion(boolean companion) {
		source.setCompanion(companion);
	}

	public long getTargetId() {
		return target.getId();
	}

	public void setTargetId(long id) {
		target.setId(id);
	}

	public String getTarget() {
		return target.getName();
	}

	public void setTarget(String name) {
		target.setName(name);
	}

	public boolean targetIsPlayer() {
		return target.isPlayer();
	}

	public void setTargetIsPlayer(boolean player) {
		target.setPlayer(player);
	}

	public boolean targetIsCompanion() {
		return target.isCompanion();
	}

	public void setTargetIsCompanion(boolean companion) {
		target.setCompanion(companion);
	}

	public String toString() {
		return String.format("ln:%s, time:%s, src:%s, trgt:%s, %s, %s, %s", lineNumber, DateFormat.getTimeInstance()
				.format(time.getTime()), source, target, ability, event, result);
	}

}
