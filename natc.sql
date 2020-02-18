CREATE TABLE Teams_T
(
    Team_Id          INTEGER,
    Year                CHAR( 4),
    Location         VARCHAR(30),
    Name             VARCHAR(30),
    Abbrev           VARCHAR( 5),
    Conference       INTEGER,
    Division         INTEGER,
    Allstar_Team     INTEGER,
    Preseason_Games  INTEGER,
    Preseason_Wins   INTEGER,
    Preseason_Losses INTEGER,
    Games            INTEGER,
    Wins             INTEGER,
    Losses           INTEGER,
    Div_Wins         INTEGER,
    Div_Losses       INTEGER,
    Ooc_Wins         INTEGER,
    Ooc_Losses       INTEGER,
    Ot_Wins          INTEGER,
    Ot_Losses        INTEGER,
    Road_Wins        INTEGER,
    Road_Losses      INTEGER,
    Home_Wins        INTEGER,
    Home_Losses      INTEGER,
    Division_Rank    INTEGER,
    Playoff_Rank     INTEGER,
    Playoff_Games    INTEGER,
    Round1_Wins      INTEGER,
    Round2_Wins      INTEGER,
    Round3_Wins      INTEGER,
    Expectation      DOUBLE,
    Drought          INTEGER
);

CREATE INDEX Team_Year    ON Teams_T ( Year    );
CREATE INDEX Team_Team_Id ON Teams_T ( Team_Id );

CREATE TABLE Managers_T
(
    Manager_Id      INTEGER,
    Team_Id         INTEGER,
    Player_Id       INTEGER,
    Year               CHAR( 4),
    First_Name      VARCHAR(30),
    Last_Name       VARCHAR(30),
    Age             INTEGER,
    Offense         DOUBLE,
    Defense         DOUBLE,
    Intangible      DOUBLE,
    Penalties       DOUBLE,
    Vitality        DOUBLE,
    Style           INTEGER,
    New_Hire        INTEGER,
    Released        INTEGER,
    Retired         INTEGER,
    Former_Team_Id  INTEGER,
    Allstar_Team_Id INTEGER,
    Award           INTEGER,
    Seasons         INTEGER,
    Score           INTEGER,
    Total_Seasons   INTEGER,
    Total_Score     INTEGER
);

CREATE INDEX Manager_Year       ON Managers_T ( Year       );
CREATE INDEX Manager_Manager_Id ON Managers_T ( Manager_Id );
CREATE INDEX Manager_Player_Id  ON Managers_T ( Player_Id  );
CREATE INDEX Manager_Team_Id    ON Managers_T ( Team_Id    );

CREATE TABLE Players_T
(
    Player_Id         INTEGER,
    Team_Id           INTEGER,
    Year                 CHAR( 4),
    First_Name        VARCHAR(30),
    Last_Name         VARCHAR(30),
    Age               INTEGER,
    Scoring           DOUBLE,
    Passing           DOUBLE,
    Blocking          DOUBLE,
    Tackling          DOUBLE,
    Stealing          DOUBLE,
    Presence          DOUBLE,
    Discipline        DOUBLE,
    Penalty_Shot      DOUBLE,
    Penalty_Offense   DOUBLE,
    Penalty_Defense   DOUBLE,
    Endurance         DOUBLE,
    Confidence        DOUBLE,
    Vitality          DOUBLE,
    Durability        DOUBLE,
    Rookie            INTEGER,
    Injured           INTEGER,
    Return_Date       DATE,
    Free_Agent        INTEGER,
    Signed            INTEGER,
    Released          INTEGER,
    Retired           INTEGER,
    Former_Team_Id    INTEGER,
    Allstar_Team_Id   INTEGER,
    Allstar_Alternate INTEGER, 
    Award             INTEGER,
    Draft_Pick        INTEGER,
    Seasons_Played    INTEGER
);

CREATE INDEX Player_Year      ON Players_T ( Year      );
CREATE INDEX Player_Player_Id ON Players_T ( Player_Id );
CREATE INDEX Player_Team_Id   ON Players_T ( Team_Id   );

CREATE TABLE GameState_T
(
    Game_Id             INTEGER,
    Sequence            INTEGER,
    Period              INTEGER,
    Overtime            INTEGER,
    Time_Remaining      INTEGER,
    Clock_Stopped       INTEGER,
    Possession          INTEGER,
    Last_Event          VARCHAR(200)
);

CREATE INDEX GameState_Game_Id ON GameState_T ( Game_Id   );

CREATE TABLE TeamGames_T
(
    Game_Id             INTEGER,
    Year                   CHAR( 4),
    Datestamp           DATE,
    Type                INTEGER,
    Playoff_Round       INTEGER,
    Team_Id             INTEGER,
    Opponent            INTEGER,
    Road                INTEGER,
    Overtime            INTEGER,
    Win                 INTEGER,
    Possessions         INTEGER,
    Possession_Time     INTEGER,
    Attempts            INTEGER,
    Goals               INTEGER,
    Turnovers           INTEGER,
    Steals              INTEGER,
    Penalties           INTEGER,
    Offensive_Penalties INTEGER,
    Psa                 INTEGER,
    Psm                 INTEGER,
    Ot_Psa              INTEGER,
    Ot_Psm              INTEGER,
    Period1_Score       INTEGER,
    Period2_Score       INTEGER,
    Period3_Score       INTEGER,
    Period4_Score       INTEGER,
    Period5_Score       INTEGER,
    Overtime_Score      INTEGER,
    Total_Score         INTEGER
);

CREATE INDEX TeamGames_Game_Id   ON TeamGames_T ( Game_Id   );
CREATE INDEX TeamGames_Year      ON TeamGames_T ( Year      );
CREATE INDEX TeamGames_Datestamp ON TeamGames_T ( Datestamp );

CREATE TABLE Team_Offense_Sum_T
(
    Year                   CHAR( 4),
    Type                INTEGER,
    Team_Id             INTEGER,
    Games               INTEGER,
    Possessions         INTEGER,
    Possession_Time     INTEGER,
    Attempts            INTEGER,
    Goals               INTEGER,
    Turnovers           INTEGER,
    Steals              INTEGER,
    Penalties           INTEGER,
    Offensive_Penalties INTEGER,
    Psa                 INTEGER,
    Psm                 INTEGER,
    Ot_Psa              INTEGER,
    Ot_Psm              INTEGER,
    Score               INTEGER
);

CREATE INDEX Team_Offense_Year    ON Team_Offense_Sum_T ( Year    );
CREATE INDEX Team_Offense_Team_Id ON Team_Offense_Sum_T ( Team_Id );

CREATE TABLE Team_Defense_Sum_T
(
    Year                   CHAR( 4),
    Type                INTEGER,
    Team_Id             INTEGER,
    Games               INTEGER,
    Possessions         INTEGER,
    Possession_Time     INTEGER,
    Attempts            INTEGER,
    Goals               INTEGER,
    Turnovers           INTEGER,
    Steals              INTEGER,
    Penalties           INTEGER,
    Offensive_Penalties INTEGER,
    Psa                 INTEGER,
    Psm                 INTEGER,
    Ot_Psa              INTEGER,
    Ot_Psm              INTEGER,
    Score               INTEGER
);

CREATE INDEX Team_Defense_Year    ON Team_Defense_Sum_T ( Year    );
CREATE INDEX Team_Defense_Team_Id ON Team_Defense_Sum_T ( Team_Id );

CREATE TABLE PlayerGames_T
(
    Game_Id             INTEGER,
    Year                   CHAR( 4),
    Datestamp              DATE,
    Type                INTEGER,
    Player_Id           INTEGER,
    Team_Id             INTEGER,
    Injured             INTEGER,
    Started             INTEGER,
    Playing_Time        INTEGER,
    Attempts            INTEGER,
    Goals               INTEGER,
    Assists             INTEGER,
    Turnovers           INTEGER,
    Stops               INTEGER,
    Steals              INTEGER,
    Penalties           INTEGER,
    Offensive_Penalties INTEGER,
    Psa                 INTEGER,
    Psm                 INTEGER,
    Ot_Psa              INTEGER,
    Ot_Psm              INTEGER
);

CREATE INDEX PlayerGames_Game_Id   ON PlayerGames_T ( Game_Id   );
CREATE INDEX PlayerGames_Year      ON PlayerGames_T ( Year      );
CREATE INDEX PlayerGames_Datestamp ON PlayerGames_T ( Datestamp );
CREATE INDEX PlayerGames_Player_Id ON PlayerGames_T ( Player_Id );
CREATE INDEX PlayerGames_Team_Id   ON PlayerGames_T ( Team_Id   );

CREATE TABLE Player_Stats_Sum_T
(
    Year                   CHAR( 4),
    Type                INTEGER,
    Player_Id           INTEGER,
    Team_Id             INTEGER,
    Games               INTEGER,
    Games_Started       INTEGER,
    Playing_Time        INTEGER,
    Attempts            INTEGER,
    Goals               INTEGER,
    Assists             INTEGER,
    Turnovers           INTEGER,
    Stops               INTEGER,
    Steals              INTEGER,
    Penalties           INTEGER,
    Offensive_Penalties INTEGER,
    Psa                 INTEGER,
    Psm                 INTEGER,
    Ot_Psa              INTEGER,
    Ot_Psm              INTEGER
);

CREATE INDEX Player_Stats_Year      ON Player_Stats_Sum_T ( Year      );
CREATE INDEX Player_Stats_Player_Id ON Player_Stats_Sum_T ( Player_Id );

CREATE TABLE Injuries_T
(
    Game_Id             INTEGER,
    Player_Id           INTEGER,
    Team_Id             INTEGER,
    Duration            INTEGER
);

CREATE INDEX Injury_Game_Id   ON Injuries_T ( Game_Id   );
CREATE INDEX Injury_Player_Id ON Injuries_T ( Player_Id );
CREATE INDEX Injury_Team_Id   ON Injuries_T ( Team_Id   );

CREATE TABLE Schedule_T
(
    Year         CHAR( 4),
    Sequence  INTEGER,
    Type      INTEGER,
    Data      VARCHAR(50),
    Scheduled    DATE,
    Status    INTEGER
);

CREATE INDEX Schedule_Year     ON Schedule_T ( Year     );
CREATE INDEX Schedule_Sequence ON Schedule_T ( Sequence );



CREATE FUNCTION calcConfidenceCoef( Confidence DOUBLE, Age INT )
RETURNS DOUBLE
DETERMINISTIC
BEGIN
  DECLARE result_var DOUBLE;
  DECLARE years_var  INT;

  SET years_var  = Age - 17;
  SET result_var = (1 - ((1 - Confidence) / ((years_var * years_var) + 1)));

  RETURN result_var;
END;


CREATE FUNCTION calcAgeCoef( Vitality DOUBLE, Age INT )
RETURNS DOUBLE
DETERMINISTIC
BEGIN
 DECLARE result_var DOUBLE;
 DECLARE cutoff_var INT;

 SET cutoff_var = CEIL(20 + (15 * Vitality));

 IF ( Age > cutoff_var ) THEN
  SET result_var = (1 - ((Age - cutoff_var) * (0.05 + 0.05 * Vitality)));
 ELSE
  SET result_var = 1;
 END IF;

 RETURN result_var;
END;


CREATE FUNCTION calcCoef( Confidence DOUBLE, Vitality DOUBLE, Age INT )
RETURNS DOUBLE
DETERMINISTIC
BEGIN
 DECLARE result_var DOUBLE;

 SET result_var = calcConfidenceCoef( Confidence, Age ) * calcAgeCoef( Vitality, Age );

 RETURN result_var;
END;

CREATE PROCEDURE copyTeamsForNewYear ( IN lastYear CHAR(4), IN thisYear CHAR(4) )
BEGIN
  DECLARE team_id_var      INT;
  DECLARE location_var     VARCHAR(30);
  DECLARE name_var         VARCHAR(30);
  DECLARE abbrev_var       VARCHAR( 5);
  DECLARE conference_var   INT;
  DECLARE division_var     INT;
  DECLARE allstar_team_var INT;
  DECLARE expectation_var  DOUBLE;
  DECLARE drought_var      INT;

  DECLARE done             INT DEFAULT 0;

  DECLARE teams_cur CURSOR FOR SELECT Team_Id, Location, Name, Abbrev, Conference, Division, Allstar_Team, Expectation, Drought FROM Teams_T WHERE Year = lastYear;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  OPEN teams_cur;

  REPEAT
    FETCH teams_cur INTO team_id_var, location_var, name_var, abbrev_var, conference_var, division_var, allstar_team_var, expectation_var, drought_var;

    IF NOT done THEN
      INSERT INTO Teams_T ( Team_Id,         Year, Location,     Name,     Abbrev,     Conference,     Division,     Allstar_Team,     Expectation,     Drought     )
                   VALUES ( team_id_var, thisYear, location_var, name_var, abbrev_var, conference_var, division_var, allstar_team_var, expectation_var, drought_var );
    END IF;
  UNTIL done END REPEAT;

  CLOSE teams_cur;
END;


CREATE PROCEDURE copyManagersForNewYear ( IN lastYear CHAR(4), IN thisYear CHAR(4) )
BEGIN
  DECLARE manager_id_var      INT;
  DECLARE team_id_var         INT;
  DECLARE player_id_var       INT;
  DECLARE first_name_var      VARCHAR(30);
  DECLARE last_name_var       VARCHAR(30);
  DECLARE age_var             INT;
  DECLARE offense_var         DOUBLE;
  DECLARE defense_var         DOUBLE;
  DECLARE intangible_var      DOUBLE;
  DECLARE penalties_var       DOUBLE;
  DECLARE vitality_var        DOUBLE;
  DECLARE style_var           INT;
  DECLARE seasons_var         INT;
  DECLARE score_var           INT;
  DECLARE total_seasons_var   INT;
  DECLARE total_score_var     INT;

  DECLARE done           INT DEFAULT 0;

  DECLARE managers_cur CURSOR FOR
    SELECT Manager_Id,
           Team_Id,
           Player_Id,
           First_Name,
           Last_Name,
           Age,
           Offense,
           Defense,
           Intangible,
           Penalties,
           Vitality,
           Style,
           Seasons,
           Score,
           Total_Seasons,
           Total_Score
    FROM   Managers_T
    WHERE  Year    = lastYear
    AND    Retired = 0;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  OPEN managers_cur;

  REPEAT
    FETCH managers_cur INTO manager_id_var,
                            team_id_var,
                            player_id_var,
                            first_name_var,
                            last_name_var,
                            age_var,
                            offense_var,
                            defense_var,
                            intangible_var,
                            penalties_var,
                            vitality_var,
                            style_var,
                            seasons_var,
                            score_var,
                            total_seasons_var,
                            total_score_var;

    IF NOT done THEN
      INSERT INTO Managers_T ( Manager_Id,
                               Team_Id,
                               Player_Id,
                               Year,
                               First_Name,
                               Last_Name,
                               Age,
                               Offense,
                               Defense,
                               Intangible,
                               Penalties,
                               Vitality,
                               Style,
                               New_Hire,
                               Released,
                               Retired,
                               Seasons,
                               Score,
                               Total_Seasons,
                               Total_Score
                             )
                      VALUES ( manager_id_var,
                               team_id_var,
                               player_id_var,
                               thisYear,
                               first_name_var,
                               last_name_var,
                               age_var,
                               offense_var,
                               defense_var,
                               intangible_var,
                               penalties_var,
                               vitality_var,
                               style_var,
                               0,
                               0,
                               0,
                               seasons_var,
                               score_var,
                               total_seasons_var,
                               total_score_var
                             );
    END IF;
  UNTIL done END REPEAT;

  CLOSE managers_cur;
END;


CREATE PROCEDURE copyPlayersForNewYear ( IN lastYear CHAR(4), IN thisYear CHAR(4) )
BEGIN
  DECLARE player_id_var       INT;
  DECLARE team_id_var         INT;
  DECLARE first_name_var      VARCHAR(30);
  DECLARE last_name_var       VARCHAR(30);
  DECLARE age_var             INT;
  DECLARE scoring_var         DOUBLE;
  DECLARE passing_var         DOUBLE;
  DECLARE blocking_var        DOUBLE;
  DECLARE tackling_var        DOUBLE;
  DECLARE stealing_var        DOUBLE;
  DECLARE presence_var        DOUBLE;
  DECLARE discipline_var      DOUBLE;
  DECLARE penalty_shot_var    DOUBLE;
  DECLARE penalty_offense_var DOUBLE;
  DECLARE penalty_defense_var DOUBLE;
  DECLARE endurance_var       DOUBLE;
  DECLARE confidence_var      DOUBLE;
  DECLARE vitality_var        DOUBLE;
  DECLARE durability_var      DOUBLE;
  DECLARE seasons_played_var  INT;

  DECLARE done           INT DEFAULT 0;

  DECLARE players_cur CURSOR FOR
    SELECT Player_Id,
           Team_Id,
           First_Name,
           Last_Name,
           Age,
           Scoring,
           Passing,
           Blocking,
           Tackling,
           Stealing,
           Presence,
           Discipline,
           Penalty_Shot,
           Penalty_Offense,
           Penalty_Defense,
           Endurance,
           Confidence,
           Vitality,
           Durability,
           Seasons_Played
    FROM   Players_T
    WHERE  Year    = lastYear
    AND    Retired = 0;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  OPEN players_cur;

  REPEAT
    FETCH players_cur INTO player_id_var,
                           team_id_var,
                           first_name_var,
                           last_name_var,
                           age_var,
                           scoring_var,
                           passing_var,
                           blocking_var,
                           tackling_var,
                           stealing_var,
                           presence_var,
                           discipline_var,
                           penalty_shot_var,
                           penalty_offense_var,
                           penalty_defense_var,
                           endurance_var,
                           confidence_var,
                           vitality_var,
                           durability_var,
                           seasons_played_var;

    IF NOT done THEN
      INSERT INTO Players_T ( Player_Id,
                              Team_Id,
                              Year,
                              First_Name,
                              Last_Name,
                              Age,
                              Scoring,
                              Passing,
                              Blocking,
                              Tackling,
                              Stealing,
                              Presence,
                              Discipline,
                              Penalty_Shot,
                              Penalty_Offense,
                              Penalty_Defense,
                              Endurance,
                              Confidence,
                              Vitality,
                              Durability,
                              Rookie,
                              Retired,
                              Seasons_Played
                            )
                     VALUES ( player_id_var,
                              team_id_var,
                              thisYear,
                              first_name_var,
                              last_name_var,
                              age_var,
                              scoring_var,
                              passing_var,
                              blocking_var,
                              tackling_var,
                              stealing_var,
                              presence_var,
                              discipline_var,
                              penalty_shot_var,
                              penalty_offense_var,
                              penalty_defense_var,
                              endurance_var,
                              confidence_var,
                              vitality_var,
                              durability_var,
                              0,
                              0,
                              seasons_played_var
                            );
    END IF;
  UNTIL done END REPEAT;

  CLOSE players_cur;
END;
