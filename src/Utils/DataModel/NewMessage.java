package Utils.DataModel;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by z0rk on 09.05.2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewMessage {
    @Expose
    private String body;
    @Expose
    private Date date = new Date();
    @Expose
    private String roomName;
    @Expose
    private ArrayList<String> recipientsName;

    public NewMessage() {
    }

    public NewMessage(String body, String roomName, ArrayList<String> recipientsName) {//, String[] recipientsName) {
        this.body = body;
        this.date = new Date();
        this.roomName = roomName;
        this.recipientsName = recipientsName;
    }

//    public String getBody() {
//        return body;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public String getRoomName() {
//        return roomName;
//    }
}
