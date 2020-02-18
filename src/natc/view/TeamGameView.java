package natc.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TeamGameView {

	private String  location;
	private String  name;
	private String  abbrev;
	
	private int     team_id;
	
	private int     possessions;
	private int     possession_time;
	private int     attempts;
	private int     goals;
	private int     turnovers;
	private int     steals;
	private int     penalties;
	private int     offensive_penalties;
	private int     psa;
	private int     psm;
	private int     ot_psa;
	private int     ot_psm;
	private int     period1_score;
	private int     period2_score;
	private int     period3_score;
	private int     period4_score;
	private int     period5_score;
	private int     overtime_score;
	private int     total_score;
	private boolean win;
	
	public TeamGameView() {
	
		this.location            = null;
		this.name                = null;
		this.abbrev              = null;
		this.team_id             = 0;
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
		this.period1_score       = 0;
		this.period2_score       = 0;
		this.period3_score       = 0;
		this.period4_score       = 0;
		this.period5_score       = 0;
		this.overtime_score      = 0;
		this.total_score         = 0;
		this.win                 = false;
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();

		sb.append( "<location>"            + String.valueOf( location            ) + "</location>"            );
		sb.append( "<name>"                + String.valueOf( name                ) + "</name>"                );
		sb.append( "<abbrev>"              + String.valueOf( abbrev              ) + "</abbrev>"              );
		sb.append( "<team_id>"             + String.valueOf( team_id             ) + "</team_id>"             );
		sb.append( "<possessions>"         + String.valueOf( possessions         ) + "</possessions>"         );
		sb.append( "<possession_time>"     + getGameTimeDsp(                     ) + "</possession_time>"     );
		sb.append( "<attempts>"            + String.valueOf( attempts            ) + "</attempts>"            );
		sb.append( "<goals>"               + String.valueOf( goals               ) + "</goals>"               );
		sb.append( "<efficiency>"          + getScoringEfficiencyDsp(            ) + "</efficiency>"          );
		sb.append( "<turnovers>"           + String.valueOf( turnovers           ) + "</turnovers>"           );
		sb.append( "<steals>"              + String.valueOf( steals              ) + "</steals>"              );
		sb.append( "<penalties>"           + String.valueOf( penalties           ) + "</penalties>"           );
		sb.append( "<offensive_penalties>" + String.valueOf( offensive_penalties ) + "</offensive_penalties>" );
		sb.append( "<psa>"                 + String.valueOf( psa                 ) + "</psa>"                 );
		sb.append( "<psm>"                 + String.valueOf( psm                 ) + "</psm>"                 );
		sb.append( "<psefficiency>"        + getPsEfficiencyDsp(                 ) + "</psefficiency>"        );
		sb.append( "<ot_psa>"              + String.valueOf( ot_psa              ) + "</ot_psa>"              );
		sb.append( "<ot_psm>"              + String.valueOf( ot_psm              ) + "</ot_psm>"              );
		sb.append( "<otpsefficiency>"      + getOtPsEfficiencyDsp(               ) + "</otpsefficiency>"      );
		sb.append( "<period1_score>"       + String.valueOf( period1_score       ) + "</period1_score>"       );
		sb.append( "<period2_score>"       + String.valueOf( period2_score       ) + "</period2_score>"       );
		sb.append( "<period3_score>"       + String.valueOf( period3_score       ) + "</period3_score>"       );
		sb.append( "<period4_score>"       + String.valueOf( period4_score       ) + "</period4_score>"       );
		sb.append( "<period5_score>"       + String.valueOf( period5_score       ) + "</period5_score>"       );
		sb.append( "<overtime_score>"      + String.valueOf( overtime_score      ) + "</overtime_score>"      );
		sb.append( "<total_score>"         + String.valueOf( total_score         ) + "</total_score>"         );
		sb.append( "<win>"                 + String.valueOf( win                 ) + "</win>"                 );

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
		
		return df.format( this.possession_time / 60 ) + ":" + df.format( this.possession_time % 60 );
	}
	
	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getTotal_score() {
		return total_score;
	}

	public void setTotal_score(int score) {
		this.total_score = score;
	}

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getTurnovers() {
		return turnovers;
	}

	public void setTurnovers(int turnovers) {
		this.turnovers = turnovers;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
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

	public int getPeriod1_score() {
		return period1_score;
	}

	public void setPeriod1_score(int period1Score) {
		period1_score = period1Score;
	}

	public int getPeriod2_score() {
		return period2_score;
	}

	public void setPeriod2_score(int period2Score) {
		period2_score = period2Score;
	}

	public int getPeriod3_score() {
		return period3_score;
	}

	public void setPeriod3_score(int period3Score) {
		period3_score = period3Score;
	}

	public int getPeriod4_score() {
		return period4_score;
	}

	public void setPeriod4_score(int period4Score) {
		period4_score = period4Score;
	}

	public int getPeriod5_score() {
		return period5_score;
	}

	public void setPeriod5_score(int period5Score) {
		period5_score = period5Score;
	}

	public int getOvertime_score() {
		return overtime_score;
	}

	public void setOvertime_score(int overtimeScore) {
		overtime_score = overtimeScore;
	}
	
}
