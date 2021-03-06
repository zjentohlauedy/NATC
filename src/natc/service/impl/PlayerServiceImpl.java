package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import natc.data.Constants;
import natc.data.Injury;
import natc.data.Player;
import natc.data.PlayerGame;
import natc.data.PlayerScore;
import natc.data.PlayerStats;
import natc.data.Team;
import natc.data.TeamGame;
import natc.service.PlayerService;
import natc.service.TeamService;
import natc.view.DraftView;
import natc.view.PlayerAllstarView;
import natc.view.PlayerAwardsView;
import natc.view.PlayerGameView;
import natc.view.PlayerInjuryView;
import natc.view.PlayerStatsView;
import natc.view.ReleaseView;
import natc.view.PlayerView;
import natc.view.RookieInfoView;
import natc.view.StringView;
import natc.view.TrainingView;

public class PlayerServiceImpl implements PlayerService {

	private Connection dbConn = null;
	private String     year   = null;
	
	public PlayerServiceImpl( Connection dbConn, String year ) {
		
		this.dbConn = dbConn;
		this.year   = year;
	}
	
	private void copyPlayerFromResultSet( Player player, ResultSet resultSet ) throws SQLException {
	
		player.setPlayer_id(       resultSet.getInt(        1 ) );
		player.setTeam_id(         resultSet.getInt(        2 ) );
		player.setYear(            resultSet.getString(     3 ) );
		player.setFirst_name(      resultSet.getString(     4 ) );
		player.setLast_name(       resultSet.getString(     5 ) );
		player.setAge(             resultSet.getInt(        6 ) );
		player.setScoring(         resultSet.getDouble(     7 ) );
		player.setPassing(         resultSet.getDouble(     8 ) );
		player.setBlocking(        resultSet.getDouble(     9 ) );
		player.setTackling(        resultSet.getDouble(    10 ) );
		player.setStealing(        resultSet.getDouble(    11 ) );
		player.setPresence(        resultSet.getDouble(    12 ) );
		player.setDiscipline(      resultSet.getDouble(    13 ) );
		player.setPenalty_shot(    resultSet.getDouble(    14 ) );
		player.setPenalty_offense( resultSet.getDouble(    15 ) );
		player.setPenalty_defense( resultSet.getDouble(    16 ) );
		player.setEndurance(       resultSet.getDouble(    17 ) );
		player.setConfidence(      resultSet.getDouble(    18 ) );
		player.setVitality(        resultSet.getDouble(    19 ) );
		player.setDurability(      resultSet.getDouble(    20 ) );
		player.setRookie(          resultSet.getBoolean(   21 ) );
		player.setInjured(         resultSet.getBoolean(   22 ) );
		player.setReturn_date(     resultSet.getDate(      23 ) );
		player.setFree_agent(      resultSet.getBoolean(   24 ) );
		player.setSigned(          resultSet.getBoolean(   25 ) );
		player.setReleased(        resultSet.getBoolean(   26 ) );
		player.setRetired(         resultSet.getBoolean(   27 ) );
		player.setFormer_team_id(  resultSet.getInt(       28 ) );
		player.setAllstar_team_id( resultSet.getInt(       29 ) );
		player.setAllstar_alternate( resultSet.getBoolean( 30 ) );
		player.setAward(           resultSet.getInt(       31 ) );
		player.setDraft_pick(      resultSet.getInt(       32 ) );
		player.setSeasons_played(  resultSet.getInt(       33 ) );
	}
	
	private int getNextPlayerId() throws SQLException {
	
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		int               id   = 0;
		
		try {
			
			ps = DatabaseImpl.getNextPlayerIdSelectPs( dbConn );
			
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
	
	private String[] generatePlayerName() throws SQLException {
		
		String[]          names = new String[2];
		PreparedStatement ps    = null;
		ResultSet         dbRs  = null;
		
		try {
			
			do {
				
				ps = DatabaseImpl.getRandomFirstNameSelectPs( dbConn );
				
				dbRs = ps.executeQuery();
				
				if ( dbRs.next() ) {
					
					names[0] = dbRs.getString( 1 );
				}
				else {
				
					throw new SQLException( "Cannot find player first name." );
				}
				
				ps = DatabaseImpl.getRandomLastNameSelectPs( dbConn );
				
				dbRs = ps.executeQuery();
				
				if ( dbRs.next() ) {
					
					names[1] = dbRs.getString( 1 );
				}
				else {
				
					throw new SQLException( "Cannot find player last name." );
				}
				
				// Make sure it is not a duplicate
				ps = DatabaseImpl.getPlayerOrManagerByNameSelectPs( dbConn );
			
				ps.setString( 1, names[0] );
				ps.setString( 2, names[1] );
				ps.setString( 3, names[0] );
				ps.setString( 4, names[1] );
			
				dbRs = ps.executeQuery();
			}
			while ( dbRs.next() );
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return names;
	}
	
	public Player generatePlayer( boolean rookie, int age ) throws SQLException {
		
		PreparedStatement ps     = null;
		Player            player = null;
		
		try {
			
			int      player_id = getNextPlayerId();
			String[] name      = generatePlayerName();
			
			player = new Player( player_id, name[0], name[1], rookie, age );
			
			ps = DatabaseImpl.getPlayerInsertPs( dbConn );
			
			ps.setInt(      1, player.getPlayer_id()       );
			ps.setNull(     2, Types.INTEGER               ); // team_id
			ps.setString(   3, year                        );
			ps.setString(   4, player.getFirst_name()      );
			ps.setString(   5, player.getLast_name()       );
			ps.setInt(      6, player.getAge()             );
			ps.setDouble(   7, player.getScoring()         );
			ps.setDouble(   8, player.getPassing()         );
			ps.setDouble(   9, player.getBlocking()        );
			ps.setDouble(  10, player.getTackling()        );
			ps.setDouble(  11, player.getStealing()        );
			ps.setDouble(  12, player.getPresence()        );
			ps.setDouble(  13, player.getDiscipline()      );
			ps.setDouble(  14, player.getPenalty_shot()    );
			ps.setDouble(  15, player.getPenalty_offense() );
			ps.setDouble(  16, player.getPenalty_defense() );
			ps.setDouble(  17, player.getEndurance()       );
			ps.setDouble(  18, player.getConfidence()      );
			ps.setDouble(  19, player.getVitality()        );
			ps.setDouble(  20, player.getDurability()      );
			ps.setBoolean( 21, player.isRookie()           );
			ps.setBoolean( 22, player.isInjured()          );
			ps.setNull(    23, Types.DATE                  ); // return_date
			ps.setBoolean( 24, player.isFree_agent()       );
			ps.setBoolean( 25, player.isSigned()           );
			ps.setBoolean( 26, player.isReleased()         );
			ps.setBoolean( 27, player.isRetired()          );
			ps.setInt(     28, player.getAward()           );
			ps.setInt(     29, player.getDraft_pick()      );
			ps.setInt(     30, player.getSeasons_played()  );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return player;
	}

	public void generatePlayers() throws SQLException {

		for ( int j = 0; j < Constants.INITIAL_PLAYERS; ++j ) {
			
			generatePlayer( false, (int)Math.floor( (Math.random() * 12.0) + 18.0 ) );
		}
	}

	public void updatePlayersForNewSeason( String last_year ) throws SQLException {
		
		PreparedStatement ps       = null;
		
		try {
			
			ps = DatabaseImpl.getCopyPlayersForNewYearCallPs( dbConn );
			
			ps.setString( 1, last_year );
			ps.setString( 2, this.year );
			
			ps.execute();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updatePlayer( Player player ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerUpdatePs( dbConn );
			
			if   ( player.getTeam_id() == 0 ) ps.setNull( 1, Types.INTEGER       );
			else                              ps.setInt(  1, player.getTeam_id() );
			
			ps.setString(   2, player.getFirst_name()      );
			ps.setString(   3, player.getLast_name()       );
			ps.setInt(      4, player.getAge()             );
			ps.setDouble(   5, player.getScoring()         );
			ps.setDouble(   6, player.getPassing()         );
			ps.setDouble(   7, player.getBlocking()        );
			ps.setDouble(   8, player.getTackling()        );
			ps.setDouble(   9, player.getStealing()        );
			ps.setDouble(  10, player.getPresence()        );
			ps.setDouble(  11, player.getDiscipline()      );
			ps.setDouble(  12, player.getPenalty_shot()    );
			ps.setDouble(  13, player.getPenalty_offense() );
			ps.setDouble(  14, player.getPenalty_defense() );
			ps.setDouble(  15, player.getEndurance()       );
			ps.setDouble(  16, player.getConfidence()      );
			ps.setDouble(  17, player.getVitality()        );
			ps.setDouble(  18, player.getDurability()      );
			ps.setBoolean( 19, player.isRookie()           );
			ps.setBoolean( 20, player.isInjured()          );
			
			if   ( player.getReturn_date() != null ) ps.setDate( 21, new java.sql.Date( player.getReturn_date().getTime() ) );
			else                                     ps.setNull( 21,                     Types.DATE                         );
			
			ps.setBoolean( 22, player.isFree_agent()       );
			ps.setBoolean( 23, player.isSigned()           );
			ps.setBoolean( 24, player.isReleased()         );
			ps.setBoolean( 25, player.isRetired()          );

			if   ( player.getFormer_team_id() == 0 ) ps.setNull( 26, Types.INTEGER           );
			else                                     ps.setInt(  26, player.getFormer_team_id() );
			
			ps.setInt(     27, player.getAward()           );
			ps.setInt(     28, player.getDraft_pick()      );
			ps.setInt(     29, player.getSeasons_played()  );
			
			ps.setString(  30, year                        );
			ps.setInt(     31, player.getPlayer_id()       );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public Player getPlayerById( int player_id ) throws SQLException {
		
		Player player = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerByIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, player_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return player;
	}

	public Player getLatestPlayerById( int player_id ) throws SQLException {
		
		Player player = null;
		
		PreparedStatement ps1       = null;
		PreparedStatement ps2       = null;
		ResultSet         dbRs1     = null;
		ResultSet         dbRs2     = null;

		try {

			ps1 = DatabaseImpl.getLatestYearForPlayer( dbConn );

			ps1.setInt( 1, player_id );

			dbRs1 = ps1.executeQuery();

			if ( dbRs1.next() ) {

				ps2 = DatabaseImpl.getPlayerByIdSelectPs( dbConn );

				ps2.setString( 1, dbRs1.getString( 1 ) );
				ps2.setInt(    2, player_id            );

				dbRs2 = ps2.executeQuery();

				if ( dbRs2.next() ) {

					player = new Player();

					copyPlayerFromResultSet( player, dbRs2 );
				}
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}

		return player;
	}

	public List getPlayerHistoryById( int player_id ) throws SQLException {
	
		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playerGames = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerHistoryByIdSelectPs( dbConn );
			
			ps.setInt( 1, player_id                 );
			ps.setInt( 2, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerStatsView playerStatsView = new PlayerStatsView();
				
				playerStatsView.setYear(                dbRs.getString(  1 ) );
				playerStatsView.setGames(               dbRs.getInt(     2 ) );
				playerStatsView.setGames_started(       dbRs.getInt(     3 ) );
				playerStatsView.setPlaying_time(        dbRs.getInt(     4 ) );
				playerStatsView.setAttempts(            dbRs.getInt(     5 ) );
				playerStatsView.setGoals(               dbRs.getInt(     6 ) );
				playerStatsView.setAssists(             dbRs.getInt(     7 ) );
				playerStatsView.setTurnovers(           dbRs.getInt(     8 ) );
				playerStatsView.setStops(               dbRs.getInt(     9 ) );
				playerStatsView.setSteals(              dbRs.getInt(    10 ) );
				playerStatsView.setPenalties(           dbRs.getInt(    11 ) );
				playerStatsView.setOffensive_penalties( dbRs.getInt(    12 ) );
				playerStatsView.setPsa(                 dbRs.getInt(    13 ) );
				playerStatsView.setPsm(                 dbRs.getInt(    14 ) );
				playerStatsView.setOt_psa(              dbRs.getInt(    15 ) );
				playerStatsView.setOt_psm(              dbRs.getInt(    16 ) );
				playerStatsView.setPoints(              dbRs.getInt(    17 ) );
				playerStatsView.setAward(               dbRs.getInt(    18 ) );
				playerStatsView.setAllstar_team_id(     dbRs.getInt(    19 ) );
				playerStatsView.setTeam_id(             dbRs.getInt(    20 ) );
				playerStatsView.setTeam_abbrev(         dbRs.getString( 21 ) );
				
				if ( playerGames == null ) playerGames = new ArrayList();
				
				playerGames.add( playerStatsView );
			}
			
			if ( playerGames != null ) {
			
				PlayerStatsView totals = new PlayerStatsView();
				
				totals.setYear( "Total" );
				
				Iterator i = playerGames.iterator();
				
				while ( i.hasNext() ) {
				
					PlayerStatsView playerStatsView = (PlayerStatsView)i.next();
					
					totals.setGames(               totals.getGames()               +  playerStatsView.getGames()               );
					totals.setGames_started(       totals.getGames_started()       +  playerStatsView.getGames_started()       );
					totals.setPlaying_time(        totals.getPlaying_time()        +  playerStatsView.getPlaying_time()        );
					totals.setPoints(              totals.getPoints()              +  playerStatsView.getPoints()              );
					totals.setAttempts(            totals.getAttempts()            +  playerStatsView.getAttempts()            );
					totals.setGoals(               totals.getGoals()               +  playerStatsView.getGoals()               );
					totals.setAssists(             totals.getAssists()             +  playerStatsView.getAssists()             );
					totals.setTurnovers(           totals.getTurnovers()           +  playerStatsView.getTurnovers()           );
					totals.setStops(               totals.getStops()               +  playerStatsView.getStops()               );
					totals.setSteals(              totals.getSteals()              +  playerStatsView.getSteals()              );
					totals.setPenalties(           totals.getPenalties()           +  playerStatsView.getPenalties()           );
					totals.setOffensive_penalties( totals.getOffensive_penalties() +  playerStatsView.getOffensive_penalties() );
					totals.setPsa(                 totals.getPsa()                 +  playerStatsView.getPsa()                 );
					totals.setPsm(                 totals.getPsm()                 +  playerStatsView.getPsm()                 );
					totals.setOt_psa(              totals.getOt_psa()              +  playerStatsView.getOt_psa()              );
					totals.setOt_psm(              totals.getOt_psm()              +  playerStatsView.getOt_psm()              );
				}
				
				playerGames.add( totals );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerGames;
	}

	public List getPlayerHistoryByIdAndType( int player_id, int type ) throws SQLException {
	
		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playerGames = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerHistoryByIdSelectPs( dbConn );
			
			ps.setInt( 1, player_id );
			ps.setInt( 2, type      );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerStatsView playerStatsView = new PlayerStatsView();
				
				playerStatsView.setYear(                dbRs.getString(  1 ) );
				playerStatsView.setGames(               dbRs.getInt(     2 ) );
				playerStatsView.setGames_started(       dbRs.getInt(     3 ) );
				playerStatsView.setPlaying_time(        dbRs.getInt(     4 ) );
				playerStatsView.setAttempts(            dbRs.getInt(     5 ) );
				playerStatsView.setGoals(               dbRs.getInt(     6 ) );
				playerStatsView.setAssists(             dbRs.getInt(     7 ) );
				playerStatsView.setTurnovers(           dbRs.getInt(     8 ) );
				playerStatsView.setStops(               dbRs.getInt(     9 ) );
				playerStatsView.setSteals(              dbRs.getInt(    10 ) );
				playerStatsView.setPenalties(           dbRs.getInt(    11 ) );
				playerStatsView.setOffensive_penalties( dbRs.getInt(    12 ) );
				playerStatsView.setPsa(                 dbRs.getInt(    13 ) );
				playerStatsView.setPsm(                 dbRs.getInt(    14 ) );
				playerStatsView.setOt_psa(              dbRs.getInt(    15 ) );
				playerStatsView.setOt_psm(              dbRs.getInt(    16 ) );
				playerStatsView.setPoints(              dbRs.getInt(    17 ) );
				playerStatsView.setAward(               dbRs.getInt(    18 ) );
				playerStatsView.setAllstar_team_id(     dbRs.getInt(    19 ) );
				playerStatsView.setTeam_id(             dbRs.getInt(    20 ) );
				playerStatsView.setTeam_abbrev(         dbRs.getString( 21 ) );
				
				if ( playerGames == null ) playerGames = new ArrayList();
				
				playerGames.add( playerStatsView );
			}
			
			if ( playerGames != null ) {
			
				PlayerStatsView totals = new PlayerStatsView();
				
				totals.setYear( "Total" );
				
				Iterator i = playerGames.iterator();
				
				while ( i.hasNext() ) {
				
					PlayerStatsView playerStatsView = (PlayerStatsView)i.next();
					
					totals.setGames(               totals.getGames()               +  playerStatsView.getGames()               );
					totals.setGames_started(       totals.getGames_started()       +  playerStatsView.getGames_started()       );
					totals.setPlaying_time(        totals.getPlaying_time()        +  playerStatsView.getPlaying_time()        );
					totals.setPoints(              totals.getPoints()              +  playerStatsView.getPoints()              );
					totals.setAttempts(            totals.getAttempts()            +  playerStatsView.getAttempts()            );
					totals.setGoals(               totals.getGoals()               +  playerStatsView.getGoals()               );
					totals.setAssists(             totals.getAssists()             +  playerStatsView.getAssists()             );
					totals.setTurnovers(           totals.getTurnovers()           +  playerStatsView.getTurnovers()           );
					totals.setStops(               totals.getStops()               +  playerStatsView.getStops()               );
					totals.setSteals(              totals.getSteals()              +  playerStatsView.getSteals()              );
					totals.setPenalties(           totals.getPenalties()           +  playerStatsView.getPenalties()           );
					totals.setOffensive_penalties( totals.getOffensive_penalties() +  playerStatsView.getOffensive_penalties() );
					totals.setPsa(                 totals.getPsa()                 +  playerStatsView.getPsa()                 );
					totals.setPsm(                 totals.getPsm()                 +  playerStatsView.getPsm()                 );
					totals.setOt_psa(              totals.getOt_psa()              +  playerStatsView.getOt_psa()              );
					totals.setOt_psm(              totals.getOt_psm()              +  playerStatsView.getOt_psm()              );
				}
				
				playerGames.add( totals );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerGames;
	}
	
	public List getPlayersByTeamId( int team_id ) throws SQLException {
		
		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayersByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getPlayersByAllstarTeamId( int team_id ) throws SQLException {
		
		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayersByAllstarTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getPlayersByLetter( String letter ) throws SQLException {
		
		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayersByLetterSelectPs( dbConn );
			
			ps.setString( 1, letter + "%" );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setFirst_name(      dbRs.getString(   2 ) );
				player.setLast_name(       dbRs.getString(   3 ) );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getPlayerLetters() throws SQLException {
		
		List letters = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerLettersSelectPs( dbConn );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				StringView letter = new StringView();
				
				letter.setValue( dbRs.getString( 1 ) );
				
				if ( letters == null ) letters = new ArrayList();
				
				letters.add( letter );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return letters;
	}
	
	public List getFreePlayers() throws SQLException {

		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getFreePlayersSelectPs( dbConn );
			
			ps.setString(  1, year  );
			ps.setBoolean( 2, false );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getPlayerList() throws SQLException {
		
		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerListSelectPs( dbConn );
			
			ps.setString(  1, this.year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getUndraftedRookies() throws SQLException {

		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getUndraftedRookiesSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}
	
	public void updateSeasonsPlayed() throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getUpdateSeasonsPlayedUpdatePs( dbConn );
			
			ps.setString(  1, this.year );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void insertPlayerGame( PlayerGame playerGame ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerGameInsertPs( dbConn );
			
			ps.setInt(      1,                    playerGame.getGame_id()               );
			ps.setString(   2,                    playerGame.getYear()                  );
			ps.setDate(     3, new java.sql.Date( playerGame.getDatestamp().getTime() ) );
			ps.setInt(      4,                    playerGame.getType()                  );
			ps.setInt(      5,                    playerGame.getPlayer_id()             );
			ps.setInt(      6,                    playerGame.getTeam_id()               );
			ps.setBoolean(  7,                    playerGame.isInjured()                );
			ps.setBoolean(  8,                    playerGame.isStarted()                );
			ps.setInt(      9,                    playerGame.getPlaying_time()          );
			ps.setInt(     10,                    playerGame.getAttempts()              );
			ps.setInt(     11,                    playerGame.getGoals()                 );
			ps.setInt(     12,                    playerGame.getAssists()               );
			ps.setInt(     13,                    playerGame.getTurnovers()             );
			ps.setInt(     14,                    playerGame.getStops()                 );
			ps.setInt(     15,                    playerGame.getSteals()                );
			ps.setInt(     16,                    playerGame.getPenalties()             );
			ps.setInt(     17,                    playerGame.getOffensive_penalties()   );
			ps.setInt(     18,                    playerGame.getPsa()                   );
			ps.setInt(     19,                    playerGame.getPsm()                   );
			ps.setInt(     20,                    playerGame.getOt_psa()                );
			ps.setInt(     21,                    playerGame.getOt_psm()                );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updatePlayerGame( PlayerGame playerGame ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerGameUpdatePs( dbConn );
			
			ps.setBoolean(  1,                    playerGame.isInjured()                );
			ps.setBoolean(  2,                    playerGame.isStarted()                );
			ps.setInt(      3,                    playerGame.getPlaying_time()          );
			ps.setInt(      4,                    playerGame.getAttempts()              );
			ps.setInt(      5,                    playerGame.getGoals()                 );
			ps.setInt(      6,                    playerGame.getAssists()               );
			ps.setInt(      7,                    playerGame.getTurnovers()             );
			ps.setInt(      8,                    playerGame.getStops()                 );
			ps.setInt(      9,                    playerGame.getSteals()                );
			ps.setInt(     10,                    playerGame.getPenalties()             );
			ps.setInt(     11,                    playerGame.getOffensive_penalties()   );
			ps.setInt(     12,                    playerGame.getPsa()                   );
			ps.setInt(     13,                    playerGame.getPsm()                   );
			ps.setInt(     14,                    playerGame.getOt_psa()                );
			ps.setInt(     15,                    playerGame.getOt_psm()                );
			
			ps.setInt(     16,                    playerGame.getGame_id()               );
			ps.setInt(     17,                    playerGame.getPlayer_id()             );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updatePlayerStats( Player player, int type ) throws SQLException {
		
		PlayerStats       playerStats = new PlayerStats();
		PlayerGame        playerGame  = player.getGame();
		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;
		
		playerStats.setYear(                year                                );
		playerStats.setType(                type                                );
		playerStats.setPlayer_id(           player.getPlayer_id()               );
		playerStats.setPlaying_time(        playerGame.getPlaying_time()        );
		playerStats.setAttempts(            playerGame.getAttempts()            );
		playerStats.setGoals(               playerGame.getGoals()               );
		playerStats.setAssists(             playerGame.getAssists()             );
		playerStats.setTurnovers(           playerGame.getTurnovers()           );
		playerStats.setStops(               playerGame.getStops()               );
		playerStats.setSteals(              playerGame.getSteals()              );
		playerStats.setPenalties(           playerGame.getPenalties()           );
		playerStats.setOffensive_penalties( playerGame.getOffensive_penalties() );
		playerStats.setPsa(                 playerGame.getPsa()                 );
		playerStats.setPsm(                 playerGame.getPsm()                 );
		playerStats.setOt_psa(              playerGame.getOt_psa()              );
		playerStats.setOt_psm(              playerGame.getOt_psm()              );
		
		if   ( type == TeamGame.gt_Allstar ) playerStats.setTeam_id( player.getAllstar_team_id() );
		else                                 playerStats.setTeam_id( player.getTeam_id()         );
		
		if ( player.isPlayed_in_game() ) playerStats.setGames( 1 );
		if ( player.isStarted()        ) playerStats.setGames_started( 1 );
		
		try {
			
			ps1 = DatabaseImpl.getPlayerStatsSelectPs( dbConn );
			
			ps1.setString( 1, playerStats.getYear()      );
			ps1.setInt(    2, playerStats.getType()      );
			ps1.setInt(    3, playerStats.getPlayer_id() );
			
			dbRs1 = ps1.executeQuery();
			
			if ( dbRs1.next() ) {
			
				// Populate values from database
				playerStats.setGames(               playerStats.getGames()               + dbRs1.getInt(  1 ) );
				playerStats.setGames_started(       playerStats.getGames_started()       + dbRs1.getInt(  2 ) );
				playerStats.setPlaying_time(        playerStats.getPlaying_time()        + dbRs1.getInt(  3 ) );
				playerStats.setAttempts(            playerStats.getAttempts()            + dbRs1.getInt(  4 ) );
				playerStats.setGoals(               playerStats.getGoals()               + dbRs1.getInt(  5 ) );
				playerStats.setAssists(             playerStats.getAssists()             + dbRs1.getInt(  6 ) );
				playerStats.setTurnovers(           playerStats.getTurnovers()           + dbRs1.getInt(  7 ) );
				playerStats.setStops(               playerStats.getStops()               + dbRs1.getInt(  8 ) );
				playerStats.setSteals(              playerStats.getSteals()              + dbRs1.getInt(  9 ) );
				playerStats.setPenalties(           playerStats.getPenalties()           + dbRs1.getInt( 10 ) );
				playerStats.setOffensive_penalties( playerStats.getOffensive_penalties() + dbRs1.getInt( 11 ) );
				playerStats.setPsa(                 playerStats.getPsa()                 + dbRs1.getInt( 12 ) );
				playerStats.setPsm(                 playerStats.getPsm()                 + dbRs1.getInt( 13 ) );
				playerStats.setOt_psa(              playerStats.getOt_psa()              + dbRs1.getInt( 14 ) );
				playerStats.setOt_psm(              playerStats.getOt_psm()              + dbRs1.getInt( 15 ) );
				
				// Update the database
				ps2 = DatabaseImpl.getPlayerStatsUpdatePs( dbConn );

				ps2.setInt(     1, playerStats.getGames()               );
				ps2.setInt(     2, playerStats.getGames_started()       );
				ps2.setInt(     3, playerStats.getPlaying_time()        );
				ps2.setInt(     4, playerStats.getAttempts()            );
				ps2.setInt(     5, playerStats.getGoals()               );
				ps2.setInt(     6, playerStats.getAssists()             );
				ps2.setInt(     7, playerStats.getTurnovers()           );
				ps2.setInt(     8, playerStats.getStops()               );
				ps2.setInt(     9, playerStats.getSteals()              );
				ps2.setInt(    10, playerStats.getPenalties()           );
				ps2.setInt(    11, playerStats.getOffensive_penalties() );
				ps2.setInt(    12, playerStats.getPsa()                 );
				ps2.setInt(    13, playerStats.getPsm()                 );
				ps2.setInt(    14, playerStats.getOt_psa()              );
				ps2.setInt(    15, playerStats.getOt_psm()              );
				
				ps2.setString( 16, playerStats.getYear()                );
				ps2.setInt(    17, playerStats.getType()                );
				ps2.setInt(    18, playerStats.getPlayer_id()           );
				
				ps2.executeUpdate();
				
				return;
			}
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbStmt( ps1 );
			
			// Record not found, insert a new record
			ps1 = DatabaseImpl.getPlayerStatsInsertPs( dbConn );

			ps1.setString(  1, playerStats.getYear()                );
			ps1.setInt(     2, playerStats.getType()                );
			ps1.setInt(     3, playerStats.getPlayer_id()           );
			ps1.setInt(     4, playerStats.getTeam_id()             );
			ps1.setInt(     5, playerStats.getGames()               );
			ps1.setInt(     6, playerStats.getGames_started()       );
			ps1.setInt(     7, playerStats.getPlaying_time()        );
			ps1.setInt(     8, playerStats.getAttempts()            );
			ps1.setInt(     9, playerStats.getGoals()               );
			ps1.setInt(    10, playerStats.getAssists()             );
			ps1.setInt(    11, playerStats.getTurnovers()           );
			ps1.setInt(    12, playerStats.getStops()               );
			ps1.setInt(    13, playerStats.getSteals()              );
			ps1.setInt(    14, playerStats.getPenalties()           );
			ps1.setInt(    15, playerStats.getOffensive_penalties() );
			ps1.setInt(    16, playerStats.getPsa()                 );
			ps1.setInt(    17, playerStats.getPsm()                 );
			ps1.setInt(    18, playerStats.getOt_psa()              );
			ps1.setInt(    19, playerStats.getOt_psm()              );
			
			ps1.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}
	}

	public List getPlayerStatsSumByPlayerId( int player_id ) throws SQLException {
		
		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playerGames = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerStatsSumByPlayerIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, player_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerStatsView playerStatsView = new PlayerStatsView();
				
				playerStatsView.setType(                dbRs.getInt(  1 ) );
				playerStatsView.setGames(               dbRs.getInt(  2 ) );
				playerStatsView.setGames_started(       dbRs.getInt(  3 ) );
				playerStatsView.setPlaying_time(        dbRs.getInt(  4 ) );
				playerStatsView.setAttempts(            dbRs.getInt(  5 ) );
				playerStatsView.setGoals(               dbRs.getInt(  6 ) );
				playerStatsView.setAssists(             dbRs.getInt(  7 ) );
				playerStatsView.setTurnovers(           dbRs.getInt(  8 ) );
				playerStatsView.setStops(               dbRs.getInt(  9 ) );
				playerStatsView.setSteals(              dbRs.getInt( 10 ) );
				playerStatsView.setPenalties(           dbRs.getInt( 11 ) );
				playerStatsView.setOffensive_penalties( dbRs.getInt( 12 ) );
				playerStatsView.setPsa(                 dbRs.getInt( 13 ) );
				playerStatsView.setPsm(                 dbRs.getInt( 14 ) );
				playerStatsView.setOt_psa(              dbRs.getInt( 15 ) );
				playerStatsView.setOt_psm(              dbRs.getInt( 16 ) );
				playerStatsView.setPoints(              dbRs.getInt( 17 ) );
				
				if ( playerGames == null ) playerGames = new ArrayList();
				
				playerGames.add( playerStatsView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerGames;
	}

	public List getPlayerScores( int count ) throws SQLException {

		PreparedStatement ps           = null;
		ResultSet         dbRs         = null;
		List              playerScores = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerScoresSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, count                     );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerScore playerScore = new PlayerScore();
				
				playerScore.setPlayer_id(  dbRs.getInt( 1 ) );
				playerScore.setConference( dbRs.getInt( 2 ) );
				playerScore.setDivision(   dbRs.getInt( 3 ) );
				playerScore.setScore(      dbRs.getInt( 4 ) );
				
				if ( playerScores == null ) playerScores = new ArrayList();
				
				playerScores.add( playerScore );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerScores;
	}

	public List getPlayerAwards() throws SQLException {
		
		PreparedStatement ps     = null;
		ResultSet         dbRs   = null;
		List              awards = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerAwardsSelectPs( dbConn );
			
			ps.setString( 1,          year             );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerAwardsView awardsView = new PlayerAwardsView();
				
				awardsView.setPlayer_id(   dbRs.getInt(      1 ) );
				awardsView.setFirst_name(  dbRs.getString(   2 ) );
				awardsView.setLast_name(   dbRs.getString(   3 ) );
				awardsView.setTeam_id(     dbRs.getInt(      4 ) );
				awardsView.setTeam_abbrev( dbRs.getString(   5 ) );
				awardsView.setConference(  dbRs.getInt(      6 ) );
				awardsView.setDivision(    dbRs.getInt(      7 ) );
				awardsView.setAward(       dbRs.getInt(      8 ) );
				awardsView.setRookie(      dbRs.getBoolean(  9 ) );
				awardsView.setPoints(      dbRs.getInt(     10 ) );
				awardsView.setGoals(       dbRs.getInt(     11 ) );
				awardsView.setAssists(     dbRs.getInt(     12 ) );
				awardsView.setStops(       dbRs.getInt(     13 ) );
				awardsView.setSteals(      dbRs.getInt(     14 ) );
				awardsView.setPsm(         dbRs.getInt(     15 ) );
				
				if ( awards == null ) awards = new ArrayList();
				
				awards.add( awardsView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return awards;
	}
	
	public List getRookiePlayerList() throws SQLException {
		
		List players = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getRookiePlayerListSelectPs( dbConn );
			
			ps.setString(  1, this.year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( players == null ) players = new ArrayList();
				
				players.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}

		return players;
	}

	public List getPlayerGamesForTeamByGame(int game_id, int team_id) throws SQLException {
		
		PreparedStatement ps          = null;
		ResultSet         dbRs        = null;
		List              playerGames = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerGamesForTeamSelectPs( dbConn );
			
			ps.setInt( 1, game_id );
			ps.setInt( 2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerGameView playerGameView = new PlayerGameView();
				
				playerGameView.setFirst_name(          dbRs.getString(   1 ) );
				playerGameView.setLast_name(           dbRs.getString(   2 ) );
				playerGameView.setPlayer_id(           dbRs.getInt(      3 ) );
				playerGameView.setInjured(             dbRs.getBoolean(  4 ) );
				playerGameView.setStarted(             dbRs.getBoolean(  5 ) );
				playerGameView.setPlaying_time(        dbRs.getInt(      6 ) );
				playerGameView.setAttempts(            dbRs.getInt(      7 ) );
				playerGameView.setGoals(               dbRs.getInt(      8 ) );
				playerGameView.setAssists(             dbRs.getInt(      9 ) );
				playerGameView.setTurnovers(           dbRs.getInt(     10 ) );
				playerGameView.setStops(               dbRs.getInt(     11 ) );
				playerGameView.setSteals(              dbRs.getInt(     12 ) );
				playerGameView.setPenalties(           dbRs.getInt(     13 ) );
				playerGameView.setOffensive_penalties( dbRs.getInt(     14 ) );
				playerGameView.setPsa(                 dbRs.getInt(     15 ) );
				playerGameView.setPsm(                 dbRs.getInt(     16 ) );
				playerGameView.setOt_psa(              dbRs.getInt(     17 ) );
				playerGameView.setOt_psm(              dbRs.getInt(     18 ) );
				playerGameView.setPoints(              dbRs.getInt(     19 ) );
				
				if ( playerGames == null ) playerGames = new ArrayList();
				
				playerGames.add( playerGameView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerGames;
	}

	public void updateAllstarTeamId( int player_id, int team_id, boolean alternate ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarTeamIdUpdatePs( dbConn );
			
			ps.setInt(     1, team_id   );
			ps.setBoolean( 2, alternate );
			ps.setString(  3, this.year );
			ps.setInt(     4, player_id );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public List getAllstarsByTeamId( int team_id ) throws SQLException {
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		List              allstars = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarsByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, team_id                   );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				PlayerAllstarView playerAllstarView = new PlayerAllstarView();
				
				playerAllstarView.setPlayer_id(   dbRs.getInt(      1 ) );
				playerAllstarView.setFirst_name(  dbRs.getString(   2 ) );
				playerAllstarView.setLast_name(   dbRs.getString(   3 ) );
				playerAllstarView.setRookie(      dbRs.getBoolean(  4 ) );
				playerAllstarView.setInjured(     dbRs.getBoolean(  5 ) );
				playerAllstarView.setAlternate(   dbRs.getBoolean(  6 ) );
				playerAllstarView.setAward(       dbRs.getInt(      7 ) );
				playerAllstarView.setTeam_id(     dbRs.getInt(      8 ) );
				playerAllstarView.setTeam_abbrev( dbRs.getString(   9 ) );
				playerAllstarView.setPoints(      dbRs.getInt(     10 ) );
				playerAllstarView.setGoals(       dbRs.getInt(     11 ) );
				playerAllstarView.setAssists(     dbRs.getInt(     12 ) );
				playerAllstarView.setStops(       dbRs.getInt(     13 ) );
				playerAllstarView.setSteals(      dbRs.getInt(     14 ) );
				playerAllstarView.setPsm(         dbRs.getInt(     15 ) );
				
				if ( allstars == null ) allstars = new ArrayList();
				
				allstars.add( playerAllstarView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return allstars;
	}

	public void retireTeamPlayers() throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getRetiredTeamPlayersUpdatePs( dbConn );
			
			ps.setBoolean( 1, true          );
			ps.setNull(    2, Types.INTEGER );
			ps.setString(  3, this.year     );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void retireFreePlayers() throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getRetiredFreePlayersUpdatePs( dbConn );
			
			ps.setBoolean( 1, true      );
			ps.setBoolean( 2, false     );
			ps.setString(  3, this.year );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public int selectAllstarForTeam(int team_id) throws SQLException {
		
		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		int               selection = 0;
		
		try {
			
			ps = DatabaseImpl.getAllstarForTeamSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, team_id                   );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				selection = dbRs.getInt( 1 );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return selection;
	}

	public List selectAllstarAlternatesForDivision( int division ) throws SQLException {

		PreparedStatement ps         = null;
		ResultSet         dbRs       = null;
		List              playerList = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarAlternatesForDivisionSelectPs( dbConn );
			
			ps.setString( 1, year                      );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			ps.setInt(    3, division                  );
			ps.setInt(    4, 3                         );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				int selection = dbRs.getInt( 1 );
				
				Player player = getPlayerById( selection );
				
				if ( playerList == null ) playerList = new ArrayList();
				
				playerList.add( player );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerList;
	}

	public List getManagerialCandidates() throws SQLException {
		
		List playerList = null;
		
		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		
		try {
			
			ps = DatabaseImpl.getManagerialCandidatesSelectPs( dbConn );
			
			ps.setString( 1, year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				Player player = new Player();
				
				copyPlayerFromResultSet( player, dbRs );
				
				if ( playerList == null ) playerList = new ArrayList();
				
				playerList.add( player );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return playerList;
	}

	public void insertInjury(Injury injury) throws SQLException {

		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getInjuryInsertPs( dbConn );
			
			ps.setInt( 1, injury.getGame_id()   );
			ps.setInt( 2, injury.getPlayer_id() );
			ps.setInt( 3, injury.getTeam_id()   );
			ps.setInt( 4, injury.getDuration()  );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updatePlayerInjury(Player player) throws SQLException {

		PreparedStatement ps1       = null;
		PreparedStatement ps2       = null;
		ResultSet         dbRs      = null;
		boolean           isInjured;
		
		try {
			
			ps1 = DatabaseImpl.getPlayerInjurySelectPs( dbConn );
			
			ps1.setString( 1, year                  );
			ps1.setInt(    2, player.getPlayer_id() );
			
			dbRs = ps1.executeQuery();
			
			if ( dbRs.next() ) {
			
				isInjured = dbRs.getBoolean( 1 );
				
				if ( ! isInjured ) {
				
					ps2 = DatabaseImpl.getPlayerInjuryUpdatePs( dbConn );
					
					ps2.setBoolean( 1,                    player.isInjured()                  );
					ps2.setDate(    2, new java.sql.Date( player.getReturn_date().getTime() ) );
					ps2.setString(  3,                    year                                );
					ps2.setInt(     4,                    player.getPlayer_id()               );
					
					ps2.executeUpdate();
				}
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}
	}

	public void clearInjuries( Date gameDate ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getClearInjuriesUpdatePs( dbConn );
			
			ps.setBoolean( 1,                    false                );
			ps.setNull(    2,                    Types.DATE           );
			ps.setString(  3,                    year                 );
			ps.setDate(    4, new java.sql.Date( gameDate.getTime() ) );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public List getPlayerInjuriesById( int player_id ) throws SQLException {
		
		List injuries = null;
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		try {
			
			ps = DatabaseImpl.getInjuriesByPlayerIdSelectPs( dbConn );
			
			ps.setInt(    1, player_id );
			ps.setString( 2, year      );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				PlayerInjuryView playerInjuryView = new PlayerInjuryView();
				
				playerInjuryView.setDatestamp(       dbRs.getDate(    1 ) );
				playerInjuryView.setOpponent(        dbRs.getInt(     2 ) );
				playerInjuryView.setOpponent_abbrev( dbRs.getString(  3 ) );
				playerInjuryView.setRoad_game(       dbRs.getBoolean( 4 ) );
				playerInjuryView.setGame_id(         dbRs.getInt(     5 ) );
				playerInjuryView.setDuration(        dbRs.getInt(     6 ) );
				
				if ( injuries == null ) injuries = new ArrayList();
				
				injuries.add( playerInjuryView );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}
		
		return injuries;
	}

	public List getPlayerInjuryHistoryById( int player_id ) throws SQLException {
		
		List injuries = null;
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		try {
			
			ps = DatabaseImpl.getInjuryHistoryByPlayerIdSelectPs( dbConn );
			
			ps.setInt( 1, player_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				PlayerInjuryView playerInjuryView = new PlayerInjuryView();
				
				playerInjuryView.setDatestamp(       dbRs.getDate(    1 ) );
				playerInjuryView.setYear(            dbRs.getString(  2 ) );
				playerInjuryView.setOpponent(        dbRs.getInt(     3 ) );
				playerInjuryView.setOpponent_abbrev( dbRs.getString(  4 ) );
				playerInjuryView.setRoad_game(       dbRs.getBoolean( 5 ) );
				playerInjuryView.setGame_id(         dbRs.getInt(     6 ) );
				playerInjuryView.setDuration(        dbRs.getInt(     7 ) );
				
				if ( injuries == null ) injuries = new ArrayList();
				
				injuries.add( playerInjuryView );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}
		
		return injuries;
	}
	
	public RookieInfoView getRookieInfo( int player_id ) throws SQLException {
		
		RookieInfoView rookieInfoView = null;

		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		try {
			
			ps = DatabaseImpl.getRookieInfoSelectPs( dbConn );
			
			ps.setInt(     1, player_id );
			ps.setBoolean( 2, true      );
			ps.setInt(     3, player_id );
			ps.setBoolean( 4, true      );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
				
				rookieInfoView = new RookieInfoView();
				
				rookieInfoView.setPick(        dbRs.getInt(    1 ) );
				rookieInfoView.setTeam_id(     dbRs.getInt(    2 ) );
				rookieInfoView.setTeam_abbrev( dbRs.getString( 3 ) );
				rookieInfoView.setYear(        dbRs.getString( 4 ) );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs  );
			DatabaseImpl.closeDbStmt( ps  );
		}
		
		return rookieInfoView;
	}

	public void agePlayers() throws SQLException {

		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getAgePlayersUpdatePs( dbConn );
			
			ps.setString(  1, this.year );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
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
			
			ps.setString(  1, year  );
			ps.setBoolean( 2, false );
			
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
			
			ps = DatabaseImpl.getStandoutPlayersSelectPs( dbConn );
			
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

	public List getPlayerChangesByTeam() throws SQLException {

		TeamService teamService = new TeamServiceImpl( dbConn, year );
		
		List teams = teamService.getTeamList();
		
		Iterator i = teams.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			List playersList = new ArrayList();
			List tempList    = null;
			
			if ( (tempList = getRetiredPlayersByTeam(  team.getTeam_id() )) != null ) playersList.addAll( tempList );
			if ( (tempList = getReleasedPlayersByTeam( team.getTeam_id() )) != null ) playersList.addAll( tempList );
			if ( (tempList = getSignedPlayersByTeam(   team.getTeam_id() )) != null ) playersList.addAll( tempList );

			if ( playersList.size() == 0 ) {
			
				i.remove();
			}
			else {
			
				team.setPlayers( playersList );
			}
		}
		
		Team team = new Team();
		
		List playersList = getRetiredPlayersWithoutTeam();

		if ( playersList != null ) {
		
			team.setPlayers( playersList );
			
			teams.add( team );
		}
		
		return teams;
	}

	private void getAwardsForPlayer( PlayerView playerView ) throws SQLException {

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getAwardCountByPlayerIdSelectPs( dbConn );
			
			ps.setInt( 1, playerView.getPlayer_id() );
			ps.setInt( 2, PlayerScore.PLATINUM_AWARD );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				playerView.setPlatinum_count( dbRs.getInt( 1 ) );
			}
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );

			ps = DatabaseImpl.getAwardCountByPlayerIdSelectPs( dbConn );
			
			ps.setInt( 1, playerView.getPlayer_id() );
			ps.setInt( 2, PlayerScore.GOLD_AWARD     );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				playerView.setGold_count( dbRs.getInt( 1 ) );
			}
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );

			ps = DatabaseImpl.getAwardCountByPlayerIdSelectPs( dbConn );
			
			ps.setInt( 1, playerView.getPlayer_id() );
			ps.setInt( 2, PlayerScore.SILVER_AWARD   );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				playerView.setSilver_count( dbRs.getInt( 1 ) );
			}
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
			
			ps = DatabaseImpl.getAllstarCountByPlayerIdSelectPs( dbConn );
			
			ps.setInt( 1, playerView.getPlayer_id() );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				playerView.setAllstar_count( dbRs.getInt( 1 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
	}
	
	private List getRetiredPlayersByTeam( int team_id ) throws SQLException {

		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;
		List              playersList = null;
		
		try {
			
			ps1 = DatabaseImpl.getRetiredPlayersByTeamSelectPs( dbConn );
			
			ps1.setString(  1, year    );
			ps1.setInt(     2, team_id );
			ps1.setBoolean( 3, true    );
			
			dbRs1 = ps1.executeQuery();

			while ( dbRs1.next() ) {
			
				PlayerView playerView = new PlayerView();
				
				playerView.setStatus( PlayerView.STATUS_RETIRED );
				
				playerView.setPlayer_id(      dbRs1.getInt(    1 ) );
				playerView.setFirst_name(     dbRs1.getString( 2 ) );
				playerView.setLast_name(      dbRs1.getString( 3 ) );
				playerView.setAge(            dbRs1.getInt(    4 ) );
				playerView.setSeasons_played( dbRs1.getInt(    5 ) );
				
				ps2 = DatabaseImpl.getSeasonAveragesByPlayerSelectPs( dbConn );
				
				ps2.setInt( 1, TeamGame.gt_RegularSeason  );
				ps2.setInt( 2, playerView.getPlayer_id() );
				
				dbRs2 = ps2.executeQuery();
				
				if ( dbRs2.next() ) {
					
					playerView.setGoals(   dbRs2.getDouble( 1 ) );
					playerView.setAssists( dbRs2.getDouble( 2 ) );
					playerView.setStops(   dbRs2.getDouble( 3 ) );
					playerView.setSteals(  dbRs2.getDouble( 4 ) );
					playerView.setPsm(     dbRs2.getDouble( 5 ) );
					playerView.setPoints(  dbRs2.getDouble( 6 ) );
				}
				
				DatabaseImpl.closeDbRs( dbRs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				getAwardsForPlayer( playerView );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( playerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}

		return playersList;
	}

	private List getRetiredPlayersWithoutTeam() throws SQLException {

		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;
		List              playersList = null;
		
		try {
			
			ps1 = DatabaseImpl.getRetiredPlayersWithoutTeamSelectPs( dbConn );
			
			ps1.setInt(     1, 1    );
			ps1.setString(  2, year );
			ps1.setBoolean( 3, true );
			
			dbRs1 = ps1.executeQuery();

			while ( dbRs1.next() ) {
			
				PlayerView playerView = new PlayerView();
				
				playerView.setStatus( PlayerView.STATUS_RETIRED );
				
				playerView.setPlayer_id(      dbRs1.getInt(    1 ) );
				playerView.setFirst_name(     dbRs1.getString( 2 ) );
				playerView.setLast_name(      dbRs1.getString( 3 ) );
				playerView.setAge(            dbRs1.getInt(    4 ) );
				playerView.setSeasons_played( dbRs1.getInt(    5 ) );
				
				ps2 = DatabaseImpl.getSeasonAveragesByPlayerSelectPs( dbConn );
				
				ps2.setInt( 1, TeamGame.gt_RegularSeason  );
				ps2.setInt( 2, playerView.getPlayer_id() );
				
				dbRs2 = ps2.executeQuery();
				
				if ( dbRs2.next() ) {
					
					playerView.setGoals(   dbRs2.getDouble( 1 ) );
					playerView.setAssists( dbRs2.getDouble( 2 ) );
					playerView.setStops(   dbRs2.getDouble( 3 ) );
					playerView.setSteals(  dbRs2.getDouble( 4 ) );
					playerView.setPsm(     dbRs2.getDouble( 5 ) );
					playerView.setPoints(  dbRs2.getDouble( 6 ) );
				}
				
				DatabaseImpl.closeDbRs( dbRs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				getAwardsForPlayer( playerView );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( playerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}

		return playersList;
	}

	private List getReleasedPlayersByTeam( int team_id ) throws SQLException {

		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;
		List              playersList = null;
		
		try {
			
			ps1 = DatabaseImpl.getReleasedFreeAgentsByTeamSelectPs( dbConn );
			
			ps1.setString(  1, year    );
			ps1.setInt(     2, team_id );
			ps1.setBoolean( 3, true    );
			
			dbRs1 = ps1.executeQuery();

			while ( dbRs1.next() ) {
			
				PlayerView playerView = new PlayerView();
				
				playerView.setStatus( PlayerView.STATUS_RELEASED );
				
				playerView.setPlayer_id(      dbRs1.getInt(    1 ) );
				playerView.setFirst_name(     dbRs1.getString( 2 ) );
				playerView.setLast_name(      dbRs1.getString( 3 ) );
				playerView.setAge(            dbRs1.getInt(    4 ) );
				playerView.setSeasons_played( dbRs1.getInt(    5 ) );
				
				ps2 = DatabaseImpl.getSeasonAveragesByPlayerSelectPs( dbConn );
				
				ps2.setInt( 1, TeamGame.gt_RegularSeason  );
				ps2.setInt( 2, playerView.getPlayer_id() );
				
				dbRs2 = ps2.executeQuery();
				
				if ( dbRs2.next() ) {
					
					playerView.setGoals(   dbRs2.getDouble( 1 ) );
					playerView.setAssists( dbRs2.getDouble( 2 ) );
					playerView.setStops(   dbRs2.getDouble( 3 ) );
					playerView.setSteals(  dbRs2.getDouble( 4 ) );
					playerView.setPsm(     dbRs2.getDouble( 5 ) );
					playerView.setPoints(  dbRs2.getDouble( 6 ) );
				}
				
				DatabaseImpl.closeDbRs( dbRs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				getAwardsForPlayer( playerView );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( playerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}

		return playersList;
	}

	private List getSignedPlayersByTeam( int team_id ) throws SQLException {
		
		PreparedStatement ps1         = null;
		PreparedStatement ps2         = null;
		ResultSet         dbRs1       = null;
		ResultSet         dbRs2       = null;
		TeamService       teamService = null;
		List              playersList = null;
		
		try {
			
			ps1 = DatabaseImpl.getSignedFreeAgentsByTeamSelectPs( dbConn );
			
			ps1.setString(  1, year    );
			ps1.setInt(     2, team_id );
			ps1.setBoolean( 3, true    );
			
			dbRs1 = ps1.executeQuery();

			while ( dbRs1.next() ) {
			
				PlayerView playerView = new PlayerView();
				
				playerView.setStatus( PlayerView.STATUS_SIGNED );
				
				playerView.setPlayer_id(      dbRs1.getInt(    1 ) );
				playerView.setFirst_name(     dbRs1.getString( 2 ) );
				playerView.setLast_name(      dbRs1.getString( 3 ) );
				playerView.setAge(            dbRs1.getInt(    4 ) );
				playerView.setSeasons_played( dbRs1.getInt(    5 ) );
				playerView.setFormer_team_id( dbRs1.getInt(    6 ) );
				
				if ( teamService == null ) teamService = new TeamServiceImpl( dbConn, year );
				
				playerView.setFormer_team_abbrev( teamService.getAbbrevForTeamId( playerView.getFormer_team_id() ) );
				
				ps2 = DatabaseImpl.getSeasonAveragesByPlayerSelectPs( dbConn );
				
				ps2.setInt( 1, TeamGame.gt_RegularSeason  );
				ps2.setInt( 2, playerView.getPlayer_id() );
				
				dbRs2 = ps2.executeQuery();
				
				if ( dbRs2.next() ) {
					
					playerView.setGoals(   dbRs2.getDouble( 1 ) );
					playerView.setAssists( dbRs2.getDouble( 2 ) );
					playerView.setStops(   dbRs2.getDouble( 3 ) );
					playerView.setSteals(  dbRs2.getDouble( 4 ) );
					playerView.setPsm(     dbRs2.getDouble( 5 ) );
					playerView.setPoints(  dbRs2.getDouble( 6 ) );
				}
				
				DatabaseImpl.closeDbRs( dbRs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				getAwardsForPlayer( playerView );
				
				if ( playersList == null ) playersList = new ArrayList();
				
				playersList.add( playerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}

		return playersList;
	}

	public List getReleasedPlayers() throws SQLException {

		TeamService teamService = new TeamServiceImpl( dbConn, year );

		List teams = teamService.getTeamList();

		Iterator i = teams.iterator();

		while ( i.hasNext() ) {

			Team team = (Team)i.next();

			PreparedStatement ps          = null;
			ResultSet         dbRs        = null;
			List              playersList = null;

			try {

				ps = DatabaseImpl.getReleasedPlayersByTeamSelectPs( dbConn );

				ps.setString(  1, year              );
				ps.setInt(     2, team.getTeam_id() );
				ps.setBoolean( 3, true              );
				ps.setBoolean( 4, false             );
				ps.setBoolean( 5, false             );

				dbRs = ps.executeQuery();

				playersList = null;

				while ( dbRs.next() ) {

					ReleaseView releaseView = new ReleaseView();

					releaseView.setPlayer_id(      dbRs.getInt(     1 ) );
					releaseView.setFirst_name(     dbRs.getString(  2 ) );
					releaseView.setLast_name(      dbRs.getString(  3 ) );
					releaseView.setTeam_id(        dbRs.getInt(     4 ) );
					releaseView.setTeam_abbrev(    dbRs.getString(  5 ) );
					releaseView.setRookie(         dbRs.getBoolean( 6 ) );
					releaseView.setDraft_pick(     dbRs.getInt(     7 ) );
					releaseView.setAge(            dbRs.getInt(     8 ) );
					releaseView.setSeasons_played( dbRs.getInt(     9 ) );

					if ( playersList == null ) playersList = new ArrayList();

					playersList.add( releaseView );
				}

				if ( playersList == null ) {

					i.remove();
				}
				else {

					team.setPlayers( playersList );
				}
			}
			finally {

				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
		}

		return teams;
	}

}
