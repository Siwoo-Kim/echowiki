package org.echowiki.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "org.echowiki.core")
@PropertySource("classpath:application.properties")
public class CoreConfiguration {

}
