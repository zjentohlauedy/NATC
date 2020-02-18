package natc.view;

public class AllstarView {

	private int     player_id;
	private String  first_name;
	private String  last_name;
	private boolean rookie;
	
	private int     team_id;
	private String  team_abbrev;
	
	public AllstarView() {
	
		this.player_id   = 0;
		this.first_name  = null;
		this.last_name   = null;
		this.rookie      = false;
		this.team_id     = 0;
		this.team_abbrev = null;
	}

	public String getTeam_abbrev() {
		return team_abbrev;
	}

	public void setTeam_abbrev(String abbrev) {
		this.team_abbrev = abbrev;
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

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	
}
