package model;

import gui.frontDesk.FaontDeskOverview;

/**
 *
 * @author User
 */
public class RoomStatusGUIUpdatorFrantDeskOverView extends Thread {

    FaontDeskOverview frantDesk;
    
    public static boolean FrantDeskOvervireRoomsUpdatorStarted;

    public RoomStatusGUIUpdatorFrantDeskOverView(FaontDeskOverview frantDesk) {
        this.frantDesk = frantDesk;
    }

    @Override
    public void run() {
        FrantDeskOvervireRoomsUpdatorStarted = true;
       while(FrantDeskOvervireRoomsUpdatorStarted){
           
        frantDesk.updateRoomStatus();
        
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
       }
        
    }
     
    
}
