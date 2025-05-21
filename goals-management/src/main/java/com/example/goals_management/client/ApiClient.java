package com.example.goals_management.client;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.goals_management.service.HttpService;
import com.example.goals_management.config.UserManagementApiProperties;

@Component
public class ApiClient {

    @Autowired
    private HttpService httpService;

    @Autowired
    private UserManagementApiProperties userManagementApiProperties;

    public boolean employeeExists(Long id, String authorization) {
        String url = userManagementApiProperties.getBaseUrl() + "/employee/exists/" + id;

        ResponseEntity<Boolean> response = httpService.get(
                url,
                Boolean.class,
                Map.of("Authorization", authorization)
        );

        return response.getBody();
    }
}
