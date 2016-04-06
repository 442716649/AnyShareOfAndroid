package com.guo.duoduo.anyshareofandroid.ui.transfer.fragment;


import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.guo.duoduo.anyshareofandroid.R;
import com.guo.duoduo.anyshareofandroid.constant.Constant;
import com.guo.duoduo.anyshareofandroid.sdk.cache.Cache;
import com.guo.duoduo.anyshareofandroid.ui.transfer.FileSelectActivity;
import com.guo.duoduo.anyshareofandroid.ui.transfer.view.VideoSelectAdapter;
import com.guo.duoduo.anyshareofandroid.ui.uientity.AudioInfo;
import com.guo.duoduo.anyshareofandroid.ui.uientity.IInfo;
import com.guo.duoduo.anyshareofandroid.ui.uientity.VideoInfo;
import com.guo.duoduo.anyshareofandroid.ui.view.MyWindowManager;
import com.guo.duoduo.anyshareofandroid.utils.DeviceUtils;
import com.guo.duoduo.anyshareofandroid.utils.ViewUtils;
import com.guo.duoduo.p2pmanager.p2pconstant.P2PConstant;
import com.guo.duoduo.p2pmanager.p2pentity.P2PFileInfo;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 郭攀峰 on 2015/9/16.
 */
public class AudioFragment extends Fragment implements VideoSelectAdapter.OnItemClickListener, OnSelectItemClickListener {

    private static final String tag = AudioFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private VideoSelectAdapter adapter;

    private List<IInfo> vidList = new ArrayList<>();
    private AudioHandler handler;
    private QueryHandler queryHandler;

    private FileSelectActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "PictureFragment onCreateView function");
        if (view == null) {
            handler = new AudioHandler(this);
            view = inflater.inflate(R.layout.view_select, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            progressBar = (ProgressBar) view.findViewById(R.id.loading);
            adapter = new VideoSelectAdapter(getActivity(), vidList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);

            getAudios();
//            getList();
        }

        return view;
    }

    private void getAudios() {
        if (queryHandler == null) {
            queryHandler = new QueryHandler(getActivity());
        }

        queryHandler.startQuery(1, null, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID}, null, null, MediaStore.Audio.Media.DATE_MODIFIED + " DESC");

    }

    //    public List<?> getList() {
//        List<IInfo> list = null;
//
//        boolean needCheck = DeviceUtils.needCheckExtSDCard();
//        String mPath = DeviceUtils.getExtSDCardPath();
//        if (mPath == null) needCheck = false;
//
//        if (activity != null) {
//            Cursor cursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//            if (cursor != null) {
//                list = new ArrayList<IInfo>();
//                while (cursor.moveToNext()) {
//                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
//                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
//                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
//                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
//                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
//                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
//                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
//                    long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
//                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
//
//
//                    // 判断是否需要添加
//                    if (needCheck && path.contains(mPath)) {
//                        continue;
//                    }
//                    File file = new File(path);
//                    if (file.exists()) {
//                        VideoInfo info = new VideoInfo();
//                        info.vidPath = path;
//                        info.vidSize = DeviceUtils.getFileSize(file.length());
//                        info.vidName = DeviceUtils.getFileName(path);
//                        if (!list.contains(info)) list.add(info);
//                    }
//                }
//                cursor.close();
//                Message msg = Message.obtain();
//                msg.what = Constant.MSG.VIDEO_OK;
//                msg.obj = list;
//                handler.sendMessage(msg);
//            }
//        }
//        return list;
//    }
    @Override
    public void onAttach(Activity activity) {
        try {
            this.activity = (FileSelectActivity) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(View view, int position) {
        AudioInfo info = ((AudioInfo) adapter.getItem(position));

        P2PFileInfo fileInfo = new P2PFileInfo();
        fileInfo.name = info.getFileName();
        fileInfo.type = P2PConstant.TYPE.AUD;
        fileInfo.size = new File(info.getFilePath()).length();
        fileInfo.path = info.getFilePath();

        if (Cache.selectedList.contains(fileInfo)) {
            Cache.selectedList.remove(fileInfo);
        } else {
            Cache.selectedList.add(fileInfo);

            startFloating(view, position);
        }
        adapter.notifyDataSetChanged();
        activity.onItemClicked(P2PConstant.TYPE.AUD);
    }

    @Override
    public void onItemClicked(int type) {

    }

    private void startFloating(View view, int position) {
        if (!MyWindowManager.isWindowShowing()) {
            int[] location = ViewUtils.getViewItemLocation(view);
            int viewX = location[0];
            int viewY = location[1];

            MyWindowManager.createSmallWindow(getActivity(), viewX, viewY, 0, 0, ((ImageView) view).getDrawable());
        }
    }

    private class QueryHandler extends AsyncQueryHandler {
        public QueryHandler(Context context) {
            super(context.getContentResolver());
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            List<IInfo> vidInfo = new ArrayList<>();

            if (cursor != null) {
                for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                    String str = cursor.getString(0);
//                    if (str.endsWith(".jpg") || str.endsWith(".png")
//                        || str.endsWith(".jpeg"))
//                    {
                    File file = new File(str);
                    if (file.exists()) {
                        AudioInfo info = new AudioInfo();
                        info.audPath = str;
                        info.audSize = DeviceUtils.getFileSize(file.length());
                        info.audName = DeviceUtils.getFileName(str);
                        if (!vidInfo.contains(info)) vidInfo.add(info);
                    }
//                    }
                }
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }

            Log.d(tag, "vid size =" + vidInfo.size());
            Message msg = Message.obtain();
            msg.what = Constant.MSG.VIDEO_OK;
            msg.obj = vidInfo;
            handler.sendMessage(msg);
        }
    }


    private static class AudioHandler extends Handler {
        private WeakReference<AudioFragment> weakReference;

        public AudioHandler(AudioFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AudioFragment fragment = weakReference.get();
            if (fragment == null) return;
            if (fragment.getActivity() == null) return;
            if (fragment.getActivity().isFinishing()) return;

            switch (msg.what) {
                case Constant.MSG.VIDEO_OK:
                    fragment.vidList.clear();
                    fragment.vidList.addAll((ArrayList<IInfo>) msg.obj);
                    fragment.progressBar.setVisibility(View.GONE);
                    fragment.adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
