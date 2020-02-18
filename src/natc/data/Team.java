package natc.data;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Team {

	private String year;
	
	private int    team_id;
	
	private String location;
	private String name;
	private String abbrev;
	
	private int    conference;
	private int    division;
	
	private int    games;
	private int    wins;
	private int    losses;
	private int    division_wins;
	private int    division_losses;
	private int    ooc_wins;
	private int    ooc_losses;
	private int    ot_wins;
	private int    ot_losses;
	private int    road_wins;
	private int    road_losses;
	private int    home_wins;
	private int    home_losses;
	
	private int    division_rank; // 1-10 position in div at end of season
	
	// null or 0, 1 for round 1, 2 for round 2, 3 for div champ, 4 for conf champ, 5 for natc champ
	private int    playoff_rank;
	private int    playoff_games;
	private int    round1_wins;
	private int    round2_wins;
	private int    round3_wins;
	
	private int    preseason_games;
	private int    preseason_wins;
	private int    preseason_losses;
	
	private double offense;
	private double defense;
	private double discipline;
	private double ps_offense;
	private double ps_defense;
	
	private List   players;
	
	private TeamGame game;
	
	public Team() {
	
		this.year             = null;
		this.team_id          = 0;
		this.location         = null;
		this.name             = null;
		this.abbrev           = null;
		this.conference       = 0;
		this.division         = 0;
		this.games            = 0;
		this.wins             = 0;
		this.losses           = 0;
		this.division_wins    = 0;
		this.division_losses  = 0;
		this.ooc_wins         = 0;
		this.ooc_losses       = 0;
		this.ot_wins          = 0;
		this.ot_losses        = 0;
		this.road_wins        = 0;
		this.road_losses      = 0;
		this.home_wins        = 0;
		this.home_losses      = 0;
		this.division_rank    = 0;
		this.playoff_rank     = 0;
		this.playoff_games    = 0;
		this.round1_wins      = 0;
		this.round2_wins      = 0;
		this.round3_wins      = 0;
		this.preseason_games  = 0;
		this.preseason_wins   = 0;
		this.preseason_losses = 0;
		this.offense          = 0.0;
		this.defense          = 0.0;
		this.discipline       = 0.0;
		this.ps_offense       = 0.0;
		this.ps_defense       = 0.0;
		this.players          = null;
		this.game             = null;
	}

	public void initTeamGame( Team opponent, int game_id, String year, int game_type, Date game_date, boolean isRoad ) {
	
		TeamGame teamGame = new TeamGame();
		
		teamGame.setGame_id(       game_id               );
		teamGame.setYear(          year                  );
		teamGame.setDatestamp(     game_date             );
		teamGame.setType(          game_type             );
		teamGame.setPlayoff_round( this.playoff_rank     );
		teamGame.setTeam_id(       this.team_id          );
		teamGame.setOpponent(      opponent.getTeam_id() );
		teamGame.setRoad(          isRoad                );
		teamGame.setScore(         new Score()           );
		
		/*
		 *  base probabilities
		 */
		//               att   sco   trn   stl  dpen  open
		double[] tbl = { 0.37, 0.26, 0.20, 0.0, 0.17, 0.0 };
		
		// calc general probs, failed attempts, scoring attempts, turnovers and penalties
		tbl[0] = tbl[0] + ((this.offense - opponent.getDefense()) / 600.0) * 2.0;
		tbl[1] = tbl[1] + ((this.offense - opponent.getDefense()) / 600.0);
		tbl[2] = tbl[2] + ((this.offense - opponent.getDefense()) / 200.0);
		tbl[4] = tbl[4] + (2.0 / opponent.getDiscipline()) - 0.06;
		tbl[2] = tbl[2] + 0.17 - tbl[4];
		
		// home team advantage
		if ( isRoad ) {
			
			switch ( game_type ) {
			
			case TeamGame.gt_Preseason:                                       break;
			case TeamGame.gt_RegularSeason: tbl[1] -= 0.006; tbl[2] += 0.006; break;
			case TeamGame.gt_Postseason:    tbl[1] -= 0.01;  tbl[2] += 0.01;  break;
			case TeamGame.gt_Allstar:                                         break;
			}
		}
		else {

			switch ( game_type ) {
			
			case TeamGame.gt_Preseason:                                       break;
			case TeamGame.gt_RegularSeason: tbl[1] += 0.006; tbl[2] -= 0.006; break;
			case TeamGame.gt_Postseason:    tbl[1] -= 0.01;  tbl[2] += 0.01;  break;
			case TeamGame.gt_Allstar:                                         break;
			}
		}
		
		// break turnovers into turnovers and steals
		tbl[3] = tbl[2] * (0.45 + ((opponent.getDefense() - this.offense) / 100.0));
		tbl[3] = tbl[3] - (tbl[3] * (2.0 / opponent.getDiscipline()));
		tbl[2] = tbl[2] - tbl[3];
		
		// break penalties into defensive and offensive
		tbl[5] = tbl[4] * (2.0 / this.discipline);
		tbl[4] = tbl[4] - tbl[5];
		
		teamGame.setPb_table( tbl );
		
		this.game = teamGame;
	}
	
	public void initPlayerGames() {
	
		if ( this.players == null ) return;
		
		Iterator i = this.players.iterator();
		
		while ( i.hasNext() ) {
		
			Player player = (Player)i.next();
			
			player.initPlayerGame( this.game );
		}
	}
	
	public void distributeTime( int time ) {

		Iterator i = this.players.iterator();
		
		while ( i.hasNext() ) {
			
			Player p = (Player)i.next();
			
			p.getGame().setPlaying_time( p.getGame().getPlaying_time() + time );
		}
	}
	
	public Player distributeAttempt() {
		
		Player player = null;
		
		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += p.getRating();
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getRating();
			
			if ( x <= 0 ) {
			
				p.getGame().setAttempts( p.getGame().getAttempts() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public Player distributeStop() {
		
		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += p.getTackling();
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getTackling();
			
			if ( x <= 0 ) {
			
				p.getGame().setStops( p.getGame().getStops() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public Player distributeGoal() {

		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_scoring = 0.0;
		double total_passing = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_scoring += p.getScoring();
			total_passing += p.getPassing();
		}
		
		// Goal
		i = this.players.iterator();
		
		double x = Math.random() * total_scoring;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getScoring();
			
			if ( x <= 0 ) {
			
				p.getGame().setAttempts( p.getGame().getAttempts() + 1 );
				p.getGame().setGoals(    p.getGame().getGoals()    + 1 );
				
				player = p;
				
				break;
			}
		}

		// Assist
		i = this.players.iterator();
		
		x = Math.random() * total_passing;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getPassing();
			
			if ( x <= 0 ) {
			
				if ( p != player ) {
					
					p.getGame().setAssists( p.getGame().getAssists() + 1 );
				}
				
				break;
			}
		}
				
		return player;
	}
	
	public Player distributeTurnover() {

		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += p.getTurnoverRating();
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getTurnoverRating();
			
			if ( x <= 0 ) {
			
				p.getGame().setTurnovers( p.getGame().getTurnovers() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public Player distributeSteal() {

		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += p.getStealing();
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getStealing();
			
			if ( x <= 0 ) {
			
				p.getGame().setSteals( p.getGame().getSteals() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public Player distributePenalty() {

		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += (100.0 - p.getDiscipline());
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= (100.0 - p.getDiscipline());
			
			if ( x <= 0 ) {
			
				p.getGame().setPenalties( p.getGame().getPenalties() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public Player distributePenaltyShot() {

		Player player = null;

		Iterator i = this.players.iterator();
		
		double total_rating = 0.0;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			total_rating += p.getRating();
		}
		
		i = this.players.iterator();
		
		double x = Math.random() * total_rating;
		
		while ( i.hasNext() ) {
		
			Player p = (Player)i.next();
			
			x -= p.getRating();
			
			if ( x <= 0 ) {
			
				p.getGame().setPsa( p.getGame().getPsa() + 1 );
				
				player = p;
				
				break;
			}
		}
		
		return player;
	}
	
	public int getPostseason_losses() {
		return playoff_games - (round1_wins + round2_wins + round3_wins);
	}

	public int getPostseason_wins() {
		return round1_wins + round2_wins + round3_wins;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public int getConference() {
		return conference;
	}

	public void setConference(int conference) {
		this.conference = conference;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getDiscipline() {
		return discipline;
	}

	public void setDiscipline(double discipline) {
		this.discipline = discipline;
	}

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public int getDivision_losses() {
		return division_losses;
	}

	public void setDivision_losses(int division_losses) {
		this.division_losses = division_losses;
	}

	public int getDivision_wins() {
		return division_wins;
	}

	public void setDivision_wins(int division_wins) {
		this.division_wins = division_wins;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getHome_losses() {
		return home_losses;
	}

	public void setHome_losses(int home_losses) {
		this.home_losses = home_losses;
	}

	public int getHome_wins() {
		return home_wins;
	}

	public void setHome_wins(int home_wins) {
		this.home_wins = home_wins;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOffense() {
		return offense;
	}

	public void setOffense(double offense) {
		this.offense = offense;
	}

	public int getOoc_losses() {
		return ooc_losses;
	}

	public void setOoc_losses(int ooc_losses) {
		this.ooc_losses = ooc_losses;
	}

	public int getOoc_wins() {
		return ooc_wins;
	}

	public void setOoc_wins(int ooc_wins) {
		this.ooc_wins = ooc_wins;
	}

	public int getOt_losses() {
		return ot_losses;
	}

	public void setOt_losses(int ot_losses) {
		this.ot_losses = ot_losses;
	}

	public int getOt_wins() {
		return ot_wins;
	}

	public void setOt_wins(int ot_wins) {
		this.ot_wins = ot_wins;
	}

	public List getPlayers() {
		return players;
	}

	public void setPlayers(List players) {
		this.players = players;
	}

	public int getPlayoff_rank() {
		return playoff_rank;
	}

	public void setPlayoff_rank(int playoff_rank) {
		this.playoff_rank = playoff_rank;
	}

	public double getPs_defense() {
		return ps_defense;
	}

	public void setPs_defense(double ps_defense) {
		this.ps_defense = ps_defense;
	}

	public double getPs_offense() {
		return ps_offense;
	}

	public void setPs_offense(double ps_offense) {
		this.ps_offense = ps_offense;
	}

	public int getRoad_losses() {
		return road_losses;
	}

	public void setRoad_losses(int road_losses) {
		this.road_losses = road_losses;
	}

	public int getRoad_wins() {
		return road_wins;
	}

	public void setRoad_wins(int road_wins) {
		this.road_wins = road_wins;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getPreseason_games() {
		return preseason_games;
	}

	public void setPreseason_games(int preseason_games) {
		this.preseason_games = preseason_games;
	}

	public int getPreseason_losses() {
		return preseason_losses;
	}

	public void setPreseason_losses(int preseason_losses) {
		this.preseason_losses = preseason_losses;
	}

	public int getPreseason_wins() {
		return preseason_wins;
	}

	public void setPreseason_wins(int preseason_wins) {
		this.preseason_wins = preseason_wins;
	}

	public int getPlayoff_games() {
		return playoff_games;
	}

	public void setPlayoff_games(int playoff_games) {
		this.playoff_games = playoff_games;
	}

	public int getRound1_wins() {
		return round1_wins;
	}

	public void setRound1_wins(int round1_wins) {
		this.round1_wins = round1_wins;
	}

	public int getRound2_wins() {
		return round2_wins;
	}

	public void setRound2_wins(int round2_wins) {
		this.round2_wins = round2_wins;
	}

	public int getRound3_wins() {
		return round3_wins;
	}

	public void setRound3_wins(int round3_wins) {
		this.round3_wins = round3_wins;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getDivision_rank() {
		return division_rank;
	}

	public void setDivision_rank(int division_rank) {
		this.division_rank = division_rank;
	}

	public TeamGame getGame() {
		return game;
	}

	public void setGame(TeamGame game) {
		this.game = game;
	}

}
