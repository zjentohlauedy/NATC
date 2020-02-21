package natc.view;

public class InjuryView {

	private int    player_id;
	private String first_name;
	private String last_name;
	private int    team_id;
	private String team_abbrev;
	private int    duration;
	
	public InjuryView() {
	
		this.player_id   = 0;
		this.first_name  = null;
		this.last_name   = null;
		this.team_id     = 0;
		this.team_abbrev = null;
		this.duration    = 0;
	}

	public String getDurationDsp() {
	
		switch ( this.duration ) {
		
		case   0: return "Game Only";
		case   1: return "1 Day";
		case   2: return "2 Days";
		case   3: return "3 Days";
		case   4: return "4 Days";
		case   5: return "5 Days";
		case   6: return "6 Days";
		case   7: return "1 Week";
		case  14: return "2 Weeks";
		case  21: return "3 Weeks";
		case  28: return "1 Month";
		case  56: return "2 Months";
		case  84: return "3 Months";
		case 112: return "4 Months";
		case 999: return "Season";
		}
		
		return null;
	}
	
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int playerId) {
		player_id = playerId;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
