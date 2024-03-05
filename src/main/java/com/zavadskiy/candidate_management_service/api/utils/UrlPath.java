package com.zavadskiy.candidate_management_service.api.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {
    public final static String API = "/api/v1";
    public static final String CANDIDATES = API + "/candidates";
    public static final String TESTS = API + "/tests";
    public static final String CANDIDATE_TEST = API + "/candidates/tests";
    public static final String SPECIALITIES = API + "/specialities";
    public static final String FILES = API + "/files";
}
