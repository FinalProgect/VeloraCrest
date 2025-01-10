package model;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ModifyJList {

    public void modifyJlist(JList jlist, JScrollPane scrollPane, JPanel panel) {
        this.panel = panel;
        this.scrollPane = scrollPane;
        this.list= jlist;

        modifyProcess();
    }

    public void modifyJlist(JList jlist,JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        this.list= jlist;

        modifyProcess();
    }

    private void modifyProcess() {
        if (panel != null) {
            panel.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        }
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        listRenderer.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        list.setCellRenderer(listRenderer);

    }
    private JList list;
    private JScrollPane scrollPane;
    private JPanel panel;
}
