package de.hardtthelen

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.text.DateFormat

/**
 * Main program file
 */

fun main(args: Array<String>) {
    val br = Files.newBufferedReader(FileSystems.getDefault().getPath("events.yaml"))
    println(br.readLine())
    val events: EventsDto = loadFromFile(FileSystems.getDefault().getPath("events.yaml"))

    println(events.events.size)
}

fun loadFromFile(path: Path): EventsDto {
    val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
    mapper.registerModule(KotlinModule()) // Enable Kotlin support
    mapper.dateFormat = DateFormat.getDateInstance()

    return Files.newBufferedReader(path).use {
        mapper.readValue(it, EventsDto::class.java)
    }
}