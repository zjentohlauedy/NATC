package natc.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TeamStatsView {

	private int type;
	private int games;
	private int possessions;
	private int possession_time;
	private int attempts;
	private int goals;
	private int turnovers;
	private int steals;
	private int penalties;
	private int offensive_penalties;
	private int psa;
	private int psm;
	private int ot_psa;
	private int ot_psm;
	private int score;
	
	public TeamStatsView() {
	
		this.games               = 0;
		this.possessions         = 0;
		this.possession_time     = 0;
		this.attempts            = 0;
		this.goals               = 0;
		this.turnovers           = 0;
		this.steals              = 0;
		this.penalties           = 0;
		this.offensive_penalties = 0;
		this.psa                 = 0;
		this.psm                 = 0;
		this.ot_psa              = 0;
		this.ot_psm              = 0;
		this.score               = 0;
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

	public String getOtPsEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.ot_psa > 0 ) {
		
			double eff = ((double)this.ot_psm / (double)this.ot_psa);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}

	public String getAvgPossessionTimeDsp() {
	
		int t = this.possession_time / this.games;
		
		DecimalFormat df = new DecimalFormat( "00" );
		
		return df.format( t / 60 ) + ":" + df.format( t % 60 );
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

	public int getOt_psa() {
		return ot_psa;
	}

	public void setOt_psa(int ot_psa) {
		this.ot_psa = ot_psa;
	}

	public int getOt_psm() {
		return ot_psm;
	}

	public void setOt_psm(int ot_psm) {
		this.ot_psm = ot_psm;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getTurnovers() {
		return turnovers;
	}

	public void setTurnovers(int turnovers) {
		this.turnovers = turnovers;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getPossessions() {
		return possessions;
	}

	public void setPossessions(int possessions) {
		this.possessions = possessions;
	}

	public int getPossession_time() {
		return possession_time;
	}

	public void setPossession_time(int possession_time) {
		this.possession_time = possession_time;
	}

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getOffensive_penalties() {
		return offensive_penalties;
	}

	public void setOffensive_penalties(int offensive_penalties) {
		this.offensive_penalties = offensive_penalties;
	}
	
}
