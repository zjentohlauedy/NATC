package natc.view;

import java.text.DecimalFormat;

public class ManagerView {

	private int    manager_id;
	private String first_name;
	private String last_name;
	private int    team_id;
	private String team_abbrev;
	private int    seasons;
	private double win_pct;
	
	public ManagerView() {
	
		this.manager_id  = 0;
		this.first_name  = null;
		this.last_name   = null;
		this.team_id     = 0;
		this.team_abbrev = null;
		this.seasons     = 0;
		this.win_pct     = 0.0;
	}

	public String getWinPctDsp() {
	
		DecimalFormat df = new DecimalFormat("#.###");
		
		return df.format( win_pct );
	}
	
	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int managerId) {
		manager_id = managerId;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String firstName) {
		first_name = firstName;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String lastName) {
		last_name = lastName;
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

	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	public double getWin_pct() {
		return win_pct;
	}

	public void setWin_pct(double winPct) {
		win_pct = winPct;
	}
	
}
