package groovy.blog

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

import static io.micronaut.scheduling.TaskExecutors.BLOCKING

@ExecuteOn(BLOCKING)
@Controller('todo')
@CompileStatic
class TodoController {
    @Inject TodoService service

    @Inject ObjectMapper mapper

    @Get('/')
    Collection<Todo> list() {
        service.findAll()
    }

    @Post('/')
    @Status(HttpStatus.CREATED)
    Todo create(@NotNull @Valid @Body Todo todo) {
        service.create(todo)
    }

    @Post('/find')
    Todo find(@NotNull @Valid @Body TodoKey key) {
        service.find(key)
    }

    @Get('/stats')
    String statsAsString() {
        mapper.writeValueAsString(service.stats())
    }

    @Delete('/delete/{title}')
    Todo delete(@PathVariable String title) {
        delete(title, null)
    }

    @Delete('/delete/{title}/{due}')
    Todo delete(@PathVariable String title, @PathVariable LocalDate due) {
        service.delete(new TodoKey(title, due))
    }

    @Put('/reschedule/{newDate}')
    Todo reschedule(@NotNull @Valid @Body TodoKey key, @PathVariable LocalDate newDate) {
        service.reschedule(key, newDate)
    }

    @Put('/unschedule')
    Todo unschedule(@NotNull @Valid @Body TodoKey key) {
        service.unschedule(key)
    }

    @Put('/complete')
    Todo complete(@NotNull @Valid @Body TodoKey key) {
        service.complete(key)
    }
}
