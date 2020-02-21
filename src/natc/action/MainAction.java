package natc.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.data.Schedule;
import natc.service.GameService;
import natc.service.ScheduleService;
import natc.service.impl.GameServiceImpl;
import natc.service.impl.ScheduleServiceImpl;

public class MainAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		ActionForward forward  = null;
        String        nextPage = null;
        
        // If the page is not specified, default to the main menu
        if ( (nextPage = request.getParameter("page")) == null ) {
            
            return mapping.findForward("main");
        }
        
        // If the page specified is invalid, forward to the under construction page
        if ( (forward = mapping.findForward(nextPage)) == null ) {
         
            return mapping.findForward("error");
        }
        
        if ( forward.getName().equals( "games" ) ) {
        
        	DataSource      dataSource      = null;
    		Connection      dbConn          = null;
    		ScheduleService scheduleService = null;
    		GameService     gameService     = null;
    		Collection      teams           = null;
    		
    		try {
    			
    			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
    				
    				throw new Exception( "Cannot get datasource." );
    			}
    			
    			if ( (dbConn = dataSource.getConnection()) == null ) {
    			
    				throw new Exception( "Cannot get db connection." );
    			}
    			
    			scheduleService = new ScheduleServiceImpl( dbConn, null );
    			
    			Schedule schedule = null;

    			if ( (schedule = scheduleService.getLastScheduleEntry()) != null ) {

    				gameService = new GameServiceImpl( dbConn, schedule.getYear() );

    				teams = gameService.getRankedTeamsByDivision( 0 ); request.setAttribute( "div0teams", teams );
    				teams = gameService.getRankedTeamsByDivision( 1 ); request.setAttribute( "div1teams", teams );
    				teams = gameService.getRankedTeamsByDivision( 2 ); request.setAttribute( "div2teams", teams );
    				teams = gameService.getRankedTeamsByDivision( 3 ); request.setAttribute( "div3teams", teams );
    			}
    		}
    		finally {
    		
    			try { dbConn.close(); } catch ( Exception e ) {}
    		}
        }
        
        // Forward to the page specified
        return forward;
	}

}
