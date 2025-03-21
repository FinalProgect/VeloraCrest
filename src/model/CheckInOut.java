package model;


public class CheckInOut {
    private int reservationNo;
    private String customerName;
    private String checkInDate;
    private String checkOutDate;
    private StringBuilder rooms;
    private int status;

    /**
     * @return the reservationNo
     */
    public int getReservationNo() {
        return reservationNo;
    }

    /**
     * @param reservationNo the reservationNo to set
     */
    public void setReservationNo(int reservationNo) {
        this.reservationNo = reservationNo;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the checkInDate
     */
    public String getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate the checkInDate to set
     */
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return the checkOutDate
     */
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate the checkOutDate to set
     */
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return the rooms
     */
    public StringBuilder getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(StringBuilder rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
