package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.caipu.R;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

import java.util.List;

/**
 * Created by Silence on 2016/2/6 0006.
 */
public class SearchFgt extends ListFragment {

    private CommonAdapter mCommonAdapter;
    private onCookClickListener mOnCookClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCookClickListener) {
            mOnCookClickListener = (onCookClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommonAdapter = new CommonAdapter<Cook>(null, R.layout.item_cook) {
            @Override
            public void bindView(ViewHolder holder, Cook obj) {
                holder.setText(R.id.tv_cook_name, obj.getName());
                String material = obj.getMaterial();
                if (material.length() > Const.COLLAPSE_LEN) {
                    material = material.substring(0, Const.COLLAPSE_LEN) + "......";
                }
                holder.setText(R.id.tv_cook_material, material);
                holder.setImageResource(R.id.iv_cook, obj.getPath());
            }
        };
        setListAdapter(mCommonAdapter);
    }

    public void refresh(List<Cook> cooks) {
        mCommonAdapter.setData(cooks);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mOnCookClickListener != null) {
            mOnCookClickListener.getCook(((Cook) l.getItemAtPosition(position)).getId());
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mOnCookClickListener = null;
    }

    public interface onCookClickListener {
        void getCook(int cookId);
    }
}