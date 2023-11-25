import DashBoard.DashBoardPanel;
import Defaults.MenuButton;
import Defaults.Colors;
import Diary.DiaryPanel;
import ListToDo.ListToDoPanel;
import Notes.NotesPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    final int WINDOW_WIDTH = 750;
    final int WINDOW_HEIGHT = 600;
    private JMenuBar menuBar;
    private JPanel mainContentPanel;
    private JPanel sideContentPanel;
    private DashBoardPanel dashBoardPanel;
    private DiaryPanel diaryPanel;
    private ListToDoPanel listToDoPanel;
    private NotesPanel notesPanel;
    private MenuButton dashboardButton;
    private MenuButton diaryButton;
    private MenuButton listToDoButton;
    private MenuButton notesButton;
    private CardLayout mainContentLayout;
    private CardLayout sideContentLayout;
    public GUI() {
        //sets default width and height
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //sets title
        setTitle("Test");
        //sets close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //centers panel in window
        setLocationRelativeTo(null);
        setContentPane();

        setVisible(true);
        System.out.println(dashboardButton.getHeight() + " " + dashboardButton.getWidth());
    }
    private void setContentPane(){
        GridBagConstraints gbc = new GridBagConstraints();

        setLayout(new GridBagLayout());

        setMenuBar();
        setMainContentPanel();
        setSideContentPanel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.07;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(menuBar, gbc);

        gbc.gridy = -1;
        gbc.weightx = 0.20;
        gbc.weighty = 0.93;
        gbc.gridwidth = 1;
        add(sideContentPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        add(mainContentPanel, gbc);

    }

    private void setMenuBar(){
        GridBagConstraints gbc = new GridBagConstraints();

        menuBar = new JMenuBar();
        dashboardButton = new MenuButton("Dashboard");
        diaryButton = new MenuButton("Diary");
        listToDoButton = new MenuButton("List To-Do");
        notesButton = new MenuButton("Notes");

        dashboardButton.addActionListener(new DashboardButtonListener());
        diaryButton.addActionListener(new DiaryButtonListener());
        listToDoButton.addActionListener(new ListToDoButtonListener());
        notesButton.addActionListener(new NotesButtonListener());

        menuBar.setLayout(new GridBagLayout());
        menuBar.setBackground(Colors.cream);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        menuBar.add(dashboardButton, gbc);

        gbc.gridx = 1;
        menuBar.add(diaryButton, gbc);

        gbc.gridx = 2;
        menuBar.add(listToDoButton, gbc);

        gbc.gridx = 3;
        menuBar.add(notesButton, gbc);
    }
    private void setMainContentPanel(){
        mainContentPanel = new JPanel();

        dashBoardPanel = new DashBoardPanel();
        diaryPanel = new DiaryPanel();
        listToDoPanel = new ListToDoPanel();
        notesPanel = new NotesPanel();

        mainContentLayout = new CardLayout();
        mainContentPanel.setLayout(mainContentLayout);

        mainContentPanel.add(dashBoardPanel, "dashboard");
        mainContentPanel.add(diaryPanel, "diary");
        mainContentPanel.add(listToDoPanel, "list to do");
        mainContentPanel.add(notesPanel, "notes");

//        mainContentPanel.setMinimumSize(new Dimension(700, 50));
    }
    private void setSideContentPanel(){
        sideContentPanel = new JPanel();

        sideContentLayout = new CardLayout();
        sideContentPanel.setLayout(sideContentLayout);

        sideContentPanel.add(dashBoardPanel.getSidePanel(), "dashboard");
        sideContentPanel.add(diaryPanel.getSidePanel(), "diary");
        sideContentPanel.add(listToDoPanel.getSidePanel(), "list to do");
        sideContentPanel.add(notesPanel.getSidePanel(), "notes");
    }

    private class DashboardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sideContentLayout.show(sideContentPanel,"dashboard");
            mainContentLayout.show(mainContentPanel,"dashboard");
        }
    }
    private class DiaryButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sideContentLayout.show(sideContentPanel,"diary");
            mainContentLayout.show(mainContentPanel,"diary");
        }
    }
    private class ListToDoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sideContentLayout.show(sideContentPanel,"list to do");
            mainContentLayout.show(mainContentPanel,"list to do");
        }
    }
    //help
    private class NotesButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sideContentLayout.show(sideContentPanel,"notes");
            mainContentLayout.show(mainContentPanel,"notes");
        }
    }
    public static void main(String[] args) {
        new GUI();
    }
}

//use underline and color shift
//select pallet
//bring back button class but maybe have it within side option panel than outside
//colors class