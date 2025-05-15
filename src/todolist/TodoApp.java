package todolist;

import todolist.controller.MainController;
import javax.swing.*;

public class TodoApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainController::new);
    }
}