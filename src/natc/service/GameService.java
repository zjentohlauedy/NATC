package natc.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import natc.data.Schedule;
import natc.view.GameView;

public interface GameService {

	public void     initializeDatabase() throws SQLException;
	public void     startNewSeason( String lastYear ) throws SQLException;
	public void     processScheduleEvent( Schedule event ) throws SQLException;
	public List     getGamesByDate( Date datestamp ) throws SQLException;
	public List     getGamesByTeamId( int team_id ) throws SQLException;
	public List     getGamesByTeamIdAndType( int team_id, int type ) throws SQLException;
	public List     getRankedTeamsByDivision( int division ) throws SQLException;
	public List     getRankedTeamList() throws SQLException;
	public List     getRankedRookieList() throws SQLException;
	public List     getReleasedPlayers() throws SQLException;
	public List     getFreeAgentMoves() throws SQLException;
	public List     getRetiredPlayersByTeam() throws SQLException;
	public List     getRetiredPlayersWithoutTeam() throws SQLException;
	public List     getInjuriesByDate( Date datestamp ) throws SQLException;
	public List     getRankedAllstarTeams() throws SQLException;
	public GameView getChampionshipGame() throws SQLException;
	public List     getChampionships() throws SQLException;
	public List     getPlayoffGameInfo() throws SQLException;
}
