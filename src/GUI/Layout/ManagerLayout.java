package GUI.Layout;

import GUI.Ultilities.FieldPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ManagerLayout extends JFrame implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) action.add(getValueField());
        if (e.getSource() == updateBtn) action.update(getValueField(),table.getSelectedRow(),getTableRow(table.getSelectedRow()));
        if (e.getSource() == deleteBtn) action.delete(getValueField(),table.getSelectedRow());
        if (e.getSource() == findBtn) action.find(getValueField());
        if (e.getSource() == clearBtn) setValueField(-1);
    }

    String[] columnsName;
    String[][] columnsFieldContent;
    int[] columnsType;
    Action action;
    Vector<Component> components = new Vector<>();
    JPanel GUI = new JPanel(new BorderLayout(5,5));
    JPanel controlsPanel = new JPanel(new BorderLayout(4,4));
    JPanel fieldsControlPanel = new JPanel(new GridLayout(0,1,3,3));
    JPanel actionControlPanel = new JPanel(new GridLayout(0,3,3,3));
    JButton addBtn = new JButton("Create");
    JButton updateBtn = new JButton("Edit");
    JButton deleteBtn = new JButton("Remove");
    JButton findBtn = new JButton("Search");
    JButton clearBtn = new JButton("Clear");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String title ;
    public void configuration() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() * 0.5;
        this.setSize((int) width, (int) height);

        //GUI.setBorder(new TitledBorder("Aefewf"));
        //controlsPanel.setBorder(new TitledBorder("Control Board") );
        fieldsControlPanel.setBorder(new TitledBorder("Fields"));
        actionControlPanel.setBorder(new TitledBorder("Action"));

        Dimension tablePreferred = table.getPreferredSize();
        scrollPane.setPreferredSize(
                new Dimension(tablePreferred.width, tablePreferred.height/3) );

        //this.splitPane.setDividerLocation(0.8);
        this.setTitle(title);
        this.pack();

        this.setResizable(false);
        try {
            // 1.6+
            this.setLocationByPlatform(true);
            this.setMinimumSize(this.getSize());
        } catch(Throwable ignoreAndContinue) {
        }
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

//    public void setTypes( Map<Integer,String[]> types) {
//        columnsType = new int[types.size()];
//        columnsFieldContent = new String[types.size()][];
//        int index = 0;
//        for (Map.Entry<Integer,String[]> entry : types.entrySet()) {
//            columnsType[index] = entry.getKey();
//            columnsFieldContent[index] = entry.getValue();
//        }
//    }

    public Map<String, Object> getTableRow(int index) {
        if (index < 0) return null;
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < columnsName.length; i++) {
            map.put(columnsName[i], model.getValueAt(index,i) );
        }
        return map;
    }

    public Map<String, Object> getValueField() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < columnsName.length; i++) {
            if (components.get(i) instanceof JTextField)
                map.put(columnsName[i], ((JTextField) components.get(i)).getText() );
            if (components.get(i) instanceof JComboBox)
                map.put(columnsName[i], ((JComboBox) components.get(i)).getSelectedItem() );
        }
        return map;
    }

    public void setValueField(int j) {
        for (int i = 0; i < columnsName.length; i++) {
            if (components.get(i) instanceof JTextField)
                ((JTextField) components.get(i)).setText((j >= 0) ? String.valueOf(model.getValueAt(j,i)) : "") ;
            if (components.get(i) instanceof JComboBox)
                ((JComboBox) components.get(i)).setSelectedItem((j >= 0) ? model.getValueAt(j,i) : "Nam");
        }
    }

    public void addComp() {

        for (int i = 0; i < columnsName.length; i++) {
            FieldPanel jPanel = new FieldPanel(columnsName[i],columnsType[i] ,columnsFieldContent[i] , new Dimension(300,50),new GridLayout(0,1,0 ,0),true);
            components.add(jPanel.getInput());
            fieldsControlPanel.add(jPanel);
        }
        actionControlPanel.add(addBtn);actionControlPanel.add(updateBtn);actionControlPanel.add(deleteBtn);actionControlPanel.add(findBtn);actionControlPanel.add(clearBtn);
        controlsPanel.add(new JScrollPane(fieldsControlPanel),BorderLayout.CENTER);
        controlsPanel.add(new JScrollPane(actionControlPanel), BorderLayout.SOUTH);
        GUI.add(controlsPanel, BorderLayout.WEST);
        GUI.add(scrollPane,BorderLayout.CENTER);
        this.setContentPane(GUI);
    }

    public void listener() {
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        findBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int j = table.getSelectedRow();
                setValueField(j);
            }
        });
    }

    public void createTable(){
        table.setModel(model);
        for (String s : columnsName) {
            model.addColumn(s);
        }
    }

    public void updateTable(String[][] objects) {
        model.setRowCount(0);
        for (String[] object : objects) {
            Vector<String> vector = new Vector<String>(Arrays.asList(object));
            model.addRow(vector);
        }
    }


    public ManagerLayout(String title, String[] columns, int[] types, String[][] typesContent, Action action) {
        this.title = title;
        this.columnsName = columns;
        this.columnsType = types;
        this.columnsFieldContent = typesContent;
        this.action = action;
        //columnsType = new int[] {FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,
         //       FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD};
        this.createTable();
        this.addComp();
        this.configuration();
        this.listener();
    }
}
