package de.turing85.quarkus.camel.jacksonxml;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import static org.hamcrest.Matchers.is;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;

@QuarkusTest
@DisplayName("Route HTTP Test")
class RouteTest {
  @Test
  void postFoo() {
    final String expected = """
        <Foo>
          <bar>bar</bar>
          <baz>1</baz>
        </Foo>""";
    // @formatter:off
    RestAssured
        .given()
            .contentType(MediaType.APPLICATION_XML)
            .body(expected)
        .when().post(Route.HTTP_ENDPOINT)
        .then()
            .statusCode(is(Response.Status.OK.getStatusCode()))
            .contentType(is(MediaType.APPLICATION_XML))
            .body(isSimilarTo(expected)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .ignoreElementContentWhitespace());
    // @formatter:on
  }
}
