package dk.laj.ssl.samples.springboot2way

import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts
import org.apache.http.ssl.TrustStrategy
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.cert.X509Certificate

@Configuration
class RestTemplates {

    fun get0way(): RestTemplate {
        return RestTemplate()
    }

    fun get1way(): RestTemplate {
        val acceptingTrustStrategy = TrustStrategy { _: Array<X509Certificate?>?, _: String? -> true }
        val csf = SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build())
        val httpClient = HttpClients.custom().setSSLSocketFactory(csf).build()
        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        return RestTemplate(requestFactory)
    }
}
