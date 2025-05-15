package todolist.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String notes;
    private LocalDate dueDate;
    private boolean completed;
    private String groupId;

    public Task(String title, String notes, LocalDate dueDate, String groupId) {
        this.title = title;
        this.notes = notes;
        this.dueDate = dueDate;
        this.groupId = groupId;
        this.completed = false;
    }

    // getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
}
