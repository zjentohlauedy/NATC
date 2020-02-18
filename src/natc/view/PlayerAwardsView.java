package natc.view;

public class PlayerAwardsView {

	private int     player_id;
	private String  first_name;
	private String  last_name;
	private String  team_abbrev;
	private int     team_id;
	private int     conference;
	private int     division;
	private int     points;
	private int     goals;
	private int     assists;
	private int     stops;
	private int     steals;
	private int     psm;
	private int     award;
	private boolean rookie;
	
	public PlayerAwardsView() {
	
		this.player_id   = 0;
		this.first_name  = null;
		this.last_name   = null;
		this.team_abbrev = null;
		this.team_id     = 0;
		this.conference  = 0;
		this.division    = 0;
		this.points      = 0;
		this.goals       = 0;
		this.assists     = 0;
		this.stops       = 0;
		this.steals      = 0;
		this.psm         = 0;
		this.award       = 0;
		this.rookie      = false;
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

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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
	
}
