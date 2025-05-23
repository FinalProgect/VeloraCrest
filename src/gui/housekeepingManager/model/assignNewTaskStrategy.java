package gui.housekeepingManager.model;

import gui.SignIn;
import gui.housekeepingManager.HouseKeepingManagerDashboard;
import java.util.Date;
import model.FormatDate;
import model.MYsql;
import model.employeeDetails;
import java.sql.ResultSet;
import raven.toast.Notifications;

public class assignNewTaskStrategy implements AssignClearningStrategy {
    
    @Override
    public void assign(String room, String employee,employeeDetails manager) {
        String date = FormatDate.getToday(new Date(), "yyyy-MM-dd");
        String insertQuery = String.format("INSERT INTO `taskshedule` (`date`,`rooms_id`,`assignBy`,`assignTo`,`task_id`,`taskStatus_id`)"
                + " VALUES ('%s','%s','%s','%s','%s','%s') ", date, room, employee, manager.getEmployeeId(), "1", "1");
        String searchQuery = String.format("SELECT  * FROM taskshedule WHERE `date` = '%s' "
                + "AND `rooms_id` = '%s' AND (taskstatus_id = 1 OR taskStatus_id = 2)", date, room);
        
        try {
            
            ResultSet checkExists_rs = MYsql.execute(searchQuery);
            System.out.println();
            if (!checkExists_rs.next()) {
                MYsql.execute(insertQuery);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, 3000, "Task Already assigned to an employee");
                
            } else {
                Notifications.getInstance().setJFrame(HouseKeepingManagerDashboard.getInstance());
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, 3000, "Task Already assigned to an employee");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
        }
    }
    
}
