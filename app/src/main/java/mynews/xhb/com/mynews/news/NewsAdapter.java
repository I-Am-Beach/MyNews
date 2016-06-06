package mynews.xhb.com.mynews.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.utils.ImageLoadUtils;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<NewsBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public boolean isShowFooter() {
        return mShowFooter;
    }

    public void setShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<NewsBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(v);
            return viewHolder;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            NewsBean newsBean = mData.get(position);
            if (newsBean == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(newsBean.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(newsBean.getDigest());
            ImageLoadUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg, newsBean.getImgsrc());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public NewsBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) itemView.findViewById(R.id.ivNews);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.OnItemClick(v, this.getPosition());
            }
        }
    }
}
