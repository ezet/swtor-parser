package swtor.parser.model;

public class Ability implements Model {

	private long gameId;
	private String name;

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
	
	public String toString() {
		return "ability: " + name;
	}

}
