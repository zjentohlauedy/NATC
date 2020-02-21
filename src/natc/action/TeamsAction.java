package natc.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.data.Schedule;
import natc.service.ScheduleService;
import natc.service.TeamService;
import natc.service.impl.ScheduleServiceImpl;
import natc.service.impl.TeamServiceImpl;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TeamsAction extends Action {

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
		List            teamList        = null;
		
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
			
			teamList = teamService.getTeamList();
			
			request.setAttribute( "teamList", teamList );
		}
		finally {
		
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
