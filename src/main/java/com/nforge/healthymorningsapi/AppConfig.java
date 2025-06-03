// Klasa AppConfig służy do konfiguracji Springa
package com.nforge.healthymorningsapi;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.nforge.healthymorningsapi") // Spring ma przeszukać wszystkie podpakiety pakietu głównego
public class AppConfig { }
