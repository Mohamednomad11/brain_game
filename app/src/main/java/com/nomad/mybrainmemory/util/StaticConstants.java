package com.nomad.mybrainmemory.util;

import com.nomad.mybrainmemory.model.ScoreModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticConstants {
    public static final String KEY_GAME_NAME = "game_name";

    public static final String KEY_GAME_SCORE = "game_score";

    public static final String KEY_SCORE_REPORT = "score_report";
    public static final String GAME_MATCHING = "matching";
    public static final String GAME_JIGSAW = "jigsaw";

    public static final String KEY_DIFFICULTY_LEVEL = "level";

    public static final String LEVEL_EASY = "easy";

    public static final String LEVEL_MEDIUM = "medium";

    public static final String LEVEL_HARD = "hard";


    public static final String WARN_INVALID_USER_NAME = "User name is not valid!";

    public static final String WARN_INVALID_PASS = "Password is not valid! It should be of length 6 - 8 and include alphanumeric characters with one special character";

    public static final String WARN_INVALID_EMAIL = "Email address is not valid!";

    public static final String WARN_INVALID_MATCH_PASS = "Password does not match with confirm password";
    public static final String VAL_USER_TYPE_USER = "user";

    public static final String VAL_USER_TYPE_ADMIN = "admin";
    public static final String KEY_SCORE_LIST = "scoreList";
    public static String CURRENT_USER_FID = "fid";


    // ---
    public static String CURRENT_USER_NAME = "no name";

    public static final String WARN_EMPTY_FIELD = "Fill all the fields";

    public static ArrayList<String> MATCHING_IMAGE_LIST = new ArrayList<>();

    // shared pref key
    public static final String KEY_USER_TYPE = "userType";

    public static List<ScoreModel> scoreList = new ArrayList<>();
    public static HashMap<String,List<ScoreModel>> userScoreMap = new HashMap<>();


}
