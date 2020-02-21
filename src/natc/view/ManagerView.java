package natc.view;

import java.text.DecimalFormat;

public class ManagerView {

	public static final int STATUS_RETIRED = 1;
	public static final int STATUS_FIRED   = 2;
	public static final int STATUS_HIRED   = 3;
	
	private int    status;
	private int    manager_id;
	private String first_name;
	private String last_name;
	private int    team_id;
	private String team_abbrev;
	private double offense;
	private double defense;
	private double intangible;
	private double penalty;
	private int    style;
	private int    seasons;
	private double win_pct;
	private int    award_count;
	private int    allstar_count;
	
	public ManagerView() {
	
		this.status        = 0;
		this.manager_id    = 0;
		this.first_name    = null;
		this.last_name     = null;
		this.team_id       = 0;
		this.team_abbrev   = null;
		this.offense       = 0.0;
		this.defense       = 0.0;
		this.intangible    = 0.0;
		this.penalty       = 0.0;
		this.style         = 0;
		this.seasons       = 0;
		this.win_pct       = 0.0;
		this.award_count   = 0;
		this.allstar_count = 0;
	}

	public String getWinPctDsp() {
	
		DecimalFormat df = new DecimalFormat(".000");
		
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

	public double getOffense() {
		return offense;
	}

	public void setOffense(double offense) {
		this.offense = offense;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getIntangible() {
		return intangible;
	}

	public void setIntangible(double intangible) {
		this.intangible = intangible;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getAward_count() {
		return award_count;
	}

	public void setAward_count(int awardCount) {
		award_count = awardCount;
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
	
}
