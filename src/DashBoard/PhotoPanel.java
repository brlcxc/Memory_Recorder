package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Map;
import java.util.Objects;

public class PhotoPanel extends JPanel {
    private final JPanel photoPanel =new JPanel();
    private final JLabel picture = new JLabel();
    GridBagConstraints gbc = new GridBagConstraints();
    public PhotoPanel(){

        setPhotoPanel ();
    }
    public void setPhotoPanel (){
        photoPanel.setPreferredSize(new Dimension(150,280));
        photoPanel.setLayout(new GridBagLayout());

        gbc.insets =new Insets(2,5,2,4);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=3;
        gbc.gridwidth =2;
        gbc.fill = GridBagConstraints.VERTICAL;
        photoPanel.add(picture(), gbc);
        gbc.gridy=4;
        gbc.gridheight=1;
        gbc.gridwidth =5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        photoPanel.add(uploadButton(), gbc);
        gbc.gridx=0;
        gbc.gridy=5;
        photoPanel.add(deleteButton(), gbc);

        photoPanel.setBackground(Colors.pastelPurple);
    }

    public JPanel getPhotoPanel(){
        return photoPanel;
    }

    private JButton uploadButton (){
//        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("upload-picture.png")));
        ImageIcon image = new ImageIcon("src/Defaults/IconImages/upload-picture.png");

        JButton uploadButton = new JButton (image(image,15,15));
        uploadButton.setPreferredSize(new Dimension(100,25));
        uploadButton.setBackground(Colors.pastelPurple);
        uploadButton.setForeground(Color.BLACK);
        uploadButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        uploadButton.setBorder(BorderFactory.createEmptyBorder());
        uploadButton.setFocusable(false);
        uploadButton.setText("Upload picture");
        mouseListener(uploadButton);
        uploadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                if (result==JFileChooser.APPROVE_OPTION){
                    File selectedFile = file.getSelectedFile();
                    String path =selectedFile.getAbsolutePath();
                    ImageIcon image = new ImageIcon(path);
                    picture.setIcon(image(image,150,150));
                }
            }
        });

        return uploadButton;
    }

    private JButton deleteButton(){
//        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("delete.png")));
        ImageIcon image = new ImageIcon("src/Defaults/IconImages/delete.png");

        JButton deleteButton = new JButton (image(image,15,15));
        deleteButton.setPreferredSize(new Dimension(100,25));
        deleteButton.setBackground(Colors.pastelPurple);
        deleteButton.setForeground(Color.black);
        deleteButton.setBorder(BorderFactory.createEmptyBorder());
        deleteButton.setFocusable(false);
        deleteButton.setText("Delete picture");
        deleteButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        mouseListener(deleteButton);
        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete your profile picture","Sure?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
           if(result == JOptionPane.YES_OPTION) {
               gbc.gridx=0;
               gbc.gridy=0;
               gbc.gridheight=3;
               gbc.gridwidth =2;
               gbc.fill = GridBagConstraints.VERTICAL;
               photoPanel.add(picture(), gbc);
           }
        });
        return deleteButton;
    }

    private void mouseListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            Font original = null;
            @Override
            public void mouseEntered(MouseEvent e) {
                original = e.getComponent().getFont();
                Map attributes = original. getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                e.getComponent().setFont(original.deriveFont(attributes));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                Map attributes = original. getAttributes();
                attributes.put(TextAttribute.UNDERLINE, null);
                e.getComponent().setFont(original.deriveFont(attributes));
            }
        });
    }


    private JLabel picture (){
        ImageIcon image = new ImageIcon("src/Defaults/IconImages/profile.png");

        picture.setHorizontalAlignment(SwingConstants.CENTER);
        picture.setIcon(image (image, 150,150));
        picture.setPreferredSize( new Dimension(150, 150));
        picture.setBorder(BorderFactory.createLineBorder(Colors.cream));
        return picture;
    }

    private ImageIcon image(ImageIcon image, Integer with, Integer height){
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(with, height, Image.SCALE_SMOOTH);
        return new ImageIcon (newImg);
    }

}
