package natc.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PlayerStatsView {

	private String  year;
	
	private int     type;
	
	private int     games;
	private int     games_started;
	private int     playing_time;
	private int     points;
	private int     attempts;
	private int     goals;
	private int     assists;
	private int     turnovers;
	private int     stops;
	private int     steals;
	private int     penalties;
	private int     offensive_penalties;
	private int     psa;
	private int     psm;
	private int     ot_psa;
	private int     ot_psm;
	private int     award;
	private int     allstar_team_id;
	
	// team info
	private int     team_id;
	private String  team_abbrev;
	
	public PlayerStatsView() {
	
		this.year                = null;
		this.games               = 0;
		this.games_started       = 0;
		this.playing_time        = 0;
		this.points              = 0;
		this.attempts            = 0;
		this.goals               = 0;
		this.assists             = 0;
		this.turnovers           = 0;
		this.stops               = 0;
		this.steals              = 0;
		this.penalties           = 0;
		this.offensive_penalties = 0;
		this.psa                 = 0;
		this.psm                 = 0;
		this.ot_psa              = 0;
		this.ot_psm              = 0;
		this.award               = 0;
		this.allstar_team_id     = 0;
		this.team_id             = 0;
		this.team_abbrev         = null;
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
	
	public String getTimePerGameDsp() {
	
		int t = 0;
		
		DecimalFormat df = new DecimalFormat( "00" );
		
		if   ( this.games == 0 ) t = 0;
		else                     t = this.playing_time / this.games;
		
		return df.format( t / 60 ) + ":" + df.format( t % 60 );
	}
	
	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
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

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
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

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getAllstar_team_id() {
		return allstar_team_id;
	}

	public void setAllstar_team_id(int allstar_team_id) {
		this.allstar_team_id = allstar_team_id;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getPlaying_time() {
		return playing_time;
	}

	public void setPlaying_time(int playing_time) {
		this.playing_time = playing_time;
	}

	public int getOffensive_penalties() {
		return offensive_penalties;
	}

	public void setOffensive_penalties(int offensive_penalties) {
		this.offensive_penalties = offensive_penalties;
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

	public int getGames_started() {
		return games_started;
	}

	public void setGames_started(int gamesStarted) {
		games_started = gamesStarted;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
