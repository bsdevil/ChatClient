package ChatGUI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;

@XmlRootElement
public class ConfFormSettings {
    private String[] protocolList;
    private String protocol;
    private String host;
    private int port;
    private String login;
    private Boolean autoLogin;
    private static final ConfFormSettings settings=new ConfFormSettings();

    public static final ConfFormSettings getInstance(){return settings;}

    private ConfFormSettings() {
        protocolList=new String[] {"JSON","XML"};
        protocol="JSON";
        host="127.0.0.1";
        port=8080;
        login="Login";
        autoLogin=false;
    }

    public void Init(String[] protocolList, String protocol, String host, int port, String login, Boolean autoLogin){
        this.protocolList = protocolList;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.login = login;
        this.autoLogin = autoLogin;
    }

    public String saveToFile(String... fileName)  {
        String fn="ChatConf.xml";
        if (fileName.length!=0) {
            fn=fileName[0];
        }
        try {
            JAXBContext context=JAXBContext.newInstance(ConfFormSettings.class);
            Marshaller marshaller=context.createMarshaller();

            DataOutputStream out=new DataOutputStream(new FileOutputStream(fn));
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(this,out);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Error: "+e.getMessage();
        }
        return "Saved to: "+fn;
    }

    public String loadFromFile(String... fileName){
        ConfFormSettings fs;
        String fn="ChatConf.xml";
        if (fileName.length!=0) {
            fn=fileName[0];
        }
        JAXBContext context= null;
        try {
            DataInputStream in=new DataInputStream(new FileInputStream(fn));

            context = JAXBContext.newInstance(ConfFormSettings.class);
            Unmarshaller unmarshaller=context.createUnmarshaller();
            fs=(ConfFormSettings)unmarshaller.unmarshal(new File(fn));

            Init(fs.protocolList,fs.protocol,fs.host,fs.port,fs.login,fs.autoLogin);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            return "Error: "+e.getMessage();
        }

        return "Loaded from: "+fn;
    }

    public void setProtocolList(String[] protocolList) {
        this.protocolList = protocolList;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String[] getProtocolList() {
        return protocolList;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public Boolean isAutoLogin() {
        return autoLogin;
    }
}
