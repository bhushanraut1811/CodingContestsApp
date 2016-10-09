package apps.techmines.codingcontestapp.service.apiservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model for Challenge
 */
public class Challenge implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("platformName")
    @Expose
    private String platformName;
    @SerializedName("challengeName")
    @Expose
    private String challengeName;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("challengeUrl")
    @Expose
    private String challengeUrl;
    @SerializedName("challengeLogo")
    @Expose
    private String challengeLogo;
    @SerializedName("dateStr")
    @Expose
    private String dateStr;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The platformName
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * @param platformName The platformName
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * @return The challengeName
     */
    public String getChallengeName() {
        return challengeName;
    }

    /**
     * @param challengeName The challengeName
     */
    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    /**
     * @return The date
     */
    public long getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * @return The challengeUrl
     */
    public String getChallengeUrl() {
        return challengeUrl;
    }

    /**
     * @param challengeUrl The challengeUrl
     */
    public void setChallengeUrl(String challengeUrl) {
        this.challengeUrl = challengeUrl;
    }

    /**
     * @return The challengeLogo
     */
    public String getChallengeLogo() {
        return challengeLogo;
    }

    /**
     * @param challengeLogo The challengeLogo
     */
    public void setChallengeLogo(String challengeLogo) {
        this.challengeLogo = challengeLogo;
    }

    /**
     * @return The dateStr
     */
    public String getDateStr() {
        return dateStr;
    }

    /**
     * @param dateStr The dateStr
     */
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}

