package todolist.controller;

import todolist.model.Task;
import todolist.model.ListGroup;
import todolist.service.PersistenceService;
import todolist.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainController {
    private List<ListGroup> groups;
    private List<Task> tasks;
    private MainView view;

    public MainController() {
        PersistenceService.State state = PersistenceService.load();
        this.groups = state.groups;
        this.tasks = state.tasks;
        if (groups.isEmpty()) groups.add(new ListGroup("Default"));
        view = new MainView(groups, tasks);
        attachHandlers();
        view.setVisible(true);
    }

    private void attachHandlers() {
        view.setAddAction(e -> onAdd());
        view.setRemoveAction(e -> onRemove());
        view.setCompleteAction(e -> onToggle());
    }

    private void onAdd() {
        String title = JOptionPane.showInputDialog("Task title:");
        if (title == null || title.isBlank()) return;
        LocalDate due = LocalDate.now().plusDays(1);
        Task t = new Task(title, "", due, view.getSelectedGroup().getId());
        tasks.add(t);
        refresh();
    }

    private void onRemove() {
        Task sel = view.getSelectedTask();
        tasks.remove(sel);
        refresh();
    }

    private void onToggle() {
        Task sel = view.getSelectedTask();
        sel.setCompleted(!sel.isCompleted());
        refresh();
    }

    private void refresh() {
        view.updateTaskList(tasks.stream()
                .filter(t -> t.getGroupId().equals(view.getSelectedGroup().getId()))
                .sorted((a,b) -> a.getDueDate().compareTo(b.getDueDate()))
                .toList());
        try { PersistenceService.save(groups, tasks); }
        catch (IOException ex) { ex.printStackTrace(); }
    }
}