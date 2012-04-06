package swtor.parser.model;

import swtor.parser.constant.EffectType;
import swtor.parser.constant.MitigationType;

public class Result implements Model {

	private int value;
	private boolean critical;

	private EffectType effectType = EffectType.NONE;
	private long effectId;

	private MitigationType mitigationType = MitigationType.NONE;
	private long mitigationId;

	private boolean absorb;
	private int absorbValue;
	private long absorbId;

	private int threatDelta;

	int getValue() {
		return value;
	}

	void setValue(int value) {
		this.value = value;
	}

	boolean isCritical() {
		return critical;
	}

	void setCritical(boolean critical) {
		this.critical = critical;
	}

	EffectType getEffectType() {
		return effectType;
	}

	void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

	long getEffectId() {
		return effectId;
	}

	void setEffectId(long effectId) {
		this.effectId = effectId;
	}

	MitigationType getMitigationType() {
		return mitigationType;
	}

	void setMitigationType(MitigationType mitigationType) {
		this.mitigationType = mitigationType;
	}

	long getMitigationId() {
		return mitigationId;
	}

	void setMitigationId(long mitigateId) {
		this.mitigationId = mitigateId;
	}

	boolean isAbsorb() {
		return absorb;
	}

	void setAbsorb(boolean absorb) {
		this.absorb = absorb;
	}

	int getAbsorbValue() {
		return absorbValue;
	}

	void setAbsorbValue(int absorbValue) {
		this.absorbValue = absorbValue;
	}

	long getAbsorbId() {
		return absorbId;
	}

	void setAbsorbId(long absorbId) {
		this.absorbId = absorbId;
	}

	int getThreatDelta() {
		return threatDelta;
	}

	void setThreatDelta(int threatDelta) {
		this.threatDelta = threatDelta;
	}

	boolean isMitigate() {
		return mitigationType != MitigationType.NONE;
	}

	public String toString() {
		return String.format("effType:%s, value:%s, crit:%s, mtgType:%s, absorbVal:%s, dThreat:%s", effectType, value, critical, mitigationType,
				absorbValue, threatDelta);
	}
}
