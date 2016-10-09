package apps.techmines.codingcontestapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.apiservice.model.Platform;

/**
 * Created by bhushan.raut on 10/2/2016.
 */
public class PlatformListAdapter extends RecyclerView.Adapter<PlatformListAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<Platform> mList;
    private OnItemClickListener onClickListener;

    public PlatformListAdapter(Context context, ArrayList<Platform> platformList, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mList = platformList;
        this.onClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platform, parent, false);
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
        final Platform platform = mList.get(position);
        holder.mTvPlatformName.setText(platform.getPlatformName());
        Picasso.with(mContext).load(platform.getPlatfromLogoUrl())
                .placeholder(R.drawable.ic_launcher_grayscale).error(R.drawable.ic_launcher_grayscale)
                .resize(100, 100).into(holder.mIvPlatformLogo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvPlatformName;
        ImageView mIvPlatformLogo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvPlatformName = (TextView) itemView.findViewById(R.id.tv_platform);
            mIvPlatformLogo = (ImageView) itemView.findViewById(R.id.iv_platform_logo);

        }
    }
}
