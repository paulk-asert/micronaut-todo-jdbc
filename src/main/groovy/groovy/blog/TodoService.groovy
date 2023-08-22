package groovy.blog

import groovy.transform.CompileStatic
import jakarta.inject.Inject
import java.time.LocalDate

@CompileStatic
class TodoService {
    @Inject TodoRepository repo

    List<Todo> findAll() {
        repo.findAll()
    }

    Todo create(Todo todo) {
        repo.save(todo)
    }

    Todo find(TodoKey key) {
        repo.findByTitleAndDueOrDueIsNull(key.title, key.due)
    }

    TodoStats stats() {
        List<Todo> all = repo.findAll()
        int total = all.size()
        int completed = all.count(0,t -> t.done)
        int totalScheduled = all.count(0, t -> t.scheduled)
        int completedOnSchedule = repo.findCompletedOnSchedule().size()
        new TodoStats(total, completed, totalScheduled, completedOnSchedule)
    }

    Todo delete(TodoKey key) {
        var todo = find(key)
        repo.delete(todo)
        todo
    }

    Todo reschedule(TodoKey key, LocalDate newDate) {
        def todo = find(key).tap { due = newDate }
        repo.update(todo)
    }

    Todo unschedule(TodoKey key) {
        def todo = find(key).tap { due = null }
        repo.update(todo)
    }

    Todo complete(TodoKey key) {
        var todo = find(key).tap { completed = LocalDate.now() }
        repo.update(todo)
    }
}
