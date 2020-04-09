import javax.swing.*;
import java.awt.*;

public class GUIMainFrame extends JFrame {
    
   
    public GUIMainFrame(int width, int height){
        super("Fatemeh's DVD collection");

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        
        setResizable(false);
         
		this.setBackground(new Color(248, 73, 169, 255));
		setSize(700,450);
		setLocationRelativeTo(null);
        //UIManager.put("OptionPane.minimumSize",new Dimension(550, 250));

    }
}