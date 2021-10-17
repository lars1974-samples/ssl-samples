package dk.laj.ssl.samples.springboot0way

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
        return "Hello from 0 way"
    }

    @RequestMapping(value = ["/call1way"], produces = ["text/plain"])
    fun call1way(): String {
        val answer = templates.get1Way().getForEntity(URI(env.getProperty("dependencies.service1way","")), String::class.java).body
        return "0 way got this message from 1 way:    $answer"
    }

    @RequestMapping(value = ["/call2way"], produces = ["text/plain"])
    fun call2way(): String {
        val answer = templates.get2way().getForEntity(URI(env.getProperty("dependencies.service2way","")), String::class.java).body
        return "0 way got this message from 2 way:    $answer"
    }
}
