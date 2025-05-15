import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.List;

// Model representing a Task
class Task {
private String description;
private LocalDate dueDate;
private String bin;

public Task(String description, LocalDate dueDate, String bin) {
this.description = description;
this.dueDate = dueDate;
this.bin = bin;
}

public String getDescription() { return description; }
public LocalDate getDueDate() { return dueDate; }
public String getBin() { return bin; }

public void setDescription(String description) { this.description = description; }
public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
public void setBin(String bin) { this.bin = bin; }
}

// Table model for tasks
class TaskTableModel extends AbstractTableModel {
private final String[] cols = {"Description", "Due Date", "Bin"};
private final List<Task> tasks = new ArrayList<>();
private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

@Override
public int getRowCount() { return tasks.size(); }

@Override
public int getColumnCount() { return cols.length; }

@Override
public String getColumnName(int col) { return cols[col]; }

@Override
public Object getValueAt(int row, int col) {
Task t = tasks.get(row);
return switch (col) {
case 0 -> t.getDescription();
case 1 -> t.getDueDate().format(fmt);
case 2 -> t.getBin();
default -> null;
};
}

public void addTask(Task t) {
tasks.add(t);
fireTableDataChanged();
}

public Task getTaskAt(int row) {
return tasks.get(row);
}

public List<Task> getTasks() {
return tasks;
}
}

// Main application
public class TodoListApp extends JFrame {
private TaskTableModel model;
private JTable table;
private TableRowSorter<TaskTableModel> sorter;
private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

public TodoListApp() {
super("Todo List");
setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(600, 400);
setLocationRelativeTo(null);
initUI();
}

private void initUI() {
model = new TaskTableModel();
table = new JTable(model);
sorter = new TableRowSorter<>(model);
table.setRowSorter(sorter);

JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
JTextField descField = new JTextField();
JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
JComboBox<String> binCombo = new JComboBox<>(new String[]{"Work", "Personal", "Shopping", "Other"});
JButton addBtn = new JButton("Add Task");

inputPanel.add(new JLabel("Description:"));
inputPanel.add(descField);
inputPanel.add(new JLabel("Due Date:"));
inputPanel.add(dateSpinner);
inputPanel.add(new JLabel("Bin:"));
inputPanel.add(binCombo);
inputPanel.add(addBtn);

addBtn.addActionListener((ActionEvent e) -> {
String desc = descField.getText().trim();
Date dateVal = (Date) dateSpinner.getValue();
LocalDate due = dateVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
String bin = (String) binCombo.getSelectedItem();
if (!desc.isEmpty()) {
Task t = new Task(desc, due, bin);
model.addTask(t);
descField.setText("");
sortAndHighlightToday();
}
});

add(new JScrollPane(table), BorderLayout.CENTER);
add(inputPanel, BorderLayout.SOUTH);

// initial sort
sortAndHighlightToday();
}

private void sortAndHighlightToday() {
sorter.setComparator(1, (s1, s2) -> {
LocalDate d1 = LocalDate.parse((String) s1, fmt);
LocalDate d2 = LocalDate.parse((String) s2, fmt);
LocalDate today = LocalDate.now();
if (d1.equals(today) && !d2.equals(today)) return -1;
if (d2.equals(today) && !d1.equals(today)) return 1;
return d1.compareTo(d2);
});
sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
model.fireTableDataChanged();
}

public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new TodoListApp().setVisible(true));
}
}
