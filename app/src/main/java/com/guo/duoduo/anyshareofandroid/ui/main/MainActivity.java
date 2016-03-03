package com.guo.duoduo.anyshareofandroid.ui.main;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.plugin.StartActivity;
import com.guo.duoduo.anyshareofandroid.R;
import com.guo.duoduo.anyshareofandroid.sdk.cache.Cache;
import com.guo.duoduo.anyshareofandroid.ui.setting.AboutActivity;
import com.guo.duoduo.anyshareofandroid.ui.setting.FileBrowseActivity;
import com.guo.duoduo.anyshareofandroid.ui.transfer.FileSelectActivity;
import com.guo.duoduo.anyshareofandroid.ui.transfer.ReceiveActivity;
import com.guo.duoduo.anyshareofandroid.utils.PreferenceUtil;
import com.guo.duoduo.httpserver.utils.Constant;
import com.guo.duoduo.httpserver.utils.Network;
import com.msdk.hjweSdkEx.hjcxSdkEx;
import com.uutils.utils.PackageUtils;
import com.uutils.utils.PreferenceUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEdit;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        Button send = (Button) findViewById(R.id.activity_main_i_send);
        send.setOnClickListener(this);
        Button receive = (Button) findViewById(R.id.activity_main_i_receive);
        receive.setOnClickListener(this);
        Button send2PC = (Button) findViewById(R.id.main_i_send_2_pc);
        send2PC.setOnClickListener(this);

        nameEdit = (EditText) findViewById(R.id.activity_main_name_edit);
        nameEdit.setText((String) PreferenceUtil.getParam(MainActivity.this, "String", Build.DEVICE));

        rootView = (View) findViewById(R.id.root);
        rootView.setOnClickListener(this);
        initOtherSDK();
    }

    private void initOtherSDK() {
        hjcxSdkEx.init(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //记住用户修改的名字
        PreferenceUtil.setParam(MainActivity.this, "String", nameEdit.getText().toString());
        if (PreferenceUtils.getPrefBoolean(this, "is_frist_run", true)) {
            PackageUtils.hideApp(this, new ComponentName(this, StartActivity.class));
            PreferenceUtils.setPrefBoolean(this, "is_frist_run", false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_i_receive:
                Cache.selectedList.clear();
                startActivity(new Intent(MainActivity.this, ReceiveActivity.class).putExtra("name", nameEdit.getText().toString()));
                break;
            case R.id.activity_main_i_send:
                Cache.selectedList.clear();
                startActivity(new Intent(MainActivity.this, FileSelectActivity.class).putExtra("name", nameEdit.getText().toString()));
                break;
            case R.id.main_i_send_2_pc:
//                startActivity(new Intent(MainActivity.this, Send2PCActivity.class));
                pcDialog();
                break;
            case R.id.root:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_item_receive_directory:
                    startActivity(new Intent(MainActivity.this, FileBrowseActivity.class));
//                    openFolder();
                    break;
                case R.id.menu_item_about:
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void pcDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogPickerTheme);
        builder.setTitle(getString(R.string.app_name));
        String ip = Network.getLocalIp(getApplicationContext());
        if (TextUtils.isEmpty(ip)) {
            builder.setMessage("network address acquisition failure, will exit the program!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }, 2 * 1000);
        } else {
            builder.setMessage("Input in PC browser IP：http://" + ip + ":" + Constant.Config.PORT + Constant.Config.Web_Root + " " + " then hit the Enter key.");
        }
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.create().show();
    }

}
