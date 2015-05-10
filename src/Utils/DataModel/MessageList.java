package Utils.DataModel;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class MessageList {
    @Expose
    @XmlElement
    private List<Message> messages;
    private int lastMsgPos=0;

    public MessageList() {
        messages = new ArrayList<Message>();
    }

    public void add(Message m) {
        messages.add(m);
    }

    public void add(MessageList ml) {
        messages.addAll(ml.getAllMessages());
    }


    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getAllMessages() {
        return messages;
    }
    public List<Message> getNewMessage(){
        List<Message> result=new ArrayList<Message>();
        List<Message> allMessage=new ArrayList<Message>(messages);
        for (int i = lastMsgPos; i <allMessage.size() ; i++) {
            result.add((Message)allMessage.toArray()[i]);
            lastMsgPos=i+1;
        }
        return result;
    }

}