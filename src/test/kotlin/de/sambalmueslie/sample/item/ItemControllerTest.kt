package de.sambalmueslie.sample.item

import de.sambalmueslie.sample.item.api.Item
import de.sambalmueslie.sample.item.api.ItemChangeRequest
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
internal class ItemControllerTest {
    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun simpleCrudTest() {
        // create
        var name = "Hello World"
        var item = client.toBlocking().retrieve(HttpRequest.POST("/api/item", ItemChangeRequest(name)), Item::class.java)
        assertNotNull(item)
        assertEquals(1, item.id)
        assertEquals(name, item.name)
        // read all
        val items = client.toBlocking().retrieve(HttpRequest.GET<String>("/api/item"), Argument.listOf(Item::class.java))
        assertEquals(1, items.size)
        item = items.firstOrNull()
        assertNotNull(item)
        assertEquals(1, item.id)
        assertEquals(name, item.name)
        // update
        name = "Hello World - update"
        item = client.toBlocking().retrieve(HttpRequest.PUT("/api/item/${item.id}", ItemChangeRequest(name)), Item::class.java)
        assertNotNull(item)
        assertEquals(1, item.id)
        assertEquals(name, item.name)
        // read single
        item = client.toBlocking().retrieve(HttpRequest.GET<String>("/api/item/${item.id}"), Item::class.java)
        assertNotNull(item)
        assertEquals(1, item.id)
        assertEquals(name, item.name)
        // delete
        val response: HttpResponse<Long> = client.toBlocking().exchange(HttpRequest.DELETE<String>("/api/item/${item.id}"))
        assertEquals(HttpStatus.NO_CONTENT, response.status)
    }
}
