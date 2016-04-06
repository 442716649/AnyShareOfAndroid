package com.guo.duoduo.anyshareofandroid.ui.uientity;


import android.graphics.drawable.Drawable;

import com.guo.duoduo.p2pmanager.p2pconstant.P2PConstant;


/**
 * Created by 郭攀峰 on 2015/9/15.
 */
public class VideoInfo implements IInfo
{
    public int type = P2PConstant.TYPE.VID;
    public String vidPath;
    public String vidSize;
    public String vidName;

    @Override
    public String getFilePath()
    {
        return vidPath;
    }

    @Override
    public String getFileSize()
    {
        return vidSize;
    }

    @Override
    public int getFileType()
    {
        return type;
    }

    @Override
    public Drawable getFileIcon()
    {
        return null;

    }

    @Override
    public String getFileName()
    {
        return vidName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (getFilePath() != null && ((VideoInfo) o).getFilePath() != null)
            return getFilePath().equals(((VideoInfo) o).getFilePath());
        else
            return false;
    }
}
