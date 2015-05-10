package Utils.DataModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class RoomList {
    private List<Room> rooms;
    private Room defaultRoom;
    private int room_count;
    private static final RoomList roomList=new RoomList();

    public final static RoomList getInstance(){return roomList;}

    public RoomList() {
        rooms=new ArrayList<Room>();
        defaultRoom=new Room();
    }
    public void Init(RoomList rl){
        for (Room r:rl.rooms){      //переносим листы сообщений, т.к. сервер их не дает в общем пакете
            Room oldRoom=this.getRoom(r.getName());
            if (oldRoom!=null){
                r.setMessageList(oldRoom.getMessageList());
            }else {
                r.setMessageList(new MessageList());
            }
        }
        String defRoomName=rl.getDefaultRoom().getName();
        this.rooms=rl.getRooms();
//        this.defaultRoom=rl.getDefaultRoom();
        this.defaultRoom=rl.getRoom(defRoomName);
        this.room_count=rl.room_count;
    }


    public void setDefaultRoom(Room defaultRoom) {
        this.defaultRoom = defaultRoom;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getRoom_count() {
        return room_count;
    }

    public Room getDefaultRoom() {

        return defaultRoom;
    }
    public Room getRoom(String name){
        for (Room r:rooms){
            if (r.getName().equals(name)) return r;
        }
        return null;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
