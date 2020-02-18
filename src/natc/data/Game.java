package natc.data;

import java.util.Date;

public class Game {

	private int     game_id;
	
	private int     home_team;
	private int     road_team;
	
	private Score   home_score;
	private Score   road_score;
	
	private boolean overtime;
	
	private Date    datestamp;
	
	public Game() {
	
		game_id    = 0;
		home_team  = 0;
		road_team  = 0;
		home_score = null;
		road_score = null;
		overtime   = false;
		datestamp  = null;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public Score getHome_score() {
		return home_score;
	}

	public void setHome_score(Score home_score) {
		this.home_score = home_score;
	}

	public int getHome_team() {
		return home_team;
	}

	public void setHome_team(int home_team) {
		this.home_team = home_team;
	}

	public boolean isOvertime() {
		return overtime;
	}

	public void setOvertime(boolean overtime) {
		this.overtime = overtime;
	}

	public Score getRoad_score() {
		return road_score;
	}

	public void setRoad_score(Score road_score) {
		this.road_score = road_score;
	}

	public int getRoad_team() {
		return road_team;
	}

	public void setRoad_team(int road_team) {
		this.road_team = road_team;
	}

}
