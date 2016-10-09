package apps.techmines.codingcontestapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.apiservice.model.Challenge;
import apps.techmines.codingcontestapp.service.dataservice.RealmDB;
import apps.techmines.codingcontestapp.service.dataservice.model.BookmarkChallenge;
import apps.techmines.codingcontestapp.ui.component.view.BaseActivity;
import apps.techmines.codingcontestapp.ui.component.view.ContestsHomeActivity;
import io.realm.Realm;

/**
 * Created by bhushan.raut on 9/24/2016.
 */
public class ContestListAdapter extends RecyclerView.Adapter<ContestListAdapter.ItemViewHolder> {

    private ArrayList<Challenge> mList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public ContestListAdapter(Context context, ArrayList<Challenge> list, OnItemClickListener onClickListener) {
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
        final Challenge challenge = mList.get(position);
        holder.mTvName.setText(challenge.getChallengeName());
        holder.mTvDate.setText(challenge.getDateStr());

        Picasso.with(mContext).load(challenge.getChallengeLogo())
                .placeholder(R.drawable.ic_launcher_grayscale).error(R.drawable.ic_launcher_grayscale)
                .resize(100, 100).into(holder.mIvLogo);

        //Button Listeners
        holder.mBtnShare.setVisibility(View.VISIBLE);
        //share button listeners
        holder.mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share Intent
                shareChallenge(challenge);
            }
        });

        holder.mBtnBookmark.setVisibility(View.VISIBLE);
        //bookmark button listeners
        holder.mBtnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save into realm db show snack bar on
                //?? should do from a presenter
                saveBookmark(challenge);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
        //void onItemLongClick(View v, int position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvDate;
        ImageView mIvLogo;
        ImageButton mBtnBookmark;
        ImageButton mBtnShare;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_challenge_name);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_challenge_date);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            mBtnBookmark = (ImageButton) itemView.findViewById(R.id.btn_bookmark);
            mBtnShare = (ImageButton) itemView.findViewById(R.id.btn_share);
        }
    }


    /**
     * Bookmark challenge,Saves in Realm Database
     *
     * @param challenge
     */
    private void saveBookmark(Challenge challenge) {
        Realm realm = RealmDB.newInstance(mContext);
        BaseActivity context = (ContestsHomeActivity) mContext;
        //check if already present contest
        long count = realm.where(BookmarkChallenge.class).equalTo("mChallengeName", challenge.getChallengeName()).count();
        if (count < 1) {
            realm.beginTransaction();
            //create model for realm specific data
            BookmarkChallenge bookmark = realm.createObject(BookmarkChallenge.class);
            bookmark.setmChallengeName(challenge.getChallengeName());
            bookmark.setmPlatformName(challenge.getPlatformName());
            bookmark.setmChallengeUrl(challenge.getChallengeUrl());
            bookmark.setmChallengeLogo(challenge.getChallengeLogo());
            bookmark.setmDateStr(challenge.getDateStr());
            bookmark.setmTimeStamp(Calendar.getInstance().getTimeInMillis());
            realm.commitTransaction();
            //show snack bar
            context.showSnackBar(context.findViewById(R.id.container), mContext.getResources().getString(R.string.save_bookmark));
        } else {
            context.showSnackBar(context.findViewById(R.id.container), mContext.getResources().getString(R.string.already_saved_bookmark));
        }
    }

    /**
     * Share the challenges provides with intent chooser
     *
     * @param challenge
     */
    private void shareChallenge(Challenge challenge) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String message = "Check out this coding challenge!!! " + challenge.getChallengeName() + " on " + challenge.getPlatformName() + "\n Visit here  "
                + challenge.getChallengeUrl();
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        shareIntent.setType("text/plain");
        mContext.startActivity(shareIntent);
    }


}
