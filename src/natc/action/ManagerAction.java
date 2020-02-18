package natc.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.data.Manager;
import natc.data.Schedule;
import natc.data.Team;
import natc.service.ManagerService;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.ManagerServiceImpl;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManagerAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {

		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		ScheduleService scheduleService = null;
		ManagerService  managerService  = null;
		TeamService     teamService     = null;
		Manager         manager         = null;
		String          manager_id      = null;
		String          year            = null;
		
		if ( (manager_id = (String)request.getParameter( "manager_id" )) == null ) {
			
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
				
				managerService = new ManagerServiceImpl( dbConn, year );
				
				if ( (manager = managerService.getLatestManagerById( Integer.parseInt( manager_id ) )) == null ) {
					
					return mapping.findForward( "not_found" );
				}
			}
			else {
				
				managerService = new ManagerServiceImpl( dbConn, year );
			
				if ( (manager = managerService.getManagerById( Integer.parseInt( manager_id ) )) == null ) {
			
					return mapping.findForward( "not_found" );
				}
			}
			
			request.setAttribute( "manager", manager );
			
			if ( manager.getTeam_id() != 0 ) {
			
				teamService = new TeamServiceImpl( dbConn, year );
				
				Team team        = teamService.getTeamById( manager.getTeam_id() );
				List teamOffense = null;
				List teamDefense = null;
				
				request.setAttribute( "team", team );

				if ( (teamOffense = teamService.getTeamOffenseByTeamId( team.getTeam_id() )) != null ) {
				
					request.setAttribute( "teamOffense", teamOffense );
				}
				
				if ( (teamDefense = teamService.getTeamDefenseByTeamId( team.getTeam_id() )) != null ) {
				
					request.setAttribute( "teamDefense", teamDefense );
				}
			}

			List history;
			
			if ( (history = managerService.getManagerHistoryByManagerId( manager.getManager_id() )) != null ) {
			
				request.setAttribute( "history", history );
			}
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
