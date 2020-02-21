package natc.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.service.ManagerService;
import natc.service.PlayerService;
import natc.service.TeamService;
import natc.service.impl.ManagerServiceImpl;
import natc.service.impl.PlayerServiceImpl;
import natc.service.impl.TeamServiceImpl;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AllstarAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {

		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}

			TeamService    teamService    = new TeamServiceImpl(    dbConn, "1980" );
			ManagerService managerService = new ManagerServiceImpl( dbConn, "1980" );
			PlayerService  playerService  = new PlayerServiceImpl(  dbConn, "1980" );
			
			Collection data    = null;
			
			int allstarTeamIds[] = teamService.getAllstarTeamIds();
			
			data = playerService.getAllstarsByTeamId( allstarTeamIds[0] ); request.setAttribute( "div0stars", data );
			data = playerService.getAllstarsByTeamId( allstarTeamIds[1] ); request.setAttribute( "div1stars", data );
			data = playerService.getAllstarsByTeamId( allstarTeamIds[2] ); request.setAttribute( "div2stars", data );
			data = playerService.getAllstarsByTeamId( allstarTeamIds[3] ); request.setAttribute( "div3stars", data );
			
			request.setAttribute( "manager0", managerService.getAllstarManagerByTeamId( allstarTeamIds[0] ) );
			request.setAttribute( "manager1", managerService.getAllstarManagerByTeamId( allstarTeamIds[1] ) );
			request.setAttribute( "manager2", managerService.getAllstarManagerByTeamId( allstarTeamIds[2] ) );
			request.setAttribute( "manager3", managerService.getAllstarManagerByTeamId( allstarTeamIds[3] ) );
			
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
