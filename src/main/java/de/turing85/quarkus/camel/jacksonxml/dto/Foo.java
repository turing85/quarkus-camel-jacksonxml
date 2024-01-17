package de.turing85.quarkus.camel.jacksonxml.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class Foo {
  String bar;
  int baz;
}
