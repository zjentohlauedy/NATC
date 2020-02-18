package natc.view;

import java.text.DateFormat;
import java.util.Date;

public class GameView {

	private Integer game_id;
	
	private Date    date;
	
	private Integer home_team_id;
	private String  home_team;
	private Integer home_score;
	private Boolean home_win;
	
	private Integer road_team_id;
	private String  road_team;
	private Integer road_score;
	private Boolean road_win;
	
	private Boolean overtime;
	
	public GameView() {
	
		game_id      = null;
		date         = null;
		home_team_id = null;
		home_team    = null;
		home_score   = null;
		home_win     = null;
		road_team_id = null;
		road_team    = null;
		road_score   = null;
		road_win     = null;
		overtime     = null;
	}

	public String getDateDsp() {
	
		if ( this.date == null ) return "";
		
		DateFormat df = DateFormat.getDateInstance( DateFormat.SHORT );
		
		return df.format( this.date );
	}
	
	public Integer getGame_id() {
		return game_id;
	}

	public void setGame_id(Integer game_id) {
		this.game_id = game_id;
	}

	public Integer getHome_score() {
		return home_score;
	}

	public void setHome_score(Integer home_score) {
		this.home_score = home_score;
	}

	public String getHome_team() {
		return home_team;
	}

	public void setHome_team(String home_team) {
		this.home_team = home_team;
	}

	public Integer getHome_team_id() {
		return home_team_id;
	}

	public void setHome_team_id(Integer home_team_id) {
		this.home_team_id = home_team_id;
	}

	public Boolean getHome_win() {
		return home_win;
	}

	public void setHome_win(Boolean home_win) {
		this.home_win = home_win;
	}

	public Boolean getOvertime() {
		return overtime;
	}

	public void setOvertime(Boolean overtime) {
		this.overtime = overtime;
	}

	public Integer getRoad_score() {
		return road_score;
	}

	public void setRoad_score(Integer road_score) {
		this.road_score = road_score;
	}

	public String getRoad_team() {
		return road_team;
	}

	public void setRoad_team(String road_team) {
		this.road_team = road_team;
	}

	public Integer getRoad_team_id() {
		return road_team_id;
	}

	public void setRoad_team_id(Integer road_team_id) {
		this.road_team_id = road_team_id;
	}

	public Boolean getRoad_win() {
		return road_win;
	}

	public void setRoad_win(Boolean road_win) {
		this.road_win = road_win;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
