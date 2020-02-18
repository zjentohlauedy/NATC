package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import natc.data.Constants;
import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Team;
import natc.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService {

	private Connection dbConn = null;
	private String     year   = null;
	private boolean    flip   = false;
	private boolean    invert = false;
	
	public ScheduleServiceImpl( Connection dbConn, String year ) {
		
		this.dbConn = dbConn;
		this.year   = year;
	}

	private class ScheduleData {
	
		public int   games;
		public int[] home_teams;
		public int[] road_teams;
		
		public ScheduleData() {
		
			this.games      = 0;
			this.home_teams = new int[20];
			this.road_teams = new int[20];
		}

		public String toString() {
			
			char[] array = new char[41];
			int    idx   = 0;
			
			array[idx]  = (char)this.games;
			array[idx] += '0';
			
			idx++;
			
			for ( int i = 0; i < this.games; ++i ) {
			
				array[idx]  = (char)this.road_teams[i];
				array[idx] += '0';
				
				idx++;
				
				array[idx]  = (char)this.home_teams[i];
				array[idx] += '0';
				
				idx++;
			}
			
			return new String( array );
		}
		
	}
	
	public Schedule getLastScheduleEntry() throws SQLException {
		
		Schedule          schedule = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			ps = DatabaseImpl.getLastScheduleEntrySelectPs( dbConn );
			
			ps.setInt( 1, Schedule.st_Completed );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				schedule = new Schedule();
				
				schedule.setYear(                        dbRs.getString(  1 )   );
				schedule.setSequence(                    dbRs.getInt(     2 )   );
				schedule.setType(      new ScheduleType( dbRs.getInt(     3 ) ) );
				schedule.setData(                        dbRs.getString(  4 )   );
				schedule.setScheduled(                   dbRs.getDate(    5 )   );
				schedule.setStatus(                      dbRs.getInt(     6 )   );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return schedule;
	}

	public Schedule getCurrentScheduleEntry() throws SQLException {
		
		Schedule          schedule = null;
		PreparedStatement ps       = null;
		ResultSet         dbRs     = null;
		
		try {
			ps = DatabaseImpl.getLastScheduleEntrySelectPs( dbConn );
			
			ps.setInt( 1, Schedule.st_InProgress );
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				schedule = new Schedule();
				
				schedule.setYear(                        dbRs.getString(  1 )   );
				schedule.setSequence(                    dbRs.getInt(     2 )   );
				schedule.setType(      new ScheduleType( dbRs.getInt(     3 ) ) );
				schedule.setData(                        dbRs.getString(  4 )   );
				schedule.setScheduled(                   dbRs.getDate(    5 )   );
				schedule.setStatus(                      dbRs.getInt(     6 )   );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return schedule;
	}
	
	public Schedule getNextScheduleEntry( Schedule lastEntry ) throws SQLException {
		
		Schedule          nextEntry = null;
		PreparedStatement ps        = null;
		ResultSet         dbRs      = null;
		
		try {
			ps = DatabaseImpl.getScheduleEntrySelectPs( dbConn );
			
			if ( lastEntry == null ) {
			
				ps.setString( 1, Schedule.FIRST_YEAR     );
				ps.setInt(    2, Schedule.FIRST_SEQUENCE );
			}
			else if ( lastEntry.getType().getValue() == ScheduleType.END_OF_SEASON ) {
			
				ps.setString( 1, String.valueOf( Integer.parseInt( lastEntry.getYear() ) + 1 ) );
				ps.setInt(    2, Schedule.FIRST_SEQUENCE                                       );
			}
			else {
				ps.setString( 1, lastEntry.getYear()         );
				ps.setInt(    2, lastEntry.getSequence() + 1 );
			}
			
			dbRs = ps.executeQuery();

			if ( dbRs.next() ) {
			
				nextEntry = new Schedule();
				
				nextEntry.setYear(                        dbRs.getString(  1 )   );
				nextEntry.setSequence(                    dbRs.getInt(     2 )   );
				nextEntry.setType(      new ScheduleType( dbRs.getInt(     3 ) ) );
				nextEntry.setData(                        dbRs.getString(  4 )   );
				nextEntry.setScheduled(                   dbRs.getDate(    5 )   );
				nextEntry.setStatus(                      dbRs.getInt(     6 )   );
			}
		}
		finally {
			
			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
		
		return nextEntry;
	}

	private void rotate( int[] array, int offset, int length ) {

		int i;
		int x = array[(offset + length) - 1];

		for ( i = (offset + length) - 1; i > offset; --i )
		{
			array[i] = array[i - 1];
		}

		array[offset] = x;
	}

	private void shuffle( int[] array, int offset, int length ) {

		for ( int i = length; i > 1; --i ) {
		
			int n = offset + (int)Math.floor( Math.random() * (float)(i - 1) );
			int m = offset + i - 1;
			int x;
			
			x        = array[n];
			array[n] = array[m];
			array[m] = x;
		}
	}

	private void scheduleConferenceGames( ScheduleData data, int[] teams ) {
	
		for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; i += Constants.TEAMS_PER_CONFERENCE )
		{
			for ( int j = 0; j < 10; ++j )
			{
				int match = (i / 2) + j;
				int road  = i + j;
				int home  = i + 19 - j;

				// Swap road and home for first team (since it doesn't rotate)
				if ( j == 0 && this.flip ) {
				
					int temp;
					
					temp = road;
					road = home;
					home = temp;
				}
				
				// Every other pass swap home and road teams (so some teams don't play every game at home)
				if ( j != 0 && this.invert ) {
				
					int temp;
					
					temp = road;
					road = home;
					home = temp;
				}
				
				data.road_teams[match] = teams[road];
				data.home_teams[match] = teams[home];
			}
		}

		this.flip = ! this.flip;
	}
	
	private void scheduleDivisionGames( ScheduleData[] data, int day, int[] teams ) {

		for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; i += Constants.TEAMS_PER_DIVISION )
		{
			for ( int j = 0; j < 5; ++j )
			{
				int match = (i / 2) + j;
				int road  = i + j;
				int home  = i + 9 - j;
				
				boolean done = false;

				for ( int previous_day = day - 1; previous_day > 0; --previous_day ) {
				
					for ( int game = 0; game < data[previous_day].games; ++game ) {
					
						if (    data[previous_day].road_teams[game] == teams[home] &&
								data[previous_day].home_teams[game] == teams[road]    ) {
						
							done = true;
							
							break;
						}

						if (    data[previous_day].road_teams[game] == teams[road] &&
								data[previous_day].home_teams[game] == teams[home]    ) {
						
							int temp;

							temp = road;
							road = home;
							home = temp;
							
							done = true;
							
							break;
						}
					}
					
					if ( done ) break;
				}
				
				data[day].road_teams[match] = teams[road];
				data[day].home_teams[match] = teams[home];
			}
		}

		this.flip = ! this.flip;
	}

	private List generateSeasonSchedule() {
	
		List           schedule = new ArrayList();
		ScheduleData[] data     = new ScheduleData[Constants.DAYS_IN_SEASON];
		int[]          teams    = new int[Constants.NUMBER_OF_TEAMS];
		
		
		for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; ++i ) teams[i] = i + 1;
		
		int day = 0;

		// Conference Series
		shuffle( teams,  0, Constants.TEAMS_PER_CONFERENCE );
		shuffle( teams, 20, Constants.TEAMS_PER_CONFERENCE );
		
		this.flip = false;
		
		for ( int series = 0; series < Constants.TEAMS_PER_CONFERENCE - 1; ++series ) {
		
			this.invert = false;
			
			for ( int round = 0; round < 3; ++round ) {
			
				data[day] = new ScheduleData();
				
				data[day].games = Constants.GAMES_PER_DAY;
				
				scheduleConferenceGames( data[day], teams );
				
				day++;
				
				this.invert = ! this.invert;
			}
			
			rotate( teams,  1, Constants.TEAMS_PER_CONFERENCE - 1 );
			rotate( teams, 21, Constants.TEAMS_PER_CONFERENCE - 1 );
		}

		
		// After conference schedule team list has the form with road/home bias:
		//  Div: 1111111111 2222222222 3333333333 4444444444
		// Conf: 1111111111 1111111111 2222222222 2222222222
		// Bias: rhrhrhrhrh rhrhrhrhrh rhrhrhrhrh rhrhrhrhrh
		
		// Out of Conference Series
		for ( int round = 0; round < Constants.OUT_OF_CONFERENCE_GAMES; ++round ) {
			
			data[day] = new ScheduleData();
			
			data[day].games = Constants.GAMES_PER_DAY;
			
			for ( int match = 0; match < Constants.GAMES_PER_DAY; ++match ) {
			
				if ( (round % 2) != 0 ) {
					
					data[day].road_teams[match] = teams[match];
					data[day].home_teams[match] = teams[39 - match];
				}
				else {
				
					if ( round == 0  &&  (match % 2) != 0 ) {
						
						data[day].road_teams[match] = teams[match];
						data[day].home_teams[match] = teams[39 - match];
					}
					else {
						
						data[day].road_teams[match] = teams[39 - match];
						data[day].home_teams[match] = teams[match];
					}
				}
			}
			
			day ++;
			
			rotate( teams,  20, Constants.TEAMS_PER_CONFERENCE );
		}
		
		// Division Series
		for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; ++i ) teams[i] = i + 1;
		
		shuffle( teams,  0, Constants.TEAMS_PER_DIVISION );
		shuffle( teams, 10, Constants.TEAMS_PER_DIVISION );
		shuffle( teams, 20, Constants.TEAMS_PER_DIVISION );
		shuffle( teams, 30, Constants.TEAMS_PER_DIVISION );
		
		this.flip = false;
		
		for ( int series = 0; series < Constants.TEAMS_PER_DIVISION - 1; ++series ) {
		
			for ( int round = 0; round < 4; ++round ) {
			
				data[day] = new ScheduleData();
				
				data[day].games = Constants.GAMES_PER_DAY;
				
				scheduleDivisionGames( data, day, teams );
				
				day++;
			}
			
			rotate( teams,  1, Constants.TEAMS_PER_DIVISION - 1 );
			rotate( teams, 11, Constants.TEAMS_PER_DIVISION - 1 );
			rotate( teams, 21, Constants.TEAMS_PER_DIVISION - 1 );
			rotate( teams, 31, Constants.TEAMS_PER_DIVISION - 1 );
		}
		
		
		for ( int i = 0; i < Constants.DAYS_IN_SEASON; ++i ) {
			
			String s = data[i].toString();
			
			schedule.add( s );
		}
		
		Collections.shuffle( schedule );
		
		return schedule;
	}
	
	private List generatePreseasonSchedule() {
	
		List           schedule = new ArrayList();
		ScheduleData[] data     = new ScheduleData[Constants.DAYS_IN_PRESEASON];
		int[]          teams    = new int[Constants.NUMBER_OF_TEAMS];
		
		
		for ( int i = 0; i < Constants.NUMBER_OF_TEAMS; ++i ) teams[i] = i + 1;
		
		shuffle( teams, 0, Constants.NUMBER_OF_TEAMS );
		
		for ( int day = 0; day < Constants.DAYS_IN_PRESEASON; ++day ) {
			
			data[day] = new ScheduleData();
			
			data[day].games = Constants.GAMES_PER_DAY;
			
			for ( int match = 0; match < Constants.GAMES_PER_DAY; ++match ) {
			
				if ( (day % 2) == 0 ) {
				
					data[day].road_teams[match] = teams[match + Constants.TEAMS_PER_CONFERENCE];
					data[day].home_teams[match] = teams[match];
				}
				else {
				
					data[day].road_teams[match] = teams[match];
					data[day].home_teams[match] = teams[match + Constants.TEAMS_PER_CONFERENCE];
				}
			}
			
			rotate( teams, Constants.TEAMS_PER_CONFERENCE, Constants.TEAMS_PER_CONFERENCE );
		}
		
		for ( int i = 0; i < Constants.DAYS_IN_PRESEASON; ++i ) {
		
			String s = data[i].toString();
			
			schedule.add( s );
		}
		
		Collections.shuffle( schedule );
		
		return schedule;
	}
	
	private long advanceTime( long time, int days ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis( time );
		
		cal.add( Calendar.DAY_OF_YEAR, days );
		
		return cal.getTime().getTime();
	}

	private long getFirstMondayInNovember( int year ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, Calendar.NOVEMBER, 1 );
		
		while ( cal.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY ) {
		
			cal.add( Calendar.DAY_OF_YEAR, 1);
		}
		
		return cal.getTime().getTime();
	}
	
	private long getFirstMondayInMay( int year ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, Calendar.MAY, 1 );
		
		while ( cal.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY ) {
		
			cal.add( Calendar.DAY_OF_YEAR, 1);
		}
		
		return cal.getTime().getTime();
	}
	
	private long getFirstMondayInApril( int year ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, Calendar.APRIL, 1 );
		
		while ( cal.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY ) {
		
			cal.add( Calendar.DAY_OF_YEAR, 1);
		}
		
		return cal.getTime().getTime();
	}
	
	private long getFirstMondayInMarch( int year ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, Calendar.MARCH, 1 );
		
		while ( cal.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY ) {
		
			cal.add( Calendar.DAY_OF_YEAR, 1);
		}
		
		return cal.getTime().getTime();
	}

	private long getFirstMondayInFebruary( int year ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, Calendar.FEBRUARY, 1 );
		
		while ( cal.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY ) {
		
			cal.add( Calendar.DAY_OF_YEAR, 1);
		}
		
		return cal.getTime().getTime();
	}
	
	private int getDayOfWeek( long time ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis( time );
		
		return cal.get( Calendar.DAY_OF_WEEK );
	}
	
	private long getTime( int year, int month, int day ) {
	
		Calendar cal = Calendar.getInstance();
		
		cal.set( year, month, day );
		
		return cal.getTime().getTime();
	}
	
	public void generateSchedule() throws SQLException {
	
		if ( year == null ) {
		
			// Generate year string from current date
			Calendar now = Calendar.getInstance();
			
			year = String.valueOf( now.get( Calendar.YEAR ) );
		}
		
		PreparedStatement ps = null;
		Iterator          i  = null;
		
		try {
			
			DatabaseImpl.beginTransaction( dbConn );
			
			int  sequence = Schedule.FIRST_SEQUENCE;
			long time     = 0;
			
			ps = DatabaseImpl.getScheduleInsertPs( dbConn );
			
			// Beginning of season
			time = getTime( Integer.valueOf( year ).intValue(), Calendar.JANUARY, 1 );
			
			ps.setString(  1, year                             );
			ps.setInt(     2, sequence++                       );
			ps.setInt(     3, ScheduleType.BEGINNING_OF_SEASON );
			ps.setNull(    4, Types.VARCHAR                    );
			ps.setDate(    5, new java.sql.Date( time )        );
			ps.setInt(     6, Schedule.st_Scheduled            );
			
			ps.executeUpdate();
			
			// Manager Changes
			time = getFirstMondayInFebruary( Integer.valueOf( year ).intValue() );
			
			ps.setString(  1, year                             );
			ps.setInt(     2, sequence++                       );
			ps.setInt(     3, ScheduleType.MANAGER_CHANGES     );
			ps.setNull(    4, Types.VARCHAR                    );
			ps.setDate(    5, new java.sql.Date( time )        );
			ps.setInt(     6, Schedule.st_Scheduled            );

			ps.executeUpdate();

			// Player Changes (Free Agency and Retirement)
			time = getTime( Integer.valueOf( year ).intValue(), Calendar.FEBRUARY, 28 );
			
			ps.setString(  1, year                             );
			ps.setInt(     2, sequence++                       );
			ps.setInt(     3, ScheduleType.PLAYER_CHANGES      );
			ps.setNull(    4, Types.VARCHAR                    );
			ps.setDate(    5, new java.sql.Date( time )        );
			ps.setInt(     6, Schedule.st_Scheduled            );

			ps.executeUpdate();
			
			// Rookie draft - round 1
			time = getFirstMondayInMarch( Integer.valueOf( year ).intValue() );
			
			ps.setString(  1, year                              );
			ps.setInt(     2, sequence++                        );
			ps.setInt(     3, ScheduleType.ROOKIE_DRAFT_ROUND_1 );
			ps.setNull(    4, Types.VARCHAR                     );
			ps.setDate(    5, new java.sql.Date( time )         );
			ps.setInt(     6, Schedule.st_Scheduled             );
			
			ps.executeUpdate();
			
			// Rookie draft - round 2
			time = advanceTime( time, 1 ); // next day after round 1
			
			ps.setString(  1, year                              );
			ps.setInt(     2, sequence++                        );
			ps.setInt(     3, ScheduleType.ROOKIE_DRAFT_ROUND_2 );
			ps.setNull(    4, Types.VARCHAR                     );
			ps.setDate(    5, new java.sql.Date( time )         );
			ps.setInt(     6, Schedule.st_Scheduled             );
			
			ps.executeUpdate();
			
			// Training camp
			time = advanceTime( time, 13 ); // monday, two weeks after draft
			
			ps.setString(  1, year                       );
			ps.setInt(     2, sequence++                 );
			ps.setInt(     3, ScheduleType.TRAINING_CAMP );
			ps.setNull(    4, Types.VARCHAR              );
			ps.setDate(    5, new java.sql.Date( time )  );
			ps.setInt(     6, Schedule.st_Scheduled      );
			
			ps.executeUpdate();
			
			// Preseason
			time = getFirstMondayInApril( Integer.valueOf( year ).intValue() );
			
			List preseason = generatePreseasonSchedule();
			
			i = preseason.iterator();
			
			while ( i.hasNext() ) {
			
				String data = (String)i.next();
				
				ps.setString(  1, year                      );
				ps.setInt(     2, sequence++                );
				ps.setInt(     3, ScheduleType.PRESEASON    );
				ps.setString(  4, data                      );
				ps.setDate(    5, new java.sql.Date( time ) );
				ps.setInt(     6, Schedule.st_Scheduled     );
				
				ps.executeUpdate();
				
				// Continue to next day, skipping weekends
				time = advanceTime( time, 1 );
				
				if ( getDayOfWeek( time ) == Calendar.SATURDAY ) time = advanceTime( time, 2 );
			}
			
			// End of Preseason
			time = advanceTime( time, -2 ); // Roll back the last advance
			
			ps.setString(  1, year                          );
			ps.setInt(     2, sequence++                    );
			ps.setInt(     3, ScheduleType.END_OF_PRESEASON );
			ps.setNull(    4, Types.VARCHAR                 );
			ps.setDate(    5, new java.sql.Date( time )     );
			ps.setInt(     6, Schedule.st_Scheduled         );
			
			ps.executeUpdate();
			
			// Roster Cut
			time = getTime( Integer.valueOf( year ).intValue(), Calendar.APRIL, 30 );
			
			ps.setString(  1, year                      );
			ps.setInt(     2, sequence++                );
			ps.setInt(     3, ScheduleType.ROSTER_CUT   );
			ps.setNull(    4, Types.VARCHAR             );
			ps.setDate(    5, new java.sql.Date( time ) );
			ps.setInt(     6, Schedule.st_Scheduled     );
			
			ps.executeUpdate();
			
			// Regular Season
			time = getFirstMondayInMay( Integer.valueOf( year ).intValue() );
			
			List season = generateSeasonSchedule();
			
			i = season.iterator();
			
			while ( i.hasNext() ) {
				
				String data = (String)i.next();
				
				ps.setString(  1, year                        );
				ps.setInt(     2, sequence++                  );
				ps.setInt(     3, ScheduleType.REGULAR_SEASON );
				ps.setString(  4, data                        );
				ps.setDate(    5, new java.sql.Date( time )   );
				ps.setInt(     6, Schedule.st_Scheduled       );
				
				ps.executeUpdate();
				
				// Continue to next day, skipping weekends
				time = advanceTime( time, 1 );
				
				if ( getDayOfWeek( time ) == Calendar.SATURDAY ) time = advanceTime( time, 2 );
			}
			
			// End of Regular Season
			time = advanceTime( time, -2 ); // Roll back last advance
			
			ps.setString(  1, year                               );
			ps.setInt(     2, sequence++                         );
			ps.setInt(     3, ScheduleType.END_OF_REGULAR_SEASON );
			ps.setNull(    4, Types.VARCHAR                      );
			ps.setDate(    5, new java.sql.Date( time )          );
			ps.setInt(     6, Schedule.st_Scheduled              );
			
			ps.executeUpdate();
			
			
			// Awards
			time = advanceTime( time, 2 ); // Monday following end of season
			
			ps.setString(  1, year                      );
			ps.setInt(     2, sequence++                );
			ps.setInt(     3, ScheduleType.AWARDS       );
			ps.setNull(    4, Types.VARCHAR             );
			ps.setDate(    5, new java.sql.Date( time ) );
			ps.setInt(     6, Schedule.st_Scheduled     );
			
			ps.executeUpdate();
			
			// Postseason (set up division playoff schedule)
			time = advanceTime( time, 4 ); // friday after awards
			
			ps.setString(  1, year                      );
			ps.setInt(     2, sequence++                );
			ps.setInt(     3, ScheduleType.POSTSEASON   );
			ps.setNull(    4, Types.VARCHAR             );
			ps.setDate(    5, new java.sql.Date( time ) );
			ps.setInt(     6, Schedule.st_Scheduled     );
			
			ps.executeUpdate();
			
			// Division Playoff
			time = advanceTime( time, 2 ); // sunday after postseason

			for ( int j = 0; j < Constants.PLAYOFF_GAMES_ROUND_1; ++j ) {

				ps.setString(  1, year                          );
				ps.setInt(     2, sequence++                    );
				ps.setInt(     3, ScheduleType.DIVISION_PLAYOFF );
				ps.setNull(    4, Types.VARCHAR                 );
				ps.setDate(    5, new java.sql.Date( time )     );
				ps.setInt(     6, Schedule.st_Scheduled         );

				ps.executeUpdate();
				
				time = advanceTime( time, 1 );
			}

			// Division Championship - time should be correct from day after round 1
			for ( int j = 0; j < Constants.PLAYOFF_GAMES_ROUND_2; ++j ) {

				ps.setString(  1, year                               );
				ps.setInt(     2, sequence++                         );
				ps.setInt(     3, ScheduleType.DIVISION_CHAMPIONSHIP );
				ps.setNull(    4, Types.VARCHAR                      );
				ps.setDate(    5, new java.sql.Date( time )          );
				ps.setInt(     6, Schedule.st_Scheduled              );

				ps.executeUpdate();
				
				time = advanceTime( time, 1 );
			}

			// Conference Championship
			time = advanceTime( time, 2 ); // Sunday after finish of round 2

			for ( int j = 0; j < Constants.PLAYOFF_GAMES_ROUND_3; ++j ) {
				
				ps.setString(  1, year                                 );
				ps.setInt(     2, sequence++                           );
				ps.setInt(     3, ScheduleType.CONFERENCE_CHAMPIONSHIP );
				ps.setNull(    4, Types.VARCHAR                        );
				ps.setDate(    5, new java.sql.Date( time )            );
				ps.setInt(     6, Schedule.st_Scheduled                );

				ps.executeUpdate();
				
				time = advanceTime( time, 1 );
			}
			
			// NATC Championship
			time = advanceTime( time, 4 ); // sunday after finish of round 3
			
			ps.setString(  1, year                           );
			ps.setInt(     2, sequence++                     );
			ps.setInt(     3, ScheduleType.NATC_CHAMPIONSHIP );
			ps.setNull(    4, Types.VARCHAR                  );
			ps.setDate(    5, new java.sql.Date( time )      );
			ps.setInt(     6, Schedule.st_Scheduled          );
			
			ps.executeUpdate();

			// End of Postseason
			time = advanceTime( time, 1 );
			
			ps.setString(  1, year                           );
			ps.setInt(     2, sequence++                     );
			ps.setInt(     3, ScheduleType.END_OF_POSTSEASON );
			ps.setNull(    4, Types.VARCHAR                  );
			ps.setDate(    5, new java.sql.Date( time )      );
			ps.setInt(     6, Schedule.st_Scheduled          );
			
			ps.executeUpdate();
			
			// All Star Selection
			time = advanceTime( time, 1 );
			
			ps.setString(  1, year                       );
			ps.setInt(     2, sequence++                 );
			ps.setInt(     3, ScheduleType.ALL_STARS     );
			ps.setNull(    4, Types.VARCHAR              );
			ps.setDate(    5, new java.sql.Date( time )  );
			ps.setInt(     6, Schedule.st_Scheduled      );
			
			ps.executeUpdate();

			// All Stars Day 1
			time = advanceTime( time, 4 ); // Saturday after NATC championship
			
			ps.setString(  1, year                        );
			ps.setInt(     2, sequence++                  );
			ps.setInt(     3, ScheduleType.ALL_STAR_DAY_1 );
			ps.setNull(    4, Types.VARCHAR               );
			ps.setDate(    5, new java.sql.Date( time )   );
			ps.setInt(     6, Schedule.st_Scheduled       );
			
			ps.executeUpdate();

			// All Stars Day 2
			time = advanceTime( time, 1 );
			
			ps.setString(  1, year                        );
			ps.setInt(     2, sequence++                  );
			ps.setInt(     3, ScheduleType.ALL_STAR_DAY_2 );
			ps.setNull(    4, Types.VARCHAR               );
			ps.setDate(    5, new java.sql.Date( time )   );
			ps.setInt(     6, Schedule.st_Scheduled       );
			
			ps.executeUpdate();

			// End of allstar weekend
			time = advanceTime( time, 1 );
			
			ps.setString(  1, year                              );
			ps.setInt(     2, sequence++                        );
			ps.setInt(     3, ScheduleType.END_OF_ALLSTAR_GAMES );
			ps.setNull(    4, Types.VARCHAR                     );
			ps.setDate(    5, new java.sql.Date( time )         );
			ps.setInt(     6, Schedule.st_Scheduled             );
			
			ps.executeUpdate();
			
			// End of season
			time = getFirstMondayInNovember( Integer.valueOf( year ).intValue() );
			
			ps.setString(  1, year                       );
			ps.setInt(     2, sequence++                 );
			ps.setInt(     3, ScheduleType.END_OF_SEASON );
			ps.setNull(    4, Types.VARCHAR              );
			ps.setDate(    5, new java.sql.Date( time )  );
			ps.setInt(     6, Schedule.st_Scheduled      );
			
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
	
	public void updateScheduleEntry( Schedule schedule ) throws SQLException {
	
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getScheduleUpdatePs( dbConn );
			
			ps.setInt(     1,                    schedule.getType().getValue()       );
			ps.setString(  2,                    schedule.getData()                  );
			ps.setDate(    3, new java.sql.Date( schedule.getScheduled().getTime() ) );
			ps.setInt(     4,                    schedule.getStatus()                );
			
			ps.setString(  5, schedule.getYear()     );
			ps.setInt(     6, schedule.getSequence() );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}
	
	public void completeScheduleEvent( Schedule schedule ) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = DatabaseImpl.getScheduleCompleteUpdatePs( dbConn );
			
			ps.setInt(     1, Schedule.st_Completed  );
			ps.setString(  2, schedule.getYear()     );
			ps.setInt(     3, schedule.getSequence() );
			
			ps.executeUpdate();
		}
		finally {
			
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updatePlayoffSchedule( int type, List[] teams ) throws SQLException {
		
		PreparedStatement ps   = null;
		ResultSet         dbRs = null;
		boolean           flip = false;

		try {

			ps = DatabaseImpl.getScheduleEntriesByTypeSelectPs( dbConn );

			ps.setString( 1, year );
			ps.setInt(    2, type );

			dbRs = ps.executeQuery();

			while ( dbRs.next() ) {

				Schedule schedule = new Schedule();

				schedule.setYear(                        dbRs.getString(  1 )   );
				schedule.setSequence(                    dbRs.getInt(     2 )   );
				schedule.setType(      new ScheduleType( dbRs.getInt(     3 ) ) );
				schedule.setData(                        dbRs.getString(  4 )   );
				schedule.setScheduled(                   dbRs.getDate(    5 )   );
				schedule.setStatus(                      dbRs.getInt(     6 )   );

				ScheduleData scheduleData = new ScheduleData();

				for ( int i = 0; i < teams.length; ++i ) {

					if ( flip ) {

						if ( teams[i].size() == 4 ) {

							int n = i * 2;
							
							scheduleData.road_teams[n    ] = ((Team) teams[i].get( 0 )).getTeam_id();
							scheduleData.home_teams[n    ] = ((Team) teams[i].get( 3 )).getTeam_id();
							scheduleData.games++;

							scheduleData.road_teams[n + 1] = ((Team) teams[i].get( 1 )).getTeam_id();
							scheduleData.home_teams[n + 1] = ((Team) teams[i].get( 2 )).getTeam_id();
							scheduleData.games++;
						}

						if ( teams[i].size() == 2 ) {

							scheduleData.road_teams[i] = ((Team) teams[i].get( 0 )).getTeam_id();
							scheduleData.home_teams[i] = ((Team) teams[i].get( 1 )).getTeam_id();
							scheduleData.games++;
						}
					}
					else {

						if ( teams[i].size() == 4 ) {

							int n = i * 2;
							
							scheduleData.road_teams[n    ] = ((Team) teams[i].get( 3 )).getTeam_id();
							scheduleData.home_teams[n    ] = ((Team) teams[i].get( 0 )).getTeam_id();
							scheduleData.games++;

							scheduleData.road_teams[n + 1] = ((Team) teams[i].get( 2 )).getTeam_id();
							scheduleData.home_teams[n + 1] = ((Team) teams[i].get( 1 )).getTeam_id();
							scheduleData.games++;
						}

						if ( teams[i].size() == 2 ) {

							scheduleData.road_teams[i] = ((Team) teams[i].get( 1 )).getTeam_id();
							scheduleData.home_teams[i] = ((Team) teams[i].get( 0 )).getTeam_id();
							scheduleData.games++;
						}
					}
				}

				schedule.setData( scheduleData.toString() );

				updateScheduleEntry( schedule );
				
				flip = ! flip;
			}
		}
		finally {

			DatabaseImpl.closeDbRs( dbRs );
			DatabaseImpl.closeDbStmt( ps );
		}
	}

	public void updateAllstarSchedule( Schedule schedule, int[] teams ) throws SQLException {
		
		ScheduleData scheduleData = new ScheduleData();
		
		scheduleData.games = 2;
		
		scheduleData.road_teams[0] = teams[0];
		scheduleData.home_teams[0] = teams[1];
		
		scheduleData.road_teams[1] = teams[2];
		scheduleData.home_teams[1] = teams[3];
		
		schedule.setData( scheduleData.toString() );
		
		updateScheduleEntry( schedule );
	}

}
