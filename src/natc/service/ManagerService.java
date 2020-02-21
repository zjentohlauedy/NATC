package natc.service;

import java.sql.SQLException;
import java.util.List;

import natc.data.Manager;
import natc.data.Player;
import natc.view.ManagerAllstarView;
import natc.view.ManagerAwardsView;

public interface ManagerService {

	public Manager            generateManager( int age ) throws SQLException;
	public void               generateManagers() throws SQLException;
	public Manager            generateManagerFromPlayer( Player player ) throws SQLException;
	public int                getManagerCount() throws SQLException;
	public Manager            getManagerById( int manager_id ) throws SQLException;
	public Manager            getLatestManagerById( int manager_id ) throws SQLException;
	public Manager            getManagerByTeamId( int team_id ) throws SQLException;
	public Manager            getManagerByAllstarTeamId( int team_id ) throws SQLException;
	public ManagerAllstarView getAllstarManagerByTeamId( int team_id ) throws SQLException;
	public List               getFreeManagers() throws SQLException;
	public List               getManagerMovesByTeam() throws SQLException;
	public void               updateManager( Manager manager ) throws SQLException;
	public void               updateAllstarTeamId( int manager_id, int allstar_team_id ) throws SQLException;
	public List               getManagerHistoryByManagerId( int manager_id ) throws SQLException;
	public void               updateSeasons() throws SQLException;
	public void               updateManagersForNewSeason( String last_year ) throws SQLException;
	public void               ageManagers() throws SQLException;
	public void               updateScore() throws SQLException;
	public void               selectManagerOfTheYear() throws SQLException;
	public ManagerAwardsView  getManagerOfTheYear() throws SQLException;
	public Manager            getBestManagerByDivision( int division ) throws SQLException;
}
