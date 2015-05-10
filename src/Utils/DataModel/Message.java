package Utils.DataModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    private String body;
    private Date date;
    private String senderName;
    private Boolean priv=false;
    private ArrayList<User> recipients;

    public Message() {
    }


//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public void setSenderName(String senderName) {
//        this.senderName = senderName;
//    }
//
//    public void setPriv(Boolean priv) {
//        this.priv = priv;
//    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat=new SimpleDateFormat();

        String to=":";
        if (priv) {
            to="> "+recipients+" :";
        }
        return dateFormat.format(date)+" "+senderName+" "+to+" "+body;
    }
}
