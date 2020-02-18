package natc.view;

public class FreeAgentView {

	public static final int STATUS_RELEASED = 0;
	public static final int STATUS_SIGNED   = 1;
	
	private int    status;
	private int    player_id;
	private String first_name;
	private String last_name;
	private int    old_team_id;
	private String old_team_abbrev;
	private int    new_team_id;
	private String new_team_abbrev;
	private double rating;
	private double adjustedRating;
	private int    age;
	private int    seasons_played;
	
	public FreeAgentView() {
	
		this.status          = 0;
		this.player_id       = 0;
		this.first_name      = null;
		this.last_name       = null;
		this.old_team_id     = 0;
		this.old_team_abbrev = null;
		this.new_team_id     = 0;
		this.new_team_abbrev = null;
		this.rating          = 0.0;
		this.adjustedRating  = 0.0;
		this.age             = 0;
		this.seasons_played  = 0;
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

	public String getNew_team_abbrev() {
		return new_team_abbrev;
	}

	public void setNew_team_abbrev(String new_team_abbrev) {
		this.new_team_abbrev = new_team_abbrev;
	}

	public int getNew_team_id() {
		return new_team_id;
	}

	public void setNew_team_id(int new_team_id) {
		this.new_team_id = new_team_id;
	}

	public String getOld_team_abbrev() {
		return old_team_abbrev;
	}

	public void setOld_team_abbrev(String old_team_abbrev) {
		this.old_team_abbrev = old_team_abbrev;
	}

	public int getOld_team_id() {
		return old_team_id;
	}

	public void setOld_team_id(int old_team_id) {
		this.old_team_id = old_team_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasonsPlayed) {
		seasons_played = seasonsPlayed;
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
	
}
