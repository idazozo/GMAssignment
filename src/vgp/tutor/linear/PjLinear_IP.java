package vgp.tutor.linear;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jv.object.PsDebug;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;

/**
 * Info panel for simple interactive linear algebra.
 *
 * @author		Konrad Polthier
 * @version		04.03.01, 1.10 revised (kp) Package moved to vgp.tutor.linear from dev.app.linear.<br>
 *					22.10.00, 1.00 created (kp)
 */
public class PjLinear_IP extends PjProject_IP implements ActionListener, ItemListener {
    protected	PjLinear				m_pjLinear;
    protected	CheckboxGroup		m_cbg;
    protected	Checkbox []			m_cOperation;
    protected	String []			m_sOperation = { "Add", "Sub", "Cross"};
    protected	Button				m_bReset;

    public PjLinear_IP() {
        super();

        if (getClass() == PjLinear_IP.class)
            init();
    }
    public void init() {
        super.init();
        addTitle("Select Operation");
        addLine(1);

        Panel pStyle = new Panel();
        pStyle.setLayout(new GridLayout(1,3));
        add(pStyle);
        {
            m_cbg = new CheckboxGroup();
            m_cOperation = new Checkbox[m_sOperation.length];
            for (int i=0; i<m_sOperation.length; i++) {
                m_cOperation[i] = new Checkbox(m_sOperation[i], m_cbg, i==0);
                m_cOperation[i].addItemListener(this);
                pStyle.add(m_cOperation[i]);
            }
        }

        addLine(1);

        // buttons at bottom
        Panel m_pBottomButtons = new Panel();
        m_pBottomButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        m_bReset = new Button("Reset");
        m_bReset.addActionListener(this);
        m_pBottomButtons.add(m_bReset);

        add(m_pBottomButtons);

    }
    /**
     * Set parent of panel which supplies the data inspected by the panel.
     */
    public void setParent(PsUpdateIf parent) {
        super.setParent(parent);
        m_pjLinear = (PjLinear) parent;
    }
    /**
     * Update the panel whenever the parent has changed somewhere else.
     * Method is invoked from the parent or its superclasses.
     */
    public boolean update(Object event) {
        if (PsDebug.NOTIFY) PsDebug.notify("isShowing = "+isShowing());
        if (m_pjLinear == event) {
            m_cbg.setSelectedCheckbox(m_cOperation[m_pjLinear.getMode()]);
            return true;
        }
        return super.update(event);
    }
    /**
     * Handle action events invoked from buttons, menu items, text fields.
     */
    public void actionPerformed(ActionEvent event) {
        if (m_pjLinear==null)
            return;
        Object source = event.getSource();
        if (source == m_bReset) {
            m_pjLinear.init();
            m_pjLinear.m_vectors.update(m_pjLinear.m_vectors);
            update(m_pjLinear);
        }
    }
    public void itemStateChanged(ItemEvent event) {
        if (PsDebug.NOTIFY) PsDebug.notify("entered");
        if (m_cbg == null)
            return;
        Object source = event.getSource();
        for (int i=0; i<m_cOperation.length; i++) {
            if (source == m_cOperation[i]) {
                m_pjLinear.setMode(i);
            }
        }
    }
}