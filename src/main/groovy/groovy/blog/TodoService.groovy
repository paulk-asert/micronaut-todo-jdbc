package groovy.blog

import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.LocalDate

@CompileStatic
@Singleton
class TodoService {
    @Inject TodoRepository repo

    Collection<Todo> findAll() {
        repo.findAll()
    }

    Todo create(Todo todo) {
        repo.save(todo)
    }

    Todo find(TodoKey key) {
        repo.findByTitleAndDueOrDueIsNull(key.title, key.due)
    }

    TodoStats stats() {
        Collection<Todo> all = repo.findAll()
        long total = all.size()
        long completed = all.count(0L,t -> t.done)
        long totalScheduled = all.count(0L, t -> t.scheduled)
        long completedOnSchedule = repo.findCompletedOnSchedule().size()
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
