package natc.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import natc.data.Schedule;
import natc.data.Team;
import natc.service.GameService;
import natc.service.TeamService;
import natc.service.impl.GameServiceImpl;
import natc.service.impl.TeamServiceImpl;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BracketAction extends Action {

	public ActionForward execute(
			ActionMapping       mapping,
			ActionForm          form,
			HttpServletRequest  request,
			HttpServletResponse response
	) throws Exception {

		DataSource      dataSource      = null;
		Connection      dbConn          = null;
		TeamService     teamService     = null;
		GameService     gameService     = null;
		Iterator        i               = null;
		Team            team            = null;
		List            round1          = null;
		List            round2          = null;
		List            round3          = null;
		List            round4          = null;
		List            round5          = null;
		List            playoffGameInfo = null; 
		
		try {
			
			if ( (dataSource = getDataSource( request, "NATC_DB" )) == null ) {
				
				throw new Exception( "Cannot get datasource." );
			}
			
			if ( (dbConn = dataSource.getConnection()) == null ) {
			
				throw new Exception( "Cannot get db connection." );
			}
			
			teamService = new TeamServiceImpl( dbConn, "1980" );
			
			round1 = teamService.getPlayoffTeams();
			
			i = round1.iterator();
			
			while ( i.hasNext() ) {
			
				team = (Team)i.next();
				
				if ( team.getPlayoff_rank() >= 2 ) {
				
					if ( round2 == null ) round2 = new ArrayList();
					
					round2.add( team );
				}
			}

			i = round2.iterator();
			
			while ( i.hasNext() ) {
			
				team = (Team)i.next();
				
				if ( team.getPlayoff_rank() >= 3 ) {
				
					if ( round3 == null ) round3 = new ArrayList();
					
					round3.add( team );
				}
			}
			
			i = round3.iterator();
			
			while ( i.hasNext() ) {
			
				team = (Team)i.next();
				
				if ( team.getPlayoff_rank() >= 4 ) {
				
					if ( round4 == null ) round4 = new ArrayList();
					
					round4.add( team );
				}
			}

			i = round4.iterator();
			
			while ( i.hasNext() ) {
			
				team = (Team)i.next();
				
				if ( team.getPlayoff_rank() >= 5 ) {
				
					if ( round5 == null ) round5 = new ArrayList();
					
					round5.add( team );
				}
			}

			request.setAttribute( "round1", round1 );
			request.setAttribute( "round2", round2 );
			request.setAttribute( "round3", round3 );
			request.setAttribute( "round4", round4 );
			request.setAttribute( "round5", round5 );
			
			gameService = new GameServiceImpl( dbConn, "1980" );
			
			if ( (playoffGameInfo = gameService.getPlayoffGameInfo()) != null ) request.setAttribute( "playoffGameInfo", playoffGameInfo );
			
			Schedule schedule = new Schedule();
			
			schedule.setScheduled( new Date() );
			
			request.setAttribute( "schedule", schedule );
		}
		finally {
			
			try { dbConn.close(); } catch ( Exception e ) {}
		}
		
		return mapping.findForward( "success" );
	}

}
