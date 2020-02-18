package natc.view;

import java.text.DecimalFormat;

public class TeamPlayerView {

	private int     player_id;
	
	private String  first_name;
	private String  last_name;
	
	private int     age;
	
	private boolean rookie;
	private boolean injured;
	private int     award;
	private int     allstar_team_id;
	
	// Allstar team members
	private int     team_id;
	private String  team_abbrev;
	
	private double  rating;
	private double  adjustedRating;
	
	private int     games;
	private int     games_started;
	private int     time_per_game;
	private int     points;
	private int     goals;
	private int     assists;
	private int     stops;
	private int     steals;
	private int     psm;
	
	public TeamPlayerView() {
	
		this.player_id       = 0;
		this.first_name      = null;
		this.last_name       = null;
		this.age             = 0;
		this.rookie          = false;
		this.injured         = false;
		this.award           = 0;
		this.allstar_team_id = 0;
		this.team_id         = 0;
		this.team_abbrev     = null;
		this.rating          = 0.0;
		this.adjustedRating  = 0.0;
		this.games           = 0;
		this.games_started   = 0;
		this.time_per_game   = 0;
		this.points          = 0;
		this.goals           = 0;
		this.assists         = 0;
		this.stops           = 0;
		this.steals          = 0;
		this.psm             = 0;
	}

	public String getTimePerGameDsp() {
	
		DecimalFormat df = new DecimalFormat( "00" );
		
		return df.format( this.time_per_game / 60 ) + ":" + df.format( this.time_per_game % 60 );
	}
	
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getAllstar_team_id() {
		return allstar_team_id;
	}

	public void setAllstar_team_id(int allstar_team_id) {
		this.allstar_team_id = allstar_team_id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getAdjustedRating() {
		return adjustedRating;
	}

	public void setAdjustedRating(double adjustedRating) {
		this.adjustedRating = adjustedRating;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getPsm() {
		return psm;
	}

	public void setPsm(int psm) {
		this.psm = psm;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getTime_per_game() {
		return time_per_game;
	}

	public void setTime_per_game(int timePerGame) {
		time_per_game = timePerGame;
	}

	public boolean isInjured() {
		return injured;
	}

	public void setInjured(boolean injured) {
		this.injured = injured;
	}

	public int getGames_started() {
		return games_started;
	}

	public void setGames_started(int gamesStarted) {
		games_started = gamesStarted;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int teamId) {
		team_id = teamId;
	}

	public String getTeam_abbrev() {
		return team_abbrev;
	}

	public void setTeam_abbrev(String teamAbbrev) {
		team_abbrev = teamAbbrev;
	}
	
}
