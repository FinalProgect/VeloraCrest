package gui.housekeepingStaff.model;

import gui.SignIn;
import gui.housekeepingStaff.HouseKeepingStaffOverview;
import java.sql.ResultSet;
import model.MYsql;
import raven.toast.Notifications;

public class StartTaskStrategy implements TaskActionStrategyInterface {

    @Override
    public void doTask(String taskID, HouseKeepingStaffOverview parent) {
        try {

            String query = String.format("SELECT * FROM taskshedule INNER JOIN cleanroom ON "
                    + "cleanroom.rooms_id = taskshedule.rooms_id WHERE taskshedule.id = '%s' ", taskID);
            ResultSet rs = MYsql.execute(query);
            String roomID = "";
            if (rs.next()) {
                roomID = rs.getString("rooms_id");

                MYsql.execute(String.format("UPDATE `taskshedule` SET `taskStatus_id`  = '2' WHERE `id` = '%s'  ", taskID));
                MYsql.execute(String.format("UPDATE `cleanroom` SET `Cleanstatus_id` = '2' WHERE `rooms_id` = '%s' ", roomID));
                parent.loadCards();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, 3000, "Task Updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, 3000, "Task Couldn't be Updated");
        }
    }

}
