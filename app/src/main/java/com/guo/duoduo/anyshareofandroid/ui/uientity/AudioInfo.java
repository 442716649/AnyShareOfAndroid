package com.guo.duoduo.anyshareofandroid.ui.uientity;


import android.graphics.drawable.Drawable;

import com.guo.duoduo.p2pmanager.p2pconstant.P2PConstant;


/**
 * Created by 郭攀峰 on 2015/9/15.
 */
public class AudioInfo implements IInfo
{
    public int type = P2PConstant.TYPE.AUD;
    public String audPath;
    public String audSize;
    public String audName;

    @Override
    public String getFilePath()
    {
        return audPath;
    }

    @Override
    public String getFileSize()
    {
        return audSize;
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
        return audName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (getFilePath() != null && ((AudioInfo) o).getFilePath() != null)
            return getFilePath().equals(((AudioInfo) o).getFilePath());
        else
            return false;
    }
}
