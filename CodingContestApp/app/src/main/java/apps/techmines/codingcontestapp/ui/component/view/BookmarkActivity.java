package apps.techmines.codingcontestapp.ui.component.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.dataservice.model.BookmarkChallenge;
import apps.techmines.codingcontestapp.ui.adapter.BookmarkListAdapter;
import apps.techmines.codingcontestapp.ui.component.presenter.BookmarkPresenter;
import apps.techmines.codingcontestapp.ui.component.presenter.IBookmarkView;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;

/**
 * Displays all the bookmarks added
 */
public class BookmarkActivity extends BaseActivity implements IBookmarkView {

    private BookmarkPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private RelativeLayout mParentLayout;
    private BookmarkListAdapter mAdapter;
    private List<BookmarkChallenge> mBookmarkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        initViews();
        //instantiate presenter
        mPresenter = new BookmarkPresenter(this);
        //fetch data from realm database
        mPresenter.fetchBookmarks(mBookmarkList);
    }

    protected void initViews() {
        setUpHomeEnabled();
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_bookmark_list);
        mParentLayout = (RelativeLayout) findViewById(R.id.rl_bookmarks);
    }

    private void deleteOnSwipe() {
        SimpleCallback simpleCallback = new SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == LEFT) {
                    //remove from list as well as from database
                    mAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Drawable drawable = getResources().getDrawable(R.drawable.ic_delete_black_24dp);
                Paint p = new Paint();
                if (actionState == ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#9e9e9e"));//"#b1ff0307"
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = drawableToBitmap(drawable);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void populateListView() {
        // do from presenter
        if (mBookmarkList.size() < 1) {
            showSnackBar(mParentLayout, getResources().getString(R.string.no_bookmarks));
        } else {
            mAdapter = new BookmarkListAdapter(this, mBookmarkList, new BookmarkListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(mBookmarkList.get(position).getmChallengeUrl());
                    viewIntent.setData(uri);
                    startActivity(viewIntent);
                }
            });

            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            //enabling delete on swipe after population data to recycler view
            deleteOnSwipe();
        }
    }

    /**
     * Converts Drawable to bitmaps
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
