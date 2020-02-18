package natc.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.service.GameService;
import natc.service.impl.GameServiceImpl;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DraftAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		DataSource      dataSource   = null;
		Connection      dbConn       = null;
		GameService     gameService1 = null;
		GameService     gameService2 = null;
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			gameService1 = new GameServiceImpl( dbConn, "2008" );
			
			Collection teams = gameService1.getRankedTeamList();
			
			if ( teams == null ) throw new Exception( "No teams found." );
			
			request.setAttribute( "teams", teams );
			
			gameService2 = new GameServiceImpl( dbConn, "2009" );
			
			Collection players = gameService2.getRankedRookieList();
			
			if ( players == null ) throw new Exception( "No players found." );
			
			request.setAttribute( "players", players );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}
}
