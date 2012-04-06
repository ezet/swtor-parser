package swtor.parser.model;

public class Actor implements Model {

	private long gameId;
	private String name;
	private boolean player;
	private boolean companion;

	long getId() {
		return gameId;
	}

	void setId(long gameId) {
		this.gameId = gameId;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	boolean isPlayer() {
		return player;
	}

	void setPlayer(boolean player) {
		this.player = player;
	}

	boolean isCompanion() {
		return companion;
	}

	void setCompanion(boolean companion) {
		this.companion = companion;
	}

	public String toString() {
		return name;
	}

}
