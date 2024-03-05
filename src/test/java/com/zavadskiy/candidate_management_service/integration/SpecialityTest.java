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
class SpecialityTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataImport dataImport;

    @Test
    void shouldGetNewSpeciality() throws Exception {
        var specialities = dataImport.importSpecialities();

        String name = "C# dev";
        String description = "C# and .NET development";

        String body = """
                  {
                    "name": "%s",
                    "description": "%s"
                  }
                """.formatted(name, description);

        mockMvc.perform(post(UrlPath.SPECIALITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(specialities.get(specialities.size() - 1).getId() + 1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description));
    }

    @Test
    void shouldGetInvalidNameError() throws Exception {
        var specialities = dataImport.importSpecialities();

        String body1 = """
                  {
                    "name": "Java Dev",
                    "description": "Java development and etc."
                  }
                """;

        mockMvc.perform(post(UrlPath.SPECIALITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[0].fieldName").value("name"));


        String body2 = """
                  {
                    "name": "Java Dev",
                    "description": "Java development and etc. asdfdfas"
                  }
                """;

        mockMvc.perform(put(UrlPath.SPECIALITIES + "/" + specialities.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[0].fieldName").value("name"));
    }

    @Test
    void shouldGetTwoViolations() throws Exception {
        var specialities = dataImport.importSpecialities();

        String body = """
                  {
                    "name": "J",
                    "description": "Java"
                  }
                """;

        mockMvc.perform(post(UrlPath.SPECIALITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(2)));

        mockMvc.perform(put(UrlPath.SPECIALITIES + "/" + specialities.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(2)));
    }

    @Test
    void shouldGetUpdatedSpeciality() throws Exception {
        var specialities = dataImport.importSpecialities();

        String name1 = "C# dev";
        String description1 = "C# and .NET development";

        String body1 = """
                  {
                    "name": "%s",
                    "description": "%s"
                  }
                """.formatted(name1, description1);

        mockMvc.perform(put(UrlPath.SPECIALITIES + "/" + specialities.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(specialities.get(0).getId()))
                .andExpect(jsonPath("$.name").value(name1))
                .andExpect(jsonPath("$.description").value(description1));

        String description2 = "C# and .NET development version 2";
        String body2 = """
                  {
                    "description": "%s"
                  }
                """.formatted(description2);

        mockMvc.perform(put(UrlPath.SPECIALITIES + "/" + specialities.get(1).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(specialities.get(1).getId()))
                .andExpect(jsonPath("$.name").value(specialities.get(1).getName()))
                .andExpect(jsonPath("$.description").value(description2));
    }


    @Test
    void shouldGetSpecialities() throws Exception {
        var specialities = dataImport.importSpecialities();

        mockMvc.perform(get(UrlPath.SPECIALITIES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(specialities.size())));
    }

    @Test
    void shouldGetNoContent() throws Exception {
        mockMvc.perform(get(UrlPath.SPECIALITIES))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetSpecialityByFilter() throws Exception {
        var specialities = dataImport.importSpecialities();

        String name = specialities.get(0).getName();

        String body = """
                  {
                    "name": "%s"
                  }
                """.formatted(name);

        mockMvc.perform(get(UrlPath.SPECIALITIES + "/0/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value(name))
                .andExpect(jsonPath("$.metadata.size").value(3))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(false));
    }

    @Test
    void shouldGetPageableSpecialities() throws Exception {
        var specialities = dataImport.importSpecialities();

        String name = "";

        String body = """
                  {
                    "name": "%s"
                  }
                """.formatted(name);

        mockMvc.perform(get(UrlPath.SPECIALITIES + "/0/2")
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
