package groovy.blog

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

import java.time.LocalDate
import spock.lang.Specification

@MicronautTest(startApplication = false)
class TodoValidationSpec extends Specification {

    @Inject TodoRepository repo
    @Inject TodoService service
    @Inject Validator validator
    private static final LocalDate ANY_DATE = LocalDate.now()
    private static final String ANY_DESC = 'Some description'

    void 'repo operations are validated'() {
        when: 'The Todo has a null title'
        repo.save(new Todo(null, ANY_DESC, ANY_DATE, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The Todo has a blank title'
        repo.save(new Todo('', ANY_DESC, ANY_DATE, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The TodoKey has a null title'
        repo.findByKey(new TodoKey(null, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The TodoKey has a blank title'
        repo.findByKey(new TodoKey('', ANY_DATE))
        then:
        thrown(ConstraintViolationException)
    }

    void 'service operations are validated'() {
        when: 'The Todo has a null title'
        service.create(new Todo(null, ANY_DESC, ANY_DATE, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The Todo has a blank title'
        service.create(new Todo('  ', ANY_DESC, ANY_DATE, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The TodoKey has a null title'
        service.find(new TodoKey(null, ANY_DATE))
        then:
        thrown(ConstraintViolationException)

        when: 'The TodoKey has a blank title'
        service.find(new TodoKey('', ANY_DATE))
        then:
        thrown(ConstraintViolationException)
    }
    void 'title must not be blank or null for Todo or TodoKey'() {
        when:
        var todo = new Todo(null, ANY_DESC, ANY_DATE, ANY_DATE)
        then:
        validator.validate(todo)

        when:
        todo = new Todo('', ANY_DESC, ANY_DATE, ANY_DATE)
        then:
        validator.validate(todo)

        when:
        todo = new TodoKey(null, ANY_DATE)
        then:
        validator.validate(todo)

        when:
        todo = new TodoKey('', ANY_DATE)
        then:
        validator.validate(todo)
    }
}
