package GUI.Layout;

import GUI.ActionInterface.TimekeepingAction;
import GUI.Ultilities.FontCustom;
import GUI.Ultilities.HighlightButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

public class ChooseEntitiesDialog extends JDialog implements ActionListener, DocumentListener {

    JPanel GUI = new JPanel(new BorderLayout(0,0));
    Font font20 = FontCustom.getFont("OpenSans",FontCustom.SEMI_BOLD,20f);
    JPanel searchPanel = new JPanel(new BorderLayout(0,0));
    JLabel searchLabel = new JLabel("Search:");
    JTextField jTextField = new JTextField();
    DefaultTableModel model = new DefaultTableModel();

    JTable table = new JTable(model);
    TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
    JScrollPane scrollPane = new JScrollPane(table);
    HighlightButton button = new HighlightButton("Choose");
    String[] displayColumnsName;
    String[] columnsName;

    String data = null;

    public ChooseEntitiesDialog(String[] columnsName,String[] displayColumnsName,Frame frame) {
        super(frame,"Test",true);
        this.columnsName = columnsName;
        this.displayColumnsName = displayColumnsName;
        //this.action = action;
        this.createTable();
        addcomp();
        configuration();
        listener();
    }

    void addcomp() {
        searchPanel.add(searchLabel,BorderLayout.WEST);
        searchPanel.add(jTextField,BorderLayout.CENTER);
        GUI.add(searchPanel,BorderLayout.NORTH);
        GUI.add(scrollPane,BorderLayout.CENTER);
        GUI.add(button,BorderLayout.SOUTH);
        this.setContentPane(GUI);
    }

    void configuration() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.6;
        this.setSize((int) width, (int)(screenSize.getHeight()));
        Dimension tablePreferred = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(tablePreferred.width, (int)(screenSize.getHeight()*0.2)));
        scrollPane.setMinimumSize(new Dimension(tablePreferred.width, 300));
        scrollPane.getViewport().setBackground(Color.WHITE);
        GUI.setBackground(Color.WHITE);
        searchPanel.setBackground(Color.WHITE);
        scrollPane.setBorder(new EmptyBorder(10,0,10,0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(Color.WHITE);
        searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jTextField.setPreferredSize(new Dimension(200,50));
        searchPanel.setBorder(new EmptyBorder(10,10,10,10));
        button.setPreferredSize(new Dimension(GUI.getWidth()-20,50));
        button.setBorder(new EmptyBorder(10,10,10,10));
        button.setHighlight(new Color(217, 83, 79,55));
        button.setEnabled(false);
        Font font = FontCustom.getFont("OpenSans",FontCustom.LIGHT,20);
        searchLabel.setFont(font);
        jTextField.setFont(font);
        //scrollPane.setBorder(new EmptyBorder(10,10,10,10));
        searchPanel.setPreferredSize(jTextField.getPreferredSize());
        table.setRowSorter(rowSorter);
        //this.splitPane.setDividerLocation(0.8);
        this.setTitle("Dashboard");
        this.pack();

        this.setResizable(false);
        try {
            // 1.6+
            this.setLocationByPlatform(true);
            this.setMinimumSize(this.getSize());
        } catch(Throwable ignored) {
        }

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dispose();
        this.setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

    }

    void listener() {
        jTextField.getDocument().addDocumentListener(this);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int j = table.getSelectedRow();
                data = String.valueOf(model.getValueAt(j,0 ));
                button.setEnabled(true);
            }
        });
        button.addActionListener(this);
    }

    public void createTable(){
        for (String s : displayColumnsName) {
            model.addColumn(FontCustom.upcaseFirst(s));
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void updateTable(String[][] objects) {
        model.setRowCount(0);
        for (String[] object : objects) {
            Vector<String> vector = new Vector<String>(Arrays.asList(object));
            model.addRow(vector);
        }
        resizeColumnWidth(table);
        this.setContentPane(GUI);
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

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (data == null) JOptionPane.showMessageDialog(this,"Please choose a row");
//        else dispose();
        dispose();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = jTextField.getText();

        if (text.trim().length() == 0)
            rowSorter.setRowFilter(null);
        else
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = jTextField.getText();

        if (text.trim().length() == 0)
            rowSorter.setRowFilter(null);
        else {
            try {
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not support YET");
    }

    public String run() {
        this.setVisible(true);
        return data;
    }
}
