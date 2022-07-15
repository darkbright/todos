package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

import java.util.Collections;
import java.util.List;

@RestController
public class TodoRestOontroller {

    private final TodoFinder finder;

    public TodoRestOontroller(TodoFinder finder) {
        this.finder = finder;
    }

    @RequestMapping("/api/todos")
    public List<Todo> list() {
        return finder.getAll();
    }
}
