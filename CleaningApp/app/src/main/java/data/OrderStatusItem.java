package data;

/**
 * Created by Serge on 1/7/2015.
 */
public class OrderStatusItem {
    private int id;
    private String shirtsQ, summ, status, timeStamp;

    public OrderStatusItem() {
    }

    public OrderStatusItem(int id, String summ, String status,
                   String timeStamp, String shirtsQ) {
        super();
        this.id = id;
        this.status = status;
        this.timeStamp = timeStamp;
        this.summ = summ;
        this.shirtsQ = shirtsQ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSumm(){
        return summ;
    }
    public void setSumm(String summ){
        this.summ = summ;
    }
    public String getShirtsQ(){
        return shirtsQ;
    }
    public void setShirtsQ(String shirtsQ){
        this.shirtsQ = shirtsQ;
    }

}
