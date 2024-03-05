package com.zavadskiy.candidate_management_service.integration;

import com.zavadskiy.candidate_management_service.api.utils.UrlPath;
import com.zavadskiy.candidate_management_service.integration.utils.IntegrationTestBase;
import com.zavadskiy.candidate_management_service.integration.utils.TestDataImport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class TestTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataImport dataImport;

    @Test
    void shouldGetNewTest() throws Exception {
        var tests = dataImport.importTests();

        String name = "C# Test";
        String description = "Tests C# and .NET knowledge";
        String specialityId = Long.toString(tests.get(0).getSpeciality().getId());

        String body = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name, description, specialityId);

        mockMvc.perform(post(UrlPath.TESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(tests.get(tests.size() - 1).getId() + 1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.speciality.id").value(specialityId));
    }

    @Test
    void shouldGetInvalidNameError() throws Exception {
        var tests = dataImport.importTests();

        String name1 = "Java Test";
        String description1 = "Tests C# and .NET knowledge";
        String specialityId1 = Long.toString(tests.get(0).getSpeciality().getId());

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name1, description1, specialityId1);

        mockMvc.perform(post(UrlPath.TESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[0].fieldName").value("name"));


        String name2 = "Java Test";
        String description2 = "Tests C# and .NET knowledge";
        String specialityId2 = Long.toString(tests.get(0).getSpeciality().getId());

        String body2 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name2, description2, specialityId2);

        mockMvc.perform(put(UrlPath.TESTS + "/" + tests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[0].fieldName").value("name"));
    }

    @Test
    void shouldGetViolations() throws Exception {
        var tests = dataImport.importTests();

        String name1 = "J";
        String description1 = "Tests";

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": ""
                  }
                """.formatted(name1, description1);

        mockMvc.perform(post(UrlPath.TESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(3)));

        String name2 = "J";
        String description2 = "Tests";
        String specialityId2 = Long.toString(-1L);

        String body2 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name2, description2, specialityId2);

        mockMvc.perform(put(UrlPath.TESTS + "/" + tests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(3)));
    }

    @Test
    void shouldGetNotFoundSpeciality() throws Exception {
        var tests = dataImport.importTests();

        String name1 = "C# Test";
        String description1 = "Tests C# and .NET knowledge";
        String specialityId1 = Long.toString(100L);

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name1, description1, specialityId1);

        mockMvc.perform(post(UrlPath.TESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("speciality.not.found"));


        String name2 = "C# Test";
        String description2 = "Tests C# and .NET knowledge";
        String specialityId2 = Long.toString(100L);

        String body2 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name2, description2, specialityId2);

        mockMvc.perform(put(UrlPath.TESTS + "/" + tests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("speciality.not.found"));
    }

    @Test
    void shouldGetUpdatedTest() throws Exception {
        var tests = dataImport.importTests();

        String name1 = "C# Test";
        String description1 = "Tests C# and .NET knowledge";
        String specialityId1 = Long.toString(tests.get(0).getSpeciality().getId());

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name1, description1, specialityId1);

        mockMvc.perform(put(UrlPath.TESTS + "/" + tests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tests.get(0).getId()))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.description").value(description1))
                .andExpect(jsonPath("$.speciality.id").value(specialityId1));

        String description2 = "C# and .NET development version 2";
        String body2 = """
                  {
                    "description": "%s"
                  }
                """.formatted(description2);

        mockMvc.perform(put(UrlPath.TESTS + "/" + tests.get(1).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tests.get(1).getId()))
                .andExpect(jsonPath("$.name").value(tests.get(1).getName()))
                .andExpect(jsonPath("$.description").value(description2));
    }

    @Test
    void shouldGetNotFoundTest() throws Exception {
        var tests = dataImport.importTests();

        String name1 = "C# Test";
        String description1 = "Tests C# and .NET knowledge";
        String specialityId1 = Long.toString(tests.get(0).getSpeciality().getId());

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s",
                    "speciality": "%s"
                  }
                """.formatted(name1, description1, specialityId1);

        mockMvc.perform(put(UrlPath.TESTS + "/" + "100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("test.not.found"));
    }


    @Test
    void shouldGetTests() throws Exception {
        var tests = dataImport.importTests();

        mockMvc.perform(get(UrlPath.TESTS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(tests.size())));
    }

    @Test
    void shouldGetNoContent() throws Exception {
        mockMvc.perform(get(UrlPath.TESTS))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTestByFilter() throws Exception {
        var tests = dataImport.importTests();

        String name = tests.get(0).getName();
        String specialityName = tests.get(0).getSpeciality().getName();

        String body = """
                  {
                    "name": "%s",                 
                    "specialityName": "%s"                 
                  }
                """.formatted(name, specialityName);

        mockMvc.perform(get(UrlPath.TESTS + "/0/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value(name))
                .andExpect(jsonPath("$.content[0].speciality.name").value(specialityName))
                .andExpect(jsonPath("$.metadata.size").value(3))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(false));
    }

    @Test
    void shouldGetPageableTests() throws Exception {
        var tests = dataImport.importTests();

        String name = "";
        String specialityName = "";

        String body = """
                  {
                    "name": "%s",                 
                    "specialityName": "%s"                 
                  }
                """.formatted(name, specialityName);

        mockMvc.perform(get(UrlPath.TESTS + "/0/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.metadata.size").value(2))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(true));
    }
}

