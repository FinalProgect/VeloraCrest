package gui.housekeepingManager.model;

import gui.SignIn;
import java.util.Date;
import model.FormatDate;
import model.employeeDetails;
import java.sql.ResultSet;
import model.MYsql;
import raven.toast.Notifications;

public class assignExistingTaskStrategy implements AssignClearningStrategy {

    @Override
    public void assign(String room, String employee, employeeDetails manager) {
        String date = FormatDate.getToday(new Date(), "yyyy-MM-dd");
        String updateQuery = String.format("UPDATE `taskshedule` SET `assignTo` = '%s' WHERE `date` = '%s' "
                + " AND `rooms_id` = '%s' AND `taskStatus_id` != '3'   ",
                employee,
                date,
                room);
        String searchQuery = String.format("SELECT  * FROM taskshedule WHERE `date` = '%s' "
                + "AND `rooms_id` = '%s' AND (taskstatus_id = 1 OR taskStatus_id = 2)", date, room);
        try {
            ResultSet searchTask = MYsql.execute(searchQuery);
            if (searchTask.next()) {
                MYsql.execute(updateQuery);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, 3000, "Task Updated Successfully");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, 3000, "Task does not exist");

            }
        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
        }

    }

}
