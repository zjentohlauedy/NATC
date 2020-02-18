package natc.view;

import java.text.NumberFormat;

public class ManagerAllstarView {

	private int    manager_id;
	private String first_name;
	private String last_name;
	private int    award;
	private int    team_id;
	private String team_abbrev;
	private int    wins;
	private int    losses;
	private int    points;
	private int    attempts;
	private int    goals;
	private int    psa;
	private int    psm;

	public ManagerAllstarView() {
	
		this.manager_id    = 0;
		this.first_name    = null;
		this.last_name     = null;
		this.award         = 0;
		this.team_id       = 0;
		this.team_abbrev   = null;
		this.wins          = 0;
		this.losses        = 0;
		this.points        = 0;
		this.attempts      = 0;
		this.goals         = 0;
		this.psa           = 0;
		this.psm           = 0;
	}

	public String getRecordDsp() {
	
		return String.valueOf( this.wins ) + " - " + String.valueOf( this.losses );
	}
	
	public String getScoringEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.attempts > 0 ) {
		
			double eff = ((double)this.goals / (double)this.attempts);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}
	
	public String getPsEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.psa > 0 ) {
		
			double eff = ((double)this.psm / (double)this.psa);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
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

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getPsa() {
		return psa;
	}

	public void setPsa(int psa) {
		this.psa = psa;
	}

	public int getPsm() {
		return psm;
	}

	public void setPsm(int psm) {
		this.psm = psm;
	}

}
