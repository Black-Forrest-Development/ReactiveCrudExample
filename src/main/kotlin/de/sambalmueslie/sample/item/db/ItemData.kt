package de.sambalmueslie.sample.item.db

import de.sambalmueslie.sample.item.api.Item
import de.sambalmueslie.sample.item.api.ItemChangeRequest
import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Item")
@Table(name = "item")
data class ItemData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column()
    var name: String
) {
    companion object {
        fun create(request: ItemChangeRequest) = ItemData(0, request.name)
    }

    fun convert() = Item(id, name)

    fun update(request: ItemChangeRequest): ItemData {
        name = request.name
        return this
    }
}

