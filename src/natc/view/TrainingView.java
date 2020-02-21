package natc.view;

public class TrainingView {

	private int    player_id;
	private String first_name;
	private String last_name;
	private int    team_id;
	private String team_abbrev;
	private int    seasons_played;
	private int    draft_pick;
	
	public TrainingView() {
	
		player_id      = 0;
		first_name     = null;
		last_name      = null;
		team_id        = 0;
		team_abbrev    = null;
		seasons_played = 0;
		draft_pick     = 0;
	}

	public int getDraft_pick() {
		return draft_pick;
	}

	public void setDraft_pick(int draft_pick) {
		this.draft_pick = draft_pick;
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
	
}
