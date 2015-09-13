package com.example.ali.topcoderandroid.Api;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ali on 27.08.2015.
 */
public enum ChallengeType {
    Design(0),
    Develop(1),
    Data(2);

    private static final Map<Integer, ChallengeType> challeTypeMap = new HashMap<Integer, ChallengeType>();
    private final static String PREF_CHALLENGE_TYPE_SETTING_NAME = "preferredChallengeType";

    static {
        for (ChallengeType challengeType : ChallengeType.values()) {
            challeTypeMap.put(challengeType.challengeType, challengeType);
        }
    }

    private final int challengeType;

    ChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }

    public static ChallengeType getChallengeType(int challeTypeValue) {
        ChallengeType type = challeTypeMap.get(Integer.valueOf(challeTypeValue));
      /*  if (type == null)
            return ChallengeType.DLT_UNKNOWN;*/

        return type;
    }

    public static ChallengeType getPreferred(SharedPreferences sharedPreferences) {
       /* ChallengeType defaultChallengeType = ChallengeType.Develop;
        defaultChallengeType.getChallengeTypeValue()*/

        String preferredChallengeTypeValueStr = sharedPreferences.getString(PREF_CHALLENGE_TYPE_SETTING_NAME, "0");


        int preferredChallengeTypeValue = Integer.parseInt(preferredChallengeTypeValueStr); //todo stringin not null olması lazım patlayabilir

        ChallengeType preferredChallengeType = ChallengeType.getChallengeType(preferredChallengeTypeValue);

        return preferredChallengeType;

    }

    public int getChallengeTypeValue() {
        return challengeType;
    }

}
