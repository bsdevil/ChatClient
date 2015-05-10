/*
 * Created by JFormDesigner on Tue May 05 16:53:53 EEST 2015
 */

package ChatGUI;

import Utils.DataModel.Room;
import Utils.DataModel.RoomList;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class RoomManageForm extends JDialog {
    public RoomManageForm(Frame owner) {
        super(owner);
        initComponents();
        aRoomTable.setModel(new AvailRoomTableModel());
        oRoomTable.setModel(new OpenRoomTableModel());
    }

    public RoomManageForm(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        aRoomTable = new JTable();
        addOneBtn = new JButton();
        scrollPane2 = new JScrollPane();
        oRoomTable = new JTable();
        addAllBtn = new JButton();
        delOneBtn = new JButton();
        delAllBtn = new JButton();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();
        cancelAction = new CancelAction();
        aoOneRoomMove = new AvailOpenedOneRoomMoveAction();
        oaOneRoomMoveAction = new OpenedAvailOneRoomMoveAction();
        aoAllRoomMoveAction = new AvailOpenedAllRoomMoveAction();
        oaAllRoomMoveAction = new OpenedAvailAllRoomMoveAction();
        okAction = new OkAction();

        //======== this ========
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rooms manager");
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setPreferredSize(new Dimension(500, 500));
                contentPanel.setMinimumSize(new Dimension(500, 600));
                contentPanel.setMaximumSize(new Dimension(500, 600));
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 232, 90, 125, 87, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};

                //======== scrollPane1 ========
                {

                    //---- aRoomTable ----
                    aRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    aRoomTable.setPreferredSize(new Dimension(30, 32));
                    aRoomTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
                    aRoomTable.setShowHorizontalLines(false);
                    aRoomTable.setShowVerticalLines(false);
                    aRoomTable.setFillsViewportHeight(true);
                    scrollPane1.setViewportView(aRoomTable);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 2, 2, 5, 0.4, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- addOneBtn ----
                addOneBtn.setAction(aoOneRoomMove);
                contentPanel.add(addOneBtn, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                    new Insets(0, 5, 5, 5), 0, 0));

                //======== scrollPane2 ========
                {

                    //---- oRoomTable ----
                    oRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    oRoomTable.setPreferredSize(new Dimension(30, 32));
                    oRoomTable.setShowHorizontalLines(false);
                    oRoomTable.setShowVerticalLines(false);
                    oRoomTable.setPreferredScrollableViewportSize(new Dimension(100, 400));
                    oRoomTable.setFillsViewportHeight(true);
                    scrollPane2.setViewportView(oRoomTable);
                }
                contentPanel.add(scrollPane2, new GridBagConstraints(3, 2, 2, 5, 0.4, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- addAllBtn ----
                addAllBtn.setAction(aoAllRoomMoveAction);
                contentPanel.add(addAllBtn, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTH, GridBagConstraints.NONE,
                    new Insets(0, 5, 0, 5), 0, 0));

                //---- delOneBtn ----
                delOneBtn.setAction(oaOneRoomMoveAction);
                contentPanel.add(delOneBtn, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- delAllBtn ----
                delAllBtn.setAction(oaAllRoomMoveAction);
                contentPanel.add(delAllBtn, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTH, GridBagConstraints.NONE,
                    new Insets(0, 5, 0, 5), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setAction(okAction);
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setAction(cancelAction);
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTable aRoomTable;
    private JButton addOneBtn;
    private JScrollPane scrollPane2;
    private JTable oRoomTable;
    private JButton addAllBtn;
    private JButton delOneBtn;
    private JButton delAllBtn;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    private CancelAction cancelAction;
    private AvailOpenedOneRoomMoveAction aoOneRoomMove;
    private OpenedAvailOneRoomMoveAction oaOneRoomMoveAction;
    private AvailOpenedAllRoomMoveAction aoAllRoomMoveAction;
    private OpenedAvailAllRoomMoveAction oaAllRoomMoveAction;
    private OkAction okAction;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private class CancelAction extends AbstractAction {
        private CancelAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "Cancel");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            RoomManageForm.this.dispose();
        }
    }
    private class AvailRoomTableModel extends AbstractTableModel{
        private int rowCount;
        private ArrayList<String> dataArrayList;

        public AvailRoomTableModel() {
            ArrayList<String> oppenedRooms=MainForm.getInstance().getOppenedRoomsName();
            this.rowCount = 0;
            dataArrayList=new ArrayList<String>();
            String excludeRoomName=RoomList.getInstance().getDefaultRoom().getName();

            for (Room r:RoomList.getInstance().getRooms()) {
                String newRoom=r.getName();
                if ((!newRoom.equals(excludeRoomName)) && (!oppenedRooms.contains(newRoom))){
                    dataArrayList.add(newRoom);
                    rowCount++;
                }
            }
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int column) {
            return "Available rooms:";
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return dataArrayList.toArray()[rowIndex];
        }

        public void removeRow(int rowIndex){
            dataArrayList.remove(rowIndex);
            rowCount--;
            aRoomTable.updateUI();
        }
        public void addRow(String row){
            dataArrayList.add(row);
            rowCount++;
            aRoomTable.updateUI();
        }


    }

    private class OpenRoomTableModel extends AbstractTableModel {
        private int rowCount;
        private ArrayList<String> dataArrayList;

        public OpenRoomTableModel() {
            this.dataArrayList = MainForm.getInstance().getOppenedRoomsName();
            this.rowCount = dataArrayList.size();
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }
        @Override
        public String getColumnName(int column) {
            return "Open rooms:";
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return dataArrayList.toArray()[rowIndex];
        }

        public void removeRow(int rowIndex){
            dataArrayList.remove(rowIndex);
            rowCount--;
            oRoomTable.updateUI();
        }
        public void addRow(String row){
            dataArrayList.add(row);
            rowCount++;
            oRoomTable.updateUI();
        }
    }

    private class AvailOpenedOneRoomMoveAction extends AbstractAction {
        private AvailOpenedOneRoomMoveAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, ">");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            int rowIndex=aRoomTable.getSelectedRow();
            AvailRoomTableModel ar = (AvailRoomTableModel) aRoomTable.getModel();
            OpenRoomTableModel or = (OpenRoomTableModel) oRoomTable.getModel();
            if ((rowIndex>-1)&&(aRoomTable.getRowCount()>0)) {
                or.addRow((String) ar.getValueAt(rowIndex, 0));
                ar.removeRow(rowIndex);
            }

        }
    }

    private class OpenedAvailOneRoomMoveAction extends AbstractAction {
        private OpenedAvailOneRoomMoveAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "<");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            int rowIndex=oRoomTable.getSelectedRow();
            AvailRoomTableModel ar = (AvailRoomTableModel) aRoomTable.getModel();
            OpenRoomTableModel or = (OpenRoomTableModel) oRoomTable.getModel();
            if ((rowIndex>-1)&&(oRoomTable.getRowCount()>0)) {
                ar.addRow((String) or.getValueAt(rowIndex, 0));
                or.removeRow(rowIndex);
            }
        }
    }

    private class AvailOpenedAllRoomMoveAction extends AbstractAction {
        private AvailOpenedAllRoomMoveAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, ">>");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            int num=aRoomTable.getRowCount();
            AvailRoomTableModel ar = (AvailRoomTableModel) aRoomTable.getModel();
            OpenRoomTableModel or = (OpenRoomTableModel) oRoomTable.getModel();
            for (int i = 0; i <num; i++) {
                if (aRoomTable.getRowCount()>0) {
                    or.addRow((String) ar.getValueAt(0, 0));
                    ar.removeRow(0);
                }
            }
        }
    }

    private class OpenedAvailAllRoomMoveAction extends AbstractAction {
        private OpenedAvailAllRoomMoveAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "<<");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            int num = oRoomTable.getRowCount();
            AvailRoomTableModel ar = (AvailRoomTableModel) aRoomTable.getModel();
            OpenRoomTableModel or = (OpenRoomTableModel) oRoomTable.getModel();
            for (int i = 0; i < num; i++) {
                if (oRoomTable.getRowCount() > 0) {
                    ar.addRow((String) or.getValueAt(0, 0));
                    or.removeRow(0);
                }
            }
        }
    }

    private class OkAction extends AbstractAction {
        private OkAction() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            putValue(NAME, "OK");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            MainForm mainForm=MainForm.getInstance();
            int num = oRoomTable.getRowCount();
            ArrayList<String> roomsToOpen=new ArrayList<String>();
            OpenRoomTableModel or = (OpenRoomTableModel) oRoomTable.getModel();
            ArrayList<String> oppenedRooms=mainForm.getOppenedRoomsName();

            for (String s:oppenedRooms){
                if (!roomsToOpen.contains(s)){
                    mainForm.delRoom(s);
                }
            }

            for (int i = 0; i < num; i++) {
                roomsToOpen.add((String)or.getValueAt(i,0));
            }
            mainForm.createRoomsTP(roomsToOpen);
            RoomManageForm.this.dispose();
        }
    }
}
