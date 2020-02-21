package natc.data;

import java.util.Arrays;

public class Manager {

	public static final double DEFAULT_PERFORMANCE_RATING = 1.0;
	
	public static final int STYLE_OFFENSIVE  = 1;
	public static final int STYLE_DEFENSIVE  = 2;
	public static final int STYLE_INTANGIBLE = 3;
	public static final int STYLE_PENALTY    = 4;
	public static final int STYLE_BALANCED   = 5;
	
	public static final int MANAGER_OF_THE_YEAR = 1;
	public static final int STARTING_AGE        = 40;
	
	private int     manager_id;
	
	private int     player_id;
	
	private int     team_id;
	private int     allstar_team_id;
	private int     award;
	
	private String  year;
	
	private String  first_name;
	private String  last_name;
	
	private int     age;
	
	private double  offense;
	private double  defense;
	private double  intangible;
	private double  penalties;
	private double  vitality;
	
	private int     style;
	
	private boolean new_hire;
	private boolean released;
	private boolean retired;
	private int     former_team_id;
	
	private int     seasons;
	private int     score;
	private int     total_seasons;
	private int     total_score;
	
	public Manager() {
	
		this.manager_id      = 0;
		this.player_id       = 0;
		this.team_id         = 0;
		this.allstar_team_id = 0;
		this.year            = null;
		this.first_name      = null;
		this.last_name       = null;
		this.age             = 0;
		this.offense         = 0.0;
		this.defense         = 0.0;
		this.intangible      = 0.0;
		this.penalties       = 0.0;
		this.vitality        = 0.0;
		this.style           = 0;
		this.new_hire        = false;
		this.released        = false;
		this.retired         = false;
		this.former_team_id  = 0;
		this.seasons         = 0;
		this.score           = 0;
		this.total_seasons   = 0;
		this.total_score     = 0;
		this.award           = 0;
	}

	public Manager( int manager_id, String first_name, String last_name ) {
	
		this.manager_id      = manager_id;
		this.player_id       = 0;
		this.team_id         = 0;
		this.allstar_team_id = 0;
		this.year            = null;
		this.first_name      = first_name;
		this.last_name       = last_name;
		this.age             = STARTING_AGE;
		this.offense         = Math.random();
		this.defense         = Math.random();
		this.intangible      = Math.random();
		this.penalties       = Math.random();
		this.vitality        = Math.random();
		this.new_hire        = false;
		this.released        = false;
		this.retired         = false;
		this.former_team_id  = 0;
		this.seasons         = 0;
		this.score           = 0;
		this.total_seasons   = 0;
		this.total_score     = 0;
		this.award           = 0;
		
		determineStyle();
	}

	public Manager( int manager_id, int player_id, String first_name, String last_name ) {
	
		this.manager_id      = manager_id;
		this.player_id       = player_id;
		this.team_id         = 0;
		this.allstar_team_id = 0;
		this.year            = null;
		this.first_name      = first_name;
		this.last_name       = last_name;
		this.age             = STARTING_AGE;
		this.offense         = 0.0;
		this.defense         = 0.0;
		this.intangible      = 0.0;
		this.penalties       = 0.0;
		this.vitality        = 0.0;
		this.style           = 0;
		this.new_hire        = false;
		this.released        = false;
		this.retired         = false;
		this.former_team_id  = 0;
		this.seasons         = 0;
		this.score           = 0;
		this.total_seasons   = 0;
		this.total_score     = 0;
		this.award           = 0;
	}

	private class Attribute implements Comparable {
	
		public double value;
		public int    style;
		
		public Attribute() {
		
			this.value = 0.0;
			this.style = 0;
		}
		
		public Attribute( double value, int style ) {
		
			this.value = value;
			this.style = style;
		}

		public int compareTo( Object o ) {
			
			Attribute other = (Attribute)o;
			
			if ( this.value == other.value ) return 0;
			
			return ((this.value - other.value) > 0) ? 1 : -1;
		}
		
	}
	
	public Attribute AttributeStub() {
	
		Attribute a = new Attribute();
		
		return a;
	}
	
	public void determineStyle() {

		Attribute[] attributes = new Attribute[4];
		
		attributes[0] = new Attribute( this.offense,    Manager.STYLE_OFFENSIVE  );
		attributes[1] = new Attribute( this.defense,    Manager.STYLE_DEFENSIVE  );
		attributes[2] = new Attribute( this.intangible, Manager.STYLE_INTANGIBLE );
		attributes[3] = new Attribute( this.penalties,  Manager.STYLE_PENALTY    );
		
		Arrays.sort( attributes );

		if ( (attributes[3].value - attributes[2].value) > 0.1 )
		{
			this.style =  attributes[3].style;
		}
		else
		{
			if (    (attributes[3].style == Manager.STYLE_OFFENSIVE && attributes[2].style == Manager.STYLE_DEFENSIVE) ||
					(attributes[3].style == Manager.STYLE_DEFENSIVE && attributes[2].style == Manager.STYLE_OFFENSIVE)    )
			{
				this.style = Manager.STYLE_BALANCED;
			}
			else
			{
				if ( (attributes[2].value - attributes[1].value) < 0.1 )
				{
					this.style = Manager.STYLE_BALANCED;
				}
				else
				{
					if ( attributes[3].style == Manager.STYLE_INTANGIBLE )
					{
						this.style =  attributes[2].style;
					}
					else if ( attributes[2].style == Manager.STYLE_INTANGIBLE )
					{
						this.style =  attributes[3].style;
					}
					else
					{
						if ( attributes[3].style == Manager.STYLE_PENALTY )
						{
							this.style =  attributes[2].style;
						}
						else if ( attributes[2].style == Manager.STYLE_PENALTY )
						{
							this.style =  attributes[3].style;
						}
					}
				}
			}
		}
	}

	public boolean readyToRetire() {
	
		if ( this.age > ((int)Math.ceil( 50.0 + (20.0 * this.vitality) )) ) return true;
		
		return false;
	}
	
	public double getOverallRating() {
		
		return (this.offense + this.defense + this.intangible + this.penalties) / 4.0;
	}
	
	public double getPerformanceRating() {
	
		if ( this.seasons > 0 ) {
		
			double currentPerformanceRating = ((double)this.score       / (double)this.seasons);
			double careerPerformanceRating  = ((double)this.total_score / (double)this.total_seasons);
			
			return (currentPerformanceRating > careerPerformanceRating) ? currentPerformanceRating : careerPerformanceRating;
		}
		
		return DEFAULT_PERFORMANCE_RATING;
	}
	
	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int managerId) {
		manager_id = managerId;
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

	public double getOffense() {
		return offense;
	}

	public void setOffense(double offense) {
		this.offense = offense;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getIntangible() {
		return intangible;
	}

	public void setIntangible(double intangible) {
		this.intangible = intangible;
	}

	public double getPenalties() {
		return penalties;
	}

	public void setPenalties(double penalties) {
		this.penalties = penalties;
	}

	public double getVitality() {
		return vitality;
	}

	public void setVitality(double vitality) {
		this.vitality = vitality;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public int getFormer_team_id() {
		return former_team_id;
	}

	public void setFormer_team_id(int formerTeamId) {
		former_team_id = formerTeamId;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public boolean isNew_hire() {
		return new_hire;
	}

	public void setNew_hire(boolean newHire) {
		new_hire = newHire;
	}

	public int getAllstar_team_id() {
		return allstar_team_id;
	}

	public void setAllstar_team_id(int allstarTeamId) {
		allstar_team_id = allstarTeamId;
	}

	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getTotal_seasons() {
		return total_seasons;
	}

	public void setTotal_seasons(int totalSeasons) {
		total_seasons = totalSeasons;
	}

	public int getTotal_score() {
		return total_score;
	}

	public void setTotal_score(int totalScore) {
		total_score = totalScore;
	}

}
