package todolist.service;

import todolist.model.Task;
import todolist.model.ListGroup;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceService {
    private static final String FILE = "todolist_state.bin";

    public static void save(List<ListGroup> groups, List<Task> tasks) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(groups);
            out.writeObject(tasks);
        }
    }

    @SuppressWarnings("unchecked")
    public static State load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            List<ListGroup> groups = (List<ListGroup>) in.readObject();
            List<Task> tasks = (List<Task>) in.readObject();
            return new State(groups, tasks);
        } catch (Exception e) {
            return new State(new ArrayList<>(), new ArrayList<>());
        }
    }

    public static class State {
        public final List<ListGroup> groups;
        public final List<Task> tasks;
        public State(List<ListGroup> g, List<Task> t) { groups = g; tasks = t; }
    }
}