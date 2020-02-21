package natc.data;

public class Injury {

	private int game_id;
	private int player_id;
	private int team_id;
	private int duration; // in days
	
	public Injury() {
	
		this.game_id   = 0;
		this.player_id = 0;
		this.team_id   = 0;
		this.duration  = 0;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int gameId) {
		game_id = gameId;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int playerId) {
		player_id = playerId;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int teamId) {
		team_id = teamId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
