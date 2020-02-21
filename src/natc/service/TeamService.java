package natc.service;

import java.sql.SQLException;
import java.util.List;

import natc.data.Team;
import natc.data.TeamGame;
import natc.view.TeamGameView;
import natc.view.TeamPlayerView;

public interface TeamService {

	public void         generateTeams() throws SQLException;
	public void         updateTeamsForNewSeason( String last_year ) throws SQLException;
	public void         updateTeam( Team team ) throws SQLException;
	public void         insertTeamGame( TeamGame teamGame ) throws SQLException;
	public void         updateTeamGame( TeamGame teamGame ) throws SQLException;
	public void         updateTeamStats( Team team, Team opponent, int type ) throws SQLException;
	public List         getTeamList() throws SQLException;
	public List         getAllstarTeamList() throws SQLException;
	public Team         getTeamById( int team_id ) throws SQLException;
	public List         getTeamsByDivision( int division ) throws SQLException;
	public List         getTeamsByPlayoffRank( int rank ) throws SQLException;
	public List         getPlayoffTeams() throws SQLException;
	public List         getTeamOffenseByTeamId( int team_id ) throws SQLException;
	public List         getTeamDefenseByTeamId( int team_id ) throws SQLException;
	public List         getTeamHistoryByTeamId( int team_id ) throws SQLException;
	public TeamGameView getHomeGame( int game_id ) throws SQLException;
	public TeamGameView getRoadGame( int game_id ) throws SQLException;
	public int[]        getAllstarTeamIds() throws SQLException;
	public int[]        getVsDivisionWins( int division1, int division2 ) throws SQLException;
	public int[]        getVsConferenceWins( int conference1, int conference2 ) throws SQLException;
	public void         getTeamPlayerData( TeamPlayerView teamPlayer, int gameType ) throws SQLException;
	public void         updateExpectations() throws SQLException;
	public List         getTeamInjuriesByTeamId( int team_id ) throws SQLException;
	public String       getAbbrevForTeamId( int team_id ) throws SQLException;
}
