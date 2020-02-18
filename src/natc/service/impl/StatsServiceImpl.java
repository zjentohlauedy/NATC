package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import natc.data.TeamGame;
import natc.service.StatsService;
import natc.view.StatsView;

public class StatsServiceImpl implements StatsService {

	private Connection dbConn = null;
	
	public StatsServiceImpl( Connection dbConn ) {
	
		this.dbConn = dbConn;
	}
	
	private class Parameters {
	
		private int     stat;
		private String  headingKey;
		private boolean ascending;
		
		public Parameters( int stat, String headingKey, boolean ascending ) {
		
			this.stat       = stat;
			this.headingKey = headingKey;
			this.ascending  = ascending;
		}

		public boolean isAscending() {
			return ascending;
		}

		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}

		public String getHeadingKey() {
			return headingKey;
		}

		public void setHeadingKey(String headingKey) {
			this.headingKey = headingKey;
		}

		public int getStat() {
			return stat;
		}

		public void setStat(int stat) {
			this.stat = stat;
		}

	}
	
	public Collection getTopPlayersByGame() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,         StatsView.KEY_SCORE,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,      StatsView.KEY_ATTEMPTS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,         StatsView.KEY_GOALS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ASSISTS,       StatsView.KEY_ASSISTS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OFFENSE,       StatsView.KEY_OFFENSE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,     StatsView.KEY_TURNOVERS,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STOPS,         StatsView.KEY_STOPS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,        StatsView.KEY_STEALS,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,     StatsView.KEY_PENALTIES,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,           StatsView.KEY_PSA,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,           StatsView.KEY_PSM,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,        StatsView.KEY_OT_PSA,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,        StatsView.KEY_OT_PSM,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TIME_PER_GAME, StatsView.KEY_TIME_PER_GAME, false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getPlayerStatsByGameSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.PLAYERBYGAME );
					
					if ( param.getHeadingKey().equals( StatsView.KEY_TIME_PER_GAME ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setPlayer_id(  dbRs.getInt(    2 ) );
					statsView.setFirst_name( dbRs.getString( 3 ) );
					statsView.setLast_name(  dbRs.getString( 4 ) );
					statsView.setYear(       dbRs.getString( 5 ) );
					statsView.setGame_id(    dbRs.getInt(    6 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey() );
						header.setHeadingKey2( StatsView.KEY_PLAYER  );
						header.setHeadingKey3( StatsView.KEY_YEAR    );
						header.setHeadingKey4( StatsView.KEY_UNUSED  );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopPlayersBySeason() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,         StatsView.KEY_SCORE,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,      StatsView.KEY_ATTEMPTS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,         StatsView.KEY_GOALS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ASSISTS,       StatsView.KEY_ASSISTS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OFFENSE,       StatsView.KEY_OFFENSE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,     StatsView.KEY_TURNOVERS,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STOPS,         StatsView.KEY_STOPS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,        StatsView.KEY_STEALS,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,     StatsView.KEY_PENALTIES,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,           StatsView.KEY_PSA,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,           StatsView.KEY_PSM,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,        StatsView.KEY_OT_PSA,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,        StatsView.KEY_OT_PSM,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,     StatsView.KEY_SCORE_PCT,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,        StatsView.KEY_PS_PCT,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,     StatsView.KEY_OT_PS_PCT,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TIME_PER_GAME, StatsView.KEY_TIME_PER_GAME, false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getPlayerStatsBySeasonSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.PLAYERBYSEASON );

					if ( param.getHeadingKey().equals( StatsView.KEY_TIME_PER_GAME ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setPlayer_id(  dbRs.getInt(    2 ) );
					statsView.setFirst_name( dbRs.getString( 3 ) );
					statsView.setLast_name(  dbRs.getString( 4 ) );
					statsView.setYear(       dbRs.getString( 5 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey() );
						header.setHeadingKey2( StatsView.KEY_PLAYER  );
						header.setHeadingKey3( StatsView.KEY_YEAR    );
						header.setHeadingKey4( StatsView.KEY_UNUSED  );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopPlayersByCareer() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,         StatsView.KEY_SCORE,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,      StatsView.KEY_ATTEMPTS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,         StatsView.KEY_GOALS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ASSISTS,       StatsView.KEY_ASSISTS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OFFENSE,       StatsView.KEY_OFFENSE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,     StatsView.KEY_TURNOVERS,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STOPS,         StatsView.KEY_STOPS,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,        StatsView.KEY_STEALS,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,     StatsView.KEY_PENALTIES,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,           StatsView.KEY_PSA,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,           StatsView.KEY_PSM,           false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,        StatsView.KEY_OT_PSA,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,        StatsView.KEY_OT_PSM,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,     StatsView.KEY_SCORE_PCT,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,        StatsView.KEY_PS_PCT,        false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,     StatsView.KEY_OT_PS_PCT,     false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TIME_PER_GAME, StatsView.KEY_TIME_PER_GAME, false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getPlayerStatsByCareerSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.PLAYERBYCAREER );

					if ( param.getHeadingKey().equals( StatsView.KEY_TIME_PER_GAME ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setPlayer_id(      dbRs.getInt(    2 ) );
					statsView.setFirst_name(     dbRs.getString( 3 ) );
					statsView.setLast_name(      dbRs.getString( 4 ) );
					statsView.setFirst_year(     dbRs.getString( 5 ) );
					statsView.setLast_year(      dbRs.getString( 6 ) );
					statsView.setSeasons_played( dbRs.getInt(    7 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey() );
						header.setHeadingKey2( StatsView.KEY_PLAYER  );
						header.setHeadingKey3( StatsView.KEY_YEAR    );
						header.setHeadingKey4( StatsView.KEY_SEASONS );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopTeamsByGame() throws SQLException {
		
		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,       StatsView.KEY_SCORE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,    StatsView.KEY_ATTEMPTS,    false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,       StatsView.KEY_GOALS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,   StatsView.KEY_TURNOVERS,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,      StatsView.KEY_STEALS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,   StatsView.KEY_PENALTIES,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,         StatsView.KEY_PSA,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,         StatsView.KEY_PSM,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,      StatsView.KEY_OT_PSA,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,      StatsView.KEY_OT_PSM,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,   StatsView.KEY_SCORE_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,      StatsView.KEY_PS_PCT,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,   StatsView.KEY_OT_PS_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_POSSESSIONS, StatsView.KEY_POSSESSIONS, false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_AVG_TOP,     StatsView.KEY_AVG_TOP,     false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getTeamStatsByGameSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.TEAMBYGAME );

					if ( param.getHeadingKey().equals( StatsView.KEY_AVG_TOP ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setTeam_id(         dbRs.getInt(     2 ) );
					statsView.setTeam_abbrev(     dbRs.getString(  3 ) );
					statsView.setYear(            dbRs.getString(  4 ) );
					statsView.setGame_id(         dbRs.getInt(     5 ) );
					statsView.setRoad(            dbRs.getBoolean( 6 ) );
					statsView.setOpponent_id(     dbRs.getInt(     7 ) );
					statsView.setOpponent_abbrev( dbRs.getString(  8 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey()  );
						header.setHeadingKey2( StatsView.KEY_TEAM     );
						header.setHeadingKey3( StatsView.KEY_YEAR     );
						header.setHeadingKey4( StatsView.KEY_OPPONENT );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopTeamsOffenseBySeason() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,       StatsView.KEY_SCORE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,    StatsView.KEY_ATTEMPTS,    false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,       StatsView.KEY_GOALS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,   StatsView.KEY_TURNOVERS,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,      StatsView.KEY_STEALS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,   StatsView.KEY_PENALTIES,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,         StatsView.KEY_PSA,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,         StatsView.KEY_PSM,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,      StatsView.KEY_OT_PSA,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,      StatsView.KEY_OT_PSM,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,   StatsView.KEY_SCORE_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,      StatsView.KEY_PS_PCT,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,   StatsView.KEY_OT_PS_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_POSSESSIONS, StatsView.KEY_POSSESSIONS, false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_AVG_TOP,     StatsView.KEY_AVG_TOP,     false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getTeamOffStatsBySeasonSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.TEAMBYSEASON );

					if ( param.getHeadingKey().equals( StatsView.KEY_AVG_TOP ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setTeam_id(         dbRs.getInt(     2 ) );
					statsView.setTeam_abbrev(     dbRs.getString(  3 ) );
					statsView.setYear(            dbRs.getString(  4 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey()  );
						header.setHeadingKey2( StatsView.KEY_TEAM     );
						header.setHeadingKey3( StatsView.KEY_YEAR     );
						header.setHeadingKey4( StatsView.KEY_UNUSED   );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopTeamsDefenseBySeason() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,       StatsView.KEY_SCORE,       true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,    StatsView.KEY_ATTEMPTS,    true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,       StatsView.KEY_GOALS,       true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STOPS,       StatsView.KEY_STOPS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,   StatsView.KEY_TURNOVERS,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,   StatsView.KEY_PENALTIES,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,         StatsView.KEY_PSA,         true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,         StatsView.KEY_PSM,         true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,   StatsView.KEY_SCORE_PCT,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,      StatsView.KEY_PS_PCT,      true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,   StatsView.KEY_OT_PS_PCT,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_POSSESSIONS, StatsView.KEY_POSSESSIONS, true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_AVG_TOP,     StatsView.KEY_AVG_TOP,     true  ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getTeamDefStatsBySeasonSelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.TEAMBYSEASON );

					if ( param.getHeadingKey().equals( StatsView.KEY_AVG_TOP ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setTeam_id(         dbRs.getInt(     2 ) );
					statsView.setTeam_abbrev(     dbRs.getString(  3 ) );
					statsView.setYear(            dbRs.getString(  4 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey()  );
						header.setHeadingKey2( StatsView.KEY_TEAM     );
						header.setHeadingKey3( StatsView.KEY_YEAR     );
						header.setHeadingKey4( StatsView.KEY_UNUSED   );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopTeamsOffenseByHistory() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,       StatsView.KEY_SCORE,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,    StatsView.KEY_ATTEMPTS,    false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,       StatsView.KEY_GOALS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,   StatsView.KEY_TURNOVERS,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STEALS,      StatsView.KEY_STEALS,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,   StatsView.KEY_PENALTIES,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,         StatsView.KEY_PSA,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,         StatsView.KEY_PSM,         false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSA,      StatsView.KEY_OT_PSA,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PSM,      StatsView.KEY_OT_PSM,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,   StatsView.KEY_SCORE_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,      StatsView.KEY_PS_PCT,      false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,   StatsView.KEY_OT_PS_PCT,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_POSSESSIONS, StatsView.KEY_POSSESSIONS, false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_AVG_TOP,     StatsView.KEY_AVG_TOP,     false ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getTeamOffStatsByHistorySelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.TEAMBYHISTORY );

					if ( param.getHeadingKey().equals( StatsView.KEY_AVG_TOP ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setTeam_id(         dbRs.getInt(     2 ) );
					statsView.setTeam_abbrev(     dbRs.getString(  3 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey()  );
						header.setHeadingKey2( StatsView.KEY_TEAM     );
						header.setHeadingKey3( StatsView.KEY_UNUSED   );
						header.setHeadingKey4( StatsView.KEY_UNUSED   );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

	public Collection getTopTeamsDefenseByHistory() throws SQLException {

		ArrayList categories = new ArrayList();
		
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE,       StatsView.KEY_SCORE,       true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_ATTEMPTS,    StatsView.KEY_ATTEMPTS,    true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_GOALS,       StatsView.KEY_GOALS,       true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_STOPS,       StatsView.KEY_STOPS,       false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_TURNOVERS,   StatsView.KEY_TURNOVERS,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PENALTIES,   StatsView.KEY_PENALTIES,   false ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSA,         StatsView.KEY_PSA,         true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PSM,         StatsView.KEY_PSM,         true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_SCORE_PCT,   StatsView.KEY_SCORE_PCT,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_PS_PCT,      StatsView.KEY_PS_PCT,      true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_OT_PS_PCT,   StatsView.KEY_OT_PS_PCT,   true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_POSSESSIONS, StatsView.KEY_POSSESSIONS, true  ) );
		categories.add( new Parameters( DatabaseImpl.STAT_AVG_TOP,     StatsView.KEY_AVG_TOP,     true  ) );
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		
		Collection lists = null;
		
		Iterator i = categories.iterator();
		
		while ( i.hasNext() ) {
		
			Parameters param = (Parameters)i.next();
			
			Collection list = null;
			
			try {
				
				ps = DatabaseImpl.getTeamDefStatsByHistorySelectPs( dbConn, param.getStat(), param.isAscending() );
				
				ps.setInt( 1, TeamGame.gt_RegularSeason );
				ps.setInt( 2, 10                        );
				
				dbRs = ps.executeQuery();
				
				while ( dbRs.next() ) {
				
					StatsView statsView = new StatsView( StatsView.TEAMBYSEASON );

					if ( param.getHeadingKey().equals( StatsView.KEY_AVG_TOP ) ) {
						
						int t = dbRs.getInt( 1 );
						
						DecimalFormat df = new DecimalFormat( "00" );
						
						statsView.setStat( df.format( t / 60 ) + ":" + df.format( t % 60 ) );
					}
					else {
					
						statsView.setStat(       dbRs.getString( 1 ) );
					}
					
					statsView.setTeam_id(         dbRs.getInt(     2 ) );
					statsView.setTeam_abbrev(     dbRs.getString(  3 ) );
					
					if ( list == null ) {
						
						list = new ArrayList();
						
						StatsView header = new StatsView( StatsView.HEADER );
						
						header.setHeadingKey1( param.getHeadingKey()  );
						header.setHeadingKey2( StatsView.KEY_TEAM     );
						header.setHeadingKey3( StatsView.KEY_UNUSED     );
						header.setHeadingKey4( StatsView.KEY_UNUSED   );
						
						list.add( header );
					}
					
					list.add( statsView );
				}
			}
			catch ( SQLException sqle ) {
			
				throw sqle;
			}
			finally {
				
				DatabaseImpl.closeDbRs( dbRs );
				DatabaseImpl.closeDbStmt( ps );
			}
			
			if ( lists == null ) lists = new ArrayList();
			
			lists.add( list );
		}
		
		return lists;
	}

}
