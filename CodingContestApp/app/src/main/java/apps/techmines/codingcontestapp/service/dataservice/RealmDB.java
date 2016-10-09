package apps.techmines.codingcontestapp.service.dataservice;

import android.content.Context;

import io.realm.Realm;

/**
 * Provides RealmDB object
 */
public class RealmDB {

    public static Realm mRealmDB = null;

    private RealmDB(Context context) {
        this.mRealmDB = Realm.getInstance(context);
    }

    /**
     * Provides instance for RealmDB object
     */
    public static Realm newInstance(Context context) {
        if (mRealmDB == null) {
            new RealmDB(context);
        }
        return mRealmDB;
    }
}
