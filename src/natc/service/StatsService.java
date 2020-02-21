package natc.service;

import java.sql.SQLException;
import java.util.Collection;

public interface StatsService {

	public Collection getTopPlayersThisSeason() throws SQLException;
	public Collection getTopTeamsOffenseThisSeason()  throws SQLException;
	public Collection getTopTeamsDefenseThisSeason()  throws SQLException;
	public Collection getTopPlayersByGame() throws SQLException;
	public Collection getTopPlayersBySeason() throws SQLException;
	public Collection getTopPlayersByCareer() throws SQLException;
	public Collection getTopTeamsByGame() throws SQLException;
	public Collection getTopTeamsOffenseBySeason()  throws SQLException;
	public Collection getTopTeamsDefenseBySeason()  throws SQLException;
	public Collection getTopTeamsOffenseByHistory()  throws SQLException;
	public Collection getTopTeamsDefenseByHistory()  throws SQLException;
}
