package de.sambalmueslie.sample.item


import de.sambalmueslie.sample.item.api.Item
import de.sambalmueslie.sample.item.api.ItemChangeRequest
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller("/api/item")
@Tag(name = "ITEM API")
class ItemController(private val service: ItemService) {

    @Post()
    fun create(@Body request: ItemChangeRequest): Mono<Item> = service.create(request)

    @Get("/{id}")
    fun get(@PathVariable id: Long): Mono<Item> = service.get(id)

    @Get()
    fun getAll(): Flux<Item> = service.getAll()

    @Put("/{id}")
    fun update(@PathVariable id: Long, @Body request: ItemChangeRequest): Mono<Item> = service.update(id, request)

    @Delete("/{id}")
    fun delete(@PathVariable id: Long): Mono<Long> = service.delete(id)

}
