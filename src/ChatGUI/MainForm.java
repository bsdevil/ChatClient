/*
 * Created by JFormDesigner on Mon May 04 18:04:08 EEST 2015
 */

package ChatGUI;

import javax.swing.event.*;
import javax.swing.table.*;

import Utils.ChatSession;
import Utils.DataModel.Message;
import Utils.DataModel.MessageList;
import Utils.DataModel.RoomList;
import Utils.DataModel.User;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;


public class MainForm extends JFrame implements Runnable{
    private MainForm() {
        initComponents();
        formDataPrepare();
        ConfFormSettings.getInstance().loadFromFile();
        if (ConfFormSettings.getInstance().isAutoLogin()) {
            new ConnectAction().actionPerformed(null);
        }
//
        t.setDaemon(true);
        t.start();

    }

    @Override
    public void dispose() {
        threadFlag=-1;
        if (chatSession.getSessionID() != null) {
            chatSession.logout();
        }
        System.exit(0);
    }


    public void formDataPrepare() {
        if ((chatSession.getSessionID() != null) && (chatSession.getLastError() == null)) {
            roomsInit();
            usersTable.setModel(new UsersTableModel());
            connectMI.setEnabled(false);
            disconnectMI.setEnabled(true);
            threadFlag=1;

            mainPanel.setVisible(true);
            statusPanel.setVisible(true);
        } else {
            if (chatSession.getLastError() != null) {
                JOptionPane.showMessageDialog(MainForm.this, chatSession.getLastError());
            }
            connectMI.setEnabled(true);
            disconnectMI.setEnabled(false);
            threadFlag=0;
            mainPanel.setVisible(false);
            statusPanel.setVisible(false);
        }
    }

    private synchronized void usersUpdate(Boolean resend) {
        if (resend) chatSession.getListUsers(roomsTP.getTitleAt(roomsTP.getSelectedIndex()));
        UsersTableModel utm=(UsersTableModel)usersTable.getModel();
        int rowID=usersTable.getSelectedRow();
        utm.update();
        utm.fireTableDataChanged();
        if (rowID>0)usersTable.setRowSelectionInterval(rowID,rowID);
    }

    private synchronized void messageUpdate (Boolean resend)  {
        if (resend) try {
            chatSession.getMessages(curentRoomLabel.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Component comp=roomsTP.getComponent(roomsTP.getSelectedIndex());
        JScrollPane jsp=(JScrollPane)comp;
        JTextPane jtp=(JTextPane)jsp.getViewport().getComponent(0);
        MessageList ml=RoomList.getInstance().getRoom(curentRoomLabel.getText()).getMessageList();
        StyledDocument doc=jtp.getStyledDocument();
        Boolean autoScroll=false;
        if (jtp.getCaretPosition()==doc.getLength()) autoScroll=true;
        for (Message m:ml.getNewMessage()) {
            try {
                doc.insertString(doc.getLength(),m.toString()+"\r\n",null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        if (autoScroll) jtp.setCaretPosition(doc.getLength());
    }

    private void roomsInit() {
        chatSession.getListRooms();
        String defRoom = rl.getDefaultRoom().getName();
        roomsTP.setTitleAt(0, defRoom);
        roomsTP.setSelectedIndex(0);
        curentRoomLabel.setText(defRoom);
    }

    private void roomsTPStateChanged(ChangeEvent e) {
        String roomTabName = roomsTP.getTitleAt(roomsTP.getSelectedIndex());
        if ((!curentRoomLabel.getText().equals("")) && (!curentRoomLabel.getText().equals(roomTabName))) {
            curentRoomLabel.setText(roomTabName);
            usersUpdate(false);
            messageUpdate(false);
        }
    }

    private void usersTableMousePressed(MouseEvent e) {
        Point p=e.getPoint();
        int row=usersTable.rowAtPoint(p);
        if ((e.getClickCount()==2)&&(row>-1)){
            messageEditPane.setText(usersTable.getValueAt(row, 0) + "> " + messageEditPane.getText());
        }
    }

    private void messageEditPaneKeyPressed(KeyEvent e) {
        if ((e.getKeyCode()==10)&&(e.getModifiers()==2)&&(!messageEditPane.getText().replaceAll("[\\s]", "").isEmpty()))
            sendAction.actionPerformed(null);
    }

    private void chatTextPanelKeyPressed(KeyEvent e) {
        if (e.getKeyCode()==116){
            messageUpdate(true);
        }


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        connectMI = new JMenuItem();
        disconnectMI = new JMenuItem();
        exitMI = new JMenuItem();
        configMenu = new JMenu();
        configMI = new JMenuItem();
        statusPanel = new JPanel();
        panel2 = new JPanel();
        label8 = new JLabel();
        protocolLabel = new JLabel();
        label7 = new JLabel();
        loginLabel = new JLabel();
        label1 = new JLabel();
        curentRoomLabel = new JLabel();
        panel3 = new JPanel();
        statusCB = new JComboBox();
        mainPanel = new JPanel();
        panel5 = new JPanel();
        scrollPane1 = new JScrollPane();
        messageEditPane = new JEditorPane();
        panel6 = new JPanel();
        button1 = new JButton();
        panel7 = new JPanel();
        scrollPane2 = new JScrollPane();
        usersTable = new JTable();
        roomsTP = new JTabbedPane();
        scrollPane3 = new JScrollPane();
        chatTextPanel = new JTextPane();
        chatTabsPopUp = new JPopupMenu();
        menuItem2 = new JMenuItem();
        menuItem1 = new JMenuItem();
        menuItem3 = new JMenuItem();
        openConf = new OpenConf();
        connection = new ConnectAction();
        disconnect = new DisconnectAction();
        exit = new ExitAction();
        openRoomAction = new OpenRoomAction();
        closeRoomAction = new CloseRoomAction();
        createRoomAction = new CreateRoomAction();
        sendAction = new SendAction();
        changeStatusAction = new ChangeStatusAction();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("ChatClient");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar ========
        {

            //======== fileMenu ========
            {
                fileMenu.setText("File");

                //---- connectMI ----
                connectMI.setText("Connect");
                connectMI.setAction(connection);
                connectMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_MASK));
                fileMenu.add(connectMI);

                //---- disconnectMI ----
                disconnectMI.setText("Disconnect");
                disconnectMI.setAction(disconnect);
                disconnectMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_MASK));
                fileMenu.add(disconnectMI);
                fileMenu.addSeparator();

                //---- exitMI ----
                exitMI.setText("Exit");
                exitMI.setAction(exit);
                exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_MASK));
                fileMenu.add(exitMI);
            }
            menuBar.add(fileMenu);

            //======== configMenu ========
            {
                configMenu.setText("Configuration");

                //---- configMI ----
                configMI.setAction(openConf);
                configMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK|KeyEvent.ALT_MASK));
                configMenu.add(configMI);
            }
            menuBar.add(configMenu);
        }
        setJMenuBar(menuBar);

        //======== statusPanel ========
        {
            statusPanel.setLayout(new GridLayout(1, 2));

            //======== panel2 ========
            {
                panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

                //---- label8 ----
                label8.setText("Protocol:");
                label8.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(label8);

                //---- protocolLabel ----
                protocolLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(protocolLabel);

                //---- label7 ----
                label7.setText("Name:");
                label7.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(label7);

                //---- loginLabel ----
                loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(loginLabel);

                //---- label1 ----
                label1.setText("Room:");
                label1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(label1);

                //---- curentRoomLabel ----
                curentRoomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                panel2.add(curentRoomLabel);
            }
            statusPanel.add(panel2);

            //======== panel3 ========
            {
                panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

                //---- statusCB ----
                statusCB.setModel(new DefaultComboBoxModel(new String[] {
                    "OnLine",
                    "Away"
                }));
                statusCB.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                statusCB.setAction(changeStatusAction);
                panel3.add(statusCB);
            }
            statusPanel.add(panel3);
        }
        contentPane.add(statusPanel, BorderLayout.SOUTH);

        //======== mainPanel ========
        {
            mainPanel.setBackground(Color.black);
            mainPanel.setLayout(new BorderLayout());

            //======== panel5 ========
            {
                panel5.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //---- messageEditPane ----
                    messageEditPane.setMinimumSize(new Dimension(958, 88));
                    messageEditPane.setPreferredSize(new Dimension(958, 88));
                    messageEditPane.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            messageEditPaneKeyPressed(e);
                        }
                    });
                    scrollPane1.setViewportView(messageEditPane);
                }
                panel5.add(scrollPane1, BorderLayout.CENTER);

                //======== panel6 ========
                {
                    panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));

                    //---- button1 ----
                    button1.setAlignmentY(0.0F);
                    button1.setContentAreaFilled(false);
                    button1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    button1.setAction(sendAction);
                    button1.setToolTipText("Ctrl+Enter");
                    panel6.add(button1);
                }
                panel5.add(panel6, BorderLayout.EAST);
            }
            mainPanel.add(panel5, BorderLayout.SOUTH);

            //======== panel7 ========
            {
                panel7.setLayout(new BorderLayout());

                //======== scrollPane2 ========
                {

                    //---- usersTable ----
                    usersTable.setModel(new DefaultTableModel(
                        new Object[][] {
                            {"user1", null},
                            {null, null},
                        },
                        new String[] {
                            null, null
                        }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[] {
                            String.class, Object.class
                        };
                        boolean[] columnEditable = new boolean[] {
                            false, true
                        };
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            return columnTypes[columnIndex];
                        }
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    usersTable.setShowVerticalLines(false);
                    usersTable.setShowHorizontalLines(false);
                    usersTable.setFillsViewportHeight(true);
                    usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    usersTable.setPreferredScrollableViewportSize(new Dimension(200, 400));
                    usersTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            usersTableMousePressed(e);
                        }
                    });
                    scrollPane2.setViewportView(usersTable);
                }
                panel7.add(scrollPane2, BorderLayout.LINE_START);

                //======== roomsTP ========
                {
                    roomsTP.setTabPlacement(SwingConstants.BOTTOM);
                    roomsTP.setComponentPopupMenu(chatTabsPopUp);
                    roomsTP.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            roomsTPStateChanged(e);
                        }
                    });

                    //======== scrollPane3 ========
                    {
                        scrollPane3.setPreferredSize(new Dimension(13, 25));
                        scrollPane3.setMaximumSize(new Dimension(13, 32767));
                        scrollPane3.setMinimumSize(new Dimension(13, 6));

                        //---- chatTextPanel ----
                        chatTextPanel.setEditable(false);
                        chatTextPanel.setPreferredSize(new Dimension(13, 23));
                        chatTextPanel.setMaximumSize(new Dimension(13, 2147483647));
                        chatTextPanel.setMinimumSize(new Dimension(13, 23));
                        chatTextPanel.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                chatTextPanelKeyPressed(e);
                            }
                        });
                        scrollPane3.setViewportView(chatTextPanel);
                    }
                    roomsTP.addTab("1", scrollPane3);
                }
                panel7.add(roomsTP, BorderLayout.CENTER);
            }
            mainPanel.add(panel7, BorderLayout.CENTER);
        }
        contentPane.add(mainPanel, BorderLayout.CENTER);
        setSize(1025, 775);
        setLocationRelativeTo(null);

        //======== chatTabsPopUp ========
        {

            //---- menuItem2 ----
            menuItem2.setText("Open room...");
            menuItem2.setAction(openRoomAction);
            chatTabsPopUp.add(menuItem2);

            //---- menuItem1 ----
            menuItem1.setAction(createRoomAction);
            chatTabsPopUp.add(menuItem1);
            chatTabsPopUp.addSeparator();

            //---- menuItem3 ----
            menuItem3.setAction(closeRoomAction);
            chatTabsPopUp.add(menuItem3);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem connectMI;
    private JMenuItem disconnectMI;
    private JMenuItem exitMI;
    private JMenu configMenu;
    private JMenuItem configMI;
    private JPanel statusPanel;
    private JPanel panel2;
    private JLabel label8;
    private JLabel protocolLabel;
    private JLabel label7;
    private JLabel loginLabel;
    private JLabel label1;
    private JLabel curentRoomLabel;
    private JPanel panel3;
    private JComboBox statusCB;
    private JPanel mainPanel;
    private JPanel panel5;
    private JScrollPane scrollPane1;
    private JEditorPane messageEditPane;
    private JPanel panel6;
    private JButton button1;
    private JPanel panel7;
    private JScrollPane scrollPane2;
    private JTable usersTable;
    private JTabbedPane roomsTP;
    private JScrollPane scrollPane3;
    private JTextPane chatTextPanel;
    private JPopupMenu chatTabsPopUp;
    private JMenuItem menuItem2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem3;
    private OpenConf openConf;
    private ConnectAction connection;
    private DisconnectAction disconnect;
    private ExitAction exit;
    private OpenRoomAction openRoomAction;
    private CloseRoomAction closeRoomAction;
    private CreateRoomAction createRoomAction;
    private SendAction sendAction;
    private ChangeStatusAction changeStatusAction;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private static MainForm mainForm = new MainForm();
    private Thread t=new Thread(this);
    private int threadFlag=0; //1-разрешено выполнение 0-пауза -1-завершить
    private ChatSession chatSession=ChatSession.getInstance();
    private RoomList rl=RoomList.getInstance();

    @Override
    public void run() {
        for (;;) {
            if (threadFlag==-1){return;}
            while (threadFlag==0){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (threadFlag==1) {
                usersUpdate(true);
                messageUpdate(true);
            }
        }
    }

    public ArrayList<String> getOppenedRoomsName() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 1; i < roomsTP.getTabCount(); i++) {
            result.add(roomsTP.getTitleAt(i));
        }
        return result;
    }

    public JTextPane getRoomByName(String name) {
        return null;
    }

    public void addRoom(String name) {
        if (!isRoomOpen(name)) {
            JScrollPane scroolpane = new JScrollPane();
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    chatTextPanelKeyPressed(e);
                }
            });
            scroolpane.setViewportView(textPane);
            roomsTP.add(name, scroolpane);
//            roomsTP.add(name, textPane);
        }
    }

    public int getRoomIndex(String name) {
        for (int i = 1; i < roomsTP.getTabCount(); i++) {
            if (roomsTP.getTitleAt(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean delRoom(String name) {
        int index = getRoomIndex(name);
        chatSession.exitRoom(name);
        if (index >= 0) {
            roomsTP.removeTabAt(index);
            return true;
        }
        return false;
    }

    public void delRoom(int index) {
        chatSession.exitRoom(roomsTP.getTitleAt(index));
        roomsTP.removeTabAt(index);

    }

    private Boolean isRoomOpen(String name) {
        for (int i = 1; i < roomsTP.getTabCount(); i++) {
            if (roomsTP.getTitleAt(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static final MainForm getInstance() {
        return mainForm;
    }


    private class OpenConf extends AbstractAction {
        private OpenConf() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Configure...");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_MASK));
            putValue(ACTION_COMMAND_KEY, "Configure...");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            ConfForm cf = new ConfForm(MainForm.this);
            cf.setVisible(true);
        }
    }


    private class ConnectAction extends AbstractAction {
        private ConnectAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Connect");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            if (chatSession.getSessionID() == null) {
                ConfFormSettings cf = ConfFormSettings.getInstance();
                cf.loadFromFile();
                chatSession.Init(cf.getProtocol(), cf.getHost(), cf.getPort(), cf.getLogin());
                try {
                    String result = chatSession.login();

                    if (!result.equals("200")) {
                        JOptionPane.showMessageDialog(MainForm.this, result);
                        chatSession.removeSessionID();
                    } else {
                        loginLabel.setText(cf.getLogin());
                        protocolLabel.setText(cf.getProtocol());
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainForm.this, e1.getMessage());
                    chatSession.removeSessionID();
                }
            }
            if (chatSession.getLastError() == null) {
                MainForm.this.formDataPrepare();
            }
        }
    }

    private class DisconnectAction extends AbstractAction {
        private DisconnectAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Disconnect");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            if (chatSession.getSessionID() != null) {
                threadFlag=0;
                chatSession.logout();
            }
            protocolLabel.setText("");
            loginLabel.setText("");
            MainForm.this.formDataPrepare();
        }
    }

    private class ExitAction extends AbstractAction {
        private ExitAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Exit");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            MainForm.this.dispose();
        }
    }

    private class OpenRoomAction extends AbstractAction {
        private OpenRoomAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Open room...");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            chatSession.getListRooms();
            new RoomManageForm(MainForm.this).setVisible(true);
        }
    }

    private class CloseRoomAction extends AbstractAction {
        private CloseRoomAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Close room");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            if (roomsTP.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(MainForm.this, "You can not close this room (" + roomsTP.getTitleAt(roomsTP.getSelectedIndex()) + ")");
                return;
            } else {
                delRoom(roomsTP.getSelectedIndex());

            }
        }
    }

    public void createRoomsTP(ArrayList<String> tName) {
        for (String s : tName) {
            chatSession.joinRoom(s);
            addRoom(s);
        }
    }

    public void createRoomsTP(String name) {
        chatSession.addRoom(name);
        addRoom(name);
        chatSession.getListRooms();
        usersUpdate(false);
        messageUpdate(false);
    }

    public ArrayList<String> getRoomsTP() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < roomsTP.getTabCount(); i++) {
            result.add(roomsTP.getTitleAt(i));
        }
        return result;
    }

    private class CreateRoomAction extends AbstractAction {
        private CreateRoomAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Create room...");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            new CreateRoomForm(MainForm.this).setVisible(true);
        }
    }

    private class SendAction extends AbstractAction {
        private SendAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "SEND");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            String message=messageEditPane.getText();
            messageEditPane.setText("");
            if (!message.replaceAll("[\\s]","").isEmpty()) {
                chatSession.sendMessage(curentRoomLabel.getText(),message);
            }
        }
    }

    private class UsersTableModel extends AbstractTableModel {
        private int rowCount;
        private ArrayList<User> dataSet;

        public UsersTableModel() {
            update();
        }

        public void update(){
            RoomList roomList = RoomList.getInstance();
            String roomName = roomsTP.getTitleAt(roomsTP.getSelectedIndex());
            this.dataSet = roomList.getRoom(roomName).getUserList();
            this.rowCount = dataSet.size();
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "User";
                case 1:
                    return "Status";
                default:
                    return null;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int i = 0;
            User[] users = new User[dataSet.size()];

            for (User u : dataSet) {
                users[i++] = u;
            }
//            User[] users=(User[])dataSet.toArray();
            switch (columnIndex) {
                case 0:
                    return users[rowIndex].getUserName();
                case 1:
                    return users[rowIndex].getStatusString();
                default:
                    return null;
            }
        }


    }

    private class ChangeStatusAction extends AbstractAction {
        private ChangeStatusAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "chahgeStatus");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            chatSession.changeStatus(statusCB.getSelectedIndex());
        }
    }
}
