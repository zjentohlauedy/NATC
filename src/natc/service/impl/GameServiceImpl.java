package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import natc.data.Constants;
import natc.data.Match;
import natc.data.Player;
import natc.data.PlayerScore;
import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Score;
import natc.data.Team;
import natc.data.TeamGame;
import natc.service.GameService;
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.DraftView;
import natc.view.GameView;
import natc.view.ReleaseView;
import natc.view.ResignView;
import natc.view.RetiredView;
import natc.view.TrainingView;

public class GameServiceImpl implements GameService {

	private Connection dbConn = null;
	private String     year   = null;
	
	public GameServiceImpl( Connection dbConn, String year ) {
	
		this.dbConn = dbConn;
		this.year   = year;
	}

	private class TeamComparator implements Comparator {

		private Connection dbConn;
		private String     year;
		
		public TeamComparator( Connection dbConn, String year ) {
		
			this.dbConn = dbConn;
			this.year   = year;
		}
		
		public int compare( Object arg0, Object arg1 ) {

			Team team0 = (Team)arg0;
			Team team1 = (Team)arg1;

			if ( team0.equals( team1 ) ) return 0;

			if ( team0.getGames() == 0  &&  team1.getGames() == 0 ) return 0;
			
			// Playoff Ranking is the top priority
			if ( team0.getPlayoff_rank() != team1.getPlayoff_rank() ) {
				
				return team0.getPlayoff_rank() - team1.getPlayoff_rank();
			}

			// Regular season record is next
			if ( team0.getWins() != team1.getWins() ) {
				
				return team0.getWins() - team1.getWins();
			}

			// Teams in the same division compare division records next
			if ( team0.getDivision() == team1.getDivision() ) {

				int wins   = team0.getDivision_wins();
				int losses = team0.getDivision_losses();

				double team0_pct;
				double team1_pct;

				if   ( (wins + losses) != 0 ) team0_pct = (double)wins / (double)(wins + losses);
				else                          team0_pct = 0.0;

				wins   = team1.getDivision_wins();
				losses = team1.getDivision_losses();

				if   ( (wins + losses) != 0 ) team1_pct = (double)wins / (double)(wins + losses);
				else                          team1_pct = 0.0;

				if ( team0_pct != team1_pct ) return (team0_pct > team1_pct) ? 1 : -1;
			}

			if ( team0.getGames() > 5 ) {

				int headToHead = compareHeadToHead( team0.getTeam_id(), team1.getTeam_id() );
			
				if ( headToHead != 0 ) return headToHead;
			
				int team0_sd = getScoringDifference( team0.getTeam_id() );
				int team1_sd = getScoringDifference( team1.getTeam_id() );

				return team0_sd - team1_sd;
			}
			
			return 0;
		}

		private int compareHeadToHead( int team0_id, int team1_id ) {
		
			PreparedStatement ps       = null;
			ResultSet         dbRs     = null;
			int               x        = 0;
			
			try {
				
				ps = DatabaseImpl.getWinsByMatchSelectPs( dbConn );
				
				ps.setString(  1, year                      );
				ps.setInt(     2, TeamGame.gt_RegularSeason );
				ps.setInt(     3, team0_id                  );
				ps.setInt(     4, team1_id                  );
				ps.setBoolean( 5, true                      );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x += dbRs.getInt( 1 );
				}
				
				ps = DatabaseImpl.getWinsByMatchSelectPs( dbConn );
				
				ps.setString(  1, year                      );
				ps.setInt(     2, TeamGame.gt_RegularSeason );
				ps.setInt(     3, team1_id                  );
				ps.setInt(     4, team0_id                  );
				ps.setBoolean( 5, true                      );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x -= dbRs.getInt( 1 );
				}
			}
			catch ( SQLException sqle ) {
				
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			return x;
		}
		
		private int getScoringDifference( int team_id ) {
		
			PreparedStatement ps       = null;
			ResultSet         dbRs     = null;
			int               x        = 0;
			
			try {
				
				ps = DatabaseImpl.getTotalPointsScoredSelectPs( dbConn );
				
				ps.setString( 1, year    );
				ps.setInt(    2, team_id );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x += dbRs.getInt( 1 );
				}
				
				ps = DatabaseImpl.getTotalPointsAllowedSelectPs( dbConn );
				
				ps.setString( 1, year    );
				ps.setInt(    2, team_id );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x -= dbRs.getInt( 1 );
				}
			}
			catch ( SQLException sqle ) {
				
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			return x;
		}
	}
	
	private class PlayerComparator implements Comparator {
	
		public static final int pcm_Actual    = 0;
		public static final int pcm_Projected = 1;
		
		private Connection dbConn;
		private String     year;
		private int        mode;
		
		public PlayerComparator( Connection dbConn, String year, int mode ) {
		
			this.dbConn = dbConn;
			this.year   = year;
			this.mode   = mode;
		}

		public int compare( Object arg0, Object arg1 ) {
			
			Player player0 = (Player)arg0;
			Player player1 = (Player)arg1;
			
			if ( mode == pcm_Projected ) {
				
				return (player0.getRating() > player1.getRating()) ? 1 : -1;
			}
			
			if ( mode == pcm_Actual ) {
					
				if ( player0.getScore() == 0 ) player0.setScore( getPlayerScore( player0.getPlayer_id() ) );
				if ( player1.getScore() == 0 ) player1.setScore( getPlayerScore( player1.getPlayer_id() ) );
				
				return player0.getScore() - player1.getScore();
			}
			
			return 0;
		}

		private int getPlayerScore( int player_id ) {
		
			PreparedStatement ps   = null;
			ResultSet         dbRs = null;
			
			try {

				ps = DatabaseImpl.getPlayerScoreByIdSelectPs( dbConn );
				
				ps.setString( 1, year                      );
				ps.setInt(    2, TeamGame.gt_RegularSeason );
				ps.setInt(    3, player_id                 );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {

					return dbRs.getInt( 1 );
				}
			}
			catch ( SQLException sqle ) {
				
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			return 0;
		}
		
		public int getMode() {
			return mode;
		}

		public void setMode(int mode) {
			this.mode = mode;
		}
		
	}

	private int getNextGameId() throws SQLException {

		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		int               id   = 0;

		try {

			ps = DatabaseImpl.getNextGameIdSelectPs( dbConn );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {

				id = dbRs.getInt( 1 ) + 1;
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return id;
	}

	private boolean isPenaltyShotGood( Player shooter, Team offense, Team defense ) {
	
		double pbb = 42.5 + shooter.getPenalty_shot() + (offense.getPs_offense() - defense.getPs_defense())/2.0;
		
		if ( (Math.random() * 100.0) < pbb ) {
		
			return true;
		}
		
		return false;
	}
	
	private void simulateOvertime( Team homeTeam, Team roadTeam ) {

		homeTeam.getGame().setOvertime( true );
		roadTeam.getGame().setOvertime( true );
		
		// overtime penalty shots - sort the player list by penalty shot attribute
		Collections.sort( homeTeam.getPlayers(), new Comparator() { public int compare( Object arg1, Object arg2 ){
			/**/                                                                 Player p1 = (Player)arg1;
			/**/                                                                 Player p2 = (Player)arg2;
			/**/                                                                 return (p1.getPenalty_shot() > p2.getPenalty_shot()) ? 1 : -1; } });
		
		Collections.sort( roadTeam.getPlayers(), new Comparator() { public int compare( Object arg1, Object arg2 ){
			/**/                                                                 Player p1 = (Player)arg1;
			/**/                                                                 Player p2 = (Player)arg2;
			/**/                                                                 return (p1.getPenalty_shot() > p2.getPenalty_shot()) ? 1 : -1; } });
		

		int p = 0;
		
		while ( homeTeam.getGame().getScore().getScore() == roadTeam.getGame().getScore().getScore() ) {
		
			Player player = (Player)roadTeam.getPlayers().get( p );
			
			// Player and road team get an attempt
			player.getGame().setOt_psa( player.getGame().getOt_psa() + 1 );
			roadTeam.getGame().getScore().setOt_psa( roadTeam.getGame().getScore().getOt_psa() + 1 );
			
			if ( isPenaltyShotGood( player, roadTeam, homeTeam ) ) {
				
				// Player and road team get a make
				player.getGame().setOt_psm( player.getGame().getOt_psm() + 1 );
				roadTeam.getGame().getScore().setOt_psm( roadTeam.getGame().getScore().getOt_psm() + 1 );
				
				// Update road score
				roadTeam.getGame().getScore().setScore( roadTeam.getGame().getScore().getScore() + 1 );
			}
			
			player = (Player)homeTeam.getPlayers().get( p );
			
			// Player and home team get an attempt
			player.getGame().setOt_psa( player.getGame().getOt_psa() + 1 );
			homeTeam.getGame().getScore().setOt_psa( homeTeam.getGame().getScore().getOt_psa() + 1 );

			if ( isPenaltyShotGood( player, homeTeam, roadTeam ) ) {

				// Player and home team get a make
				player.getGame().setOt_psm( player.getGame().getOt_psm() + 1 );
				homeTeam.getGame().getScore().setOt_psm( homeTeam.getGame().getScore().getOt_psm() + 1 );

				// Update home score
				homeTeam.getGame().getScore().setScore( homeTeam.getGame().getScore().getScore() + 1 );
			}
			
			p++;
			
			// All players have taken a shot, start over from the top
			if ( p >= homeTeam.getPlayers().size() ) p = 0;
		}
	}
	
	private void simulateGame( Team roadTeam, Team homeTeam, int game_type ) {
		
		Team    attacker      = null;
		boolean clock_stopped = true;
		
		for ( int period = 1; period <= 5; ++period ) {
		
			roadTeam.determineActivePlayers( 720 );
			homeTeam.determineActivePlayers( 720 );
			
			// Determine attacker (team in possession of ball)
			if   ( (period % 2) == 1 ) attacker = homeTeam;
			else                       attacker = roadTeam;
			
			attacker.getGame().getScore().setPossessions( attacker.getGame().getScore().getPossessions() + 1 );
			
			// Each period is 12:00
			for ( int time_remaining = 720, time_elapsed = 0; ; ) {
			
				// Time between events is 20-50 seconds
				time_elapsed = (int)Math.floor( Math.random() * 30.0 ) + 20;
				
				// check for end of period
				if ( time_elapsed >= time_remaining ) {
					
					attacker.getGame().getScore().setPossession_time( attacker.getGame().getScore().getPossession_time() + time_remaining );
					
					roadTeam.distributeTime( time_remaining );
					homeTeam.distributeTime( time_remaining );
					
					break;
				}
				
				time_remaining -= time_elapsed;
				
				attacker.getGame().getScore().setPossession_time( attacker.getGame().getScore().getPossession_time() + time_elapsed );

				roadTeam.distributeTime( time_elapsed );
				homeTeam.distributeTime( time_elapsed );
				
				// recalculate event probabilities as players may have become tired
				roadTeam.calcProbabilities( homeTeam, game_type, true  );
				homeTeam.calcProbabilities( roadTeam, game_type, false );
				
				clock_stopped = false;
				
				switch ( attacker.getGame().pickEvent() ) {
				
				case 0: // Failed Attempt
					
					attacker.getGame().getScore().setAttempts( attacker.getGame().getScore().getAttempts() + 1 );
					
					attacker.distributeAttempt();
					
					if   ( attacker == homeTeam ) roadTeam.distributeStop();
					else                          homeTeam.distributeStop();
					
					break;
					
				case 1: // Scoring Attempt
					
					attacker.getGame().getScore().setAttempts( attacker.getGame().getScore().getAttempts() + 1 );
					attacker.getGame().getScore().setGoals(    attacker.getGame().getScore().getGoals()    + 1 );
					attacker.getGame().getScore().setScore(    attacker.getGame().getScore().getScore()    + 3 );
					
					attacker.distributeGoal();
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					attacker.getGame().getScore().setPossessions( attacker.getGame().getScore().getPossessions() + 1 );
					
					clock_stopped = true;
					
					break;
					
				case 2: // Turnover
					
					attacker.getGame().getScore().setTurnovers( attacker.getGame().getScore().getTurnovers() + 1 );
					
					attacker.distributeTurnover();
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					attacker.getGame().getScore().setPossessions( attacker.getGame().getScore().getPossessions() + 1 );
					
					clock_stopped = true;
					
					break;
					
				case 3: // Defender Steals
					
					attacker.getGame().getScore().setTurnovers( attacker.getGame().getScore().getTurnovers() + 1 );
					
					attacker.distributeTurnover();
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					attacker.getGame().getScore().setSteals(      attacker.getGame().getScore().getSteals()      + 1 );
					attacker.getGame().getScore().setPossessions( attacker.getGame().getScore().getPossessions() + 1 );
					
					attacker.distributeSteal();
					
					break;
					
				case 4: // Defensive Penalty
					
					attacker.getGame().getScore().setPsa( attacker.getGame().getScore().getPsa() + 1 );
					
					Player shooter = attacker.distributePenaltyShot();
					
					if ( isPenaltyShotGood( shooter, attacker, (attacker == homeTeam) ? roadTeam : homeTeam ) ) {
					
						attacker.getGame().getScore().setPsm(   attacker.getGame().getScore().getPsm()  + 1 );
						attacker.getGame().getScore().setScore( attacker.getGame().getScore().getScore()+ 1 );
						
						shooter.getGame().setPsm( shooter.getGame().getPsm() + 1 );
					}
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					attacker.getGame().getScore().setPenalties(   attacker.getGame().getScore().getPenalties()   + 1 );
					attacker.getGame().getScore().setPossessions( attacker.getGame().getScore().getPossessions() + 1 );
					
					attacker.distributePenalty();
					
					clock_stopped = true;
					
					break;
					
				case 5: // Offensive Penalty
					
					attacker.getGame().getScore().setPenalties(           attacker.getGame().getScore().getPenalties()           + 1 );
					attacker.getGame().getScore().setOffensive_penalties( attacker.getGame().getScore().getOffensive_penalties() + 1 );
					
					Player offender = attacker.distributePenalty();
					
					offender.getGame().setOffensive_penalties( offender.getGame().getOffensive_penalties() + 1 );
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					attacker.getGame().getScore().setPsa( attacker.getGame().getScore().getPsa() + 1 );
					
					shooter = attacker.distributePenaltyShot();
					
					if ( isPenaltyShotGood( shooter, attacker, (attacker == homeTeam) ? roadTeam : homeTeam ) ) {
					
						attacker.getGame().getScore().setPsm(   attacker.getGame().getScore().getPsm()  + 1 );
						attacker.getGame().getScore().setScore( attacker.getGame().getScore().getScore()+ 1 );
						
						shooter.getGame().setPsm( shooter.getGame().getPsm() + 1 );
					}
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					clock_stopped = true;
					
					break;
				}
				
				// If clock is stopped, substitute players if necessary
				if ( clock_stopped ) {
				
					roadTeam.determineActivePlayers( time_remaining );
					homeTeam.determineActivePlayers( time_remaining );
				}
			}
			
			// Give players 5 minutes rest
			roadTeam.restPlayers( 300 );
			homeTeam.restPlayers( 300 );
		}
		
		if ( homeTeam.getGame().getScore().getScore() == roadTeam.getGame().getScore().getScore() ) {
			
			simulateOvertime( homeTeam, roadTeam );
		}
	}
	
	private void processMatch( Match match, Date gameDate, int type ) throws SQLException {
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		Team homeTeam = teamService.getTeamById( match.getHome_team_id() );
		Team roadTeam = teamService.getTeamById( match.getRoad_team_id() );
		
		// If the playoff ranks are different then it is the post season and one team already advanced
		if ( homeTeam.getPlayoff_rank() != roadTeam.getPlayoff_rank() ) return;
		
		// Sorter players on each team by rating.
		Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Projected ) );
		Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Projected ) );
		
		int game_id = getNextGameId();

		roadTeam.initTeamGame( homeTeam, game_id, year, type, gameDate, true  );
		homeTeam.initTeamGame( roadTeam, game_id, year, type, gameDate, false );
		
		roadTeam.initPlayerGames();
		homeTeam.initPlayerGames();
		
		simulateGame( roadTeam, homeTeam, type );

		Score homeScore = homeTeam.getGame().getScore();
		Score roadScore = roadTeam.getGame().getScore();
		
		if   ( homeScore.getScore() >  roadScore.getScore() ) {
			
			homeTeam.getGame().setWin( true  );
			roadTeam.getGame().setWin( false );
		}
		else {
			
			roadTeam.getGame().setWin( true  );
			homeTeam.getGame().setWin( false );
		}
		
		// Update counters on team records
		switch ( type ) {
		
		case TeamGame.gt_Preseason:
			
			homeTeam.setPreseason_games( homeTeam.getPreseason_games() + 1 );
			roadTeam.setPreseason_games( roadTeam.getPreseason_games() + 1 );
			
			if ( homeScore.getScore() > roadScore.getScore() ) {
			
				homeTeam.setPreseason_wins(   homeTeam.getPreseason_wins()   + 1 );
				roadTeam.setPreseason_losses( roadTeam.getPreseason_losses() + 1 );
			}
			else {
				
				roadTeam.setPreseason_wins(   roadTeam.getPreseason_wins()   + 1 );
				homeTeam.setPreseason_losses( homeTeam.getPreseason_losses() + 1 );
			}
			
			break;
			
		case TeamGame.gt_RegularSeason:
			
			homeTeam.setGames( homeTeam.getGames() + 1 );
			roadTeam.setGames( roadTeam.getGames() + 1 );
			
			if ( homeScore.getScore() > roadScore.getScore() ) {
				
				// Home team wins
				homeTeam.setWins(    homeTeam.getWins()   + 1 );
				roadTeam.setLosses(  roadTeam.getLosses() + 1 );
				
				homeTeam.setHome_wins(    homeTeam.getHome_wins()   + 1 );
				roadTeam.setRoad_losses(  roadTeam.getRoad_losses() + 1 );
				
				if ( homeScore.getOt_psa() > 0 ) {
				
					homeTeam.setOt_wins(    homeTeam.getOt_wins()   + 1 );
					roadTeam.setOt_losses(  roadTeam.getOt_losses() + 1 );
				}
				
				if ( homeTeam.getDivision() == roadTeam.getDivision() ) {
				
					homeTeam.setDivision_wins(    homeTeam.getDivision_wins()   + 1 );
					roadTeam.setDivision_losses(  roadTeam.getDivision_losses() + 1 );
				}
				
				if ( homeTeam.getConference() != roadTeam.getConference() ) {
				
					homeTeam.setOoc_wins(    homeTeam.getOoc_wins()   + 1 );
					roadTeam.setOoc_losses(  roadTeam.getOoc_losses() + 1 );
				}
			}
			else {
				
				// Road team wins
				roadTeam.setWins(    roadTeam.getWins()   + 1 );
				homeTeam.setLosses(  homeTeam.getLosses() + 1 );
				
				roadTeam.setRoad_wins(    roadTeam.getRoad_wins()   + 1 );
				homeTeam.setHome_losses(  homeTeam.getHome_losses() + 1 );
				
				if ( roadScore.getOt_psa() > 0 ) {
					
					roadTeam.setOt_wins(    roadTeam.getOt_wins()   + 1 );
					homeTeam.setOt_losses(  homeTeam.getOt_losses() + 1 );
				}
				
				if ( roadTeam.getDivision() == homeTeam.getDivision() ) {
				
					roadTeam.setDivision_wins(    roadTeam.getDivision_wins()   + 1 );
					homeTeam.setDivision_losses(  homeTeam.getDivision_losses() + 1 );
				}
				
				if ( roadTeam.getConference() != homeTeam.getConference() ) {
				
					roadTeam.setOoc_wins(    roadTeam.getOoc_wins()   + 1 );
					homeTeam.setOoc_losses(  homeTeam.getOoc_losses() + 1 );
				}
			}
			
			break;
			
		case TeamGame.gt_Postseason:
			
			homeTeam.setPlayoff_games( homeTeam.getPlayoff_games() + 1 );
			roadTeam.setPlayoff_games( roadTeam.getPlayoff_games() + 1 );
			
			if ( homeScore.getScore() > roadScore.getScore() ) {
			
				switch ( homeTeam.getPlayoff_rank() ) {
				
				case 1:
					
					homeTeam.setRound1_wins( homeTeam.getRound1_wins() + 1 );
					
					if ( homeTeam.getRound1_wins() == Constants.ROUND_1_WINS_TO_ADVANCE ) {
						
						homeTeam.setPlayoff_rank( homeTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 2:
					
					homeTeam.setRound2_wins( homeTeam.getRound2_wins() + 1 );
					
					if ( homeTeam.getRound2_wins() == Constants.ROUND_2_WINS_TO_ADVANCE ) {
						
						homeTeam.setPlayoff_rank( homeTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 3:
					
					homeTeam.setRound3_wins( homeTeam.getRound3_wins() + 1 );
					
					if ( homeTeam.getRound3_wins() == Constants.ROUND_3_WINS_TO_ADVANCE ) {
					
						homeTeam.setPlayoff_rank( homeTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 4:
					
					homeTeam.setPlayoff_rank( homeTeam.getPlayoff_rank() + 1 );
					
					break;
				}
			}
			else {
				
				switch ( roadTeam.getPlayoff_rank() ) {
				
				case 1:
					
					roadTeam.setRound1_wins( roadTeam.getRound1_wins() + 1 );
					
					if ( roadTeam.getRound1_wins() == Constants.ROUND_1_WINS_TO_ADVANCE ) {
						
						roadTeam.setPlayoff_rank( roadTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 2:
					
					roadTeam.setRound2_wins( roadTeam.getRound2_wins() + 1 );
					
					if ( roadTeam.getRound2_wins() == Constants.ROUND_2_WINS_TO_ADVANCE ) {
						
						roadTeam.setPlayoff_rank( roadTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 3:
					
					roadTeam.setRound3_wins( roadTeam.getRound3_wins() + 1 );
					
					if ( roadTeam.getRound3_wins() == Constants.ROUND_3_WINS_TO_ADVANCE ) {
					
						roadTeam.setPlayoff_rank( roadTeam.getPlayoff_rank() + 1 );
					}
					
					break;
					
				case 4:
					
					roadTeam.setPlayoff_rank( roadTeam.getPlayoff_rank() + 1 );
					
					break;
				}
			}
			
			break;
			
		case TeamGame.gt_Allstar:
			
			homeTeam.setGames( homeTeam.getGames() + 1 );
			roadTeam.setGames( roadTeam.getGames() + 1 );
			
			if ( homeScore.getScore() > roadScore.getScore() ) {
				
				// Home team wins
				homeTeam.setWins(    homeTeam.getWins()   + 1 );
				roadTeam.setLosses(  roadTeam.getLosses() + 1 );
			}
			else {
			
				// Road team wins
				roadTeam.setWins(    roadTeam.getWins()   + 1 );
				homeTeam.setLosses(  homeTeam.getLosses() + 1 );
			}
		}

		teamService.updateTeam( homeTeam );
		teamService.updateTeam( roadTeam );
		
		teamService.insertTeamGame( homeTeam.getGame() );
		teamService.insertTeamGame( roadTeam.getGame() );
		
		teamService.updateTeamStats( homeTeam, roadTeam, type );
		teamService.updateTeamStats( roadTeam, homeTeam, type );
		

		// Save to database
		PlayerService playerService = new PlayerServiceImpl( dbConn, year );
		
		Iterator i = homeTeam.getPlayers().iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			playerService.insertPlayerGame( player.getGame() );
			
			playerService.updatePlayerStats( player, type );
		}
		
		i = roadTeam.getPlayers().iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			playerService.insertPlayerGame( player.getGame() );
			
			playerService.updatePlayerStats( player, type );
		}
	}

	public void initializeDatabase() throws SQLException {
		
		// Generate teams - also generates players for each team
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamService.generateTeams();
		
		// Generate a new schedule
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		
		scheduleService.generateSchedule();
	}
	
	public void startNewSeason( String lastYear ) throws SQLException {
	
		// Create team records for new season
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamService.updateTeamsForNewSeason( lastYear );
		
		//Create player records for new season
		PlayerService playerservice = new PlayerServiceImpl( dbConn, year );
		
		playerservice.updatePlayersForNewSeason( lastYear );
		
		// Generate a new schedule
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		
		scheduleService.generateSchedule();
	}
	
	public void processScheduleEvent( Schedule event ) throws SQLException {
	
		switch ( event.getType().getValue() ) {
		
		case ScheduleType.BEGINNING_OF_SEASON:     /* Nothing to do here */        break;
		case ScheduleType.ROOKIE_DRAFT_ROUND_1:    processRookieDraft( event );    break;
		case ScheduleType.ROOKIE_DRAFT_ROUND_2:    processRookieDraft( event );    break;
		case ScheduleType.TRAINING_CAMP:           processTrainingCamp( event );   break;
		case ScheduleType.PRESEASON:               processPreseasonGame( event );  break;
		case ScheduleType.ROSTER_CUT:              processRosterCut( event );      break;
		case ScheduleType.FREE_AGENT_DRAFT:        processFreeAgentDraft( event ); break;
		case ScheduleType.REGULAR_SEASON:          processSeasonGame( event );     break;
		case ScheduleType.AWARDS:                  processAwards( event );         break;
		case ScheduleType.POSTSEASON:              setupPlayoffs( event );         break;
		case ScheduleType.DIVISION_PLAYOFF:        processPostseasonGame( event ); break;
		case ScheduleType.DIVISION_CHAMPIONSHIP:   processPostseasonGame( event ); break;
		case ScheduleType.CONFERENCE_CHAMPIONSHIP: processPostseasonGame( event ); break;
		case ScheduleType.NATC_CHAMPIONSHIP:       processPostseasonGame( event ); break;
		case ScheduleType.ALL_STARS:               selectAllstarTeams( event );    break;
		case ScheduleType.ALL_STAR_DAY_1:          processAllstarGame( event );    break;
		case ScheduleType.ALL_STAR_DAY_2:          processAllstarGame( event );    break;
		case ScheduleType.END_OF_SEASON:           processEndOfSeason( event );    break;
		}
		
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, event.getYear() );
		
		scheduleService.completeScheduleEvent( event );
	}
	
	private void processRookieDraft( Schedule event ) throws SQLException {
		
		// Get list of teams from previous season
		List   teamList   = null;
		List   playerList = null;
		String lastYear   = String.valueOf(  Integer.parseInt( event.getYear() ) - 1 );
		int    pick       = 0;
		
		TeamService   teamService   = new TeamServiceImpl( dbConn, lastYear );
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		if ( (teamList = teamService.getTeamList()) == null ) return;
		
		// Rank teams - sort list using compareto of team object
		Collections.sort( teamList, new TeamComparator( dbConn, lastYear ) );
		
		if ( event.getType().getValue() == ScheduleType.ROOKIE_DRAFT_ROUND_1 ) {
			
			// Generate 100 new players for this year and no team_id
			for ( int i = 0; i < 100; ++i ) {
			
				playerService.generatePlayer( false, 0 );
			}
			
			pick = 1;
		}
		else {
		
			pick = 41;
		}
		
		// Get list of remaining undrafted players
		if ( (playerList = playerService.getFreePlayers()) == null ) return;
		
		// Rank players - sort list using comparetp of player object
		Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Projected ) );
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
			
			Team nextTeam = (Team)i.next();
			
			// Assign highest ranked player to lowest ranked team - playerList is sorted in ascending order
			Player bestPlayer = (Player)playerList.get( playerList.size() - 1 );
			
			bestPlayer.setTeam_id(    nextTeam.getTeam_id() );
			bestPlayer.setDraft_pick( pick                  );
			
			// Update player in database
			playerService.updatePlayer( bestPlayer, true, false );
		
			// Remove selected player from list
			playerList.remove( playerList.size() - 1 );
			
			pick++;
		}
	}
	
	private void processTrainingCamp( Schedule event ) throws SQLException {
		
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		List playerList = playerService.getPlayerList();
		
		Iterator i = playerList.iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			player.agePlayer();
			
			playerService.updatePlayer( player, true, false );
		}
		
		// All undrafted rookies age should be incremented as well (but they aren't in training camp so no stat change
		playerService.ageUndraftedRookies();
	}
	
	private void processPreseasonGame( Schedule event ) throws SQLException {
		
		event.parseMatches();
		
		List matches = event.getMatches();
		
		Iterator i = matches.iterator();
		
		while ( i.hasNext() ) {
		
			Match match = (Match)i.next();
			
			try {
				DatabaseImpl.beginTransaction( dbConn );
			
				processMatch( match, event.getScheduled(), TeamGame.gt_Preseason );
			
				DatabaseImpl.endTransaction( dbConn );
			}
			catch ( SQLException e ) {
			
				DatabaseImpl.cancelTransaction( dbConn );
				
				throw e;
			}
		}
	}
	
	private void processRosterCut( Schedule event ) throws SQLException {
		
		List   teamList   = null;
		List   playerList = null;
		
		TeamService   teamService   = new TeamServiceImpl( dbConn, event.getYear() );
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		if ( (teamList = teamService.getTeamList()) == null ) return;
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			if ( (playerList = playerService.getPlayersByTeamId( team.getTeam_id() )) != null ) {
			
				if ( playerList.size() > 10 ) {
				
					Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Projected ) );
					
					for ( int j = 0; j < playerList.size() - 10; ++j ) {
					
						Player player = (Player)playerList.get( j );
						
						player.setReleased( true );
						
						playerService.updatePlayer( player, false, true );
					}
				}
			}
		}
	}
	
	private void processFreeAgentDraft( Schedule event ) throws SQLException {
		
		List   teamList      = null;
		List   freeAgentList = null;
		String lastYear      = String.valueOf(  Integer.parseInt( event.getYear() ) - 1 );
		
		TeamService   teamService   = new TeamServiceImpl( dbConn, event.getYear() );
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		if ( (teamList = teamService.getTeamList()) == null ) return;
		
		// Rank teams - sort list using compareto of team object
		Collections.sort( teamList, new TeamComparator( dbConn, lastYear ) );
		
		boolean player_selected;
		
		do {

			if ( (freeAgentList = playerService.getFreePlayers()) == null ) return;
			
			PlayerComparator pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Projected );
			
			Collections.sort( freeAgentList, pc );
			
			Collections.reverse( freeAgentList );
			
			player_selected = false;

			Iterator i = teamList.iterator();

			while ( i.hasNext() ) {

				Team nextTeam = (Team)i.next();

				List playerList = playerService.getPlayersByTeamId( nextTeam.getTeam_id() );

				Collections.sort( playerList, pc );
				
				Player bestFreeAgent   = (Player)freeAgentList.get( 0 );
				Player worstTeamPlayer = (Player)playerList.get( 0 );
				
				if ( pc.compare( bestFreeAgent, worstTeamPlayer ) > 0 ) {
					
					bestFreeAgent.setTeam_id( nextTeam.getTeam_id() );
					
					playerService.updatePlayer( bestFreeAgent, true, false );
					
					worstTeamPlayer.setReleased( true );
					
					playerService.updatePlayer( worstTeamPlayer, false, true );
					
					freeAgentList.remove( 0 );
					
					player_selected = true;
				}
			}
		}
		while ( player_selected == true );
		
		// Retire all unselected veteran players.
		playerService.retirePlayers();
	}
	
	private void processSeasonGame( Schedule event ) throws SQLException {
		
		event.parseMatches();
		
		List matches = event.getMatches();
		
		Iterator i = matches.iterator();
		
		while ( i.hasNext() ) {
		
			Match match = (Match)i.next();
			
			try {
				DatabaseImpl.beginTransaction( dbConn );
			
				processMatch( match, event.getScheduled(), TeamGame.gt_RegularSeason );
			
				DatabaseImpl.endTransaction( dbConn );
			}
			catch ( SQLException e ) {
			
				DatabaseImpl.cancelTransaction( dbConn );
				
				throw e;
			}
		}
	}
	
	private void processAwards( Schedule event ) throws SQLException {
		
		PlayerScore   platinum = null;
		PlayerScore[] gold     = new PlayerScore[2];
		PlayerScore[] silver   = new PlayerScore[4];
		Player        player   = null;
		
		PlayerService playerService = new PlayerServiceImpl( dbConn, year );
		
		List playerScores = playerService.getPlayerScores( 20 );
		
		Iterator i = playerScores.iterator();
		
		while ( i.hasNext() ) {
		
			PlayerScore playerScore = (PlayerScore)i.next();
			
			if ( platinum == null ) {
			
				platinum = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.PLATINUM_AWARD );
				
				playerService.updatePlayer( player, true, false );
				
				continue;
			}
			
			if ( gold[ playerScore.getConference() ] == null ) {
			
				gold[ playerScore.getConference() ] = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.GOLD_AWARD );
				
				playerService.updatePlayer( player, true, false );
				
				continue;
			}
			
			if ( silver[ playerScore.getDivision() ] == null ) {
			
				silver[ playerScore.getDivision() ] = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.SILVER_AWARD );
				
				playerService.updatePlayer( player, true, false );
				
				continue;
			}
		}
	}
	
	private void setupPlayoffs( Schedule event ) throws SQLException {
	
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		TeamService     teamService     = new TeamServiceImpl( dbConn, year );
		List[]          teams           = new List[4];
		
		for ( int division = 0; division < 4; ++division ) {
		
			List teamList = teamService.getTeamsByDivision( division );
			
			if ( teamList == null ) throw new SQLException( "Missing teams for division " + String.valueOf( division ) );
			
			TeamComparator tc = new TeamComparator( dbConn, year );
			
			Collections.sort( teamList, tc );
			Collections.reverse( teamList );
			
			for ( int i = 0; i < teamList.size(); ++i ) {
			
				Team team = (Team)teamList.get( i );
				
				team.setDivision_rank( i + 1 );
				
				if ( i < 4 ) team.setPlayoff_rank( 1 );
				
				teamService.updateTeam( team );
			}
			
			teams[division] = teamList.subList( 0, 4 );
		}
		
		scheduleService.updatePlayoffSchedule( ScheduleType.DIVISION_PLAYOFF, teams );
	}
	
	private void setupPlayoff( int type ) throws SQLException {
	
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		TeamService     teamService     = new TeamServiceImpl( dbConn, year );
		List[]          teams           = null;
		List            teamList        = null;
		Iterator        i               = null;
		
		switch ( type ) {
		
		case ScheduleType.DIVISION_CHAMPIONSHIP:
			
			teamList = teamService.getTeamsByPlayoffRank( 2 );
			
			teams = new List[4];
			
			teams[0] = new ArrayList();
			teams[1] = new ArrayList();
			teams[2] = new ArrayList();
			teams[3] = new ArrayList();
			
			i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				teams[ team.getDivision() ].add( team );
			}
			
			break;
			
		case ScheduleType.CONFERENCE_CHAMPIONSHIP:
			
			teamList = teamService.getTeamsByPlayoffRank( 3 );
			
			teams = new List[2];
			
			teams[0] = new ArrayList();
			teams[1] = new ArrayList();
			
			i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				teams[ team.getConference() ].add( team );
			}
			
			break;
			
		case ScheduleType.NATC_CHAMPIONSHIP:
			
			teamList = teamService.getTeamsByPlayoffRank( 4 );
			
			teams = new List[1];
			
			teams[0] = new ArrayList();
			
			i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				teams[0].add( team );
			}
			
			break;
		}
		
		if ( teams == null ) return;
		
		for ( int j = 0; j < teams.length; ++j ) {
		
			TeamComparator tc = new TeamComparator( dbConn, year );
			
			if ( tc.compare( teams[j].get( 0 ), teams[j].get( 1 ) ) < 0 ) {
			
				Collections.reverse( teams[j] );
			}
		}
		
		scheduleService.updatePlayoffSchedule( type, teams );
	}
	
	private void processPostseasonGame( Schedule event )  throws SQLException {
	
		event.parseMatches();
		
		List matches = event.getMatches();
		
		Iterator i = matches.iterator();
		
		while ( i.hasNext() ) {
		
			Match match = (Match)i.next();
			
			try {
				DatabaseImpl.beginTransaction( dbConn );
			
				processMatch( match, event.getScheduled(), TeamGame.gt_Postseason );
			
				DatabaseImpl.endTransaction( dbConn );
			}
			catch ( SQLException e ) {
			
				DatabaseImpl.cancelTransaction( dbConn );
				
				throw e;
			}
		}
		
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		
		Schedule nextEvent = scheduleService.getNextScheduleEntry( event );
		
		if ( ! nextEvent.getType().equals( event.getType() ) ) {
		
			setupPlayoff( nextEvent.getType().getValue() );
		}
	}

	private void selectAllstarTeams( Schedule event )  throws SQLException {
	
		int[]  allstarTeamIds = null;
		List   teamList       = null;
		
		TeamService   teamService   = new TeamServiceImpl( dbConn, event.getYear() );
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		allstarTeamIds = teamService.getAllstarTeamIds();
		
		teamList = teamService.getTeamList();
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();

			Player player = playerService.getPlayerById( playerService.selectAllstarForTeam( team.getTeam_id() ) );
			
			playerService.updateAllstarTeamId( player.getPlayer_id(), allstarTeamIds[team.getDivision()] );
		}

		// Update Day 1 Allstar Schedule
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, event.getYear() );
		
		Schedule nextEvent = scheduleService.getNextScheduleEntry( event );
		
		int[] wins;
		
		wins = teamService.getVsDivisionWins( 0, 1 );
		
		if ( wins[0] > wins[1] ) {
		
			int x             = allstarTeamIds[0];
			allstarTeamIds[0] = allstarTeamIds[1];
			allstarTeamIds[1] = x;
		}
		
		wins = teamService.getVsDivisionWins( 2, 3 );
		
		if ( wins[0] > wins[1] ) {
		
			int x             = allstarTeamIds[2];
			allstarTeamIds[2] = allstarTeamIds[3];
			allstarTeamIds[3] = x;
		}
		
		scheduleService.updateAllstarSchedule( nextEvent, allstarTeamIds );
	}
	
	private void processAllstarGame( Schedule event )  throws SQLException {
	
		event.parseMatches();
		
		List matches = event.getMatches();
		
		Iterator i = matches.iterator();
		
		while ( i.hasNext() ) {
		
			Match match = (Match)i.next();
			
			try {
				DatabaseImpl.beginTransaction( dbConn );
			
				processMatch( match, event.getScheduled(), TeamGame.gt_Allstar );
			
				DatabaseImpl.endTransaction( dbConn );
			}
			catch ( SQLException e ) {
			
				DatabaseImpl.cancelTransaction( dbConn );
				
				throw e;
			}
		}
		
		if ( event.getType().getValue() == ScheduleType.ALL_STAR_DAY_1 ) {
			
			// Update Day 2 Allstar Schedule
			TeamService     teamService     = new TeamServiceImpl( dbConn, event.getYear() );
			ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, event.getYear() );
			
			int[]  allstarTeamIds = teamService.getAllstarTeamIds();
			List   allstarTeams   = new ArrayList();
			
			for ( int j = 0; j < 4; ++j ) {
				
				Team team = teamService.getTeamById( allstarTeamIds[j] );
			
				allstarTeams.add( team );
			}
			
			Collections.sort(allstarTeams, new Comparator() {

				public int compare( Object arg0, Object arg1 ) {
					
					Team team1 = (Team)arg0;
					Team team2 = (Team)arg1;
					
					return team1.getWins() - team2.getWins();
				} } );
			
			int[] wins;
			
			wins = teamService.getVsConferenceWins( 
					((Team)allstarTeams.get( 0 )).getConference(),
					((Team)allstarTeams.get( 1 )).getConference() );
			
			if ( wins[0] > wins[1] ) {
			
				allstarTeamIds[0] = ((Team)allstarTeams.get( 1 )).getTeam_id();
				allstarTeamIds[1] = ((Team)allstarTeams.get( 0 )).getTeam_id();
			}
			else {
			
				allstarTeamIds[0] = ((Team)allstarTeams.get( 0 )).getTeam_id();
				allstarTeamIds[1] = ((Team)allstarTeams.get( 1 )).getTeam_id();
			}

			wins = teamService.getVsConferenceWins( 
					((Team)allstarTeams.get( 2 )).getConference(),
					((Team)allstarTeams.get( 3 )).getConference() );
			
			if ( wins[0] > wins[1] ) {
			
				allstarTeamIds[2] = ((Team)allstarTeams.get( 3 )).getTeam_id();
				allstarTeamIds[3] = ((Team)allstarTeams.get( 2 )).getTeam_id();
			}
			else {
			
				allstarTeamIds[2] = ((Team)allstarTeams.get( 2 )).getTeam_id();
				allstarTeamIds[3] = ((Team)allstarTeams.get( 3 )).getTeam_id();
			}
			
			Schedule nextEvent = scheduleService.getNextScheduleEntry( event );
			
			scheduleService.updateAllstarSchedule( nextEvent, allstarTeamIds );
		}
	}
	
	private void processEndOfSeason( Schedule event )  throws SQLException {
	
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		playerService.updateSeasonsPlayed();
	}
	
	public List getGamesByDate( Date datestamp ) throws SQLException {
		
		List games = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		GameView          gameView = null;
		
		try {
			
			ps = DatabaseImpl.getGamesByDateSelectPs( dbConn );
			
			ps.setDate( 1, new java.sql.Date( datestamp.getTime() ) );
			
			dbRs = ps.executeQuery();
			
			int old_game_id = -1;
			
			while ( dbRs.next() ) {
			
				int game_id = dbRs.getInt( "Game_Id" );
				
				if ( game_id != old_game_id ) {
				
					if ( gameView != null ) {
					
						if ( games == null ) games = new ArrayList();
						
						games.add( gameView );
					}
					
					gameView = new GameView();
					
					old_game_id = game_id;
				}
				
				if ( dbRs.getBoolean( "Road" ) ) {
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt( "Team_Id" ) ) );
					gameView.setRoad_team(               dbRs.getString( "Abbrev" )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"   ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt( "Team_Id" ) ) );
					gameView.setHome_team(               dbRs.getString( "Abbrev" )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"   ) ) );
				}
				
				gameView.setGame_id(  new Integer( game_id                       ) );
				gameView.setOvertime( new Boolean( dbRs.getBoolean( "Overtime" ) ) );
			}
			
			if ( gameView != null ) {
				
				if ( games == null ) games = new ArrayList();
				
				games.add( gameView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return games;
	}

	public List getGamesByTeamId( int team_id ) throws SQLException {
		
		List games = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		GameView          gameView = null;
		
		try {
			
			ps = DatabaseImpl.getGamesByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			ps.setInt(    3, team_id );
			
			dbRs = ps.executeQuery();
			
			int old_game_id = -1;
			
			while ( dbRs.next() ) {
			
				int game_id = dbRs.getInt( "Game_Id" );
				
				if ( game_id != old_game_id ) {
				
					if ( gameView != null ) {
					
						if ( games == null ) games = new ArrayList();
						
						games.add( gameView );
					}
					
					gameView = new GameView();
					
					old_game_id = game_id;
				}
				
				if ( dbRs.getBoolean( "Road" ) ) {
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt( "Team_Id" ) ) );
					gameView.setRoad_team(               dbRs.getString( "Abbrev" )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"   ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt( "Team_Id" ) ) );
					gameView.setHome_team(               dbRs.getString( "Abbrev" )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"   ) ) );
				}
				
				gameView.setGame_id(  new Integer( game_id                       ) );
				gameView.setOvertime( new Boolean( dbRs.getBoolean( "Overtime" ) ) );
			}
			
			if ( gameView != null ) {
				
				if ( games == null ) games = new ArrayList();
				
				games.add( gameView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return games;
	}

	public List getRankedTeamsByDivision( int division ) throws SQLException {

		List              teamsList = null;
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamsList = teamService.getTeamsByDivision( division );
		
		TeamComparator tc = new TeamComparator( dbConn, year );
		
		Collections.sort( teamsList, tc );
		
		Collections.reverse( teamsList );
		
		return teamsList;
	}
	
	public List getRankedTeamList() throws SQLException {
	
		List teamsList = null;
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamsList = teamService.getTeamList();
		
		TeamComparator tc = new TeamComparator( dbConn, year );
		
		Collections.sort( teamsList, tc );
		
		return teamsList;
	}
	
	public List getRankedRookieList() throws SQLException {
	
		List playersList = null;
		
		PlayerService playerService = new PlayerServiceImpl( dbConn, year );
		
		playersList = playerService.getRookiePlayerList();
		
		PlayerComparator pc = new PlayerComparator( dbConn, year, PlayerComparator.pcm_Projected );
		
		Collections.sort( playersList, pc );
		
		Collections.reverse( playersList );
		
		return playersList;
	}

	public List getDraftPicks( int start_pick ) throws SQLException {
		
		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getDraftPicksSelectPs( dbConn );
			
			ps.setString(  1, year       );
			ps.setBoolean( 2, true       );
			ps.setInt(     3, start_pick );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				DraftView draftView = new DraftView();
				
				draftView.setTeam_id(    dbRs.getInt(    1 ) );
				draftView.setLocation(   dbRs.getString( 2 ) );
				draftView.setTeam_name(  dbRs.getString( 3 ) );
				draftView.setPlayer_id(  dbRs.getInt(    4 ) );
				draftView.setFirst_name( dbRs.getString( 5 ) );
				draftView.setLast_name(  dbRs.getString( 6 ) );
				draftView.setDraft_pick( dbRs.getInt(    7 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( draftView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getMostImprovedPlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		String            lastYear    = String.valueOf(  Integer.parseInt( year ) - 1 );
		
		try {
			
			ps = DatabaseImpl.getMostImprovedSelectPs( dbConn );
			
			ps.setString(  1, year     );
			ps.setString(  2, lastYear );
			ps.setString(  3, year     );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				TrainingView trainingView = new TrainingView();
				
				trainingView.setPlayer_id(      dbRs.getInt(    1 ) );
				trainingView.setFirst_name(     dbRs.getString( 2 ) );
				trainingView.setLast_name(      dbRs.getString( 3 ) );
				trainingView.setTeam_id(        dbRs.getInt(    4 ) );
				trainingView.setTeam_abbrev(    dbRs.getString( 5 ) );
				trainingView.setSeasons_played( dbRs.getInt(    6 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( trainingView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getStandoutPlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getStandoutPlayersSelectPs( dbConn );
			
			ps.setString(  1, year     );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				TrainingView trainingView = new TrainingView();
				
				trainingView.setPlayer_id(      dbRs.getInt(    1 ) );
				trainingView.setFirst_name(     dbRs.getString( 2 ) );
				trainingView.setLast_name(      dbRs.getString( 3 ) );
				trainingView.setTeam_id(        dbRs.getInt(    4 ) );
				trainingView.setTeam_abbrev(    dbRs.getString( 5 ) );
				trainingView.setSeasons_played( dbRs.getInt(    6 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( trainingView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getStandoutRookies() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getStandoutRookiesSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				TrainingView trainingView = new TrainingView();
				
				trainingView.setPlayer_id(   dbRs.getInt(    1 ) );
				trainingView.setFirst_name(  dbRs.getString( 2 ) );
				trainingView.setLast_name(   dbRs.getString( 3 ) );
				trainingView.setTeam_id(     dbRs.getInt(    4 ) );
				trainingView.setTeam_abbrev( dbRs.getString( 5 ) );
				trainingView.setDraft_pick(  dbRs.getInt(    6 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( trainingView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getReleasedRookiePlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getReleasedRookiesSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			ps.setBoolean( 3, true );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				ReleaseView releaseView = new ReleaseView();
				
				releaseView.setPlayer_id(   dbRs.getInt(    1 ));
				releaseView.setFirst_name(  dbRs.getString( 2 ));
				releaseView.setLast_name(   dbRs.getString( 3 ));
				releaseView.setTeam_id(     dbRs.getInt(    4 ));
				releaseView.setTeam_abbrev( dbRs.getString( 5 ));
				releaseView.setDraft_pick(  dbRs.getInt(    6 ));
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( releaseView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getReleasedVeteranPlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getReleasedVeteransSelectPs( dbConn );
			
			ps.setString(  1, year  );
			ps.setBoolean( 2, false );
			ps.setBoolean( 3, true  );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				ReleaseView releaseView = new ReleaseView();
				
				releaseView.setPlayer_id(      dbRs.getInt(    1 ) );
				releaseView.setFirst_name(     dbRs.getString( 2 ) );
				releaseView.setLast_name(      dbRs.getString( 3 ) );
				releaseView.setTeam_id(        dbRs.getInt(    4 ) );
				releaseView.setTeam_abbrev(    dbRs.getString( 5 ) );
				releaseView.setAge(            dbRs.getInt(    6 ) );
				releaseView.setSeasons_played( dbRs.getInt(    7 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( releaseView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getResignedPlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getResignedPlayersSelectPs( dbConn );
			
			ps.setString(  1, year  );
			ps.setBoolean( 2, true  );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				ResignView resignView = new ResignView();
				
				resignView.setPlayer_id(       dbRs.getInt(    1 ) );
				resignView.setFirst_name(      dbRs.getString( 2 ) );
				resignView.setLast_name(       dbRs.getString( 3 ) );
				resignView.setOld_team_id(     dbRs.getInt(    4 ) );
				resignView.setOld_team_abbrev( dbRs.getString( 5 ) );
				resignView.setNew_team_id(     dbRs.getInt(    6 ) );
				resignView.setNew_team_abbrev( dbRs.getString( 7 ) );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( resignView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}

	public List getRetiredPlayers() throws SQLException {

		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playersList = null;
		
		try {
			
			ps = DatabaseImpl.getRetiredPlayersSelectPs( dbConn );
			
			ps.setString(  1, year  );
			ps.setBoolean( 2, true  );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				RetiredView retiredView = new RetiredView();
				
				retiredView.setPlayer_id(      dbRs.getInt(    1 ) );
				retiredView.setFirst_name(     dbRs.getString( 2 ) );
				retiredView.setLast_name(      dbRs.getString( 3 ) );
				retiredView.setAge(            dbRs.getInt(    4 ) );
				retiredView.setSeasons_played( dbRs.getInt(    5 ) );
				
				// TODO: add extra player info here
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( retiredView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playersList;
	}
	
}
