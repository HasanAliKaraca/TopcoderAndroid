/*
* Copyright (C) 2015 Hasan Ali Karaca - http://www.hasanalikaraca.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.ali.topcoderandroid.Models;

import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class ChallengeModel implements Serializable {

    private boolean isPrivate;
    private boolean registrationOpen;
    private long currentPhaseRemainingTime;
    private long totalPrize;


    private String firstPlacePrize;

    private String challengeType;

    private String registrationEndDate;

    private String[] platforms;

    private String currentPhaseEndDate;

    private String challengeName;

    private String eventId;

    private String forumId;

    private String submissionEndDate;

    private String numRegistrants;

    private String currentPhaseName;

    private String status;

    private String digitalRunPoints;

    private String totalCheckpointPrize;


    private String numberOfCheckpointsPrizes;

    private String reviewType;

    private String numSubmissions;

    private String reliabilityBonus;

    private String[] technologies;

    private String checkpointSubmissionEndDate;

    private String eventName;

    private String challengeCommunity;


    private String challengeId;
    private String registrationStartDate;

    public static ChallengeModel mapJsonToChallengeModel(JSONObject json) {
        ChallengeModel item;
        try {

            Gson gson = new Gson();

            String jsonString = json.toString();

            item = gson.fromJson(jsonString, ChallengeModel.class);

            item.platforms = json.getJSONArray("platforms").toString().replace("},{", " ,").split(" ");
            item.registrationOpen = json.getString("registrationOpen").toLowerCase().trim() == "yes" ? true : false;
            item.platforms = json.getJSONArray("platforms").toString().replace("},{", " ,").split(" ");
            item.registrationOpen = json.getString("registrationOpen").toLowerCase().trim() == "yes" ? true : false;
            item.technologies = json.getJSONArray("technologies").toString()
                    .replace("\"", "")
                    .replace("}", "")
                    .replace("{", "")
                    .replace("[", "")
                    .replace("]", "")
                    .split(",");



        } catch (Exception e) {
            LogHelper.Log(e);
            return null;
        }
        return item;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public long getCurrentPhaseRemainingTime() {
        return currentPhaseRemainingTime;
    }

    public void setCurrentPhaseRemainingTime(long currentPhaseRemainingTime) {
        this.currentPhaseRemainingTime = currentPhaseRemainingTime;
    }

    public String getFirstPlacePrize() {
        return firstPlacePrize;
    }

    public void setFirstPlacePrize(String firstPlacePrize) {
        this.firstPlacePrize = firstPlacePrize;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    public String getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(String registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String getCurrentPhaseEndDate() {
        return currentPhaseEndDate;
    }

    public void setCurrentPhaseEndDate(String currentPhaseEndDate) {
        this.currentPhaseEndDate = currentPhaseEndDate;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getSubmissionEndDate() {
        return submissionEndDate;
    }

    public void setSubmissionEndDate(String submissionEndDate) {
        this.submissionEndDate = submissionEndDate;
    }

    public String getNumRegistrants() {
        return numRegistrants;
    }

    public void setNumRegistrants(String numRegistrants) {
        this.numRegistrants = numRegistrants;
    }

    public String getCurrentPhaseName() {
        return currentPhaseName;
    }

    public void setCurrentPhaseName(String currentPhaseName) {
        this.currentPhaseName = currentPhaseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDigitalRunPoints() {
        return digitalRunPoints;
    }

    public void setDigitalRunPoints(String digitalRunPoints) {
        this.digitalRunPoints = digitalRunPoints;
    }

    public String getTotalCheckpointPrize() {
        return totalCheckpointPrize;
    }

    public void setTotalCheckpointPrize(String totalCheckpointPrize) {
        this.totalCheckpointPrize = totalCheckpointPrize;
    }

    public long getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(long totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getNumberOfCheckpointsPrizes() {
        return numberOfCheckpointsPrizes;
    }

    public void setNumberOfCheckpointsPrizes(String numberOfCheckpointsPrizes) {
        this.numberOfCheckpointsPrizes = numberOfCheckpointsPrizes;
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public String getNumSubmissions() {
        return numSubmissions;
    }

    public void setNumSubmissions(String numSubmissions) {
        this.numSubmissions = numSubmissions;
    }

    public String getReliabilityBonus() {
        return reliabilityBonus;
    }

    public void setReliabilityBonus(String reliabilityBonus) {
        this.reliabilityBonus = reliabilityBonus;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }

    public String getCheckpointSubmissionEndDate() {
        return checkpointSubmissionEndDate;
    }

    public void setCheckpointSubmissionEndDate(String checkpointSubmissionEndDate) {
        this.checkpointSubmissionEndDate = checkpointSubmissionEndDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getChallengeCommunity() {
        return challengeCommunity;
    }

    public void setChallengeCommunity(String challengeCommunity) {
        this.challengeCommunity = challengeCommunity;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(String registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

   /* public static long getTotalPrice(long[] prizeArr){
        long totalPrize = 0l;

        if (prizeArr != null) {
            int len = prizeArr.length;
            long total=0l;
            for (int i = 0; i < len; i++) {
                total +=   prizeArr[i]; // .get(i);
            }
            totalPrize += total;
        }
        return totalPrize;
    }*/


}
