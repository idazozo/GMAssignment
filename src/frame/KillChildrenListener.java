package frame;

import frame.ShumpiFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 23/5/15.
 */
public class KillChildrenListener implements ActionListener {
    private ShumpiFrame parent;

    public KillChildrenListener(ShumpiFrame parent) {
        this.parent = parent;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        parent.closeChildren();
    }
}
