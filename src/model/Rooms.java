package model;


public class Rooms {
    
    private int roomId;
    private String roomNo;
    private int roomPersonCount;
    private Double price;
    private int type;
    private int occupideStatus;
    private int roomCleanStatus;
   

    /**
     * @return the roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the roomNo
     */
    public String getRoomNo() {
        return roomNo;
    }

    /**
     * @param roomNo the roomNo to set
     */
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    /**
     * @return the roomPersonCount
     */
    public int getRoomPersonCount() {
        return roomPersonCount;
    }

    /**
     * @param roomPersonCount the roomPersonCount to set
     */
    public void setRoomPersonCount(int roomPersonCount) {
        this.roomPersonCount = roomPersonCount;
    }

 

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the occupideStatus
     */
    public int getOccupideStatus() {
        return occupideStatus;
    }

    /**
     * @param occupideStatus the occupideStatus to set
     */
    public void setOccupideStatus(int occupideStatus) {
        this.occupideStatus = occupideStatus;
    }

    /**
     * @return the roomCleanStatus
     */
    public int getRoomCleanStatus() {
        return roomCleanStatus;
    }

    /**
     * @param roomCleanStatus the roomCleanStatus to set
     */
    public void setRoomCleanStatus(int roomCleanStatus) {
        this.roomCleanStatus = roomCleanStatus;
    }
    
    
}
