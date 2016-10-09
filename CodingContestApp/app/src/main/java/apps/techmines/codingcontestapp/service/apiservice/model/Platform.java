
package apps.techmines.codingcontestapp.service.apiservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Model for Platform
 */
public class Platform {

    @SerializedName("platformName")
    @Expose
    private String platformName;
    @SerializedName("platfromLogoUrl")
    @Expose
    private String platfromLogoUrl;
    @SerializedName("platformUrl")
    @Expose
    private String platformUrl;

    /**
     * 
     * @return
     *     The platformName
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * 
     * @param platformName
     *     The platformName
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * 
     * @return
     *     The platfromLogoUrl
     */
    public String getPlatfromLogoUrl() {
        return platfromLogoUrl;
    }

    /**
     * 
     * @param platfromLogoUrl
     *     The platfromLogoUrl
     */
    public void setPlatfromLogoUrl(String platfromLogoUrl) {
        this.platfromLogoUrl = platfromLogoUrl;
    }

    /**
     * 
     * @return
     *     The platformUrl
     */
    public String getPlatformUrl() {
        return platformUrl;
    }

    /**
     * 
     * @param platformUrl
     *     The platformUrl
     */
    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

}
