package groovy.blog

import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.annotation.Nullable
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@CompileStatic
@JdbcRepository(dialect = Dialect.H2)
abstract class TodoRepository implements CrudRepository<@Valid Todo, @NotNull Long> {

    abstract Todo findByTitleAndDueOrDueIsNull(@NotNull @NotBlank String title, @Nullable LocalDate due)

    @Query('SELECT * FROM todo WHERE completed <= due')
    abstract List<Todo> findCompletedOnSchedule()

    Todo findByKey(@Valid @NotNull TodoKey key) {
        findByTitleAndDueOrDueIsNull(key.title, key.due)
    }
}
