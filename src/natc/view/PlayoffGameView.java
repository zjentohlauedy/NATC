package natc.view;

public class PlayoffGameView {

	int     playoff_round;
	int     team_id;
	int     game_id;
	int     game_num;
	int     conference;
	int     division;
	int     division_rank;
	boolean road;
	boolean win;
	
	public PlayoffGameView() {
	
		this.playoff_round = 0;
		this.team_id       = 0;
		this.game_id       = 0;
		this.game_num      = 0;
		this.conference    = 0;
		this.division      = 0;
		this.division_rank = 0;
		this.road          = false;
		this.win           = false;
	}

	public int getPlayoff_round() {
		return playoff_round;
	}

	public void setPlayoff_round(int playoffRound) {
		playoff_round = playoffRound;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int teamId) {
		team_id = teamId;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int gameId) {
		game_id = gameId;
	}

	public int getGame_num() {
		return game_num;
	}

	public void setGame_num(int gameNum) {
		game_num = gameNum;
	}

	public int getConference() {
		return conference;
	}

	public void setConference(int conference) {
		this.conference = conference;
	}

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public int getDivision_rank() {
		return division_rank;
	}

	public void setDivision_rank(int divisionRank) {
		division_rank = divisionRank;
	}

	public boolean isRoad() {
		return road;
	}

	public void setRoad(boolean road) {
		this.road = road;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
}
