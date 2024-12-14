package model;

import gui.frontDesk.FaontDeskOverview;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class RoomStatusChecker extends Thread {

    public RoomStatusChecker(FaontDeskOverview petent) {
        this.perent = petent;
    }
    
    
    
    
    
    
    public boolean roomReservationChecker = true;
    
    private FaontDeskOverview perent;
    
    @Override
    public void run() {
        
        while (roomReservationChecker) {
            
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
        
    }
    
}
