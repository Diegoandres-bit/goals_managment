package com.example.goals_management.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "api.user-management")
@Data
public class UserManagementApiProperties {
    private String baseUrl;
}
