package com.guo.duoduo.anyshareofandroid.ui.setting.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guo.duoduo.anyshareofandroid.R;
import com.guo.duoduo.p2pmanager.p2pcore.P2PManager;

import java.io.File;


/**
 * show the received images, click item to browse the big one
 */
public class ReceivedPicture extends Fragment
{

    Context context ;
    private View mView;

    public static ReceivedPicture newInstance()
    {
        ReceivedPicture fragment = new ReceivedPicture();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_received2, container, false);
           mView.findViewById(R.id.received_button).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   openFolder();
               }
           });


        }
        return mView;

    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        context = activity ;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    public void openFolder() {

        if (!TextUtils.isEmpty(P2PManager.getSavePath(1))) {
            File file = new File(P2PManager.getSavePath(1));
            if (file.exists() && file.isDirectory()) {
                File[] appFileArray = file.listFiles();
                if (appFileArray != null && appFileArray.length > 0) {
                    Intent intent = new Intent();
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setDataAndType(Uri.fromFile(file), "file/");
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(context, "receiving path :" + P2PManager.getSavePath(1), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "no receiving content", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "no receiving content", Toast.LENGTH_SHORT).show();
            }

        }
    }
}




