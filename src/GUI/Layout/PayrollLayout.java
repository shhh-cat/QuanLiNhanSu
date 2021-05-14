package GUI.Layout;

import BLL.DTO.Department;
import BLL.DTO.Employee;
import BLL.DTO.Project;
import BLL.DTO.TimeKeeping;
import BLL.EmployeeBLL;
import BLL.TimeKeepingBLL;
import DAL.DepartmentDAL;
import DAL.Eloquent.Eloquent;
import DAL.EmployeeDAL;
import DAL.ProjectDAL;
import DAL.TimeKeepingDAL;
import GUI.ActionInterface.PayrollAction;
import GUI.GUI;
import GUI.Ultilities.FieldPanel;
import GUI.Ultilities.FontCustom;
import GUI.Ultilities.HighlightButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.List;

public class PayrollLayout extends JFrame implements ActionListener {

    PayrollAction payrollAction;
    JLabel jLabel = new JLabel("Payroll of Employee");
    Font font = FontCustom.getFont("OpenSans",FontCustom.SEMI_BOLD_ITALIC,15);;
    JPanel gui = new JPanel(new BorderLayout(5,5));
    JPanel controlsPanel = new JPanel(new GridLayout(1, 0));
    JPanel left = new JPanel(new GridLayout(0,1,3,3));
    JPanel right = new JPanel(new GridLayout(0,1,3,3));
    FieldPanel emplName = new FieldPanel("Employee name: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel deptName = new FieldPanel("Department: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel projName = new FieldPanel("Project: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel salary = new FieldPanel("Salary(Month): ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel seniority = new FieldPanel("Seniority: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel position = new FieldPanel("Position: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel bonus_position = new FieldPanel("Bonus position(%): ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel numWork = new FieldPanel("Number of working days: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel stanDay = new FieldPanel("Standard workday: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel salaryDays = new FieldPanel("Salary(Day): ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel basicSalary = new FieldPanel("Basic Salary(subtracted violation): ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    FieldPanel totalSalary = new FieldPanel("Total Salary(include bonus): ",FieldPanel.TEXT_FIELD,null,new Dimension(200,25),new GridLayout(1,0,0,0),true,font);
    HighlightButton back = new HighlightButton("Back to menu");
    HighlightButton another = new HighlightButton("Another employee");

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);

    public PayrollLayout(PayrollAction payrollAction) {
        this.payrollAction = payrollAction;
        createTable();
        addcomp();
        configuration();
        listener();
    }

    void listener() {
        back.addActionListener(this);
        another.addActionListener(this);
    }

    void configuration() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.7;
        double height = screenSize.getHeight() * 0.7;

        back.setMargin(new Insets(2,2,2,2));
        back.setVerticalTextPosition(SwingConstants.BOTTOM);
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        back.setHighlight(new Color(217, 83, 79, 100));

        another.setMargin(new Insets(2,2,2,2));
        another.setVerticalTextPosition(SwingConstants.BOTTOM);
        another.setHorizontalTextPosition(SwingConstants.CENTER);
        another.setHighlight(new Color(92, 184, 92, 100));

        left.setBorder(new EmptyBorder(10,10,5,10));
        right.setBorder(new EmptyBorder(10,0,5,10));
        controlsPanel.setBorder(new EmptyBorder(5,10,10,10));
        back.setBorder(new EmptyBorder(0,0,0,5));
        another.setBorder(new EmptyBorder(0,5,0,0));
        //fieldsControlPanel.setBorder(new TitledBorder("Fields"));
        left.setBackground(Color.WHITE);
        right.setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE
        );
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        Dimension tablePreferred = table.getPreferredSize();
        scrollPane.setPreferredSize(
                new Dimension((int)(tablePreferred.width * 1.1), tablePreferred.height/3) );
        scrollPane.setBorder(new EmptyBorder(10,0,5,10));
        left.setMaximumSize(new Dimension((int)width,(int)height));
        jLabel.setFont(FontCustom.getFont("OpenSans",FontCustom.SEMI_BOLD,30));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setTitle("Payroll");
        this.pack();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dispose();
        this.setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    }

    public void createTable(){
        table.setModel(model);
        String[] displayColumnsName = new String[] {"Date", "Violation"};
        for (String s : displayColumnsName) {
            model.addColumn(FontCustom.upcaseFirst(s));
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void updateTable(String[][] objects) {
        model.setRowCount(0);
        this.setContentPane(gui);
        for (int i = 0; i < objects.length; i++) {
            Vector<String> vector = new Vector<>();
            for (int j = 0; j < objects[i].length; j++) {
                if (j == 1 || j == 2)
                    vector.add(objects[i][j]);
            }
            model.addRow(vector);
        }
        resizeColumnWidth(table);
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

    public String getIDEmployee() {
        ChooseEntitiesDialog chooseEntitiesDialog = new ChooseEntitiesDialog(GUI.getColumns(Employee.class),GUI.getColumnsDisplay(Employee.class),this);
        chooseEntitiesDialog.updateTable(EmployeeBLL.all(null));
        return chooseEntitiesDialog.run();
    }

    public void updateData(Employee employee,int month, int year) {
        LocalDate now = LocalDate.now();
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        jLabel.setText("Payroll of "+ employee.getFirstname());
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        Department department = DepartmentDAL.getField(employee.getDepartment_id());
        Project project = ProjectDAL.getField(employee.getProject_id());
        TimeKeeping[] timeKeepings = TimeKeepingDAL.getObjects("employee_id",employee.getId(), Eloquent.EQUAL);
        List<String[]> timeKeeping = new ArrayList<>();

        for (TimeKeeping timeKeepingg : timeKeepings) {
            if (timeKeepingg.getDate().getMonthValue() == month && timeKeepingg.getDate().getYear() == year)
                timeKeeping.add(timeKeepingg.toStrings());
        }
        ((JTextField) emplName.getInput()).setText(employee.getFirstname() + " " + employee.getLastname());
        ((JTextField) emplName.getInput()).setEditable(false);
        ((JTextField) deptName.getInput()).setText(department.getName());
        ((JTextField) deptName.getInput()).setEditable(false);
        ((JTextField) projName.getInput()).setText(project.getName());
        ((JTextField) projName.getInput()).setEditable(false);
        ((JTextField) salary.getInput()).setText(formatter.format(employee.getSalary()));
        ((JTextField) salary.getInput()).setEditable(false);
        Period period = Period.between(employee.getStart_date(),now);
        ((JTextField) seniority.getInput()).setText(String.valueOf(period.getMonths()));
        ((JTextField) seniority.getInput()).setEditable(false);
        ((JTextField) position.getInput()).setText(employee.getPosition());
        ((JTextField) position.getInput()).setEditable(false);
        ((JTextField) bonus_position.getInput()).setText(employee.getBonus_position() + "%");
        ((JTextField) bonus_position.getInput()).setEditable(false);
        ((JTextField) numWork.getInput()).setText(String.valueOf(timeKeeping.size()));
        ((JTextField) numWork.getInput()).setEditable(false);
        int standardWork = 26;
        int salaryDay = employee.getSalary() / 26;
        ((JTextField) stanDay.getInput()).setText(String.valueOf(standardWork));
        ((JTextField) stanDay.getInput()).setEditable(false);
        ((JTextField) salaryDays.getInput()).setText(formatter.format(salaryDay));
        ((JTextField) salaryDays.getInput()).setEditable(false);
        int basicSalary = 0;
        for (String[] strings : timeKeeping) {
            int violation = Integer.parseInt(strings[2]);
            basicSalary += salaryDay - (salaryDay * violation / 100);
        }
        ((JTextField) this.basicSalary.getInput()).setText(formatter.format(basicSalary));
        ((JTextField) this.basicSalary.getInput()).setEditable(false);
        int totalSalary = basicSalary + basicSalary * employee.getBonus_position() / 100;
        ((JTextField) this.totalSalary.getInput()).setText(formatter.format(totalSalary));
        ((JTextField) this.totalSalary.getInput()).setEditable(false);
        updateTable(timeKeeping.toArray(new String[0][]));
    }

    void addcomp(){
        controlsPanel.add(back);
        controlsPanel.add(another);
        left.add(emplName);
        left.add(deptName);
        left.add(projName);
        left.add(salary);
        left.add(seniority);
        left.add(position);
        right.add(bonus_position);
        right.add(numWork);
        right.add(stanDay);
        right.add(salaryDays);
        right.add(basicSalary);
        right.add(totalSalary);
        gui.add(left,BorderLayout.WEST);
        gui.add(right,BorderLayout.CENTER);
        gui.add(controlsPanel,BorderLayout.SOUTH);
        gui.add(jLabel,BorderLayout.NORTH);
        gui.add(scrollPane,BorderLayout.EAST);
        this.setContentPane(gui);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back)
            payrollAction.back();
        if (e.getSource() == another)
            payrollAction.another();
    }
}
