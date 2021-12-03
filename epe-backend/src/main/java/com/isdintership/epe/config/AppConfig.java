package com.isdintership.epe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    @NotNull
    private String frontendUrl;
    @NotNull
    private String frontendHttpUrl;

    public String getFrontendUrl() {
        return frontendUrl;
    }

    public void setFrontendUrl(String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    public String getFrontendHttpUrl() {
        return frontendHttpUrl;
    }

    public void setFrontendHttpUrl(String frontendHttpUrl) {
        this.frontendHttpUrl = frontendHttpUrl;
    }
}