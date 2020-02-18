package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import natc.data.Manager;
import natc.data.Player;
import natc.data.Team;
import natc.data.TeamGame;
import natc.service.ManagerService;
import natc.service.TeamService;
import natc.view.ManagerStatsView;
import natc.view.ManagerView;

public class ManagerServiceImpl implements ManagerService {

	private Connection dbConn = null;
	private String     year   = null;
	
	public ManagerServiceImpl( Connection dbConn, String year ) {
	
		this.dbConn = dbConn;
		this.year   = year;
	}

	private int getNextManagerId() throws SQLException {
	
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		int               id   = 0;
		
		try {
			
			ps = DatabaseImpl.getNextManagerIdSelectPs( dbConn );
			
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
	
	private String[] generateManagerName() throws SQLException {
		
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
	
	public Manager generateManager() throws SQLException {
		
		PreparedStatement ps      = null;
		Manager           manager = null;
		
		try {
			
			int      manager_id = getNextManagerId();
			String[] name       = generateManagerName();
			
			manager = new Manager( manager_id, name[0], name[1] );
			
			ps = DatabaseImpl.getManagerInsertPs( dbConn );
			
			ps.setInt(      1, manager.getManager_id() );
			ps.setNull(     2, Types.INTEGER           );
			ps.setNull(     3, Types.INTEGER           );
			ps.setString(   4, year                    );
			ps.setString(   5, manager.getFirst_name() );
			ps.setString(   6, manager.getLast_name()  );
			ps.setInt(      7, manager.getAge()        );
			ps.setDouble(   8, manager.getOffense()    );
			ps.setDouble(   9, manager.getDefense()    );
			ps.setDouble(  10, manager.getIntangible() );
			ps.setDouble(  11, manager.getPenalties()  );
			ps.setDouble(  12, manager.getVitality()   );
			ps.setInt(     13, manager.getStyle()      );
			ps.setBoolean( 14, manager.isNew_hire()    );
			ps.setBoolean( 15, manager.isReleased()    );
			ps.setBoolean( 16, manager.isRetired()     );
			ps.setInt(     17, manager.getAward()      );
			ps.setInt(     18, manager.getSeasons()    );
			ps.setInt(     19, manager.getScore()      );
			ps.setDouble(  20, manager.getCpr()        );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return null;
	}

	public Manager generateManagerFromPlayer(Player player) throws SQLException {
		
		PreparedStatement ps      = null;
		Manager           manager = null;
		
		try {
			
			int manager_id = getNextManagerId();
			
			manager = new Manager( manager_id, player.getPlayer_id(), player.getFirst_name(), player.getLast_name() );
			
			manager.setOffense(    player.getOffensiveRating()  );
			manager.setDefense(    player.getDefensiveRating()  );
			manager.setIntangible( player.getIntangibleRating() );
			manager.setPenalties(  player.getPenaltyRating()    );
			manager.setVitality(   player.getVitality()         );
			manager.setAge(        player.getAge()              );
			
			manager.determineStyle();
			
			// adjust manager age and check for retirement
			int thisYear        = Integer.parseInt( year             );
			int playersLastYear = Integer.parseInt( player.getYear() );
			
			manager.setAge( manager.getAge() + (thisYear - playersLastYear) );
			
			if ( manager.readyToRetire() ) return null;
			
			ps = DatabaseImpl.getManagerInsertPs( dbConn );
			
			ps.setInt(      1, manager.getManager_id() );
			ps.setNull(     2, Types.INTEGER           );
			ps.setInt(      3, manager.getPlayer_id()  );
			ps.setString(   4, year                    );
			ps.setString(   5, manager.getFirst_name() );
			ps.setString(   6, manager.getLast_name()  );
			ps.setInt(      7, manager.getAge()        );
			ps.setDouble(   8, manager.getOffense()    );
			ps.setDouble(   9, manager.getDefense()    );
			ps.setDouble(  10, manager.getIntangible() );
			ps.setDouble(  11, manager.getPenalties()  );
			ps.setDouble(  12, manager.getVitality()   );
			ps.setInt(     13, manager.getStyle()      );
			ps.setBoolean( 14, manager.isNew_hire()    );
			ps.setBoolean( 15, manager.isReleased()    );
			ps.setBoolean( 16, manager.isRetired()     );
			ps.setInt(     17, manager.getAward()      );
			ps.setInt(     18, manager.getSeasons()    );
			ps.setInt(     19, manager.getScore()      );
			ps.setDouble(  20, manager.getCpr()        );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return manager;
	}

	public List getFreeManagers() throws SQLException {
		
		List managers = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getFreeManagersSelectPs( dbConn );
			
			ps.setString( 1, year      );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
			
				Manager manager = new Manager();
				
				manager.setManager_id(      dbRs.getInt(      1 ) );
				manager.setTeam_id(         dbRs.getInt(      2 ) );
				manager.setPlayer_id(       dbRs.getInt(      3 ) );
				manager.setYear(            dbRs.getString(   4 ) );
				manager.setFirst_name(      dbRs.getString(   5 ) );
				manager.setLast_name(       dbRs.getString(   6 ) );
				manager.setAge(             dbRs.getInt(      7 ) );
				manager.setOffense(         dbRs.getDouble(   8 ) );
				manager.setDefense(         dbRs.getDouble(   9 ) );
				manager.setIntangible(      dbRs.getDouble(  10 ) );
				manager.setPenalties(       dbRs.getDouble(  11 ) );
				manager.setVitality(        dbRs.getDouble(  12 ) );
				manager.setStyle(           dbRs.getInt(     13 ) );
				manager.setNew_hire(        dbRs.getBoolean( 14 ) );
				manager.setReleased(        dbRs.getBoolean( 15 ) );
				manager.setReleased_by(     dbRs.getInt(     16 ) );
				manager.setRetired(         dbRs.getBoolean( 17 ) );
				manager.setAllstar_team_id( dbRs.getInt(     18 ) );
				manager.setAward(           dbRs.getInt(     19 ) );
				manager.setSeasons(         dbRs.getInt(     20 ) );
				manager.setScore(           dbRs.getInt(     21 ) );
				manager.setCpr(             dbRs.getDouble(  22 ) );
				
				if ( managers == null ) managers = new ArrayList();
				
				managers.add( manager );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managers;
	}

	public Manager getManagerById( int manager_id ) throws SQLException {
		
		Manager manager = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, manager_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				manager = new Manager();
				
				manager.setManager_id(      dbRs.getInt(      1 ) );
				manager.setTeam_id(         dbRs.getInt(      2 ) );
				manager.setPlayer_id(       dbRs.getInt(      3 ) );
				manager.setYear(            dbRs.getString(   4 ) );
				manager.setFirst_name(      dbRs.getString(   5 ) );
				manager.setLast_name(       dbRs.getString(   6 ) );
				manager.setAge(             dbRs.getInt(      7 ) );
				manager.setOffense(         dbRs.getDouble(   8 ) );
				manager.setDefense(         dbRs.getDouble(   9 ) );
				manager.setIntangible(      dbRs.getDouble(  10 ) );
				manager.setPenalties(       dbRs.getDouble(  11 ) );
				manager.setVitality(        dbRs.getDouble(  12 ) );
				manager.setStyle(           dbRs.getInt(     13 ) );
				manager.setNew_hire(        dbRs.getBoolean( 14 ) );
				manager.setReleased(        dbRs.getBoolean( 15 ) );
				manager.setReleased_by(     dbRs.getInt(     16 ) );
				manager.setRetired(         dbRs.getBoolean( 17 ) );
				manager.setAllstar_team_id( dbRs.getInt(     18 ) );
				manager.setAward(           dbRs.getInt(     19 ) );
				manager.setSeasons(         dbRs.getInt(     20 ) );
				manager.setScore(           dbRs.getInt(     21 ) );
				manager.setCpr(             dbRs.getDouble(  22 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return manager;
	}

	public Manager getLatestManagerById( int manager_id ) throws SQLException {

		Manager manager = null;
		

		PreparedStatement ps1       = null;
		PreparedStatement ps2       = null;
		ResultSet         dbRs1     = null;
		ResultSet         dbRs2     = null;

		try {

			ps1 = DatabaseImpl.getLatestYearForManager( dbConn );

			ps1.setInt( 1, manager_id );

			dbRs1 = ps1.executeQuery();

			if ( dbRs1.next() ) {

				ps2 = DatabaseImpl.getManagerByIdSelectPs( dbConn );

				ps2.setString( 1, dbRs1.getString( 1 ) );
				ps2.setInt(    2, manager_id           );

				dbRs2 = ps2.executeQuery();

				if ( dbRs2.next() ) {
					
					manager = new Manager();

					manager.setManager_id(      dbRs2.getInt(      1 ) );
					manager.setTeam_id(         dbRs2.getInt(      2 ) );
					manager.setPlayer_id(       dbRs2.getInt(      3 ) );
					manager.setYear(            dbRs2.getString(   4 ) );
					manager.setFirst_name(      dbRs2.getString(   5 ) );
					manager.setLast_name(       dbRs2.getString(   6 ) );
					manager.setAge(             dbRs2.getInt(      7 ) );
					manager.setOffense(         dbRs2.getDouble(   8 ) );
					manager.setDefense(         dbRs2.getDouble(   9 ) );
					manager.setIntangible(      dbRs2.getDouble(  10 ) );
					manager.setPenalties(       dbRs2.getDouble(  11 ) );
					manager.setVitality(        dbRs2.getDouble(  12 ) );
					manager.setStyle(           dbRs2.getInt(     13 ) );
					manager.setNew_hire(        dbRs2.getBoolean( 14 ) );
					manager.setReleased(        dbRs2.getBoolean( 15 ) );
					manager.setReleased_by(     dbRs2.getInt(     16 ) );
					manager.setRetired(         dbRs2.getBoolean( 17 ) );
					manager.setAllstar_team_id( dbRs2.getInt(     18 ) );
					manager.setAward(           dbRs2.getInt(     19 ) );
					manager.setSeasons(         dbRs2.getInt(     20 ) );
					manager.setScore(           dbRs2.getInt(     21 ) );
					manager.setCpr(             dbRs2.getDouble(  22 ) );
				}
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs1 );
			DatabaseImpl.closeDbRs( dbRs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}
		
		return manager;
	}

	public Manager getManagerByTeamId( int team_id ) throws SQLException {

		Manager manager = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				manager = new Manager();
				
				manager.setManager_id(      dbRs.getInt(      1 ) );
				manager.setTeam_id(         dbRs.getInt(      2 ) );
				manager.setPlayer_id(       dbRs.getInt(      3 ) );
				manager.setYear(            dbRs.getString(   4 ) );
				manager.setFirst_name(      dbRs.getString(   5 ) );
				manager.setLast_name(       dbRs.getString(   6 ) );
				manager.setAge(             dbRs.getInt(      7 ) );
				manager.setOffense(         dbRs.getDouble(   8 ) );
				manager.setDefense(         dbRs.getDouble(   9 ) );
				manager.setIntangible(      dbRs.getDouble(  10 ) );
				manager.setPenalties(       dbRs.getDouble(  11 ) );
				manager.setVitality(        dbRs.getDouble(  12 ) );
				manager.setStyle(           dbRs.getInt(     13 ) );
				manager.setNew_hire(        dbRs.getBoolean( 14 ) );
				manager.setReleased(        dbRs.getBoolean( 15 ) );
				manager.setReleased_by(     dbRs.getInt(     16 ) );
				manager.setRetired(         dbRs.getBoolean( 17 ) );
				manager.setAllstar_team_id( dbRs.getInt(     18 ) );
				manager.setAward(           dbRs.getInt(     19 ) );
				manager.setSeasons(         dbRs.getInt(     20 ) );
				manager.setScore(           dbRs.getInt(     21 ) );
				manager.setCpr(             dbRs.getDouble(  22 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return manager;
	}

	public Manager getManagerByAllstarTeamId( int team_id ) throws SQLException {

		Manager manager = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByAllstarTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				manager = new Manager();
				
				manager.setManager_id(      dbRs.getInt(      1 ) );
				manager.setTeam_id(         dbRs.getInt(      2 ) );
				manager.setPlayer_id(       dbRs.getInt(      3 ) );
				manager.setYear(            dbRs.getString(   4 ) );
				manager.setFirst_name(      dbRs.getString(   5 ) );
				manager.setLast_name(       dbRs.getString(   6 ) );
				manager.setAge(             dbRs.getInt(      7 ) );
				manager.setOffense(         dbRs.getDouble(   8 ) );
				manager.setDefense(         dbRs.getDouble(   9 ) );
				manager.setIntangible(      dbRs.getDouble(  10 ) );
				manager.setPenalties(       dbRs.getDouble(  11 ) );
				manager.setVitality(        dbRs.getDouble(  12 ) );
				manager.setStyle(           dbRs.getInt(     13 ) );
				manager.setNew_hire(        dbRs.getBoolean( 14 ) );
				manager.setReleased(        dbRs.getBoolean( 15 ) );
				manager.setReleased_by(     dbRs.getInt(     16 ) );
				manager.setRetired(         dbRs.getBoolean( 17 ) );
				manager.setAllstar_team_id( dbRs.getInt(     18 ) );
				manager.setAward(           dbRs.getInt(     19 ) );
				manager.setSeasons(         dbRs.getInt(     20 ) );
				manager.setScore(           dbRs.getInt(     21 ) );
				manager.setCpr(             dbRs.getDouble(  22 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return manager;
	}

	public ManagerView getManagerViewByAllstarTeamId( int team_id ) throws SQLException {

		ManagerView managerView = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerViewByAllstarTeamIdSelectPs( dbConn );
			
			ps.setString( 1, year      );
			ps.setInt(    2, team_id );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				managerView = new ManagerView();
				
				managerView.setManager_id(  dbRs.getInt(    1 ) );
				managerView.setFirst_name(  dbRs.getString( 2 ) );
				managerView.setLast_name(   dbRs.getString( 3 ) );
				managerView.setTeam_id(     dbRs.getInt(    4 ) );
				managerView.setTeam_abbrev( dbRs.getString( 5 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managerView;
	}

	public int getManagerCount() throws SQLException {
		
		int count = 0;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {

			ps = DatabaseImpl.getManagerCountSelectPs( dbConn );
			
			ps.setString( 1, year );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
				
				count = dbRs.getInt( 1 );
			}
		}
		finally {
		
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return count;
	}

	public List getRetiredManagers() throws SQLException {
		
		List managers = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getRetiredManagersSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
			
				ManagerView managerView = new ManagerView();
				
				managerView.setManager_id(  dbRs.getInt(    1 ) );
				managerView.setFirst_name(  dbRs.getString( 2 ) );
				managerView.setLast_name(   dbRs.getString( 3 ) );
				
				if ( managers == null ) managers = new ArrayList();
				
				managers.add( managerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managers;
	}

	public List getFiredManagers() throws SQLException {

		List managers = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getFiredManagersSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
			
				ManagerView managerView = new ManagerView();
				
				managerView.setManager_id(  dbRs.getInt(    1 ) );
				managerView.setFirst_name(  dbRs.getString( 2 ) );
				managerView.setLast_name(   dbRs.getString( 3 ) );
				managerView.setTeam_id(     dbRs.getInt(    4 ) );
				managerView.setTeam_abbrev( dbRs.getString( 5 ) );
				
				if ( managers == null ) managers = new ArrayList();
				
				managers.add( managerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managers;
	}

	public List getHiredManagers() throws SQLException {

		List managers = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getHiredManagersSelectPs( dbConn );
			
			ps.setString(  1, year );
			ps.setBoolean( 2, true );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
			
				ManagerView managerView = new ManagerView();
				
				managerView.setManager_id(  dbRs.getInt(    1 ) );
				managerView.setFirst_name(  dbRs.getString( 2 ) );
				managerView.setLast_name(   dbRs.getString( 3 ) );
				managerView.setTeam_id(     dbRs.getInt(    4 ) );
				managerView.setTeam_abbrev( dbRs.getString( 5 ) );
				
				if ( managers == null ) managers = new ArrayList();
				
				managers.add( managerView );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managers;
	}

	public void updateManager(Manager manager) throws SQLException {

		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getManagerUpdatePs( dbConn );
			
			if   ( manager.getTeam_id() == 0 ) ps.setNull( 1, Types.INTEGER        );
			else                               ps.setInt(  1, manager.getTeam_id() );
			
			if   ( manager.getPlayer_id() == 0 ) ps.setNull( 2, Types.INTEGER          );
			else                                 ps.setInt(  2, manager.getPlayer_id() );
			
			ps.setString(   3, manager.getFirst_name()  );
			ps.setString(   4, manager.getLast_name()   );
			ps.setInt(      5, manager.getAge()         );
			ps.setDouble(   6, manager.getOffense()     );
			ps.setDouble(   7, manager.getDefense()     );
			ps.setDouble(   8, manager.getIntangible()  );
			ps.setDouble(   9, manager.getPenalties()   );
			ps.setDouble(  10, manager.getVitality()    );
			ps.setInt(     11, manager.getStyle()       );
			ps.setBoolean( 12, manager.isNew_hire()     );
			ps.setBoolean( 13, manager.isReleased()     );
			
			if   ( manager.getReleased_by() == 0 ) ps.setNull( 14, Types.INTEGER            );
			else                                   ps.setInt(  14, manager.getReleased_by() );
			
			ps.setBoolean( 15, manager.isRetired()      );
			ps.setInt(     16, manager.getAward()       );
			ps.setInt(     17, manager.getSeasons()     );
			ps.setInt(     18, manager.getScore()       );
			ps.setDouble(  19, manager.getCpr()         );
			
			ps.setString(  20, manager.getYear()       );
			ps.setInt(     21, manager.getManager_id() );
			
			ps.executeUpdate();
		}
		finally {
		
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateAllstarTeamId( int manager_id, int allstar_team_id ) throws SQLException {

		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getManagerAllstarTeamIdUpdatePs( dbConn );
			
			ps.setInt(    1, allstar_team_id   );
			ps.setString( 2, this.year );
			ps.setInt(    3, manager_id );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public List getManagerHistoryByManagerId( int manager_id ) throws SQLException {

		List managerStats = null;
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerHistoryByIdSelectPs( dbConn );
			
			ps.setInt( 1, manager_id                );
			ps.setInt( 2, TeamGame.gt_RegularSeason );
			ps.setInt( 3, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {
			
				ManagerStatsView managerStatsView = new ManagerStatsView();
				
				managerStatsView.setTeam_id(         dbRs.getInt(     1 ) );
				managerStatsView.setTeam_abbrev(     dbRs.getString(  2 ) );
				managerStatsView.setYear(            dbRs.getString(  3 ) );
				managerStatsView.setGames(           dbRs.getInt(     4 ) );
				managerStatsView.setWins(            dbRs.getInt(     5 ) );
				managerStatsView.setLosses(          dbRs.getInt(     6 ) );
				managerStatsView.setDivision_rank(   dbRs.getInt(     7 ) );
				managerStatsView.setPlayoff_rank(    dbRs.getInt(     8 ) );
				managerStatsView.setOff_possession(  dbRs.getInt(     9 ) );
				managerStatsView.setOff_points(      dbRs.getInt(    10 ) );
				managerStatsView.setOff_attempts(    dbRs.getInt(    11 ) );
				managerStatsView.setOff_goals(       dbRs.getInt(    12 ) );
				managerStatsView.setOff_psa(         dbRs.getInt(    13 ) );
				managerStatsView.setOff_psm(         dbRs.getInt(    14 ) );
				managerStatsView.setDef_possession(  dbRs.getInt(    15 ) );
				managerStatsView.setDef_points(      dbRs.getInt(    16 ) );
				managerStatsView.setDef_attempts(    dbRs.getInt(    17 ) );
				managerStatsView.setDef_goals(       dbRs.getInt(    18 ) );
				managerStatsView.setDef_psa(         dbRs.getInt(    19 ) );
				managerStatsView.setDef_psm(         dbRs.getInt(    20 ) );
				managerStatsView.setAllstar_team_id( dbRs.getInt(    21 ) );
				managerStatsView.setAward(           dbRs.getInt(    22 ) );
				
				if ( managerStats == null ) managerStats = new ArrayList();
				
				managerStats.add( managerStatsView );
			}
			
			if ( managerStats != null ) {
			
				ManagerStatsView totals = new ManagerStatsView();
				
				totals.setYear( "Total" );
				
				Iterator i = managerStats.iterator();
				
				while ( i.hasNext() ) {
				
					ManagerStatsView managerStatsView = (ManagerStatsView)i.next();
					
					totals.setGames(          totals.getGames()          + managerStatsView.getGames()          );
					totals.setWins(           totals.getWins()           + managerStatsView.getWins()           );
					totals.setLosses(         totals.getLosses()         + managerStatsView.getLosses()         );
					totals.setOff_possession( totals.getOff_possession() + managerStatsView.getOff_possession() );
					totals.setOff_points(     totals.getOff_points()     + managerStatsView.getOff_points()     );
					totals.setOff_attempts(   totals.getOff_attempts()   + managerStatsView.getOff_attempts()   );
					totals.setOff_goals(      totals.getOff_goals()      + managerStatsView.getOff_goals()      );
					totals.setOff_psa(        totals.getOff_psa()        + managerStatsView.getOff_psa()        );
					totals.setOff_psm(        totals.getOff_psm()        + managerStatsView.getOff_psm()        );
					totals.setDef_possession( totals.getDef_possession() + managerStatsView.getDef_possession() );
					totals.setDef_points(     totals.getDef_points()     + managerStatsView.getDef_points()     );
					totals.setDef_attempts(   totals.getDef_attempts()   + managerStatsView.getDef_attempts()   );
					totals.setDef_goals(      totals.getDef_goals()      + managerStatsView.getDef_goals()      );
					totals.setDef_psa(        totals.getDef_psa()        + managerStatsView.getDef_psa()        );
					totals.setDef_psm(        totals.getDef_psm()        + managerStatsView.getDef_psm()        );
				}
				
				managerStats.add( totals );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managerStats;
	}

	public void updateSeasons() throws SQLException {

		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getUpdateSeasonsUpdatePs( dbConn );
			
			ps.setString(  1, this.year );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateManagersForNewSeason( String last_year ) throws SQLException {

		PreparedStatement ps       = null;
		
		try {
			
			ps = DatabaseImpl.getCopyManagersForNewYearCallPs( dbConn );
			
			ps.setString( 1, last_year );
			ps.setString( 2, this.year );
			
			ps.execute();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void ageManagers() throws SQLException {

		PreparedStatement ps       = null;
		
		try {
			
			ps = DatabaseImpl.getAgeManagersUpdatePs( dbConn );
			
			ps.setString( 1, this.year );
			
			ps.execute();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateScore() throws SQLException {
		
		TeamService teamService = new TeamServiceImpl( dbConn, this.year );
		
		List teamList = teamService.getTeamList();
		
		Iterator i = teamList.iterator();
		
		while ( i.hasNext() ) {
		
			Team team = (Team)i.next();
			
			Manager manager = getManagerByTeamId( team.getTeam_id() );
			
			// Add points based on team success
			if      ( team.getPlayoff_rank() == 5                ) manager.setScore( manager.getScore() + 5 );
			else if ( team.getPlayoff_rank() >  0                ) manager.setScore( manager.getScore() + 3 );
			else if ( team.getWins()         >  team.getLosses() ) manager.setScore( manager.getScore() + 1 );
			
			// Add points based on manager awards
			if      ( manager.getAward()            > 0 ) manager.setScore( manager.getScore() + 2 );
			else if ( manager.getAllstar_team_id() != 0 ) manager.setScore( manager.getScore() + 1 );
			
			updateManager( manager );
		}
	}

	public void selectManagerOfTheYear() throws SQLException {
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByPerformanceSelectPs( dbConn );
			
			ps.setString( 1,     this.year             );
			ps.setInt(    2, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				int manager_id = dbRs.getInt( 1 );
				
				Manager manager = getManagerById( manager_id );
				
				manager.setAward( Manager.MANAGER_OF_THE_YEAR );
				
				updateManager( manager );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public ManagerView getManagerOfTheYear() throws SQLException {

		ManagerView managerView = null;

		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByAwardSelectPs( dbConn );
			
			ps.setString( 1,    this.year                );
			ps.setInt(    2, Manager.MANAGER_OF_THE_YEAR );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				managerView = new ManagerView();
				
				managerView.setManager_id(  dbRs.getInt(    1 ) );
				managerView.setFirst_name(  dbRs.getString( 2 ) );
				managerView.setLast_name(   dbRs.getString( 3 ) );
				managerView.setTeam_id(     dbRs.getInt(    4 ) );
				managerView.setTeam_abbrev( dbRs.getString( 5 ) );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return managerView;
	}

	public Manager getBestManagerByDivision( int division ) throws SQLException {
		
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		Manager           manager  = null;
		
		try {
			
			ps = DatabaseImpl.getManagerByPerfForDivSelectPs( dbConn );
			
			ps.setString( 1,     this.year             );
			ps.setInt(    2,          division         );
			ps.setInt(    3, TeamGame.gt_RegularSeason );
			
			dbRs = ps.executeQuery();
			
			if ( dbRs.next() ) {
			
				int manager_id = dbRs.getInt( 1 );
				
				manager = getManagerById( manager_id );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return manager;
	}

	public void updateCareerPerformanceRating() throws SQLException {
	
		PreparedStatement ps1     = null;
		PreparedStatement ps2     = null;
		ResultSet         rs1     = null;
		ResultSet         rs2     = null;
		
		try {
			
			ps1 = DatabaseImpl.getActiveManagerIdsSelectPs( dbConn );
			
			ps1.setString( 1, year );
			
			rs1 = ps1.executeQuery();
			
			while ( rs1.next() ) {
			
				int seasons    = 0;
				int totalScore = 0;
				int manager_id = rs1.getInt( 1 );
				
				ps2 = DatabaseImpl.getManagerSeasonsSelectPs( dbConn );
				
				ps2.setInt( 1, manager_id );
				
				rs2 = ps2.executeQuery();
				
				if ( rs2.next() ) seasons = rs2.getInt( 1 );
				
				DatabaseImpl.closeDbRs(   rs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				ps2 = DatabaseImpl.getManagerTotalScoreSelectPs( dbConn );
				
				ps2.setInt( 1, manager_id );
				
				rs2 = ps2.executeQuery();
				
				if ( rs2.next() ) totalScore = rs2.getInt( 1 );
				
				DatabaseImpl.closeDbRs(   rs2 );
				DatabaseImpl.closeDbStmt( ps2 );
				
				double cpr = (double)totalScore / (double)seasons;
				
				ps2 = DatabaseImpl.getManagerCprUpdatePs( dbConn );
				
				ps2.setDouble( 1, cpr        );
				ps2.setString( 2, year       );
				ps2.setInt(    3, manager_id );
				
				ps2.execute();
			}
		}
		finally {

			DatabaseImpl.closeDbRs(   rs1 );
			DatabaseImpl.closeDbRs(   rs2 );
			DatabaseImpl.closeDbStmt( ps1 );
			DatabaseImpl.closeDbStmt( ps2 );
		}
	}
}
