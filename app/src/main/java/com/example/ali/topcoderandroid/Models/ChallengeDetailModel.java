package com.example.ali.topcoderandroid.Models;/*
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

import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class ChallengeDetailModel implements Serializable {

    private Long currentPhaseRemainingTime;
    private String challengeType;
    private long[] prize;
    private long totalPrize;
    private String[] technology;
    private String registrationEndDate;
    private String finalFixEndDate;
    private String topCheckPointPrize;
    private String[] platforms;
    private String finalSubmissionGuidelines;
    private String projectId;
    private String type;
    private String reviewScorecardId;
    private String currentPhaseEndDate;
    private String cmcTaskId;
    private String challengeName;
    private String forumId;
    private String submissionEndDate;
    private String currentPhaseName;
    private String postingDate;
    private String digitalRunPoints;
    private String numberOfCheckpointsPrizes;
    private String reviewType;
    private String forumLink;
    private String currentStatus;
    private long reliabilityBonus;
    private String appealsEndDate;
    private String directUrl;
    private String detailedRequirements;
    private String checkpointSubmissionEndDate;
    private String challengeCommunity;
    private String challengeId;
    private String screeningScorecardId;

    public static ChallengeDetailModel mapJsonToChallengeDetailModel(JSONObject json) {
        ChallengeDetailModel item;
        try {

            Gson gson = new Gson();

            String jsonString = json.toString();

            item = gson.fromJson(jsonString, ChallengeDetailModel.class);

            item.totalPrize = 0;

            long[] prize = item.prize;
            if (prize != null) {
                for (int i = 0; i < prize.length; i++) {
                    item.totalPrize += prize[i];
                }
            }

            //item.submissionEndDate = json.getString("submissionEndDate");

        } catch (Exception e) {
            LogHelper.Log(e);
            return null;
        }
        return item;
    }

    public void setCurrentPhaseRemainingTime(Long currentPhaseRemainingTime) {
        this.currentPhaseRemainingTime = currentPhaseRemainingTime;
    }

    public long getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(long totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    public String[] getTechnology() {
        return technology;
    }

    public void setTechnology(String[] technology) {
        this.technology = technology;
    }

    public String getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(String registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public String getFinalFixEndDate() {
        return finalFixEndDate;
    }

    public void setFinalFixEndDate(String finalFixEndDate) {
        this.finalFixEndDate = finalFixEndDate;
    }

    public String getTopCheckPointPrize() {
        return topCheckPointPrize;
    }

    public void setTopCheckPointPrize(String topCheckPointPrize) {
        this.topCheckPointPrize = topCheckPointPrize;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String getFinalSubmissionGuidelines() {
        return finalSubmissionGuidelines;
    }

    public void setFinalSubmissionGuidelines(String finalSubmissionGuidelines) {
        this.finalSubmissionGuidelines = finalSubmissionGuidelines;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReviewScorecardId() {
        return reviewScorecardId;
    }

    public void setReviewScorecardId(String reviewScorecardId) {
        this.reviewScorecardId = reviewScorecardId;
    }

    public String getCurrentPhaseEndDate() {
        return currentPhaseEndDate;
    }

    public void setCurrentPhaseEndDate(String currentPhaseEndDate) {
        this.currentPhaseEndDate = currentPhaseEndDate;
    }

    public String getCmcTaskId() {
        return cmcTaskId;
    }

    public void setCmcTaskId(String cmcTaskId) {
        this.cmcTaskId = cmcTaskId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public long getCurrentPhaseRemainingTime() {
        return currentPhaseRemainingTime;
    }

    public void setCurrentPhaseRemainingTime(long currentPhaseRemainingTime) {
        this.currentPhaseRemainingTime = currentPhaseRemainingTime;
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

    public String getCurrentPhaseName() {
        return currentPhaseName;
    }

    public void setCurrentPhaseName(String currentPhaseName) {
        this.currentPhaseName = currentPhaseName;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getDigitalRunPoints() {
        return digitalRunPoints;
    }

    public void setDigitalRunPoints(String digitalRunPoints) {
        this.digitalRunPoints = digitalRunPoints;
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

    public String getForumLink() {
        return forumLink;
    }

    public void setForumLink(String forumLink) {
        this.forumLink = forumLink;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public long getReliabilityBonus() {
        return reliabilityBonus;
    }

    public void setReliabilityBonus(long reliabilityBonus) {
        this.reliabilityBonus = reliabilityBonus;
    }

    public String getAppealsEndDate() {
        return appealsEndDate;
    }

    public void setAppealsEndDate(String appealsEndDate) {
        this.appealsEndDate = appealsEndDate;
    }

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }

    public long[] getPrize() {
        return prize;
    }

    public void setPrize(long[] prize) {
        this.prize = prize;
    }

    public String getDetailedRequirements() {
        return detailedRequirements;
    }

    public void setDetailedRequirements(String detailedRequirements) {
        this.detailedRequirements = detailedRequirements;
    }

    public String getCheckpointSubmissionEndDate() {
        return checkpointSubmissionEndDate;
    }

    public void setCheckpointSubmissionEndDate(String checkpointSubmissionEndDate) {
        this.checkpointSubmissionEndDate = checkpointSubmissionEndDate;
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

    public String getScreeningScorecardId() {
        return screeningScorecardId;
    }

    public void setScreeningScorecardId(String screeningScorecardId) {
        this.screeningScorecardId = screeningScorecardId;
    }


}
