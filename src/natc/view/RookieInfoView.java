package natc.view;

public class RookieInfoView {

	private int    pick;
	private String year;
	private int    team_id;
	private String team_abbrev;
	
	public RookieInfoView() {
	
		this.pick        = 0;
		this.year        = null;
		this.team_id     = 0;
		this.team_abbrev = null;
	}

	public int getPick() {
		return pick;
	}

	public void setPick(int pick) {
		this.pick = pick;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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
