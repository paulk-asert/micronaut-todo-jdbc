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
        client.create(new Todo('Create Todo class', null, LocalDate.of(2023, 9, 1), null))
        client.create(new Todo('Create TodoKey class', null, LocalDate.of(2023, 9, 1), null))
        client.create(new Todo('Create TodoStats class', null, LocalDate.of(2023, 9, 1), null))
        client.create(new Todo('Create repo classes', null, LocalDate.of(2023, 9, 2), null))
        client.create(new Todo('Declarative client example', null, LocalDate.of(2023, 9, 2), null))
        client.create(new Todo('Create test classes', null, LocalDate.of(2023, 9, 2), null))

        then:
        GQ {
            from todo in client.list()
            groupby todo.due
            orderby todo.due
            select todo.due, list(todo.title).toSorted()
        }.toString() == '''
        +------------+------------------------------------------------------------------------+
        | due        | this.list(todo.title).toSorted()                                       |
        +------------+------------------------------------------------------------------------+
        | 2023-09-01 | [Create Todo class, Create TodoKey class, Create TodoStats class]      |
        | 2023-09-02 | [Create repo classes, Create test classes, Declarative client example] |
        +------------+------------------------------------------------------------------------+
        '''.stripIndent()
    }

}
