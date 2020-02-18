package natc.view;

import java.util.Date;

public class PlayerInjuryView {

	private Date    datestamp;
	private String  year;
	private int     opponent;
	private String  opponent_abbrev;
	private boolean road_game;
	private int     game_id;
	private int     duration;
	
	public PlayerInjuryView() {
	
		this.datestamp       = null;
		this.year            = null;
		this.opponent        = 0;
		this.opponent_abbrev = null;
		this.road_game       = false;
		this.game_id         = 0;
		this.duration        = 0;
	}

	public String getDurationDsp() {
	
		switch ( this.duration ) {
		
		case   0: return "Game Only";
		case   1: return "1 Day";
		case   2: return "2 Days";
		case   3: return "3 Days";
		case   4: return "4 Days";
		case   5: return "5 Days";
		case   6: return "6 Days";
		case   7: return "1 Week";
		case  14: return "2 Weeks";
		case  21: return "3 Weeks";
		case  28: return "1 Month";
		case  56: return "2 Months";
		case  84: return "3 Months";
		case 112: return "4 Months";
		case 999: return "Season";
		}
		
		return null;
	}
	
	public int getOpponent() {
		return opponent;
	}

	public void setOpponent(int opponent) {
		this.opponent = opponent;
	}

	public String getOpponent_abbrev() {
		return opponent_abbrev;
	}

	public void setOpponent_abbrev(String opponentAbbrev) {
		opponent_abbrev = opponentAbbrev;
	}

	public boolean isRoad_game() {
		return road_game;
	}

	public void setRoad_game(boolean roadGame) {
		road_game = roadGame;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int gameId) {
		game_id = gameId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}
