package de.hardtthelen

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

/**
 * Main program file
 */

fun main(args: Array<String>) {
    val events: EventsDto = loadFromFile(FileSystems.getDefault().getPath("events.yaml"))

    println(events.events[0].startTime)
}

fun loadFromFile(path: Path): EventsDto {
    val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
    mapper.registerModule(KotlinModule()) // Enable Kotlin support

    return Files.newBufferedReader(path).use {
        mapper.readValue(it, EventsDto::class.java)
    }
}