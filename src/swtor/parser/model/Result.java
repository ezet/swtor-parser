package swtor.parser.model;

public class Result {
	public enum MitigationType {
		GLANCE, MISS, DEFLECT, PARRY, NONE
	}

	public enum EffectType {
		INTERNAL, ENERGY, KINETIC, NONE
	}

	public enum ResultType {
		HEAL, DAMAGE, OTHER
	}

	private ResultType resultType;

	private int value;
	private boolean critical;

	private EffectType effectType;
	private long typeId;

	private MitigationType mitigationType;
	private long mitigateId;

	private boolean absorb;
	private int absorbValue;
	private long absorbId;

	private int threatDelta;

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

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

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
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
}
