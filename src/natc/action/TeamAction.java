package natc.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import natc.data.Manager;
import natc.data.Player;
import natc.data.Schedule;
import natc.data.ScheduleType;
import natc.data.Team;
import natc.data.TeamGame;
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
		Team            team            = null;
		Manager         manager         = null;
		Schedule        schedule        = null;
		String          team_id         = null;
		String          year            = null;
		
		if ( (team_id = (String)request.getParameter( "team_id" )) == null ) {
		
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

				schedule = scheduleService.getLastScheduleEntry();
				
				year = schedule.getYear();
			}

			teamService = new TeamServiceImpl( dbConn, year );
			
			if ( (team = teamService.getTeamById( Integer.parseInt( team_id ) )) == null ) {
			
				return mapping.findForward( "not_found" );
			}
			
			request.setAttribute( "team", team );
			
			if ( (manager = team.getManager()) != null ) request.setAttribute( "manager", manager );
			
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
				teamPlayerView.setInjured(         player.isInjured()          );
				teamPlayerView.setAward(           player.getAward()           );
				teamPlayerView.setAllstar_team_id( player.getAllstar_team_id() );
				
				if ( team.isAllstar_team() ) {
				
					teamPlayerView.setTeam_id(                                     player.getTeam_id()   );
					teamPlayerView.setTeam_abbrev( teamService.getAbbrevForTeamId( player.getTeam_id() ) );
				}
				
				if ( manager == null ) {
				
					teamPlayerView.setRating( player.getAdjustedPerformanceRating( true, false, false ) ); // 10 is theoretical max - convert to 0-1 ratio
				}
				else {
				
					switch( manager.getStyle() ) {
					
					//                                                                         Age    Conf.  Fatigue
					case Manager.STYLE_OFFENSIVE:
						
						teamPlayerView.setRating(         player.getAdjustedOffensiveRating(   false, false, false ) );
						teamPlayerView.setAdjustedRating( player.getAdjustedOffensiveRating(   true,  true,  false ) );
						break;
						
					case Manager.STYLE_DEFENSIVE:
						
						teamPlayerView.setRating(         player.getAdjustedDefensiveRating(   false, false, false ) );
						teamPlayerView.setAdjustedRating( player.getAdjustedDefensiveRating(   true,  true,  false ) );
						break;
						
					case Manager.STYLE_INTANGIBLE:
						
						teamPlayerView.setRating(         player.getAdjustedIntangibleRating(  false, false, false ) );
						teamPlayerView.setAdjustedRating( player.getAdjustedIntangibleRating(  true,  true,  false ) );
						break;
						
					case Manager.STYLE_PENALTY:
						
						teamPlayerView.setRating(         player.getAdjustedPenaltyRating(     false, false, false ) );
						teamPlayerView.setAdjustedRating( player.getAdjustedPenaltyRating(     true,  true,  false ) );
						break;
						
					case Manager.STYLE_BALANCED:
						
						teamPlayerView.setRating(         player.getAdjustedPerformanceRating( false, false, false ) );
						teamPlayerView.setAdjustedRating( player.getAdjustedPerformanceRating( true,  true,  false ) );
						break;
					}
				}
				
				if ( schedule != null ) {
				
					switch ( schedule.getType().getValue() ) {
					
					case ScheduleType.PRESEASON:      
					case ScheduleType.END_OF_PRESEASON: 
					case ScheduleType.ROSTER_CUT:              teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_Preseason     ); break;
					
					case ScheduleType.REGULAR_SEASON:
					case ScheduleType.END_OF_REGULAR_SEASON:
					case ScheduleType.AWARDS:                  teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_RegularSeason ); break;
					
					case ScheduleType.POSTSEASON:
					case ScheduleType.DIVISION_PLAYOFF:
					case ScheduleType.DIVISION_CHAMPIONSHIP:
					case ScheduleType.CONFERENCE_CHAMPIONSHIP:
					case ScheduleType.NATC_CHAMPIONSHIP:
					case ScheduleType.END_OF_POSTSEASON:       teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_Postseason    ); break;
					
					case ScheduleType.ALL_STARS:
					case ScheduleType.ALL_STAR_DAY_1:
					case ScheduleType.ALL_STAR_DAY_2:
					case ScheduleType.END_OF_ALLSTAR_GAMES:    teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_Allstar       ); break;
					
					default:                                   teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_RegularSeason ); break;
					}
				}
				else {
				
					teamService.getTeamPlayerData( teamPlayerView, TeamGame.gt_RegularSeason );
				}
				
				teamPlayers.add( teamPlayerView );
			}
			
			// Sort players by time per game
			Collections.sort( teamPlayers, new Comparator() {
				
				public int compare( Object arg1, Object arg2 ) {
					
					TeamPlayerView tp1 = (TeamPlayerView)arg1;
					TeamPlayerView tp2 = (TeamPlayerView)arg2;
					
					if ( (tp1.getTime_per_game() + tp2.getTime_per_game()) == 0 ) {
					
						return (tp1.getRating() > tp2.getRating()) ? 1 : -1;
					}
					
					if ( tp1.getPoints() == tp2.getPoints() ) {
					
					if ( tp1.getTime_per_game() == tp2.getTime_per_game() ) return (tp1.getGames() > tp2.getGames()) ? 1 : -1;
					
					return (tp1.getTime_per_game() > tp2.getTime_per_game()) ? 1 : -1;
					}
					
					return (tp1.getPoints() > tp2.getPoints()) ? 1 : -1;
				}
			});
			Collections.reverse( teamPlayers );
			
			request.setAttribute( "teamPlayers", teamPlayers );
			
			List injuries;
			
			if ( (injuries = teamService.getTeamInjuriesByTeamId( team.getTeam_id() )) != null ) {
			
				request.setAttribute( "injuries", injuries );
			}
			
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
