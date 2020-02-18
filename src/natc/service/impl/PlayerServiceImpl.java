package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import natc.data.Player;
import natc.data.PlayerGame;
import natc.data.PlayerScore;
import natc.data.PlayerStats;
import natc.data.TeamGame;
import natc.service.PlayerService;
import natc.view.AllstarView;
import natc.view.AwardsView;
import natc.view.PlayerGameView;
import natc.view.PlayerStatsView;
import natc.view.StringView;

public class PlayerServiceImpl implements PlayerService {

	private Connection dbConn = null;
	private String     year   = null;
	
	public PlayerServiceImpl( Connection dbConn, String year ) {
		
		this.dbConn = dbConn;
		this.year   = year;
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
				ps = DatabaseImpl.getPlayerByNameSelectPs( dbConn );
			
				ps.setString( 1, names[0] );
				ps.setString( 2, names[1] );
			
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
	
	public Player generatePlayer( boolean is_on_team, int team_id ) throws SQLException {
		
		PreparedStatement ps     = null;
		Player            player = null;
		
		try {
			
			int      player_id = getNextPlayerId();
			String[] name      = generatePlayerName();
			
			player = new Player( player_id, name[0], name[1], team_id );
			
			ps = DatabaseImpl.getPlayerInsertPs( dbConn );
			
			ps.setInt(      1, player.getPlayer_id()       );
			
			if   ( is_on_team ) ps.setInt(  2, player.getTeam_id() );
			else                ps.setNull( 2, Types.INTEGER       );
			
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
			ps.setBoolean( 20, player.isRookie()           );
			ps.setBoolean( 21, player.isRetired()          );
			ps.setInt(     22, player.getAward()           );
			ps.setInt(     23, player.getDraft_pick()      );
			ps.setInt(     24, player.getSeasons_played()  );
			ps.setBoolean( 25, player.isReleased()         );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return player;
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
	
	public void updatePlayer( Player player, boolean is_on_team, boolean is_released ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getPlayerUpdatePs( dbConn );
			
			if   ( is_on_team ) ps.setInt(  1, player.getTeam_id() );
			else                ps.setNull( 1, Types.INTEGER       );
			
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
			ps.setBoolean( 18, player.isRookie()           );
			ps.setBoolean( 19, player.isRetired()          );
			ps.setInt(     20, player.getAward()           );
			ps.setInt(     21, player.getDraft_pick()      );
			ps.setInt(     22, player.getSeasons_played()  );
			ps.setBoolean( 23, player.isReleased()         );
			
			if   ( is_released  &&  player.getReleased_by() == 0 ) {
				
				ps.setInt( 24, player.getTeam_id()     );
			}
			else {
				
				ps.setInt( 24, player.getReleased_by() );
			}
			
			ps.setString(  25, year                        );
			ps.setInt(     26, player.getPlayer_id()       );
			
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
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
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

					player.setPlayer_id(       dbRs2.getInt(      1 ) );
					player.setTeam_id(         dbRs2.getInt(      2 ) );
					player.setYear(            dbRs2.getString(   3 ) );
					player.setFirst_name(      dbRs2.getString(   4 ) );
					player.setLast_name(       dbRs2.getString(   5 ) );
					player.setAge(             dbRs2.getInt(      6 ) );
					player.setScoring(         dbRs2.getDouble(   7 ) );
					player.setPassing(         dbRs2.getDouble(   8 ) );
					player.setBlocking(        dbRs2.getDouble(   9 ) );
					player.setTackling(        dbRs2.getDouble(  10 ) );
					player.setStealing(        dbRs2.getDouble(  11 ) );
					player.setPresence(        dbRs2.getDouble(  12 ) );
					player.setDiscipline(      dbRs2.getDouble(  13 ) );
					player.setPenalty_shot(    dbRs2.getDouble(  14 ) );
					player.setPenalty_offense( dbRs2.getDouble(  15 ) );
					player.setPenalty_defense( dbRs2.getDouble(  16 ) );
					player.setEndurance(       dbRs2.getDouble(  17 ) );
					player.setConfidence(      dbRs2.getDouble(  18 ) );
					player.setVitality(        dbRs2.getDouble(  19 ) );
					player.setRookie(          dbRs2.getBoolean( 20 ) );
					player.setRetired(         dbRs2.getBoolean( 21 ) );
					player.setAward(           dbRs2.getInt(     22 ) );
					player.setDraft_pick(      dbRs2.getInt(     23 ) );
					player.setSeasons_played(  dbRs2.getInt(     24 ) );
					player.setAllstar_team_id( dbRs2.getInt(     25 ) );
					player.setReleased(        dbRs2.getBoolean( 26 ) );
					player.setReleased_by(     dbRs2.getInt(     27 ) );
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
				playerStatsView.setPlaying_time(        dbRs.getInt(     3 ) );
				playerStatsView.setAttempts(            dbRs.getInt(     4 ) );
				playerStatsView.setGoals(               dbRs.getInt(     5 ) );
				playerStatsView.setAssists(             dbRs.getInt(     6 ) );
				playerStatsView.setTurnovers(           dbRs.getInt(     7 ) );
				playerStatsView.setStops(               dbRs.getInt(     8 ) );
				playerStatsView.setSteals(              dbRs.getInt(     9 ) );
				playerStatsView.setPenalties(           dbRs.getInt(    10 ) );
				playerStatsView.setOffensive_penalties( dbRs.getInt(    11 ) );
				playerStatsView.setPsa(                 dbRs.getInt(    12 ) );
				playerStatsView.setPsm(                 dbRs.getInt(    13 ) );
				playerStatsView.setOt_psa(              dbRs.getInt(    14 ) );
				playerStatsView.setOt_psm(              dbRs.getInt(    15 ) );
				playerStatsView.setAward(               dbRs.getInt(    16 ) );
				playerStatsView.setAllstar_team_id(     dbRs.getInt(    17 ) );
				playerStatsView.setTeam_id(             dbRs.getInt(    18 ) );
				playerStatsView.setTeam_abbrev(         dbRs.getString( 19 ) );
				
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
					totals.setPlaying_time(        totals.getPlaying_time()        +  playerStatsView.getPlaying_time()        );
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
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
				
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
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
				
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
			
			ps.setString( 1, year    );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
				
				Player player = new Player();
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
				
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
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
				
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

	public void ageUndraftedRookies() throws SQLException {
	
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getAgeUndraftedRookiesUpdatePs( dbConn );
			
			ps.setString(  1, this.year );
			ps.setBoolean( 2, true      );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateSeasonsPlayed() throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getUpdateSeasonesPlayedUpdatePs( dbConn );
			
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
			
			ps.setInt(    1,                    playerGame.getGame_id()               );
			ps.setString( 2,                    playerGame.getYear()                  );
			ps.setDate(   3, new java.sql.Date( playerGame.getDatestamp().getTime() ) );
			ps.setInt(    4,                    playerGame.getType()                  );
			ps.setInt(    5,                    playerGame.getPlayer_id()             );
			ps.setInt(    6,                    playerGame.getTeam_id()               );
			ps.setInt(    7,                    playerGame.getPlaying_time()          );
			ps.setInt(    8,                    playerGame.getAttempts()              );
			ps.setInt(    9,                    playerGame.getGoals()                 );
			ps.setInt(   10,                    playerGame.getAssists()               );
			ps.setInt(   11,                    playerGame.getTurnovers()             );
			ps.setInt(   12,                    playerGame.getStops()                 );
			ps.setInt(   13,                    playerGame.getSteals()                );
			ps.setInt(   14,                    playerGame.getPenalties()             );
			ps.setInt(   15,                    playerGame.getOffensive_penalties()   );
			ps.setInt(   16,                    playerGame.getPsa()                   );
			ps.setInt(   17,                    playerGame.getPsm()                   );
			ps.setInt(   18,                    playerGame.getOt_psa()                );
			ps.setInt(   19,                    playerGame.getOt_psm()                );
			
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
		
		if ( player.isPlayed_in_game() ) playerStats.setGames( 1 );
		
		try {
			
			ps1 = DatabaseImpl.getPlayerStatsSelectPs( dbConn );
			
			ps1.setString( 1, playerStats.getYear()      );
			ps1.setInt(    2, playerStats.getType()      );
			ps1.setInt(    3, playerStats.getPlayer_id() );
			
			dbRs1 = ps1.executeQuery();
			
			if ( dbRs1.next() ) {
			
				// Populate values from database
				playerStats.setGames(               playerStats.getGames()               + dbRs1.getInt(  1 ) );
				playerStats.setPlaying_time(        playerStats.getPlaying_time()        + dbRs1.getInt(  2 ) );
				playerStats.setAttempts(            playerStats.getAttempts()            + dbRs1.getInt(  3 ) );
				playerStats.setGoals(               playerStats.getGoals()               + dbRs1.getInt(  4 ) );
				playerStats.setAssists(             playerStats.getAssists()             + dbRs1.getInt(  5 ) );
				playerStats.setTurnovers(           playerStats.getTurnovers()           + dbRs1.getInt(  6 ) );
				playerStats.setStops(               playerStats.getStops()               + dbRs1.getInt(  7 ) );
				playerStats.setSteals(              playerStats.getSteals()              + dbRs1.getInt(  8 ) );
				playerStats.setPenalties(           playerStats.getPenalties()           + dbRs1.getInt(  9 ) );
				playerStats.setOffensive_penalties( playerStats.getOffensive_penalties() + dbRs1.getInt( 10 ) );
				playerStats.setPsa(                 playerStats.getPsa()                 + dbRs1.getInt( 11 ) );
				playerStats.setPsm(                 playerStats.getPsm()                 + dbRs1.getInt( 12 ) );
				playerStats.setOt_psa(              playerStats.getOt_psa()              + dbRs1.getInt( 13 ) );
				playerStats.setOt_psm(              playerStats.getOt_psm()              + dbRs1.getInt( 14 ) );
				
				// Update the database
				ps2 = DatabaseImpl.getPlayerStatsUpdatePs( dbConn );

				ps2.setInt(     1, playerStats.getGames()               );
				ps2.setInt(     2, playerStats.getPlaying_time()        );
				ps2.setInt(     3, playerStats.getAttempts()            );
				ps2.setInt(     4, playerStats.getGoals()               );
				ps2.setInt(     5, playerStats.getAssists()             );
				ps2.setInt(     6, playerStats.getTurnovers()           );
				ps2.setInt(     7, playerStats.getStops()               );
				ps2.setInt(     8, playerStats.getSteals()              );
				ps2.setInt(     9, playerStats.getPenalties()           );
				ps2.setInt(    10, playerStats.getOffensive_penalties() );
				ps2.setInt(    11, playerStats.getPsa()                 );
				ps2.setInt(    12, playerStats.getPsm()                 );
				ps2.setInt(    13, playerStats.getOt_psa()              );
				ps2.setInt(    14, playerStats.getOt_psm()              );
				ps2.setString( 15, playerStats.getYear()                );
				ps2.setInt(    16, playerStats.getType()                );
				ps2.setInt(    17, playerStats.getPlayer_id()           );
				
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
			ps1.setInt(     4, playerStats.getGames()               );
			ps1.setInt(     5, playerStats.getPlaying_time()        );
			ps1.setInt(     6, playerStats.getAttempts()            );
			ps1.setInt(     7, playerStats.getGoals()               );
			ps1.setInt(     8, playerStats.getAssists()             );
			ps1.setInt(     9, playerStats.getTurnovers()           );
			ps1.setInt(    10, playerStats.getStops()               );
			ps1.setInt(    11, playerStats.getSteals()              );
			ps1.setInt(    12, playerStats.getPenalties()           );
			ps1.setInt(    13, playerStats.getOffensive_penalties() );
			ps1.setInt(    14, playerStats.getPsa()                 );
			ps1.setInt(    15, playerStats.getPsm()                 );
			ps1.setInt(    16, playerStats.getOt_psa()              );
			ps1.setInt(    17, playerStats.getOt_psm()              );
			
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
				playerStatsView.setPlaying_time(        dbRs.getInt(  3 ) );
				playerStatsView.setAttempts(            dbRs.getInt(  4 ) );
				playerStatsView.setGoals(               dbRs.getInt(  5 ) );
				playerStatsView.setAssists(             dbRs.getInt(  6 ) );
				playerStatsView.setTurnovers(           dbRs.getInt(  7 ) );
				playerStatsView.setStops(               dbRs.getInt(  8 ) );
				playerStatsView.setSteals(              dbRs.getInt(  9 ) );
				playerStatsView.setPenalties(           dbRs.getInt( 10 ) );
				playerStatsView.setOffensive_penalties( dbRs.getInt( 11 ) );
				playerStatsView.setPsa(                 dbRs.getInt( 12 ) );
				playerStatsView.setPsm(                 dbRs.getInt( 13 ) );
				playerStatsView.setOt_psa(              dbRs.getInt( 14 ) );
				playerStatsView.setOt_psm(              dbRs.getInt( 15 ) );
				
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
			
			ps.setString( 1, year );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				AwardsView awardsView = new AwardsView();
				
				awardsView.setPlayer_id(   dbRs.getInt(     1 ) );
				awardsView.setFirst_name(  dbRs.getString(  2 ) );
				awardsView.setLast_name(   dbRs.getString(  3 ) );
				awardsView.setTeam_id(     dbRs.getInt(     4 ) );
				awardsView.setTeam_abbrev( dbRs.getString(  5 ) );
				awardsView.setConference(  dbRs.getInt(     6 ) );
				awardsView.setDivision(    dbRs.getInt(     7 ) );
				awardsView.setAward(       dbRs.getInt(     8 ) );
				awardsView.setRookie(      dbRs.getBoolean( 9 ) );
				
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
				
				player.setPlayer_id(       dbRs.getInt(      1 ) );
				player.setTeam_id(         dbRs.getInt(      2 ) );
				player.setYear(            dbRs.getString(   3 ) );
				player.setFirst_name(      dbRs.getString(   4 ) );
				player.setLast_name(       dbRs.getString(   5 ) );
				player.setAge(             dbRs.getInt(      6 ) );
				player.setScoring(         dbRs.getDouble(   7 ) );
				player.setPassing(         dbRs.getDouble(   8 ) );
				player.setBlocking(        dbRs.getDouble(   9 ) );
				player.setTackling(        dbRs.getDouble(  10 ) );
				player.setStealing(        dbRs.getDouble(  11 ) );
				player.setPresence(        dbRs.getDouble(  12 ) );
				player.setDiscipline(      dbRs.getDouble(  13 ) );
				player.setPenalty_shot(    dbRs.getDouble(  14 ) );
				player.setPenalty_offense( dbRs.getDouble(  15 ) );
				player.setPenalty_defense( dbRs.getDouble(  16 ) );
				player.setEndurance(       dbRs.getDouble(  17 ) );
				player.setConfidence(      dbRs.getDouble(  18 ) );
				player.setVitality(        dbRs.getDouble(  19 ) );
				player.setRookie(          dbRs.getBoolean( 20 ) );
				player.setRetired(         dbRs.getBoolean( 21 ) );
				player.setAward(           dbRs.getInt(     22 ) );
				player.setDraft_pick(      dbRs.getInt(     23 ) );
				player.setSeasons_played(  dbRs.getInt(     24 ) );
				player.setAllstar_team_id( dbRs.getInt(     25 ) );
				player.setReleased(        dbRs.getBoolean( 26 ) );
				player.setReleased_by(     dbRs.getInt(     27 ) );
				
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
				playerGameView.setPlaying_time(        dbRs.getInt(      4 ) );
				playerGameView.setAttempts(            dbRs.getInt(      5 ) );
				playerGameView.setGoals(               dbRs.getInt(      6 ) );
				playerGameView.setAssists(             dbRs.getInt(      7 ) );
				playerGameView.setTurnovers(           dbRs.getInt(      8 ) );
				playerGameView.setStops(               dbRs.getInt(      9 ) );
				playerGameView.setSteals(              dbRs.getInt(     10 ) );
				playerGameView.setPenalties(           dbRs.getInt(     11 ) );
				playerGameView.setOffensive_penalties( dbRs.getInt(     12 ) );
				playerGameView.setPsa(                 dbRs.getInt(     13 ) );
				playerGameView.setPsm(                 dbRs.getInt(     14 ) );
				playerGameView.setOt_psa(              dbRs.getInt(     15 ) );
				playerGameView.setOt_psm(              dbRs.getInt(     16 ) );
				
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

	public void updateAllstarTeamId( int player_id, int team_id ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getAllstarTeamIdUpdatePs( dbConn );
			
			ps.setInt(    1, team_id   );
			ps.setString( 2, this.year );
			ps.setInt(    3, player_id );
			
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
			
			ps.setString( 1, year    );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();
			
			while ( dbRs.next() ) {
			
				AllstarView allstarView = new AllstarView();
				
				allstarView.setPlayer_id(   dbRs.getInt(     1 ) );
				allstarView.setFirst_name(  dbRs.getString(  2 ) );
				allstarView.setLast_name(   dbRs.getString(  3 ) );
				allstarView.setRookie(      dbRs.getBoolean( 4 ) );
				allstarView.setTeam_id(     dbRs.getInt(     5 ) );
				allstarView.setTeam_abbrev( dbRs.getString(  6 ) );
				
				if ( allstars == null ) allstars = new ArrayList();
				
				allstars.add( allstarView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return allstars;
	}

	public void retirePlayers() throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getRetirePlayersUpdatePs( dbConn );
			
			ps.setBoolean( 1, true      );
			ps.setString(  2, this.year );
			
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
	
}
