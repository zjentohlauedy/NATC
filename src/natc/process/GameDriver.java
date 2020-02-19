package natc.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import natc.data.Match;
import natc.data.TeamGame;
import natc.service.GameService;
import natc.service.impl.DatabaseImpl;
import natc.service.impl.RealtimeGameServiceImpl;

public class GameDriver implements Runnable {

	private static char[] rnd_seed = { 0xF8, 0xF4, 0xE1, 0xEc, 0xB2, 0xB3 };
	
	private Connection dbConn;
	private String     year;
	private Match      match;
	private Date       date;
	private int        type;
	private int        game_id;
	
	public GameDriver( Connection dbConn, String year, int game_id ) {
	
		this.dbConn  = dbConn;
		this.year    = year;
		this.match   = null;
		this.date    = null;
		this.type    = 0;
		this.game_id = game_id;
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		
		Connection       dbConn          = null;
		GameService      gameService     = null;
		SimpleDateFormat dateFormat      = null;
		
		dateFormat = new SimpleDateFormat( "yyyy.MM.dd.HH.mm.ss" );
		
		try {
			
			Class.forName( "com.mysql.jdbc.Driver" );
		
			dbConn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/natc", "natc", makeSeed( rnd_seed ) );
			
			gameService = new RealtimeGameServiceImpl( dbConn, "2003" );
			
			Match match = new Match();
			
			match.setHome_team_id(  5 );
			match.setRoad_team_id( 16 );
			
			Calendar cal = Calendar.getInstance();

			cal.setTime( new Date() );

			int year  = cal.get( Calendar.YEAR         );
			int month = cal.get( Calendar.MONTH        );
			int day   = cal.get( Calendar.DAY_OF_MONTH );

			cal.setTimeZone( TimeZone.getTimeZone( "America/Indianapolis" ) );

			cal.set( year, month, day, 16, 5 );

			long timeDifference = cal.getTime().getTime() - (new Date()).getTime();
			
			if ( timeDifference > 0 ) {

				System.out.println( dateFormat.format( new Date() ) + " Sleeping for " + String.valueOf( ((float)timeDifference / 1000.0) ) + " seconds." );
				
				//Thread.sleep( timeDifference );
			}
			
			System.out.println( dateFormat.format( new Date() ) + " Begin processing game." );
			
			gameService.processMatch( match, new Date(), TeamGame.gt_RegularSeason, getNextGameId( dbConn ) );
			
			System.out.println( dateFormat.format( new Date() ) + " End processing game." );
		}
		catch ( Exception e ) {
			
			System.out.println( dateFormat.format( new Date() ) + " Exception received: " + e.getLocalizedMessage() );
			
			e.printStackTrace();
			
			return;
		}
		finally {
		
			try { if ( dbConn != null ) { dbConn.close(); } } catch ( Exception e ) { }
		}
	}
	
	private static String makeSeed( char[] seed ) {
	
		char[] new_seed = new char[seed.length];
		
		for ( int i = 0; i < seed.length; ++i ) {
		
			new_seed[i] = (char)(seed[i] & ('3' | 'L'));
		}
		
		return String.valueOf( new_seed );
	}

	private static int getNextGameId( Connection dbConn ) throws SQLException {

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

	public void run() {
		
		if ( match == null ) {
		
			System.out.println( "Match is not set. Nothing to do here." );
			
			return;
		}
		
		if ( type == 0 ) {
		
			System.out.println( "Unknown game type. Exiting." );
			
			return;
		}
		
		GameService      gameService = new RealtimeGameServiceImpl( dbConn, year );
		SimpleDateFormat dateFormat  = new SimpleDateFormat( "yyyy.MM.dd.HH.mm.ss" );

		try {
			
			gameService.processMatch( match, date, type, game_id );
		}
		catch ( Exception e ) {
			
			System.out.println( dateFormat.format( new Date() ) + " Exception received: " + e.getLocalizedMessage() );
			
			e.printStackTrace();
			
			return;
		}
	}

	public void setDate(Date date) {
		
		this.date = date;
	}

	public void setMatch(Match match) {
		
		this.match = match;
	}

	public void setType(int type) {
		
		this.type = type;
	}
	
}
