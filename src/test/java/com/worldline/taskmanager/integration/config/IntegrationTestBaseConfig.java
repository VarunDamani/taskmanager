package com.worldline.taskmanager.integration.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

public abstract class IntegrationTestBaseConfig {

    protected MockHttpServletRequestBuilder requestWithBody(HttpMethod httpMethod, String url, String content) {
        return request(httpMethod, url).contentType(MediaType.APPLICATION_JSON).content(content);
    }

    protected MockHttpServletRequestBuilder getWithAuth(HttpMethod method, String url) {
        return request(method, url);
    }

    protected String getFileContent(String resourceFileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + resourceFileName);

        return Files.readString(Path.of(file.getPath()));
    }
}
