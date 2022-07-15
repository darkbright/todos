package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
public class TodoRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TodoFinder finder;
    private final TodoEditor editor;

    public TodoRestController(TodoFinder finder, TodoEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }

    @GetMapping("/api/todos")
    public List<Todo> list() {
        return finder.getAll();
    }

    @PostMapping(path = "/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid TodoRestController.WriteTodoCommand command) {
        logger.debug("디버그: request payload: {}", command);

        editor.create(command.getTitle());
    }

    @PutMapping(path = "/api/todos/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid WriteTodoCommand command) {
        logger.debug("디버그: request update id: {}, command: {}", id, command);

        editor.update(id, command.getTitle(), command.isCompleted());
    }

    static class WriteTodoCommand {
        @NotBlank
        @Size(min = 4, max = 140)
        private String title;
        private boolean completed;

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return String.format("[title=%s, completed=%s]", title, completed);
        }
    }

}
