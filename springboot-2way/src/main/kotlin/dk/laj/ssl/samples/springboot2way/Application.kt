package dk.laj.ssl.samples.springboot2way

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Springboot2wayApplication

fun main(args: Array<String>) {
    runApplication<Springboot2wayApplication>(*args)
}
