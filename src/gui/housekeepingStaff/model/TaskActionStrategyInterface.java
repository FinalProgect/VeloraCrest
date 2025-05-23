package gui.housekeepingStaff.model;

import gui.housekeepingStaff.HouseKeepingStaffOverview;

public interface TaskActionStrategyInterface {

    public void doTask(String taskID, HouseKeepingStaffOverview parent);
}
