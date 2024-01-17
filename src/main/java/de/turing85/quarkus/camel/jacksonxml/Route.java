package de.turing85.quarkus.camel.jacksonxml;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import de.turing85.quarkus.camel.jacksonxml.dto.Foo;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.component.platform.http.spi.Method;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.platformHttp;

public class Route extends RouteBuilder {

  public static final String HTTP_ENDPOINT = "/foo";
  public static final String ROUTE_ID = "http-foo";

  @Override
  public void configure() {
    // @formatter:off
    from(
        platformHttp(HTTP_ENDPOINT)
            .httpMethodRestrict(Method.POST.name()))
        .routeId(ROUTE_ID)
        .log(LoggingLevel.DEBUG,"Body, raw: ${body}")
        .unmarshal(new JacksonXMLDataFormat(Foo.class))
        .log(LoggingLevel.DEBUG, "body, unmarshalled: ${body}")
        .marshal().jacksonXml(true)
        .log(LoggingLevel.DEBUG,"body, marshalled: ${body}")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.Status.OK.getStatusCode()))
        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_XML));
    // @formatter:on
  }
}
