/*
 * Created by JFormDesigner on Mon May 04 19:58:34 EEST 2015
 */

package ChatGUI;

import Utils.ChatSession;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author zork
 */
public class ConfForm extends JDialog {
    public ConfForm(Frame owner) {
        super(owner);
        initComponents();
        LoadConfig();

    }

    public String LoadConfig(){
        String result;
        ConfFormSettings fs=ConfFormSettings.getInstance();
        result=fs.loadFromFile();
        protocolBox.setModel(new DefaultComboBoxModel(fs.getProtocolList()));
        protocolBox.setSelectedItem(fs.getProtocol());
        hostField.setText(fs.getHost());
        portField.setText(String.valueOf(fs.getPort()));
        loginField.setText(fs.getLogin());
        autoConnectCheck.setSelected(fs.isAutoLogin());
        return result;
    }

    public String SaveConfig(){
        String[] protocols=new String[protocolBox.getModel().getSize()];
        int port=0;

        for (int i = 0; i < protocolBox.getModel().getSize(); i++) {
            protocols[i]=(String)protocolBox.getModel().getElementAt(i);
        }

        try {
            port = Integer.parseInt(portField.getText());
        }catch (NumberFormatException ex){
            return "\"Port\" field should be a number";
        }

        ConfFormSettings fs=ConfFormSettings.getInstance();
                fs.Init(protocols
                        , protocolBox.getSelectedItem().toString(),
                        hostField.getText(), port, loginField.getText(),
                        autoConnectCheck.isSelected());
        return fs.saveToFile();
    }

    public ConfForm(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        panel2 = new JPanel();
        protocolBox = new JComboBox();
        hostField = new JTextField();
        portField = new JFormattedTextField();
        loginField = new JTextField();
        buttonBar = new JPanel();
        autoConnectCheck = new JCheckBox();
        saveBtn = new JButton();
        connectBtn = new JButton();
        okButton = new JButton();
        cancelButton = new JButton();
        saveAction = new SaveConfig();
        cancelAction = new CancelAction();
        okAction = new OkAction();
        TestConnAction = new TestConnectionAction();

        //======== this ========
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration");
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== panel1 ========
                {
                    panel1.setLayout(new GridLayout(4, 0));

                    //---- label2 ----
                    label2.setText("Protocol: ");
                    label2.setHorizontalAlignment(SwingConstants.RIGHT);
                    panel1.add(label2);

                    //---- label3 ----
                    label3.setText("Host: ");
                    label3.setHorizontalAlignment(SwingConstants.RIGHT);
                    panel1.add(label3);

                    //---- label4 ----
                    label4.setText("Port: ");
                    label4.setHorizontalAlignment(SwingConstants.RIGHT);
                    panel1.add(label4);

                    //---- label5 ----
                    label5.setText("Login: ");
                    label5.setHorizontalAlignment(SwingConstants.RIGHT);
                    panel1.add(label5);
                }
                contentPanel.add(panel1, BorderLayout.WEST);

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout(4, 0));

                    //---- protocolBox ----
                    protocolBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    protocolBox.setModel(new DefaultComboBoxModel(new String[] {
                        "JSON",
                        "XML"
                    }));
                    panel2.add(protocolBox);

                    //---- hostField ----
                    hostField.setText("127.0.0.1");
                    panel2.add(hostField);

                    //---- portField ----
                    portField.setColumns(3);
                    portField.setText("8080");
                    panel2.add(portField);

                    //---- loginField ----
                    loginField.setText("Login");
                    panel2.add(loginField);
                }
                contentPanel.add(panel2, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

                //---- autoConnectCheck ----
                autoConnectCheck.setText("AutoConnect");
                buttonBar.add(autoConnectCheck, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- saveBtn ----
                saveBtn.setText("Save");
                saveBtn.setAction(saveAction);
                buttonBar.add(saveBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- connectBtn ----
                connectBtn.setText("Test connection...");
                connectBtn.setAction(TestConnAction);
                buttonBar.add(connectBtn, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- okButton ----
                okButton.setText("OK");
                okButton.setAction(okAction);
                buttonBar.add(okButton, new GridBagConstraints(10, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setAction(cancelAction);
                buttonBar.add(cancelButton, new GridBagConstraints(11, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.PAGE_END);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JPanel panel2;
    private JComboBox protocolBox;
    private JTextField hostField;
    private JFormattedTextField portField;
    private JTextField loginField;
    private JPanel buttonBar;
    private JCheckBox autoConnectCheck;
    private JButton saveBtn;
    private JButton connectBtn;
    private JButton okButton;
    private JButton cancelButton;
    private SaveConfig saveAction;
    private CancelAction cancelAction;
    private OkAction okAction;
    private TestConnectionAction TestConnAction;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private class SaveConfig extends AbstractAction {
        private SaveConfig() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Save");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(ConfForm.this,ConfForm.this.SaveConfig());
        }
    }

    private class CancelAction extends AbstractAction {
        private CancelAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Cancel");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            ConfForm.this.dispose();
        }
    }

    private class OkAction extends AbstractAction {
        private OkAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "OK");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            ConfForm.this.SaveConfig();
            ConfForm.this.dispose();
        }
    }

    private class TestConnectionAction extends AbstractAction {
        private TestConnectionAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Test Connection...");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            ConfForm.this.SaveConfig();
            ConfFormSettings fs=ConfFormSettings.getInstance();
            ChatSession cs=ChatSession.getInstance();
            cs.Init(fs.getProtocol(), fs.getHost(), fs.getPort(), fs.getLogin());
            try {
                if (cs.getSessionID()!=null){
                    JOptionPane.showMessageDialog(ConfForm.this,"Already connected!");
                    return;
                }
                String result = cs.login();

            if (result.equals("200")) {
                JOptionPane.showMessageDialog(ConfForm.this,"Connection successful!");
                cs.logout();
            } else
            {
                JOptionPane.showMessageDialog(ConfForm.this,result);
            }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(ConfForm.this,e1.getMessage());
            }
        }
    }
}
