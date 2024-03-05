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

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CandidateTestTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataImport dataImport;

    @Test
    void shouldGetNewCandidateTest() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade = "10";
        String passedAt = LocalDate.of(2024, 3, 3).toString();
        String candidateId = Long.toString(candidateTests.get(0).getCandidate().getId());
        String testId = Long.toString(candidateTests.get(1).getTest().getId());

        String body = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId, testId, grade, passedAt);

        mockMvc.perform(post(UrlPath.CANDIDATE_TEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(candidateTests.get(candidateTests.size() - 1).getId() + 1))
                .andExpect(jsonPath("$.candidate.id").value(candidateId))
                .andExpect(jsonPath("$.test.id").value(testId))
                .andExpect(jsonPath("$.grade").value(grade))
                .andExpect(jsonPath("$.passedAt").value(passedAt));
    }

    @Test
    void shouldGetViolations() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade = "-10";
        String passedAt = "";
        String candidateId = "";
        String testId = "";

        String body = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId, testId, grade, passedAt);

        mockMvc.perform(post(UrlPath.CANDIDATE_TEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(4)));
    }

    @Test
    void shouldGetNotFoundCandidate() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade = "10";
        String passedAt = LocalDate.of(2024, 3, 3).toString();
        String candidateId = "100";
        String testId = Long.toString(candidateTests.get(1).getCandidate().getId());

        String body = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId, testId, grade, passedAt);

        mockMvc.perform(post(UrlPath.CANDIDATE_TEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("candidate.not.found"));
    }

    @Test
    void shouldGetNotFoundTest() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade = "10";
        String passedAt = LocalDate.of(2024, 3, 3).toString();
        String candidateId = Long.toString(candidateTests.get(0).getCandidate().getId());
        String testId = "100";

        String body = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId, testId, grade, passedAt);

        mockMvc.perform(post(UrlPath.CANDIDATE_TEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("test.not.found"));
    }

    @Test
    void shouldGetUpdatedCandidateTest() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade1 = "10";
        String passedAt1 = LocalDate.of(2024, 3, 3).toString();
        String candidateId1 = Long.toString(candidateTests.get(0).getCandidate().getId());
        String testId1 = Long.toString(candidateTests.get(1).getTest().getId());

        String body1 = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId1, testId1, grade1, passedAt1);

        mockMvc.perform(put(UrlPath.CANDIDATE_TEST + "/" + candidateTests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidateTests.get(0).getId()))
                .andExpect(jsonPath("$.candidate.id").value(candidateId1))
                .andExpect(jsonPath("$.test.id").value(testId1))
                .andExpect(jsonPath("$.grade").value(grade1))
                .andExpect(jsonPath("$.passedAt").value(passedAt1));

        String candidateId2 = Long.toString(candidateTests.get(0).getCandidate().getId());
        String testId2 = Long.toString(candidateTests.get(1).getTest().getId());

        String body2 = """
                  {
                    "candidateId": "%s",
                    "testId": "%s"
                  }
                """.formatted(candidateId2, testId2);

        mockMvc.perform(put(UrlPath.CANDIDATE_TEST + "/" + candidateTests.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidateTests.get(0).getId()))
                .andExpect(jsonPath("$.candidate.id").value(candidateId2))
                .andExpect(jsonPath("$.test.id").value(testId2));
    }

    @Test
    void shouldGetNotFoundCandidateTest() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String grade1 = "10";
        String passedAt1 = LocalDate.of(2024, 3, 3).toString();
        String candidateId1 = Long.toString(candidateTests.get(0).getCandidate().getId());
        String testId1 = Long.toString(candidateTests.get(1).getTest().getId());

        String body1 = """
                  {
                    "candidateId": "%s",
                    "testId": "%s",
                    "grade": "%s",
                    "passedAt": "%s"
                  }
                """.formatted(candidateId1, testId1, grade1, passedAt1);

        mockMvc.perform(put(UrlPath.CANDIDATE_TEST + "/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("candidate.test.not.found"));
    }

    @Test
    void shouldGetTests() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        mockMvc.perform(get(UrlPath.CANDIDATE_TEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(candidateTests.size())));
    }

    @Test
    void shouldGetNoContent() throws Exception {
        mockMvc.perform(get(UrlPath.CANDIDATE_TEST))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetCandidateTestsByFilter() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String candidateLastname = candidateTests.get(0).getCandidate().getLastname();
        String testName = candidateTests.get(0).getTest().getName();
        String grade = String.valueOf(candidateTests.get(0).getGrade());
        String date = candidateTests.get(0).getPassedAt().toString();

        String body = """
                  {
                    "candidateLastname": "%s",                 
                    "testName": "%s",                
                    "grade": "%s"               
                  }
                """.formatted(candidateLastname, testName, grade);

        mockMvc.perform(get(UrlPath.CANDIDATE_TEST + "/0/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].candidate.lastname").value(candidateLastname))
                .andExpect(jsonPath("$.content[0].test.name").value(testName))
                .andExpect(jsonPath("$.content[0].grade").value(grade))
                .andExpect(jsonPath("$.content[0].passedAt").value(date))
                .andExpect(jsonPath("$.metadata.size").value(3))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(false));
    }

    @Test
    void shouldGetPageableSortedCandidateTests() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String candidateLastname = "";
        String testName = "";
        String grade = "";
        String dateSort = "ASC";
        String gradeSort = "";

        String body = """
                  {
                    "candidateLastname": "%s",                 
                    "testName": "%s",                
                    "grade": "%s",
                    "gradeSort": "%s",
                    "dateSort": "%s"               
                  }
                """.formatted(candidateLastname, testName, grade, gradeSort, dateSort);

        mockMvc.perform(get(UrlPath.CANDIDATE_TEST + "/0/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(candidateTests.get(0).getId()))
                .andExpect(jsonPath("$.content[1].id").value(candidateTests.get(1).getId()))
                .andExpect(jsonPath("$.metadata.size").value(2))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(true));
    }

    @Test
    void shouldGet2Violations() throws Exception {
        var candidateTests = dataImport.importCandidateTests();

        String candidateLastname = "";
        String testName = "";
        String grade = "";
        String dateSort = "bimbim";
        String gradeSort = "asc";

        String body = """
                  {
                    "candidateLastname": "%s",                 
                    "testName": "%s",                
                    "grade": "%s",
                    "gradeSort": "%s",
                    "dateSort": "%s"               
                  }
                """.formatted(candidateLastname, testName, grade, gradeSort, dateSort);

        mockMvc.perform(get(UrlPath.CANDIDATE_TEST + "/0/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(2)));
    }
}
