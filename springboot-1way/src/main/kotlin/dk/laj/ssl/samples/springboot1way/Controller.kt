package dk.laj.ssl.samples.springboot1way

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class Controller(
    @Autowired val templates: RestTemplates,
    @Autowired val env: Environment
) {

    @RequestMapping(value = ["/hello"], produces = ["text/plain"])
    fun hello(): String {
        return "Hello from 1 way"
    }

    @RequestMapping(value = ["/call0way"], produces = ["text/plain"])
    fun call0way(): String {
        val answer = templates.get0way().getForEntity(URI(env.getProperty("dependencies.service0way")), String::class.java).body
        return "1 way got this message from 0 way:   $answer"
    }

    @RequestMapping(value = ["/call2way"], produces = ["text/plain"])
    fun call2way(): String {
        val answer = templates.get2way().getForEntity(URI(env.getProperty("dependencies.service2way")), String::class.java).body
        return "1 way got this message from 2 way:    $answer"
    }

}
