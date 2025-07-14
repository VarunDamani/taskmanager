package com.worldline.taskmanager.integration;

import com.worldline.taskmanager.integration.config.IntegrationTestBaseConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "developer", roles = {"TASK_GROUP_EDITOR"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskGroupIntegrationTest extends IntegrationTestBaseConfig {

    private static final String BASE_URL = "/api/v1/task-group";
    private static final long TEST_GROUP_ID = 3L;
    private static final long TEST_TASK_ID = 5L;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Task Group Retrieval Tests")
    class TaskGroupRetrievalTests {

        @Test
        @Order(1)
        @DisplayName("Should retrieve all task groups initially")
        void shouldGetAllTaskGroupsInitially() throws Exception {
            mockMvc.perform(getWithAuth(HttpMethod.GET, BASE_URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(getFileContent("test-data/response/get_all_task_groups_1.json")));
        }
    }

    @Nested
    @DisplayName("Task Group Lifecycle Tests")
    class TaskGroupLifecycleTests {

        @Test
        @Order(2)
        @DisplayName("Should perform complete task group lifecycle including create, add task, update, delete task and delete group")
        void shouldPerformCompleteTaskGroupLifecycle() throws Exception {
            createNewTaskGroup();
            addNewTask();
            updateTask();
            deleteTask();
            deleteTaskGroup();
        }

        private void createNewTaskGroup() throws Exception {
            mockMvc.perform(requestWithBody(HttpMethod.POST, BASE_URL,
                            getFileContent("test-data/request/create_task_group_request.json")))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(getFileContent("test-data/response/create_task_group_response.json")));
        }

        private void addNewTask() throws Exception {
            mockMvc.perform(requestWithBody(HttpMethod.POST, taskGroupTasksUrl(TEST_GROUP_ID),
                            getFileContent("test-data/request/create_task_request.json")))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(getFileContent("test-data/response/create_task_response.json")));
        }

        private void updateTask() throws Exception {
            mockMvc.perform(requestWithBody(HttpMethod.PUT, taskUrl(TEST_GROUP_ID, TEST_TASK_ID),
                            getFileContent("test-data/request/update_task_request.json")))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(getFileContent("test-data/response/update_task_response.json")));
        }

        private void deleteTask() throws Exception {
            mockMvc.perform(getWithAuth(HttpMethod.DELETE, taskUrl(TEST_GROUP_ID, TEST_TASK_ID)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(getFileContent("test-data/response/after_delete_task_response.json")));
        }

        private void deleteTaskGroup() throws Exception {
            mockMvc.perform(getWithAuth(HttpMethod.DELETE, taskGroupUrl(TEST_GROUP_ID)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("Task Group Failure Tests")
    class TaskGroupFailureCases {

        @Test
        @Order(4)
        @DisplayName("Should return BAD_REQUEST error for invalid task group creation request")
        void shouldReturnBadRequestErrorForInvalidTaskGroupCreationRequest() throws Exception {
            mockMvc.perform(requestWithBody(HttpMethod.POST, BASE_URL, "{\"name\":\"\"}"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Order(4)
        @DisplayName("Should return NOT_FOUND error for invalid task creation request")
        void shouldReturnNotFoundErrorForInvalidTaskCreationRequest() throws Exception {
            final Long NON_EXISTING_TASK_GROUP_ID = 200L;
            mockMvc.perform(requestWithBody(HttpMethod.POST, taskGroupTasksUrl(NON_EXISTING_TASK_GROUP_ID),
                            getFileContent("test-data/request/create_task_request.json")))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @Order(5)
        @DisplayName("Should return BAD_REQUEST error for invalid task deletion request")
        void shouldReturnBadRequestForInvalidTaskDeletionRequest() throws Exception {
            final Long NON_EXISTING_TASK_ID = 200L;
            mockMvc.perform(getWithAuth(HttpMethod.DELETE, taskUrl(TEST_GROUP_ID, NON_EXISTING_TASK_ID)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        @DisplayName("Should return BAD_REQUEST error for invalid task group and task association")
        void shouldReturnBadRequestForInvalidTaskAndTaskGroupAssociation() throws Exception {
            final Long NON_ASSOCIATED_TASK_ID = 1L;
            mockMvc.perform(getWithAuth(HttpMethod.DELETE, taskUrl(TEST_GROUP_ID, NON_ASSOCIATED_TASK_ID)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    private String taskGroupUrl(Long taskGroupId) {
        return BASE_URL + "/" + taskGroupId;
    }

    private String taskGroupTasksUrl(Long taskGroupId) {
        return BASE_URL + "/" + taskGroupId + "/task";
    }

    private String taskUrl(Long taskGroupId, Long taskId) {
        return BASE_URL + "/" + taskGroupId + "/task/" + taskId;
    }
}