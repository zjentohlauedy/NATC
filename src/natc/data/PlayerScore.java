package natc.data;

public class PlayerScore {

	public static final int PLATINUM_AWARD = 3;
	public static final int GOLD_AWARD     = 2;
	public static final int SILVER_AWARD   = 1;
	
	private int player_id;
	private int conference;
	private int division;
	private int score;
	private int award;
	
	public PlayerScore() {
	
		this.player_id  = 0;
		this.conference = 0;
		this.division   = 0;
		this.score      = 0;
		this.award      = 0;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
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

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
