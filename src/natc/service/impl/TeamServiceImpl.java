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
import natc.data.Player;
import natc.data.Score;
import natc.data.Team;
import natc.data.TeamDefense;
import natc.data.TeamGame;
import natc.data.TeamOffense;
import natc.service.PlayerService;
import natc.service.TeamService;
import natc.view.TeamGameView;
import natc.view.TeamPlayerView;
import natc.view.TeamStatsView;

public class TeamServiceImpl implements TeamService {

	private Connection dbConn = null;
	private String     year   = null;
	
	//                                               Location          Name           Abbrev.
	private static final String[][] teamData  = { { "Indianapolis",   "Titans",      "IND." },
		/**/                                      { "Cincinnati",     "Whirlwind",   "CIN." },
		/**/                                      { "Kansas City",    "Flames",      "K.C." },
		/**/                                      { "Dallas",         "Rustlers",    "DAL." },
		/**/                                      { "Washington",     "Olympians",   "WAS." },
		/**/                                      { "Minneapolis",    "Marauders",   "MIN." },
		/**/                                      { "New Orleans",    "Tigersharks", "N.O." },
		/**/                                      { "Oakland",        "Aces",        "OAK." },
		/**/                                      { "Vancouver",      "Comets",      "VAN." },
		/**/                                      { "Salt Lake City", "Lightning",   "SLC." },
		/**/                                      { "Boston",         "Blacks",      "BOS." },
		/**/                                      { "Pittsburgh",     "Pirahnas",    "PIT." },
		/**/                                      { "San Diego",      "Stingrays",   "S.D." },
		/**/                                      { "Philadelphia",   "Photons",     "PHI." },
		/**/                                      { "Detroit",        "Thunder",     "DET." },
		/**/                                      { "Atlanta",        "Renegades",   "ATL." },
		/**/                                      { "Baltimore",      "BlueCrabs",   "BAL." },
		/**/                                      { "St. Louis",      "Juggernauts", "S.L." },
		/**/                                      { "Orlando",        "Hurricanes",  "ORL." },
		/**/                                      { "Las Vegas",      "Vampires",    "L.V." },
		/**/                                      { "Miami",          "Voyagers",    "MIA." },
		/**/                                      { "Houston",        "Hammers",     "HOU." },
		/**/                                      { "Los Angeles",    "Legends",     "L.A." },
		/**/                                      { "New York",       "Knights",     "N.Y." },
		/**/                                      { "Chicago",        "Goblins",     "CHI." },
		/**/                                      { "Tampa Bay",      "Terror",      "T.B." },
		/**/                                      { "San Francisco",  "Tsunami",     "S.F." },
		/**/                                      { "Montreal",       "Dragons",     "MON." },
		/**/                                      { "New Jersey",     "Phantoms",    "N.J." },
		/**/                                      { "Mexico City",    "Aztecs",      "MEX." },
		/**/                                      { "Buffalo",        "Icers",       "BUF." },
		/**/                                      { "Cleveland",      "Scorpions",   "CLE." },
		/**/                                      { "Denver",         "Nukes",       "DEN." },
		/**/                                      { "Seattle",        "Psychotics",  "SEA." },
		/**/                                      { "Phoenix",        "Eclipse",     "PHX." },
		/**/                                      { "Milwaukee",      "Warriors",    "MIL." },
		/**/                                      { "Kingston",       "Outlaws",     "KIN." },
		/**/                                      { "Toronto",        "Overlords",   "TOR." },
		/**/                                      { "Charlotte",      "Serpents",    "CHA." },
		/**/                                      { "Portland",       "Rhinos",      "POR." } };
	
	public TeamServiceImpl( Connection dbConn, String year ) {
	
		this.dbConn = dbConn;
		this.year   = year;
	}
	
	public void generateTeams() throws SQLException {
		
		if ( year == null ) {
			
			// Generate year string from current date
			Calendar now = Calendar.getInstance();
			
			year = String.valueOf( now.get( Calendar.YEAR ) );
		}
		
		PreparedStatement ps            = null;
		PlayerService     playerService = new PlayerServiceImpl( dbConn, year );
		int               team_id       = 0;
		
		try {
			
			DatabaseImpl.beginTransaction( dbConn );
			
			ps = DatabaseImpl.getTeamInsertPs( dbConn );
			
			for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; ++i ) {
			
				team_id = i + 1;
				
				ps.setInt(    1, team_id                            );
				ps.setString( 2, year                               );
				ps.setString( 3, teamData[i][0]                     );
				ps.setString( 4, teamData[i][1]                     );
				ps.setString( 5, teamData[i][2]                     );
				ps.setInt(    6, i / Constants.TEAMS_PER_CONFERENCE );
				ps.setInt(    7, i / Constants.TEAMS_PER_DIVISION   );
				
				ps.executeUpdate();

				for ( int j = 0; j < Constants.PLAYERS_PER_TEAM; ++j ) {
					
					Player player = playerService.generatePlayer( true, team_id );
					
					player.setAge( (int)Math.floor( (Math.random() * 12.0) + 18.0 ) );
					player.setRookie( false );
					
					playerService.updatePlayer( player, true, false );
				}
			}
			
			team_id++;
			
			// All Star Teams
			ps.setInt(    1, team_id     );
			ps.setString( 2, year        );
			ps.setString( 3, "Greene"    );
			ps.setString( 4, "All Stars" );
			ps.setString( 5, "GRN."      );
			ps.setInt(    6, 0           );
			ps.setInt(    7, 0           );
			
			ps.executeUpdate();
			
			team_id++;
			
			ps.setInt(    1, team_id     );
			ps.setString( 2, year        );
			ps.setString( 3, "Davis"     );
			ps.setString( 4, "All Stars" );
			ps.setString( 5, "DVS."      );
			ps.setInt(    6, 0           );
			ps.setInt(    7, 1           );
			
			ps.executeUpdate();

			team_id++;
			
			ps.setInt(    1, team_id     );
			ps.setString( 2, year        );
			ps.setString( 3, "Smith"     );
			ps.setString( 4, "All Stars" );
			ps.setString( 5, "SMI."      );
			ps.setInt(    6, 1           );
			ps.setInt(    7, 2           );
			
			ps.executeUpdate();

			team_id++;
			
			ps.setInt(    1, team_id     );
			ps.setString( 2, year        );
			ps.setString( 3, "Lawrence"  );
			ps.setString( 4, "All Stars" );
			ps.setString( 5, "LAW."      );
			ps.setInt(    6, 1           );
			ps.setInt(    7, 3           );
			
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
			
			ps.setString( 1, year );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
				
				Team team = new Team();
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
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
		List players = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getTeamByIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
				
				team = new Team();
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
				// Get players
				PlayerService playerService = new PlayerServiceImpl( dbConn, year );
				
				if ( team.getName().equals( "All Stars" ) ) {
					
					players = playerService.getPlayersByAllstarTeamId( team_id );
				}
				else {
					
					players = playerService.getPlayersByTeamId( team_id );
				}
				
				if ( players != null ) {
					
					team.setPlayers( players );

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
			
			ps.setString( 1, this.year );
			ps.setInt(    2, division  );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Team team = new Team();
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
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
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
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
			ps.setInt(     4, team.getConference()       );
			ps.setInt(     5, team.getDivision()         );
			ps.setInt(     6, team.getPreseason_games()  );
			ps.setInt(     7, team.getPreseason_wins()   );
			ps.setInt(     8, team.getPreseason_losses() );
			ps.setInt(     9, team.getGames()            );
			ps.setInt(    10, team.getWins()             );
			ps.setInt(    11, team.getLosses()           );
			ps.setInt(    12, team.getDivision_wins()    );
			ps.setInt(    13, team.getDivision_losses()  );
			ps.setInt(    14, team.getOoc_wins()         );
			ps.setInt(    15, team.getOoc_losses()       );
			ps.setInt(    16, team.getOt_wins()          );
			ps.setInt(    17, team.getOt_losses()        );
			ps.setInt(    18, team.getRoad_wins()        );
			ps.setInt(    19, team.getRoad_losses()      );
			ps.setInt(    20, team.getHome_wins()        );
			ps.setInt(    21, team.getHome_losses()      );
			ps.setInt(    22, team.getDivision_rank()    );
			ps.setInt(    23, team.getPlayoff_rank()     );
			ps.setInt(    24, team.getPlayoff_games()    );
			ps.setInt(    25, team.getRound1_wins()      );
			ps.setInt(    26, team.getRound2_wins()      );
			ps.setInt(    27, team.getRound3_wins()      );
			
			ps.setString( 28, this.year         );
			ps.setInt(    29, team.getTeam_id() );
			
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
			ps.setInt(     23,                    teamGame.getScore().getScore()               );
			
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
		teamOffense.setScore(               teamScore.getScore()               );

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
		teamDefense.setScore(               opponentScore.getScore()               );

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
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
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
				teamGameView.setScore(               dbRs.getInt(     17 ) );
				teamGameView.setWin(                 dbRs.getBoolean( 18 ) );
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
				teamGameView.setScore(               dbRs.getInt(     17 ) );
				teamGameView.setWin(                 dbRs.getBoolean( 18 ) );
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
			
			ps.setString( 1, year       );
			ps.setString( 2, "All Stars" );
			
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
				
				team.setTeam_id(          dbRs.getInt(     1 ) );
				team.setYear(             dbRs.getString(  2 ) );
				team.setLocation(         dbRs.getString(  3 ) );
				team.setName(             dbRs.getString(  4 ) );
				team.setAbbrev(           dbRs.getString(  5 ) );
				team.setConference(       dbRs.getInt(     6 ) );
				team.setDivision(         dbRs.getInt(     7 ) );
				team.setPreseason_games(  dbRs.getInt(     8 ) );
				team.setPreseason_wins(   dbRs.getInt(     9 ) );
				team.setPreseason_losses( dbRs.getInt(    10 ) );
				team.setGames(            dbRs.getInt(    11 ) );
				team.setWins(             dbRs.getInt(    12 ) );
				team.setLosses(           dbRs.getInt(    13 ) );
				team.setDivision_wins(    dbRs.getInt(    14 ) );
				team.setDivision_losses(  dbRs.getInt(    15 ) );
				team.setOoc_wins(         dbRs.getInt(    16 ) );
				team.setOoc_losses(       dbRs.getInt(    17 ) );
				team.setOt_wins(          dbRs.getInt(    18 ) );
				team.setOt_losses(        dbRs.getInt(    19 ) );
				team.setRoad_wins(        dbRs.getInt(    20 ) );
				team.setRoad_losses(      dbRs.getInt(    21 ) );
				team.setHome_wins(        dbRs.getInt(    22 ) );
				team.setHome_losses(      dbRs.getInt(    23 ) );
				team.setDivision_rank(    dbRs.getInt(    24 ) );
				team.setPlayoff_rank(     dbRs.getInt(    25 ) );
				team.setPlayoff_games(    dbRs.getInt(    26 ) );
				team.setRound1_wins(      dbRs.getInt(    27 ) );
				team.setRound2_wins(      dbRs.getInt(    28 ) );
				team.setRound3_wins(      dbRs.getInt(    29 ) );
				
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

	public void getTeamPlayerData( TeamPlayerView teamPlayer ) throws SQLException {
		
		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		
		try {
		
			ps = DatabaseImpl.getTeamPlayerDataSelectPs( dbConn );
			
			ps.setInt(    1, teamPlayer.getPlayer_id() );
			ps.setString( 2, this.year                 );
			ps.setInt(    3, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				teamPlayer.setGames(         dbRs.getInt( 1 ) );
				teamPlayer.setGoals(         dbRs.getInt( 3 ) );
				teamPlayer.setAssists(       dbRs.getInt( 4 ) );
				teamPlayer.setStops(         dbRs.getInt( 5 ) );
				teamPlayer.setSteals(        dbRs.getInt( 6 ) );
				teamPlayer.setPsm(           dbRs.getInt( 7 ) );
				
				if   ( teamPlayer.getGames() == 0 ) teamPlayer.setTime_per_game( 0 );
				else                                teamPlayer.setTime_per_game( dbRs.getInt( 2 ) / teamPlayer.getGames() );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
	}
	
}
