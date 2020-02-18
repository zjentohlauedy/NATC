package natc.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class PlayerGameView {

	private String  first_name;
	private String  last_name;
	
	private int     player_id;
	
	private int     opponent;
	private String  opponent_abbrev;
	private boolean road;
	private Date    datestamp;
	private int     game_id;
	
	private boolean injured;
	private boolean started;
	
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
	
	public PlayerGameView() {
	
		this.first_name          = null;
		this.last_name           = null;
		this.player_id           = 0;
		this.opponent            = 0;
		this.opponent_abbrev     = null;
		this.road                = false;
		this.datestamp           = null;
		this.game_id             = 0;
		this.injured             = false;
		this.started             = false;
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
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();
		
		sb.append( "<first_name>" + String.valueOf( first_name ) + "</first_name>" );
		sb.append( "<last_name>" + String.valueOf( last_name ) + "</last_name>" );
		sb.append( "<player_id>" + String.valueOf( player_id ) + "</player_id>" );
		sb.append( "<opponent>" + String.valueOf( opponent ) + "</opponent>" );
		sb.append( "<opponent_abbrev>" + String.valueOf( opponent_abbrev ) + "</opponent_abbrev>" );
		sb.append( "<road>" + String.valueOf( road ) + "</road>" );
		sb.append( "<datestamp>" + String.valueOf( datestamp ) + "</datestamp>" );
		sb.append( "<game_id>" + String.valueOf( game_id ) + "</game_id>" );
		sb.append( "<injured>" + String.valueOf( injured ) + "</injured>" );
		sb.append( "<started>" + String.valueOf( started ) + "</started>" );
		sb.append( "<playing_time>" + getGameTimeDsp() + "</playing_time>" );
		sb.append( "<points>" + String.valueOf( points ) + "</points>" );
		sb.append( "<attempts>" + String.valueOf( attempts ) + "</attempts>" );
		sb.append( "<goals>" + String.valueOf( goals ) + "</goals>" );
		sb.append( "<efficiency>" + getScoringEfficiencyDsp() + "</efficiency>" );
		sb.append( "<assists>" + String.valueOf( assists ) + "</assists>" );
		sb.append( "<turnovers>" + String.valueOf( turnovers ) + "</turnovers>" );
		sb.append( "<stops>" + String.valueOf( stops ) + "</stops>" );
		sb.append( "<steals>" + String.valueOf( steals ) + "</steals>" );
		sb.append( "<penalties>" + String.valueOf( penalties ) + "</penalties>" );
		sb.append( "<offensive_penalties>" + String.valueOf( offensive_penalties ) + "</offensive_penalties>" );
		sb.append( "<psa>" + String.valueOf( psa ) + "</psa>" );
		sb.append( "<psm>" + String.valueOf( psm ) + "</psm>" );
		sb.append( "<psefficiency>" + getPsEfficiencyDsp() + "</psefficiency>" );
		sb.append( "<ot_psa>" + String.valueOf( ot_psa ) + "</ot_psa>" );
		sb.append( "<ot_psm>" + String.valueOf( ot_psm ) + "</ot_psm>" );
		sb.append( "<otpsefficiency>" + getOtPsEfficiencyDsp() + "</otpsefficiency>" );
		
		return sb.toString();
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

	public String getGameTimeDsp() {
	
		DecimalFormat df = new DecimalFormat( "00" );
		
		return df.format( this.playing_time / 60 ) + ":" + df.format( this.playing_time % 60 );
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
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

	public boolean isInjured() {
		return injured;
	}

	public void setInjured(boolean injured) {
		this.injured = injured;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getOpponent() {
		return opponent;
	}

	public void setOpponent(int opponent) {
		this.opponent = opponent;
	}

	public String getOpponent_abbrev() {
		return opponent_abbrev;
	}

	public void setOpponent_abbrev(String opponentAbbrev) {
		opponent_abbrev = opponentAbbrev;
	}

	public boolean isRoad() {
		return road;
	}

	public void setRoad(boolean road) {
		this.road = road;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int gameId) {
		game_id = gameId;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}
}
