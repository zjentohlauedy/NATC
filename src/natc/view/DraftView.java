package natc.view;

public class DraftView {

	private int    team_id;
	private String location;
	private String team_name;
	private int    player_id;
	private String first_name;
	private String last_name;
	private int    draft_pick;
	
	public DraftView() {
	
		this.team_id    = 0;
		this.location   = null;
		this.team_name  = null;
		this.player_id  = 0;
		this.first_name = null;
		this.last_name  = null;
		this.draft_pick = 0;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	
}
