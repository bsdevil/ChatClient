package Utils.DataModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by potaninoa on 05.05.2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Room {
    @XmlElement
    private String name;
    @XmlElement
    private ArrayList<User> userList;
    private MessageList messageList;

//    public void setUserList(ArrayList<User> userList) {
//        this.userList = userList;
//    }

    public void updateRoom(Room room){
        this.userList=room.userList;
    }
    public void updateMsgList(MessageList ml){
        this.messageList.add(ml);
    }

    public ArrayList<String> getUsersName(){
        ArrayList<String> result=new ArrayList<String>();
        for (User u: userList){
            result.add(u.getUserName());
        }
        return result;
    }

    public Room() {
        userList=new ArrayList<User>();
        messageList=new MessageList();
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    public MessageList getMessageList() {

        return messageList;
    }

//    public User getUserByName(String name){
//        for (User u:userList){
//            if (u.getUserName().equals(name))
//                return u;
//        }
//        return null;
//    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }
}
