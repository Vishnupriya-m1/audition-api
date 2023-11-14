package com.audition.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.model.AuditionPostRequest;
import com.audition.service.AuditionService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
class AuditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditionService service;

    @Test
    void noRequestParamsSuccess() throws Exception {
        when(service.getPosts(AuditionPostRequest.builder().build())).thenReturn(List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build()));
        mockMvc.perform(get("/posts")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void withUserIdRequestParamsSuccess() throws Exception {
        when(service.getPosts(AuditionPostRequest.builder().userId("1").build())).thenReturn(List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build()));
        mockMvc.perform(get("/posts").param("userId", "1")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void withTitleContainsRequestParamsSuccess() throws Exception {
        when(service.getPosts(AuditionPostRequest.builder().titleContains("Software").build())).thenReturn(List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build()));
        mockMvc.perform(get("/posts").queryParam("titleContains", "Software")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void withBodyContainsRequestParamsSuccess() throws Exception {
        when(service.getPosts(AuditionPostRequest.builder().bodyContains("Software").build())).thenReturn(List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build()));
        mockMvc.perform(get("/posts").queryParam("bodyContains", "Software")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void allRequestParamsSuccess() throws Exception {
        when(service.getPosts(
            AuditionPostRequest.builder().userId("1").titleContains("Software").bodyContains("distributed")
                .build())).thenReturn(
            List.of(
                AuditionPost.builder()
                    .userId(1)
                    .id(1)
                    .title("Software Architecture")
                    .body("Software Architecture in distributed systems").build()));
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("userId", "1");
        queryParams.add("titleContains", "Software");
        queryParams.add("bodyContains", "distributed");
        mockMvc.perform(get("/posts").queryParams(queryParams)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void withInvalidUserIdRequestParamsFailure() throws Exception {
        when(service.getPosts(AuditionPostRequest.builder().userId("someUserId").build())).thenReturn(List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build()));
        mockMvc.perform(get("/posts").param("userId", "someUserId")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Software Architecture")))
            .andExpect(jsonPath("$[0].body", is("Software Architecture in distributed systems")));
    }

    @Test
    void testGetPostById() throws Exception {
        when(service.getPostById("1")).thenReturn(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build());
        mockMvc.perform(get("/posts/1")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.userId", is(1)))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Software Architecture")))
            .andExpect(jsonPath("$.body", is("Software Architecture in distributed systems")));
    }

    @Test
    void testGetPostAndComments() throws Exception {
        when(service.getPostAndComments("1")).thenReturn(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").comments(List.of(
                    AuditionPostComment.builder().id(1).postId(1).email("test.1@test.com").name("amazing")
                        .body("amazing post").build())).build());
        mockMvc.perform(get("/posts/1/comments")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.userId", is(1)))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Software Architecture")))
            .andExpect(jsonPath("$.body", is("Software Architecture in distributed systems")))
            .andExpect(jsonPath("$.comments[0].id", is(1)));
    }

    @Test
    void testGetComments() throws Exception {
        when(service.getComments("1")).thenReturn(List.of(
            AuditionPostComment.builder().id(1).postId(1).email("test.1@test.com").name("amazing")
                .body("amazing post").build()));
        mockMvc.perform(get("/comments").param("postId", "1")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].postId", is(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].email", is("test.1@test.com")))
            .andExpect(jsonPath("$[0].name", is("amazing")))
            .andExpect(jsonPath("$[0].body", is("amazing post")));
    }

}
