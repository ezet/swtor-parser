package swtor.parser.model;

public class Actor implements Model {

	private long gameId;
	private String name;
	private boolean player;
	private boolean companion;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public boolean isCompanion() {
		return companion;
	}

	public void setCompanion(boolean companion) {
		this.companion = companion;
	}

	public String toString() {
		return name;
	}

}
