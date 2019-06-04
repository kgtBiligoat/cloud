package data;

/**
 * Author: cwz
 * Time: 2017/9/19
 * Description:
 */
public class Record {
    private String eid;
    private String eid2;
    private long time;
    private int placeId;
    private String address;
    private double longitude;
    private double latitude;
    private int count;

    public Record() { }

    public Record(String eid, long time, int placeId, String address, double longitude, double latitude) {
        this.eid = eid;
        this.time = time;
        this.placeId = placeId;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getEid() {
        return eid;
    }
    public String getEid2() {
        return eid2;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
    public void setEid2(String eid2){
        this.eid2 = eid2;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }
}
