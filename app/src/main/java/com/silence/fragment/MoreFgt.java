package com.silence.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.silence.caipu.R;
import com.silence.utils.Const;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Silence on 2016/2/5 0005.
 */
public class MoreFgt extends Fragment implements View.OnClickListener {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        view.findViewById(R.id.tv_more_update).setOnClickListener(this);
        view.findViewById(R.id.tv_more_recommend).setOnClickListener(this);
        view.findViewById(R.id.tv_more_feedback).setOnClickListener(this);
        view.findViewById(R.id.tv_more_ads).setOnClickListener(this);
        view.findViewById(R.id.tv_more_help).setOnClickListener(this);
        view.findViewById(R.id.tv_more_setting).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.text_intro);
                builder.setMessage(R.string.readme);
                builder.setPositiveButton(R.string.text_ok, null);
                builder.show();
                break;
            case R.id.tv_more_update:
                final ProgressDialog dialog = new ProgressDialog(mContext);
                dialog.setTitle(mContext.getString(R.string.text_checking));
                dialog.setMessage(mContext.getString(R.string.text_wait));
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(mContext, R.string.text_new_version, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, Const.DELAY_TIME);
                break;
            case R.id.tv_more_ads:
            case R.id.tv_more_feedback:
            case R.id.tv_more_setting:
                Toast.makeText(mContext, "未实现的功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_recommend:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                File file = new File(mContext.getFilesDir() + File.separator + "share.png");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                intent.putExtra(Intent.EXTRA_TEXT, "推荐给你一个好玩的的应用--家常菜菜谱");
                startActivity(intent);
                break;
        }
    }
}
