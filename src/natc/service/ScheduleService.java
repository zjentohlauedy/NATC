package natc.service;

import java.sql.SQLException;
import java.util.List;

import natc.data.Schedule;

public interface ScheduleService {

	public Schedule getLastScheduleEntry() throws SQLException;
	public Schedule getCurrentScheduleEntry() throws SQLException;
	public Schedule getNextScheduleEntry( Schedule schedule ) throws SQLException;
	public void     generateSchedule() throws SQLException;
	public void     updateScheduleEntry( Schedule schedule ) throws SQLException;
	public void     completeScheduleEvent( Schedule schedule ) throws SQLException;
	public void     updatePlayoffSchedule( int type, List[] teams ) throws SQLException;
	public void     updateAllstarSchedule( Schedule schedule, int[] teams ) throws SQLException;
}
