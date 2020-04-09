import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class RightPanel extends JPanel {
    private JLabel totalTime;
    private Dimension boxSize;
    private JPanel box;
    String content;

    private StringListener selectoinListener;
    
    RightPanel(int time){
        super();
        setBackground(new Color(255,127,39,255));
        setPreferredSize(new Dimension(100, 400));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        boxSize = new Dimension(95,100);
        box = new JPanel();
        box.setPreferredSize(boxSize);
        
        
        content = Integer.toString(time) + " mins";
        
        totalTime = new JLabel(content);
        totalTime.setPreferredSize(boxSize );
        totalTime.setFont(new writingFont(20));

        box.add(totalTime);
        add(box);
    }

    public void setSelectionListener(StringListener listener){
        selectoinListener = listener;
    }
}