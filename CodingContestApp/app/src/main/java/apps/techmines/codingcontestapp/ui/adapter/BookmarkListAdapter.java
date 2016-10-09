package apps.techmines.codingcontestapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.dataservice.RealmDB;
import apps.techmines.codingcontestapp.service.dataservice.model.BookmarkChallenge;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by bhushan.raut on 9/25/2016.
 */
public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.ItemViewHolder> {

    private List<BookmarkChallenge> mList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public BookmarkListAdapter(Context context, List<BookmarkChallenge> list, OnItemClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge, parent, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItemClick(v, itemViewHolder.getLayoutPosition());
            }
        });
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final BookmarkChallenge bookmarkChallenge = mList.get(position);
        holder.mTvName.setText(bookmarkChallenge.getmChallengeName());
        holder.mTvDate.setText(bookmarkChallenge.getmDateStr());
        holder.mTvPlatform.setVisibility(View.VISIBLE);
        holder.mTvPlatform.setText(bookmarkChallenge.getmPlatformName());

        Picasso.with(mContext).load(bookmarkChallenge.getmChallengeLogo())
                .placeholder(R.drawable.ic_launcher_grayscale).error(R.drawable.ic_launcher_grayscale)
                .resize(100, 100).into(holder.mIvLogo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvDate;
        ImageView mIvLogo;
        TextView mTvPlatform;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_challenge_name);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_challenge_date);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            mTvPlatform = (TextView) itemView.findViewById(R.id.tv_platform_name);
        }
    }

    /**
     * remves item from DB and list on Swipe Left
     *
     * @param position
     */
    public void removeItem(int position) {
        //delete from database and list
        //check feasibility
        deleteFromDb(position);
        mList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * removes item from DB
     *
     * @param position
     */
    private void deleteFromDb(int position) {
        Realm realm = RealmDB.newInstance(mContext);
        realm.beginTransaction();
        RealmQuery query = realm.where(BookmarkChallenge.class);
        RealmResults results = query.findAllSorted("mTimeStamp", RealmResults.SORT_ORDER_DESCENDING);
        results.remove(position);
        realm.commitTransaction();
    }
}

