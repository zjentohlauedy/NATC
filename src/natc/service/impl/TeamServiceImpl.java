package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import natc.data.Constants;
import natc.data.Score;
import natc.data.Team;
import natc.data.TeamDefense;
import natc.data.TeamGame;
import natc.data.TeamOffense;
import natc.service.ManagerService;
import natc.service.PlayerService;
import natc.service.TeamService;
import natc.view.TeamGameView;
import natc.view.TeamInjuryView;
import natc.view.TeamPlayerView;
import natc.view.TeamStatsView;

public class TeamServiceImpl implements TeamService {

	private Connection dbConn = null;
	private String     year   = null;

	//                                               Location          Name           Abbr.,  Time Zone
	private static final String[][] teamData  = { { "Indianapolis",   "Titans",      "IND.", "America/Indianapolis" },
		/**/                                      { "Cincinnati",     "Whirlwind",   "CIN.", "America/New_York"     },
		/**/                                      { "Kansas City",    "Flames",      "K.C.", "America/Chicago"      },
		/**/                                      { "Dallas",         "Rustlers",    "DAL.", "America/Chicago"      },
		/**/                                      { "Washington",     "Olympians",   "WAS.", "America/New_York"     },
		/**/                                      { "Minneapolis",    "Marauders",   "MIN.", "America/Chicago"      },
		/**/                                      { "New Orleans",    "Tigersharks", "N.O.", "America/Chicago"      },
		/**/                                      { "Oakland",        "Aces",        "OAK.", "America/Los_Angeles"  },
		/**/                                      { "Vancouver",      "Comets",      "VAN.", "America/Vancouver"    },
		/**/                                      { "Salt Lake City", "Lightning",   "SLC.", "America/Phoenix"      },
		/**/                                      { "Boston",         "Blacks",      "BOS.", "America/New_York"     },
		/**/                                      { "Pittsburgh",     "Pirahnas",    "PIT.", "America/New_York"     },
		/**/                                      { "San Diego",      "Stingrays",   "S.D.", "America/Los_Angeles"  },
		/**/                                      { "Philadelphia",   "Photons",     "PHI.", "America/New_York"     },
		/**/                                      { "Detroit",        "Thunder",     "DET.", "America/New_York"     },
		/**/                                      { "Atlanta",        "Renegades",   "ATL.", "America/New_York"     },
		/**/                                      { "Baltimore",      "Crabbers",    "BAL.", "America/New_York"     },
		/**/                                      { "St. Louis",      "Juggernauts", "S.L.", "America/Chicago"      },
		/**/                                      { "Orlando",        "Hurricanes",  "ORL.", "America/New_York"     },
		/**/                                      { "Las Vegas",      "Vampires",    "L.V.", "America/Los_Angeles"  },
		/**/                                      { "Miami",          "Voyagers",    "MIA.", "America/New_York"     },
		/**/                                      { "Houston",        "Hammers",     "HOU.", "America/Chicago"      },
		/**/                                      { "Los Angeles",    "Legends",     "L.A.", "America/Los_Angeles"  },
		/**/                                      { "New York",       "Knights",     "N.Y.", "America/New_York"     },
		/**/                                      { "Chicago",        "Goblins",     "CHI.", "America/Chicago"      },
		/**/                                      { "Tampa Bay",      "Terror",      "T.B.", "America/New_York"     },
		/**/                                      { "San Francisco",  "Tsunami",     "S.F.", "America/Los_Angeles"  },
		/**/                                      { "Montreal",       "Dragons",     "MON.", "America/New_York"     },
		/**/                                      { "New Jersey",     "Phantoms",    "N.J.", "America/New_York"     },
		/**/                                      { "Mexico City",    "Aztecs",      "MEX.", "America/Mexico_City"  },
		/**/                                      { "Buffalo",        "Icers",       "BUF.", "America/New_York"     },
		/**/                                      { "Cleveland",      "Scorpions",   "CLE.", "America/New_York"     },
		/**/                                      { "Denver",         "Nukes",       "DEN.", "America/Denver"       },
		/**/                                      { "Seattle",        "Psychotics",  "SEA.", "America/Los_Angeles"  },
		/**/                                      { "Phoenix",        "Eclipse",     "PHX.", "America/Phoenix"      },
		/**/                                      { "Milwaukee",      "Warriors",    "MIL.", "America/Chicago"      },
		/**/                                      { "Kingston",       "Outlaws",     "KIN.", "America/Jamaica"      },
		/**/                                      { "Toronto",        "Overlords",   "TOR.", "America/New_York"     },
		/**/                                      { "Charlotte",      "Serpents",    "CHA.", "America/New_York"     },
		/**/                                      { "Portland",       "Rhinos",      "POR.", "America/Los_Angeles"  } };

	public TeamServiceImpl( Connection dbConn, String year ) {
	
		this.dbConn = dbConn;
		this.year   = year;
	}
	
	private void copyTeamFromResultSet( Team team, ResultSet resultSet ) throws SQLException {

		team.setTeam_id(          resultSet.getInt(      1 ) );
		team.setYear(             resultSet.getString(   2 ) );
		team.setLocation(         resultSet.getString(   3 ) );
		team.setName(             resultSet.getString(   4 ) );
		team.setAbbrev(           resultSet.getString(   5 ) );
		team.setTime_zone(        resultSet.getString(   6 ) );
		team.setGame_time(        resultSet.getInt(      7 ) );
		team.setConference(       resultSet.getInt(      8 ) );
		team.setDivision(         resultSet.getInt(      9 ) );
		team.setAllstar_team(     resultSet.getBoolean( 10 ) );
		team.setPreseason_games(  resultSet.getInt(     11 ) );
		team.setPreseason_wins(   resultSet.getInt(     12 ) );
		team.setPreseason_losses( resultSet.getInt(     13 ) );
		team.setGames(            resultSet.getInt(     14 ) );
		team.setWins(             resultSet.getInt(     15 ) );
		team.setLosses(           resultSet.getInt(     16 ) );
		team.setDivision_wins(    resultSet.getInt(     17 ) );
		team.setDivision_losses(  resultSet.getInt(     18 ) );
		team.setOoc_wins(         resultSet.getInt(     19 ) );
		team.setOoc_losses(       resultSet.getInt(     20 ) );
		team.setOt_wins(          resultSet.getInt(     21 ) );
		team.setOt_losses(        resultSet.getInt(     22 ) );
		team.setRoad_wins(        resultSet.getInt(     23 ) );
		team.setRoad_losses(      resultSet.getInt(     24 ) );
		team.setHome_wins(        resultSet.getInt(     25 ) );
		team.setHome_losses(      resultSet.getInt(     26 ) );
		team.setDivision_rank(    resultSet.getInt(     27 ) );
		team.setPlayoff_rank(     resultSet.getInt(     28 ) );
		team.setPlayoff_games(    resultSet.getInt(     29 ) );
		team.setRound1_wins(      resultSet.getInt(     30 ) );
		team.setRound2_wins(      resultSet.getInt(     31 ) );
		team.setRound3_wins(      resultSet.getInt(     32 ) );
		team.setExpectation(      resultSet.getDouble(  33 ) );
		team.setDrought(          resultSet.getInt(     34 ) );
	}
	
	public void generateTeams() throws SQLException {
		
		if ( year == null ) {
			
			// Generate year string from current date
			Calendar now = Calendar.getInstance();
			
			year = String.valueOf( now.get( Calendar.YEAR ) );
		}
		
		PreparedStatement ps            = null;
		int               team_id       = 0;
		
		try {
			
			DatabaseImpl.beginTransaction( dbConn );
			
			ps = DatabaseImpl.getTeamInsertPs( dbConn );
			
			for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; ++i ) {
			
				ps.setInt(      1, ++team_id                          );
				ps.setString(   2, year                               );
				ps.setString(   3, teamData[i][0]                     );
				ps.setString(   4, teamData[i][1]                     );
				ps.setString(   5, teamData[i][2]                     );
				ps.setString(   6, teamData[i][3]                     );
				ps.setString(   7, teamData[i][4]                     );
				ps.setInt(      8, i / Constants.TEAMS_PER_CONFERENCE );
				ps.setInt(      9, i / Constants.TEAMS_PER_DIVISION   );
				ps.setBoolean( 10, false                              );
				
				ps.executeUpdate();
			}
			
			// All Star Teams
			ps.setInt(      1, ++team_id          );
			ps.setString(   2, year               );
			ps.setString(   3, "Greene"           );
			ps.setString(   4, "All Stars"        );
			ps.setString(   5, "GRN."             );
			ps.setString(   6, "America/New_York" );
			ps.setInt(      7, 965                );
			ps.setInt(      8, 0                  );
			ps.setInt(      9, 0                  );
			ps.setBoolean( 10, true               );
			
			ps.executeUpdate();
			
			ps.setInt(      1, ++team_id          );
			ps.setString(   2, year               );
			ps.setString(   3, "Davis"            );
			ps.setString(   4, "All Stars"        );
			ps.setString(   5, "DVS."             );
			ps.setString(   6, "America/New_York" );
			ps.setInt(      7, 965                );
			ps.setInt(      8, 0                  );
			ps.setInt(      9, 1                  );
			ps.setBoolean( 10, true               );
			
			ps.executeUpdate();

			ps.setInt(      1, ++team_id          );
			ps.setString(   2, year               );
			ps.setString(   3, "Smith"            );
			ps.setString(   4, "All Stars"        );
			ps.setString(   5, "SMI."             );
			ps.setString(   6, "America/New_York" );
			ps.setInt(      7, 965                );
			ps.setInt(      8, 1                  );
			ps.setInt(      9, 2                  );
			ps.setBoolean( 10, true               );
			
			ps.executeUpdate();

			ps.setInt(      1, ++team_id          );
			ps.setString(   2, year               );
			ps.setString(   3, "Lawrence"         );
			ps.setString(   4, "All Stars"        );
			ps.setString(   5, "LAW."             );
			ps.setString(   6, "America/New_York" );
			ps.setInt(      7, 965                );
			ps.setInt(      8, 1                  );
			ps.setInt(      9, 3                  );
			ps.setBoolean( 10, true               );
			
			ps.executeUpdate();
			
			DatabaseImpl.endTransaction( dbConn );
		}
		catch ( SQLException e ) {
		
			DatabaseImpl.cancelTransaction( dbConn );
			
			throw e;
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateTeamsForNewSeason( String last_year ) throws SQLException {
	
		PreparedStatement ps       = null;
		
		try {
			
			ps = DatabaseImpl.getCopyTeamsForNewYearCallPs( dbConn );
			
			ps.setString( 1, last_year );
			ps.setString( 2, this.year );
			
			ps.execute();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}
	
	public List getTeamList() throws SQLException {
	
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		List              teamList = null;
		
		try {
			
			ps = DatabaseImpl.getTeamListSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, false );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
				
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}

	public List getAllstarTeamList() throws SQLException {
	
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		List              teamList = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarTeamListSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
				
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}
	
	public Team getTeamById( int team_id ) throws SQLException {

		Team team    = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getTeamByIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
				
				team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				// Get manager
				ManagerService managerService = new ManagerServiceImpl( dbConn, year );
				
				if ( team.isAllstar_team() ) {
				
					team.setManager( managerService.getManagerByAllstarTeamId( team_id ) );
				}
				else {
				
					team.setManager( managerService.getManagerByTeamId( team_id ) );
				}
				
				// Get players
				PlayerService playerService = new PlayerServiceImpl( dbConn, year );
				
				if ( team.isAllstar_team() ) {
					
					team.setPlayers( playerService.getPlayersByAllstarTeamId( team_id ) );
				}
				else {
					
					team.setPlayers( playerService.getPlayersByTeamId( team_id ) );
				}
				
				if ( team.getPlayers() != null ) {
					
					team.calcTeamRatings();
				}
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return team;
	}

	public List getTeamsByDivision( int division ) throws SQLException {
		
		List              teamList = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
		
			ps = DatabaseImpl.getTeamsByDivisionSelectPs( dbConn );
			
			ps.setString(  1, this.year );
			ps.setInt(     2, division  );
			ps.setBoolean( 3, false     );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}

	public List getTeamsByPlayoffRank( int rank ) throws SQLException {
		
		List              teamList = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
		
			ps = DatabaseImpl.getTeamsByPlayoffRankSelectPs( dbConn );
			
			ps.setString( 1, this.year );
			ps.setInt(    2, rank      );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}
	
	public void updateTeam( Team team ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getTeamUpdatePs( dbConn );
			
			ps.setString(  1, team.getLocation()         );
			ps.setString(  2, team.getName()             );
			ps.setString(  3, team.getAbbrev()           );
			ps.setString(  4, team.getTime_zone()        );
			ps.setInt(     5, team.getGame_time()        );
			ps.setInt(     6, team.getConference()       );
			ps.setInt(     7, team.getDivision()         );
			ps.setBoolean( 8, team.isAllstar_team()      );
			ps.setInt(     9, team.getPreseason_games()  );
			ps.setInt(    10, team.getPreseason_wins()   );
			ps.setInt(    11, team.getPreseason_losses() );
			ps.setInt(    12, team.getGames()            );
			ps.setInt(    13, team.getWins()             );
			ps.setInt(    14, team.getLosses()           );
			ps.setInt(    15, team.getDivision_wins()    );
			ps.setInt(    16, team.getDivision_losses()  );
			ps.setInt(    17, team.getOoc_wins()         );
			ps.setInt(    18, team.getOoc_losses()       );
			ps.setInt(    19, team.getOt_wins()          );
			ps.setInt(    20, team.getOt_losses()        );
			ps.setInt(    21, team.getRoad_wins()        );
			ps.setInt(    22, team.getRoad_losses()      );
			ps.setInt(    23, team.getHome_wins()        );
			ps.setInt(    24, team.getHome_losses()      );
			ps.setInt(    25, team.getDivision_rank()    );
			ps.setInt(    26, team.getPlayoff_rank()     );
			ps.setInt(    27, team.getPlayoff_games()    );
			ps.setInt(    28, team.getRound1_wins()      );
			ps.setInt(    29, team.getRound2_wins()      );
			ps.setInt(    30, team.getRound3_wins()      );
			ps.setDouble( 31, team.getExpectation()      );
			ps.setInt(    32, team.getDrought()          );
			
			ps.setString( 33, this.year         );
			ps.setInt(    34, team.getTeam_id() );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void insertTeamGame( TeamGame teamGame ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getTeamGameInsertPs( dbConn );
			
			ps.setInt(      1,                    teamGame.getGame_id()                        );
			ps.setString(   2,                    teamGame.getYear()                           );
			ps.setDate(     3, new java.sql.Date( teamGame.getDatestamp().getTime()  )         );
			ps.setInt(      4,                    teamGame.getType()                           );
			ps.setInt(      5,                    teamGame.getPlayoff_round()                  );
			ps.setInt(      6,                    teamGame.getTeam_id()                        );
			ps.setInt(      7,                    teamGame.getOpponent()                       );
			ps.setBoolean(  8,                    teamGame.isRoad()                            );
			ps.setBoolean(  9,                    teamGame.isOvertime()                        );
			ps.setBoolean( 10,                    teamGame.isWin()                             );
			ps.setInt(     11,                    teamGame.getScore().getPossessions()         );
			ps.setInt(     12,                    teamGame.getScore().getPossession_time()     );
			ps.setInt(     13,                    teamGame.getScore().getAttempts()            );
			ps.setInt(     14,                    teamGame.getScore().getGoals()               );
			ps.setInt(     15,                    teamGame.getScore().getTurnovers()           );
			ps.setInt(     16,                    teamGame.getScore().getSteals()              );
			ps.setInt(     17,                    teamGame.getScore().getPenalties()           );
			ps.setInt(     18,                    teamGame.getScore().getOffensive_penalties() );
			ps.setInt(     19,                    teamGame.getScore().getPsa()                 );
			ps.setInt(     20,                    teamGame.getScore().getPsm()                 );
			ps.setInt(     21,                    teamGame.getScore().getOt_psa()              );
			ps.setInt(     22,                    teamGame.getScore().getOt_psm()              );
			ps.setInt(     23,                    teamGame.getScore().getPeriod1_score()       );
			ps.setInt(     24,                    teamGame.getScore().getPeriod2_score()       );
			ps.setInt(     25,                    teamGame.getScore().getPeriod3_score()       );
			ps.setInt(     26,                    teamGame.getScore().getPeriod4_score()       );
			ps.setInt(     27,                    teamGame.getScore().getPeriod5_score()       );
			ps.setInt(     28,                    teamGame.getScore().getOvertime_score()      );
			ps.setInt(     29,                    teamGame.getScore().getTotal_score()         );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateTeamGame( TeamGame teamGame ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getTeamGameUpdatePs( dbConn );
			
			ps.setBoolean(  1,                    teamGame.isOvertime()                        );
			ps.setBoolean(  2,                    teamGame.isWin()                             );
			ps.setInt(      3,                    teamGame.getScore().getPossessions()         );
			ps.setInt(      4,                    teamGame.getScore().getPossession_time()     );
			ps.setInt(      5,                    teamGame.getScore().getAttempts()            );
			ps.setInt(      6,                    teamGame.getScore().getGoals()               );
			ps.setInt(      7,                    teamGame.getScore().getTurnovers()           );
			ps.setInt(      8,                    teamGame.getScore().getSteals()              );
			ps.setInt(      9,                    teamGame.getScore().getPenalties()           );
			ps.setInt(     10,                    teamGame.getScore().getOffensive_penalties() );
			ps.setInt(     11,                    teamGame.getScore().getPsa()                 );
			ps.setInt(     12,                    teamGame.getScore().getPsm()                 );
			ps.setInt(     13,                    teamGame.getScore().getOt_psa()              );
			ps.setInt(     14,                    teamGame.getScore().getOt_psm()              );
			ps.setInt(     15,                    teamGame.getScore().getPeriod1_score()       );
			ps.setInt(     16,                    teamGame.getScore().getPeriod2_score()       );
			ps.setInt(     17,                    teamGame.getScore().getPeriod3_score()       );
			ps.setInt(     18,                    teamGame.getScore().getPeriod4_score()       );
			ps.setInt(     19,                    teamGame.getScore().getPeriod5_score()       );
			ps.setInt(     20,                    teamGame.getScore().getOvertime_score()      );
			ps.setInt(     21,                    teamGame.getScore().getTotal_score()         );
			
			ps.setInt(     22,                    teamGame.getGame_id()                        );
			ps.setInt(     23,                    teamGame.getTeam_id()                        );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateTeamStats( Team team, Team opponent, int type ) throws SQLException {

		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;

		// Offense
		TeamOffense teamOffense = new TeamOffense();
		Score       teamScore   = team.getGame().getScore();

		teamOffense.setYear(                year                               );
		teamOffense.setType(                type                               );
		teamOffense.setTeam_id(             team.getTeam_id()                  );
		teamOffense.setGames(               1                                  );
		teamOffense.setPossessions(         teamScore.getPossessions()         );
		teamOffense.setPossession_time(     teamScore.getPossession_time()     );
		teamOffense.setAttempts(            teamScore.getAttempts()            );
		teamOffense.setGoals(               teamScore.getGoals()               );
		teamOffense.setTurnovers(           teamScore.getTurnovers()           );
		teamOffense.setSteals(              teamScore.getSteals()              );
		teamOffense.setPenalties(           teamScore.getPenalties()           );
		teamOffense.setOffensive_penalties( teamScore.getOffensive_penalties() );
		teamOffense.setPsa(                 teamScore.getPsa()                 );
		teamOffense.setPsm(                 teamScore.getPsm()                 );
		teamOffense.setOt_psa(              teamScore.getOt_psa()              );
		teamOffense.setOt_psm(              teamScore.getOt_psm()              );
		teamOffense.setScore(               teamScore.getTotal_score()               );

		// Defense
		TeamDefense teamDefense   = new TeamDefense();
		Score       opponentScore = opponent.getGame().getScore();

		teamDefense.setYear(                year                                   );
		teamDefense.setType(                type                                   );
		teamDefense.setTeam_id(             team.getTeam_id()                      );
		teamDefense.setGames(               1                                      );
		teamDefense.setPossessions(         opponentScore.getPossessions()         );
		teamDefense.setPossession_time(     opponentScore.getPossession_time()     );
		teamDefense.setAttempts(            opponentScore.getAttempts()            );
		teamDefense.setGoals(               opponentScore.getGoals()               );
		teamDefense.setTurnovers(           opponentScore.getTurnovers()           );
		teamDefense.setSteals(              opponentScore.getSteals()              );
		teamDefense.setPenalties(           opponentScore.getPenalties()           );
		teamDefense.setOffensive_penalties( opponentScore.getOffensive_penalties() );
		teamDefense.setPsa(                 opponentScore.getPsa()                 );
		teamDefense.setPsm(                 opponentScore.getPsm()                 );
		teamDefense.setOt_psa(              opponentScore.getOt_psa()              );
		teamDefense.setOt_psm(              opponentScore.getOt_psm()              );
		teamDefense.setScore(               opponentScore.getTotal_score()               );

		try {

			// Offense
			ps1 = DatabaseImpl.getTeamOffenseSelectPs( dbConn );

			ps1.setString( 1, teamOffense.getYear()    );
			ps1.setInt(    2, teamOffense.getType()    );
			ps1.setInt(    3, teamOffense.getTeam_id() );

			dbRs1 = ps1.executeQuery();

			if ( dbRs1.next() ) {

				// Populate values from database
				teamOffense.setGames(               teamOffense.getGames()               + dbRs1.getInt(  1 ) );
				teamOffense.setPossessions(         teamOffense.getPossessions()         + dbRs1.getInt(  2 ) );
				teamOffense.setPossession_time(     teamOffense.getPossession_time()     + dbRs1.getInt(  3 ) );
				teamOffense.setAttempts(            teamOffense.getAttempts()            + dbRs1.getInt(  4 ) );
				teamOffense.setGoals(               teamOffense.getGoals()               + dbRs1.getInt(  5 ) );
				teamOffense.setTurnovers(           teamOffense.getTurnovers()           + dbRs1.getInt(  6 ) );
				teamOffense.setSteals(              teamOffense.getSteals()              + dbRs1.getInt(  7 ) );
				teamOffense.setPenalties(           teamOffense.getPenalties()           + dbRs1.getInt(  8 ) );
				teamOffense.setOffensive_penalties( teamOffense.getOffensive_penalties() + dbRs1.getInt(  9 ) );
				teamOffense.setPsa(                 teamOffense.getPsa()                 + dbRs1.getInt( 10 ) );
				teamOffense.setPsm(                 teamOffense.getPsm()                 + dbRs1.getInt( 11 ) );
				teamOffense.setOt_psa(              teamOffense.getOt_psa()              + dbRs1.getInt( 12 ) );
				teamOffense.setOt_psm(              teamOffense.getOt_psm()              + dbRs1.getInt( 13 ) );
				teamOffense.setScore(               teamOffense.getScore()               + dbRs1.getInt( 14 ) );
				
				// Update the database
				ps2 = DatabaseImpl.getTeamOffenseUpdatePs( dbConn );

				ps2.setInt(     1, teamOffense.getGames()               );
				ps2.setInt(     2, teamOffense.getPossessions()         );
				ps2.setInt(     3, teamOffense.getPossession_time()     );
				ps2.setInt(     4, teamOffense.getAttempts()            );
				ps2.setInt(     5, teamOffense.getGoals()               );
				ps2.setInt(     6, teamOffense.getTurnovers()           );
				ps2.setInt(     7, teamOffense.getSteals()              );
				ps2.setInt(     8, teamOffense.getPenalties()           );
				ps2.setInt(     9, teamOffense.getOffensive_penalties() );
				ps2.setInt(    10, teamOffense.getPsa()                 );
				ps2.setInt(    11, teamOffense.getPsm()                 );
				ps2.setInt(    12, teamOffense.getOt_psa()              );
				ps2.setInt(    13, teamOffense.getOt_psm()              );
				ps2.setInt(    14, teamOffense.getScore()               );
				ps2.setString( 15, teamOffense.getYear()                );
				ps2.setInt(    16, teamOffense.getType()                );
				ps2.setInt(    17, teamOffense.getTeam_id()             );

				ps2.executeUpdate();
			}
			else {

				DatabaseImpl.closeDbRs( dbRs1 );
				DatabaseImpl.closeDbStmt( ps1 );

				// Record not found, insert a new record
				ps1 = DatabaseImpl.getTeamOffenseInsertPs( dbConn );

				ps1.setString(  1, teamOffense.getYear()                );
				ps1.setInt(     2, teamOffense.getType()                );
				ps1.setInt(     3, teamOffense.getTeam_id()             );
				ps1.setInt(     4, teamOffense.getGames()               );
				ps1.setInt(     5, teamOffense.getPossessions()         );
				ps1.setInt(     6, teamOffense.getPossession_time()     );
				ps1.setInt(     7, teamOffense.getAttempts()            );
				ps1.setInt(     8, teamOffense.getGoals()               );
				ps1.setInt(     9, teamOffense.getTurnovers()           );
				ps1.setInt(    10, teamOffense.getSteals()              );
				ps1.setInt(    11, teamOffense.getPenalties()           );
				ps1.setInt(    12, teamOffense.getOffensive_penalties() );
				ps1.setInt(    13, teamOffense.getPsa()                 );
				ps1.setInt(    14, teamOffense.getPsm()                 );
				ps1.setInt(    15, teamOffense.getOt_psa()              );
				ps1.setInt(    16, teamOffense.getOt_psm()              );
				ps1.setInt(    17, teamOffense.getScore()               );

				ps1.executeUpdate();
			}

			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );

			// Defense
			ps1 = DatabaseImpl.getTeamDefenseSelectPs( dbConn );

			ps1.setString( 1, teamDefense.getYear()    );
			ps1.setInt(    2, teamDefense.getType()    );
			ps1.setInt(    3, teamDefense.getTeam_id() );

			dbRs1 = ps1.executeQuery();

			if ( dbRs1.next() ) {

				// Populate values from database
				teamDefense.setGames(               teamDefense.getGames()               + dbRs1.getInt(  1 ) );
				teamDefense.setPossessions(         teamDefense.getPossessions()         + dbRs1.getInt(  2 ) );
				teamDefense.setPossession_time(     teamDefense.getPossession_time()     + dbRs1.getInt(  3 ) );
				teamDefense.setAttempts(            teamDefense.getAttempts()            + dbRs1.getInt(  4 ) );
				teamDefense.setGoals(               teamDefense.getGoals()               + dbRs1.getInt(  5 ) );
				teamDefense.setTurnovers(           teamDefense.getTurnovers()           + dbRs1.getInt(  6 ) );
				teamDefense.setSteals(              teamDefense.getSteals()              + dbRs1.getInt(  7 ) );
				teamDefense.setPenalties(           teamDefense.getPenalties()           + dbRs1.getInt(  8 ) );
				teamDefense.setOffensive_penalties( teamDefense.getOffensive_penalties() + dbRs1.getInt(  9 ) );
				teamDefense.setPsa(                 teamDefense.getPsa()                 + dbRs1.getInt( 10 ) );
				teamDefense.setPsm(                 teamDefense.getPsm()                 + dbRs1.getInt( 11 ) );
				teamDefense.setOt_psa(              teamDefense.getOt_psa()              + dbRs1.getInt( 12 ) );
				teamDefense.setOt_psm(              teamDefense.getOt_psm()              + dbRs1.getInt( 13 ) );
				teamDefense.setScore(               teamDefense.getScore()               + dbRs1.getInt( 14 ) );

				// Update the database
				ps2 = DatabaseImpl.getTeamDefenseUpdatePs( dbConn );

				ps2.setInt(     1, teamDefense.getGames()               );
				ps2.setInt(     2, teamDefense.getPossessions()         );
				ps2.setInt(     3, teamDefense.getPossession_time()     );
				ps2.setInt(     4, teamDefense.getAttempts()            );
				ps2.setInt(     5, teamDefense.getGoals()               );
				ps2.setInt(     6, teamDefense.getTurnovers()           );
				ps2.setInt(     7, teamDefense.getSteals()              );
				ps2.setInt(     8, teamDefense.getPenalties()           );
				ps2.setInt(     9, teamDefense.getOffensive_penalties() );
				ps2.setInt(    10, teamDefense.getPsa()                 );
				ps2.setInt(    11, teamDefense.getPsm()                 );
				ps2.setInt(    12, teamDefense.getOt_psa()              );
				ps2.setInt(    13, teamDefense.getOt_psm()              );
				ps2.setInt(    14, teamDefense.getScore()               );
				ps2.setString( 15, teamDefense.getYear()                );
				ps2.setInt(    16, teamDefense.getType()                );
				ps2.setInt(    17, teamDefense.getTeam_id()             );

				ps2.executeUpdate();
			}
			else {

				DatabaseImpl.closeDbRs( dbRs1 );
				DatabaseImpl.closeDbStmt( ps1 );

				// Record not found, insert a new record
				ps1 = DatabaseImpl.getTeamDefenseInsertPs( dbConn );

				ps1.setString(  1, teamDefense.getYear()                );
				ps1.setInt(     2, teamDefense.getType()                );
				ps1.setInt(     3, teamDefense.getTeam_id()             );
				ps1.setInt(     4, teamDefense.getGames()               );
				ps1.setInt(     5, teamDefense.getPossessions()         );
				ps1.setInt(     6, teamDefense.getPossession_time()     );
				ps1.setInt(     7, teamDefense.getAttempts()            );
				ps1.setInt(     8, teamDefense.getGoals()               );
				ps1.setInt(     9, teamDefense.getTurnovers()           );
				ps1.setInt(    10, teamDefense.getSteals()              );
				ps1.setInt(    11, teamDefense.getPenalties()           );
				ps1.setInt(    12, teamDefense.getOffensive_penalties() );
				ps1.setInt(    13, teamDefense.getPsa()                 );
				ps1.setInt(    14, teamDefense.getPsm()                 );
				ps1.setInt(    15, teamDefense.getOt_psa()              );
				ps1.setInt(    16, teamDefense.getOt_psm()              );
				ps1.setInt(    17, teamDefense.getScore()               );

				ps1.executeUpdate();
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}
	}

	public List getTeamDefenseByTeamId(int team_id) throws SQLException {

		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		List              teamGames = null;
		
		try {
		
			ps = DatabaseImpl.getTeamDefenseByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				TeamStatsView teamStatsView = new TeamStatsView();
				
				teamStatsView.setType(                dbRs.getInt(  1 ) );
				teamStatsView.setGames(               dbRs.getInt(  2 ) );
				teamStatsView.setPossessions(         dbRs.getInt(  3 ) );
				teamStatsView.setPossession_time(     dbRs.getInt(  4 ) );
				teamStatsView.setAttempts(            dbRs.getInt(  5 ) );
				teamStatsView.setGoals(               dbRs.getInt(  6 ) );
				teamStatsView.setTurnovers(           dbRs.getInt(  7 ) );
				teamStatsView.setSteals(              dbRs.getInt(  8 ) );
				teamStatsView.setPenalties(           dbRs.getInt(  9 ) );
				teamStatsView.setOffensive_penalties( dbRs.getInt( 10 ) );
				teamStatsView.setPsa(                 dbRs.getInt( 11 ) );
				teamStatsView.setPsm(                 dbRs.getInt( 12 ) );
				teamStatsView.setOt_psa(              dbRs.getInt( 13 ) );
				teamStatsView.setOt_psm(              dbRs.getInt( 14 ) );
				teamStatsView.setScore(               dbRs.getInt( 15 ) );
				
				if ( teamGames == null ) teamGames = new ArrayList();
				
				teamGames.add( teamStatsView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamGames;
	}

	public List getTeamOffenseByTeamId( int team_id ) throws SQLException {
		
		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		List              teamGames = null;
		
		try {
		
			ps = DatabaseImpl.getTeamOffenseByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				TeamStatsView teamStatsView = new TeamStatsView();
				
				teamStatsView.setType(                dbRs.getInt(  1 ) );
				teamStatsView.setGames(               dbRs.getInt(  2 ) );
				teamStatsView.setPossessions(         dbRs.getInt(  3 ) );
				teamStatsView.setPossession_time(     dbRs.getInt(  4 ) );
				teamStatsView.setAttempts(            dbRs.getInt(  5 ) );
				teamStatsView.setGoals(               dbRs.getInt(  6 ) );
				teamStatsView.setTurnovers(           dbRs.getInt(  7 ) );
				teamStatsView.setSteals(              dbRs.getInt(  8 ) );
				teamStatsView.setPenalties(           dbRs.getInt(  9 ) );
				teamStatsView.setOffensive_penalties( dbRs.getInt( 10 ) );
				teamStatsView.setPsa(                 dbRs.getInt( 11 ) );
				teamStatsView.setPsm(                 dbRs.getInt( 12 ) );
				teamStatsView.setOt_psa(              dbRs.getInt( 13 ) );
				teamStatsView.setOt_psm(              dbRs.getInt( 14 ) );
				teamStatsView.setScore(               dbRs.getInt( 15 ) );
				
				if ( teamGames == null ) teamGames = new ArrayList();
				
				teamGames.add( teamStatsView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamGames;
	}

	public List getTeamHistoryByTeamId(int team_id) throws SQLException {
		
		List              teamList = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
		
			ps = DatabaseImpl.getTeamHistorySelectPs( dbConn );
			
			ps.setInt( 1, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
			
			if ( teamList != null ) {
			
				Team totals = new Team();
				
				totals.setYear( "Total" );
				
				Iterator i = teamList.iterator();
				
				while ( i.hasNext() ) {
				
					Team team = (Team)i.next();
					
					totals.setPreseason_games(  totals.getPreseason_games()  + team.getPreseason_games()  );
					totals.setPreseason_wins(   totals.getPreseason_wins()   + team.getPreseason_wins()   );
					totals.setPreseason_losses( totals.getPreseason_losses() + team.getPreseason_losses() );
					totals.setGames(            totals.getGames()            + team.getGames()            );
					totals.setWins(             totals.getWins()             + team.getWins()             );
					totals.setLosses(           totals.getLosses()           + team.getLosses()           );
					totals.setDivision_wins(    totals.getDivision_wins()    + team.getDivision_wins()    );
					totals.setDivision_losses(  totals.getDivision_losses()  + team.getDivision_losses()  );
					totals.setOoc_wins(         totals.getOoc_wins()         + team.getOoc_wins()         );
					totals.setOoc_losses(       totals.getOoc_losses()       + team.getOoc_losses()       );
					totals.setOt_wins(          totals.getOt_wins()          + team.getOt_wins()          );
					totals.setOt_losses(        totals.getOt_losses()        + team.getOt_losses()        );
					totals.setRoad_wins(        totals.getRoad_wins()        + team.getRoad_wins()        );
					totals.setRoad_losses(      totals.getRoad_losses()      + team.getRoad_losses()      );
					totals.setHome_wins(        totals.getHome_wins()        + team.getHome_wins()        );
					totals.setHome_losses(      totals.getHome_losses()      + team.getHome_losses()      );
					totals.setPlayoff_games(    totals.getPlayoff_games()    + team.getPlayoff_games()    );
					totals.setRound1_wins(      totals.getRound1_wins()      + team.getRound1_wins()      );
					totals.setRound2_wins(      totals.getRound2_wins()      + team.getRound2_wins()      );
					totals.setRound3_wins(      totals.getRound3_wins()      + team.getRound3_wins()      );
				}
				
				teamList.add( totals );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}

	public TeamGameView getHomeGame( int game_id ) throws SQLException {

		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		TeamGameView      teamGameView = null;
		
		try {
		
			ps = DatabaseImpl.getSingleGameSelectPs( dbConn );
			
			ps.setInt(     1, game_id );
			ps.setBoolean( 2, false   );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				teamGameView = new TeamGameView();
				
				teamGameView.setLocation(            dbRs.getString(   1 ) );
				teamGameView.setName(                dbRs.getString(   2 ) );
				teamGameView.setAbbrev(              dbRs.getString(   3 ) );
				teamGameView.setTeam_id(             dbRs.getInt(      4 ) );
				teamGameView.setPossessions(         dbRs.getInt(      5 ) );
				teamGameView.setPossession_time(     dbRs.getInt(      6 ) );
				teamGameView.setAttempts(            dbRs.getInt(      7 ) );
				teamGameView.setGoals(               dbRs.getInt(      8 ) );
				teamGameView.setTurnovers(           dbRs.getInt(      9 ) );
				teamGameView.setSteals(              dbRs.getInt(     10 ) );
				teamGameView.setPenalties(           dbRs.getInt(     11 ) );
				teamGameView.setOffensive_penalties( dbRs.getInt(     12 ) );
				teamGameView.setPsa(                 dbRs.getInt(     13 ) );
				teamGameView.setPsm(                 dbRs.getInt(     14 ) );
				teamGameView.setOt_psa(              dbRs.getInt(     15 ) );
				teamGameView.setOt_psm(              dbRs.getInt(     16 ) );
				teamGameView.setPeriod1_score(       dbRs.getInt(     17 ) );
				teamGameView.setPeriod2_score(       dbRs.getInt(     18 ) );
				teamGameView.setPeriod3_score(       dbRs.getInt(     19 ) );
				teamGameView.setPeriod4_score(       dbRs.getInt(     20 ) );
				teamGameView.setPeriod5_score(       dbRs.getInt(     21 ) );
				teamGameView.setOvertime_score(      dbRs.getInt(     22 ) );
				teamGameView.setTotal_score(         dbRs.getInt(     23 ) );
				teamGameView.setWin(                 dbRs.getBoolean( 24 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamGameView;
	}

	public TeamGameView getRoadGame(int game_id) throws SQLException {

		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		TeamGameView      teamGameView = null;
		
		try {
		
			ps = DatabaseImpl.getSingleGameSelectPs( dbConn );
			
			ps.setInt(     1, game_id );
			ps.setBoolean( 2, true    );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				teamGameView = new TeamGameView();
				
				teamGameView.setLocation(            dbRs.getString(   1 ) );
				teamGameView.setName(                dbRs.getString(   2 ) );
				teamGameView.setAbbrev(              dbRs.getString(   3 ) );
				teamGameView.setTeam_id(             dbRs.getInt(      4 ) );
				teamGameView.setPossessions(         dbRs.getInt(      5 ) );
				teamGameView.setPossession_time(     dbRs.getInt(      6 ) );
				teamGameView.setAttempts(            dbRs.getInt(      7 ) );
				teamGameView.setGoals(               dbRs.getInt(      8 ) );
				teamGameView.setTurnovers(           dbRs.getInt(      9 ) );
				teamGameView.setSteals(              dbRs.getInt(     10 ) );
				teamGameView.setPenalties(           dbRs.getInt(     11 ) );
				teamGameView.setOffensive_penalties( dbRs.getInt(     12 ) );
				teamGameView.setPsa(                 dbRs.getInt(     13 ) );
				teamGameView.setPsm(                 dbRs.getInt(     14 ) );
				teamGameView.setOt_psa(              dbRs.getInt(     15 ) );
				teamGameView.setOt_psm(              dbRs.getInt(     16 ) );
				teamGameView.setPeriod1_score(       dbRs.getInt(     17 ) );
				teamGameView.setPeriod2_score(       dbRs.getInt(     18 ) );
				teamGameView.setPeriod3_score(       dbRs.getInt(     19 ) );
				teamGameView.setPeriod4_score(       dbRs.getInt(     20 ) );
				teamGameView.setPeriod5_score(       dbRs.getInt(     21 ) );
				teamGameView.setOvertime_score(      dbRs.getInt(     22 ) );
				teamGameView.setTotal_score(         dbRs.getInt(     23 ) );
				teamGameView.setWin(                 dbRs.getBoolean( 24 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamGameView;
	}

	public int[] getAllstarTeamIds() throws SQLException {
		
		int[] team_ids = new int[4];
		
		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarTeamIdsSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();
			
			int i = 0;
			
			while ( dbRs.next() ) {
				
				team_ids[i] = dbRs.getInt( 1 );
				
				i++;
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return team_ids;
	}

	public int[] getVsConferenceWins(int conference1, int conference2) throws SQLException {
		
		int[] wins = new int[2];
		
		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		
		try {
			
			ps = DatabaseImpl.getVsConferenceWinsSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, conference1               );
			ps.setInt(    4, conference2               );
			
			dbRs = ps.executeQuery();
			
			
			while ( dbRs.next() ) {
				
				int games = dbRs.getInt( 1 );
				
				wins[0] = dbRs.getInt( 1 );
				wins[1] = games - wins[0];
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return wins;
	}

	public int[] getVsDivisionWins(int division1, int division2) throws SQLException {
		
		int[] wins = new int[2];
		
		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		
		try {
			
			ps = DatabaseImpl.getVsDivisionWinsSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, division1                 );
			ps.setInt(    4, division2                 );
			
			dbRs = ps.executeQuery();
			
			
			while ( dbRs.next() ) {
				
				int games = dbRs.getInt( 1 );
				
				wins[0] = dbRs.getInt( 1 );
				wins[1] = games - wins[0];
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return wins;
	}

	public List getPlayoffTeams() throws SQLException {
		
		List              teamList = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
		
			ps = DatabaseImpl.getPlayoffTeamsSelectPs( dbConn );
			
			ps.setString( 1, this.year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Team team = new Team();
				
				copyTeamFromResultSet( team, dbRs );
				
				if ( teamList == null ) teamList = new ArrayList();
				
				teamList.add( team );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return teamList;
	}

	public void getTeamPlayerData( TeamPlayerView teamPlayer, int gameType ) throws SQLException {
		
		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		
		try {
		
			ps = DatabaseImpl.getTeamPlayerDataSelectPs( dbConn );
			
			ps.setInt(    1, teamPlayer.getPlayer_id() );
			ps.setString( 2, this.year                 );
			ps.setInt(    3, gameType                  );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				teamPlayer.setGames(         dbRs.getInt( 1 ) );
				teamPlayer.setGames_started( dbRs.getInt( 2 ) );
				
				if   ( teamPlayer.getGames() == 0 ) teamPlayer.setTime_per_game( 0 );
				else                                teamPlayer.setTime_per_game( dbRs.getInt( 3 ) / teamPlayer.getGames() );
				
				teamPlayer.setGoals(         dbRs.getInt( 4 ) );
				teamPlayer.setAssists(       dbRs.getInt( 5 ) );
				teamPlayer.setStops(         dbRs.getInt( 6 ) );
				teamPlayer.setSteals(        dbRs.getInt( 7 ) );
				teamPlayer.setPsm(           dbRs.getInt( 8 ) );
				teamPlayer.setPoints(        dbRs.getInt( 9 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateExpectations() throws SQLException {
		
		List teamList = getTeamList();
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			if ( team.getExpectation() == 0 ) team.setExpectation( Team.BASE_EXPECTATION );
			
			if ( team.getPlayoff_rank() == 0 ) {
				
				if ( team.getWins() < 50 ) team.setDrought( team.getDrought() + 1 );
				if ( team.getWins() > 50 ) team.setDrought( 0                     );
			}
			else {
				
				team.setDrought( 0 );
			}
			
			if ( team.getDrought() >= Team.MAX_DROUGHT ) {
			
				team.setExpectation( Team.BASE_EXPECTATION );
				team.setDrought(     0                     );
			}
			else {
			
				team.setExpectation( team.getExpectation() + ((double)team.getPlayoff_rank() / 10.0) );
			}
			
			if ( team.getExpectation() > Team.MAX_EXPECTATION ) team.setExpectation( Team.MAX_EXPECTATION );
			
			updateTeam( team );
		}
	}

	public List getTeamInjuriesByTeamId( int team_id ) throws SQLException {
		
		List injuries = null;
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		

		try {
			
			ps = DatabaseImpl.getInjuriesByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				TeamInjuryView teamInjuryView = new TeamInjuryView();
				
				teamInjuryView.setDatestamp(       dbRs.getDate(    1 ) );
				teamInjuryView.setPlayer_id(       dbRs.getInt(     2 ) );
				teamInjuryView.setFirst_name(      dbRs.getString(  3 ) );
				teamInjuryView.setLast_name(       dbRs.getString(  4 ) );
				teamInjuryView.setOpponent(        dbRs.getInt(     5 ) );
				teamInjuryView.setOpponent_abbrev( dbRs.getString(  6 ) );
				teamInjuryView.setRoad_game(       dbRs.getBoolean( 7 ) );
				teamInjuryView.setGame_id(         dbRs.getInt(     8 ) );
				teamInjuryView.setDuration(        dbRs.getInt(     9 ) );
				
				if ( injuries == null ) injuries = new ArrayList();
				
				injuries.add( teamInjuryView );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}
		
		return injuries;
	}

	public String getAbbrevForTeamId( int team_id ) throws SQLException {

		PreparedStatement ps     = null;
		ResultSet         dbRs   = null;
		String            abbrev = null;
		
		try {
			
			ps = DatabaseImpl.getAbbrevForTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
				
				abbrev = dbRs.getString( 1 );
			}
			
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}	
		
		return abbrev;
	}
	
}
