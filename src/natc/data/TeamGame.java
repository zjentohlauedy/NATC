package natc.data;

import java.util.Date;

public class TeamGame {

	public static final int gt_Preseason     = 1;
	public static final int gt_RegularSeason = 2;
	public static final int gt_Postseason    = 3;
	public static final int gt_Allstar       = 4;
	
	private int     game_id;
	
	private String  year;
	private Date    datestamp;
	
	private int     type;
	private int     playoff_round;
	private int     team_id;
	private int     opponent;

	private Score   score;
	
	private boolean road;
	private boolean overtime;
	private boolean win;
	
	private double[] pb_table;
	
	public TeamGame() {
	
		this.game_id       = 0;
		this.year          = null;
		this.datestamp     = null;
		this.type          = 0;
		this.playoff_round = 0;
		this.team_id       = 0;
		this.opponent      = 0;
		this.score         = null;
		this.road          = false;
		this.overtime      = false;
		this.win           = false;
		this.pb_table      = null;
	}

	public int pickEvent() {
	
		double x = Math.random();
		
		for ( int i = 0; i < this.pb_table.length; ++i ) {
			
			if ( (x -= this.pb_table[i]) <= 0 ) return i;
		}
		
		return -1;
	}
	
	public void updateScores( int points, int period, boolean overtime ) {
	
		if ( score == null ) return;
		
		score.setTotal_score( score.getTotal_score() + points );
		
		if ( overtime ) {
		
			score.setOvertime_score( score.getOvertime_score() + points );
		}
		else {
			
			switch ( period ) {
			
			case 1: score.setPeriod1_score( score.getPeriod1_score() + points ); break;
			case 2: score.setPeriod2_score( score.getPeriod2_score() + points ); break;
			case 3: score.setPeriod3_score( score.getPeriod3_score() + points ); break;
			case 4: score.setPeriod4_score( score.getPeriod4_score() + points ); break;
			case 5: score.setPeriod5_score( score.getPeriod5_score() + points ); break;
			}
		}
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

	public int getOpponent() {
		return opponent;
	}

	public void setOpponent(int opponent) {
		this.opponent = opponent;
	}

	public boolean isOvertime() {
		return overtime;
	}

	public void setOvertime(boolean overtime) {
		this.overtime = overtime;
	}

	public boolean isRoad() {
		return road;
	}

	public void setRoad(boolean road) {
		this.road = road;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public static int getGt_Postseason() {
		return gt_Postseason;
	}

	public static int getGt_Preseason() {
		return gt_Preseason;
	}

	public static int getGt_RegularSeason() {
		return gt_RegularSeason;
	}

	public int getPlayoff_round() {
		return playoff_round;
	}

	public void setPlayoff_round(int playoff_round) {
		this.playoff_round = playoff_round;
	}

	public double[] getPb_table() {
		return pb_table;
	}

	public void setPb_table(double[] pb_table) {
		this.pb_table = pb_table;
	}

}
