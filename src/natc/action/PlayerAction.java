package natc.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.data.Player;
import natc.data.Schedule;
import natc.data.Team;
import natc.data.TeamGame;
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.RookieInfoView;

public class PlayerAction extends Action {

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
		ScheduleService scheduleService = null;
		Player          player          = null;
		String          player_id       = null;
		String          year            = null;
		
		if ( (player_id = (String)request.getParameter( "player_id" )) == null ) {
			
			return mapping.findForward( "not_found" );
		}
		
		year = (String)request.getParameter( "year" );
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}

			if ( year == null ) {

				scheduleService = new ScheduleServiceImpl( dbConn, null );

				Schedule schedule = scheduleService.getLastScheduleEntry();

				year = schedule.getYear();
				
				playerService = new PlayerServiceImpl( dbConn, year );

				if ( (player = playerService.getLatestPlayerById( Integer.parseInt( player_id ) )) == null ) {
				
					return mapping.findForward( "not_found" );
				}
			}
			else {
			
				playerService = new PlayerServiceImpl( dbConn, year );
				
				if ( (player = playerService.getPlayerById( Integer.parseInt( player_id ) )) == null ) {
				
					return mapping.findForward( "not_found" );
				}
			}

			// For this action we want to see what the player's in-game ratings are as well as potential ratings
			player.setIn_game( true );
			
			request.setAttribute( "player", player );
			
			if ( player.getTeam_id() != 0 ) {
			
				teamService = new TeamServiceImpl( dbConn, year );
				
				Team team = teamService.getTeamById( player.getTeam_id() );
				
				request.setAttribute( "team", team );
			}
			
			RookieInfoView rookieInfoView;
			
			if ( (rookieInfoView = playerService.getRookieInfo( player.getPlayer_id() )) != null ) request.setAttribute( "rookieInfoView", rookieInfoView );
			
			List playerStats;
			
			if ( (playerStats = playerService.getPlayerStatsSumByPlayerId( player.getPlayer_id() )) != null ) {
			
				request.setAttribute( "playerStats", playerStats );
			}
			
			List injuries;
			
			if ( (injuries = playerService.getPlayerInjuriesById(      player.getPlayer_id() )) != null ) request.setAttribute( "injuries",       injuries );
			if ( (injuries = playerService.getPlayerInjuryHistoryById( player.getPlayer_id() )) != null ) request.setAttribute( "injury_history", injuries );
			
			List history;
			
			if ( (history = playerService.getPlayerHistoryByIdAndType( player.getPlayer_id(), TeamGame.gt_Preseason     )) != null ) request.setAttribute( "preseason",  history );
			if ( (history = playerService.getPlayerHistoryByIdAndType( player.getPlayer_id(), TeamGame.gt_RegularSeason )) != null ) request.setAttribute( "regseason",  history );
			if ( (history = playerService.getPlayerHistoryByIdAndType( player.getPlayer_id(), TeamGame.gt_Postseason    )) != null ) request.setAttribute( "postseason", history );
			if ( (history = playerService.getPlayerHistoryByIdAndType( player.getPlayer_id(), TeamGame.gt_Allstar       )) != null ) request.setAttribute( "allstar",    history );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
