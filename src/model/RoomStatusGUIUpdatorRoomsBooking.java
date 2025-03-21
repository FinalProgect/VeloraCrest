package model;

import gui.frontDesk.RoomsBooking;

public class RoomStatusGUIUpdatorRoomsBooking extends Thread {

    RoomsBooking perent;

    public static boolean roomsStatusUpdatingWorkerStarted;

    public RoomStatusGUIUpdatorRoomsBooking(RoomsBooking perent) {
        this.perent = perent;
    }

    @Override
    public void run() {
        roomsStatusUpdatingWorkerStarted = true;

        while (roomsStatusUpdatingWorkerStarted) {
            perent.updateRoomStatus();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
