package dk.laj.ssl.samples.springboot2way

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class Controller(
    @Autowired val env: Environment,
    @Autowired val templates: RestTemplates
) {

    @RequestMapping(value = ["/hello"], produces = ["text/plain"])
    fun hello(): String {
        return "Hello from 2 way"
    }

    @RequestMapping(value = ["/call0way"], produces = ["text/plain"])
    fun call0way(): String {
        val answer = templates.get0way().getForEntity(URI(env.getProperty("dependencies.service0way","")), String::class.java).body
        return "2 way got this message from 0 way:   $answer"
    }

    @RequestMapping(value = ["/call1way"], produces = ["text/plain"])
    fun call1way(): String {
        val answer = templates.get1way().getForEntity(URI(env.getProperty("dependencies.service1way","")), String::class.java).body
        return "2 way got this message from 1 way:   $answer"
    }


}
