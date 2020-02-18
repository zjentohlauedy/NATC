package natc.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import natc.data.Schedule;

public interface GameService {

	public void initializeDatabase() throws SQLException;
	public void startNewSeason( String lastYear ) throws SQLException;
	public void processScheduleEvent( Schedule event ) throws SQLException;
	public List getGamesByDate( Date datestamp ) throws SQLException;
	public List getGamesByTeamId( int team_id ) throws SQLException;
	public List getRankedTeamsByDivision( int division ) throws SQLException;
	public List getRankedTeamList() throws SQLException;
	public List getRankedRookieList() throws SQLException;
	public List getDraftPicks( int start_pick ) throws SQLException;
	public List getStandoutPlayers() throws SQLException;
	public List getMostImprovedPlayers() throws SQLException;
	public List getStandoutRookies() throws SQLException;
	public List getReleasedVeteranPlayers() throws SQLException;
	public List getReleasedRookiePlayers() throws SQLException;
	public List getResignedPlayers() throws SQLException;
	public List getRetiredPlayers() throws SQLException;
	public List getAbandonedRookiePlayers() throws SQLException;
}
