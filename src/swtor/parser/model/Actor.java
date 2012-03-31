package swtor.parser.model;

public class Actor {

	private long gameId;
	private String name;
	private boolean npc;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId){
		this.gameId = gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNpc() {
		return npc;
	}

	public void setNpc(boolean npc) {
		this.npc = npc;
	}
	
	public String toString() {
		return name;
	}

}
