package natc.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ManagerStatsView {

	private int    team_id;
	private String team_abbrev;
	
	private String year;
	
	private int    games;
	private int    wins;
	private int    losses;
	private int    division_rank;
	private int    playoff_rank;
	
	private int    off_possession;
	private int    off_points;
	private int    off_attempts;
	private int    off_goals;
	private int    off_psa;
	private int    off_psm;

	private int    def_possession;
	private int    def_points;
	private int    def_attempts;
	private int    def_goals;
	private int    def_psa;
	private int    def_psm;
	
	public ManagerStatsView() {
	
		this.team_id        = 0;
		this.team_abbrev    = null;
		this.year           = null;
		this.games          = 0;
		this.wins           = 0;
		this.losses         = 0;
		this.division_rank  = 0;
		this.playoff_rank   = 0;
		this.off_possession = 0;
		this.off_points     = 0;
		this.off_attempts   = 0;
		this.off_goals      = 0;
		this.off_psa        = 0;
		this.off_psm        = 0;
		this.def_possession = 0;
		this.def_points     = 0;
		this.def_attempts   = 0;
		this.def_goals      = 0;
		this.def_psa        = 0;
		this.def_psm        = 0;
	}

	public String getOffScoringEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.off_attempts > 0 ) {
		
			double eff = ((double)this.off_goals / (double)this.off_attempts);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}
	
	public String getOffPsEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.off_psa > 0 ) {
		
			double eff = ((double)this.off_psm / (double)this.off_psa);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}

	public String getOffAvgPossessionTimeDsp() {
	
		int t = this.off_possession / this.games;
		
		DecimalFormat df = new DecimalFormat( "00" );
		
		return df.format( t / 60 ) + ":" + df.format( t % 60 );
	}

	public String getDefScoringEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.def_attempts > 0 ) {
		
			double eff = ((double)this.def_goals / (double)this.def_attempts);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}
	
	public String getDefPsEfficiencyDsp() {
	
		String output = null;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		
		if ( this.def_psa > 0 ) {
		
			double eff = ((double)this.def_psm / (double)this.def_psa);
			
			nf.setMaximumFractionDigits( 2 );
			
			output = nf.format( eff );
		}
		else {
		
			output = nf.format( 0 );
		}
		
		return output;
	}

	public String getDefAvgPossessionTimeDsp() {
	
		int t = this.def_possession / this.games;
		
		DecimalFormat df = new DecimalFormat( "00" );
		
		return df.format( t / 60 ) + ":" + df.format( t % 60 );
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
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

	public int getDivision_rank() {
		return division_rank;
	}

	public void setDivision_rank(int divisionRank) {
		division_rank = divisionRank;
	}

	public int getPlayoff_rank() {
		return playoff_rank;
	}

	public void setPlayoff_rank(int playoffRank) {
		playoff_rank = playoffRank;
	}

	public int getOff_possession() {
		return off_possession;
	}

	public void setOff_possession(int offPossession) {
		off_possession = offPossession;
	}

	public int getOff_points() {
		return off_points;
	}

	public void setOff_points(int offPoints) {
		off_points = offPoints;
	}

	public int getOff_attempts() {
		return off_attempts;
	}

	public void setOff_attempts(int offAttempts) {
		off_attempts = offAttempts;
	}

	public int getOff_goals() {
		return off_goals;
	}

	public void setOff_goals(int offGoals) {
		off_goals = offGoals;
	}

	public int getOff_psa() {
		return off_psa;
	}

	public void setOff_psa(int offPsa) {
		off_psa = offPsa;
	}

	public int getOff_psm() {
		return off_psm;
	}

	public void setOff_psm(int offPsm) {
		off_psm = offPsm;
	}

	public int getDef_possession() {
		return def_possession;
	}

	public void setDef_possession(int defPossession) {
		def_possession = defPossession;
	}

	public int getDef_points() {
		return def_points;
	}

	public void setDef_points(int defPoints) {
		def_points = defPoints;
	}

	public int getDef_attempts() {
		return def_attempts;
	}

	public void setDef_attempts(int defAttempts) {
		def_attempts = defAttempts;
	}

	public int getDef_goals() {
		return def_goals;
	}

	public void setDef_goals(int defGoals) {
		def_goals = defGoals;
	}

	public int getDef_psa() {
		return def_psa;
	}

	public void setDef_psa(int defPsa) {
		def_psa = defPsa;
	}

	public int getDef_psm() {
		return def_psm;
	}

	public void setDef_psm(int defPsm) {
		def_psm = defPsm;
	}
	
}
