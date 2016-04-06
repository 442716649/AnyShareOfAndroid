package com.guo.duoduo.anyshareofandroid.ui.transfer.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guo.duoduo.anyshareofandroid.R;
import com.guo.duoduo.anyshareofandroid.sdk.cache.Cache;
import com.guo.duoduo.anyshareofandroid.ui.uientity.IInfo;
import com.guo.duoduo.p2pmanager.p2pconstant.P2PConstant;
import com.guo.duoduo.p2pmanager.p2pentity.P2PFileInfo;

import java.io.File;
import java.util.List;


/**
 * Created by 郭攀峰 on 2015/9/15.
 */
public class VideoSelectAdapter extends RecyclerView.Adapter<VideoSelectAdapter.MyViewHolder> {
    private Context context;
    private List<IInfo> list;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public VideoSelectAdapter(Context context, List<IInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public VideoSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_app_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideoSelectAdapter.MyViewHolder holder, final int position) {
//        holder.imageView.setImageBitmap(((BitmapDrawable) list.get(position)
//                .getFileIcon()).getBitmap());

        IInfo info = list.get(position);
        if(P2PConstant.TYPE.AUD==info.getFileType())
            holder.imageView.setImageResource(R.mipmap.audio_icon);
        else {
            holder.imageView.setImageResource(R.mipmap.video_icon);
        }
        P2PFileInfo fileInfo = new P2PFileInfo();
        fileInfo.name = info.getFileName();
        fileInfo.type = info.getFileType();
        fileInfo.size = new File(info.getFilePath()).length();
        fileInfo.path = info.getFilePath();

        if (Cache.selectedList.contains(fileInfo)) {
            holder.app_choice.setVisibility(View.VISIBLE);
        } else {
            holder.app_choice.setVisibility(View.GONE);
        }

        holder.appName.setText(list.get(position).getFileName());
        holder.appSize.setText(list.get(position).getFileSize());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.imageView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public IInfo getItem(int position) {
        return list.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView app_choice;
        TextView appName;
        TextView appSize;
        LinearLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.AppIcon);
            appName = (TextView) view.findViewById(R.id.AppName);
            appSize = (TextView) view.findViewById(R.id.AppSize);
            app_choice = (ImageView) view.findViewById(R.id.app_choice);
            itemLayout = (LinearLayout) view.findViewById(R.id.app_item_layout);
        }
    }
}
