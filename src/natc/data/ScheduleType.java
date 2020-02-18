package natc.data;

public class ScheduleType {

	public static final int BEGINNING_OF_SEASON     =  0;
	public static final int MANAGER_CHANGES         =  1;
	public static final int PLAYER_RETIREMENT       =  2;
	public static final int FREE_AGENCY             =  3;
	public static final int ROOKIE_DRAFT_ROUND_1    =  4;
	public static final int ROOKIE_DRAFT_ROUND_2    =  5;
	public static final int TRAINING_CAMP           =  6;
	public static final int PRESEASON               =  7;
	public static final int END_OF_PRESEASON        =  8;
	public static final int ROSTER_CUT              =  9;
	public static final int REGULAR_SEASON          = 10;
	public static final int END_OF_REGULAR_SEASON   = 11;
	public static final int AWARDS                  = 12;
	public static final int POSTSEASON              = 13;
	public static final int DIVISION_PLAYOFF        = 14;
	public static final int DIVISION_CHAMPIONSHIP   = 15;
	public static final int CONFERENCE_CHAMPIONSHIP = 16;
	public static final int NATC_CHAMPIONSHIP       = 17;
	public static final int END_OF_POSTSEASON       = 18;
	public static final int ALL_STARS               = 19;
	public static final int ALL_STAR_DAY_1          = 20;
	public static final int ALL_STAR_DAY_2          = 21;
	public static final int END_OF_ALLSTAR_GAMES    = 22;
	public static final int END_OF_SEASON           = 23;
	
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
