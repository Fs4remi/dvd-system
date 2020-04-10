import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class RightPanel extends JPanel {
    private JLabel totalTime;
    private Dimension boxSize;
    private JPanel box;
    private JButton sortButt;
    private JLabel icon;
    private String content;

    private componentListener listener;

    RightPanel(int time) {
        super();
        setBackground(new Color(255, 127, 39, 255));
        setPreferredSize(new Dimension(100, 400));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 40));

        boxSize = new Dimension(95, 70);
        box = new JPanel();
        box.setPreferredSize(boxSize);
        box.setBackground(new Color(0, 204, 204, 255));

        minTohr(time);

        totalTime = new JLabel(content);
        totalTime.setPreferredSize(boxSize);
        totalTime.setFont(new writingFont(15));

        box.add(totalTime);

        ImageIcon image = new ImageIcon("images/butthutt.png");
        icon = new JLabel(image);

        sortButt = new JButton(image);
        sortButt.setPreferredSize(new Dimension(30,30));

        sortButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               listener.informationEmitted("Rates");
            }
        });

        add(box);
        add(sortButt);
    }

    public void setListener(componentListener incominglistener){
        this.listener = incominglistener;
    }

    public void updateTime(int time){
        minTohr(time);
        totalTime.setText(content);
        
    }

    private void minTohr(int time){
        //instead of min show 3 hours and 30 mins
        int hours = time/60;
        int minutes = time - (hours*60);

        if(hours > 0 && minutes > 0){ //4 hr 8 mins
            content = Integer.toString(hours) + "hrs " + Integer.toString(minutes) + "mins";
        }
        else if (hours <= 0 && minutes > 0){ // 20 min
            content = Integer.toString(minutes) + "mins";
        }
        else if(hours > 0 && minutes <= 0){ // exactly 1 hour
            content = Integer.toString(hours) + "hrs";
        }
        else{
            content = "0hrs";
        }
    }

}