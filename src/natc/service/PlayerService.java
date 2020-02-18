package natc.service;

import java.sql.SQLException;
import java.util.List;

import natc.data.Player;
import natc.data.PlayerGame;

public interface PlayerService {

	public Player generatePlayer( boolean is_on_team, int team_id ) throws SQLException;
	public void   updatePlayersForNewSeason( String last_year ) throws SQLException;
	public void   updatePlayer( Player player, boolean is_on_team, boolean is_released ) throws SQLException;
	public void   insertPlayerGame( PlayerGame playerGame ) throws SQLException;
	public void   updatePlayerStats( Player player, int type ) throws SQLException;
	public Player getPlayerById( int player_id ) throws SQLException;
	public Player getLatestPlayerById( int player_id ) throws SQLException;
	public List   getPlayerHistoryById( int player_id ) throws SQLException;
	public List   getPlayersByTeamId( int team_id ) throws SQLException;
	public List   getPlayersByAllstarTeamId( int team_id ) throws SQLException;
	public List   getPlayersByLetter( String letter ) throws SQLException;
	public List   getPlayerLetters() throws SQLException;
	public List   getFreePlayers() throws SQLException;
	public List   getPlayerList() throws SQLException;
	public List   getRookiePlayerList() throws SQLException;
	public void   ageUndraftedRookies() throws SQLException;
	public void   retirePlayers() throws SQLException;
	public List   getPlayerStatsSumByPlayerId( int player_id ) throws SQLException;
	public List   getPlayerScores( int count ) throws SQLException;
	public List   getPlayerAwards() throws SQLException;
	public List   getPlayerGamesForTeamByGame( int game_id, int team_id ) throws SQLException;
	public void   updateSeasonsPlayed() throws SQLException;
	public void   updateAllstarTeamId( int player_id, int team_id ) throws SQLException;
	public List   getAllstarsByTeamId( int division ) throws SQLException;
	public int    selectAllstarForTeam( int team_id ) throws SQLException;
}
