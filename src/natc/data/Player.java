package natc.data;

import natc.utility.Utility;

public class Player {

	public static final double MAX_FATIGUE_RATE = (1.0 / 300.0); // 5 minutes
	public static final double MIN_FATIGUE_RATE = (1.0 / 420.0); // 7 minutes
	
	public static final double MAX_REST_RATE    = (1.0 / 300.0); // 5 minutes
	public static final double MIN_REST_RATE    = (1.0 / 420.0); // 7 minutes
	
	private int     player_id;
	
	private int     team_id;
	
	private String  year;
	
	private String  first_name;
	private String  last_name;
	
	private int     age;
	
	// Offense
	private double  scoring;         // attempts and goals
	private double  passing;         // assists, indirect turnovers
	private double  blocking;        // intangible
	
	// Defense
	private double  tackling;        // stops
	private double  stealing;        // steals
	private double  presence;        // intangible
	
	// Penalties
	private double  discipline;      // indirect penalties, indirect turnovers
	private double  penalty_shot;    // psm
	private double  penalty_offense; // intangible
	private double  penalty_defense; // intangible
	
	private double  endurance;
	private double  fatigue;
	private boolean playing;
	private boolean resting;
	
	private boolean rookie;
	private boolean retired;
	
	private int     award;
	private int     draft_pick;
	private int     seasons_played;
	private int     allstar_team_id;
	
	// Tracking roster cuts
	private boolean released;
	private int     released_by;
	
	private int     score; // not part of player record - used for caching

	private PlayerGame game;
	
	public Player() {
	
		this.player_id       = 0;
		this.team_id         = 0;
		this.year            = null;
		this.first_name      = null;
		this.last_name       = null;
		this.age             = 0;
		this.scoring         = 0;
		this.passing         = 0;
		this.blocking        = 0;
		this.tackling        = 0;
		this.stealing        = 0;
		this.presence        = 0;
		this.discipline      = 0;
		this.penalty_shot    = 0;
		this.penalty_offense = 0;
		this.penalty_defense = 0;
		this.endurance       = 0;
		this.fatigue         = 0;
		this.playing         = false;
		this.resting         = false;
		this.rookie          = false;
		this.retired         = false;
		this.award           = 0;
		this.draft_pick      = 0;
		this.seasons_played  = 0;
		this.allstar_team_id = 0;
		this.score           = 0;
		this.released        = false;
		this.game            = null;
	}
	
	public Player( int player_id, String first_name, String last_name, int team_id ) {
	
		this.player_id       = player_id;
		this.team_id         = team_id;
		this.year            = null;
		this.first_name      = first_name;
		this.last_name       = last_name;
		this.age             = 25;
		this.scoring         = 30.0;
		this.passing         = 30.0;
		this.blocking        = 30.0;
		this.tackling        = 30.0;
		this.stealing        = 30.0;
		this.presence        = 30.0;
		this.discipline      = 30.0;
		this.penalty_shot    = 30.0;
		this.penalty_offense = 30.0;
		this.penalty_defense = 30.0;
		this.endurance       = 30.0;
		this.fatigue         = 30.0;
		this.playing         = false;
		this.resting         = false;
		this.rookie          = true;
		this.retired         = false;
		this.award           = 0;
		this.draft_pick      = 0;
		this.seasons_played  = 0;
		this.allstar_team_id = 0;
		this.score           = 0;
		this.released        = false;
		this.game            = null;
		
		adjustAttributes();
		
		// Age initially set to 25 for call to adjustAttributes, now set to new
		// player age of 17
		this.age = 17;
	}

	public double getRating() {
	
		double rating = (this.scoring + this.passing + this.blocking) * 2.0;
		
		rating += (this.tackling + this.stealing + this.presence) * 2.0;
		
		rating += this.discipline;
		
		rating += this.penalty_shot + this.penalty_offense + this.penalty_defense;
		
		rating /= 16.0;
		
		return rating;
	}
	
	public double getTurnoverRating() {
	
		// The inverse of discipline and passing
		return 100.0 - ((this.discipline + this.passing) /2.0);
	}
	
	private double adjustAttribute( double old_value )
	{
		double next_roll;

		/*
		 * Players under 21 will only get better, but if the attribute is already over 40
		 * it cannot be increased. So keep it the same. The same idea with players over
		 * 30 with attributes under 25, just keep it the same.
		 */
		if ( this.age < 21  &&  old_value > 40.0 ) return old_value;
		if ( this.age > 30  &&  old_value < 25.0 ) return old_value;

		next_roll = Utility.roll( 10.0 ) - 5.0;

		/*
		 * As mentioned above, players under 21 will only increase, and players over 30
		 * will only decrease. Players from 21 to 30 will adjust in either direction
		 * with the exception of attributes over 40 or under 25 which will only decrease
		 * or increase respectively.
		 */
		/**/ if ( this.age < 21 ) next_roll =  Math.abs( next_roll );
		else if ( this.age > 30 ) next_roll = -Math.abs( next_roll );
		else {
			
			if ( old_value > 40.0 ) next_roll = -Math.abs( next_roll );
			if ( old_value < 25.0 ) next_roll =  Math.abs( next_roll );
		}

		return old_value + next_roll;
	}

	private void adjustAttributes() {
	
		this.scoring         = adjustAttribute( this.scoring         );
		this.passing         = adjustAttribute( this.passing         );
		this.blocking        = adjustAttribute( this.blocking        );
		this.tackling        = adjustAttribute( this.tackling        );
		this.stealing        = adjustAttribute( this.stealing        );
		this.presence        = adjustAttribute( this.presence        );
		this.discipline      = adjustAttribute( this.discipline      );
		this.penalty_shot    = adjustAttribute( this.penalty_shot    );
		this.penalty_offense = adjustAttribute( this.penalty_offense );
		this.penalty_defense = adjustAttribute( this.penalty_defense );
		this.endurance       = adjustAttribute( this.endurance       );
	}
	
	public void agePlayer() {
	
		this.age++;
		
		adjustAttributes();
	}

	public void initPlayerGame( TeamGame teamGame ) {
	
		PlayerGame playerGame = new PlayerGame();
		
		playerGame.setGame_id(   teamGame.getGame_id()   );
		playerGame.setYear(      teamGame.getYear()      );
		playerGame.setDatestamp( teamGame.getDatestamp() );
		playerGame.setType(      teamGame.getType()      );
		playerGame.setPlayer_id(     this.player_id      );
		playerGame.setTeam_id(   teamGame.getTeam_id()   );
		
		this.game = playerGame;
	}
	
	public void fatiguePlayer( int time ) {
	
		double x = (this.endurance - 20.0) / 25.0;
		
		double fatigue_rate = Player.MAX_FATIGUE_RATE - (x * (Player.MAX_FATIGUE_RATE - Player.MIN_FATIGUE_RATE));
		
		this.fatigue += fatigue_rate * time;
		
		if ( this.fatigue > 1.0 ) {
		
			//System.out.println( this.first_name + " " + this.last_name + " is tired, Fatigue: " + String.valueOf( this.fatigue ) );
		}
	}
	
	public void restPlayer( int time ) {
	
		double x = (this.endurance - 20.0) / 25.0;
		
		double resting_rate = Player.MIN_REST_RATE + (x * (Player.MAX_REST_RATE - Player.MIN_REST_RATE));
		
		this.fatigue -= resting_rate * time;
		
		if ( this.fatigue <= 0.0 ) {
		
			this.fatigue = 0.0;
			
			this.resting = false;
		}
	}
	
	public int getTimeUntilTired() {
	
		double x = (this.endurance - 20.0) / 25.0;
		
		double fatigue_rate = Player.MAX_FATIGUE_RATE - (x * (Player.MAX_FATIGUE_RATE - Player.MIN_FATIGUE_RATE));
		
		return (int)Math.ceil( (1.0 - this.fatigue) / fatigue_rate );
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public double getBlocking() {
		
		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.blocking * (this.fatigue - 1.0);
				
				return this.blocking - x;
		}
		
		return this.blocking;
	}

	public void setBlocking(double blocking) {
		this.blocking = blocking;
	}

	public double getDiscipline() {
		
		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.discipline * (this.fatigue - 1.0);
				
				return this.discipline - x;
		}
		
		return this.discipline;
	}

	public void setDiscipline(double discipline) {
		this.discipline = discipline;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public double getPassing() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.passing * (this.fatigue - 1.0);
				
				return this.passing - x;
		}
		
		return this.passing;
	}

	public void setPassing(double passing) {
		this.passing = passing;
	}

	public double getPenalty_defense() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.penalty_defense * (this.fatigue - 1.0);
				
				return this.penalty_defense - x;
		}
		
		return this.penalty_defense;
	}

	public void setPenalty_defense(double penalty_defense) {
		this.penalty_defense = penalty_defense;
	}

	public double getPenalty_offense() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.penalty_offense * (this.fatigue - 1.0);
				
				return this.penalty_offense - x;
		}
		
		return this.penalty_offense;
	}

	public void setPenalty_offense(double penalty_offense) {
		this.penalty_offense = penalty_offense;
	}

	public double getPenalty_shot() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.penalty_shot * (this.fatigue - 1.0);
				
				return this.penalty_shot - x;
		}
		
		return this.penalty_shot;
	}

	public void setPenalty_shot(double penalty_shot) {
		this.penalty_shot = penalty_shot;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public double getPresence() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.presence * (this.fatigue - 1.0);
				
				return this.presence - x;
		}
		
		return this.presence;
	}

	public void setPresence(double presence) {
		this.presence = presence;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}

	public double getScoring() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.scoring * (this.fatigue - 1.0);
				
				return this.scoring - x;
		}
		
		return this.scoring;
	}

	public void setScoring(double scoring) {
		this.scoring = scoring;
	}

	public double getStealing() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.stealing * (this.fatigue - 1.0);
				
				return this.stealing - x;
		}
		
		return this.stealing;
	}

	public void setStealing(double stealing) {
		this.stealing = stealing;
	}

	public double getTackling() {

		if ( this.isPlaying() && this.fatigue > 1.0 ) {
			
				double x = this.tackling * (this.fatigue - 1.0);
				
				return this.tackling - x;
		}
		
		return this.tackling;
	}

	public void setTackling(double tackling) {
		this.tackling = tackling;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getDraft_pick() {
		return draft_pick;
	}

	public void setDraft_pick(int draft_pick) {
		this.draft_pick = draft_pick;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasons_played) {
		this.seasons_played = seasons_played;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public int getReleased_by() {
		return released_by;
	}

	public void setReleased_by(int released_by) {
		this.released_by = released_by;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getAllstar_team_id() {
		return allstar_team_id;
	}

	public void setAllstar_team_id(int allstar_team_id) {
		this.allstar_team_id = allstar_team_id;
	}

	public PlayerGame getGame() {
		return game;
	}

	public void setGame(PlayerGame game) {
		this.game = game;
	}

	public double getEndurance() {
		return endurance;
	}

	public void setEndurance(double endurance) {
		this.endurance = endurance;
	}

	public double getFatigue() {
		return fatigue;
	}

	public void setFatigue(double fatigue) {
		this.fatigue = fatigue;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
	}
	
}
