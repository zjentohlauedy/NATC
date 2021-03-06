package natc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImpl {

	/*
	 * SCHEDULE QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public static PreparedStatement getLastScheduleEntrySelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Year, Sequence, Type, Data, Scheduled, Status FROM Schedule_T WHERE Status = ? ORDER BY Scheduled DESC LIMIT 1";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getScheduleEntrySelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT Year, Sequence, Type, Data, Scheduled, Status FROM Schedule_T WHERE Year = ? AND Sequence = ? LIMIT 1";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getScheduleEntriesByTypeSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Year, Sequence, Type, Data, Scheduled, Status FROM Schedule_T WHERE Year = ? AND Type = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getScheduleInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Schedule_T ( Year, Sequence, Type, Data, Scheduled, Status ) VALUES ( ?, ?, ?, ?, ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getScheduleUpdatePs( Connection dbConn ) throws SQLException {
		
		String sql = "UPDATE Schedule_T "
			/**/
			/**/   + "   SET Type      = ?, "
			/**/   + "       Data      = ?, "
			/**/   + "       Scheduled = ?, "
			/**/   + "       Status    = ?  "
			/**/
			/**/   + " WHERE Year     = ? "
			/**/   + "   AND Sequence = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getScheduleCompleteUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Schedule_T SET Status = ? WHERE Year = ? AND Sequence = ?";
		
		return dbConn.prepareStatement( sql );
	}

	
	/*
	 * TEAM QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private static final String TEAMS_T_COLUMNS = "Team_Id,          "
		/**/                                    + "Year,             "
		/**/                                    + "Location,         "
		/**/                                    + "Name,             "
		/**/                                    + "Abbrev,           "
		/**/                                    + "Time_Zone,        "
		/**/                                    + "Game_Time,        "
		/**/                                    + "Conference,       "
		/**/                                    + "Division,         "
		/**/                                    + "Allstar_Team,     "
		/**/                                    + "Preseason_Games,  "
		/**/                                    + "Preseason_Wins,   "
		/**/                                    + "Preseason_Losses, "
		/**/                                    + "Games,            "
		/**/                                    + "Wins,             "
		/**/                                    + "Losses,           "
		/**/                                    + "Div_Wins,         "
		/**/                                    + "Div_Losses,       "
		/**/                                    + "Ooc_Wins,         "
		/**/                                    + "Ooc_Losses,       "
		/**/                                    + "Ot_Wins,          "
		/**/                                    + "Ot_Losses,        "
		/**/                                    + "Road_Wins,        "
		/**/                                    + "Road_Losses,      "
		/**/                                    + "Home_Wins,        "
		/**/                                    + "Home_Losses,      "
		/**/                                    + "Division_Rank,    "
		/**/                                    + "Playoff_rank,     "
		/**/                                    + "Playoff_Games,    "
		/**/                                    + "Round1_Wins,      "
		/**/                                    + "Round2_Wins,      "
		/**/                                    + "Round3_Wins,      "
		/**/                                    + "Expectation,      "
		/**/                                    + "Drought           ";

	public static PreparedStatement getTeamListSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year         = ? "
			/**/   + "  AND Allstar_Team = ? "
			/**/   + "ORDER BY Location, Name ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarTeamListSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year         = ? "
			/**/   + "  AND Allstar_Team = ? "
			/**/   + "ORDER BY Location, Name ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamByIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTeamsByDivisionSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year         = ? "
			/**/   + "AND   Division     = ? "
			/**/   + "AND   Allstar_Team = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamHistorySelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Team_Id = ? "
			/**/   + "ORDER BY Year";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTeamsByPlayoffRankSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year         = ? "
			/**/   + "AND   Playoff_Rank = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayoffTeamsSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + TEAMS_T_COLUMNS
			/**/
			/**/   + "FROM Teams_T "
			/**/
			/**/   + "WHERE Year         = ? "
			/**/   + "AND   Playoff_Rank > 0 ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAbbrevForTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Abbrev FROM Teams_T WHERE Year = ? AND Team_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarTeamIdsSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Team_Id FROM Teams_T WHERE Year = ? AND Allstar_Team = ? ORDER BY Division";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Teams_T ( Team_Id, Year, Location, Name, Abbrev, Time_Zone, Game_Time, Conference, Division, Allstar_Team ) " +
		/**/         "             VALUES (       ?,    ?,        ?,    ?,      ?,         ?,         ?,          ?,        ?,            ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamUpdatePs( Connection dbConn ) throws SQLException {
		
		String sql = "UPDATE Teams_T "
			/**/
			/**/   + "SET Location         = ?, "
			/**/   + "    Name             = ?, "
			/**/   + "    Abbrev           = ?, "
			/**/   + "    Time_Zone        = ?, "
			/**/   + "    Game_Time        = ?, "
			/**/   + "    Conference       = ?, "
			/**/   + "    Division         = ?, "
			/**/   + "    Allstar_Team     = ?, "
			/**/   + "    Preseason_Games  = ?, "
			/**/   + "    Preseason_Wins   = ?, "
			/**/   + "    Preseason_Losses = ?, "
			/**/   + "    Games            = ?, "
			/**/   + "    Wins             = ?, "
			/**/   + "    Losses           = ?, "
			/**/   + "    Div_Wins         = ?, "
			/**/   + "    Div_Losses       = ?, "
			/**/   + "    Ooc_Wins         = ?, "
			/**/   + "    Ooc_Losses       = ?, "
			/**/   + "    Ot_Wins          = ?, "
			/**/   + "    Ot_Losses        = ?, "
			/**/   + "    Road_Wins        = ?, "
			/**/   + "    Road_Losses      = ?, "
			/**/   + "    Home_Wins        = ?, "
			/**/   + "    Home_Losses      = ?, "
			/**/   + "    Division_Rank    = ?, "
			/**/   + "    Playoff_Rank     = ?, "
			/**/   + "    Playoff_Games    = ?, "
			/**/   + "    Round1_Wins      = ?, "
			/**/   + "    Round2_Wins      = ?, "
			/**/   + "    Round3_Wins      = ?, "
			/**/   + "    Expectation      = ?, "
			/**/   + "    Drought          = ?  "
			/**/
			/**/   + "WHERE  Year    = ? "
			/**/   + "AND    Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}
	
	
	/*
	 * MANAGER QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	

	private static final String MANAGERS_T_COLUMNS = "Manager_Id,      "
		/**/                                       + "Team_Id,         "
		/**/                                       + "Player_Id,       "
		/**/                                       + "Year,            "
		/**/                                       + "First_Name,      "
		/**/                                       + "Last_Name,       "
		/**/                                       + "Age,             "
		/**/                                       + "Offense,         "
		/**/                                       + "Defense,         "
		/**/                                       + "Intangible,      "
		/**/                                       + "Penalties,       "
		/**/                                       + "Vitality,        "
		/**/                                       + "Style,           "
		/**/                                       + "New_Hire,        "
		/**/                                       + "Released,        "
		/**/                                       + "Retired,         "
		/**/                                       + "Former_Team_Id,  "
	    /**/                                       + "Allstar_Team_Id, "
	    /**/                                       + "Award,           "
	    /**/                                       + "Seasons,         "
	    /**/                                       + "Score,           "
	    /**/                                       + "Total_Seasons,   "
	    /**/                                       + "Total_Score      ";

	public static PreparedStatement getNextManagerIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MAX( Manager_Id ) FROM Managers_T";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerCountSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT COUNT(1) FROM Managers_T WHERE Year = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getLatestYearForManager( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MAX( Year ) FROM Managers_T WHERE Manager_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + MANAGERS_T_COLUMNS
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year       = ? "
			/**/   + "AND   Manager_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + MANAGERS_T_COLUMNS
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByAllstarTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + MANAGERS_T_COLUMNS
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year            = ? "
			/**/   + "AND   Allstar_Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarManagerByTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT M.Manager_Id, "
			/**/   +        "M.First_Name, "
			/**/   +        "M.Last_Name,  "
			/**/   +        "M.Award,      "
			/**/   +        "T.Team_Id,    "
			/**/   +        "T.Abbrev,     "
			/**/   +        "T.Wins,       "
			/**/   +        "T.Losses,     "
			/**/   +        "O.Score,      "
			/**/   +        "O.Attempts,   "
			/**/   +        "O.Goals,      "
			/**/   +        "O.Psa,        "
			/**/   +        "O.Psm         "
			/**/
			/**/   + "FROM Managers_T M, Teams_T T, Team_Offense_Sum_T O "
			/**/
			/**/   + "WHERE M.Year            =  T.Year    "
			/**/   + "AND   M.Team_Id         =  T.Team_Id "
			/**/   + "AND   T.Year            =  O.Year    "
			/**/   + "AND   T.Team_Id         =  O.Team_Id "
			/**/   + "AND   M.Year            =  ?         "
			/**/   + "AND   O.Type            =  ?         "
			/**/   + "AND   M.Allstar_Team_Id =  ?         "
			/**/
			/**/   + "ORDER BY M.Last_Name, M.First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getFreeManagersSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + MANAGERS_T_COLUMNS
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year    =  ?    "
			/**/   + "AND   Team_Id IS NULL "
			/**/   + "AND   Retired =  ?    "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRetiredManagersByTeamSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT Manager_Id,    "
			/**/   +        "First_Name,    "
			/**/   +        "Last_Name,     "
			/**/   +        "Total_Seasons, "
			/**/   +        "Offense,       "
			/**/   +        "Defense,       "
			/**/   +        "Intangible,    "
			/**/   +        "Penalties,     "
			/**/   +        "Style          "
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year           =  ? "
			/**/   + "AND   Former_Team_Id =  ? "
			/**/   + "AND   Retired        =  ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRetiredManagersWithoutTeamSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT Manager_Id,    "
			/**/   +        "First_Name,    "
			/**/   +        "Last_Name,     "
			/**/   +        "Total_Seasons, "
			/**/   +        "Offense,       "
			/**/   +        "Defense,       "
			/**/   +        "Intangible,    "
			/**/   +        "Penalties,     "
			/**/   +        "Style          "
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Total_Seasons  >  0    "
			/**/   + "AND   Year           =  ?    "
			/**/   + "AND   Former_Team_Id IS NULL "
			/**/   + "AND   Retired        =  ?    "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getFiredManagersByTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Manager_Id,    "
			/**/   +        "First_Name,    "
			/**/   +        "Last_Name,     "
			/**/   +        "Offense,       "
			/**/   +        "Defense,       "
			/**/   +        "Intangible,    "
			/**/   +        "Penalties,     "
			/**/   +        "Style          "
			/**/
			/**/   + "FROM Managers_T "
			/**/
			/**/   + "WHERE Year           =  ? "
			/**/   + "AND   Former_Team_Id =  ? "
			/**/   + "AND   Released       =  ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getHiredManagersByTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT M.Manager_Id,    "
			/**/   +        "M.First_Name,    "
			/**/   +        "M.Last_Name,     "
			/**/   +        "M.Total_Seasons, "
			/**/   +        "M.Offense,       "
			/**/   +        "M.Defense,       "
			/**/   +        "M.Intangible,    "
			/**/   +        "M.Penalties,     "
			/**/   +        "M.Style,         "
			/**/   +        "T.Team_Id,       "
			/**/   +        "T.Abbrev         "
			/**/
			/**/   + "FROM Managers_T M LEFT JOIN Teams_T T "
			/**/   + "ON ( M.Year = T.Year AND M.Former_Team_Id = T.Team_Id ) "
			/**/
			/**/   + "WHERE M.Year        =  ?         "
			/**/   + "AND   M.Team_Id     =  ?         "
			/**/   + "AND   M.New_Hire    =  ?         "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerCareerWinPctSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT SUM(T.Wins)/SUM(T.Wins + T.Losses) "
			/**/
			/**/   + "  FROM Teams_T T, Managers_T M "
			/**/
			/**/   + " WHERE T.Year       =      M.Year    "
			/**/   + "   AND T.Team_Id    =      M.Team_Id "
			/**/   + "   AND M.Team_Id    IS NOT NULL      "
			/**/   + "   AND M.Manager_Id =      ?         ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerTeamWinPctSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT COUNT(1), SUM(T.Wins)/SUM(T.Wins + T.Losses) "
			/**/
			/**/   + "  FROM Teams_T T, Managers_T M "
			/**/
			/**/   + " WHERE T.Year       = M.Year    "
			/**/   + "   AND T.Team_Id    = M.Team_Id "
			/**/   + "   AND M.Team_Id    = ?         "
			/**/   + "   AND M.Manager_Id = ?         ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAwardCountByManagerIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT COUNT(1) FROM Managers_T WHERE Manager_Id = ? AND Award = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarCountByManagerIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT COUNT(1) FROM Managers_T WHERE Manager_Id = ? AND Allstar_Team_Id IS NOT NULL";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerHistoryByIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT T.Team_Id, T.Abbrev, M.Year, T.Games, T.Wins, T.Losses, T.Division_Rank, T.Playoff_Rank, "
			/**/   +        "O.Possession_Time, O.Score, O.Attempts, O.Goals, O.Psa, O.Psm, "
			/**/   +        "D.Possession_Time, D.Score, D.Attempts, D.Goals, D.Psa, D.Psm, "
			/**/   +        "M.AllStar_Team_Id, M.Award "
			/**/
			/**/   + "  FROM Managers_T M, Teams_T T, Team_Offense_Sum_T O, Team_Defense_Sum_T D "
			/**/
			/**/   + " WHERE M.Year       = T.Year    "
			/**/   + "   AND T.Year       = O.Year    "
			/**/   + "   AND T.Year       = D.Year    "
			/**/   + "   AND M.Team_Id    = T.Team_Id "
			/**/   + "   AND T.Team_Id    = O.Team_Id "
			/**/   + "   AND T.Team_Id    = D.Team_Id "
			/**/   + "   AND M.Manager_Id = ?         "
			/**/   + "   AND O.Type       = ?         "
			/**/   + "   AND D.Type       = ?         "
			/**/
			/**/   + "ORDER BY M.Year ASC";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByPerformanceSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT M.Manager_Id, T.Wins, ((TS.Goals * 2) - TS.Turnovers) + ( (TS.Steals * 2) - TS.Penalties) Score "
			/**/
			/**/   + "  FROM Managers_T M, Teams_T T, Team_Offense_Sum_T TS "
			/**/
			/**/   + " WHERE  M.Year    =  T.Year    "
			/**/   + "   AND  M.Team_Id =  T.Team_Id "
			/**/   + "   AND  M.Year    = TS.Year    "
			/**/   + "   AND  M.Team_Id = TS.Team_Id "
			/**/   + "   AND  M.Year    =    ?       "
			/**/   + "   AND TS.Type    =    ?       "
			/**/   + "ORDER BY 2 DESC, 3 DESC LIMIT 1";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByPerfForDivSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT M.Manager_Id, T.Wins, ((TS.Goals * 2) - TS.Turnovers) + ( (TS.Steals * 2) - TS.Penalties) Score "
			/**/
			/**/   + "  FROM Managers_T M, Teams_T T, Team_Offense_Sum_T TS "
			/**/
			/**/   + " WHERE  M.Year     =  T.Year    "
			/**/   + "   AND  M.Team_Id  =  T.Team_Id "
			/**/   + "   AND  M.Year     = TS.Year    "
			/**/   + "   AND  M.Team_Id  = TS.Team_Id "
			/**/   + "   AND  M.Year     =    ?       "
			/**/   + "   AND  T.Division =    ?       "
			/**/   + "   AND TS.Type     =    ?       "
			/**/   + "ORDER BY 2 DESC, 3 DESC LIMIT 1";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerByAwardSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT M.Manager_Id, "
			/**/   +        "M.First_Name, "
			/**/   +        "M.Last_Name,  "
			/**/   +        "T.Team_Id,    "
			/**/   +        "T.Abbrev,     "
			/**/   +        "T.Wins,       "
			/**/   +        "T.Losses,     "
			/**/   +        "O.Score,      "
			/**/   +        "O.Attempts,   "
			/**/   +        "O.Goals,      "
			/**/   +        "O.Psa,        "
			/**/   +        "O.Psm         "
			/**/
			/**/   + "FROM Managers_T M, Teams_T T, Team_Offense_Sum_T O "
			/**/
			/**/   + "WHERE M.Year    = T.Year    "
			/**/   + "AND   M.Team_Id = T.Team_Id "
			/**/   + "AND   T.Year    = O.Year    "
			/**/   + "AND   T.Team_Id = O.Team_Id "
			/**/   + "AND   M.Year    = ?         "
			/**/   + "AND   O.Type    = ?         "
			/**/   + "AND   M.Award   = ?         ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerInsertPs( Connection dbConn ) throws SQLException {

		String sql = "INSERT INTO Managers_T ( Manager_Id,       "
			/**/   + "                         Team_Id,          "
			/**/   + "                         Player_Id,        "
			/**/   + "                         Year,             "
			/**/   + "                         First_Name,       "
			/**/   + "                         Last_Name,        "
			/**/   + "                         Age,              "
			/**/   + "                         Offense,          "
			/**/   + "                         Defense,          "
			/**/   + "                         Intangible,       "
			/**/   + "                         Penalties,        "
			/**/   + "                         Vitality,         "
			/**/   + "                         Style,            "
			/**/   + "                         New_Hire,         "
			/**/   + "                         Released,         "
			/**/   + "                         Retired,          "
			/**/   + "                         Award,            "
			/**/   + "                         Seasons,          "
			/**/   + "                         Score,            "
			/**/   + "                         Total_Seasons,    "
			/**/   + "                         Total_Score    )  "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" ;

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Managers_T "
			/**/
			/**/   + "SET    Team_Id         = ?, "
			/**/   +        "Player_Id       = ?, "
			/**/   +        "First_Name      = ?, "
			/**/   +        "Last_Name       = ?, "
			/**/   +        "Age             = ?, "
			/**/   +        "Offense         = ?, "
			/**/   +        "Defense         = ?, "
			/**/   +        "Intangible      = ?, "
			/**/   +        "Penalties       = ?, "
			/**/   +        "Vitality        = ?, "
			/**/   +        "Style           = ?, "
			/**/   +        "New_Hire        = ?, "
			/**/   +        "Released        = ?, "
			/**/   +        "Retired         = ?, "
			/**/   +        "Former_Team_Id  = ?, "
			/**/   +        "Award           = ?, "
			/**/   +        "Seasons         = ?, "
			/**/   +        "Score           = ?, "
			/**/   +        "Total_Seasons   = ?, "
			/**/   +        "Total_Score     = ?  "
			/**/
			/**/   + "WHERE  Year       = ? "
			/**/   + "AND    Manager_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAgeManagersUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Managers_T SET Age = Age + 1 WHERE Year = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getUpdateSeasonsUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Managers_T SET Seasons = Seasons + 1, Total_Seasons = Total_Seasons + 1 WHERE Year = ? AND Team_Id IS NOT NULL";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerAllstarTeamIdUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Managers_T SET Allstar_Team_Id = ? WHERE Year = ? AND Manager_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	
	/*
	 * PLAYER QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private static final String PLAYERS_T_COLUMNS = "Player_Id,        "
		/**/                                      + "Team_Id,          "
		/**/                                      + "Year,             "
		/**/                                      + "First_Name,       "
		/**/                                      + "Last_Name,        "
		/**/                                      + "Age,              "
		/**/                                      + "Scoring,          "
		/**/                                      + "Passing,          "
		/**/                                      + "Blocking,         "
		/**/                                      + "Tackling,         "
		/**/                                      + "Stealing,         "
		/**/                                      + "Presence,         "
		/**/                                      + "Discipline,       "
		/**/                                      + "Penalty_Shot,     "
		/**/                                      + "Penalty_Offense,  "
		/**/                                      + "Penalty_Defense,  "
		/**/                                      + "Endurance,        "
		/**/                                      + "Confidence,       "
		/**/                                      + "Vitality,         "
		/**/                                      + "Durability,       "
		/**/                                      + "Rookie,           "
		/**/                                      + "Injured,          "
		/**/                                      + "Return_Date,      "
		/**/                                      + "Free_Agent,       "
		/**/                                      + "Signed,           "
		/**/                                      + "Released,         "
		/**/                                      + "Retired,          "
		/**/                                      + "Former_Team_Id,   "
		/**/                                      + "Allstar_Team_Id,  "
		/**/                                      + "Allstar_Alternate, "
		/**/                                      + "Award,            "
		/**/                                      + "Draft_Pick,       "
		/**/                                      + "Seasons_Played    " 
		;
		
	public static PreparedStatement getNextPlayerIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MAX( Player_Id ) FROM Players_T";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerLettersSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT DISTINCT SUBSTR( Last_Name, 1, 1) X FROM Players_T ORDER BY X";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayersByLetterSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT DISTINCT Player_Id, First_Name, Last_Name FROM Players_T WHERE Last_Name LIKE ? ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getLatestYearForPlayer( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MAX( Year ) FROM Players_T WHERE Player_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerListSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Team_Id IS NOT NULL ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRookiePlayerListSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Rookie  = 1 ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getManagerialCandidatesSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT " + PLAYERS_T_COLUMNS + ", "
		/**/                   + "(Scoring + Passing + Blocking + Tackling + Stealing + Presence + Discipline + Endurance + Penalty_Shot + Penalty_Offense + Penalty_Defense) Score "
		/**/
		/**/       + "FROM Players_T "
		/**/
		/**/       + "WHERE Year    = ? "
		/**/       + "AND   Retired = 1 "
		/**/
		/**/       + "ORDER BY Score DESC "
		/**/       + "LIMIT 2 ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerByIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year      = ? "
			/**/   + "AND   Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayersByTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Team_Id = ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayersByAllstarTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year            = ? "
			/**/   + "AND   Allstar_Team_Id = ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getFreePlayersSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    =  ?    "
			/**/   + "AND   Retired =  ?    "
			/**/   + "AND   Team_Id IS NULL "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getUndraftedRookiesSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT " + PLAYERS_T_COLUMNS
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    =  ?    "
			/**/   + "AND   Rookie  =  ?    "
			/**/   + "AND   Team_Id IS NULL "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerHistoryByIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT S.Year,                "
			/**/   +        "S.Games,               "
			/**/   +        "S.Games_Started,       "
			/**/   +        "S.Playing_Time,        "
	        /**/   +        "S.Attempts,            "
	        /**/   +        "S.Goals,               "
	        /**/   +        "S.Assists,             "
	        /**/   +        "S.Turnovers,           "
	        /**/   +        "S.Stops,               "
	        /**/   +        "S.Steals,              "
	        /**/   +        "S.Penalties,           "
	        /**/   +        "S.Offensive_Penalties, "
	        /**/   +        "S.Psa,                 "
	        /**/   +        "S.Psm,                 "
	        /**/   +        "S.Ot_Psa,              "
	        /**/   +        "S.Ot_Psm,              "
	        /**/   +        "S.Goals * 3 + S.Psm,   "
	        /**/   +        "P.Award,               "
	        /**/   +        "P.Allstar_Team_Id,     "
	        /**/   +        "T.Team_Id,             "
	        /**/   +        "T.Abbrev               "
			/**/   + "  FROM Player_Stats_Sum_T S, Players_T P, Teams_T T "
			/**/   + " WHERE S.Player_Id = P.Player_Id "
			/**/   + "   AND S.Year      = P.Year      "
			/**/   + "   AND S.Team_Id   = T.Team_Id   "
			/**/   + "   AND S.Year      = T.Year      "
			/**/   + "   AND S.Player_Id = ?           "
			/**/   + "   AND S.Type      = ?           "
			/**/   + "ORDER BY Year";

		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayerScoresSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT ps.Player_Id,  "
			/**/   +         "t.Conference, "
			/**/   +         "t.Division,   "
			/**/   +      "((ps.Goals * 2) + ps.Assists - ps.Turnovers) + (ps.Stops + (ps.Steals * 2) - ps.Penalties) Score "
			/**/
			/**/   +"  FROM  Player_Stats_Sum_T ps, Teams_T t "
			/**/
			/**/   +" WHERE ps.Team_Id   = t.Team_Id   "
			/**/   +"   AND ps.Year      = t.Year      "
			/**/   +"   AND ps.Year      = ?           "
			/**/   +"   AND ps.Type      = ?           "
			/**/
			/**/   +"ORDER BY Score DESC "
			/**/   +"LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerScoreByIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT ((Goals * 2) + Assists - Turnovers) + (Stops + (Steals * 2) - Penalties) Score "
			/**/
			/**/   +"  FROM  Player_Stats_Sum_T "
			/**/
			/**/   +" WHERE Year      = ? "
			/**/   +"   AND Type      = ? "
			/**/   +"   AND Player_Id = ? ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarForTeamSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT Player_Id, ((Goals * 2) + Assists - Turnovers) + (Stops + (Steals * 2) - Penalties) Score "
			/**/
			/**/   +"  FROM  Player_Stats_Sum_T PS "
			/**/
			/**/   +" WHERE Year      = ? "
			/**/   +"   AND Type      = ? "
			/**/   +"   AND Team_Id   = ? "
			/**/   +" ORDER BY Score DESC "
			/**/   +" LIMIT 1             ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarAlternatesForDivisionSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT P.Player_Id, ((PS.Goals * 2) + PS.Assists - PS.Turnovers) + (PS.Stops + (PS.Steals * 2) - PS.Penalties) Score "
			/**/
			/**/   + "  FROM  Player_Stats_Sum_T PS, Players_T P, Teams_T T "
			/**/
			/**/   + " WHERE PS.Year            = P.Year      "
			/**/   + "   AND PS.Player_Id       = P.Player_Id "
			/**/   + "   AND  P.Year            = T.Year      "
			/**/   + "   AND  P.Team_Id         = T.Team_Id   "
			/**/   + "   AND PS.Year            =   ?         "
			/**/   + "   AND PS.Type            =   ?         "
			/**/   + "   AND  T.Division        =   ?         "
			/**/   + "   AND  P.Allstar_Team_Id IS  NULL      "
			/**/   + " ORDER BY Score DESC "
			/**/   + " LIMIT ?";

		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayerAwardsSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT p.Player_Id,         "
			/**/   +        "p.First_Name,        "
			/**/   +        "p.Last_Name,         "
			/**/   +        "t.Team_Id,           "
			/**/   +        "t.Abbrev,            "
			/**/   +        "t.Conference,        "
			/**/   +        "t.Division,          "
			/**/   +        "p.Award,             "
			/**/   +        "p.Rookie,            "
			/**/   +        "s.Goals * 3 + s.Psm, "
			/**/   +        "s.Goals,             "
			/**/   +        "s.Assists,           "
			/**/   +        "s.Stops,             "
			/**/   +        "s.Steals,            "
			/**/   +        "s.Psm                "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t, Player_Stats_Sum_T s "
			/**/
			/**/   + " WHERE p.Team_Id   = t.Team_Id "
			/**/   + "   AND p.Year      = t.Year "
			/**/   + "   AND p.Player_Id = s.Player_Id "
			/**/   + "   AND p.Year      = s.Year "
			/**/   + "   AND p.Year      = ? "
			/**/   + "   AND s.Type      = ? "
			/**/   + "   AND p.Award     > 0 "
			/**/   + "ORDER BY Award DESC";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getDraftPicksSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT t.Team_Id,    "
			/**/   + "       t.Location,   "
			/**/   + "       t.Name,       "
			/**/   + "       p.Player_Id,  "
			/**/   + "       p.First_Name, "
			/**/   + "       p.Last_Name,  "
			/**/   + "       p.Draft_Pick  "
			/**/
			/**/   + "  FROM Teams_T t, Players_T p "
			/**/
			/**/   + " WHERE t.Year        = p.Year    "
			/**/   + "   AND t.Team_Id     = p.Team_Id "
			/**/   + "   AND p.Year        = ?         "
			/**/   + "   AND p.Rookie      = ?         "
			/**/   + "   AND p.Draft_Pick >= ?         "
			/**/   + "   AND p.Team_Id    IS NOT NULL "
			/**/   + "ORDER BY p.Draft_Pick ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getStandoutPlayersSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT p.Player_Id,      "
			/**/   + "       p.First_Name,     "
			/**/   + "       p.Last_Name,      "
			/**/   + "       t.Team_Id,        "
			/**/   + "       t.Abbrev,         "
			/**/   + "       p.Seasons_Played, "
			/**/   + "     ( p.Scoring  + p.Passing  + p.Blocking + p.Tackling + p.Stealing + p.Presence + p.Discipline + p.Endurance ) * calcCoef( p.Confidence, p.Vitality, p.Age ) AS Rating "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t "
			/**/
			/**/   + " WHERE p.Team_Id = t.Team_Id "
			/**/   + "   AND p.Year    = t.Year    "
			/**/   + "   AND p.Year    = ?         "
			/**/   + "   AND p.Rookie  = ?         "
			/**/   + "   AND p.Team_Id IS NOT NULL "
			/**/
			/**/   + "ORDER BY 7 DESC LIMIT 10";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getMostImprovedSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT this_year.Player_Id,                "
			/**/   + "       this_year.First_Name,               "
			/**/   + "       this_year.Last_Name,                "
			/**/   + "               t.Team_Id,                  "
			/**/   + "               t.Abbrev,                   "
			/**/   + "       this_year.Seasons_Played,           "
			/**/   + "       this_year.Rating - last_year.Rating "
			/**/
			/**/   + "  FROM (SELECT Player_Id, First_Name, Last_Name, Team_Id, Seasons_Played, "
			/**/   + "             ( Scoring  + Passing  + Blocking + Tackling + Stealing + Presence + Discipline + Endurance ) * calcCoef( Confidence, Vitality, Age ) AS Rating "
			/**/   + "          FROM Players_T "
			/**/   + "         WHERE Year = ?) this_year, "
			/**/
			/**/   + "       (SELECT Player_Id, "
			/**/   + "             ( Scoring  + Passing  + Blocking + Tackling + Stealing + Presence + Discipline + Endurance ) * calcCoef( Confidence, Vitality, Age ) AS Rating "
			/**/   + "          FROM Players_T "
			/**/   + "         WHERE Year = ?) last_year, "
			/**/
			/**/   + "       Teams_T t "
			/**/
			/**/   + " WHERE this_year.Player_Id = last_year.Player_Id "
			/**/   + "   AND this_year.Team_Id   =         t.Team_Id   "
			/**/   + "   AND this_year.Team_Id   IS NOT NULL           "
			/**/   + "   AND         t.Year      = ?                   "
			/**/
			/**/   + "ORDER BY 7 DESC LIMIT 10";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getReleasedPlayersByTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT p.Player_Id,      "
			/**/   + "       p.First_Name,     "
			/**/   + "       p.Last_Name,      "
			/**/   + "       t.Team_Id,        "
			/**/   + "       t.Abbrev,         "
			/**/   + "       p.Rookie,         "
			/**/   + "       p.Draft_Pick,     "
			/**/   + "       p.Age,            "
			/**/   + "       p.Seasons_Played  "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t "
			/**/
			/**/   + " WHERE p.Year           = t.Year "
			/**/   + "   AND p.Former_Team_Id = t.Team_Id "
			/**/   + "   AND p.Year           = ? "
			/**/   + "   AND p.Former_Team_Id = ? "
			/**/   + "   AND p.Released       = ? "
			/**/   + "   AND p.Signed         = ? "
			/**/   + "   AND p.Free_Agent     = ? "
			/**/   + "ORDER BY 2 ASC, 3 ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getReleasedFreeAgentsByTeamSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT Player_Id,     "
			/**/   + "       First_Name,    "
			/**/   + "       Last_Name,     "
			/**/   + "       Age,           "
			/**/   + "       Seasons_Played "
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year           = ? "
			/**/   + "AND   Former_Team_Id = ? "
			/**/   + "AND   Released       = ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getSignedFreeAgentsByTeamSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT Player_Id,      "
			/**/   + "       First_Name,     "
			/**/   + "       Last_Name,      "
			/**/   + "       Age,            "
			/**/   + "       Seasons_Played, "
			/**/   + "       Former_Team_Id  "
			/**/
			/**/   + "FROM Players_T "
			/**/
			/**/   + "WHERE Year    = ? "
			/**/   + "AND   Team_Id = ? "
			/**/   + "AND   Signed  = ? "
			/**/
			/**/   + "ORDER BY Last_Name, First_Name";

		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getRetiredPlayersByTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Player_Id,     "
			/**/   + "       First_Name,    "
			/**/   + "       Last_Name,     "
			/**/   + "       Age,           "
			/**/   + "       Seasons_Played "
			/**/
			/**/   + "  FROM Players_T "
			/**/
			/**/   + " WHERE Year           = ? "
			/**/   + "   AND Former_Team_Id = ? "
			/**/   + "   AND Retired        = ? "
			/**/   + "ORDER BY Last_Name ASC, First_Name ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRetiredPlayersWithoutTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT p.Player_Id,     "
			/**/   + "       p.First_Name,    "
			/**/   + "       p.Last_Name,     "
			/**/   + "       p.Age,           "
			/**/   + "       p.Seasons_Played "
			/**/
			/**/   + "  FROM Players_T p, (SELECT Player_Id, MAX( Games_Started ) Starts FROM Player_Stats_Sum_T GROUP BY Player_Id) x "
			/**/
			/**/   + " WHERE p.Player_Id       = x.Player_Id "
			/**/   + "   AND x.Starts         >=   ?         "
			/**/   + "   AND p.Year            =   ?         "
			/**/   + "   AND p.Former_Team_Id IS   NULL      "
			/**/   + "   AND p.Retired         =   ?         "
			/**/   + "ORDER BY p.Last_Name ASC, p.First_Name ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getSeasonAveragesByPlayerSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT AVG( Goals           ), "
			/**/   + "       AVG( Assists         ), "
			/**/   + "       AVG( Stops           ), "
			/**/   + "       AVG( Steals          ), "
			/**/   + "       AVG( Psm             ), "
			/**/   + "       AVG( Goals * 3 + Psm )  "
			/**/
			/**/   + "  FROM Player_Stats_Sum_T "
			/**/
			/**/   + " WHERE Type      = ? "
			/**/   + " AND   Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAwardCountByPlayerIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT COUNT(1) FROM Players_T WHERE Player_Id = ? AND Award = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarCountByPlayerIdSelectPs( Connection dbConn ) throws SQLException {

		String sql = "SELECT COUNT(1) FROM Players_T WHERE Player_Id = ? AND Allstar_Team_Id IS NOT NULL";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAbandonedRookiesSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT p.Player_Id,  "
			/**/   + "       p.First_Name, "
			/**/   + "       p.Last_Name,  "
			/**/   + "       t.Team_Id,    "
			/**/   + "       t.Abbrev,     "
			/**/   + "       p.Draft_Pick  "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t "
			/**/
			/**/   + " WHERE p.Year           = t.Year "
			/**/   + "   AND p.Former_Team_Id = t.Team_Id "
			/**/   + "   AND p.Year           = ? "
			/**/   + "   AND p.Rookie         = ? "
			/**/   + "   AND p.Team_Id        IS NULL "
			/**/   + "ORDER BY p.Draft_Pick ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarsByTeamIdSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT p.Player_Id,         "
			/**/   +        "p.First_Name,        "
			/**/   +        "p.Last_Name,         "
			/**/   +        "p.Rookie,            "
			/**/   +        "p.Injured,           "
			/**/   +        "p.Allstar_Alternate, "
			/**/   +        "p.Award,             "
			/**/   +        "t.Team_Id,           "
			/**/   +        "t.Abbrev,            "
			/**/   +        "s.Goals * 3 + s.Psm, "
			/**/   +        "s.Goals,             "
			/**/   +        "s.Assists,           "
			/**/   +        "s.Stops,             "
			/**/   +        "s.Steals,            "
			/**/   +        "s.Psm                "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t, Player_Stats_Sum_T s "
			/**/
			/**/   + " WHERE p.Year            = t.Year    "
			/**/   + "   AND p.Team_Id         = t.Team_Id "
			/**/   + "   AND p.Player_Id       = s.Player_Id "
			/**/   + "   AND p.Year            = s.Year "
			/**/   + "   AND p.Year            = ?         "
			/**/   + "   AND s.Type            = ?         "
			/**/   + "   AND p.Allstar_Team_Id = ?         "
			/**/   + "ORDER BY p.Allstar_Alternate ASC, p.Last_Name ASC, p.First_Name ASC";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRookieInfoSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT p.Draft_Pick, "
			/**/   + "       t.Team_Id,    "
			/**/   + "       t.Abbrev,     "
			/**/   + "       p.Year        "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t "
			/**/
			/**/   + " WHERE p.Year           = t.Year    "
			/**/   + "   AND p.Former_Team_Id = t.Team_Id "
			/**/   + "   AND p.Player_Id      = ?         "
			/**/   + "   AND p.Rookie         = ?         "
			/**/   + "UNION "
			/**/   + "SELECT p.Draft_Pick, "
			/**/   + "       t.Team_Id,    "
			/**/   + "       t.Abbrev,     "
			/**/   + "       p.Year        "
			/**/
			/**/   + "  FROM Players_T p, Teams_T t "
			/**/
			/**/   + " WHERE p.Year      = t.Year    "
			/**/   + "   AND p.Team_Id   = t.Team_Id "
			/**/   + "   AND p.Player_Id = ?         "
			/**/   + "   AND p.Rookie    = ?         ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerInsertPs( Connection dbConn ) throws SQLException {

		String sql = "INSERT INTO Players_T ( Player_Id,        "
			/**/   + "                        Team_Id,          "
			/**/   + "                        Year,             "
			/**/   + "                        First_Name,       "
			/**/   + "                        Last_Name,        "
			/**/   + "                        Age,              "
			/**/   + "                        Scoring,          "
			/**/   + "                        Passing,          "
			/**/   + "                        Blocking,         "
			/**/   + "                        Tackling,         "
			/**/   + "                        Stealing,         "
			/**/   + "                        Presence,         "
			/**/   + "                        Discipline,       "
			/**/   + "                        Penalty_Shot,     "
			/**/   + "                        Penalty_Offense,  "
			/**/   + "                        Penalty_Defense,  "
			/**/   + "                        Endurance,        "
			/**/   + "                        Confidence,       "
			/**/   + "                        Vitality,         "
			/**/   + "                        Durability,       "
			/**/   + "                        Rookie,           "
			/**/   + "                        Injured,          "
			/**/   + "                        Return_Date,      "
			/**/   + "                        Free_Agent,       "
			/**/   + "                        Signed,           "
			/**/   + "                        Released,         "
			/**/   + "                        Retired,          "
			/**/   + "                        Award,            "
			/**/   + "                        Draft_Pick,       "
			/**/   + "                        Seasons_Played )  "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" ;

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T "
			/**/
			/**/   + "SET    Team_Id         = ?, "
			/**/   +        "First_Name      = ?, "
			/**/   +        "Last_Name       = ?, "
			/**/   +        "Age             = ?, "
			/**/   +        "Scoring         = ?, "
			/**/   +        "Passing         = ?, "
			/**/   +        "Blocking        = ?, "
			/**/   +        "Tackling        = ?, "
			/**/   +        "Stealing        = ?, "
			/**/   +        "Presence        = ?, "
			/**/   +        "Discipline      = ?, "
			/**/   +        "Penalty_Shot    = ?, "
			/**/   +        "Penalty_Offense = ?, "
			/**/   +        "Penalty_Defense = ?, "
			/**/   +        "Endurance       = ?, "
			/**/   +        "Confidence      = ?, "
			/**/   +        "Vitality        = ?, "
			/**/   +        "Durability      = ?, "
			/**/   +        "Rookie          = ?, "
			/**/   +        "Injured         = ?, "
			/**/   +        "Return_Date     = ?, "
			/**/   +        "Free_Agent      = ?, "
			/**/   +        "Signed          = ?, "
			/**/   +        "Released        = ?, "
			/**/   +        "Retired         = ?, "
			/**/   +        "Former_Team_Id  = ?, "
			/**/   +        "Award           = ?, "
			/**/   +        "Draft_Pick      = ?, "
			/**/   +        "Seasons_Played  = ?  "
			/**/
			/**/   + "WHERE  Year      = ? "
			/**/   + "AND    Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerInjurySelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT Injured       "
			/**/   + "  FROM Players_T     "
			/**/   + " WHERE Year      = ? "
			/**/   + "   AND Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerInjuryUpdatePs( Connection dbConn ) throws SQLException {
		
		String sql = "UPDATE Players_T "
			/**/
			/**/   + "   SET Injured     = ?, "
			/**/   + "       Return_Date = ?  "
			/**/
			/**/   + " WHERE Year      = ? "
			/**/   + "   AND Player_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getClearInjuriesUpdatePs( Connection dbConn ) throws SQLException {
		
		String sql = "UPDATE Players_T "
			/**/
			/**/   + "   SET Injured     = ?, "
			/**/   + "       Return_Date = ?  "
			/**/
			/**/   + " WHERE Year         = ? "
			/**/   + "   AND Return_Date <= ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getUpdateSeasonsPlayedUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T SET Seasons_Played = Seasons_Played + 1 WHERE Year = ? AND Team_Id IS NOT NULL";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAllstarTeamIdUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T SET Allstar_Team_Id = ?, Allstar_Alternate = ? WHERE Year = ? AND Player_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRetiredTeamPlayersUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T SET Retired = ?, Former_Team_Id = Team_Id, Team_Id = ? WHERE Year = ? AND Team_Id IS NOT NULL AND calcAgeCoef( Vitality, Age ) < RAND()";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getRetiredFreePlayersUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T SET Retired = ?, Released = ? WHERE Year = ? AND Team_Id IS NULL AND calcAgeCoef( Vitality, Age ) < 1.0";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getAgePlayersUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Players_T SET Age = Age + 1 WHERE Year = ?";
		
		return dbConn.prepareStatement( sql );
	}
	
	
	/*
	 * STATS QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public static final int STAT_SCORE         =   0;
	public static final int STAT_ATTEMPTS      =   1;
	public static final int STAT_GOALS         =   2;
	public static final int STAT_ASSISTS       =   3;
	public static final int STAT_OFFENSE       =   4;
	public static final int STAT_TURNOVERS     =   5;
	public static final int STAT_STOPS         =   6;
	public static final int STAT_STEALS        =   7;
	public static final int STAT_PENALTIES     =   8;
	public static final int STAT_PSA           =   9;
	public static final int STAT_PSM           =  10;
	public static final int STAT_OT_PSA        =  11;
	public static final int STAT_OT_PSM        =  12;
	public static final int STAT_SCORE_PCT     =  13;
	public static final int STAT_PS_PCT        =  14;
	public static final int STAT_OT_PS_PCT     =  15;
	public static final int STAT_POSSESSIONS   =  16;
	public static final int STAT_AVG_TOP       =  17;
	public static final int STAT_TIME_PER_GAME =  18;

	public static PreparedStatement getGamesPlayedByYearSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MIN(Count) FROM ( SELECT Team_Id, COUNT(1) Count FROM Teamgames_T WHERE Year = ? AND Type = ? GROUP BY Team_Id ) x";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsForSeasonSelectPs( Connection dbConn, int stat, int games_played, boolean ascending ) throws SQLException {
	
		String queryStr          = null;
		String additional_filter = null;
		String orderClause       = null;
		float  min_attempts      = 0;
		float  min_psa           = 0;
		
		min_attempts = (float)games_played * (float)2.0;
		min_psa      = (float)games_played * (float)0.6;
		
		switch ( stat ) {
		
		case STAT_SCORE:         queryStr = "pg.Goals*3+pg.Psm";        additional_filter = ""; break;
		case STAT_ATTEMPTS:      queryStr = "pg.Attempts";              additional_filter = ""; break;
		case STAT_GOALS:         queryStr = "pg.Goals";                 additional_filter = ""; break;
		case STAT_ASSISTS:       queryStr = "pg.Assists";               additional_filter = ""; break;
		case STAT_OFFENSE:       queryStr = "pg.Goals+pg.Assists";      additional_filter = ""; break;
		case STAT_TURNOVERS:     queryStr = "pg.Turnovers";             additional_filter = ""; break;
		case STAT_STOPS:         queryStr = "pg.Stops";                 additional_filter = ""; break;
		case STAT_STEALS:        queryStr = "pg.Steals";                additional_filter = ""; break;
		case STAT_PENALTIES:     queryStr = "pg.Penalties";             additional_filter = ""; break;
		case STAT_PSA:           queryStr = "pg.Psa";                   additional_filter = ""; break;
		case STAT_PSM:           queryStr = "pg.Psm";                   additional_filter = ""; break;
		case STAT_OT_PSA:        queryStr = "pg.Ot_Psa";                additional_filter = ""; break;
		case STAT_OT_PSM:        queryStr = "pg.Ot_Psm";                additional_filter = ""; break;
		case STAT_SCORE_PCT:     queryStr = "pg.Goals/pg.Attempts*100"; additional_filter = "AND pg.Attempts > " + String.valueOf( min_attempts ) + " "; break;
		case STAT_PS_PCT:        queryStr = "pg.Psm/pg.Psa*100";        additional_filter = "AND pg.Psa > " + String.valueOf( min_psa ) + " "; break;
		case STAT_OT_PS_PCT:     queryStr = "pg.Ot_Psm/pg.Ot_Psa*100";  additional_filter = ""; break;
		case STAT_TIME_PER_GAME: queryStr = "pg.Playing_Time/pg.Games"; additional_filter = ""; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";

		String sql = "SELECT " +  queryStr + ",  "
		/**/                   + "p1.Player_Id,  "
		/**/                   + "p1.First_Name, "
		/**/                   + "p1.Last_Name,  "
		/**/                   + "pg.Year        "
		/**/
		/**/       + "FROM Player_Stats_Sum_T pg, "
		/**/       +      "Players_T          p1  "
		/**/
		/**/       + "WHERE pg.Player_Id = p1.Player_Id "
		/**/       + "AND   pg.Year      = p1.Year      "
		/**/       + "AND   pg.Type      = ?            "
		/**/       + "AND   pg.Year      = ?            "
		/**/
		/**/       + additional_filter
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffStatsForSeasonSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "tg.Score";                    break;
		case STAT_ATTEMPTS:    queryStr = "tg.Attempts";                 break;
		case STAT_GOALS:       queryStr = "tg.Goals";                    break;
		case STAT_TURNOVERS:   queryStr = "tg.Turnovers";                break;
		case STAT_STEALS:      queryStr = "tg.Steals";                   break;
		case STAT_PENALTIES:   queryStr = "tg.Penalties";                break;
		case STAT_PSA:         queryStr = "tg.Psa";                      break;
		case STAT_PSM:         queryStr = "tg.Psm";                      break;
		case STAT_OT_PSA:      queryStr = "tg.Ot_Psa";                   break;
		case STAT_OT_PSM:      queryStr = "tg.Ot_Psm";                   break;
		case STAT_SCORE_PCT:   queryStr = "tg.Goals/tg.Attempts*100";    break;
		case STAT_PS_PCT:      queryStr = "tg.Psm/tg.Psa*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "tg.Ot_Psm/tg.Ot_Psa*100";     break;
		case STAT_POSSESSIONS: queryStr = "tg.Possessions";              break;
		case STAT_AVG_TOP:     queryStr = "tg.Possession_Time/tg.Games"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev,    "
		/**/                   + "tg.Year       "
		/**/
		/**/       + "FROM Team_Offense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/       + "AND   tg.Year     = ?          "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefStatsForSeasonSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "tg.Score";                    break;
		case STAT_ATTEMPTS:    queryStr = "tg.Attempts";                 break;
		case STAT_GOALS:       queryStr = "tg.Goals";                    break;
		case STAT_STOPS:       queryStr = "tg.Attempts-tg.Goals";        break;
		case STAT_TURNOVERS:   queryStr = "tg.Turnovers";                break;
		case STAT_PENALTIES:   queryStr = "tg.Penalties";                break;
		case STAT_PSA:         queryStr = "tg.Psa";                      break;
		case STAT_PSM:         queryStr = "tg.Psm";                      break;
		case STAT_SCORE_PCT:   queryStr = "tg.Goals/tg.Attempts*100";    break;
		case STAT_PS_PCT:      queryStr = "tg.Psm/tg.Psa*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "tg.Ot_Psm/tg.Ot_Psa*100";     break;
		case STAT_POSSESSIONS: queryStr = "tg.Possessions";              break;
		case STAT_AVG_TOP:     queryStr = "tg.Possession_Time/tg.Games"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev,    "
		/**/                   + "tg.Year       "
		/**/
		/**/       + "FROM Team_Defense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/       + "AND   tg.Year     = ?          "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsByGameSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:         queryStr = "pg.Points";                break;
		case STAT_ATTEMPTS:      queryStr = "pg.Attempts";              break;
		case STAT_GOALS:         queryStr = "pg.Goals";                 break;
		case STAT_ASSISTS:       queryStr = "pg.Assists";               break;
		case STAT_OFFENSE:       queryStr = "pg.Offense";               break;
		case STAT_TURNOVERS:     queryStr = "pg.Turnovers";             break;
		case STAT_STOPS:         queryStr = "pg.Stops";                 break;
		case STAT_STEALS:        queryStr = "pg.Steals";                break;
		case STAT_PENALTIES:     queryStr = "pg.Penalties";             break;
		case STAT_PSA:           queryStr = "pg.Psa";                   break;
		case STAT_PSM:           queryStr = "pg.Psm";                   break;
		case STAT_OT_PSA:        queryStr = "pg.Ot_Psa";                break;
		case STAT_OT_PSM:        queryStr = "pg.Ot_Psm";                break;
		case STAT_TIME_PER_GAME: queryStr = "pg.Playing_Time";          break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC ";
		else               orderClause = "ORDER BY 1 DESC ";

		String sql = "SELECT * FROM ("
				+ "SELECT " +    queryStr + ", "
				+               "p1.Player_Id, "
				+               "p1.First_Name, "
				+               "p1.Last_Name, "
				+               "pg.Year, "
				+               "pg.Game_Id "
		
				+ "FROM Playergames_T pg, "
				+      "Players_T     p1 "

				+ "WHERE pg.Player_Id = p1.Player_Id "
				+ "AND   pg.Year      = p1.Year "
				+ "AND   pg.Type      = ? "

				+ orderClause + "LIMIT ? "
				+ ") pg "
				+ orderClause + ", pg.Game_Id ASC ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsBySeasonSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr          = null;
		String additional_filter = null;
		String orderClause       = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:         queryStr = "pg.Goals*3+pg.Psm";        additional_filter = ""; break;
		case STAT_ATTEMPTS:      queryStr = "pg.Attempts";              additional_filter = ""; break;
		case STAT_GOALS:         queryStr = "pg.Goals";                 additional_filter = ""; break;
		case STAT_ASSISTS:       queryStr = "pg.Assists";               additional_filter = ""; break;
		case STAT_OFFENSE:       queryStr = "pg.Goals+pg.Assists";      additional_filter = ""; break;
		case STAT_TURNOVERS:     queryStr = "pg.Turnovers";             additional_filter = ""; break;
		case STAT_STOPS:         queryStr = "pg.Stops";                 additional_filter = ""; break;
		case STAT_STEALS:        queryStr = "pg.Steals";                additional_filter = ""; break;
		case STAT_PENALTIES:     queryStr = "pg.Penalties";             additional_filter = ""; break;
		case STAT_PSA:           queryStr = "pg.Psa";                   additional_filter = ""; break;
		case STAT_PSM:           queryStr = "pg.Psm";                   additional_filter = ""; break;
		case STAT_OT_PSA:        queryStr = "pg.Ot_Psa";                additional_filter = ""; break;
		case STAT_OT_PSM:        queryStr = "pg.Ot_Psm";                additional_filter = ""; break;
		case STAT_SCORE_PCT:     queryStr = "pg.Goals/pg.Attempts*100"; additional_filter = "AND pg.Attempts > 200 "; break;
		case STAT_PS_PCT:        queryStr = "pg.Psm/pg.Psa*100";        additional_filter = "AND pg.Psa > 60 "; break;
		case STAT_OT_PS_PCT:     queryStr = "pg.Ot_Psm/pg.Ot_Psa*100";  additional_filter = ""; break;
		case STAT_TIME_PER_GAME: queryStr = "pg.Playing_Time/pg.Games"; additional_filter = ""; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";

		String sql = "SELECT " +  queryStr + ",  "
		/**/                   + "p1.Player_Id,  "
		/**/                   + "p1.First_Name, "
		/**/                   + "p1.Last_Name,  "
		/**/                   + "pg.Year        "
		/**/
		/**/       + "FROM Player_Stats_Sum_T pg, "
		/**/       +      "Players_T          p1  "
		/**/
		/**/       + "WHERE pg.Player_Id = p1.Player_Id "
		/**/       + "AND   pg.Year      = p1.Year      "
		/**/       + "AND   pg.Type      = ?            "
		/**/       + "AND   pg.Games    >= 75           "
		/**/
		/**/       + additional_filter
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsByCareerSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr          = null;
		String additional_filter = null;
		String orderClause       = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:         queryStr = "SUM( pg.Goals*3+pg.Psm )";                 additional_filter = ""; break;
		case STAT_ATTEMPTS:      queryStr = "SUM( pg.Attempts )";                       additional_filter = ""; break;
		case STAT_GOALS:         queryStr = "SUM( pg.Goals )";                          additional_filter = ""; break;
		case STAT_ASSISTS:       queryStr = "SUM( pg.Assists )";                        additional_filter = ""; break;
		case STAT_OFFENSE:       queryStr = "SUM( pg.Goals+pg.Assists )";               additional_filter = ""; break;
		case STAT_TURNOVERS:     queryStr = "SUM( pg.Turnovers )";                      additional_filter = ""; break;
		case STAT_STOPS:         queryStr = "SUM( pg.Stops )";                          additional_filter = ""; break;
		case STAT_STEALS:        queryStr = "SUM( pg.Steals )";                         additional_filter = ""; break;
		case STAT_PENALTIES:     queryStr = "SUM( pg.Penalties )";                      additional_filter = ""; break;
		case STAT_PSA:           queryStr = "SUM( pg.Psa )";                            additional_filter = ""; break;
		case STAT_PSM:           queryStr = "SUM( pg.Psm )";                            additional_filter = ""; break;
		case STAT_OT_PSA:        queryStr = "SUM( pg.Ot_Psa )";                         additional_filter = ""; break;
		case STAT_OT_PSM:        queryStr = "SUM( pg.Ot_Psm )";                         additional_filter = ""; break;
		case STAT_SCORE_PCT:     queryStr = "SUM( pg.Goals )/SUM( pg.Attempts )*100";   additional_filter = "AND xx.Attempts > 200 "; break;
		case STAT_PS_PCT:        queryStr = "SUM( pg.Psm )/SUM( pg.Psa )*100";          additional_filter = "AND xx.Psa > 60 "; break;
		case STAT_OT_PS_PCT:     queryStr = "SUM( pg.Ot_Psm )/SUM( pg.Ot_Psa )*100";    additional_filter = ""; break;
		case STAT_TIME_PER_GAME: queryStr = "SUM( pg.Playing_Time )/SUM( pg.Games )";   additional_filter = ""; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC  ";
		else               orderClause = "ORDER BY 1 DESC ";
		
		String sql = "SELECT " +          queryStr + ",     "
		/**/                   + "     p1.Player_Id,        "
		/**/                   + "     p1.First_Name,       "
		/**/                   + "     p1.Last_Name,        "
		/**/                   + "MIN( p1.Year           ), "
		/**/                   + "MAX( p1.Year           ), "
		/**/                   + "MAX( p1.Seasons_Played )  "
		/**/
		/**/       + "FROM Player_Stats_Sum_T pg, "
		/**/       +      "Players_T          p1, "
		/**/       + "(SELECT Player_Id, SUM( Games ) Games, AVG( Attempts ) Attempts, AVG( Psa ) Psa FROM Player_Stats_Sum_T GROUP BY Player_Id) xx "
		/**/
		/**/       + "WHERE pg.Player_Id = p1.Player_Id "
		/**/       + "AND   pg.Player_Id = xx.Player_Id "
		/**/       + "AND   pg.Year      = p1.Year      "
		/**/       + "AND   pg.Type      = ?            "
		/**/       + "AND   xx.Games    >= 100          "
		/**/
		/**/       + additional_filter
		/**/
		/**/       + "GROUP BY p1.Player_Id, p1.First_Name, p1.Last_Name "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamStatsByGameSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "tg.Total_Score";           break;
		case STAT_ATTEMPTS:    queryStr = "tg.Attempts";              break;
		case STAT_GOALS:       queryStr = "tg.Goals";                 break;
		case STAT_TURNOVERS:   queryStr = "tg.Turnovers";             break;
		case STAT_STEALS:      queryStr = "tg.Steals";                break;
		case STAT_PENALTIES:   queryStr = "tg.Penalties";             break;
		case STAT_PSA:         queryStr = "tg.Psa";                   break;
		case STAT_PSM:         queryStr = "tg.Psm";                   break;
		case STAT_OT_PSA:      queryStr = "tg.Ot_Psa";                break;
		case STAT_OT_PSM:      queryStr = "tg.Ot_Psm";                break;
		case STAT_SCORE_PCT:   queryStr = "tg.Goals/tg.Attempts*100"; break;
		case STAT_PS_PCT:      queryStr = "tg.Psm/tg.Psa*100";        break;
		case STAT_OT_PS_PCT:   queryStr = "tg.Ot_Psm/tg.Ot_Psa*100";  break;
		case STAT_POSSESSIONS: queryStr = "tg.Possessions";           break;
		case STAT_AVG_TOP:     queryStr = "tg.Possession_Time";       break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  tg.Game_Id ASC ";
		else               orderClause = "ORDER BY 1 DESC, tg.Game_Id ASC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev,    "
		/**/                   + "tg.Year,      "
		/**/                   + "tg.Game_Id,   "
		/**/                   + "tg.Road,      "
		/**/                   + "t2.Team_Id,   "
		/**/                   + "t2.Abbrev     "
		/**/
		/**/       + "FROM Teamgames_T tg, "
		/**/       +      "Teams_T     t1, "
		/**/       +      "Teams_T     t2  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Opponent = t2.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Year     = t2.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffStatsBySeasonSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "tg.Score";                    break;
		case STAT_ATTEMPTS:    queryStr = "tg.Attempts";                 break;
		case STAT_GOALS:       queryStr = "tg.Goals";                    break;
		case STAT_TURNOVERS:   queryStr = "tg.Turnovers";                break;
		case STAT_STEALS:      queryStr = "tg.Steals";                   break;
		case STAT_PENALTIES:   queryStr = "tg.Penalties";                break;
		case STAT_PSA:         queryStr = "tg.Psa";                      break;
		case STAT_PSM:         queryStr = "tg.Psm";                      break;
		case STAT_OT_PSA:      queryStr = "tg.Ot_Psa";                   break;
		case STAT_OT_PSM:      queryStr = "tg.Ot_Psm";                   break;
		case STAT_SCORE_PCT:   queryStr = "tg.Goals/tg.Attempts*100";    break;
		case STAT_PS_PCT:      queryStr = "tg.Psm/tg.Psa*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "tg.Ot_Psm/tg.Ot_Psa*100";     break;
		case STAT_POSSESSIONS: queryStr = "tg.Possessions";              break;
		case STAT_AVG_TOP:     queryStr = "tg.Possession_Time/tg.Games"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev,    "
		/**/                   + "tg.Year       "
		/**/
		/**/       + "FROM Team_Offense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefStatsBySeasonSelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "tg.Score";                    break;
		case STAT_ATTEMPTS:    queryStr = "tg.Attempts";                 break;
		case STAT_GOALS:       queryStr = "tg.Goals";                    break;
		case STAT_STOPS:       queryStr = "tg.Attempts-tg.Goals";        break;
		case STAT_TURNOVERS:   queryStr = "tg.Turnovers";                break;
		case STAT_PENALTIES:   queryStr = "tg.Penalties";                break;
		case STAT_PSA:         queryStr = "tg.Psa";                      break;
		case STAT_PSM:         queryStr = "tg.Psm";                      break;
		case STAT_SCORE_PCT:   queryStr = "tg.Goals/tg.Attempts*100";    break;
		case STAT_PS_PCT:      queryStr = "tg.Psm/tg.Psa*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "tg.Ot_Psm/tg.Ot_Psa*100";     break;
		case STAT_POSSESSIONS: queryStr = "tg.Possessions";              break;
		case STAT_AVG_TOP:     queryStr = "tg.Possession_Time/tg.Games"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC,  YEAR ASC ";
		else               orderClause = "ORDER BY 1 DESC, YEAR ASC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev,    "
		/**/                   + "tg.Year       "
		/**/
		/**/       + "FROM Team_Defense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffStatsByHistorySelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "SUM( tg.Score )";                           break;
		case STAT_ATTEMPTS:    queryStr = "SUM( tg.Attempts )";                        break;
		case STAT_GOALS:       queryStr = "SUM( tg.Goals )";                           break;
		case STAT_TURNOVERS:   queryStr = "SUM( tg.Turnovers )";                       break;
		case STAT_STEALS:      queryStr = "SUM( tg.Steals )";                          break;
		case STAT_PENALTIES:   queryStr = "SUM( tg.Penalties )";                       break;
		case STAT_PSA:         queryStr = "SUM( tg.Psa )";                             break;
		case STAT_PSM:         queryStr = "SUM( tg.Psm )";                             break;
		case STAT_OT_PSA:      queryStr = "SUM( tg.Ot_Psa )";                          break;
		case STAT_OT_PSM:      queryStr = "SUM( tg.Ot_Psm )";                          break;
		case STAT_SCORE_PCT:   queryStr = "SUM( tg.Goals )/SUM( tg.Attempts )*100";    break;
		case STAT_PS_PCT:      queryStr = "SUM( tg.Psm )/SUM( tg.Psa )*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "SUM( tg.Ot_Psm )/SUM( tg.Ot_Psa )*100";     break;
		case STAT_POSSESSIONS: queryStr = "SUM( tg.Possessions )";                     break;
		case STAT_AVG_TOP:     queryStr = "SUM( tg.Possession_Time )/SUM( tg.Games )"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC  ";
		else               orderClause = "ORDER BY 1 DESC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev     "
		/**/
		/**/       + "FROM Team_Offense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/
		/**/       + "GROUP BY t1.Team_Id, t1.Abbrev "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefStatsByHistorySelectPs( Connection dbConn, int stat, boolean ascending ) throws SQLException {
	
		String queryStr    = null;
		String orderClause = null;
		
		switch ( stat ) {
		
		case STAT_SCORE:       queryStr = "SUM( tg.Score )";                           break;
		case STAT_ATTEMPTS:    queryStr = "SUM( tg.Attempts )";                        break;
		case STAT_GOALS:       queryStr = "SUM( tg.Goals )";                           break;
		case STAT_STOPS:       queryStr = "SUM( tg.Attempts-tg.Goals )";               break;
		case STAT_TURNOVERS:   queryStr = "SUM( tg.Turnovers )";                       break;
		case STAT_PENALTIES:   queryStr = "SUM( tg.Penalties )";                       break;
		case STAT_PSA:         queryStr = "SUM( tg.Psa )";                             break;
		case STAT_PSM:         queryStr = "SUM( tg.Psm )";                             break;
		case STAT_SCORE_PCT:   queryStr = "SUM( tg.Goals )/SUM( tg.Attempts )*100";    break;
		case STAT_PS_PCT:      queryStr = "SUM( tg.Psm )/SUM( tg.Psa )*100";           break;
		case STAT_OT_PS_PCT:   queryStr = "SUM( tg.Ot_Psm )/SUM( tg.Ot_Psa )*100";     break;
		case STAT_POSSESSIONS: queryStr = "SUM( tg.Possessions )";                     break;
		case STAT_AVG_TOP:     queryStr = "SUM( tg.Possession_Time )/SUM( tg.Games )"; break;
			
		default: return null;
		}

		if   ( ascending ) orderClause = "ORDER BY 1 ASC  ";
		else               orderClause = "ORDER BY 1 DESC ";
		
		String sql = "SELECT " +  queryStr + ", "
		/**/                   + "t1.Team_Id,   "
		/**/                   + "t1.Abbrev     "
		/**/
		/**/       + "FROM Team_Defense_Sum_T tg, "
		/**/       +      "Teams_T            t1  "
		/**/
		/**/       + "WHERE tg.Team_Id  = t1.Team_Id "
		/**/       + "AND   tg.Year     = t1.Year    "
		/**/       + "AND   tg.Type     = ?          "
		/**/
		/**/       + "GROUP BY t1.Team_Id, t1.Abbrev "
		/**/
		/**/       + orderClause + "LIMIT ?";

		return dbConn.prepareStatement( sql );
	}

	
	/*
	 * MISC QUERIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public static PreparedStatement getGameStateInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Gamestate_T ( Game_Id,             "
			/**/   + "                          Started,             "
			/**/   + "                          Start_Time,          "
			/**/   + "                          Sequence,            "
			/**/   + "                          Period,              "
			/**/   + "                          Overtime,            "
			/**/   + "                          Time_Remaining,      "
			/**/   + "                          Clock_Stopped,       "
			/**/   + "                          Possession,          "
			/**/   + "                          Last_Event )         "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getGameStateUpdatePs( Connection dbConn ) throws SQLException {

		String sql = "UPDATE Gamestate_T "
		    /**/
		    /**/   + "   SET Period              = ?, "
		    /**/   + "       Started             = ?, "
		    /**/   + "       Start_Time          = ?, "
		    /**/   + "       Sequence            = ?, "
		    /**/   + "       Overtime            = ?, "
		    /**/   + "       Time_Remaining      = ?, "
		    /**/   + "       Clock_Stopped       = ?, "
		    /**/   + "       Possession          = ?, "
		    /**/   + "       Last_Event          = ?  "
		    /**/
		    /**/   + " WHERE Game_Id = ? ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getGameStateSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Started,             "
			/**/   +        "Start_Time,          "
			/**/   +        "Period,              "
			/**/   +        "Sequence,            "
			/**/   +        "Overtime,            "
			/**/   +        "Time_Remaining,      "
			/**/   +        "Clock_Stopped,       "
			/**/   +        "Possession,          "
			/**/   +        "Last_Event           "
			/**/   + "  FROM Gamestate_T          "
			/**/   + " WHERE Game_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getNextGameIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT MAX( Game_Id ) FROM Teamgames_T";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getRandomFirstNameSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Name FROM Firstnames_T ORDER BY -LOG(1 - RAND())/Frequency LIMIT 1";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getRandomLastNameSelectPs( Connection dbConn ) throws SQLException {
		
		// Explanation here: http://use.perl.org/~bart/journal/33630
		
		String sql = "SELECT Name FROM Lastnames_T ORDER BY -LOG(1 - RAND())/Frequency LIMIT 1";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayerOrManagerByNameSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "(SELECT 1 FROM Players_T WHERE First_Name = ? AND Last_Name = ? LIMIT 1) UNION (SELECT 1 FROM Managers_T WHERE First_Name = ? AND Last_Name = ? LIMIT 1)";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getWinsByStreakPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT SUM( Win ) FROM (SELECT Game_Id, Win FROM Teamgames_T WHERE Type = ? AND Year = ? AND Team_Id = ? ORDER BY Game_Id DESC LIMIT ?) x";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getWinsByMatchSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT COUNT(1) "
			/**/   + "  FROM Teamgames_T "
			/**/   + " WHERE Year         =    ? "
			/**/   + "   AND Type         =    ? "
			/**/   + "   AND Team_Id      =    ? "
			/**/   + "   AND Opponent     =    ? "
			/**/   + "   AND Win          =    ? ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTotalPointsScoredSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Score FROM Team_Offense_Sum_T WHERE Year = ? AND Type = ? AND Team_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTotalPointsAllowedSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Score FROM Team_Defense_Sum_T WHERE Year = ? AND Type = ? AND Team_Id = ?";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getGamesByDateSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Game_Id,        "
			/**/   + "        t.Team_Id,        "
			/**/   + "        t.Abbrev,         "
			/**/   + "       tg.Road,           "
			/**/   + "       tg.Overtime,       "
			/**/   + "       gs.Started,        "
			/**/   + "       gs.Start_Time,     "
			/**/   + "       gs.Period,         "
			/**/   + "       gs.Time_Remaining, "
			/**/   + "       gs.Possession,     "
			/**/   + "       tg.Win,            "
			/**/   + "       tg.Total_Score     "
			/**/
			/**/   + "  FROM Gamestate_T gs, Teamgames_T tg, Teams_T t"
			/**/   + " WHERE tg.Team_Id   =  t.Team_Id "
			/**/   + "   AND tg.Year      =  t.Year "
			/**/   + "   AND tg.Game_Id   = gs.Game_Id "
			/**/   + "   AND tg.Datestamp =    ? "
			/**/   + "ORDER BY Game_Id";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getGamesByTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Game_Id, tg.Datestamp, t.Team_Id, t.Abbrev, tg.Road, tg.Overtime, tg.Win, tg.Total_Score "
			/**/   + "  FROM Teamgames_T tg, Teams_T t"
			/**/   + " WHERE   tg.Team_Id   = t.Team_Id "
			/**/   + "   AND   tg.Year      = t.Year "
			/**/   + "   AND   tg.Year      = ? "
			/**/   + "   AND ( tg.Team_Id   = ? OR   "
			/**/   + "         tg.Opponent  = ?    ) "
			/**/   + "ORDER BY tg.Game_Id";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getGamesByTeamIdAndTypeSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Game_Id, tg.Datestamp, t.Team_Id, t.Abbrev, tg.Road, tg.Overtime, tg.Win, tg.Total_Score "
			/**/   + "  FROM Teamgames_T tg, Teams_T t"
			/**/   + " WHERE   tg.Team_Id   = t.Team_Id "
			/**/   + "   AND   tg.Year      = t.Year "
			/**/   + "   AND   tg.Year      = ? "
			/**/   + "   AND   tg.Type      = ? "
			/**/   + "   AND ( tg.Team_Id   = ? OR   "
			/**/   + "         tg.Opponent  = ?    ) "
			/**/   + "ORDER BY tg.Game_Id";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getChampionshipGameByYearSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Game_Id, tg.Datestamp, t.Team_Id, t.Location, tg.Road, tg.Overtime, tg.Win, tg.Total_Score "
			/**/   + "  FROM Teamgames_T tg, Teams_T t"
			/**/   + " WHERE   tg.Team_Id       = t.Team_Id "
			/**/   + "   AND   tg.Year          = t.Year "
			/**/   + "   AND   tg.Year          = ? "
			/**/   + "   AND   tg.Type          = ? "
			/**/   + "   AND   tg.Playoff_Round = ? "
			/**/   + "ORDER BY tg.Game_Id";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getChampionshipGamesSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Game_Id, tg.Datestamp, t.Team_Id, t.Location, t.Name, tg.Road, tg.Overtime, tg.Win, tg.Total_Score "
			/**/   + "  FROM Teamgames_T tg, Teams_T t "
			/**/   + " WHERE tg.Team_Id       = t.Team_Id "
			/**/   + "   AND tg.Year          = t.Year    "
			/**/   + "   AND tg.Playoff_Round =   ?       "
			/**/   + "ORDER BY Game_Id";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayoffGameInfoSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Playoff_Round, tg.Team_Id, tg.Game_Id, t.Conference, t.Division, t.Division_Rank, tg.Road, tg.Win "
			/**/   + "  FROM Teamgames_T tg, Teams_T t "
			/**/   + " WHERE tg.Team_Id       = t.Team_Id "
			/**/   + "   AND tg.Year          = t.Year    "
			/**/   + "   AND tg.Year          =   ?       "
			/**/   + "   AND tg.Playoff_Round >   0       "
			/**/   + "ORDER BY 1, 2, 3";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getInjuriesByDateSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT i.Player_Id, p.First_Name, p.Last_Name, i.Team_Id, t.Abbrev, i.Duration "
			/**/   + "  FROM Injuries_T i, Players_T p, Teams_T t "
			/**/   + " WHERE i.Player_Id  =  p.Player_Id "
			/**/   + "   AND p.Year       =  ?           "
			/**/   + "   AND i.Team_Id    =  t.Team_Id   "
			/**/   + "   AND t.Year       =  ?           "
			/**/   + "   AND i.Game_Id    in ( SELECT DISTINCT Game_Id FROM Teamgames_T WHERE Datestamp = ? ) ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefenseByTeamIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Type,                "
			/**/   +        "Games,               "
			/**/   +        "Possessions,         "
			/**/   +        "Possession_Time,     "
			/**/   +        "Attempts,            "
			/**/   +        "Goals,               "
			/**/   +        "Turnovers,           "
			/**/   +        "Steals,              "
			/**/   +        "Penalties,           "
			/**/   +        "Offensive_Penalties, "
			/**/   +        "Psa,                 "
			/**/   +        "Psm,                 "
			/**/   +        "Ot_Psa,              "
			/**/   +        "Ot_Psm,              "
			/**/   +        "Score                "
			/**/   + "  FROM Team_Defense_Sum_T "
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Team_Id = ? "
			/**/   + "ORDER BY Type";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTeamOffenseByTeamIdSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT Type,                "
			/**/   +        "Games,               "
			/**/   +        "Possessions,         "
			/**/   +        "Possession_Time,     "
			/**/   +        "Attempts,            "
			/**/   +        "Goals,               "
			/**/   +        "Turnovers,           "
			/**/   +        "Steals,              "
			/**/   +        "Penalties,           "
			/**/   +        "Offensive_Penalties, "
			/**/   +        "Psa,                 "
			/**/   +        "Psm,                 "
			/**/   +        "Ot_Psa,              "
			/**/   +        "Ot_Psm,              "
			/**/   +        "Score                "
			/**/   + "  FROM Team_Offense_Sum_T "
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Team_Id = ? "
			/**/   + "ORDER BY Type";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsSumByPlayerIdSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Type,                "
			/**/   +        "Games,               "
			/**/   +        "Games_Started,       "
			/**/   +        "Playing_Time,        "
			/**/   +        "Attempts,            "
			/**/   +        "Goals,               "
			/**/   +        "Assists,             "
			/**/   +        "Turnovers,           "
			/**/   +        "Stops,               "
			/**/   +        "Steals,              "
			/**/   +        "Penalties,           "
			/**/   +        "Offensive_Penalties, "
			/**/   +        "Psa,                 "
			/**/   +        "Psm,                 "
			/**/   +        "Ot_Psa,              "
			/**/   +        "Ot_Psm,              "
			/**/   +        "Goals * 3 + Psm      "
			/**/   + "  FROM Player_Stats_Sum_T "
			/**/   + " WHERE Year      = ? "
			/**/   + "   AND Player_Id = ? "
			/**/   + "ORDER BY Type";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getSingleGameSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT t.Location,            "
			/**/   + "       t.Name,                "
			/**/   + "       t.Abbrev,              "
			/**/   + "       t.Team_Id,             "
			/**/   + "      tg.Possessions,         "
			/**/   + "      tg.Possession_Time,     "
			/**/   + "      tg.Attempts,            "
			/**/   + "      tg.Goals,               "
			/**/   + "      tg.Turnovers,           "
			/**/   + "      tg.Steals,              "
			/**/   + "      tg.Penalties,           "
			/**/   + "      tg.Offensive_Penalties, "
			/**/   + "      tg.Psa,                 "
			/**/   + "      tg.Psm,                 "
			/**/   + "      tg.Ot_Psa,              "
			/**/   + "      tg.Ot_Psm,              "
			/**/   + "      tg.Period1_Score,       "
			/**/   + "      tg.Period2_Score,       "
			/**/   + "      tg.Period3_Score,       "
			/**/   + "      tg.Period4_Score,       "
			/**/   + "      tg.Period5_Score,       "
			/**/   + "      tg.Overtime_Score,      "
			/**/   + "      tg.Total_Score,         "
			/**/   + "      tg.Win                  "
			/**/
			/**/   + "  FROM Teams_T t, Teamgames_T tg "
			/**/
			/**/   + " WHERE tg.Year    = t.Year    "
			/**/   + "   AND tg.Team_Id = t.Team_Id "
			/**/   + "   AND tg.Game_Id = ?         "
			/**/   + "   AND tg.Road    = ?         ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayerGamesForTeamSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT p.First_Name,          "
			/**/   + "       p.Last_Name,           "
			/**/   + "       p.Player_Id,           "
			/**/   + "      pg.Injured,             "
			/**/   + "      pg.Started,             "
			/**/   + "      pg.Playing_Time,        "
			/**/   + "      pg.Attempts,            "
			/**/   + "      pg.Goals,               "
			/**/   + "      pg.Assists,             "
			/**/   + "      pg.Turnovers,           "
			/**/   + "      pg.Stops,               "
			/**/   + "      pg.Steals,              "
			/**/   + "      pg.Penalties,           "
			/**/   + "      pg.Offensive_Penalties, "
			/**/   + "      pg.Psa,                 "
			/**/   + "      pg.Psm,                 "
			/**/   + "      pg.Ot_Psa,              "
			/**/   + "      pg.Ot_Psm,              "
			/**/   + "      pg.Goals * 3 + pg.Psm   "
			/**/
			/**/   + "  FROM Players_T p, Playergames_T pg "
			/**/
			/**/   + " WHERE pg.Year      = p.Year      "
			/**/   + "   AND pg.Player_Id = p.Player_Id "
			/**/   + "   AND pg.Game_Id   = ?           "
			/**/   + "   AND pg.Team_Id   = ?           "
			/**/   + "ORDER BY 19 DESC, p.Last_Name, p.First_Name ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getGamesByPlayerIdAndTypeSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT tg.Opponent,            "
			/**/   + "       tg.Road,                "
			/**/   + "        t.Abbrev,              "
			/**/   + "       pg.Datestamp,           "
			/**/   + "       pg.Game_Id,             "
			/**/   + "       pg.Injured,             "
			/**/   + "       pg.Started,             "
			/**/   + "       pg.Playing_Time,        "
			/**/   + "       pg.Attempts,            "
			/**/   + "       pg.Goals,               "
			/**/   + "       pg.Assists,             "
			/**/   + "       pg.Turnovers,           "
			/**/   + "       pg.Stops,               "
			/**/   + "       pg.Steals,              "
			/**/   + "       pg.Penalties,           "
			/**/   + "       pg.Offensive_Penalties, "
			/**/   + "       pg.Psa,                 "
			/**/   + "       pg.Psm,                 "
			/**/   + "       pg.Ot_Psa,              "
			/**/   + "       pg.Ot_Psm,              "
			/**/   + "       pg.Goals * 3 + pg.Psm   "
			/**/
			/**/   + "  FROM Teamgames_T tg, Playergames_T pg, Teams_T t "
			/**/
			/**/   + " WHERE tg.Game_Id   = pg.Game_Id  "
			/**/   + "   AND tg.Team_Id   = pg.Team_Id  "
			/**/   + "   AND  t.Year      = tg.Year     "
			/**/   + "   AND  t.Team_Id   = tg.Opponent "
			/**/   + "   AND pg.Year      = ?           "
			/**/   + "   AND pg.Player_Id = ?           "
			/**/   + "   AND pg.Type      = ?           "
			/**/   + "ORDER BY pg.Game_Id ASC ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTeamPlayerDataSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Games, Games_Started, Playing_Time, Goals, Assists, Stops, Steals, Psm, Goals * 3 + Psm "
			/**/   + "  FROM Player_Stats_Sum_T "
			/**/   + " WHERE Player_Id = ? "
			/**/   + "   AND Year      = ? "
			/**/   + "   AND Type      = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getVsConferenceWinsSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT COUNT(1), SUM( win ) "
			/**/   + "  FROM Teamgames_T "
			/**/   + " WHERE Year     =  ? "
			/**/   + "   AND Type     =  ? "
			/**/   + "   AND Team_Id  IN (SELECT Team_Id FROM Teams_T WHERE Conference = ?) "
			/**/   + "   AND Opponent IN (SELECT Team_Id FROM Teams_T WHERE Conference = ?) ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getVsDivisionWinsSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT COUNT(1), SUM( win ) "
			/**/   + "  FROM Teamgames_T "
			/**/   + " WHERE Year     =  ? "
			/**/   + "   AND Type     =  ? "
			/**/   + "   AND Team_Id  IN (SELECT Team_Id FROM Teams_T WHERE Division = ?) "
			/**/   + "   AND Opponent IN (SELECT Team_Id FROM Teams_T WHERE Division = ?) ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Games,               "
			/**/   + "       Games_Started,       "
			/**/   + "       Playing_Time,        "
			/**/   + "       Attempts,            "
			/**/   + "       Goals,               "
			/**/   + "       Assists,             "
			/**/   + "       Turnovers,           "
			/**/   + "       Stops,               "
			/**/   + "       Steals,              "
			/**/   + "       Penalties,           "
			/**/   + "       Offensive_Penalties, "
			/**/   + "       Psa,                 "
			/**/   + "       Psm,                 "
			/**/   + "       Ot_Psa,              "
			/**/   + "       Ot_Psm               "
			/**/   + "  FROM Player_Stats_Sum_T "
			/**/   + " WHERE Year      = ? "
			/**/   + "   AND Type      = ? "
			/**/   + "   AND Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffenseSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Games,               "
			/**/   + "       Possessions,         "
			/**/   + "       Possession_Time,     "
			/**/   + "       Attempts,            "
			/**/   + "       Goals,               "
			/**/   + "       Turnovers,           "
			/**/   + "       Steals,              "
			/**/   + "       Penalties,           "
			/**/   + "       Offensive_Penalties, "
			/**/   + "       Psa,                 "
			/**/   + "       Psm,                 "
			/**/   + "       Ot_Psa,              "
			/**/   + "       Ot_Psm,              "
			/**/   + "       Score                "
			/**/   + "  FROM Team_Offense_Sum_T "
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Type    = ? "
			/**/   + "   AND Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefenseSelectPs( Connection dbConn ) throws SQLException {
	
		String sql = "SELECT Games,               "
			/**/   + "       Possessions,         "
			/**/   + "       Possession_Time,     "
			/**/   + "       Attempts,            "
			/**/   + "       Goals,               "
			/**/   + "       Turnovers,           "
			/**/   + "       Steals,              "
			/**/   + "       Penalties,           "
			/**/   + "       Offensive_Penalties, "
			/**/   + "       Psa,                 "
			/**/   + "       Psm,                 "
			/**/   + "       Ot_Psa,              "
			/**/   + "       Ot_Psm,              "
			/**/   + "       Score                "
			/**/   + "  FROM Team_Defense_Sum_T "
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Type    = ? "
			/**/   + "   AND Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Player_Stats_Sum_T "
			/**/
			/**/   + "   SET Games               = ?, "
			/**/   + "       Games_Started       = ?, "
			/**/   + "       Playing_Time        = ?, "
			/**/   + "       Attempts            = ?, "
			/**/   + "       Goals               = ?, "
			/**/   + "       Assists             = ?, "
			/**/   + "       Turnovers           = ?, "
			/**/   + "       Stops               = ?, "
			/**/   + "       Steals              = ?, "
			/**/   + "       Penalties           = ?, "
			/**/   + "       Offensive_Penalties = ?, "
			/**/   + "       Psa                 = ?, "
			/**/   + "       Psm                 = ?, "
			/**/   + "       Ot_Psa              = ?, "
			/**/   + "       Ot_Psm              = ?  "
			/**/
			/**/   + " WHERE Year      = ? "
			/**/   + "   AND Type      = ? "
			/**/   + "   AND Player_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffenseUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Team_Offense_Sum_T "
			/**/
			/**/   + "   SET Games               = ?, "
			/**/   + "       Possessions         = ?, "
			/**/   + "       Possession_Time     = ?, "
			/**/   + "       Attempts            = ?, "
			/**/   + "       Goals               = ?, "
			/**/   + "       Turnovers           = ?, "
			/**/   + "       Steals              = ?, "
			/**/   + "       Penalties           = ?, "
			/**/   + "       Offensive_Penalties = ?, "
			/**/   + "       Psa                 = ?, "
			/**/   + "       Psm                 = ?, "
			/**/   + "       Ot_Psa              = ?, "
			/**/   + "       Ot_Psm              = ?, "
			/**/   + "       Score               = ?  "
			/**/
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Type    = ? "
			/**/   + "   AND Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefenseUpdatePs( Connection dbConn ) throws SQLException {
	
		String sql = "UPDATE Team_Defense_Sum_T "
			/**/
			/**/   + "   SET Games               = ?, "
			/**/   + "       Possessions         = ?, "
			/**/   + "       Possession_Time     = ?, "
			/**/   + "       Attempts            = ?, "
			/**/   + "       Goals               = ?, "
			/**/   + "       Turnovers           = ?, "
			/**/   + "       Steals              = ?, "
			/**/   + "       Penalties           = ?, "
			/**/   + "       Offensive_Penalties = ?, "
			/**/   + "       Psa                 = ?, "
			/**/   + "       Psm                 = ?, "
			/**/   + "       Ot_Psa              = ?, "
			/**/   + "       Ot_Psm              = ?, "
			/**/   + "       Score               = ?  "
			/**/
			/**/   + " WHERE Year    = ? "
			/**/   + "   AND Type    = ? "
			/**/   + "   AND Team_Id = ? ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerStatsInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Player_Stats_Sum_T ( Year,                "
			/**/   +                           "       Type,                "
			/**/   +                           "       Player_Id,           "
			/**/   +                           "       Team_Id,             "
			/**/   +                           "       Games,               "
			/**/   +                           "       Games_Started,       "
			/**/   +                           "       Playing_Time,        "
			/**/   +                           "       Attempts,            "
			/**/   +                           "       Goals,               "
			/**/   +                           "       Assists,             "
			/**/   +                           "       Turnovers,           "
			/**/   +                           "       Stops,               "
			/**/   +                           "       Steals,              "
			/**/   +                           "       Penalties,           "
			/**/   +                           "       Offensive_Penalties, "
			/**/   +                           "       Psa,                 "
			/**/   +                           "       Psm,                 "
			/**/   +                           "       Ot_Psa,              "
			/**/   +                           "       Ot_Psm )             "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamOffenseInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Team_Offense_Sum_T ( Year,                "
			/**/   +                           "       Type,                "
			/**/   +                           "       Team_Id,             "
			/**/   +                           "       Games,               "
			/**/   +                           "       Possessions,         "
			/**/   +                           "       Possession_Time,     "
			/**/   +                           "       Attempts,            "
			/**/   +                           "       Goals,               "
			/**/   +                           "       Turnovers,           "
			/**/   +                           "       Steals,              "
			/**/   +                           "       Penalties,           "
			/**/   +                           "       Offensive_Penalties, "
			/**/   +                           "       Psa,                 "
			/**/   +                           "       Psm,                 "
			/**/   +                           "       Ot_Psa,              "
			/**/   +                           "       Ot_Psm,              "
			/**/   +                           "       Score )              "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamDefenseInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Team_Defense_Sum_T ( Year,                "
			/**/   +                           "       Type,                "
			/**/   +                           "       Team_Id,             "
			/**/   +                           "       Games,               "
			/**/   +                           "       Possessions,         "
			/**/   +                           "       Possession_Time,     "
			/**/   +                           "       Attempts,            "
			/**/   +                           "       Goals,               "
			/**/   +                           "       Turnovers,           "
			/**/   +                           "       Steals,              "
			/**/   +                           "       Penalties,           "
			/**/   +                           "       Offensive_Penalties, "
			/**/   +                           "       Psa,                 "
			/**/   +                           "       Psm,                 "
			/**/   +                           "       Ot_Psa,              "
			/**/   +                           "       Ot_Psm,              "
			/**/   +                           "       Score )              "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getTeamGameInsertPs( Connection dbConn ) throws SQLException {
	
		String sql = "INSERT INTO Teamgames_T ( Game_Id,             "
			/**/   + "                          Year,                "
			/**/   + "                          Datestamp,           "
			/**/   + "                          Type,                "
			/**/   + "                          Playoff_Round,       "
			/**/   + "                          Team_Id,             "
			/**/   + "                          Opponent,            "
			/**/   + "                          Road,                "
			/**/   + "                          Overtime,            "
			/**/   + "                          Win,                 "
			/**/   + "                          Possessions,         "
			/**/   + "                          Possession_Time,     "
			/**/   + "                          Attempts,            "
			/**/   + "                          Goals,               "
			/**/   + "                          Turnovers,           "
			/**/   + "                          Steals,              "
			/**/   + "                          Penalties,           "
			/**/   + "                          Offensive_Penalties, "
			/**/   + "                          Psa,                 "
			/**/   + "                          Psm,                 "
			/**/   + "                          Ot_Psa,              "
			/**/   + "                          Ot_Psm,              "
			/**/   + "                          Period1_Score,       "
			/**/   + "                          Period2_Score,       "
			/**/   + "                          Period3_Score,       "
			/**/   + "                          Period4_Score,       "
			/**/   + "                          Period5_Score,       "
			/**/   + "                          Overtime_Score,      "
			/**/   + "                          Total_Score )        "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getTeamGameUpdatePs( Connection dbConn ) throws SQLException {

		String sql = "UPDATE Teamgames_T "
		    /**/
		    /**/   + "   SET Overtime            = ?, "
		    /**/   + "       Win                 = ?, "
		    /**/   + "       Possessions         = ?, "
		    /**/   + "       Possession_Time     = ?, "
		    /**/   + "       Attempts            = ?, "
		    /**/   + "       Goals               = ?, "
		    /**/   + "       Turnovers           = ?, "
		    /**/   + "       Steals              = ?, "
		    /**/   + "       Penalties           = ?, "
		    /**/   + "       Offensive_Penalties = ?, "
		    /**/   + "       Psa                 = ?, "
		    /**/   + "       Psm                 = ?, "
		    /**/   + "       Ot_Psa              = ?, "
		    /**/   + "       Ot_Psm              = ?, "
		    /**/   + "       Period1_Score       = ?, "
		    /**/   + "       Period2_Score       = ?, "
		    /**/   + "       Period3_Score       = ?, "
		    /**/   + "       Period4_Score       = ?, "
		    /**/   + "       Period5_Score       = ?, "
		    /**/   + "       Overtime_Score      = ?, "
		    /**/   + "       Total_Score         = ?  "
		    /**/
		    /**/   + " WHERE Game_Id = ? "
		    /**/   + "   AND Team_Id = ? ";

		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getPlayerGameInsertPs( Connection dbConn ) throws SQLException {
		
		String sql = "INSERT INTO Playergames_T ( Game_Id,             "
			/**/   + "                            Year,                "
			/**/   + "                            Datestamp,           "
			/**/   + "                            Type,                "
			/**/   + "                            Player_Id,           "
			/**/   + "                            Team_Id,             "
			/**/   + "                            Injured,             "
			/**/   + "                            Started,             "
			/**/   + "                            Playing_Time,        "
			/**/   + "                            Attempts,            "
			/**/   + "                            Goals,               "
			/**/   + "                            Assists,             "
			/**/   + "                            Turnovers,           "
			/**/   + "                            Stops,               "
			/**/   + "                            Steals,              "
			/**/   + "                            Penalties,           "
			/**/   + "                            Offensive_Penalties, "
			/**/   + "                            Psa,                 "
			/**/   + "                            Psm,                 "
			/**/   + "                            Ot_Psa,              "
			/**/   + "                            Ot_Psm )             "
			/**/   + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getPlayerGameUpdatePs( Connection dbConn ) throws SQLException {

		String sql = "UPDATE Playergames_T "
		    /**/
		    /**/   + "   SET Injured             = ?, "
		    /**/   + "       Started             = ?, "
		    /**/   + "       Playing_Time        = ?, "
		    /**/   + "       Attempts            = ?, "
		    /**/   + "       Goals               = ?, "
		    /**/   + "       Assists             = ?, "
		    /**/   + "       Turnovers           = ?, "
		    /**/   + "       Stops               = ?, "
		    /**/   + "       Steals              = ?, "
		    /**/   + "       Penalties           = ?, "
		    /**/   + "       Offensive_Penalties = ?, "
		    /**/   + "       Psa                 = ?, "
		    /**/   + "       Psm                 = ?, "
		    /**/   + "       Ot_Psa              = ?, "
		    /**/   + "       Ot_Psm              = ?  "
		    /**/
		    /**/   + " WHERE Game_Id   = ? "
		    /**/   + "   AND Player_Id = ? ";

		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getInjuryInsertPs( Connection dbConn ) throws SQLException {
		
		String sql = "INSERT INTO Injuries_T ( Game_Id,             "
			/**/   + "                         Player_Id,           "
			/**/   + "                         Team_Id,             "
			/**/   + "                         Duration )           "
			/**/   + "VALUES ( ?, ?, ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getInjuryHistoryByPlayerIdSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT TG.Datestamp, TG.Year, TG.Opponent, T.Abbrev, TG.Road, I.Game_Id, I.Duration "
			/**/   + "  FROM Injuries_T I, Teamgames_T TG, Teams_T T "
			/**/   + " WHERE  I.Game_Id   = TG.Game_Id "
			/**/   + "   AND  I.Team_Id   = TG.Team_Id "
			/**/   + "   AND TG.Opponent  =  T.Team_Id "
			/**/   + "   AND TG.Year      =  T.Year    "
			/**/   + "   AND  I.Player_Id =    ?       "
			/**/   + "ORDER BY I.Game_Id ASC ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getInjuriesByPlayerIdSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT TG.Datestamp, TG.Opponent, T.Abbrev, TG.Road, I.Game_Id, I.Duration "
			/**/   + "  FROM Injuries_T I, Teamgames_T TG, Teams_T T "
			/**/   + " WHERE  I.Game_Id   = TG.Game_Id "
			/**/   + "   AND  I.Team_Id   = TG.Team_Id "
			/**/   + "   AND TG.Opponent  =  T.Team_Id "
			/**/   + "   AND TG.Year      =  T.Year    "
			/**/   + "   AND  I.Player_Id =    ?       "
			/**/   + "   AND TG.Year      =    ?       "
			/**/   + "ORDER BY I.Game_Id ASC ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getInjuriesByTeamIdSelectPs( Connection dbConn ) throws SQLException {
		
		String sql = "SELECT TG.Datestamp, P.Player_Id, P.First_Name, P.Last_Name, TG.Opponent, T.Abbrev, TG.Road, I.Game_Id, I.Duration "
			/**/   + "  FROM Injuries_T I, Teamgames_T TG, Teams_T T, Players_T P "
			/**/   + " WHERE  I.Game_Id   = TG.Game_Id   "
			/**/   + "   AND  I.Team_Id   = TG.Team_Id   "
			/**/   + "   AND TG.Opponent  =  T.Team_Id   "
			/**/   + "   AND TG.Year      =  T.Year      "
			/**/   + "   AND  I.Player_Id =  P.Player_Id "
			/**/   + "   AND TG.Year      =  P.Year      "
			/**/   + "   AND TG.Year      =    ?         "
			/**/   + "   AND  I.Team_Id   =    ?         "
			/**/   + "ORDER BY I.Game_Id ASC, P.Last_Name ASC, P.First_Name ASC ";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getCopyTeamsForNewYearCallPs( Connection dbConn ) throws SQLException {
	
		String sql = "CALL copyTeamsForNewYear( ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}

	public static PreparedStatement getCopyManagersForNewYearCallPs( Connection dbConn ) throws SQLException {
		
		String sql = "CALL copyManagersForNewYear( ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static PreparedStatement getCopyPlayersForNewYearCallPs( Connection dbConn ) throws SQLException {
		
		String sql = "CALL copyPlayersForNewYear( ?, ? )";
		
		return dbConn.prepareStatement( sql );
	}
	
	public static void closeDbStmt( Statement dbStmt ) {
		
		if ( dbStmt != null ) {
			
			try {
				dbStmt.close();
				dbStmt = null;
			}
			catch ( Exception e ) {
			}
		}
	}

	public static void closeDbRs( ResultSet dbRs ) {
		
		if ( dbRs != null ) {
			
			try {
				dbRs.close();
				dbRs = null;
			}
			catch ( Exception e ) {
			}
		}
	}
	
	public static void beginTransaction( Connection dbConn ) throws SQLException {
	
		dbConn.setAutoCommit( false );
	}
	
	public static void endTransaction( Connection dbConn ) throws SQLException {
	
		dbConn.commit();
		
		dbConn.setAutoCommit( true );
	}
	
	public static void cancelTransaction( Connection dbConn ) throws SQLException {
	
		dbConn.rollback();
		
		dbConn.setAutoCommit( true );
	}
}
