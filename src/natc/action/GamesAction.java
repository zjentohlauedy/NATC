package natc.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.data.GameState;
import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Team;
import natc.data.TeamGame;
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
import natc.view.GameView;
import natc.view.ManagerAwardsView;

public class GamesAction extends Action {

	private Connection dbConn = null;

	private ActionForward generateAjaxResponse(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {

		GameService     gameService     = null;
		ScheduleService scheduleService = null;
		Schedule        scheduleEntry   = null;
		StringBuffer    stringBuffer    = null;
		Collection      games           = null;
		
		scheduleService = new ScheduleServiceImpl( dbConn, null );

		// First check for an in-progress schedule entry
		if ( (scheduleEntry = scheduleService.getCurrentScheduleEntry()) == null ) {

			// Nothing in-progress, find the last completed schedule entry
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {

				return null;
			}
		}

		gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );

		if ( (games = gameService.getGamesByDate( scheduleEntry.getScheduled() )) == null ) {

			return null;
		}

		stringBuffer  = new StringBuffer();
		
		stringBuffer.append( "<response>" );
		
		Iterator i = games.iterator();
		
		while ( i.hasNext() ) {
		
			GameView  gameView  = (GameView)i.next();
			GameState gameState = gameService.getGameState( gameView.getGame_id().intValue() ); 
			
			stringBuffer.append( "<game>" );
			
			stringBuffer.append( "<gameId>"    + gameView.getGame_id()    + "</gameId>"    );
			stringBuffer.append( "<roadScore>" + gameView.getRoad_score() + "</roadScore>" );
			stringBuffer.append( "<roadWin>"   + gameView.getRoad_win()   + "</roadWin>"   );
			stringBuffer.append( "<homeScore>" + gameView.getHome_score() + "</homeScore>" );
			stringBuffer.append( "<homeWin>"   + gameView.getHome_win()   + "</homeWin>"   );
			
			if ( gameState != null ) {
			
				stringBuffer.append( "<state>" );
				
				stringBuffer.append( gameState.toXML() );
				
				stringBuffer.append( "</state>" );
			}
			
			stringBuffer.append( "</game>" );
		}

		stringBuffer.append( "</response>" );
		
		response.getWriter().println(stringBuffer.toString());
		response.getWriter().close();
		
		return null;
	}
	
	private ActionForward getPlayerGames(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response,
			String              player_id_str
	) throws Exception {

		GameService     gameService     = null;
		ScheduleService scheduleService = null;
		Schedule        scheduleEntry   = null;
		Collection data                 = null;
		String     year                 = null;
		String     type_str             = null;
		int        type                 = 0;
		int        player_id            = Integer.parseInt( player_id_str );
		

		scheduleService = new ScheduleServiceImpl( dbConn, null );

		if ( (year = request.getParameter( "year" )) == null ) {
		
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {
				
				return mapping.findForward( "games" );
			}
			
			year = scheduleEntry.getYear();
		}

		if ( (type_str = request.getParameter( "type" )) != null ) {
		
			type = Integer.parseInt( type_str );
		}
		
		gameService = new GameServiceImpl( dbConn, year );

		if ( type == 0  ||  type == TeamGame.gt_Preseason ) {
			
			if ( (data = gameService.getGamesByPlayerIdAndType( player_id, TeamGame.gt_Preseason )) != null ) {

				request.setAttribute( "preseasonPlayerGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_RegularSeason ) {

			if ( (data = gameService.getGamesByPlayerIdAndType( player_id, TeamGame.gt_RegularSeason )) != null ) {

				request.setAttribute( "seasonPlayerGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_Postseason ) {

			if ( (data = gameService.getGamesByPlayerIdAndType( player_id, TeamGame.gt_Postseason )) != null ) {

				request.setAttribute( "postseasonPlayerGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_Allstar ) {

			if ( (data = gameService.getGamesByPlayerIdAndType( player_id, TeamGame.gt_Allstar )) != null ) {

				request.setAttribute( "allstarPlayerGames", data );
			}
		}

		return mapping.findForward( "games" );
	}
	
	private ActionForward getTeamGames(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response,
			String              team_id_str
	) throws Exception {

		GameService     gameService     = null;
		ScheduleService scheduleService = null;
		Schedule        scheduleEntry   = null;
		Collection      data            = null;
		String          year            = null;
		String          type_str        = null;
		int             type            = 0;
		int             team_id         = Integer.parseInt( team_id_str );

		
		scheduleService = new ScheduleServiceImpl( dbConn, null );

		if ( (year = request.getParameter( "year" )) == null ) {
		
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {
				
				return mapping.findForward( "games" );
			}
			
			year = scheduleEntry.getYear();
		}

		if ( (type_str = request.getParameter( "type" )) != null ) {
		
			type = Integer.parseInt( type_str );
		}
		
		gameService = new GameServiceImpl( dbConn, year );

		if ( type == 0  ||  type == TeamGame.gt_Preseason ) {
			
			if ( (data = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_Preseason )) != null ) {

				request.setAttribute( "preseasonTeamGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_RegularSeason ) {

			if ( (data = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_RegularSeason )) != null ) {

				request.setAttribute( "seasonTeamGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_Postseason ) {

			if ( (data = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_Postseason )) != null ) {

				request.setAttribute( "postseasonTeamGames", data );
			}
		}

		if ( type == 0  ||  type == TeamGame.gt_Allstar ) {

			if ( (data = gameService.getGamesByTeamIdAndType( team_id, TeamGame.gt_Allstar )) != null ) {

				request.setAttribute( "allstarTeamGames", data );
			}
		}

		return mapping.findForward( "games" );
	}
	
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
		String          nextPage        = null;
		String          team_id         = null;
		String          player_id       = null;
		
		try {

			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
			
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}

			if ( request.getParameter( "ajax" ) != null ) {
				
				return generateAjaxResponse( mapping, form, request, response );
			}
			
			// If a team ID was specified only get game results for that team and exit
			if ( (team_id = request.getParameter( "team_id" )) != null ) {

				return getTeamGames( mapping, form, request, response, team_id );
			}

			// If a player ID was specified only get game results for that player and exit
			if ( (player_id = request.getParameter( "player_id" )) != null ) {

				return getPlayerGames( mapping, form, request, response, player_id );
			}

			scheduleService = new ScheduleServiceImpl( dbConn, null );

			// First check for an in-progress schedule entry
			if ( (scheduleEntry = scheduleService.getCurrentScheduleEntry()) == null ) {

				// Nothing in-progress, find the last completed schedule entry
				if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {

					return mapping.findForward( "games" );
				}
			}

			gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
			
			Collection data     = null;
			List       teams    = null;
			GameView   gameView = null;
			Comparator precomp  = new Comparator() {

				public int compare( Object arg0, Object arg1 ) {

					Team t1 = (Team)arg0;
					Team t2 = (Team)arg1;

					return t1.getPreseason_losses() - t2.getPreseason_losses();
				}
			};
			
			teamService    = new TeamServiceImpl(    dbConn, scheduleEntry.getYear() );
			managerService = new ManagerServiceImpl( dbConn, scheduleEntry.getYear() );
			playerService  = new PlayerServiceImpl(  dbConn, scheduleEntry.getYear() );
			
			// Choose the next page to display
			switch ( scheduleEntry.getType().getValue() ) {

			case ScheduleType.BEGINNING_OF_SEASON:    nextPage = "beginning";         break;
			case ScheduleType.MANAGER_CHANGES:
				
				if ( (data = managerService.getManagerMovesByTeam()) != null ) request.setAttribute( "manager_changes", data );
				
				nextPage = "manager_changes";
				
				break;
				
			case ScheduleType.PLAYER_CHANGES:
				
				if ( (data = playerService.getPlayerChangesByTeam()) != null ) request.setAttribute( "player_changes", data );
				
				nextPage = "player_changes";
				
				break;
				
			case ScheduleType.ROOKIE_DRAFT_ROUND_1:
				
				if ( (data = playerService.getDraftPicks( 1 )) != null ) {
				
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.ROOKIE_DRAFT_ROUND_2:
				
				if ( (data = playerService.getDraftPicks( 41 )) != null ) {
					
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.TRAINING_CAMP:
				
				if ( (data = playerService.getStandoutPlayers())     != null ) request.setAttribute( "standout_players", data );
				if ( (data = playerService.getStandoutRookies())     != null ) request.setAttribute( "standout_rookies", data );
				if ( (data = playerService.getMostImprovedPlayers()) != null ) request.setAttribute( "most_improved",    data );
				
				nextPage = "training_camp";
				
				break;

			case ScheduleType.PRESEASON:

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

			case ScheduleType.END_OF_PRESEASON:
				
				teams = gameService.getRankedTeamsByDivision( 0 ); Collections.sort( teams, precomp ); request.setAttribute( "div0teams", teams );
				teams = gameService.getRankedTeamsByDivision( 1 ); Collections.sort( teams, precomp ); request.setAttribute( "div1teams", teams );
				teams = gameService.getRankedTeamsByDivision( 2 ); Collections.sort( teams, precomp ); request.setAttribute( "div2teams", teams );
				teams = gameService.getRankedTeamsByDivision( 3 ); Collections.sort( teams, precomp ); request.setAttribute( "div3teams", teams );

				nextPage = "end_of_pre";
				
				break;
				
			case ScheduleType.ROSTER_CUT:
				
				if ( (data = playerService.getReleasedPlayers()) != null ) request.setAttribute( "released_players", data );
				
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

			case ScheduleType.END_OF_REGULAR_SEASON:

				data = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", data );
				data = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", data );
				data = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", data );
				data = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", data );
				
				nextPage = "end_of_reg";
				
				break;
				
			case ScheduleType.AWARDS:
				
				if ( (data = playerService.getPlayerAwards()) != null ) {
				
					request.setAttribute( "awards", data );
				}
				
				ManagerAwardsView managerAwardsView = managerService.getManagerOfTheYear();
				
				if ( managerAwardsView != null ) request.setAttribute( "manager", managerAwardsView );
				
				nextPage = "awards";
				
				break;
				
			case ScheduleType.POSTSEASON:
			case ScheduleType.DIVISION_PLAYOFF:
			case ScheduleType.DIVISION_CHAMPIONSHIP:
			case ScheduleType.CONFERENCE_CHAMPIONSHIP:
			case ScheduleType.NATC_CHAMPIONSHIP:

				Iterator        i               = null;
				Team            team            = null;
				List            round1          = null;
				List            round2          = null;
				List            round3          = null;
				List            round4          = null;
				List            round5          = null;
				List            playoffGameInfo = null; 

				round1 = teamService.getPlayoffTeams();
				
				i = round1.iterator();
				
				while ( i.hasNext() ) {
				
					team = (Team)i.next();
					
					if ( team.getPlayoff_rank() >= 2 ) {
					
						if ( round2 == null ) round2 = new ArrayList();
						
						round2.add( team );
					}
				}

				if ( round2 != null ) {
					i = round2.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 3 ) {

							if ( round3 == null ) round3 = new ArrayList();

							round3.add( team );
						}
					}
				}

				if ( round3 != null ) {
					i = round3.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 4 ) {

							if ( round4 == null ) round4 = new ArrayList();

							round4.add( team );
						}
					}
				}

				if ( round4 != null ) {
					i = round4.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 5 ) {

							if ( round5 == null ) round5 = new ArrayList();

							round5.add( team );
						}
					}
				}

				request.setAttribute( "round1", round1 );
				request.setAttribute( "round2", round2 );
				request.setAttribute( "round3", round3 );
				request.setAttribute( "round4", round4 );
				request.setAttribute( "round5", round5 );
				
				if ( (playoffGameInfo = gameService.getPlayoffGameInfo()) != null ) {
					
					request.setAttribute( "playoffGameInfo", playoffGameInfo );
				}

				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "bracket";
				
				break;
				
			case ScheduleType.END_OF_POSTSEASON:

				gameView = null;
				
				if ( (gameView = gameService.getChampionshipGame()) != null ) {

					Team champion = null;
					int  winner   = 0;
					
					request.setAttribute( "championship", gameView );
					
					if   ( gameView.getRoad_win().booleanValue() ) winner = gameView.getRoad_team_id().intValue();
					else                                           winner = gameView.getHome_team_id().intValue();
					
					if ( (champion = teamService.getTeamById( winner )) != null ) request.setAttribute( "champion", champion );
				}

				nextPage = "end_of_post";
				
				break;
				
			case ScheduleType.ALL_STARS:
				
				int allstarTeamIds[] = teamService.getAllstarTeamIds();
				
				data = playerService.getAllstarsByTeamId( allstarTeamIds[0] ); request.setAttribute( "div0stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[1] ); request.setAttribute( "div1stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[2] ); request.setAttribute( "div2stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[3] ); request.setAttribute( "div3stars", data );
				
				request.setAttribute( "manager0", managerService.getAllstarManagerByTeamId( allstarTeamIds[0] ) );
				request.setAttribute( "manager1", managerService.getAllstarManagerByTeamId( allstarTeamIds[1] ) );
				request.setAttribute( "manager2", managerService.getAllstarManagerByTeamId( allstarTeamIds[2] ) );
				request.setAttribute( "manager3", managerService.getAllstarManagerByTeamId( allstarTeamIds[3] ) );
				
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

			case ScheduleType.END_OF_ALLSTAR_GAMES:

				data = gameService.getRankedAllstarTeams(); request.setAttribute( "allstarTeams", data );
				
				nextPage = "end_of_asg";
				
				break;
				
			case ScheduleType.END_OF_SEASON:

				data = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", data );
				data = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", data );
				data = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", data );
				data = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", data );

				gameView = null;
				
				if ( (gameView = gameService.getChampionshipGame()) != null ) {

					Team champion = null;
					int  winner   = 0;
					
					request.setAttribute( "championship", gameView );
					
					if   ( gameView.getRoad_win().booleanValue() ) winner = gameView.getRoad_team_id().intValue();
					else                                           winner = gameView.getHome_team_id().intValue();
					
					if ( (champion = teamService.getTeamById( winner )) != null ) request.setAttribute( "champion", champion );
				}

				data = gameService.getRankedAllstarTeams(); request.setAttribute( "allstarTeams", data );
				
				nextPage = "end";
				
				break;
			}
			
			request.setAttribute( "schedule", scheduleEntry );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}

		return mapping.findForward( nextPage );
	}

	public ActionForward execute_old(
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
		String          nextPage        = null;
		String          team_id_str     = null;
		String          player_id_str   = null;
		
		try {

			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
			
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}

			// If a team ID was specified only get game results for that team and exit
			if ( (team_id_str = request.getParameter( "team_id" )) != null ) {

				return getTeamGames( mapping, form, request, response, team_id_str );
			}

			scheduleService = new ScheduleServiceImpl( dbConn, null );

			// If a player ID was specified only get game results for that player and exit
			if ( (player_id_str = request.getParameter( "player_id" )) != null ) {

				return getPlayerGames( mapping, form, request, response, player_id_str );
			}

			// Find out what the program did last
			if ( (scheduleEntry = scheduleService.getLastScheduleEntry()) == null ) {
			
				/*
				// Get the current year as the base year for the first season
				String year = Schedule.FIRST_YEAR;
				
				// Get a new game service with the above year
				gameService = new GameServiceImpl( dbConn, year );
				
				// No schedule entries exist - initialize natc data
				gameService.initializeDatabase();
				*/
				return mapping.findForward( "games" );
			}
			else if ( scheduleEntry.getType().getValue() == ScheduleType.END_OF_SEASON ) {
			
				/*
				// If the last entry is end of season, generate a new schedule
				
				// Get a new game service with the next year
				gameService = new GameServiceImpl( dbConn, String.valueOf( Integer.parseInt( scheduleEntry.getYear() ) + 1 ) );
				
				// Start a new season
				gameService.startNewSeason( scheduleEntry.getYear() );
				*/
				//return mapping.findForward( "games" );
			}
			/*
			// Get the next scheduled event
			scheduleEntry = scheduleService.getNextScheduleEntry( scheduleEntry );
			*/
			
			gameService = new GameServiceImpl( dbConn, scheduleEntry.getYear() );
			/*
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
			*/
			Collection data     = null;
			List       teams    = null;
			GameView   gameView = null;
			Comparator precomp  = new Comparator() {

				public int compare( Object arg0, Object arg1 ) {

					Team t1 = (Team)arg0;
					Team t2 = (Team)arg1;

					return t1.getPreseason_losses() - t2.getPreseason_losses();
				}
			};
			
			teamService    = new TeamServiceImpl(    dbConn, scheduleEntry.getYear() );
			managerService = new ManagerServiceImpl( dbConn, scheduleEntry.getYear() );
			playerService  = new PlayerServiceImpl(  dbConn, scheduleEntry.getYear() );
			
			// Choose the next page to display
			switch ( scheduleEntry.getType().getValue() ) {

			case ScheduleType.BEGINNING_OF_SEASON:    nextPage = "beginning";         break;
			case ScheduleType.MANAGER_CHANGES:
				
				if ( (data = managerService.getManagerMovesByTeam()) != null ) request.setAttribute( "manager_changes", data );
				
				nextPage = "manager_changes";
				
				break;
				
			case ScheduleType.PLAYER_CHANGES:
				
				if ( (data = playerService.getPlayerChangesByTeam()) != null ) request.setAttribute( "player_changes", data );
				
				nextPage = "player_changes";
				
				break;
				
			case ScheduleType.ROOKIE_DRAFT_ROUND_1:
				
				if ( (data = playerService.getDraftPicks( 1 )) != null ) {
				
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.ROOKIE_DRAFT_ROUND_2:
				
				if ( (data = playerService.getDraftPicks( 41 )) != null ) {
					
					request.setAttribute( "draft_picks", data );
				}
				
				nextPage = "rookie_draft";
				
				break;
				
			case ScheduleType.TRAINING_CAMP:
				
				if ( (data = playerService.getStandoutPlayers())     != null ) request.setAttribute( "standout_players", data );
				if ( (data = playerService.getStandoutRookies())     != null ) request.setAttribute( "standout_rookies", data );
				if ( (data = playerService.getMostImprovedPlayers()) != null ) request.setAttribute( "most_improved",    data );
				
				nextPage = "training_camp";
				
				break;

			case ScheduleType.PRESEASON:

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

			case ScheduleType.END_OF_PRESEASON:
				
				teams = gameService.getRankedTeamsByDivision( 0 ); Collections.sort( teams, precomp ); request.setAttribute( "div0teams", teams );
				teams = gameService.getRankedTeamsByDivision( 1 ); Collections.sort( teams, precomp ); request.setAttribute( "div1teams", teams );
				teams = gameService.getRankedTeamsByDivision( 2 ); Collections.sort( teams, precomp ); request.setAttribute( "div2teams", teams );
				teams = gameService.getRankedTeamsByDivision( 3 ); Collections.sort( teams, precomp ); request.setAttribute( "div3teams", teams );

				nextPage = "end_of_pre";
				
				break;
				
			case ScheduleType.ROSTER_CUT:
				
				if ( (data = playerService.getReleasedPlayers()) != null ) request.setAttribute( "released_players", data );
				
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

			case ScheduleType.END_OF_REGULAR_SEASON:

				data = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", data );
				data = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", data );
				data = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", data );
				data = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", data );
				
				nextPage = "end_of_reg";
				
				break;
				
			case ScheduleType.AWARDS:
				
				if ( (data = playerService.getPlayerAwards()) != null ) {
				
					request.setAttribute( "awards", data );
				}
				
				ManagerAwardsView managerAwardsView = managerService.getManagerOfTheYear();
				
				if ( managerAwardsView != null ) request.setAttribute( "manager", managerAwardsView );
				
				nextPage = "awards";
				
				break;
				
			case ScheduleType.POSTSEASON:
			case ScheduleType.DIVISION_PLAYOFF:
			case ScheduleType.DIVISION_CHAMPIONSHIP:
			case ScheduleType.CONFERENCE_CHAMPIONSHIP:
			case ScheduleType.NATC_CHAMPIONSHIP:

				Iterator        i               = null;
				Team            team            = null;
				List            round1          = null;
				List            round2          = null;
				List            round3          = null;
				List            round4          = null;
				List            round5          = null;
				List            playoffGameInfo = null; 

				round1 = teamService.getPlayoffTeams();
				
				i = round1.iterator();
				
				while ( i.hasNext() ) {
				
					team = (Team)i.next();
					
					if ( team.getPlayoff_rank() >= 2 ) {
					
						if ( round2 == null ) round2 = new ArrayList();
						
						round2.add( team );
					}
				}

				if ( round2 != null ) {
					i = round2.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 3 ) {

							if ( round3 == null ) round3 = new ArrayList();

							round3.add( team );
						}
					}
				}

				if ( round3 != null ) {
					i = round3.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 4 ) {

							if ( round4 == null ) round4 = new ArrayList();

							round4.add( team );
						}
					}
				}

				if ( round4 != null ) {
					i = round4.iterator();

					while ( i.hasNext() ) {

						team = (Team)i.next();

						if ( team.getPlayoff_rank() >= 5 ) {

							if ( round5 == null ) round5 = new ArrayList();

							round5.add( team );
						}
					}
				}

				request.setAttribute( "round1", round1 );
				request.setAttribute( "round2", round2 );
				request.setAttribute( "round3", round3 );
				request.setAttribute( "round4", round4 );
				request.setAttribute( "round5", round5 );
				
				if ( (playoffGameInfo = gameService.getPlayoffGameInfo()) != null ) {
					
					request.setAttribute( "playoffGameInfo", playoffGameInfo );
				}

				if ( (data = gameService.getGamesByDate( scheduleEntry.getScheduled() )) != null ) {

					request.setAttribute( "games", data );
				}

				if ( (data = gameService.getInjuriesByDate( scheduleEntry.getScheduled() )) != null ) {
					
					request.setAttribute( "injuries", data );
				}
				
				nextPage = "bracket";
				
				break;
				
			case ScheduleType.END_OF_POSTSEASON:

				gameView = null;
				
				if ( (gameView = gameService.getChampionshipGame()) != null ) {

					Team champion = null;
					int  winner   = 0;
					
					request.setAttribute( "championship", gameView );
					
					if   ( gameView.getRoad_win().booleanValue() ) winner = gameView.getRoad_team_id().intValue();
					else                                           winner = gameView.getHome_team_id().intValue();
					
					if ( (champion = teamService.getTeamById( winner )) != null ) request.setAttribute( "champion", champion );
				}

				nextPage = "end_of_post";
				
				break;
				
			case ScheduleType.ALL_STARS:
				
				int allstarTeamIds[] = teamService.getAllstarTeamIds();
				
				data = playerService.getAllstarsByTeamId( allstarTeamIds[0] ); request.setAttribute( "div0stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[1] ); request.setAttribute( "div1stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[2] ); request.setAttribute( "div2stars", data );
				data = playerService.getAllstarsByTeamId( allstarTeamIds[3] ); request.setAttribute( "div3stars", data );
				
				request.setAttribute( "manager0", managerService.getAllstarManagerByTeamId( allstarTeamIds[0] ) );
				request.setAttribute( "manager1", managerService.getAllstarManagerByTeamId( allstarTeamIds[1] ) );
				request.setAttribute( "manager2", managerService.getAllstarManagerByTeamId( allstarTeamIds[2] ) );
				request.setAttribute( "manager3", managerService.getAllstarManagerByTeamId( allstarTeamIds[3] ) );
				
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

			case ScheduleType.END_OF_ALLSTAR_GAMES:

				data = gameService.getRankedAllstarTeams(); request.setAttribute( "allstarTeams", data );
				
				nextPage = "end_of_asg";
				
				break;
				
			case ScheduleType.END_OF_SEASON:

				data = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", data );
				data = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", data );
				data = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", data );
				data = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", data );

				gameView = null;
				
				if ( (gameView = gameService.getChampionshipGame()) != null ) {

					Team champion = null;
					int  winner   = 0;
					
					request.setAttribute( "championship", gameView );
					
					if   ( gameView.getRoad_win().booleanValue() ) winner = gameView.getRoad_team_id().intValue();
					else                                           winner = gameView.getHome_team_id().intValue();
					
					if ( (champion = teamService.getTeamById( winner )) != null ) request.setAttribute( "champion", champion );
				}

				data = gameService.getRankedAllstarTeams(); request.setAttribute( "allstarTeams", data );
				
				nextPage = "end";
				
				break;
			}
			
			request.setAttribute( "schedule", scheduleEntry );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}

		return mapping.findForward( nextPage );
	}
}
