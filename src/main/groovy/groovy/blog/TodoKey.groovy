package groovy.blog

import groovy.transform.Canonical
import io.micronaut.core.annotation.*
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

import java.time.LocalDate

@Serdeable
@Canonical
class TodoKey {
    @NonNull
    @NotBlank
    String title

    @Nullable
    LocalDate due = null
}
