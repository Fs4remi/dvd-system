import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LeftPanel extends JPanel  {
    private JList<String> list;
    private TitledBorder out;
    private JScrollPane listScroller;

    private componentListener listener;

    private Color theColor;
    LeftPanel(String[] names) {
        super();

        theColor = new Color(0, 162, 232, 225);

        setPreferredSize(new Dimension(160,400));
        setBackground(theColor);
     
        list = new JList<String>(names);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(10);
        list.setBackground(theColor);
        list.setFont(new writingFont(14));

        list.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Auto-generated method stub
                if (e.getValueIsAdjusting() == false) {

                    if (list.getSelectedIndex() == -1) {
                        listener.informationEmitted("nothing");
                        
                    } else {
                    //Selection, enable the fire button.
                        listener.informationEmitted(list.getSelectedValue().toString());
                    }
                }
            }
        });

        out = BorderFactory.createTitledBorder("Available movies");
        listScroller = new JScrollPane(list);
    
		listScroller.setBorder(out);
        listScroller.setPreferredSize(new Dimension(150, 365 ));
        
        add(listScroller);
    }

    public void setListener(componentListener incominglistener){
        this.listener = incominglistener;
    }

    public void updateList(String[] titles){
        list.setListData(titles);
        
    }
}