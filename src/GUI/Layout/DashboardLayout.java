package GUI.Layout;

import GUI.Ultilities.HighlightButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class DashboardLayout extends JFrame {

    Image background = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Background/Background.jpg")));
    ImagePanel GUI = new ImagePanel(background,new BorderLayout(5,5));
    JLabel hello = new JLabel("Hi! ");
    JPanel menu = new JPanel(new GridLayout(2,3,50,25));
    JPanel empty1 = new JPanel();
    JPanel empty2 = new JPanel();
    JPanel empty3 = new JPanel();
    Image imgEmployee = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/employee.png")));
    Image imgDepartment = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/department.png")));
    Image imgProject = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/project.png")));
    Image imgPayroll = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/payroll.png")));
    Image imgTimekeeping = ImageIO.read(Objects.requireNonNull(getClass().getResource("../../RES/Icon/timekeeping.png")));
    HighlightButton jButtonEmployee = new HighlightButton("Employee",new ImageIcon(imgEmployee));
    HighlightButton jButtonDepartment = new HighlightButton("Department",new ImageIcon(imgDepartment));
    HighlightButton jButtonProject = new HighlightButton("Project",new ImageIcon(imgProject));
    HighlightButton jButtonPayroll = new HighlightButton("Payroll",new ImageIcon(imgPayroll));
    HighlightButton jButtonTimekeeping = new HighlightButton("Timekeeping",new ImageIcon(imgTimekeeping));

    public DashboardLayout() throws IOException {
        addcomp();
        configuration();
    }

    public void addcomp() {
        menu.add(jButtonEmployee);
        menu.add(jButtonDepartment);
        menu.add(jButtonPayroll);
        menu.add(jButtonProject);
        menu.add(jButtonTimekeeping);
        GUI.add(hello,BorderLayout.NORTH);
        GUI.add(empty1,BorderLayout.WEST);
        GUI.add(empty2,BorderLayout.EAST);
        GUI.add(empty3,BorderLayout.SOUTH);
        GUI.add(menu,BorderLayout.CENTER);
        this.setContentPane(GUI);
    }

    public void configuration() throws IOException {
        empty1.setPreferredSize(new Dimension(100,100));
        empty1.setBackground(new Color(0,0,0,0));
        empty2.setPreferredSize(new Dimension(100,100));
        empty2.setBackground(new Color(0,0,0,0));
        empty3.setPreferredSize(new Dimension(100,100));
        empty3.setBackground(new Color(0,0,0,0));
        menu.setBackground(new Color(255,255,255,155));
        menu.setBorder(new EmptyBorder(10, 10, 10, 10));
        hello.setFont(new Font("Arial",Font.BOLD,40));
        hello.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.7;
        double height = screenSize.getHeight() * 0.7;
        this.setSize((int) width, (int) height);
        GUI.setBackground(new Color(168,246,255,255));

        jButtonEmployee.setMargin(new Insets(20,20,20,20));
        jButtonEmployee.setFont(new Font("Arial", Font.PLAIN, 30));
        jButtonEmployee.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButtonEmployee.setHorizontalTextPosition(SwingConstants.CENTER);
        jButtonEmployee.setHighlight(new Color(217, 83, 79, 100));


        jButtonDepartment.setMargin(new Insets(20,20,20,20));
        jButtonDepartment.setFont(new Font("Arial", Font.PLAIN, 30));
        jButtonDepartment.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButtonDepartment.setHorizontalTextPosition(SwingConstants.CENTER);
        jButtonDepartment.setHighlight(new Color(92, 184, 92, 100));

        jButtonPayroll.setMargin(new Insets(20,20,20,20));
        jButtonPayroll.setFont(new Font("Arial", Font.PLAIN, 30));
        jButtonPayroll.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButtonPayroll.setHorizontalTextPosition(SwingConstants.CENTER);
        jButtonPayroll.setHighlight(new Color(240, 173, 78, 100));

        jButtonProject.setMargin(new Insets(20,20,20,20));
        jButtonProject.setFont(new Font("Arial", Font.PLAIN, 30));
        jButtonProject.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButtonProject.setHorizontalTextPosition(SwingConstants.CENTER);
        jButtonProject.setHighlight(new Color(2, 117, 216, 100));

        jButtonTimekeeping.setMargin(new Insets(20,20,20,20));
        jButtonTimekeeping.setFont(new Font("Arial", Font.PLAIN, 30));
        jButtonTimekeeping.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButtonTimekeeping.setHorizontalTextPosition(SwingConstants.CENTER);
        jButtonTimekeeping.setHighlight(new Color(91, 192, 222, 100));

        //this.splitPane.setDividerLocation(0.8);
        this.setTitle("Dashboard");

        //this.setResizable(false);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        new DashboardLayout();
    }
}

class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(String img, LayoutManager layoutManager) {
        this(new ImageIcon(img).getImage(),layoutManager);
    }

    public ImagePanel(Image img, LayoutManager layoutManager) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setLayout(layoutManager);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}

