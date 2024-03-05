package com.zavadskiy.candidate_management_service.integration.utils;

import com.zavadskiy.candidate_management_service.database.entity.*;
import com.zavadskiy.candidate_management_service.database.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataImport {
    private final CandidateRepository candidateRepository;
    private final CandidateTestRepository candidateTestRepository;
    private final FileRepository fileRepository;
    private final SpecialityRepository specialityRepository;
    private final TestRepository testRepository;

    public List<Speciality> importSpecialities() {
        var speciality1 = Speciality.builder()
                .name("Java Dev")
                .description("Developing java apps with spring and hibernate")
                .build();
        var speciality2 = Speciality.builder()
                .name("Python Dev")
                .description("Developing python apps with django")
                .build();
        var speciality3 = Speciality.builder()
                .name("JS Dev")
                .description("Developing js apps with angular")
                .build();

        return specialityRepository.saveAll(Arrays.asList(speciality1, speciality2, speciality3));
    }

    public List<Test> importTests() {
        var specialities = importSpecialities();
        var test1 = Test.builder()
                .name("Java Test")
                .description("Test java core knowledge")
                .speciality(specialities.get(0))
                .build();
        var test2 = Test.builder()
                .name("Python Test")
                .description("Test python core knowledge")
                .speciality(specialities.get(1))
                .build();
        var test3 = Test.builder()
                .name("JS Test")
                .description("Test angular core knowledge")
                .speciality(specialities.get(2))
                .build();

        return testRepository.saveAll(Arrays.asList(test1, test2, test3));
    }

    private List<Test> importTests(List<Speciality> specialities) {
        var test1 = Test.builder()
                .name("Java Test")
                .description("Test java core knowledge")
                .speciality(specialities.get(0))
                .build();
        var test2 = Test.builder()
                .name("Python Test")
                .description("Test python core knowledge")
                .speciality(specialities.get(1))
                .build();
        var test3 = Test.builder()
                .name("JS Test")
                .description("Test angular core knowledge")
                .speciality(specialities.get(2))
                .build();

        return testRepository.saveAll(Arrays.asList(test1, test2, test3));
    }

    @SneakyThrows
    public List<File> importFiles() {
        var avatar1 = File.builder()
                .name("avatar1")
                .contentType("image/png")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("avatar1.png"))
                .build();
        var file1 = File.builder()
                .name("cv1")
                .contentType("application/pdf")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("cv1.pdf"))
                .build();
        var avatar2 = File.builder()
                .name("avatar2")
                .contentType("image/png")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("avatar2.png"))
                .build();
        var file2 = File.builder()
                .name("cv2")
                .contentType("application/pdf")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("cv2.pdf"))
                .build();
        var avatar3 = File.builder()
                .name("avatar3")
                .contentType("image/png")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("avatar3.png"))
                .build();
        var file3 = File.builder()
                .name("cv3")
                .contentType("application/pdf")
                .size(1000L)
                .data(FileUtils.readFileAsBytes("cv3.pdf"))
                .build();

        return fileRepository.saveAll(Arrays.asList(avatar1, file1, avatar2, file2, avatar3, file3));
    }

    public List<Candidate> importCandidates() {
        var specialities = importSpecialities();
        var files = importFiles();

        var candidate1 = Candidate.builder()
                .firstname("Alex")
                .lastname("Zernov")
                .patronymic("Alekseevich")
                .description("I`m Java Developer with spring knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(0))))
                .photo(files.get(0))
                .cv(files.get(1))
                .build();
        var candidate2 = Candidate.builder()
                .firstname("Anton")
                .lastname("Abelov")
                .patronymic("Sergeevich")
                .description("I`m Python Developer with django knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(1))))
                .photo(files.get(2))
                .cv(files.get(3))
                .build();
        var candidate3 = Candidate.builder()
                .firstname("Lena")
                .lastname("Zelenova")
                .patronymic("Alexandrovna")
                .description("I`m Js Developer with core knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(2))))
                .photo(files.get(4))
                .cv(files.get(5))
                .build();

        return candidateRepository.saveAll(Arrays.asList(candidate1, candidate2, candidate3));
    }

    private List<Candidate> importCandidates(List<Speciality> specialities) {
        var files = importFiles();

        var candidate1 = Candidate.builder()
                .firstname("Alex")
                .lastname("Zernov")
                .patronymic("Alekseevich")
                .description("I`m Java Developer with spring knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(0))))
                .photo(files.get(0))
                .cv(files.get(1))
                .build();
        var candidate2 = Candidate.builder()
                .firstname("Anton")
                .lastname("Abelov")
                .patronymic("Sergeevich")
                .description("I`m Python Developer with django knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(1))))
                .photo(files.get(2))
                .cv(files.get(3))
                .build();
        var candidate3 = Candidate.builder()
                .firstname("Lena")
                .lastname("Zelenova")
                .patronymic("Alexandrovna")
                .description("I`m Js Developer with core knowledge")
                .specialities(new HashSet<>(Collections.singletonList(specialities.get(2))))
                .photo(files.get(4))
                .cv(files.get(5))
                .build();

        return candidateRepository.saveAll(Arrays.asList(candidate1, candidate2, candidate3));
    }

    public List<CandidateTest> importCandidateTests() {
        var specialities = importSpecialities();
        var candidates = importCandidates(specialities);
        var tests = importTests(specialities);

        var candidateTest1 = CandidateTest.builder()
                .candidate(candidates.get(0))
                .test(tests.get(0))
                .grade(9)
                .passedAt(LocalDate.of(2024, 1, 1))
                .build();
        var candidateTest2 = CandidateTest.builder()
                .candidate(candidates.get(1))
                .test(tests.get(1))
                .grade(10)
                .passedAt(LocalDate.of(2024, 2, 3))
                .build();
        var candidateTest3 = CandidateTest.builder()
                .candidate(candidates.get(2))
                .test(tests.get(2))
                .grade(8)
                .passedAt(LocalDate.of(2024, 3, 1))
                .build();

        return candidateTestRepository.saveAll(Arrays.asList(candidateTest1, candidateTest2, candidateTest3));
    }

}

