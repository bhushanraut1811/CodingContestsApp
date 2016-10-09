package apps.techmines.codingcontestapp.service.dataservice.model;

import io.realm.RealmObject;

/**
 * Model for BookMarks
 */
public class BookmarkChallenge extends RealmObject {

    private String mChallengeName;
    private String mDateStr;
    private String mChallengeUrl;
    private String mChallengeLogo;
    private String mPlatformName;
    private Long mTimeStamp;

    /**
     * Default Constructor
     */
    public BookmarkChallenge() {

    }

    /**
     * Constructor
     */
    public BookmarkChallenge(String mChallengeLogo, String mChallengeName, String mChallengeUrl, String mDateStr, String mPlatformName) {

        this.mChallengeLogo = mChallengeLogo;
        this.mChallengeName = mChallengeName;
        this.mChallengeUrl = mChallengeUrl;
        this.mDateStr = mDateStr;
        this.mPlatformName = mPlatformName;
    }

    public String getmChallengeLogo() {
        return mChallengeLogo;
    }

    public void setmChallengeLogo(String mChallengeLogo) {
        this.mChallengeLogo = mChallengeLogo;
    }

    public String getmChallengeName() {
        return mChallengeName;
    }

    public void setmChallengeName(String mChallengeName) {
        this.mChallengeName = mChallengeName;
    }

    public String getmChallengeUrl() {
        return mChallengeUrl;
    }

    public void setmChallengeUrl(String mChallengeUrl) {
        this.mChallengeUrl = mChallengeUrl;
    }

    public String getmDateStr() {
        return mDateStr;
    }

    public void setmDateStr(String mDateStr) {
        this.mDateStr = mDateStr;
    }

    public String getmPlatformName() {
        return mPlatformName;
    }

    public void setmPlatformName(String mPlatformName) {
        this.mPlatformName = mPlatformName;
    }

    public Long getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(Long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }
}
