package mynews.xhb.com.mynews.images;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.ImageBean;
import mynews.xhb.com.mynews.utils.ImageLoadUtils;
import mynews.xhb.com.mynews.utils.ToolUtils;

/**
 * Created by Administrator on 2016/6/4.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<ImageBean> mData;
    private int mMaxWidth;
    private int mMaxHeight;

    private OnItemClickListener mOnItemClickListener;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        mMaxWidth = ToolUtils.getWidthInPx(mContext) - 20;
        mMaxHeight = ToolUtils.getHeightInPx(mContext) - ToolUtils.getStatusHeight(mContext) - ToolUtils.dip2px(mContext, 96);
    }

    public void setData(List<ImageBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageBean imageBean = mData.get(position);
        if (imageBean == null) {
            return;
        }
        holder.tvTitle.setText(imageBean.getTitle());

        float scale = (float) imageBean.getWidth() / (float) mMaxWidth;
        int height = (int) (imageBean.getHeight() / scale);
        if (height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.ivImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        ImageLoadUtils.display(mContext, holder.ivImage, imageBean.getThumburl());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public android.widget.ImageView ivImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.img_title);
            ivImage = (android.widget.ImageView) itemView.findViewById(R.id.img_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }
}
