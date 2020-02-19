package natc.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.service.GameService;
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.impl.GameServiceImpl;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.RealtimeGameServiceImpl;
import natc.service.impl.ScheduleServiceImpl;

public class SeasonDriver {

	private static char[] rnd_seed = { 0xF8, 0xF4, 0xE1, 0xEc, 0xB2, 0xB3 }; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection       dbConn          = null;
		ScheduleService  scheduleService = null;
		GameService      gameService     = null;
		Schedule         scheduleEntry   = null;
		SimpleDateFormat dateFormat      = null;
		
		dateFormat = new SimpleDateFormat( "yyyy.MM.dd.HH.mm.ss" );
		
		try {
			
			Class.forName( "com.mysql.jdbc.Driver" );
		
			dbConn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/natc", "natc", makeSeed( rnd_seed ) );
			
			scheduleService = new ScheduleServiceImpl( dbConn, null );

			// Make sure there are no schedule entries in progress
			if ( (scheduleEntry = scheduleService.getCurrentScheduleEntry()) != null ) {
			
				System.out.println( "Schedule entry (" + scheduleEntry.getYear() + ":" + String.valueOf( scheduleEntry.getSequence() ) + ") is in progress. Exiting." );
				
				return;
			}
			
			// Find out what the program did last
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {

				System.out.println( dateFormat.format( new Date() ) + " No schedule information found, initializing database." );
				
				// Get the current year as the base year for the first season
				String year = Schedule.FIRST_YEAR;
				
				// Get a new game service with the above year
				gameService = new GameServiceImpl( dbConn, year );
				
				// No schedule entries exist - initialize natc data
				gameService.initializeDatabase();
			}
			else if ( scheduleEntry.getType().getValue() == ScheduleType.END_OF_SEASON ) {
			
				// If the last entry is end of season, generate a new schedule
				System.out.println( dateFormat.format( new Date() ) + " Previous season has ended, generating new season." );
				
				// Get a new game service with the next year
				gameService = new GameServiceImpl( dbConn, String.valueOf( Integer.parseInt( scheduleEntry.getYear() ) + 1 ) );
				
				// Start a new season
				gameService.startNewSeason( scheduleEntry.getYear() );
			}
			
			// Get the next scheduled event
			scheduleEntry = scheduleService.getNextScheduleEntry( scheduleEntry );

			/*
			if (    scheduleEntry.getType().getValue() == ScheduleType.NATC_CHAMPIONSHIP ||
					scheduleEntry.getType().getValue() == ScheduleType.ALL_STAR_DAY_1    ||
					scheduleEntry.getType().getValue() == ScheduleType.ALL_STAR_DAY_2       ) {
				
				gameService = new RealtimeGameServiceImpl( dbConn, scheduleEntry.getYear() );
			}
			else {

				gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
			}
			*/
			gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
			
			System.out.println( dateFormat.format( new Date() ) + " Processing Schedule Entry..." );

			scheduleEntry.setStatus( Schedule.st_InProgress );
			
			scheduleService.updateScheduleEntry( scheduleEntry );
			
			switch ( scheduleEntry.getType().getValue() ) {
			
			case ScheduleType.BEGINNING_OF_SEASON:     System.out.println( dateFormat.format( new Date() ) + " Official Start of " + scheduleEntry.getYear() + " season." ); break;
			case ScheduleType.MANAGER_CHANGES:         System.out.println( dateFormat.format( new Date() ) + " Manager Changes." ); break;
			case ScheduleType.PLAYER_CHANGES:          System.out.println( dateFormat.format( new Date() ) + " Player Changes." ); break;
			case ScheduleType.ROOKIE_DRAFT_ROUND_1:    System.out.println( dateFormat.format( new Date() ) + " Rookie Draft Round 1" ); break;
			case ScheduleType.ROOKIE_DRAFT_ROUND_2:    System.out.println( dateFormat.format( new Date() ) + " Rookie Draft Round 2" ); break;
			case ScheduleType.TRAINING_CAMP:           System.out.println( dateFormat.format( new Date() ) + " Training Camp." ); break;
			case ScheduleType.PRESEASON:               System.out.println( dateFormat.format( new Date() ) + " Pre-season Games." ); break;
			case ScheduleType.END_OF_PRESEASON:        System.out.println( dateFormat.format( new Date() ) + " Official End of Pre-season." ); break;
			case ScheduleType.ROSTER_CUT:              System.out.println( dateFormat.format( new Date() ) + " Roster Cuts." ); break;
			case ScheduleType.REGULAR_SEASON:          System.out.println( dateFormat.format( new Date() ) + " Regular Season Games." ); break;
			case ScheduleType.END_OF_REGULAR_SEASON:   System.out.println( dateFormat.format( new Date() ) + " Official End of Regular Season." ); break;
			case ScheduleType.AWARDS:                  System.out.println( dateFormat.format( new Date() ) + " Recognition Awards." ); break;
			case ScheduleType.POSTSEASON:              System.out.println( dateFormat.format( new Date() ) + " Official Start of Postseason." ); break;
			case ScheduleType.DIVISION_PLAYOFF:        System.out.println( dateFormat.format( new Date() ) + " Division Playoff Games." ); break;
			case ScheduleType.DIVISION_CHAMPIONSHIP:   System.out.println( dateFormat.format( new Date() ) + " Division Championship Games." ); break;
			case ScheduleType.CONFERENCE_CHAMPIONSHIP: System.out.println( dateFormat.format( new Date() ) + " Conference Championship Games." ); break;
			case ScheduleType.NATC_CHAMPIONSHIP:       System.out.println( dateFormat.format( new Date() ) + " NATC Championship." ); break;
			case ScheduleType.END_OF_POSTSEASON:       System.out.println( dateFormat.format( new Date() ) + " Official End of Postseason." ); break;
			case ScheduleType.ALL_STARS:               System.out.println( dateFormat.format( new Date() ) + " All Star Selection." ); break;
			case ScheduleType.ALL_STAR_DAY_1:          System.out.println( dateFormat.format( new Date() ) + " All Star Games Day 1." ); break;
			case ScheduleType.ALL_STAR_DAY_2:          System.out.println( dateFormat.format( new Date() ) + " All Star Games Day 2." ); break;
			case ScheduleType.END_OF_ALLSTAR_GAMES:    System.out.println( dateFormat.format( new Date() ) + " Official End of All Star Games." ); break;
			case ScheduleType.END_OF_SEASON:           System.out.println( dateFormat.format( new Date() ) + " Official End of " + scheduleEntry.getYear() + " Season." ); break;
			}
			
			gameService.processScheduleEvent( scheduleEntry );
			
			System.out.println( dateFormat.format( new Date() ) + " Processing Complete." );
		}
		catch ( Exception e ) {
		
			System.out.println( dateFormat.format( new Date() ) + " Exception received: " + e.getLocalizedMessage() );
			
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
