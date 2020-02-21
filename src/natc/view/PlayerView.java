package natc.view;

import java.text.DecimalFormat;

public class PlayerView {

	public static final int STATUS_RETIRED  = 1;
	public static final int STATUS_RELEASED = 2;
	public static final int STATUS_SIGNED   = 3;
	
	public static final String NUMBER_FORMAT = "#,##0.#";
	
	private int    status;
	private int    player_id;
	private String first_name;
	private String last_name;
	private int    former_team_id;
	private String former_team_abbrev;
	private int    age;
	private int    seasons_played;
	private double goals;
	private double assists;
	private double stops;
	private double steals;
	private double psm;
	private double points;
	private int    platinum_count;
	private int    gold_count;
	private int    silver_count;
	private int    allstar_count;
	
	public PlayerView() {
	
		this.status             = 0;
		this.player_id          = 0;
		this.first_name         = null;
		this.last_name          = null;
		this.former_team_id     = 0;
		this.former_team_abbrev = null;
		this.age                = 0;
		this.seasons_played     = 0;
		this.goals              = 0.0;
		this.assists            = 0.0;
		this.stops              = 0.0;
		this.steals             = 0.0;
		this.psm                = 0.0;
		this.points             = 0.0;
		this.platinum_count     = 0;
		this.gold_count         = 0;
		this.silver_count       = 0;
		this.allstar_count      = 0;
	}

	public String getPointsDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.points );
	}
	
	public String getGoalsDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.goals );
	}

	public String getAssistsDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.assists );
	}

	public String getStopsDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.stops );
	}

	public String getStealsDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.steals );
	}

	public String getPsmDsp() {
		
		DecimalFormat df = new DecimalFormat( NUMBER_FORMAT );
		
		return df.format( this.psm );
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasons_played) {
		this.seasons_played = seasons_played;
	}

	public double getGoals() {
		return goals;
	}

	public void setGoals(double goals) {
		this.goals = goals;
	}

	public double getAssists() {
		return assists;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}

	public double getStops() {
		return stops;
	}

	public void setStops(double stops) {
		this.stops = stops;
	}

	public double getSteals() {
		return steals;
	}

	public void setSteals(double steals) {
		this.steals = steals;
	}

	public double getPsm() {
		return psm;
	}

	public void setPsm(double psm) {
		this.psm = psm;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public int getPlatinum_count() {
		return platinum_count;
	}

	public void setPlatinum_count(int platinumCount) {
		platinum_count = platinumCount;
	}

	public int getGold_count() {
		return gold_count;
	}

	public void setGold_count(int goldCount) {
		gold_count = goldCount;
	}

	public int getSilver_count() {
		return silver_count;
	}

	public void setSilver_count(int silverCount) {
		silver_count = silverCount;
	}

	public int getAllstar_count() {
		return allstar_count;
	}

	public void setAllstar_count(int allstarCount) {
		allstar_count = allstarCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFormer_team_id() {
		return former_team_id;
	}

	public void setFormer_team_id(int formerTeamId) {
		former_team_id = formerTeamId;
	}

	public String getFormer_team_abbrev() {
		return former_team_abbrev;
	}

	public void setFormer_team_abbrev(String formerTeamAbbrev) {
		former_team_abbrev = formerTeamAbbrev;
	}
	
}
