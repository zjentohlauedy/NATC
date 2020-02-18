package natc.data;

public class ScheduleType {

	public static final int BEGINNING_OF_SEASON     =  0;
	public static final int ROOKIE_DRAFT_ROUND_1    =  1;
	public static final int ROOKIE_DRAFT_ROUND_2    =  2;
	public static final int TRAINING_CAMP           =  3;
	public static final int PRESEASON               =  4;
	public static final int ROSTER_CUT              =  5;
	public static final int FREE_AGENT_DRAFT        =  6;
	public static final int REGULAR_SEASON          =  7;
	public static final int AWARDS                  =  8;
	public static final int POSTSEASON              =  9;
	public static final int DIVISION_PLAYOFF        = 10;
	public static final int DIVISION_CHAMPIONSHIP   = 11;
	public static final int CONFERENCE_CHAMPIONSHIP = 12;
	public static final int NATC_CHAMPIONSHIP       = 13;
	public static final int ALL_STARS               = 14;
	public static final int ALL_STAR_DAY_1          = 15;
	public static final int ALL_STAR_DAY_2          = 16;
	public static final int END_OF_SEASON           = 17;
	
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
