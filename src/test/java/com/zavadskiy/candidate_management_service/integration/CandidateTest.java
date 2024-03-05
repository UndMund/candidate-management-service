package com.zavadskiy.candidate_management_service.integration;

import com.zavadskiy.candidate_management_service.api.utils.UrlPath;
import com.zavadskiy.candidate_management_service.integration.utils.IntegrationTestBase;
import com.zavadskiy.candidate_management_service.integration.utils.TestDataImport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
class CandidateTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataImport dataImport;

    @Test
    void shouldGetNewCandidate() throws Exception {
        var candidates = dataImport.importCandidates();
        var photo = candidates.get(0).getPhoto();
        var cv = candidates.get(0).getCv();

        String firstName = "Firstname";
        String lastName = "Lastname";
        String patronymic = "Patronymic";
        String description = "I`m test candidate with test data";
        var avatarMock = new MockMultipartFile(
                "photo",
                photo.getName(),
                photo.getContentType(),
                photo.getData()
        );
        var cvMock = new MockMultipartFile(
                "cv",
                cv.getName(),
                cv.getContentType(),
                cv.getData()
        );
        String specialities = candidates.get(0).getSpecialities()
                .stream().toList()
                .get(0).getId().toString();

        var result = mockMvc.perform(multipart(UrlPath.CANDIDATES)
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(candidates.get(candidates.size() - 1).getId() + 1))
                .andExpect(jsonPath("$.firstname").value(firstName))
                .andExpect(jsonPath("$.lastname").value(lastName))
                .andExpect(jsonPath("$.patronymic").value(patronymic))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.specialities[0].id").value(specialities))
                .andExpect(jsonPath("$.photo.name").value(avatarMock.getOriginalFilename()))
                .andExpect(jsonPath("$.cv.name").value(cvMock.getOriginalFilename()));
    }

    @Test
    void shouldGetViolations() throws Exception {
        var candidates = dataImport.importCandidates();
        var photo = candidates.get(0).getPhoto();
        var cv = candidates.get(0).getCv();

        String firstName = "F";
        String lastName = "g";
        String patronymic = "g";
        String description = "I`m";
        var avatarMock = new MockMultipartFile(
                "photo",
                photo.getName(),
                photo.getContentType(),
                photo.getData()
        );
        var cvMock = new MockMultipartFile(
                "cv",
                cv.getName(),
                cv.getContentType(),
                cv.getData()
        );
        String specialities = "";

        mockMvc.perform(multipart(UrlPath.CANDIDATES)
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(5)));

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(UrlPath.CANDIDATES + "/" + candidates.get(0).getId());

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(4)));
    }

    @Test
    void shouldGetNotFoundSpeciality() throws Exception {
        var candidates = dataImport.importCandidates();
        var photo = candidates.get(0).getPhoto();
        var cv = candidates.get(0).getCv();

        String firstName = "Firstname";
        String lastName = "Lastname";
        String patronymic = "Patronymic";
        String description = "I`m test candidate with test data";
        var avatarMock = new MockMultipartFile(
                "photo",
                photo.getName(),
                photo.getContentType(),
                photo.getData()
        );
        var cvMock = new MockMultipartFile(
                "cv",
                cv.getName(),
                cv.getContentType(),
                cv.getData()
        );
        String specialities = "100";

        mockMvc.perform(multipart(UrlPath.CANDIDATES)
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("speciality.not.found"));

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(UrlPath.CANDIDATES + "/" + candidates.get(0).getId());

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("speciality.not.found"));
    }

    @Test
    void shouldGetUpdatedCandidate() throws Exception {
        var candidates = dataImport.importCandidates();
        var photo = candidates.get(0).getPhoto();
        var cv = candidates.get(0).getCv();

        String firstName = "Firstname";
        String lastName = "Lastname";
        String patronymic = "Patronymic";
        String description = "I`m test candidate with test data";
        var avatarMock = new MockMultipartFile(
                "photo",
                photo.getName(),
                photo.getContentType(),
                photo.getData()
        );
        var cvMock = new MockMultipartFile(
                "cv",
                cv.getName(),
                cv.getContentType(),
                cv.getData()
        );
        String specialities = candidates.get(0).getSpecialities()
                .stream().toList()
                .get(0).getId().toString();


        MockMultipartHttpServletRequestBuilder builder1 =
                MockMvcRequestBuilders.multipart(UrlPath.CANDIDATES + "/" + candidates.get(1).getId());

        builder1.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder1
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidates.get(1).getId()))
                .andExpect(jsonPath("$.firstname").value(firstName))
                .andExpect(jsonPath("$.lastname").value(lastName))
                .andExpect(jsonPath("$.patronymic").value(patronymic))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.specialities[0].id").value(specialities))
                .andExpect(jsonPath("$.photo.name").value(avatarMock.getOriginalFilename()))
                .andExpect(jsonPath("$.cv.name").value(cvMock.getOriginalFilename()));

        MockMultipartHttpServletRequestBuilder builder2 =
                MockMvcRequestBuilders.multipart(UrlPath.CANDIDATES + "/" + candidates.get(1).getId());

        builder2.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder2
                        .file(avatarMock)
                        .file(cvMock)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidates.get(1).getId()))
                .andExpect(jsonPath("$.lastname").value(lastName))
                .andExpect(jsonPath("$.patronymic").value(patronymic));
    }

    @Test
    void shouldGetCandidateNotFound() throws Exception {
        var candidates = dataImport.importCandidates();
        var photo = candidates.get(0).getPhoto();
        var cv = candidates.get(0).getCv();

        String firstName = "Firstname";
        String lastName = "Lastname";
        String patronymic = "Patronymic";
        String description = "I`m test candidate with test data";
        var avatarMock = new MockMultipartFile(
                "photo",
                photo.getName(),
                photo.getContentType(),
                photo.getData()
        );
        var cvMock = new MockMultipartFile(
                "cv",
                cv.getName(),
                cv.getContentType(),
                cv.getData()
        );
        String specialities = candidates.get(0).getSpecialities()
                .stream().toList()
                .get(0).getId().toString();

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(UrlPath.CANDIDATES + "/100");

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(avatarMock)
                        .file(cvMock)
                        .param("firstname", firstName)
                        .param("lastname", lastName)
                        .param("patronymic", patronymic)
                        .param("description", description)
                        .param("specialities", specialities)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("candidate.not.found"));
    }

    @Test
    void shouldGetTests() throws Exception {
        var candidates = dataImport.importCandidates();

        mockMvc.perform(get(UrlPath.CANDIDATES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(candidates.size())));
    }

    @Test
    void shouldGetNoContent() throws Exception {
        mockMvc.perform(get(UrlPath.CANDIDATES))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetCandidateByFilter() throws Exception {
        var candidates = dataImport.importCandidates();

        String lastname = candidates.get(0).getLastname();
        String specialityName = candidates.get(0).getSpecialities()
                .stream().toList()
                .get(0).getName();

        String body = """
                  {
                    "lastname": "%s",                 
                    "specialityName": "%s"                 
                  }
                """.formatted(lastname, specialityName);

        mockMvc.perform(get(UrlPath.CANDIDATES + "/0/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].lastname").value(lastname))
                .andExpect(jsonPath("$.content[0].specialities[0].name").value(specialityName))
                .andExpect(jsonPath("$.metadata.size").value(3))
                .andExpect(jsonPath("$.metadata.page").value(0))
                .andExpect(jsonPath("$.metadata.nextPagePresent").value(false));
    }

    @Test
    void shouldGetPageableSpecialities() throws Exception {
        var candidates = dataImport.importCandidates();

        String lastname = "";
        String specialityName = "";

        String body = """
                  {
                    "lastname": "%s",                 
                    "specialityName": "%s"                 
                  }
                """.formatted(lastname, specialityName);

        mockMvc.perform(get(UrlPath.CANDIDATES + "/0/2")
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
