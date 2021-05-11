package GUI.Layout;

import BLL.DTO.Department;
import BLL.DTO.Employee;
import BLL.DTO.Project;
import BLL.DepartmentBLL;
import BLL.EmployeeBLL;
import BLL.ProjectBLL;
import DAL.DepartmentDAL;
import DAL.Eloquent.Eloquent;
import DAL.EmployeeDAL;
import DAL.ProjectDAL;
import GUI.ActionInterface.ManagerAction;
import GUI.GUI;
import GUI.Ultilities.FieldPanel;
import GUI.Ultilities.FontCustom;
import GUI.Ultilities.HighlightButton;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ManagerLayout extends JFrame implements ActionListener {

    private String[][] dataTable;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) action.add(getValueField());
        if (e.getSource() == updateBtn) action.update(getValueField(),table.getSelectedRow(),getTableRow(table.getSelectedRow()));
        if (e.getSource() == deleteBtn) action.delete(getValueField(),table.getSelectedRow());
        if (e.getSource() == findBtn) action.find();
        if (e.getSource() == clearBtn) setValueField(-1);
        if (e.getSource() == allBtn) {
            allBtn.setEnabled(false);
            action.showAll();
        }
        if (e.getSource() == closeBtn) action.back();
    }

    String[] displayColumnsName;
    String[] columnsName;
    Map<String,Class<?>> entityFields;
    Map<Integer,String> columnsEntity;
    String[][] columnsFieldContent;
    int[] columnsType;
    ManagerAction action;
    Vector<Component> components = new Vector<>();
    JPanel gui = new JPanel(new BorderLayout(5,5));
    JPanel controlsPanel = new JPanel(new BorderLayout(4,4));
    JPanel fieldsControlPanel = new JPanel(new GridLayout(0,1,3,3));
    JPanel actionControlPanel = new JPanel(new GridLayout(0,3,3,3));
    Image addIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/132-add.png")));
    Image updateIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/092-edit.png")));
    Image deleteIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/097-delete-3.png")));
    Image findIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/024-loupe.png")));
    Image clearIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/099-delete-1.png")));
    Image allIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/063-more.png")));
    Image closeIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/interface13/010-undo.png")));
    HighlightButton addBtn = new HighlightButton("Create",new ImageIcon(addIcon));
    HighlightButton updateBtn = new HighlightButton("Edit",new ImageIcon(updateIcon));
    HighlightButton deleteBtn = new HighlightButton("Remove",new ImageIcon(deleteIcon));
    HighlightButton findBtn = new HighlightButton("Search",new ImageIcon(findIcon));
    HighlightButton clearBtn = new HighlightButton("Clear",new ImageIcon(clearIcon));
    HighlightButton allBtn = new HighlightButton("Show all",new ImageIcon(allIcon));
    HighlightButton closeBtn = new HighlightButton("Back to menu",new ImageIcon(closeIcon));
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable();
    JLabel tableStatus = new JLabel();
    JScrollPane scrollPane = new JScrollPane(table);
    Font font;
    String title ;
    Integer columnSort = null;

    public void configuration() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.7;
        double height = screenSize.getHeight() * 0.7;
        //this.setSize((int) width, (int) height);

        fieldsControlPanel.setBorder(new TitledBorder("Fields"));
        fieldsControlPanel.setBackground(Color.WHITE);
//        int a = 0;
//        for (Component component : fieldsControlPanel.getComponents()) {
//            a+=component.getHeight();
//        }
//        fieldsControlPanel.setPreferredSize(new Dimension(actionControlPanel.getWidth(),a));
        fieldsControlPanel.setMaximumSize(new Dimension(actionControlPanel.getWidth(),(int)height-actionControlPanel.getHeight()));
        actionControlPanel.setBorder(new TitledBorder("Action"));
        actionControlPanel.setBackground(Color.WHITE);

        addBtn.setMargin(new Insets(2,2,2,2));
        addBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        addBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        addBtn.setHighlight(new Color(91, 192, 222, 100));

        updateBtn.setMargin(new Insets(2,2,2,2));
        updateBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        updateBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        updateBtn.setHighlight(new Color(240, 173, 78, 100));

        deleteBtn.setMargin(new Insets(2,2,2,2));
        deleteBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteBtn.setHighlight(new Color(217, 83, 79, 100));

        findBtn.setMargin(new Insets(2,2,2,2));
        findBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        findBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        findBtn.setHighlight(new Color(92, 184, 92, 100));

        clearBtn.setMargin(new Insets(2,2,2,2));
        clearBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        clearBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        clearBtn.setHighlight(new Color(2, 117, 216, 100));

        allBtn.setMargin(new Insets(2,2,2,2));
        allBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        allBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        allBtn.setHighlight(new Color(255, 255, 0, 100));

        closeBtn.setMargin(new Insets(2,2,2,2));
        closeBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        closeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        closeBtn.setHighlight(new Color(217, 83, 79, 100));

        int controlsPanelHeight = 0;
        controlsPanelHeight+=actionControlPanel.getPreferredSize().height;
        for (int i = 0; i < fieldsControlPanel.getComponents().length; i++) {
            if (i < 7)
                controlsPanelHeight+=fieldsControlPanel.getComponent(i).getPreferredSize().height;
        }

        controlsPanel.setPreferredSize(new Dimension((int)(width*0.3),(int)controlsPanelHeight));
        Dimension tablePreferred = table.getPreferredSize();
        scrollPane.setPreferredSize(
                new Dimension(tablePreferred.width, tablePreferred.height/3) );
        allBtn.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setFont(font);


        //this.splitPane.setDividerLocation(0.8);
        this.setTitle(title);
        this.pack();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dispose();
        this.setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    }

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
            if (components.get(i) instanceof JDatePickerImpl) {
                Date date = (Date) ((JDatePickerImpl) components.get(i)).getModel().getValue();
                map.put(columnsName[i], date == null ? null :date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }

        }
        return map;
    }

    public void setValueField(int j) {
        for (int i = 0; i < columnsName.length; i++) {
            if (components.get(i) instanceof JTextField)
                ((JTextField) components.get(i)).setText((j >= 0) ? String.valueOf(dataTable[j][i]) : "") ;
            if (components.get(i) instanceof JComboBox)
                ((JComboBox) components.get(i)).setSelectedItem((j >= 0) ? dataTable[j][i] : "Nam");
            if (components.get(i) instanceof JDatePickerImpl) {
                if (j >= 0) {
                    LocalDate l = LocalDate.parse(String.valueOf(dataTable[j][i]));
                    ((JDatePickerImpl) components.get(i)).getModel().setDate(l.getYear(),l.getMonthValue(),l.getDayOfMonth());
                }
                else ((JDatePickerImpl) components.get(i)).getModel().setValue(null);
            }
        }
    }

    public void addComp() {

        for (int i = 0; i < columnsName.length; i++) {
            FieldPanel jPanel = new FieldPanel(FontCustom.upcaseFirst(displayColumnsName[i]),columnsType[i] ,columnsFieldContent[i] , new Dimension(200,35),new GridLayout(0,1,0 ,0),true,font);
            if (entityFields != null && entityFields.containsKey(columnsName[i])) {
                try {
                    setEntityField(jPanel.getInput(),entityFields.get(columnsName[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            components.add(jPanel.getInput());
            fieldsControlPanel.add(jPanel);
        }
        actionControlPanel.add(addBtn);actionControlPanel.add(updateBtn);actionControlPanel.add(deleteBtn);actionControlPanel.add(findBtn);actionControlPanel.add(clearBtn);actionControlPanel.add(allBtn);actionControlPanel.add(closeBtn);
        controlsPanel.add(new JScrollPane(fieldsControlPanel),BorderLayout.CENTER);
        controlsPanel.add(new JScrollPane(actionControlPanel), BorderLayout.SOUTH);
        gui.add(controlsPanel, BorderLayout.WEST);
        gui.add(scrollPane,BorderLayout.CENTER);
        this.setContentPane(gui);
    }

    public void setEntityField(Component component,Class<?> c) throws Exception {
        JTextField jTextField;

        if (component instanceof JTextField)
            jTextField = (JTextField) component;
        else throw new Exception("Entity is not JTextField");
        jTextField.setFocusable(false);
        jTextField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[][] data;
                switch (c.getSimpleName()) {
                    case "Employee" -> data = EmployeeBLL.all(null);
                    case "Department" -> data = DepartmentBLL.all(null);
                    case "Project" -> data = ProjectBLL.all(null);
                    default -> throw new IllegalStateException("Unexpected value: " + c.getSimpleName());
                }
                if (data.length > 0) {
                    ChooseEntitiesDialog chooseEntitiesDialog = new ChooseEntitiesDialog(GUI.getColumns(c),GUI.getColumnsDisplay(c),ManagerLayout.this);
                    chooseEntitiesDialog.updateTable(data);
                    jTextField.setText(chooseEntitiesDialog.run());
                }
                else {
                    JOptionPane.showMessageDialog(ManagerLayout.this,"Nothing to choose","Error",JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    public void listener() {
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        findBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        allBtn.addActionListener(this);
        closeBtn.addActionListener(this);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int j = table.getSelectedRow();
                setValueField(j);
            }
        });
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int col = table.columnAtPoint(e.getPoint());
                String name = table.getColumnName(col);
                changeStatusHeaderTable(col,name);
            }
        });
    }

    public void changeStatusHeaderTable(Integer col, String name) {
        if (columnSort == null) {
            columnSort = col;
            table.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(name + "\u23F6");
            action.sort(name, Eloquent.ASC);
            table.getTableHeader().resizeAndRepaint();
        }
        else {
            if (!columnSort.equals(col)){
                table.getTableHeader().getColumnModel().getColumn(columnSort).setHeaderValue(table.getColumnName(columnSort));
                table.getTableHeader().resizeAndRepaint();
                table.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(name+ "\u23F6");
                action.sort(name, Eloquent.ASC);
                table.getTableHeader().resizeAndRepaint();
                columnSort = col;
            }
            else {
                if (table.getTableHeader().getColumnModel().getColumn(col).getHeaderValue().equals(name + "\u23F6")) {
                    table.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(name + "\u23F7");
                    action.sort(name, Eloquent.DESC);
                }
                else {
                    table.getTableHeader().getColumnModel().getColumn(col).setHeaderValue(name + "\u23F6");
                    action.sort(name, Eloquent.ASC);
                }
                table.getTableHeader().resizeAndRepaint();
            }
        }

    }

    public void createTable(){
        table.setModel(model);
        for (String s : displayColumnsName) {
            model.addColumn(FontCustom.upcaseFirst(s));
        }
        columnsEntity = new HashMap<>();
        for (int i = 0; i < columnsName.length; i++) {
            if (columnsName[i].contains("_id"))
                columnsEntity.put(i,columnsName[i].substring(0,columnsName[i].length()-3));
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void setDataTable(String[][] dataTable) {
        this.dataTable = dataTable;
        System.out.println(Arrays.deepToString(dataTable));
    }

    public void updateTable(String[][] objects) {
        setDataTable(objects.clone());
        model.setRowCount(0);
        gui.remove(tableStatus);
        this.setContentPane(gui);
        for (int i = 0; i < objects.length; i++) {
            Vector<String> vector = new Vector<>();
            for (int j = 0; j < objects[i].length; j++) {
                if (columnsEntity.containsKey(j)) {
                    int id = Integer.parseInt(objects[i][j]);
                    switch (columnsEntity.get(j)) {
                        case "employee" :
                            Employee employee = EmployeeDAL.getField(id);
                            vector.add( employee.getFirstname() + " " + employee.getLastname());
                            break;
                        case "department" :
                            Department department = DepartmentDAL.getField(id);
                            vector.add( department.getName());
                            break;
                        case "project" :
                            Project project = ProjectDAL.getField(id);
                            vector.add( project.getName());
                            break;
                        default:
                            break;
                    }
                }
                else vector.add(objects[i][j]);
            }
            model.addRow(vector);


        }
        resizeColumnWidth(table);
        setValueField(-1);
    }

    public void searched(String status) {
        allBtn.setEnabled(true);
        tableStatus.setText(status);
        tableStatus.setHorizontalAlignment(SwingConstants.CENTER);
        tableStatus.setForeground(Color.RED);
        gui.add(tableStatus, BorderLayout.NORTH);
        this.setContentPane(gui);
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            width = Math.max(width, table.getColumnModel().getColumn(column).getPreferredWidth());
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public ManagerLayout(String title, String[] displayColumnsName, String[] columns, Map<String, Class<?>> entityFields, int[] types, String[][] typesContent, ManagerAction action) throws IOException {
        this.entityFields = entityFields;
        this.title = title;
        this.displayColumnsName = displayColumnsName;
        this.columnsName = columns;
        this.columnsType = types;
        this.columnsFieldContent = typesContent;
        this.action = action;
        font = FontCustom.getFont("OpenSans",FontCustom.SEMI_BOLD,20);
        //columnsType = new int[] {FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,
         //       FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD,FieldPanel.TEXT_FIELD};
        this.createTable();
        this.addComp();
        this.configuration();
        this.listener();
    }
}
