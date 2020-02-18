package natc.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Team;
import natc.data.TeamGame;
import natc.form.GamesForm;
import natc.service.GameService;
import natc.service.ManagerService;
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.GameServiceImpl;
import natc.service.impl.ManagerServiceImpl;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.ManagerView;

public class GamesAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {

		GameService     gameService     = null;
		PlayerService   playerService   = null;
		ScheduleService scheduleService = null;
		TeamService     teamService     = null;
		ManagerService  managerService  = null;
		Schedule        scheduleEntry   = null;
		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		String          nextPage        = null;
		String          team_id_str     = null;
		
		try {

			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
			
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			scheduleService = new ScheduleServiceImpl( dbConn, null );

			// If a team ID was specified only get game results for that team and exit
			if ( (team_id_str = request.getParameter( "team_id" )) != null ) {
				
				int team_id = Integer.parseInt( team_id_str );
				
				if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) != null ) {
					
					Collection preseasonTeamGames  = null;
					Collection seasonTeamGames     = null;
					Collection postseasonTeamGames = null;
					
					gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
					
					if ( (preseasonTeamGames = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_Preseason )) != null ) {

						request.setAttribute( "preseasonTeamGames", preseasonTeamGames );
					}

					if ( (seasonTeamGames = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_RegularSeason )) != null ) {

						request.setAttribute( "seasonTeamGames", seasonTeamGames );
					}

					if ( (postseasonTeamGames = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_Postseason )) != null ) {

						request.setAttribute( "postseasonTeamGames", postseasonTeamGames );
					}
				}
				
				return mapping.findForward( "games" );
			}

			// Find out what the program did last
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {
			
				// Get the current year as the base year for the first season
				String year = Schedule.FIRST_YEAR;
				
				// Get a new game service with the above year
				gameService = new GameServiceImpl( dbConn, year );
				
				// No schedule entries exist - initialize natc data
				gameService.initializeDatabase();
			}
			else if ( scheduleEntry.getType().getValue() == ScheduleType.END_OF_SEASON ) {
			
				// If the last entry is end of season, generate a new schedule
				
				// Get a new game service with the next year
				gameService = new GameServiceImpl( dbConn, String.valueOf( Integer.parseInt( scheduleEntry.getYear() ) + 1 ) );
				
				// Start a new season
				gameService.startNewSeason( scheduleEntry.getYear() );
			}
			
			// Get the next scheduled event
			scheduleEntry = scheduleService.getNextScheduleEntry( scheduleEntry );
			
			gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
			
			// Run the next season event(s) based on the submitted form value
			GamesForm gamesForm = (GamesForm)form;
			
			Calendar cal = Calendar.getInstance();
			
			switch ( gamesForm.getOperation().intValue() ) {
			
			case 0: gameService.processScheduleEvent( scheduleEntry ); break;
			
			case 1:
				
				while ( true ) {
					
					gameService.processScheduleEvent( scheduleEntry );
					
					Schedule nextEntry = scheduleService.getNextScheduleEntry( scheduleEntry );
					
					if ( ! nextEntry.getType().equals( scheduleEntry.getType() ) ) break;
					
					cal.setTime( scheduleEntry.getScheduled() );
					
					int lastDow = cal.get( Calendar.DAY_OF_WEEK );
					
					cal.setTime( nextEntry.getScheduled() );
					
					int nextDow = cal.get( Calendar.DAY_OF_WEEK );
					
					if ( nextDow < lastDow ) break;
					
					scheduleEntry = nextEntry;
				}
				
				break;
				
			case 2:
				
				while ( true ) {
					
					gameService.processScheduleEvent( scheduleEntry );
					
					Schedule nextEntry = scheduleService.getNextScheduleEntry( scheduleEntry );

					if ( ! nextEntry.getType().equals( scheduleEntry.getType() ) ) break;
					
					cal.setTime( scheduleEntry.getScheduled() );
					
					int lastMonth = cal.get( Calendar.MONTH );
					
					cal.setTime( nextEntry.getScheduled() );
					
					int nextMonth = cal.get( Calendar.MONTH );
					
					if ( nextMonth != lastMonth ) break;
					
					scheduleEntry = nextEntry;
				}
				
				break;
				
			case 3:
				
				while ( true ) {
				
					gameService.processScheduleEvent( scheduleEntry );
					
					Schedule nextEntry = scheduleService.getNextScheduleEntry( scheduleEntry );
					
					if ( ! nextEntry.getType().equals( scheduleEntry.getType() ) ) break;
					
					scheduleEntry = nextEntry;
				}
				
				break;
				
			default: gameService.processScheduleEvent( scheduleEntry ); break;
			}
			
			Collection data = null;
			
			// Choose the next page to display
			switch ( scheduleEntry.getType().getValue() ) {

			case ScheduleType.BEGINNING_OF_SEASON:    nextPage = "beginning";         break;
			case ScheduleType.MANAGER_CHANGES:
				
				managerService = (ManagerService)new ManagerServiceImpl( dbConn, scheduleEntry.getYear() );
				
				if ( (data = managerService.getRetiredManagers()) != null ) request.setAttribute( "retired_managers", data );
				if ( (data = managerService.getFiredManagers())   != null ) request.setAttribute( "fired_managers",   data );
				if ( (data = managerService.getHiredManagers())   != null ) request.setAttribute( "hired_managers",   data );
				
				nextPage = "manager_changes";
				
				break;
				
			case ScheduleType.PLAYER_RETIREMENT:
				
				if ( (data = gameService.getRetiredPlayersByTeam())      != null ) request.setAttribute( "retired_team_players", data );
				if ( (data = gameService.getRetiredPlayersWithoutTeam()) != null ) request.setAttribute( "retired_players",      data );
				
				nextPage = "retired_players";
				
				break;
				
			case ScheduleType.FREE_AGENCY:
				
				if ( (data = gameService.getFreeAgentMoves()) != null ) request.setAttribute( "free_agency",  data );

				nextPage = "free_agents";
				
				break;

			case ScheduleType.ROOKIE_DRAFT_ROUND_1:
				
				if ( (data = gameService.getDraftPicks( 1 )) != null ) {
				
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.ROOKIE_DRAFT_ROUND_2:
				
				if ( (data = gameService.getDraftPicks( 41 )) != null ) {
					
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.TRAINING_CAMP:
				
				if ( (data = gameService.getStandoutPlayers())     != null ) request.setAttribute( "standout_players", data );
				if ( (data = gameService.getStandoutRookies())     != null ) request.setAttribute( "standout_rookies", data );
				if ( (data = gameService.getMostImprovedPlayers()) != null ) request.setAttribute( "most_improved",    data );
				
				nextPage = "training_camp";
				
				break;
				
			case ScheduleType.PRESEASON:

				List       teams   = null;
				Comparator precomp = new Comparator() {

					public int compare( Object arg0, Object arg1 ) {
						
						Team t1 = (Team)arg0;
						Team t2 = (Team)arg1;
						
						return t1.getPreseason_losses() - t2.getPreseason_losses();
					}};
				
					teams = gameService.getRankedTeamsByDivision( 0 ); Collections.sort( teams, precomp ); request.setAttribute( "div0teams", teams );
					teams = gameService.getRankedTeamsByDivision( 1 ); Collections.sort( teams, precomp ); request.setAttribute( "div1teams", teams );
					teams = gameService.getRankedTeamsByDivision( 2 ); Collections.sort( teams, precomp ); request.setAttribute( "div2teams", teams );
					teams = gameService.getRankedTeamsByDivision( 3 ); Collections.sort( teams, precomp ); request.setAttribute( "div3teams", teams );
				
				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "games";

				break;

			case ScheduleType.END_OF_PRESEASON:     nextPage = "end_of_pre";         break;
				
			case ScheduleType.ROSTER_CUT:
				
				if ( (data = gameService.getReleasedPlayers()) != null ) request.setAttribute( "released_players", data );
				
				nextPage = "roster_cut";
				
				break;
				
			case ScheduleType.REGULAR_SEASON:

				data = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", data );
				data = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", data );
				data = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", data );
				data = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", data );
				
				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "games";

				break;

			case ScheduleType.END_OF_REGULAR_SEASON:    nextPage = "end_of_reg";         break;
				
			case ScheduleType.AWARDS:
				
				managerService = new ManagerServiceImpl( dbConn, scheduleEntry.getYear() );
				playerService  = new PlayerServiceImpl(  dbConn, scheduleEntry.getYear() );
				
				if ( (data = playerService.getPlayerAwards()) != null ) {
				
					request.setAttribute( "awards", data );
				}
				
				ManagerView managerView = managerService.getManagerOfTheYear();
				
				if ( managerView != null ) request.setAttribute( "manager", managerView );
				
				nextPage = "awards";
				
				break;
				
			case ScheduleType.POSTSEASON:
			case ScheduleType.DIVISION_PLAYOFF:
			case ScheduleType.DIVISION_CHAMPIONSHIP:
			case ScheduleType.CONFERENCE_CHAMPIONSHIP:
			case ScheduleType.NATC_CHAMPIONSHIP:

				teamService = new TeamServiceImpl( dbConn, scheduleEntry.getYear() );
				
				List round1 = null;
				
				if ( (round1 = teamService.getPlayoffTeams()) != null ) {
				
					Collections.sort( round1, new Comparator() {

						public int compare( Object arg0, Object arg1 ) {
							
							Team t1 = (Team)arg0;
							Team t2 = (Team)arg1;
							
							if ( t1.getDivision() != t2.getDivision() ) {
							
								return t1.getDivision() - t2.getDivision();
							}
							
							return t1.getDivision_rank() - t2.getDivision_rank();
						} } );

					swapElement( round1,  1,  3 );
					swapElement( round1,  0,  1 );
					
					swapElement( round1,  5,  7 );
					swapElement( round1,  4,  5 );
					
					swapElement( round1,  9, 11 );
					swapElement( round1,  8,  9 );
					
					swapElement( round1, 13, 15 );
					swapElement( round1, 12, 13 );
					
					List round2 = new ArrayList();
					
					Team team1 = null;
					Team team2 = null;
					
					for ( int i = 0; i < round1.size(); ++i ) {
					
						if ( i == 0  ||  (i % 2) == 0 ) {
							
							team1 = (Team) round1.get( i );
						}
						else {
						
							team2 = (Team) round1.get( i );
							
							if (    team1.getPlayoff_rank() == 0  ||
									team2.getPlayoff_rank() == 0  ||
									team1.getPlayoff_rank() == team2.getPlayoff_rank() ) {
							
								round2.add( new Team() );
							}
							else {
						
								round2.add( (team1.getPlayoff_rank() > team2.getPlayoff_rank()) ? team1 : team2 );
							}
						}
					}
					
					List round3 = new ArrayList();
					
					for ( int i = 0; i < round2.size(); ++i ) {
						
						if ( (i % 2) == 0 ) {
							
							team1 = (Team) round2.get( i );
						}
						else {
						
							team2 = (Team) round2.get( i );
							
							if (    team1.getPlayoff_rank() == 0  ||
									team2.getPlayoff_rank() == 0  ||
									team1.getPlayoff_rank() == team2.getPlayoff_rank() ) {
							
								round3.add( new Team() );
							}
							else {
						
								round3.add( (team1.getPlayoff_rank() > team2.getPlayoff_rank()) ? team1 : team2 );
							}
						}
					}

					List round4 = new ArrayList();
					
					for ( int i = 0; i < round3.size(); ++i ) {
						
						if ( (i % 2) == 0 ) {
							
							team1 = (Team) round3.get( i );
						}
						else {
						
							team2 = (Team) round3.get( i );
							
							if (    team1.getPlayoff_rank() == 0  ||
									team2.getPlayoff_rank() == 0  ||
									team1.getPlayoff_rank() == team2.getPlayoff_rank() ) {
							
								round4.add( new Team() );
							}
							else {
						
								round4.add( (team1.getPlayoff_rank() > team2.getPlayoff_rank()) ? team1 : team2 );
							}
						}
					}

					List round5 = new ArrayList();
					
					if ( round4.size() == 2 ) {

						team1 = (Team) round4.get( 0 );
						team2 = (Team) round4.get( 1 );
						
						if      ( team1.getPlayoff_rank() > 4 ) round5.add(     team1  );
						else if ( team2.getPlayoff_rank() > 4 ) round5.add(     team2  );
						else                                    round5.add( new Team() );
					}
					
					request.setAttribute( "round1", round1 );
					request.setAttribute( "round2", round2 );
					request.setAttribute( "round3", round3 );
					request.setAttribute( "round4", round4 );
					request.setAttribute( "round5", round5 );
				}

				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "games";
				
				break;
				
			case ScheduleType.END_OF_POSTSEASON:    nextPage = "end_of_post";         break;
				
			case ScheduleType.ALL_STARS:
				
				teamService    = new TeamServiceImpl(    dbConn, scheduleEntry.getYear() );
				playerService  = new PlayerServiceImpl(  dbConn, scheduleEntry.getYear() );
				managerService = new ManagerServiceImpl( dbConn, scheduleEntry.getYear() );
				
				int allstarTeamIds[] = teamService.getAllstarTeamIds();
				
				data = playerService.getAllstarsByTeamId( allstarTeamIds[0] ); request.setAttribute( "div0stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[1] ); request.setAttribute( "div1stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[2] ); request.setAttribute( "div2stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[3] ); request.setAttribute( "div3stars", data );
				
				request.setAttribute( "manager0", managerService.getManagerViewByAllstarTeamId( allstarTeamIds[0] ) );
				request.setAttribute( "manager1", managerService.getManagerViewByAllstarTeamId( allstarTeamIds[1] ) );
				request.setAttribute( "manager2", managerService.getManagerViewByAllstarTeamId( allstarTeamIds[2] ) );
				request.setAttribute( "manager3", managerService.getManagerViewByAllstarTeamId( allstarTeamIds[3] ) );
				
				nextPage = "allstars";
				
				break;
				
			case ScheduleType.ALL_STAR_DAY_1:
			case ScheduleType.ALL_STAR_DAY_2:

				data = gameService.getRankedAllstarTeams(); request.setAttribute( "allstarTeams", data );
				
				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "games";

				break;

			case ScheduleType.END_OF_ALLSTAR_GAMES:    nextPage = "end_of_asg";         break;
				
			case ScheduleType.END_OF_SEASON:           nextPage = "end";                break;
			}
			
			request.setAttribute( "schedule", scheduleEntry );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}

		return mapping.findForward( nextPage );
	}
	
	private void swapElement( List set, int idx1, int idx2 ) {
	
		Object x = set.get( idx1 );
		set.set( idx1, set.get( idx2 ) );
		set.set( idx2, x );
	}
}
