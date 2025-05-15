package todolist.view;

import todolist.model.Task;
import todolist.model.ListGroup;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {
    private JComboBox<ListGroup> groupCombo;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JButton addBtn, removeBtn, completeBtn;

    public MainView(List<ListGroup> groups, List<Task> tasks) {
        super("Java 24 Todo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initComponents(groups, tasks);
    }

    private void initComponents(List<ListGroup> groups, List<Task> tasks) {
        groupCombo = new JComboBox<>(groups.toArray(new ListGroup[0]));
        taskListModel = new DefaultListModel<>();
        tasks.forEach(taskListModel::addElement);
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskRenderer());

        addBtn = new JButton("Add Task");
        removeBtn = new JButton("Remove Task");
        completeBtn = new JButton("Toggle Complete");

        JPanel top = new JPanel();
        top.add(new JLabel("List: ")); top.add(groupCombo);
        top.add(addBtn); top.add(removeBtn); top.add(completeBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
    }

    public void setAddAction(ActionListener l) { addBtn.addActionListener(l); }
    public void setRemoveAction(ActionListener l) { removeBtn.addActionListener(l); }
    public void setCompleteAction(ActionListener l) { completeBtn.addActionListener(l); }
    public ListGroup getSelectedGroup() { return (ListGroup) groupCombo.getSelectedItem(); }
    public Task getSelectedTask() { return taskList.getSelectedValue(); }
    public void updateTaskList(List<Task> tasks) {
        taskListModel.clear(); tasks.forEach(taskListModel::addElement);
    }

    static class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int idx, boolean sel, boolean foc) {
            Task t = (Task) value;
            String txt = (t.isCompleted()? "[✔] ": "[ ] ") + t.getTitle() + " (Due: " + t.getDueDate() + ")";
            return super.getListCellRendererComponent(list, txt, idx, sel, foc);
        }
    }
}