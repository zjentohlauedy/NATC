package natc.data;

import java.util.Calendar;
import java.util.Date;

public class Player {

	public static final int    STARTING_AGE     = 17;
	
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
	
	private double  confidence;
	private double  vitality;
	private double  endurance;
	private double  fatigue;
	private boolean in_game;
	private boolean playing;
	private boolean resting;
	private boolean played_in_game;
	private boolean started;
	
	private double  durability;
	private boolean injured;
	private int     duration;
	private Date    return_date;
	
	private boolean rookie;
	private boolean free_agent;
	private boolean signed;
	private boolean retired;
	
	private int     award;
	private int     draft_pick;
	private int     seasons_played;
	private int     allstar_team_id;
	
	// Tracking roster cuts
	private boolean released;
	private int     former_team_id;
	
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
		this.confidence      = 0;
		this.vitality        = 0;
		this.fatigue         = 0;
		this.in_game         = false;
		this.playing         = false;
		this.resting         = false;
		this.played_in_game  = false;
		this.started         = false;
		this.durability      = 0;
		this.injured         = false;
		this.duration        = 0;
		this.return_date     = null;
		this.rookie          = false;
		this.free_agent      = false;
		this.signed          = false;
		this.retired         = false;
		this.award           = 0;
		this.draft_pick      = 0;
		this.seasons_played  = 0;
		this.allstar_team_id = 0;
		this.score           = 0;
		this.released        = false;
		this.former_team_id  = 0;
		this.game            = null;
	}
	
	public Player( int player_id, String first_name, String last_name, boolean rookie, int age ) {
	
		this.player_id       = player_id;
		this.team_id         = 0;
		this.year            = null;
		this.first_name      = first_name;
		this.last_name       = last_name;
		this.age             = age;
		this.scoring         = Math.random();
		this.passing         = Math.random();
		this.blocking        = Math.random();
		this.tackling        = Math.random();
		this.stealing        = Math.random();
		this.presence        = Math.random();
		this.discipline      = Math.random();
		this.penalty_shot    = Math.random();
		this.penalty_offense = Math.random();
		this.penalty_defense = Math.random();
		this.endurance       = Math.random();
		this.confidence      = Math.random();
		this.vitality        = Math.random();
		this.fatigue         = 0.0;
		this.in_game         = false;
		this.playing         = false;
		this.resting         = false;
		this.played_in_game  = false;
		this.started         = false;
		this.durability      = Math.random();
		this.injured         = false;
		this.duration        = 0;
		this.return_date     = null;
		this.rookie          = rookie;
		this.free_agent      = false;
		this.signed          = false;
		this.retired         = false;
		this.award           = 0;
		this.draft_pick      = 0;
		this.seasons_played  = 0;
		this.allstar_team_id = 0;
		this.score           = 0;
		this.released        = false;
		this.former_team_id  = 0;
		this.game            = null;
	}

	public double getOverallRating() {
	
		return          this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.penalty_shot
		/**/          + this.penalty_offense
		/**/          + this.penalty_defense
		/**/          + this.endurance
		/**/          + this.confidence
		/**/          + this.vitality;
	}
	
	public double getRating() {
	
		return          this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.penalty_shot
		/**/          + this.penalty_offense
		/**/          + this.penalty_defense;
	}

	public double getAdjustedRating() {
	
		double rating = this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.penalty_shot
		/**/          + this.penalty_offense
		/**/          + this.penalty_defense;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.penalty_shot
		/**/          + this.penalty_offense
		/**/          + this.penalty_defense;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getPerformanceRating() {
	
		return       (  this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.endurance  ) / 8.0;
	}

	public double getAdjustedPerformanceRating() {
	
		double rating = this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.endurance;

		rating /= 8.0;
		
		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPerformanceRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = this.scoring
		/**/          + this.passing
		/**/          + this.blocking
		/**/          + this.tackling
		/**/          + this.stealing
		/**/          + this.presence
		/**/          + this.discipline
		/**/          + this.endurance;

		rating /= 8.0;
		
		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getOffensiveRating() {
	
		return (this.scoring + this.passing + this.blocking) / 3.0;
	}

	public double getAdjustedOffensiveRating() {
	
		double rating = (this.scoring + this.passing + this.blocking) / 3.0;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedOffensiveRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = (this.scoring + this.passing + this.blocking) / 3.0;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getDefensiveRating() {
	
		return (this.tackling + this.stealing + this.presence) / 3.0;
	}

	public double getAdjustedDefensiveRating() {
	
		double rating = (this.tackling + this.stealing + this.presence) / 3.0;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedDefensiveRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = (this.tackling + this.stealing + this.presence) / 3.0;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getIntangibleRating() {
	
		return (this.blocking + this.presence + this.discipline + this.endurance) / 4.0;
	}

	public double getAdjustedIntangibleRating() {
	
		double rating = (this.blocking + this.presence + this.discipline + this.endurance) / 4.0;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedIntangibleRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {
	
		double rating = (this.blocking + this.presence + this.discipline + this.endurance) / 4.0;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getPenaltyRating() {
	
		return (this.penalty_shot + this.penalty_offense + this.penalty_defense) / 3.0;
	}

	public double getAdjustedPenaltyRating() {
	
		double rating = (this.penalty_shot + this.penalty_offense + this.penalty_defense) / 3.0;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPenaltyRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {
	
		double rating = (this.penalty_shot + this.penalty_offense + this.penalty_defense) / 3.0;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}
	
	public double getTurnoverRating() {
	
		// The inverse of discipline and passing
		return 1.0 - ((this.discipline + this.passing) / 2.0);

	}

	public double getAdjustedTurnoverRating() {
	
		// The inverse of discipline and passing
		double rating = 1.0 - ((this.discipline + this.passing) / 2.0);

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedTurnoverRating( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		// The inverse of discipline and passing
		double rating = 1.0 - ((this.discipline + this.passing) / 2.0);

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedScoring() {
		
		double rating = scoring;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedScoring( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {
		
		double rating = scoring;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedPassing() {

		double rating = passing;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPassing( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = passing;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedBlocking() {

		double rating = blocking;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedBlocking( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = blocking;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedTackling() {

		double rating = tackling;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedTackling( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = tackling;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedStealing() {

		double rating = stealing;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedStealing( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = stealing;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedPresence() {

		double rating = presence;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPresence( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = presence;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedDiscipline() {

		double rating = discipline;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedDiscipline( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = discipline;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedPenalty_shot() {

		double rating = penalty_shot;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPenalty_shot( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = penalty_shot;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedPenalty_offense() {

		double rating = penalty_offense;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPenalty_offense( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = penalty_offense;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedPenalty_defense() {

		double rating = penalty_defense;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedPenalty_defense( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = penalty_defense;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAdjustedEndurance() {

		double rating = endurance;

		if ( this.isIn_game() ) {

			rating *= this.getRatingCoefficient( true, true );

			if ( this.isPlaying() && this.fatigue > 1.0 ) {

				rating *= (this.fatigue - 1.0);
			}
		}

		return rating;
	}

	public double getAdjustedEndurance( boolean applyAge, boolean applyConfidence, boolean applyFatigue ) {

		double rating = endurance;

		rating *= this.getRatingCoefficient( applyAge, applyConfidence );

		if ( applyFatigue && this.fatigue > 1.0 ) {

			rating *= (this.fatigue - 1.0);
		}

		return rating;
	}

	public double getAgeFactor() {
	
		int cutoff = (int)Math.ceil( 20.0 + (15.0 * this.vitality) );
		
		return (this.age < cutoff) ? 1.0 : 1.0 - (((double)this.age - cutoff) * (0.05 + 0.05 * this.vitality));
	}
	
	public double getConfidenceFactor() {
	
		int years = this.age - STARTING_AGE;
		
		return 1.0 - ((1.0 - this.confidence) / ((double)(years * years) + 1.0));
	}
	
	public double getRatingCoefficient( boolean useAge, boolean useConfidence ) {
		
		double age_factor        = 1.0;
		double confidence_factor = 1.0;
		
		if ( useAge        ) age_factor        = getAgeFactor();
		if ( useConfidence ) confidence_factor = getConfidenceFactor();
		
		return age_factor * confidence_factor;
	}
	
	public void agePlayer() {
	
		this.age++;
	}

	public void initPlayerGame( TeamGame teamGame ) {
	
		PlayerGame playerGame = new PlayerGame();
		
		playerGame.setGame_id(   teamGame.getGame_id()   );
		playerGame.setYear(      teamGame.getYear()      );
		playerGame.setDatestamp( teamGame.getDatestamp() );
		playerGame.setType(      teamGame.getType()      );
		playerGame.setPlayer_id(     this.player_id      );
		playerGame.setTeam_id(   teamGame.getTeam_id()   );
		
		this.game    = playerGame;
		this.in_game = true;
	}
	
	public void fatiguePlayer( int time ) {
	
		double fatigue_rate = Player.MAX_FATIGUE_RATE - (getAdjustedEndurance() * (Player.MAX_FATIGUE_RATE - Player.MIN_FATIGUE_RATE));
		
		this.fatigue += fatigue_rate * time;
		
		if ( this.fatigue > 1.0 ) {
		
			//System.out.println( this.first_name + " " + this.last_name + " is tired, Fatigue: " + String.valueOf( this.fatigue ) );
		}
	}
	
	public void restPlayer( int time ) {
	
		double resting_rate = Player.MIN_REST_RATE + (getAdjustedEndurance() * (Player.MAX_REST_RATE - Player.MIN_REST_RATE));
		
		this.fatigue -= resting_rate * time;
		
		if ( this.fatigue <= 0.0 ) {
		
			this.fatigue = 0.0;
			
			this.resting = false;
		}
	}
	
	public int getTimeUntilTired() {
	
		double fatigue_rate = Player.MAX_FATIGUE_RATE - (getAdjustedEndurance() * (Player.MAX_FATIGUE_RATE - Player.MIN_FATIGUE_RATE));
		
		return (int)Math.ceil( (1.0 - this.fatigue) / fatigue_rate );
	}

	public void injurePlayer( Date injuryDate ) {
	
		//    index                        0    1   2   3   4   5   6  7  8  9 10 11 12 13 14
		//    score                        1    2   3   4   5   6   7  8  9 10 11 12 13 14 15
		int[] injury_types = new int[] { 999, 112, 84, 56, 28, 21, 14, 7, 6, 5, 4, 3, 2, 1, 0 };
		
		int total_score = 120;
		
		int roll = (int)Math.round( Math.random() * (double)total_score );
		
		for ( int i = 0; i < 15; ++i ) {
		
			if ( (roll -= (i + 1)) <= 0 ) {
			
				this.duration = injury_types[i];
				
				break;
			}
		}
		
		this.injured = true;
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime( injuryDate );
		
		cal.add( Calendar.DAY_OF_YEAR, duration + 1 ); // min duration is 0 so earliest return date is tomorrow
		
		this.return_date = cal.getTime();
		
		this.game.setInjured( true );
	}
	
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int playerId) {
		player_id = playerId;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int teamId) {
		team_id = teamId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String firstName) {
		first_name = firstName;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String lastName) {
		last_name = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getScoring() {
		return scoring;
	}

	public void setScoring(double scoring) {
		this.scoring = scoring;
	}

	public double getPassing() {
		return passing;
	}

	public void setPassing(double passing) {
		this.passing = passing;
	}

	public double getBlocking() {
		return blocking;
	}

	public void setBlocking(double blocking) {
		this.blocking = blocking;
	}

	public double getTackling() {
		return tackling;
	}

	public void setTackling(double tackling) {
		this.tackling = tackling;
	}

	public double getStealing() {
		return stealing;
	}

	public void setStealing(double stealing) {
		this.stealing = stealing;
	}

	public double getPresence() {
		return presence;
	}

	public void setPresence(double presence) {
		this.presence = presence;
	}

	public double getDiscipline() {
		return discipline;
	}

	public void setDiscipline(double discipline) {
		this.discipline = discipline;
	}

	public double getPenalty_shot() {
		return penalty_shot;
	}

	public void setPenalty_shot(double penaltyShot) {
		penalty_shot = penaltyShot;
	}

	public double getPenalty_offense() {
		return penalty_offense;
	}

	public void setPenalty_offense(double penaltyOffense) {
		penalty_offense = penaltyOffense;
	}

	public double getPenalty_defense() {
		return penalty_defense;
	}

	public void setPenalty_defense(double penaltyDefense) {
		penalty_defense = penaltyDefense;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getVitality() {
		return vitality;
	}

	public void setVitality(double vitality) {
		this.vitality = vitality;
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

	public boolean isIn_game() {
		return in_game;
	}

	public void setIn_game(boolean inGame) {
		in_game = inGame;
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

	public boolean isRookie() {
		return rookie;
	}

	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getDraft_pick() {
		return draft_pick;
	}

	public void setDraft_pick(int draftPick) {
		draft_pick = draftPick;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasonsPlayed) {
		seasons_played = seasonsPlayed;
	}

	public int getAllstar_team_id() {
		return allstar_team_id;
	}

	public void setAllstar_team_id(int allstarTeamId) {
		allstar_team_id = allstarTeamId;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public int getFormer_team_id() {
		return former_team_id;
	}

	public void setFormer_team_id(int releasedBy) {
		former_team_id = releasedBy;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public PlayerGame getGame() {
		return game;
	}

	public void setGame(PlayerGame game) {
		this.game = game;
	}

	public boolean isPlayed_in_game() {
		return played_in_game;
	}

	public void setPlayed_in_game(boolean playedInGame) {
		played_in_game = playedInGame;
	}

	public double getDurability() {
		return durability;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}

	public boolean isInjured() {
		return injured;
	}

	public void setInjured(boolean injured) {
		this.injured = injured;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date returnDate) {
		return_date = returnDate;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public boolean isFree_agent() {
		return free_agent;
	}

	public void setFree_agent(boolean freeAgent) {
		free_agent = freeAgent;
	}

}
