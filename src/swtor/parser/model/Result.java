package swtor.parser.model;

import swtor.parser.constant.EffectType;
import swtor.parser.constant.MitigationType;

public class Result implements Model {

	private int value;
	private boolean critical;

	private EffectType effectType = EffectType.NONE;
	private long effectGameId;

	private MitigationType mitigationType = MitigationType.NONE;
	private long mitigateGameId;

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

	public EffectType getEffectType() {
		return effectType;
	}

	public void setDamageType(EffectType effectType) {
		this.effectType = effectType;
	}

	public long getEffectGameId() {
		return effectGameId;
	}

	public void setEffectGameId(long effectId) {
		this.effectGameId = effectId;
	}

	public MitigationType getMitigationType() {
		return mitigationType;
	}

	public void setMitigationType(MitigationType mitigationType) {
		this.mitigationType = mitigationType;
	}

	public long getMitigateGameId() {
		return mitigateGameId;
	}

	public void setMitigateGameId(long mitigateId) {
		this.mitigateGameId = mitigateId;
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
		return String.format("effType:%s, value:%s, crit:%s, mtgType:%s, absorbVal:%s, dThreat:%s", effectType, value, critical, mitigationType,
				absorbValue, threatDelta);
	}
}
