package Utils;

import Utils.DataModel.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.*;


public class ChatSession {
    private String protocol;
    private String host;
    private int port;
    private String login;
    private String sessionID;
    private String lastError;
    private static ChatSession session = new ChatSession();
    private RoomList roomList = RoomList.getInstance();

    private ChatSession() {
    }

    public static final ChatSession getInstance() {
        return session;
    }

    public void Init(String protocol, String host, int port, String login) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.login = login;
    }


    public String login() throws IOException {
        sessionID = null;
        lastError = null;
        URL url = new URL("http", host, port, "/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
        connection.setRequestProperty("login", URLEncoder.encode(login, "UTF-8"));
        connection.setRequestProperty("dataType", protocol);
        setSessionID(connection);

        if (connection.getResponseCode() == 200) {
            return "200";
        } else {
            lastError = connection.getResponseMessage();
            return lastError;
        }
    }

    public void logout() {
        if (sessionID != null) {
            try {
                getHttpConnection("/logout").getResponseMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sessionID = null;
        lastError = null;

    }


    private void setSessionID(HttpURLConnection connection) {
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String h : headers.keySet()) {
            if (h != null) {
                if (h.equals("Set-Cookie")) {
                    for (String s : headers.get(h)) {
                        String[] param = s.split(";");
                        for (int i = 0; i < param.length; i++) {
                            if (param[i].startsWith("JSESSIONID")) {
                                sessionID = param[i].split("=")[1];
                            }
                        }
                    }
                }
            }
        }

    }

    public String getSessionID() {
        return sessionID;
    }
    public void removeSessionID(){
        sessionID=null;
    }

    public String getLastError() {
        return lastError;
    }

    public String changeStatus(int statusID) {
        HttpURLConnection connection = getHttpConnection("/add?status=" + statusID);
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String exitRoom(String name) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpConnection("/add?room=" + URLEncoder.encode(name, "UTF-8") + "&a=exit");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String joinRoom(String name) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpConnection("/add?room=" + URLEncoder.encode(name, "UTF-8") + "&a=join");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addRoom(String name) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpConnection("/add?room=" + URLEncoder.encode(name, "UTF-8") + "&a=add");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendMessage(String room, String message) {
        ArrayList<String> recipients = new ArrayList<String>();
        StringBuffer msg = new StringBuffer();
        String[] tmpString = message.split("> ");
        for (int i = 0; i < tmpString.length; i++) {
            if (roomList.getDefaultRoom().getUsersName().contains(tmpString[i])) {
                recipients.add(tmpString[i]);
            } else {
                msg.append(tmpString[i]);
            }
        }

        NewMessage m = new NewMessage(msg.toString(), room, recipients);
        HttpURLConnection connection = getHttpConnection("/add");
        DataOutputStream os = null;
        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            os = new DataOutputStream(connection.getOutputStream());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (protocol.equals("JSON")) {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(m);
            try {
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write(json);
                osw.flush();
                osw.close();
                connection.getResponseMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(NewMessage.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(m, os);
                os.flush();
                os.close();
                connection.getResponseMessage();
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public synchronized Boolean getMessages(String roomName) throws IOException {
        Room room = roomList.getRoom(roomName);
        MessageList ml = room.getMessageList();
        int fromN = ml.getAllMessages().size();
        HttpURLConnection connection = getHttpConnection("/get?p=message&fromN=" + fromN + "&room=" + URLEncoder.encode(roomName, "UTF-8"));
        if (protocol.equals("JSON")) {
            String s,json="";
            BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            while ((s=br.readLine())!=null){
                json=json+s;
            }

            Gson gson = new GsonBuilder().create();
            room.updateMsgList(gson.fromJson(json.toString(), MessageList.class));
            return true;
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(MessageList.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                InputStream is = connection.getInputStream();
                room.updateMsgList((MessageList) unmarshaller.unmarshal(is));
                return true;
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized Boolean getListUsers(String roomName) {

        HttpURLConnection connection = null;
        try {
            connection = getHttpConnection("/get?p=users&room=" + URLEncoder.encode(roomName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (protocol.equals("JSON")) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                StringBuilder json = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    json.append(s);
                }
                Gson gson = new GsonBuilder().create();

                roomList.getRoom(roomName).updateRoom((gson.fromJson(json.toString(), Room.class)));

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {

                JAXBContext jaxbContext = JAXBContext.newInstance(Room.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                InputStream is = connection.getInputStream();
                roomList.getRoom(roomName).updateRoom((Room) unmarshaller.unmarshal(is));
                return true;
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public Boolean getListRooms() {

        HttpURLConnection connection = getHttpConnection("/get?p=rooms");

        if (protocol.equals("JSON")) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                StringBuilder json = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    json.append(s);
                }
                Gson gson = new GsonBuilder().create();
                roomList.Init(gson.fromJson(json.toString(), RoomList.class));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(RoomList.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                InputStream is = connection.getInputStream();

                RoomList rl = (RoomList) unmarshaller.unmarshal(is);
                roomList.Init(rl);
                return true;
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void readRAW(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String user;
            while ((user = br.readLine()) != null) {
                System.out.println(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection getHttpConnection(String uri) {
        try {
            URL url = new URL("http", host, port, uri);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Cookie", "JSESSIONID=" + sessionID);
                connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");

                return connection;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
