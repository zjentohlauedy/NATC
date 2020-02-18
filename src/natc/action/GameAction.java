package natc.action;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.service.PlayerService;
import natc.service.TeamService;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.PlayerGameView;
import natc.view.TeamGameView;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GameAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		TeamService     teamService     = null;
		PlayerService   playerService   = null;
		String          game_id_str     = null;
		int             game_id;
		boolean         ajax_request    = false;
		
		if ( request.getParameter( "ajax" ) != null ) ajax_request = true;
		
		if ( (game_id_str = request.getParameter( "game_id" )) == null ) {
		
			if ( ajax_request ) return null;
			
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
			
			teamService   = new TeamServiceImpl( dbConn, null );
			playerService = new PlayerServiceImpl( dbConn, null );
			
			TeamGameView homeGame;
			TeamGameView roadGame;
			Collection   homePlayers;
			Collection   roadPlayers;
			Iterator     i;

			if ( ajax_request ) {

				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append( "<response>" );
				
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
			}
			else {

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
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		if ( ajax_request ) return null;
		
		return mapping.findForward( "success" );
	}

}
