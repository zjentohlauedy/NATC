package natc.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import natc.data.Match;
import natc.data.TeamGame;
import natc.service.GameService;
import natc.service.impl.GameServiceImpl;

public class GameDriver {

	private static char[] rnd_seed = { 0xF8, 0xF4, 0xE1, 0xEc, 0xB2, 0xB3 }; 
	
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
			
			gameService = new GameServiceImpl( dbConn, "2001" );
			
			Match match = new Match();
			
			match.setHome_team_id( 1 );
			match.setRoad_team_id( 2 );
			Calendar cal = Calendar.getInstance();

			cal.setTime( new Date() );

			int year  = cal.get( Calendar.YEAR         );
			int month = cal.get( Calendar.MONTH        );
			int day   = cal.get( Calendar.DAY_OF_MONTH );
			int hour  = cal.get( Calendar.HOUR_OF_DAY  );
			int min   = cal.get( Calendar.MINUTE       );

			cal.setTimeZone( TimeZone.getTimeZone( "America/Indianapolis" ) );

			cal.set( year, month, day, 18, 5 );

			long timeDifference = cal.getTime().getTime() - (new Date()).getTime();
			
			if ( timeDifference > 0 ) {

				System.out.println( dateFormat.format( new Date() ) + " Sleeping for " + String.valueOf( ((float)timeDifference / 1000.0) ) + " seconds." );
				
				Thread.sleep( timeDifference );
			}
			
			System.out.println( dateFormat.format( new Date() ) + " Begin processing game." );
			
			gameService.processRealtimeMatch( match, new Date(), TeamGame.gt_RegularSeason );
			
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
}
