package de.sambalmueslie.sample.item


import de.sambalmueslie.sample.item.api.Item
import de.sambalmueslie.sample.item.api.ItemChangeRequest
import de.sambalmueslie.sample.item.db.ItemData
import de.sambalmueslie.sample.item.db.ItemRepository
import de.sambalmueslie.sample.item.error.InvalidItemRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class ItemService(private val repository: ItemRepository) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ItemService::class.java)
    }

    fun create(request: ItemChangeRequest): Mono<Item> {
        validate(request)
        return repository.save(ItemData.create(request)).map { it.convert() }
    }


    fun get(id: Long): Mono<Item> {
        return repository.findById(id).map { it.convert() }
    }

    fun getAll(): Flux<Item> {
        return repository.findAll().map { it.convert() }
    }

    fun update(id: Long, request: ItemChangeRequest): Mono<Item> {
        validate(request)
        return repository.findById(id).flatMap { data ->
            repository.update(data.update(request)).map { it.convert() }
        }
    }

    fun delete(id: Long): Mono<Long> {
        return repository.deleteById(id)
    }

    private fun validate(request: ItemChangeRequest) {
        if (request.name.isBlank()) throw InvalidItemRequestException("Invalid request name '${request.name}' cannot be blank")
    }
}
