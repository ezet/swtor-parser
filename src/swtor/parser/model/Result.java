package swtor.parser.model;

import swtor.parser.constant.DamageType;
import swtor.parser.constant.MitigationType;
import swtor.parser.constant.EntryType;

public class Result {

	private int value;
	private boolean critical;

	private DamageType effectType = DamageType.NONE;
	private long effectId;

	private MitigationType mitigationType = MitigationType.NONE;
	private long mitigateId;

	private boolean absorb;
	private int absorbValue;
	private long absorbId;

	private int threatDelta;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	public DamageType getEffectType() {
		return effectType;
	}

	public void setEffectType(DamageType effectType) {
		this.effectType = effectType;
	}

	public long getEffectId() {
		return effectId;
	}

	public void setEffectId(long effectId) {
		this.effectId = effectId;
	}

	public MitigationType getMitigationType() {
		return mitigationType;
	}

	public void setMitigationType(MitigationType mitigationType) {
		this.mitigationType = mitigationType;
	}

	public long getMitigateId() {
		return mitigateId;
	}

	public void setMitigateId(long mitigateId) {
		this.mitigateId = mitigateId;
	}

	public boolean isAbsorb() {
		return absorb;
	}

	public void setAbsorb(boolean absorb) {
		this.absorb = absorb;
	}

	public int getAbsorbValue() {
		return absorbValue;
	}

	public void setAbsorbValue(int absorbValue) {
		this.absorbValue = absorbValue;
	}

	public long getAbsorbId() {
		return absorbId;
	}

	public void setAbsorbId(long absorbId) {
		this.absorbId = absorbId;
	}

	public int getThreatDelta() {
		return threatDelta;
	}

	public void setThreatDelta(int threatDelta) {
		this.threatDelta = threatDelta;
	}
	
	public boolean isMitigate() {
		return mitigationType != MitigationType.NONE;
	}
	
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s, %s", effectType, value, critical, effectType, mitigationType, absorbValue, threatDelta);
	}
}
