package groovy.blog

import io.micronaut.core.annotation.NonNull
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

@Client('/todo')
interface TodoClient {

    @Get('/')
    List<Todo> list()

    @Post('/')
    Todo create(@NonNull @NotNull @Valid @Body Todo todo)
}
