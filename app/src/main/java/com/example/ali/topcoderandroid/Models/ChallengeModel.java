package com.example.ali.topcoderandroid.Models;


import com.example.ali.topcoderandroid.Helpers.LogHelper;

import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ChallengeModel implements Serializable {
    private String submissionEndDate;
    private long currentPhaseRemainingTime;
    private String checkpointSubmissionEndDate;
    private String registrationStartDate;
    private String registrationEndDate;
    private String currentPhaseEndDate;

    private String firstPlacePrize;
    private String challengeType;
    private String[] platforms;
    private String eventId;
    private String challengeName;
    private String forumId;
    private boolean isPrivate;
    private String numRegistrants;
    private String currentPhaseName;
    private String status;
    private String digitalRunPoints;
    private String totalPrize;
    private String numberOfCheckpointsPrizes;
    private String reviewType;
    private String numSubmissions;
    private String reliabilityBonus;
    private String[] technologies;
    private String eventName;
    private String challengeCommunity;
    private boolean registrationOpen;
    private String challengeId;

    public static ChallengeModel mapJsonToChallengeModel(JSONObject json) {
        ChallengeModel item = new ChallengeModel();
        try {
            item.submissionEndDate = json.getString("submissionEndDate");
            item.currentPhaseRemainingTime = json.getLong("currentPhaseRemainingTime");
            item.challengeName = json.getString("challengeName");
            item.eventId = json.getString("eventId");
            item.platforms = json.getJSONArray("platforms").toString().replace("},{", " ,").split(" ");
            item.registrationOpen = json.getString("registrationOpen").toLowerCase().trim() == "yes" ? true : false;

            if (json.has("totalPrize"))
                item.totalPrize = json.getString("totalPrize");
            else
                item.totalPrize = "0";

            item.numRegistrants = json.getString("numRegistrants");
            item.challengeId = json.getString("challengeId");
            item.numSubmissions = json.getString("numSubmissions");
            item.registrationStartDate = json.getString("registrationStartDate");
            item.reviewType = json.getString("reviewType");
            item.status = json.getString("status");
            item.registrationEndDate = json.getString("registrationEndDate");
            item.currentPhaseEndDate = json.getString("currentPhaseEndDate");
            item.technologies = json.getJSONArray("technologies").toString()
                    .replace("\"", "")
                    .replace("}", "")
                    .replace("{", "")
                    .replace("[", "")
                    .replace("]", "")
                    .split(",");

            if (json.has("firstPlacePrize"))
                item.firstPlacePrize = json.getString("firstPlacePrize");
            else
                item.firstPlacePrize = "0";

            if (json.has("digitalRunPoints"))
                item.digitalRunPoints = json.getString("digitalRunPoints");
            else
                item.digitalRunPoints = "0";


            item.forumId = json.getString("forumId");
            item.isPrivate = json.getBoolean("isPrivate");
            item.currentPhaseName = json.getString("currentPhaseName");
            item.challengeType = json.getString("challengeType");
            item.checkpointSubmissionEndDate = json.getString("checkpointSubmissionEndDate");
            item.reliabilityBonus = json.getString("reliabilityBonus");
            item.challengeCommunity = json.getString("challengeCommunity");

        } catch (Exception e) {
            LogHelper.Log(e);
            return null;
        }
        return item;
    }

    public String getSubmissionEndDate() {
        return submissionEndDate;
    }

    public void setSubmissionEndDate(String submissionEndDate) {
        this.submissionEndDate = submissionEndDate;
    }

    public long getCurrentPhaseRemainingTime() {
        return currentPhaseRemainingTime;
    }

    public void setCurrentPhaseRemainingTime(long currentPhaseRemainingTime) {
        this.currentPhaseRemainingTime = currentPhaseRemainingTime;
    }

    public String getCheckpointSubmissionEndDate() {
        return checkpointSubmissionEndDate;
    }

    public void setCheckpointSubmissionEndDate(String checkpointSubmissionEndDate) {
        this.checkpointSubmissionEndDate = checkpointSubmissionEndDate;
    }

    public String getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(String registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public String getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(String registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public String getCurrentPhaseEndDate() {
        return currentPhaseEndDate;
    }

    public void setCurrentPhaseEndDate(String currentPhaseEndDate) {
        this.currentPhaseEndDate = currentPhaseEndDate;
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

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
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

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }
}
