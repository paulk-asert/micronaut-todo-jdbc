package groovy.blog

import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import java.time.LocalDate
import spock.lang.Specification

@MicronautTest
class TodoClientSpec extends Specification {

    @Inject @Client TodoClient client

    void 'test interaction using declarative client'() {
        when:
        var day1 = LocalDate.of(2023, 9, 1)
        var day2 = LocalDate.of(2023, 9, 2)
        client.create(new Todo('Create Todo class', null, day1, null))
        client.create(new Todo('Create TodoKey class', null, day1, null))
        client.create(new Todo('Create TodoStats class', null, day1, null))
        client.create(new Todo('Create repo classes', null, day2, null))
        client.create(new Todo('Declarative client example', null, day2, null))
        client.create(new Todo('Create test classes', null, day2, null))

        then:
        GQ {
            from todo in client.list()
            groupby todo.due
            orderby todo.due
            select todo.due, list(todo.title) as todos_due
        }.toString() == '''
        +------------+------------------------------------------------------------------------+
        | due        | todos_due                                                              |
        +------------+------------------------------------------------------------------------+
        | 2023-09-01 | [Create Todo class, Create TodoKey class, Create TodoStats class]      |
        | 2023-09-02 | [Create repo classes, Declarative client example, Create test classes] |
        +------------+------------------------------------------------------------------------+
        '''.stripIndent()
    }

}
