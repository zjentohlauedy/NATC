package natc.data;

public class Constants {

	public static final int DAYS_IN_PRESEASON        =  10;
	public static final int DAYS_IN_SEASON           = 100;
	public static final int GAMES_PER_TEAM           = 100;
	public static final int GAMES_PER_DAY            =  20;
	public static final int GAMES_PER_SEASON         =    (GAMES_PER_DAY * DAYS_IN_SEASON);
	public static final int GAMES_PER_PRESEASON      =    (GAMES_PER_DAY * DAYS_IN_PRESEASON);
	public static final int NUMBER_OF_CONFERENCES    =   2;
	public static final int DIVISIONS_PER_CONFERENCE =   2;
	public static final int NUMBER_OF_DIVISIONS      =    (DIVISIONS_PER_CONFERENCE * NUMBER_OF_CONFERENCES);
	public static final int TEAMS_PER_DIVISION       =  10;
	public static final int NUMBER_OF_TEAMS          =    (NUMBER_OF_DIVISIONS * TEAMS_PER_DIVISION);
	public static final int TEAMS_PER_CONFERENCE     =    (DIVISIONS_PER_CONFERENCE * TEAMS_PER_DIVISION);
	public static final int PLAYERS_PER_TEAM         =  10;
	public static final int ROUNDS_PER_DIV_SERIES    =   7;
	public static final int ROUNDS_PER_CONF_SERIES   =   3;
	public static final int OUT_OF_CONFERENCE_GAMES  =   7;
	public static final int PLAYOFF_ROUNDS           =   4;
	public static final int PLAYOFF_TEAMS_ROUND_1    =  16;
	public static final int PLAYOFF_GAMES_ROUND_1    =   7;
	public static final int PLAYOFF_TEAMS_ROUND_2    =   8;
	public static final int PLAYOFF_GAMES_ROUND_2    =   5;
	public static final int PLAYOFF_TEAMS_ROUND_3    =   4;
	public static final int PLAYOFF_GAMES_ROUND_3    =   3;
	public static final int PLAYOFF_TEAMS_ROUND_4    =   2;
	public static final int ROUND_1_WINS_TO_ADVANCE  =   4;
	public static final int ROUND_2_WINS_TO_ADVANCE  =   3;
	public static final int ROUND_3_WINS_TO_ADVANCE  =   2;
	public static final int MAX_PLAYOFF_TEAMS        =  16;
	public static final int MAX_GAMES_PER_PLAYOFF    =   7;
	public static final int POINTS_PER_GOAL          =   3;
	public static final int SECONDS_PER_DAY          =    (60 * 60 * 24);
}
