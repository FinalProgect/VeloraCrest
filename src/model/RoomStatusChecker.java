package model;

import gui.frontDesk.FaontDeskOverview;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RoomStatusChecker extends Thread {

    public RoomStatusChecker(FaontDeskOverview petent) {
        this.perent = petent;
    }

    public static boolean sroomStatusChekerStarted;

    private void checkRoomReservations() {

        System.out.println("Room Reservation Status Check Thread is Working");

        try {

            String quary = "SELECT `rooms`.`no`, `reservation_has_room`.`roomReservationStatus_id`  FROM  `rooms` "
                    + "INNER JOIN  `reservation_has_room` ON `rooms`.`id` = `reservation_has_room`.`rooms_id` "
                    + "WHERE `roomReservationStatus_id` = '1'";

            ResultSet result = MYsql.execute(quary);

            while (result.next()) {

                System.out.println(result.getString("no"));

//                    FaontDeskOverview.roomResiveStatusMap.put(result.getString("no"), result.getInt("roomReservationStatus_id"));
                FaontDeskOverview.roomsMap.get(result.getString("no")).setOccupideStatus(result.getInt("roomReservationStatus_id"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void checkRoomCleaningStatus() {

        System.out.println("Room Reservation Status Check Thread is Working");

        try {

            String quary = "SELECT `rooms`.`no`, `cleanroom`.`Cleanstatus_id` FROM `rooms` INNER JOIN `cleanroom` ON `rooms`.`id` = `cleanroom`.`rooms_id`";

            ResultSet result = MYsql.execute(quary);

            while (result.next()) {

                System.out.println(result.getString("no"));

//                    FaontDeskOverview.roomResiveStatusMap.put(result.getString("no"), result.getInt("roomReservationStatus_id"));
                FaontDeskOverview.roomsMap.get(result.getString("no")).setRoomCleanStatus(result.getInt("Cleanstatus_id"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean roomReservationChecker = true;


    private FaontDeskOverview perent;

    @Override
    public void run() {

        while (roomReservationChecker) {


            if (!sroomStatusChekerStarted) {

                sroomStatusChekerStarted = true;
            }

            checkRoomReservations();
            checkRoomCleaningStatus();


            try {
                Thread.sleep(5000);  // 5 seconds
            } catch (InterruptedException ex) {
                Logger.getLogger(RoomStatusChecker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
