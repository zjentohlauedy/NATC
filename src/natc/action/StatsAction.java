package natc.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.form.StatsForm;
import natc.service.StatsService;
import natc.service.impl.StatsServiceImpl;

public class StatsAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {
		
		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		StatsService    statsService    = null;
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			statsService = new StatsServiceImpl( dbConn );
			
			Collection c = null;
			
			StatsForm statsForm = (StatsForm)form;
			
			switch ( statsForm.getOperation().intValue() ) {
			
			case  0: c = statsService.getTopPlayersThisSeason();       break;
			case  1: c = statsService.getTopTeamsOffenseThisSeason();  break;
			case  2: c = statsService.getTopTeamsDefenseThisSeason();  break;
			case  3: c = statsService.getTopPlayersByGame();           break;
			case  4: c = statsService.getTopPlayersBySeason();         break;
			case  5: c = statsService.getTopPlayersByCareer();         break;
			case  6: c = statsService.getTopTeamsByGame();             break;
			case  7: c = statsService.getTopTeamsOffenseBySeason();    break;
			case  8: c = statsService.getTopTeamsDefenseBySeason();    break;
			case  9: c = statsService.getTopTeamsOffenseByHistory();   break;
			case 10: c = statsService.getTopTeamsDefenseByHistory();   break;
			}
			
			if ( c != null ) request.setAttribute( "stats" , c );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
