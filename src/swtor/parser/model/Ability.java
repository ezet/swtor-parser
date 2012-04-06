package swtor.parser.model;

public class Ability implements Model {

	private long gameId;
	private String name;

	long getGameId() {
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
	
	public String toString() {
		return "ability: " + name;
	}

}
