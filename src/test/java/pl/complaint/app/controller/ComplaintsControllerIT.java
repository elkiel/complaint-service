package pl.complaint.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.complaint.app.frontend.model.ComplaintCreateRequestRest;
import pl.complaint.app.frontend.model.ComplaintsCriteriaRest;
import pl.complaint.app.frontend.model.ComplaintsRequestRest;
import pl.complaint.app.frontend.model.MetaDataRest;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ComplaintsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @Sql(scripts = {
            "/complaints/sql/clean.sql",
            "/complaints/sql/init.sql",
            "/complaints/sql/complaints.sql"
    })
    void shouldFindComplaintsWithoutFilters() throws Exception {
        var request = new ComplaintsRequestRest()
                .meta(new MetaDataRest().sortBy("id").pageSize(BigDecimal.valueOf(5)));

        var expected = new String(Files.readAllBytes(Paths.get("src/test/resources/complaints/response/complaints-response-without-filters.json")));

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected, false));
    }

    @Test
    @Sql(scripts = {
            "/complaints/sql/clean.sql",
            "/complaints/sql/init.sql",
            "/complaints/sql/complaints.sql"
    })
    void shouldFindComplaintsWitFilters() throws Exception {
        var request = new ComplaintsRequestRest()
                .meta(new MetaDataRest().sortBy("id").pageSize(BigDecimal.valueOf(5)))
                .criteria(new ComplaintsCriteriaRest().id(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        var expected = new String(Files.readAllBytes(Paths.get("src/test/resources/complaints/response/complaints-response-with-filters.json")));

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected, false));


    }

    @Test
    @Sql(scripts = {
            "/complaints/sql/clean.sql",
            "/complaints/sql/init.sql"})
    void shouldCreateComplaint() throws Exception {
        UUID productId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        var request = new ComplaintCreateRequestRest()
                .productId(productId)
                .content("The product stopped working after one week")
                .reporter("test@example.com");

        var expected = Files.readString(Paths.get("src/test/resources/complaints/response/create-complaint-response.json"));

        mockMvc.perform(post("/complaint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                // then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expected, false));
    }

    @Test
    @Sql(scripts = {
            "/complaints/sql/clean.sql",
            "/complaints/sql/init.sql"
    })
    void shouldIncrementCounterIfComplaintExists() throws Exception {
        // Given
        UUID productId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var request = new ComplaintCreateRequestRest()
                .productId(productId)
                .content("The product stopped working after one week")
                .reporter("test@example.com");

        // When - first request to create complaint
        mockMvc.perform(post("/complaint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Given expected JSON for incremented counter
        var expected = Files.readString(Paths.get("src/test/resources/complaints/response/create-complaint-counter-incremented.json"));

        // When - second request (duplicate), should increment counter
        mockMvc.perform(post("/complaint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))

                // Then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expected, false));
    }



    @Test
    @Sql(scripts = {
            "/complaints/sql/clean.sql",
            "/complaints/sql/init.sql",
            "/complaints/sql/complaints.sql"
    })
    void shouldUpdateComplaintContent() throws Exception {
        UUID existingId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        var expected = Files.readString(Paths.get("src/test/resources/complaints/response/update-complaint-response.json"));

        mockMvc.perform(put("/complaint/" + existingId)
                        .queryParam("content", "Updated via test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected, false));
    }



}
