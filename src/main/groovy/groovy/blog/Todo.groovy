package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

import java.time.LocalDate

@MappedEntity
@EqualsAndHashCode(excludes = 'id')
@Serdeable
@ToString(excludes = 'id', includeNames = true, ignoreNulls = true)
@JsonIgnoreProperties(['id', 'done', 'scheduled'])
class Todo {
    @GeneratedValue @Id Long id
    @NonNull @NotBlank final String title
    @Nullable final String description
    @Nullable LocalDate due = null
    @Nullable LocalDate completed

    Todo(@NonNull @NotBlank String title, @Nullable String description,
         @Nullable LocalDate due, @Nullable LocalDate completed) {
        this.title = title
        this.description = description
        this.due = due
        this.completed = completed
    }

    boolean isDone() {
        completed != null
    }

    boolean isScheduled() {
        due != null
    }
}
