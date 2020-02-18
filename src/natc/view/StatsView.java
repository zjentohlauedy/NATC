package natc.view;

public class StatsView {

	public static final int HEADER         = 0;
	public static final int TEAMBYGAME     = 1;
	public static final int TEAMBYSEASON   = 2;
	public static final int TEAMBYHISTORY  = 3;
	public static final int PLAYERBYGAME   = 4;
	public static final int PLAYERBYSEASON = 5;
	public static final int PLAYERBYCAREER = 6;
	
	public static final String KEY_SCORE         =  "stats.label.score";
	public static final String KEY_ATTEMPTS      =  "stats.label.attempts";
	public static final String KEY_GOALS         =  "stats.label.goals";
	public static final String KEY_ASSISTS       =  "stats.label.assists";
	public static final String KEY_OFFENSE       =  "stats.label.offense";
	public static final String KEY_TURNOVERS     =  "stats.label.turnovers";
	public static final String KEY_STOPS         =  "stats.label.stops";
	public static final String KEY_STEALS        =  "stats.label.steals";
	public static final String KEY_PENALTIES     =  "stats.label.penalties";
	public static final String KEY_PSA           =  "stats.label.psa";
	public static final String KEY_PSM           =  "stats.label.psm";
	public static final String KEY_OT_PSA        =  "stats.label.ot_psa";
	public static final String KEY_OT_PSM        =  "stats.label.ot_psm";
	public static final String KEY_SCORE_PCT     =  "stats.label.score_pct";
	public static final String KEY_PS_PCT        =  "stats.label.ps_pct";
	public static final String KEY_OT_PS_PCT     =  "stats.label.ot_ps_pct";
	public static final String KEY_POSSESSIONS   =  "stats.label.poss";
	public static final String KEY_AVG_TOP       =  "stats.label.avg_top";
	public static final String KEY_TIME_PER_GAME =  "stats.label.time_per_game";
	
	public static final String KEY_PLAYER        = "stats.label.player";
	public static final String KEY_TEAM          = "stats.label.team";
	public static final String KEY_YEAR          = "stats.label.year";
	public static final String KEY_OPPONENT      = "stats.label.opponent";
	public static final String KEY_SEASONS       = "stats.label.seasons";
	public static final String KEY_UNUSED        = "stats.label.unused";

	private int     type;
	
	private String  headingKey1;
	private String  headingKey2;
	private String  headingKey3;
	private String  headingKey4;
	
	private String  stat;
	private int     player_id;
	private String  first_name;
	private String  last_name;
	private int     team_id;
	private String  team_abbrev;
	private String  year;
	private int     game_id;
	private boolean road;
	private int     opponent_id;
	private String  opponent_abbrev;
	private String  first_year;
	private String  last_year;
	private int     seasons_played;
	
	public StatsView( int type ) {
	
		this.type            = type;
		
		this.headingKey1     = null;
		this.headingKey2     = null;
		this.headingKey3     = null;
		this.headingKey4     = null;
		
		this.stat            = null;
		this.player_id       = 0;
		this.first_name      = null;
		this.last_name       = null;
		this.team_id         = 0;
		this.team_abbrev     = null;
		this.year            = null;
		this.game_id         = 0;
		this.road            = false;
		this.opponent_id     = 0;
		this.opponent_abbrev = null;
		this.first_year      = null;
		this.last_year       = null;
		this.seasons_played  = 0;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public String getHeadingKey1() {
		return headingKey1;
	}

	public void setHeadingKey1(String headingKey1) {
		this.headingKey1 = headingKey1;
	}

	public String getHeadingKey2() {
		return headingKey2;
	}

	public void setHeadingKey2(String headingKey2) {
		this.headingKey2 = headingKey2;
	}

	public String getHeadingKey3() {
		return headingKey3;
	}

	public void setHeadingKey3(String headingKey3) {
		this.headingKey3 = headingKey3;
	}

	public String getHeadingKey4() {
		return headingKey4;
	}

	public void setHeadingKey4(String headingKey4) {
		this.headingKey4 = headingKey4;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getOpponent_abbrev() {
		return opponent_abbrev;
	}

	public void setOpponent_abbrev(String opponent_abbrev) {
		this.opponent_abbrev = opponent_abbrev;
	}

	public int getOpponent_id() {
		return opponent_id;
	}

	public void setOpponent_id(int opponent_id) {
		this.opponent_id = opponent_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public boolean isRoad() {
		return road;
	}

	public void setRoad(boolean road) {
		this.road = road;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getTeam_abbrev() {
		return team_abbrev;
	}

	public void setTeam_abbrev(String team_abbrev) {
		this.team_abbrev = team_abbrev;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFirst_year() {
		return first_year;
	}

	public void setFirst_year(String first_year) {
		this.first_year = first_year;
	}

	public String getLast_year() {
		return last_year;
	}

	public void setLast_year(String last_year) {
		this.last_year = last_year;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasons_played) {
		this.seasons_played = seasons_played;
	}

}
