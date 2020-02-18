package natc.view;

public class RetiredView {

	private int    player_id;
	private String first_name;
	private String last_name;
	private int    age;
	private int    seasons_played;
	
	public RetiredView() {
	
		this.player_id      = 0;
		this.first_name     = null;
		this.last_name      = null;
		this.age            = 0;
		this.seasons_played = 0;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getSeasons_played() {
		return seasons_played;
	}

	public void setSeasons_played(int seasons_played) {
		this.seasons_played = seasons_played;
	}
	
}
