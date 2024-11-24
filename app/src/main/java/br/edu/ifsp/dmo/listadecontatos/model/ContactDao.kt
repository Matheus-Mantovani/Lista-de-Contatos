package br.edu.ifsp.dmo.listadecontatos.model

import java.util.LinkedList
import java.util.stream.Collector
import java.util.stream.Collectors

object ContactDao {

    private val dataset = LinkedList<Contact>()

    fun insert(contact: Contact) {
        dataset.add(Contact(contact.name, contact.phone))
    }

    fun findAll(): List<Contact> {
        //return ArrayList(dataset).stream()
        //    .sorted { c1, c2 -> c1.name.compareTo(c2.name, true) }
        //    .collect(Collectors.toList())

        return ArrayList(dataset).sortedBy{it.name.lowercase()}
    }
}