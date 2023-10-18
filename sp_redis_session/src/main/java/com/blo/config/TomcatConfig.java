package com.blo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class TomcatConfig {
    @Value("${server.additionalPorts}")
        private String additionalPorts;

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        Connector[] connectors = this.connector();
        if (connectors != null && connectors.length > 0) {
            factory.addAdditionalTomcatConnectors(connectors);
        }
        return factory;
    }

    private  Connector[] connector(){
        if(StringUtils.isEmpty(this.additionalPorts)){
            return null;
        }
        String[] ports = this.additionalPorts.split(",");
        List<Connector> result = new ArrayList<>();
        for (String port : ports) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.parseInt(port));
            result.add(connector);
        }
        return result.toArray(new Connector[] {});
    }
}
