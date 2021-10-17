package dk.laj.ssl.samples.springboot1way

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Springboot1wayApplication

fun main(args: Array<String>) {
    runApplication<Springboot1wayApplication>(*args)
}
