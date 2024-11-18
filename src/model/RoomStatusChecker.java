package model;

import gui.frontDesk.FaontDeskOverview;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class RoomStatusChecker extends Thread {

    @Override
    public void run() {

        try {

            String quary = "SELECT `no`, `roomStatus_id` FROM `rooms` ";

            ResultSet result = MYsql.execute(quary);

            while (result.next()) {

                FaontDeskOverview.roomStatusMap.put(result.getString("no"), result.getInt("roomStatus_id"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
