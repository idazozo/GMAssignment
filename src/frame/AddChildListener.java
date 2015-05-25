package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 23/5/15.
 */
public class AddChildListener implements ActionListener {

    private ShumpiFrame parent;

    public AddChildListener(ShumpiFrame parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.addChild(new ShumpiFrame(parent.getNewChildName()));
    }
}
