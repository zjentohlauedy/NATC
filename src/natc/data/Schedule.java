package natc.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Schedule {

	public static final String FIRST_YEAR     = "1971";
	public static final int    FIRST_SEQUENCE = 1;
	
	public static final int    st_Scheduled   = 0;
	public static final int    st_InProgress  = 1;
	public static final int    st_Completed   = 2;
	
	private String       year;
	private int          sequence;
	private ScheduleType type;
	private String       data;
	private Date         scheduled;
	private int          status;
	
	private List         matches;
	
	public Schedule() {
	
		this.year      = null;
		this.sequence  = 0;
		this.type      = null;
		this.data      = null;
		this.scheduled = null;
		this.status    = st_Scheduled;
		this.matches   = null;
	}

	public void parseMatches() {
	
		if ( this.data == null ) return;
		
		char[] bytes = data.toCharArray();
		
		if ( bytes.length == 0 ) return;
		
		int games = bytes[0] - '0';
		
		for ( int i = 1; i <= (games * 2); i += 2 ) {
		
			if ( this.matches == null ) this.matches = new ArrayList();
			
			Match match = new Match();
			
			match.setRoad_team_id( bytes[i    ] - '0' );
			match.setHome_team_id( bytes[i + 1] - '0' );
			
			matches.add( match );
		}		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List getMatches() {
		return matches;
	}

	public void setMatches(List matches) {
		this.matches = matches;
	}

	public Date getScheduled() {
		return scheduled;
	}

	public void setScheduled(Date scheduled) {
		this.scheduled = scheduled;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public ScheduleType getType() {
		return type;
	}

	public void setType(ScheduleType type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
