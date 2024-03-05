package com.zavadskiy.candidate_management_service.integration;

import com.zavadskiy.candidate_management_service.api.utils.UrlPath;
import com.zavadskiy.candidate_management_service.integration.utils.IntegrationTestBase;
import com.zavadskiy.candidate_management_service.integration.utils.TestDataImport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
class FileTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataImport dataImport;

    @Test
    void shouldGetFile() throws Exception {
        var files = dataImport.importFiles();

        mockMvc.perform(get(UrlPath.FILES + "/" + files.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().bytes(files.get(0).getData()));
    }

    @Test
    void shouldGetNotFoundFile() throws Exception {
        var files = dataImport.importFiles();

        mockMvc.perform(get(UrlPath.FILES + "/asdfgfdga-a114-asdfsadf"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("file.not.found"));
    }
}
