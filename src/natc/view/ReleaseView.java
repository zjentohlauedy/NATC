package natc.view;

public class ReleaseView {

	private int     player_id;
	private String  first_name;
	private String  last_name;
	private int     team_id;
	private String  team_abbrev;
	private int     age;
	private int     seasons_played;
	private boolean rookie;
	private int     draft_pick;
	
	public ReleaseView() {
	
		this.player_id      = 0;
		this.first_name     = null;
		this.last_name      = null;
		this.team_id        = 0;
		this.team_abbrev    = null;
		this.age            = 0;
		this.seasons_played = 0;
		this.rookie         = false;
		this.draft_pick     = 0;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}
	
}
