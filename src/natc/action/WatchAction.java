package natc.action;

import java.sql.Connection;
import java.util.Collection;

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
import natc.view.StringView;
import natc.view.TeamGameView;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WatchAction extends Action {

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
		GameService     gameService     = null;
		String          game_id_str     = null;
		int             game_id;
		
		if ( (game_id_str = request.getParameter( "game_id" )) == null ) {
		
			return mapping.findForward( "complete" );
		}
		
		game_id = Integer.parseInt( game_id_str );
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			teamService   = new TeamServiceImpl(   dbConn, null );
			playerService = new PlayerServiceImpl( dbConn, null );
			gameService   = new GameServiceImpl(   dbConn, null );

			TeamGameView homeGame;
			TeamGameView roadGame;
			Collection   homePlayers;
			Collection   roadPlayers;

			GameState gameState = gameService.getGameState( game_id );
			
			request.setAttribute( "gameState", gameState );
			
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

			if ( roadGame == null || roadGame.isWin() ||
				 homeGame == null || homeGame.isWin()    ) {
				
				return mapping.findForward( "complete" );
			}
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
