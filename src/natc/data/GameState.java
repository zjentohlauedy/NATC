package natc.data;

public class GameState {

	public static final int ps_NONE = 0;
	public static final int ps_HOME = 1;
	public static final int ps_ROAD = 2;
	
	private int     game_id;
	private int     sequence;
	private int     period;
	private boolean overtime;
	private int     time_remaining;
	private boolean clock_stopped;
	private int     possession;
	private String  last_event;
	
	public GameState( int game_id) {
	
		this.game_id        = game_id;
		this.sequence       = 0;
		this.period         = 0;
		this.overtime       = false;
		this.time_remaining = 0;
		this.clock_stopped  = false;
		this.possession     = 0;
		this.last_event     = null;
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();

		sb.append( "<game_id>"        + String.valueOf( game_id        ) + "</game_id>"        );
		sb.append( "<sequence>"       + String.valueOf( sequence       ) + "</sequence>"       );
		sb.append( "<period>"         + String.valueOf( period         ) + "</period>"         );
		sb.append( "<overtime>"       + String.valueOf( overtime       ) + "</overtime>"       );
		sb.append( "<time_remaining>" + String.valueOf( time_remaining ) + "</time_remaining>" );
		sb.append( "<clock_stopped>"  + String.valueOf( clock_stopped  ) + "</clock_stopped>"  );
		sb.append( "<possession>"     + String.valueOf( possession     ) + "</possession>"     );
		sb.append( "<last_event>"     + String.valueOf( last_event     ) + "</last_event>"     );

		return sb.toString();
	}
	
	public void incrementSequence() {
	
		this.sequence++;
	}
	
	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int gameId) {
		game_id = gameId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public boolean isOvertime() {
		return overtime;
	}

	public void setOvertime(boolean overtime) {
		this.overtime = overtime;
	}

	public int getTime_remaining() {
		return time_remaining;
	}

	public void setTime_remaining(int timeRemaining) {
		time_remaining = timeRemaining;
	}

	public boolean isClock_stopped() {
		return clock_stopped;
	}

	public void setClock_stopped(boolean clockStopped) {
		clock_stopped = clockStopped;
	}

	public int getPossession() {
		return possession;
	}

	public void setPossession(int possession) {
		this.possession = possession;
	}

	public String getLast_event() {
		return last_event;
	}

	public void setLast_event(String lastEvent) {
		last_event = lastEvent;
	}
}
