package natc.data;

public class Match {

	private int home_team_id;
	private int road_team_id;
	
	public Match() {
	
		this.home_team_id = 0;
		this.road_team_id = 0;
	}

	public int getHome_team_id() {
		return home_team_id;
	}

	public void setHome_team_id(int home_team_id) {
		this.home_team_id = home_team_id;
	}

	public int getRoad_team_id() {
		return road_team_id;
	}

	public void setRoad_team_id(int road_team_id) {
		this.road_team_id = road_team_id;
	}

}
