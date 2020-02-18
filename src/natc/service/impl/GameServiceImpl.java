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
import natc.data.GameState;
import natc.data.Injury;
import natc.data.Manager;
import natc.data.Match;
import natc.data.Player;
import natc.data.PlayerScore;
import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Score;
import natc.data.Team;
import natc.data.TeamGame;
import natc.service.GameService;
import natc.service.ManagerService;
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.GameView;
import natc.view.InjuryView;
import natc.view.PlayerGameView;
import natc.view.PlayoffGameView;

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
		private boolean    usePlayoffs;
		private boolean    allstarTeams;
		
		public TeamComparator( Connection dbConn, String year ) {
		
			this.dbConn       = dbConn;
			this.year         = year;
			this.usePlayoffs  = true;
			this.allstarTeams = false;
		}

		public TeamComparator( Connection dbConn, String year, boolean allStar ) {
		
			this.dbConn       = dbConn;
			this.year         = year;
			this.usePlayoffs  = false;
			this.allstarTeams = allStar;
		}
		
		public int compare( Object arg0, Object arg1 ) {

			Team team0 = (Team)arg0;
			Team team1 = (Team)arg1;

			if ( team0.equals( team1 ) ) return 0;

			if ( team0.getGames() == 0  &&  team1.getGames() == 0 ) return 0;

			if ( usePlayoffs ) {

				// Playoff Ranking is the top priority
				if ( team0.getPlayoff_rank() != team1.getPlayoff_rank() ) {

					return team0.getPlayoff_rank() - team1.getPlayoff_rank();
				}
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

			if ( allstarTeams  ||  team0.getGames() > 5 ) {

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
				
				if   ( allstarTeams ) ps.setInt(     2, TeamGame.gt_Allstar       );
				else                  ps.setInt(     2, TeamGame.gt_RegularSeason );
				
				ps.setInt(     3, team0_id                  );
				ps.setInt(     4, team1_id                  );
				ps.setBoolean( 5, true                      );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x += dbRs.getInt( 1 );
				}
				
				ps = DatabaseImpl.getWinsByMatchSelectPs( dbConn );
				
				ps.setString(  1, year                      );

				if   ( allstarTeams ) ps.setInt(     2, TeamGame.gt_Allstar       );
				else                  ps.setInt(     2, TeamGame.gt_RegularSeason );
				
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

				if   ( allstarTeams ) ps.setInt(     2, TeamGame.gt_Allstar       );
				else                  ps.setInt(     2, TeamGame.gt_RegularSeason );
				
				ps.setInt(    3, team_id );
				
				dbRs = ps.executeQuery();

				if ( dbRs.next() ) {
					
					x += dbRs.getInt( 1 );
				}
				
				ps = DatabaseImpl.getTotalPointsAllowedSelectPs( dbConn );
				
				ps.setString( 1, year    );

				if   ( allstarTeams ) ps.setInt(     2, TeamGame.gt_Allstar       );
				else                  ps.setInt(     2, TeamGame.gt_RegularSeason );
				
				ps.setInt(    3, team_id );
				
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
	
		public static final int pcm_OverallRating     = 0;
		public static final int pcm_Rating            = 1;
		public static final int pcm_PerformanceRating = 2;
		public static final int pcm_Statistical       = 3;
		public static final int pcm_Offensive         = 4;
		public static final int pcm_Defensive         = 5;
		public static final int pcm_Intangible        = 6;
		public static final int pcm_Penalty           = 7;
		public static final int pcm_Balanced          = 8;
		
		private Connection dbConn;
		private String     year;
		private int        mode;
		private boolean    useAge;
		private boolean    useConfidence;
		private boolean    useFatigue;
		private boolean    considerAlternates;
		
		public PlayerComparator( Connection dbConn, String year, int mode ) {
		
			this.dbConn             = dbConn;
			this.year               = year;
			this.mode               = mode;
			this.useAge             = false;
			this.useConfidence      = false;
			this.useFatigue         = false;
			this.considerAlternates = false;
		}

		public PlayerComparator( Connection dbConn,
				/**/             String     year,
				/**/             int        mode,
				/**/             boolean    useAge,
				/**/             boolean    useConfidence,
				/**/             boolean    useFatigue,
				/**/             boolean    considerAlternates ) {
		
			this.dbConn             = dbConn;
			this.year               = year;
			this.mode               = mode;
			this.useAge             = useAge;
			this.useConfidence      = useConfidence;
			this.useFatigue         = useFatigue;
			this.considerAlternates = considerAlternates;
		}

		public int compare( Object arg0, Object arg1 ) {
			
			Player player0 = (Player)arg0;
			Player player1 = (Player)arg1;
			
			if ( considerAlternates  &&  player0.isAllstar_alternate() != player1.isAllstar_alternate() ) return (player0.isAllstar_alternate()) ? 0 : 1;
			
			switch ( mode ) {
			
			case pcm_OverallRating:     return (player0.getOverallRating()                                                > player1.getOverallRating()) ? 1 : -1;
			case pcm_Rating:            return (player0.getAdjustedRating( useAge, useConfidence, useFatigue )            > player1.getAdjustedRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_PerformanceRating: return (player0.getAdjustedPerformanceRating( useAge, useConfidence, useFatigue ) > player1.getAdjustedPerformanceRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Offensive:         return (player0.getAdjustedOffensiveRating( useAge, useConfidence, useFatigue )   > player1.getAdjustedOffensiveRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Defensive:         return (player0.getAdjustedDefensiveRating( useAge, useConfidence, useFatigue )   > player1.getAdjustedDefensiveRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Intangible:        return (player0.getAdjustedIntangibleRating( useAge, useConfidence, useFatigue )  > player1.getAdjustedIntangibleRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Penalty:           return (player0.getAdjustedPenaltyRating( useAge, useConfidence, useFatigue )     > player1.getAdjustedPenaltyRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Balanced:          return (player0.getAdjustedPerformanceRating( useAge, useConfidence, useFatigue ) > player1.getAdjustedPerformanceRating( useAge, useConfidence, useFatigue )) ? 1 : -1;
			case pcm_Statistical:
				
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
	}

	private class ManagerComparator implements Comparator {
	
		public int compare(Object arg0, Object arg1) {
			
			Manager manager0 = (Manager)arg0;
			Manager manager1 = (Manager)arg1;
			
			if ( manager0.getPerformanceRating() != manager1.getPerformanceRating() ) {
			
				return (manager0.getPerformanceRating() > manager1.getPerformanceRating()) ? 1 : -1;
			}
			
			return (manager0.getOverallRating() > manager1.getOverallRating()) ? 1 : -1;
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

	private Player findInjuredPlayer( Team t1, Team t2, List injuries ) {
	
		ArrayList playersInGame = new ArrayList();
		Player    player        = null;
		
		playersInGame.addAll( t1.getPlayers() );
		playersInGame.addAll( t2.getPlayers() );
		
		Iterator i = playersInGame.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			if ( ! p.isPlaying() ) continue;
				
			total_rating += (1.0 - p.getDurability()); // Use the inverse of durability rating
		}
		
		i = playersInGame.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
			
			Player p = (Player)i.next();
			
			if ( ! p.isPlaying() ) continue;
				
			x -= (1.0 - p.getDurability()); // Use the inverse of durability rating
			
			if ( x <= 0 ) {
			
				player = p;
				
				// This is the injured player
				p.injurePlayer( t1.getGame().getDatestamp() );
				
				Injury injury = new Injury();
				
				injury.setGame_id(       t1.getGame().getGame_id() );
				injury.setPlayer_id( p.getPlayer_id()         );
				injury.setDuration(  p.getDuration()          );
				
				if   ( t1.isAllstar_team() ) injury.setTeam_id( p.getAllstar_team_id() );
				else                         injury.setTeam_id( p.getTeam_id()         );
				
				injuries.add( injury );
				
				break;
			}
		}
		
		return player;
	}
	
	private boolean isPenaltyShotGood( Player shooter, Team offense, Team defense ) {
	
		double pbb = 50.0 + (40.0 * shooter.getAdjustedPenalty_shot()) + (10.0 * (offense.getPs_offense() - defense.getPs_defense()));
		
		if ( (Math.random() * 100.0) < pbb ) {
		
			return true;
		}
		
		return false;
	}
	
	private void removeInjuredPlayers( List players ) {
	
		Iterator i = players.iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			if ( player.isInjured() ) i.remove();
		}
	}
	
	private void simulateOvertime( Team homeTeam, Team roadTeam ) {

		homeTeam.getGame().setOvertime( true );
		roadTeam.getGame().setOvertime( true );
		
		ArrayList homeShooters = new ArrayList( homeTeam.getPlayers() );
		ArrayList roadShooters = new ArrayList( roadTeam.getPlayers() );
		
		removeInjuredPlayers( homeShooters );
		removeInjuredPlayers( roadShooters );
		
		// overtime penalty shots - sort the player list by penalty shot attribute
		Collections.sort( homeShooters, new Comparator() { public int compare( Object arg1, Object arg2 ){
			/**/                                                               Player p1 = (Player)arg1;
			/**/                                                               Player p2 = (Player)arg2;
			/**/                                                               return (p1.getAdjustedPenalty_shot() > p2.getAdjustedPenalty_shot()) ? 1 : -1; } });
		Collections.reverse( homeShooters );
		
		Collections.sort( roadShooters, new Comparator() { public int compare( Object arg1, Object arg2 ){
			/**/                                                               Player p1 = (Player)arg1;
			/**/                                                               Player p2 = (Player)arg2;
			/**/                                                               return (p1.getAdjustedPenalty_shot() > p2.getAdjustedPenalty_shot()) ? 1 : -1; } });
		Collections.reverse( roadShooters );

		int homeIdx = 0;
		int roadIdx = 0;
		
		while ( homeTeam.getGame().getScore().getTotal_score() == roadTeam.getGame().getScore().getTotal_score() ) {
		
			Player shooter = (Player)roadShooters.get( roadIdx );
			
			// Player and road team get an attempt
			shooter.getGame().setOt_psa( shooter.getGame().getOt_psa() + 1 );
			roadTeam.getGame().getScore().setOt_psa( roadTeam.getGame().getScore().getOt_psa() + 1 );
			
			// Select the offensive and defensive line ups
			roadTeam.selectOtPenaltyOffense( shooter );
			homeTeam.selectOtPenaltyDefense();
			
			if ( isPenaltyShotGood( shooter, roadTeam, homeTeam ) ) {
				
				// Player and road team get a make
				shooter.getGame().setOt_psm( shooter.getGame().getOt_psm() + 1 );
				roadTeam.getGame().getScore().setOt_psm( roadTeam.getGame().getScore().getOt_psm() + 1 );
				
				// Update road score
				//roadTeam.getGame().getScore().setTotal_score( roadTeam.getGame().getScore().getTotal_score() + 1 );
				roadTeam.getGame().updateScores( 1, 0, true );
			}
			
			shooter = (Player)homeShooters.get( homeIdx );
			
			// Player and home team get an attempt
			shooter.getGame().setOt_psa( shooter.getGame().getOt_psa() + 1 );
			homeTeam.getGame().getScore().setOt_psa( homeTeam.getGame().getScore().getOt_psa() + 1 );

			// Select the offensive and defensive line ups
			homeTeam.selectOtPenaltyOffense( shooter );
			roadTeam.selectOtPenaltyDefense();
			
			if ( isPenaltyShotGood( shooter, homeTeam, roadTeam ) ) {

				// Player and home team get a make
				shooter.getGame().setOt_psm( shooter.getGame().getOt_psm() + 1 );
				homeTeam.getGame().getScore().setOt_psm( homeTeam.getGame().getScore().getOt_psm() + 1 );

				// Update home score
				//homeTeam.getGame().getScore().setTotal_score( homeTeam.getGame().getScore().getTotal_score() + 1 );
				homeTeam.getGame().updateScores( 1, 0, true );
			}
			
			homeIdx++;
			roadIdx++;
			
			// All players have taken a shot, start over from the top
			if ( homeIdx >= homeShooters.size() ) homeIdx = 0;
			if ( roadIdx >= roadShooters.size() ) roadIdx = 0;
		}
	}
	
	private void simulateGame( Team roadTeam, Team homeTeam, List injuries, int game_type ) {
		
		Team    attacker      = null;
		boolean clock_stopped = true;
		
		for ( int period = 1; period <= 5; ++period ) {
		
			if ( game_type == TeamGame.gt_Preseason  &&  period == ((homeTeam.getPreseason_games() / 2) + 2) ) {
			
				// For pre-season games, put in the weakest players after a certain point
				Collections.reverse( homeTeam.getPlayers() );
				Collections.reverse( roadTeam.getPlayers() );
			}
			
			roadTeam.determineActivePlayers( 720 );
			homeTeam.determineActivePlayers( 720 );
			
			if ( period == 1 ) {
			
				roadTeam.markStarters();
				homeTeam.markStarters();
			}
			
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
					
					attacker.getGame().getScore().setAttempts(    attacker.getGame().getScore().getAttempts()    + 1 );
					attacker.getGame().getScore().setGoals(       attacker.getGame().getScore().getGoals()       + 1 );
					//attacker.getGame().getScore().setTotal_score( attacker.getGame().getScore().getTotal_score() + 3 );
					
					attacker.getGame().updateScores( 3, period, false );
					
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
						//attacker.getGame().getScore().setTotal_score( attacker.getGame().getScore().getTotal_score()+ 1 );
						
						attacker.getGame().updateScores( 1, period, false );
						
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
						//attacker.getGame().getScore().setTotal_score( attacker.getGame().getScore().getTotal_score()+ 1 );
						
						attacker.getGame().updateScores( 1, period, false );
						
						shooter.getGame().setPsm( shooter.getGame().getPsm() + 1 );
					}
					
					// Change possession
					attacker = (attacker == homeTeam) ? roadTeam : homeTeam;
					
					clock_stopped = true;
					
					break;
				}
				
				// Check for an injury
				if ( Math.random() > 0.999 ) {
					
					// There was an injury - 1 in 1000 chance - static
					findInjuredPlayer( homeTeam, roadTeam, injuries );
					
					clock_stopped = true;
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
		
		if ( homeTeam.getGame().getScore().getTotal_score() == roadTeam.getGame().getScore().getTotal_score() ) {
			
			simulateOvertime( homeTeam, roadTeam );
		}
	}
	
	public void processMatch( Match match, Date gameDate, int type ) throws SQLException {
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		Team homeTeam = teamService.getTeamById( match.getHome_team_id() );
		Team roadTeam = teamService.getTeamById( match.getRoad_team_id() );
		
		// If the playoff ranks are different then it is the post season and one team already advanced
		if ( homeTeam.getPlayoff_rank() != roadTeam.getPlayoff_rank() ) return;
		
		boolean useAlts = (type == TeamGame.gt_Allstar) ? true : false;
		
		// Sort players on each team by rating.
		switch ( homeTeam.getManager().getStyle() ) {
		
		case Manager.STYLE_OFFENSIVE:  Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Offensive,  true, true, false, useAlts ) );  break;
		case Manager.STYLE_DEFENSIVE:  Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Defensive,  true, true, false, useAlts ) );  break;
		case Manager.STYLE_INTANGIBLE: Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Intangible, true, true, false, useAlts ) );  break;
		case Manager.STYLE_PENALTY:    Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Penalty,    true, true, false, useAlts ) );  break;
		case Manager.STYLE_BALANCED:   Collections.sort( homeTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Balanced,   true, true, false, useAlts ) );  break;
		}

		switch ( roadTeam.getManager().getStyle() ) {
		
		case Manager.STYLE_OFFENSIVE:  Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Offensive,  true, true, false, useAlts ) );  break;
		case Manager.STYLE_DEFENSIVE:  Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Defensive,  true, true, false, useAlts ) );  break;
		case Manager.STYLE_INTANGIBLE: Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Intangible, true, true, false, useAlts ) );  break;
		case Manager.STYLE_PENALTY:    Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Penalty,    true, true, false, useAlts ) );  break;
		case Manager.STYLE_BALANCED:   Collections.sort( roadTeam.getPlayers(), new PlayerComparator( dbConn, year, PlayerComparator.pcm_Balanced,   true, true, false, useAlts ) );  break;
		}
		
		// Sorting puts the lowest rated players in front, so reverse the lists
		Collections.reverse( homeTeam.getPlayers() );
		Collections.reverse( roadTeam.getPlayers() );
		
		int game_id = getNextGameId();

		roadTeam.initTeamGame( homeTeam, game_id, year, type, gameDate, true  );
		homeTeam.initTeamGame( roadTeam, game_id, year, type, gameDate, false );
		
		roadTeam.initPlayerGames();
		homeTeam.initPlayerGames();
		
		List injuries = new ArrayList();
		
		simulateGame( roadTeam, homeTeam, injuries, type );

		Score homeScore = homeTeam.getGame().getScore();
		Score roadScore = roadTeam.getGame().getScore();
		
		if   ( homeScore.getTotal_score() >  roadScore.getTotal_score() ) {
			
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
			
			if ( homeScore.getTotal_score() > roadScore.getTotal_score() ) {
			
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
			
			if ( homeScore.getTotal_score() > roadScore.getTotal_score() ) {
				
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
			
			if ( homeScore.getTotal_score() > roadScore.getTotal_score() ) {
			
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
			
			if ( homeScore.getTotal_score() > roadScore.getTotal_score() ) {
				
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
			
			if ( player.isInjured() ) {
				
				playerService.updatePlayerInjury( player );
			}
		}
		
		i = roadTeam.getPlayers().iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			playerService.insertPlayerGame( player.getGame() );
			
			playerService.updatePlayerStats( player, type );
			
			if ( player.isInjured() ) {
				
				playerService.updatePlayerInjury( player );
			}
		}
		
		i = injuries.iterator();
		
		while ( i.hasNext() ) {
		
			Injury injury = (Injury)i.next();
			
			playerService.insertInjury( injury );
		}
	}

	private boolean newManagerNeeded( Team team ) throws SQLException {
	
		Manager manager = null;
	
		// If the team has no manager, the team needs a new manager
		if ( (manager = team.getManager()) == null ) return true;
		
		// If the team's manager is ready to retire, the team needs a new manager
		if ( manager.readyToRetire() ) return true;
		
		// If the manager hasn't been on the team for 3 years, he can stay
		if ( manager.getSeasons() < 3 ) return false;

		String previousYear = String.valueOf( Integer.parseInt( team.getYear() ) - 1 );
		
		TeamService teamService = new TeamServiceImpl( dbConn, previousYear );
		
		Team previousYearsTeam = teamService.getTeamById( team.getTeam_id() );

		// If the team improved from last year, the manager can stay
		if ( team.getPlayoff_rank() >  previousYearsTeam.getPlayoff_rank() ) return false;
		if ( team.getWins()         >  previousYearsTeam.getWins()         ) return false; 
		
		// If the manager's performance rating is above the team's expectation, he can stay
		if ( manager.getPerformanceRating() >= team.getExpectation() ) return false;
		
		// Time for a new manager
		return true;
	}
	
	public void initializeDatabase() throws SQLException {
		
		TeamService teamService         = new TeamServiceImpl(     dbConn, year );
		ManagerService managerService   = new ManagerServiceImpl(  dbConn, year );
		PlayerService playerService     = new PlayerServiceImpl(   dbConn, year );
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		
		teamService.generateTeams();
		managerService.generateManagers();
		playerService.generatePlayers();
		scheduleService.generateSchedule();
		
		// Now that all the data has been created, assign managers and players to teams
		List teamList    = teamService.getTeamList();
		List managerList = managerService.getFreeManagers();
		List playerList  = playerService.getFreePlayers();
		
		Collections.shuffle( teamList                             );
		Collections.sort(    managerList, new ManagerComparator() );
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			// pick the manager with the highest ratings from the pool of available managers
			Manager bestManager = (Manager)managerList.get( managerList.size() - 1 );

			bestManager.setTeam_id( team.getTeam_id() );
			bestManager.setNew_hire( true );
			bestManager.setScore( 0 );
			bestManager.setSeasons( 0 );
			
			managerService.updateManager( bestManager );
			
			team.setManager( bestManager );
			
			// Remove selected manager from list
			managerList.remove( managerList.size() - 1 );
		}
		
		for ( int j = 0; j < Constants.PLAYERS_PER_TEAM; ++j ) {
		
			Collections.reverse( teamList );
			
			i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				// Sort list based on managerial style
				switch ( team.getManager().getStyle() ) {
				
				case Manager.STYLE_OFFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, year, PlayerComparator.pcm_Offensive  ) );  break;
				case Manager.STYLE_DEFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, year, PlayerComparator.pcm_Defensive  ) );  break;
				case Manager.STYLE_INTANGIBLE: Collections.sort( playerList, new PlayerComparator( dbConn, year, PlayerComparator.pcm_Intangible ) );  break;
				case Manager.STYLE_PENALTY:    Collections.sort( playerList, new PlayerComparator( dbConn, year, PlayerComparator.pcm_Penalty    ) );  break;
				case Manager.STYLE_BALANCED:   Collections.sort( playerList, new PlayerComparator( dbConn, year, PlayerComparator.pcm_Balanced   ) );  break;
				}
				
				// Assign highest ranked player to team - playerList is sorted in ascending order
				Player bestPlayer = (Player)playerList.get( playerList.size() - 1 );
				
				bestPlayer.setTeam_id( team.getTeam_id() );
				
				// Update player in database
				playerService.updatePlayer( bestPlayer );
			
				// Remove selected player from list
				playerList.remove( playerList.size() - 1 );
				
			}
		}
	}
	
	public void startNewSeason( String lastYear ) throws SQLException {
	
		// Create team records for new season
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamService.updateTeamsForNewSeason( lastYear );
		
		// Create manager records for new season
		ManagerService managerService = new ManagerServiceImpl( dbConn, year );
		
		managerService.updateManagersForNewSeason( lastYear );
		
		//Create player records for new season
		PlayerService playerservice = new PlayerServiceImpl( dbConn, year );
		
		playerservice.updatePlayersForNewSeason( lastYear );
		
		// Generate a new schedule
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, year );
		
		scheduleService.generateSchedule();
	}
	
	public void processScheduleEvent( Schedule event ) throws SQLException {
	
		// Check for injured players that are ready to return
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		playerService.clearInjuries( event.getScheduled() );
		
		switch ( event.getType().getValue() ) {
		
		case ScheduleType.BEGINNING_OF_SEASON:     /* Nothing to do here */        break;
		case ScheduleType.MANAGER_CHANGES:         processManagerChanges( event ); break;
		case ScheduleType.PLAYER_CHANGES:          processPlayerChanges( event );  break;
		case ScheduleType.ROOKIE_DRAFT_ROUND_1:    processRookieDraft( event );    break;
		case ScheduleType.ROOKIE_DRAFT_ROUND_2:    processRookieDraft( event );    break;
		case ScheduleType.TRAINING_CAMP:           processTrainingCamp( event );   break;
		case ScheduleType.PRESEASON:               processPreseasonGame( event );  break;
		case ScheduleType.END_OF_PRESEASON:        /* Nothing to do here */        break;
		case ScheduleType.ROSTER_CUT:              processRosterCut( event );      break;
		case ScheduleType.REGULAR_SEASON:          processSeasonGame( event );     break;
		case ScheduleType.END_OF_REGULAR_SEASON:   processFinalRankings( event );  break;
		case ScheduleType.AWARDS:                  processAwards( event );         break;
		case ScheduleType.POSTSEASON:              setupPlayoffs( event );         break;
		case ScheduleType.DIVISION_PLAYOFF:        processPostseasonGame( event ); break;
		case ScheduleType.DIVISION_CHAMPIONSHIP:   processPostseasonGame( event ); break;
		case ScheduleType.CONFERENCE_CHAMPIONSHIP: processPostseasonGame( event ); break;
		case ScheduleType.NATC_CHAMPIONSHIP:       processPostseasonGame( event ); break;
		case ScheduleType.END_OF_POSTSEASON:       /* Nothing to do here */        break;
		case ScheduleType.ALL_STARS:               selectAllstarTeams( event );    break;
		case ScheduleType.ALL_STAR_DAY_1:          processAllstarGame( event );    break;
		case ScheduleType.ALL_STAR_DAY_2:          processAllstarGame( event );    break;
		case ScheduleType.END_OF_ALLSTAR_GAMES:    /* Nothing to do here */        break;
		case ScheduleType.END_OF_SEASON:           processEndOfSeason( event );    break;
		}
		
		ScheduleService scheduleService = new ScheduleServiceImpl( dbConn, event.getYear() );
		
		scheduleService.completeScheduleEvent( event );
	}
	
	private void processManagerChanges( Schedule event ) throws SQLException {
	
		List   teamList    = null;
		List   managerList = null;
		String lastYear    = String.valueOf(  Integer.parseInt( event.getYear() ) - 1 );

		TeamService    teamService    = new TeamServiceImpl( dbConn, lastYear );
		PlayerService  playerService  = null;
		ManagerService managerService = new ManagerServiceImpl( dbConn, event.getYear() );

		managerService.ageManagers();

		// Generate 2 more managers from retired players from 10 years ago
		int previousYear = Integer.parseInt( event.getYear() ) - 10;

		List playerList = null;

		playerService = new PlayerServiceImpl( dbConn, String.valueOf( previousYear ) );

		if ( (playerList = playerService.getManagerialCandidates()) != null ) {

			Iterator i = playerList.iterator();

			while ( i.hasNext() ) {

				Player player = (Player)i.next();

				managerService.generateManagerFromPlayer( player );
			}
		} else {

			// Just make 2 new managers
			managerService.generateManager( Manager.STARTING_AGE );
			managerService.generateManager( Manager.STARTING_AGE );
		}

		// Make 3 more managers to total 5 new for the season
		managerService.generateManager( Manager.STARTING_AGE );
		managerService.generateManager( Manager.STARTING_AGE );
		managerService.generateManager( Manager.STARTING_AGE );

		// Get list of teams
		// Sort by last year's records like rookie draft, or by team rating if first year
		if ( (teamList = teamService.getTeamList()) != null ) {

			// Rank teams - sort list using compareto of team object
			Collections.sort( teamList, new TeamComparator( dbConn, lastYear ) );
		}
		else {
		
			// No teams last year, this must be the first season, get this years teams and sort by rating descending
			teamService = new TeamServiceImpl( dbConn, event.getYear() );
			
			if ( (teamList = teamService.getTeamList()) == null ) return;
			
			Iterator i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				team.setPlayers( playerService.getPlayersByTeamId( team.getTeam_id() ) );
				
				team.calcTeamRatings();
			}
			
			Collections.sort( teamList, new Comparator() { public int compare( Object arg1, Object arg2 ){
				/**/                                                           Team t1 = (Team)arg1;
				/**/                                                           Team t2 = (Team)arg2;
				/**/                                                           return ((t1.getOffense() + t1.getDefense() + t1.getDiscipline()) > (t2.getOffense() + t2.getDefense() + t2.getDiscipline())) ? 1 : -1; } } );
		}
		
		if ( (managerList = managerService.getFreeManagers()) == null ) return;

		// Go through the free managers searching for managers that are ready to retire and remove them
		Iterator i = managerList.iterator();
		
		while ( i.hasNext() ) {
		
			Manager manager = (Manager)i.next();
			
			if ( manager.readyToRetire() ) {
			
				manager.setRetired( true );
				
				managerService.updateManager( manager );
				
				i.remove();
			}
		}

		// Go through each team and remove managers if necessary and return them to the pool
		i = teamList.iterator();
		
		while ( i.hasNext() ) {
			
			Team nextTeam = (Team)i.next();
			
			nextTeam.setManager( managerService.getManagerByTeamId( nextTeam.getTeam_id() ) );
			
			if ( newManagerNeeded( nextTeam ) ) {
			
				// Remove the current manager
				Manager currentManager = nextTeam.getManager();

				if ( currentManager != null ) {
				
					currentManager.setTeam_id( 0 );
					
					if ( currentManager.readyToRetire() ) {
					
						currentManager.setRetired( true );
						currentManager.setFormer_team_id( nextTeam.getTeam_id() );
					}
					else {
						
						currentManager.setReleased( true );
						currentManager.setFormer_team_id( nextTeam.getTeam_id() );
						
						managerList.add( currentManager );
					}
					
					managerService.updateManager( currentManager );
					
					nextTeam.setManager( null );
				}
			}
		}
		
		// Rank managers - sort list using manager comparator of manager object
		Collections.sort( managerList, new ManagerComparator() );

		i = teamList.iterator();
		
		while ( i.hasNext() ) {
			
			Team nextTeam = (Team)i.next();
			
			Manager currentManager = nextTeam.getManager();
			
			// For each team, determine if a new manager is needed
			if ( currentManager == null ) {
			
				int idx = managerList.size() - 1;
				
				// pick the manager with the highest ratings from the pool of available managers
				Manager bestManager = (Manager)managerList.get( idx );
				
				// If the best manager happens to be the one the team just fired, move to the next one
				if ( bestManager.getFormer_team_id() == nextTeam.getTeam_id() ) {
				
					idx--;
					
					bestManager = (Manager)managerList.get( idx );
				}
				
				bestManager.setTeam_id( nextTeam.getTeam_id() );
				bestManager.setNew_hire( true );
				bestManager.setScore( 0 );
				bestManager.setSeasons( 0 );
				
				managerService.updateManager( bestManager );
				
				// Remove selected manager from list
				managerList.remove( idx );
			}
		}
	}

	private void processPlayerChanges( Schedule event ) throws SQLException {
		
		TeamService    teamService     = new TeamServiceImpl(    dbConn, event.getYear() );
		ManagerService managerService  = new ManagerServiceImpl( dbConn, event.getYear() );
		PlayerService  playerService   = new PlayerServiceImpl(  dbConn, event.getYear() );
		List           teamList        = null;
		List           freeAgentList   = null;
		boolean        player_selected = false;
		
		// First check for players on teams that want to retire and retire them
		playerService.retireTeamPlayers();
		
		// Next process free agency
		if ( (teamList      = teamService.getTeamList())      == null ) return;
		if ( (freeAgentList = playerService.getFreePlayers()) == null ) return;
		
		do {

			player_selected = false;
			
			// Shuffle the team list every time through for random goodness
			Collections.shuffle( teamList );

			Iterator i = teamList.iterator();

			while ( i.hasNext() ) {

				Team nextTeam = (Team)i.next();

				if ( nextTeam.getManager() == null ) {
					
					nextTeam.setManager( managerService.getManagerByTeamId( nextTeam.getTeam_id() ) );
				}
				
				if ( nextTeam.getPlayers() == null ) {
				
					nextTeam.setPlayers( playerService.getPlayersByTeamId( nextTeam.getTeam_id() ) );
				}
				
				PlayerComparator pc = null;
				
				// Sorter players on each team by rating.
				switch ( nextTeam.getManager().getStyle() ) {
				
				case Manager.STYLE_OFFENSIVE:  pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Offensive,  true, false, false, false );  break;
				case Manager.STYLE_DEFENSIVE:  pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Defensive,  true, false, false, false );  break;
				case Manager.STYLE_INTANGIBLE: pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Intangible, true, false, false, false );  break;
				case Manager.STYLE_PENALTY:    pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Penalty,    true, false, false, false );  break;
				case Manager.STYLE_BALANCED:   pc = new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Balanced,   true, false, false, false );  break;
				}
				
				Collections.sort( nextTeam.getPlayers(), pc );
				Collections.sort( freeAgentList,         pc );
				Collections.reverse( freeAgentList          );
				
				Player bestFreeAgent   = (Player)freeAgentList.get( 0 );
				Player worstTeamPlayer = (Player)nextTeam.getPlayers().get( 0 );
				
				/*
				 * If the team is short one or more players (due to retirement) just select
				 * the best free agent for the team.
				 */
				if ( nextTeam.getPlayers().size() < Constants.PLAYERS_PER_TEAM ) {
				
					bestFreeAgent.setTeam_id( nextTeam.getTeam_id() );
					bestFreeAgent.setSigned( true );
					bestFreeAgent.setFree_agent( false );
					
					playerService.updatePlayer( bestFreeAgent );
					
					freeAgentList.remove( 0 );
					
					nextTeam.getPlayers().add( bestFreeAgent );
					
					player_selected = true;
				}
				else if ( pc.compare( bestFreeAgent, worstTeamPlayer ) > 0 ) {
					
					bestFreeAgent.setTeam_id( nextTeam.getTeam_id() );
					bestFreeAgent.setSigned( true );
					bestFreeAgent.setFree_agent( false );
					
					playerService.updatePlayer( bestFreeAgent );
					
					worstTeamPlayer.setTeam_id( 0 );
					worstTeamPlayer.setReleased( true );
					worstTeamPlayer.setSigned( false );
					worstTeamPlayer.setFree_agent( true );
					
					// Only update released by if the player was not previously released
					if ( worstTeamPlayer.getFormer_team_id() == 0 ) worstTeamPlayer.setFormer_team_id( nextTeam.getTeam_id() );
					
					playerService.updatePlayer( worstTeamPlayer );
					
					freeAgentList.remove( 0 );
					nextTeam.getPlayers().remove( 0 );
					
					freeAgentList.add( worstTeamPlayer );
					nextTeam.getPlayers().add( bestFreeAgent );
					
					player_selected = true;
				}
			}
		}
		while ( player_selected == true );
		
		// Finally retire those players that are ready and no longer on a team
		playerService.retireFreePlayers();
	}

	private void processRookieDraft( Schedule event ) throws SQLException {
		
		// Get list of teams from previous season
		List   teamList   = null;
		List   playerList = null;
		String lastYear   = String.valueOf(  Integer.parseInt( event.getYear() ) - 1 );
		int    pick       = 0;
		
		TeamService    teamService    = new TeamServiceImpl(    dbConn, lastYear        );
		PlayerService  playerService  = new PlayerServiceImpl(  dbConn, event.getYear() );
		ManagerService managerService = new ManagerServiceImpl( dbConn, event.getYear() );
		
		if ( (teamList = teamService.getTeamList()) != null ) {
		
			// Rank teams - sort list using compareto of team object
			Collections.sort( teamList, new TeamComparator( dbConn, lastYear ) );
		}
		else {
		
			// No teams last year, this must be the first season, get this years teams and sort by rating descending
			teamService = new TeamServiceImpl( dbConn, event.getYear() );
			
			if ( (teamList = teamService.getTeamList()) == null ) return;
			
			Iterator i = teamList.iterator();
			
			while ( i.hasNext() ) {
			
				Team team = (Team)i.next();
				
				team.setPlayers( playerService.getPlayersByTeamId( team.getTeam_id() ) );
				
				team.calcTeamRatings();
			}
			
			Collections.sort( teamList, new Comparator() { public int compare( Object arg1, Object arg2 ){
				/**/                                                           Team t1 = (Team)arg1;
				/**/                                                           Team t2 = (Team)arg2;
				/**/                                                           return ((t1.getOffense() + t1.getDefense() + t1.getDiscipline()) > (t2.getOffense() + t2.getDefense() + t2.getDiscipline())) ? 1 : -1; } } );
		}
		
		if ( event.getType().getValue() == ScheduleType.ROOKIE_DRAFT_ROUND_1 ) {
			
			// Generate 100 new players for this year and no team_id
			for ( int i = 0; i < 80; ++i ) {
			
				playerService.generatePlayer( true, Player.STARTING_AGE );
			}
			
			pick = 1;
		}
		else {
		
			pick = 41;
		}
		
		// Get list of remaining undrafted rookies
		if ( (playerList = playerService.getUndraftedRookies()) == null ) return;
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
			
			Team nextTeam = (Team)i.next();
			
			if ( nextTeam.getManager() == null ) {
			
				nextTeam.setManager( managerService.getManagerByTeamId( nextTeam.getTeam_id() ) );
			}

			// Sort list based on managerial style
			switch ( nextTeam.getManager().getStyle() ) {
			
			case Manager.STYLE_OFFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Offensive  ) );  break;
			case Manager.STYLE_DEFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Defensive  ) );  break;
			case Manager.STYLE_INTANGIBLE: Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Intangible ) );  break;
			case Manager.STYLE_PENALTY:    Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Penalty    ) );  break;
			case Manager.STYLE_BALANCED:   Collections.sort( playerList, new PlayerComparator( dbConn, lastYear, PlayerComparator.pcm_Balanced   ) );  break;
			}
			
			// Assign highest ranked player to lowest ranked team - playerList is sorted in ascending order
			Player bestPlayer = (Player)playerList.get( playerList.size() - 1 );
			
			bestPlayer.setTeam_id(    nextTeam.getTeam_id() );
			bestPlayer.setDraft_pick( pick                  );
			
			// Update player in database
			playerService.updatePlayer( bestPlayer );
		
			// Remove selected player from list
			playerList.remove( playerList.size() - 1 );
			
			pick++;
		}
	}
	
	private void processTrainingCamp( Schedule event ) throws SQLException {
		
		PlayerService playerService = new PlayerServiceImpl( dbConn, event.getYear() );
		
		playerService.agePlayers();
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
		
		TeamService    teamService    = new TeamServiceImpl( dbConn, event.getYear() );
		PlayerService  playerService  = new PlayerServiceImpl( dbConn, event.getYear() );
		ManagerService managerService = new ManagerServiceImpl( dbConn, event.getYear() );
		
		if ( (teamList = teamService.getTeamList()) == null ) return;
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			team.setManager( managerService.getManagerByTeamId( team.getTeam_id() ) );
			
			if ( (playerList = playerService.getPlayersByTeamId( team.getTeam_id() )) != null ) {
			
				if ( playerList.size() > Constants.PLAYERS_PER_TEAM ) {

					// Sorter players on each team by rating.
					switch ( team.getManager().getStyle() ) {
					
					case Manager.STYLE_OFFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Offensive,  true, false, false, false ) );  break;
					case Manager.STYLE_DEFENSIVE:  Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Defensive,  true, false, false, false ) );  break;
					case Manager.STYLE_INTANGIBLE: Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Intangible, true, false, false, false ) );  break;
					case Manager.STYLE_PENALTY:    Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Penalty,    true, false, false, false ) );  break;
					case Manager.STYLE_BALANCED:   Collections.sort( playerList, new PlayerComparator( dbConn, event.getYear(), PlayerComparator.pcm_Balanced,   true, false, false, false ) );  break;
					}

					for ( int j = 0; j < playerList.size() - Constants.PLAYERS_PER_TEAM; ++j ) {
					
						Player player = (Player)playerList.get( j );
						
						player.setTeam_id( 0 );
						player.setSigned( false );
						player.setReleased( true );
						player.setFormer_team_id( team.getTeam_id() );
						
						playerService.updatePlayer( player );
					}
				}
			}
		}
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

	private void processFinalRankings( Schedule event ) throws SQLException {
	
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
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
		}
	}
	
	private void processAwards( Schedule event ) throws SQLException {
		
		PlayerScore   platinum = null;
		PlayerScore[] gold     = new PlayerScore[2];
		PlayerScore[] silver   = new PlayerScore[4];
		Player        player   = null;
		
		ManagerService managerService = new ManagerServiceImpl( dbConn, year );
		PlayerService  playerService  = new PlayerServiceImpl(  dbConn, year );
		
		managerService.selectManagerOfTheYear();
		
		List playerScores = playerService.getPlayerScores( 50 );
		
		Iterator i = playerScores.iterator();
		
		while ( i.hasNext() ) {
		
			PlayerScore playerScore = (PlayerScore)i.next();
			
			if ( platinum == null ) {
			
				platinum = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.PLATINUM_AWARD );
				
				playerService.updatePlayer( player );
				
				continue;
			}
			
			if ( gold[ playerScore.getConference() ] == null ) {
			
				gold[ playerScore.getConference() ] = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.GOLD_AWARD );
				
				playerService.updatePlayer( player );
				
				continue;
			}
			
			if ( silver[ playerScore.getDivision() ] == null ) {
			
				silver[ playerScore.getDivision() ] = playerScore;
				
				player = playerService.getPlayerById( playerScore.getPlayer_id() );
				
				player.setAward( PlayerScore.SILVER_AWARD );
				
				playerService.updatePlayer( player );
				
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
			
			Collections.sort(teamList, new Comparator() {

				public int compare( Object arg0, Object arg1 ) {
					
					Team team1 = (Team)arg0;
					Team team2 = (Team)arg1;
					
					return team1.getDivision_rank() - team2.getDivision_rank();
				} } );

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
	
		int[]  allstarTeamIds     = null;
		List   teamList           = null;
		Manager[] allstarManagers = new Manager[4];
		
		TeamService    teamService    = new TeamServiceImpl( dbConn, event.getYear() );
		PlayerService  playerService  = new PlayerServiceImpl( dbConn, event.getYear() );
		ManagerService managerService = new ManagerServiceImpl( dbConn, event.getYear() );
		
		allstarTeamIds = teamService.getAllstarTeamIds();
		
		teamList = teamService.getTeamList();
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();

			Player player = playerService.getPlayerById( playerService.selectAllstarForTeam( team.getTeam_id() ) );
			
			playerService.updateAllstarTeamId( player.getPlayer_id(), allstarTeamIds[team.getDivision()], false );
			
			if ( allstarManagers[ team.getDivision() ] == null ) {
			
				allstarManagers[ team.getDivision() ] = managerService.getBestManagerByDivision( team.getDivision() );
				
				managerService.updateAllstarTeamId( allstarManagers[ team.getDivision() ].getManager_id(), allstarTeamIds[ team.getDivision() ] );
			}
		}

		// Choose Alternates by Division
		for ( int j = 0; j < Constants.NUMBER_OF_DIVISIONS; ++j ) {
		
			List playerList = playerService.selectAllstarAlternatesForDivision( j );
			
			i = playerList.iterator();
			
			while ( i.hasNext() ) {
			
				Player player = (Player)i.next();
				
				playerService.updateAllstarTeamId( player.getPlayer_id(), allstarTeamIds[j], true );
			}
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
	
		TeamService    teamService    = new TeamServiceImpl(    dbConn, event.getYear() );
		ManagerService managerService = new ManagerServiceImpl( dbConn, event.getYear() );
		PlayerService  playerService  = new PlayerServiceImpl(  dbConn, event.getYear() );
		
		teamService.updateExpectations();
		managerService.updateSeasons();
		managerService.updateScore();
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
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setRoad_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setHome_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
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
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setRoad_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setHome_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				
				gameView.setGame_id(  new Integer( game_id                        ) );
				gameView.setDate(                  dbRs.getDate(    "Datestamp"   ) );
				gameView.setOvertime( new Boolean( dbRs.getBoolean( "Overtime"  ) ) );
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

	public List getGamesByTeamIdAndType( int team_id, int type ) throws SQLException {
		
		List games = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		GameView          gameView = null;
		
		try {
			
			ps = DatabaseImpl.getGamesByTeamIdAndTypeSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, type    );
			ps.setInt(    3, team_id );
			ps.setInt(    4, team_id );
			
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
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setRoad_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt( "Team_Id"       ) ) );
					gameView.setHome_team(               dbRs.getString( "Abbrev"       )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				
				gameView.setGame_id(  new Integer( game_id                        ) );
				gameView.setDate(                  dbRs.getDate(    "Datestamp"   ) );
				gameView.setOvertime( new Boolean( dbRs.getBoolean( "Overtime"  ) ) );
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

	public List getGamesByPlayerIdAndType( int player_id, int type ) throws SQLException {
		
		List games = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getGamesByPlayerIdAndTypeSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, player_id );
			ps.setInt(    3, type      );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerGameView playerGameView = new PlayerGameView();
				
				playerGameView.setOpponent(            dbRs.getInt(      1 ) );
				playerGameView.setRoad(                dbRs.getBoolean(  2 ) );
				playerGameView.setOpponent_abbrev(     dbRs.getString(   3 ) );
				playerGameView.setDatestamp(           dbRs.getDate(     4 ) );
				playerGameView.setGame_id(             dbRs.getInt(      5 ) );
				playerGameView.setInjured(             dbRs.getBoolean(  6 ) );
				playerGameView.setStarted(             dbRs.getBoolean(  7 ) );
				playerGameView.setPlaying_time(        dbRs.getInt(      8 ) );
				playerGameView.setAttempts(            dbRs.getInt(      9 ) );
				playerGameView.setGoals(               dbRs.getInt(     10 ) );
				playerGameView.setAssists(             dbRs.getInt(     11 ) );
				playerGameView.setTurnovers(           dbRs.getInt(     12 ) );
				playerGameView.setStops(               dbRs.getInt(     13 ) );
				playerGameView.setSteals(              dbRs.getInt(     14 ) );
				playerGameView.setPenalties(           dbRs.getInt(     15 ) );
				playerGameView.setOffensive_penalties( dbRs.getInt(     16 ) );
				playerGameView.setPsa(                 dbRs.getInt(     17 ) );
				playerGameView.setPsm(                 dbRs.getInt(     18 ) );
				playerGameView.setOt_psa(              dbRs.getInt(     19 ) );
				playerGameView.setOt_psm(              dbRs.getInt(     20 ) );
				playerGameView.setPoints(              dbRs.getInt(     21 ) );
				
				if ( games == null ) games = new ArrayList();
				
				games.add( playerGameView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return games;
	}

	public GameView getChampionshipGame() throws SQLException {
	
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		GameView          gameView = null;
		
		try {
			
			ps = DatabaseImpl.getChampionshipGameByYearSelectPs( dbConn );
			
			ps.setString( 1, year                   );
			ps.setInt(    2, TeamGame.gt_Postseason );
			ps.setInt(    3, 4                      );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {

				if ( gameView == null ) gameView = new GameView();
				
				if ( dbRs.getBoolean( "Road" ) ) {
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt(   "Team_Id"     ) ) );
					gameView.setRoad_team(               dbRs.getString(  "Location"    )   );
					gameView.setRoad_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setRoad_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt(   "Team_Id"     ) ) );
					gameView.setHome_team(               dbRs.getString(  "Location"    )   );
					gameView.setHome_score( new Integer( dbRs.getInt(     "Total_Score" ) ) );
					gameView.setHome_win(   new Boolean( dbRs.getBoolean( "Win"         ) ) );
				}
				
				gameView.setGame_id(  new Integer( dbRs.getInt(     "Game_Id"  ) ) );
				gameView.setOvertime( new Boolean( dbRs.getBoolean( "Overtime" ) ) );
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return gameView;
	}
	
	public List getRankedTeamsByDivision( int division ) throws SQLException {

		List              teamsList = null;
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamsList = teamService.getTeamsByDivision( division );
		
		TeamComparator tc = new TeamComparator( dbConn, year );
		
		Collections.sort( teamsList, tc );
		
		Collections.reverse( teamsList );

		Iterator i = teamsList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			PreparedStatement ps          = null;
			ResultSet         dbRs        = null;
			
			try {
				
				ps = DatabaseImpl.getWinsByStreakPs( dbConn );
				
				ps.setInt(     1, TeamGame.gt_RegularSeason );
				ps.setString(  2, year                      );
				ps.setInt(     3, team.getTeam_id()         );
				ps.setInt(     4, 10                        );
				
				dbRs = ps.executeQuery();
				
				if ( dbRs.next() ) {
				
					team.setStreak_wins(   dbRs.getInt( 1 )   );
					team.setStreak_losses( Math.min( 10, team.getGames() ) - team.getStreak_wins() );
				}

				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			finally {

				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
		}

		return teamsList;
	}
	
	public List getRankedAllstarTeams() throws SQLException {
		
		List teamsList = null;
		
		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		teamsList = teamService.getAllstarTeamList();
		
		TeamComparator tc = new TeamComparator( dbConn, year, true );
		
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
		
		PlayerComparator pc = new PlayerComparator( dbConn, year, PlayerComparator.pcm_Rating );
		
		Collections.sort( playersList, pc );
		
		Collections.reverse( playersList );
		
		return playersList;
	}

	public List getInjuriesByDate(Date datestamp) throws SQLException {

		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		List              injuriesList = null;
		
		try {
			
			ps = DatabaseImpl.getInjuriesByDateSelectPs( dbConn );
			
			ps.setString( 1,                    year                  );
			ps.setString( 2,                    year                  );
			ps.setDate(   3, new java.sql.Date( datestamp.getTime() ) );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				InjuryView injuryView = new InjuryView();
				
				injuryView.setPlayer_id(   dbRs.getInt(    1 ) );
				injuryView.setFirst_name(  dbRs.getString( 2 ) );
				injuryView.setLast_name(   dbRs.getString( 3 ) );
				injuryView.setTeam_id(     dbRs.getInt(    4 ) );
				injuryView.setTeam_abbrev( dbRs.getString( 5 ) );
				injuryView.setDuration(    dbRs.getInt(    6 ) );
				
				if ( injuriesList == null ) injuriesList = new ArrayList();
				
				injuriesList.add( injuryView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return injuriesList;
	}

	public List getChampionships() throws SQLException {

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		GameView          gameView = null;
		List              games    = null;
		
		try {
			
			ps = DatabaseImpl.getChampionshipGamesSelectPs( dbConn );
			
			ps.setInt( 1, 4 );
			
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
					
					gameView.setRoad_team_id( new Integer( dbRs.getInt(     "Team_Id"     ) ) );
					gameView.setRoad_team(                 dbRs.getString(  "Location"    )   );
					gameView.setRoad_team_name(            dbRs.getString(  "Name"        )   );
					gameView.setRoad_score( new Integer(   dbRs.getInt(     "Total_Score" ) ) );
					gameView.setRoad_win(   new Boolean(   dbRs.getBoolean( "Win"         ) ) );
				}
				else {
					
					gameView.setHome_team_id( new Integer( dbRs.getInt(     "Team_Id"     ) ) );
					gameView.setHome_team(                 dbRs.getString(  "Location"    )   );
					gameView.setHome_team_name(            dbRs.getString(  "Name"        )   );
					gameView.setHome_score( new Integer(   dbRs.getInt(     "Total_Score" ) ) );
					gameView.setHome_win(   new Boolean(   dbRs.getBoolean( "Win"         ) ) );
				}
				
				gameView.setGame_id(  new Integer( game_id                       ) );
				gameView.setDate(                  dbRs.getDate(    "Datestamp"  ) );
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

	public List getPlayoffGameInfo() throws SQLException {

		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		List              playoffGames = null;
		int               gameNumber   = 0;
		int               lastRound    = 0;
		int               lastTeam     = 0;
		
		try {
			
			ps = DatabaseImpl.getPlayoffGameInfoSelectPs( dbConn );
			
			ps.setString( 1, year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayoffGameView playoffGameView = new PlayoffGameView();
				
				playoffGameView.setPlayoff_round( dbRs.getInt(     1 ) );
				playoffGameView.setTeam_id(       dbRs.getInt(     2 ) );
				playoffGameView.setGame_id(       dbRs.getInt(     3 ) );
				playoffGameView.setConference(    dbRs.getInt(     4 ) );
				playoffGameView.setDivision(      dbRs.getInt(     5 ) );
				playoffGameView.setDivision_rank( dbRs.getInt(     6 ) );
				playoffGameView.setRoad(          dbRs.getBoolean( 7 ) );
				playoffGameView.setWin(           dbRs.getBoolean( 8 ) );
				
				if ( lastRound != playoffGameView.getPlayoff_round()  ||  lastTeam != playoffGameView.getTeam_id() ) {
				
					lastRound  = playoffGameView.getPlayoff_round();
					lastTeam   = playoffGameView.getTeam_id();
					gameNumber = 1;
				}
				
				playoffGameView.setGame_num( gameNumber++ );
				
				if ( playoffGames == null ) playoffGames = new ArrayList();
				
				playoffGames.add( playoffGameView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playoffGames;
	}

	public GameState getGameState( int game_id ) throws SQLException {

		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		GameState         gameState = null;
		
		try {
			
			ps = DatabaseImpl.getGameStateSelectPs( dbConn );
			
			ps.setInt( 1, game_id );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
				
				gameState = new GameState( game_id );
				
				gameState.setPeriod(         dbRs.getInt(     1 ) );
				gameState.setSequence(       dbRs.getInt(     2 ) );
				gameState.setOvertime(       dbRs.getBoolean( 3 ) );
				gameState.setTime_remaining( dbRs.getInt(     4 ) );
				gameState.setClock_stopped(  dbRs.getBoolean( 5 ) );
				gameState.setPossession(     dbRs.getInt(     6 ) );
				gameState.setLast_event(     dbRs.getString(  7 ) );
			}
			
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}	
		
		return gameState;
	}
	
}
