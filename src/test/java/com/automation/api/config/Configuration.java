package com.automation.api.config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/test/resources/properties/test.properties")
public interface Configuration extends Config {
    @Key("baseURI")
    String baseURI();

    @Key("basePath")
    String basePath();
}
