import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class CenterPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel lengthLabel;
    private JLabel rateLabel;
    private JLabel length;
    private JLabel title;
    private JLabel rate;
    private writingFont fatemFont = new writingFont(20);
    private ImageIcon icon;
    private JLabel picLabel;
    
    CenterPanel(){
        super();

        //setBackground(new Color(255, 0, 225, 255));
        setPreferredSize(new Dimension(375, 400));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        titleLabel = new JLabel("Name");
        lengthLabel = new JLabel("Duration");
        rateLabel = new JLabel("Rate");
        title = new JLabel("");
        length = new JLabel("");
        rate = new JLabel("");

        titleLabel.setFont(fatemFont);
        lengthLabel.setFont(fatemFont);
        rateLabel.setFont(fatemFont);
        length.setFont(fatemFont);
        title.setFont(fatemFont);
        rate.setFont(fatemFont);

        add(titleLabel);
        add(title);
        add(Box.createVerticalGlue());
        
        add(lengthLabel);
        add(length);
        add(Box.createVerticalGlue());

        add(rateLabel);
        add(rate);
        add(Box.createVerticalGlue());

        icon = new ImageIcon("images/image.png"); 
        picLabel = new JLabel(icon);
        add(picLabel);
    }

    public void showDetails(DVD movie){
        rate.setText(movie.getRating());
        title.setText(movie.getTitle());
        length.setText(String.valueOf(movie.getRunningTime()));
       
        revalidate();
    }
}