package natc.action;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.data.GameState;
import natc.service.GameService;
import natc.service.PlayerService;
import natc.service.TeamService;
import natc.service.impl.GameServiceImpl;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.PlayerGameView;
import natc.view.TeamGameView;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GameAction extends Action {

	private Connection dbConn = null;
	
	private ActionForward generateAjaxResponse(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response,
			int                 game_id
	) throws Exception {

		TeamService     teamService     = null;
		PlayerService   playerService   = null;
		GameService     gameService     = null;
		StringBuffer    stringBuffer    = null;
		GameState       gameState;
		TeamGameView    homeGame;
		TeamGameView    roadGame;
		Collection      homePlayers;
		Collection      roadPlayers;
		Iterator        i;

		stringBuffer  = new StringBuffer();
		teamService   = new TeamServiceImpl(   dbConn, null );
		playerService = new PlayerServiceImpl( dbConn, null );
		gameService   = new GameServiceImpl(   dbConn, null );
		
		stringBuffer.append( "<response>" );
		
		if ( (gameState = gameService.getGameState( game_id )) != null ) {
			
			stringBuffer.append( "<gameState>" );
			stringBuffer.append( gameState.toXML() );
			stringBuffer.append( "</gameState>" );
		}
		
		if ( (homeGame = teamService.getHomeGame( game_id )) != null ) {
			
			stringBuffer.append( "<homeTeam>" );
			
			stringBuffer.append( homeGame.toXML() );

			if ( (homePlayers = playerService.getPlayerGamesForTeamByGame( game_id, homeGame.getTeam_id() )) != null ) {

				stringBuffer.append( "<players>" );
				
				i = homePlayers.iterator();
				
				while ( i.hasNext() ) {
				
					PlayerGameView playerGame = (PlayerGameView)i.next();
					
					stringBuffer.append( "<player>" );
					
					stringBuffer.append( playerGame.toXML() );
					
					stringBuffer.append( "</player>" );
				}
				
				stringBuffer.append( "</players>" );
			}

			stringBuffer.append( "</homeTeam>" );
		}

		if ( (roadGame = teamService.getRoadGame( game_id )) != null ) {
			
			stringBuffer.append( "<roadTeam>" );
			
			stringBuffer.append( roadGame.toXML() );

			if ( (roadPlayers = playerService.getPlayerGamesForTeamByGame( game_id, roadGame.getTeam_id() )) != null ) {

				stringBuffer.append( "<players>" );
				
				i = roadPlayers.iterator();
				
				while ( i.hasNext() ) {
				
					PlayerGameView playerGame = (PlayerGameView)i.next();
					
					stringBuffer.append( "<player>" );
					
					stringBuffer.append( playerGame.toXML() );
					
					stringBuffer.append( "</player>" );
				}
				
				stringBuffer.append( "</players>" );
			}

			stringBuffer.append( "</roadTeam>" );
		}
		
		stringBuffer.append( "</response>" );
		
		response.getWriter().println(stringBuffer.toString());
		response.getWriter().close();
		
		return null;
	}
	
	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		DataSource      dataSource      = null;
		TeamService     teamService     = null;
		PlayerService   playerService   = null;
		String          game_id_str     = null;
		int             game_id;
		
		if ( (game_id_str = request.getParameter( "game_id" )) == null ) {
		
			if ( request.getParameter( "ajax" ) != null ) return null;
			
			return mapping.findForward( "success" );
		}
		
		game_id = Integer.parseInt( game_id_str );
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}

			if ( request.getParameter( "ajax" ) != null ) {

				return generateAjaxResponse( mapping, form, request, response, game_id );
			}
			
			teamService   = new TeamServiceImpl(   dbConn, null );
			playerService = new PlayerServiceImpl( dbConn, null );

			TeamGameView homeGame;
			TeamGameView roadGame;
			Collection   homePlayers;
			Collection   roadPlayers;

			if ( (homeGame = teamService.getHomeGame( game_id )) != null ) {

				request.setAttribute( "homeGame", homeGame );

				if ( (homePlayers = playerService.getPlayerGamesForTeamByGame( game_id, homeGame.getTeam_id() )) != null ) {

					request.setAttribute( "homePlayers", homePlayers );
				}
			}

			if ( (roadGame = teamService.getRoadGame( game_id )) != null ) {

				request.setAttribute( "roadGame", roadGame );

				if ( (roadPlayers = playerService.getPlayerGamesForTeamByGame( game_id, roadGame.getTeam_id() )) != null ) {

					request.setAttribute( "roadPlayers", roadPlayers );
				}
			}
		}
		finally {

			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
