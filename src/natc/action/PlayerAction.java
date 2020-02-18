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
import natc.service.PlayerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;

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
		String          player_id       = null;
		
		if ( (player_id = (String)request.getParameter( "player_id" )) == null ) {
			
			return mapping.findForward( "success" );
		}
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			scheduleService = new ScheduleServiceImpl( dbConn, null );
			
			Schedule schedule = scheduleService.getLastScheduleEntry();
			
			playerService = new PlayerServiceImpl( dbConn, schedule.getYear() );
			
			Player player = playerService.getLatestPlayerById( Integer.parseInt( player_id ) );
			
			// For this action we want to see what the player's in-game ratings are as well as potential ratings
			player.setIn_game( true );
			
			request.setAttribute( "player", player );
			
			// If the player is on a team, get the team object
			Team team = null;
			
			if ( player.getTeam_id() != 0 ) {
			
				teamService = new TeamServiceImpl( dbConn, schedule.getYear() );
				
				team = teamService.getTeamById( player.getTeam_id() );
				
				request.setAttribute( "team", team );
			}
			
			List playerStats;
			
			if ( (playerStats = playerService.getPlayerStatsSumByPlayerId( player.getPlayer_id() )) != null ) {
			
				request.setAttribute( "playerStats", playerStats );
			}
			
			List history;
			
			if ( (history = playerService.getPlayerHistoryById( player.getPlayer_id() )) != null ) {
			
				request.setAttribute( "history", history );
			}
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
