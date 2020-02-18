package natc.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
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
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;
import natc.view.TeamPlayerView;

public class TeamAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		ScheduleService scheduleService = null;
		TeamService     teamService     = null;
		String          team_id         = null; 
		
		if ( (team_id = (String)request.getParameter( "team_id" )) == null ) {
		
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
			
			teamService = new TeamServiceImpl( dbConn, schedule.getYear() );
			
			Team team = teamService.getTeamById( Integer.parseInt( team_id ) );
			
			request.setAttribute( "team", team );
			
			List teamOffense;
			List teamDefense;
			
			if ( (teamOffense = teamService.getTeamOffenseByTeamId( team.getTeam_id() )) != null ) {
			
				request.setAttribute( "teamOffense", teamOffense );
			}
			
			if ( (teamDefense = teamService.getTeamDefenseByTeamId( team.getTeam_id() )) != null ) {
			
				request.setAttribute( "teamDefense", teamDefense );
			}
			
			List teamPlayers = new ArrayList();
			
			Iterator i = team.getPlayers().iterator();
			
			while ( i.hasNext() ) {
			
				Player player = (Player)i.next();
				
				TeamPlayerView teamPlayerView = new TeamPlayerView();
				
				teamPlayerView.setPlayer_id(       player.getPlayer_id()       );
				teamPlayerView.setFirst_name(      player.getFirst_name()      );
				teamPlayerView.setLast_name(       player.getLast_name()       );
				teamPlayerView.setAge(             player.getAge()             );
				teamPlayerView.setRookie(          player.isRookie()           );
				teamPlayerView.setAward(           player.getAward()           );
				teamPlayerView.setAllstar_team_id( player.getAllstar_team_id() );
				teamPlayerView.setRating(          player.getRating()          );
				
				teamService.getTeamPlayerData( teamPlayerView );
				
				teamPlayers.add( teamPlayerView );
			}
			
			request.setAttribute( "teamPlayers", teamPlayers );
			
			List history;
			
			if ( (history = teamService.getTeamHistoryByTeamId( team.getTeam_id() )) != null ) {
			
				request.setAttribute( "history", history );
			}
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
