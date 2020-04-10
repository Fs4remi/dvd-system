import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ButtonBar extends JPanel {
    private JButton edit;
    private JButton add;
    private JButton remove;
    private int buttWidth;
    private int buttHeight;
    private writingFont myFont;
    private Dimension dim;

    private componentListener listener;

    public ButtonBar() {
        super();
        myFont = new writingFont(20);

        buttWidth = 200;
        buttHeight = 50;
        dim = new Dimension(buttWidth, buttHeight);
        setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        setBackground(new Color(248, 73, 169, 25));
        setPreferredSize(new Dimension(700, 50));

        ImageIcon sodaPic = new ImageIcon("images/butt.png");
        edit = new JButton("Update", sodaPic);
        edit.setPreferredSize(dim);
        edit.setBackground(new Color(181, 230, 29, 255));
        edit.setFont(myFont);

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.informationEmitted("Add/Modify");
            }

        });

        ImageIcon glassesPic = new ImageIcon("images/butto.png");
        add = new JButton("Add", glassesPic);
        add.setPreferredSize(dim);
        add.setBackground(new Color(180,68,227, 255));
        add.setFont(myFont);
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.informationEmitted("Add/Modify");
            }
        });

        ImageIcon cutPic = new ImageIcon("images/but.png");
        remove = new JButton("Delete", cutPic);
        remove.setPreferredSize(dim);
        remove.setBackground(new Color(255,242,0, 255));
        remove.setFont(myFont);
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.informationEmitted("Remove");
            }
        });

		add(edit);
		add(add);
		add(remove);
    }

    public void setListener(componentListener incominglistener){
        this.listener = incominglistener;
    }

}