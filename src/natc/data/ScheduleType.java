package natc.data;

public class ScheduleType {

	public static final int BEGINNING_OF_SEASON     =  0;
	public static final int OFF_SEASON_MOVES        =  1;
	public static final int ROOKIE_DRAFT_ROUND_1    =  2;
	public static final int ROOKIE_DRAFT_ROUND_2    =  3;
	public static final int TRAINING_CAMP           =  4;
	public static final int PRESEASON               =  5;
	public static final int ROSTER_CUT              =  6;
	public static final int FREE_AGENT_DRAFT        =  7;
	public static final int REGULAR_SEASON          =  8;
	public static final int AWARDS                  =  9;
	public static final int POSTSEASON              = 10;
	public static final int DIVISION_PLAYOFF        = 11;
	public static final int DIVISION_CHAMPIONSHIP   = 12;
	public static final int CONFERENCE_CHAMPIONSHIP = 13;
	public static final int NATC_CHAMPIONSHIP       = 14;
	public static final int ALL_STARS               = 15;
	public static final int ALL_STAR_DAY_1          = 16;
	public static final int ALL_STAR_DAY_2          = 17;
	public static final int END_OF_SEASON           = 18;
	
	private int value;
	
	public ScheduleType() {
	
		value = 0;
	}
	
	public ScheduleType( int value ) {
	
		this.value = value;
	}

	public boolean equals( Object o ) {
		
		if ( this == o ) return true;
		
		if ( ! (o instanceof ScheduleType) ) return false;
		
		ScheduleType other = (ScheduleType)o;
		
		return (this.value == other.value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
