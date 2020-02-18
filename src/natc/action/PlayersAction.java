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

import natc.service.PlayerService;
import natc.service.impl.PlayerServiceImpl;

public class PlayersAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
    	DataSource      dataSource      = null;
		Connection      dbConn          = null;
		PlayerService   playerService   = null;
		String          group           = null;
		
		if ( (group = (String)request.getParameter( "group" )) == null ) {
		
			group = "A";
		}
		
		group = group.toUpperCase();
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			playerService = new PlayerServiceImpl( dbConn, null );
			
			List players;
			List letters;
			
			if ( (letters = playerService.getPlayerLetters()) != null ) {
			
				request.setAttribute( "letters", letters );
			}
			
			if ( (players = playerService.getPlayersByLetter( group )) != null ) {
			
				request.setAttribute( "players", players );
			}
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}
}
