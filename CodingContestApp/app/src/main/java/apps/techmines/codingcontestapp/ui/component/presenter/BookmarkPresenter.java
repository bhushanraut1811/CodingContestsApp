package apps.techmines.codingcontestapp.ui.component.presenter;

import android.content.Context;

import java.util.List;

import apps.techmines.codingcontestapp.service.dataservice.RealmDB;
import apps.techmines.codingcontestapp.service.dataservice.model.BookmarkChallenge;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Bookmark presenter handles non ui logic
 */
public class BookmarkPresenter {
    private IBookmarkView mView;

    public BookmarkPresenter(IBookmarkView mView) {
        this.mView = mView;
    }

    /**
     * Fetch bookmarks stored in realm database
     *
     * @param bookmarkList
     */
    public void fetchBookmarks(List<BookmarkChallenge> bookmarkList) {

        Realm realm = RealmDB.newInstance((Context) mView);
        RealmResults<BookmarkChallenge> realmResults = realm.where(BookmarkChallenge.class).findAllSorted("mTimeStamp", RealmResults.SORT_ORDER_DESCENDING);
        bookmarkList.addAll(realmResults);
        //call back on UI screen to show list view
        mView.populateListView();
    }
}
