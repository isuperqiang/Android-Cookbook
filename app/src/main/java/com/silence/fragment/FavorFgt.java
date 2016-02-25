package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.silence.adapter.CommonAdapter;
import com.silence.caipu.R;
import com.silence.dao.CookDao;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

import java.util.List;

/**
 * Created by Silence on 2016/2/5 0005.
 */
public class FavorFgt extends Fragment implements AdapterView.OnItemClickListener {
    private CookDao mCookDao;
    private CommonAdapter mCommonAdapter;
    private onCookClickable mOnCookClickable;
    private TextView mTextView;
    private ListView mListView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCookClickable) {
            mOnCookClickable = (onCookClickable) context;
        }
        mCookDao = new CookDao(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor, container, false);
        mListView = (ListView) view.findViewById(R.id.list_favor);
        mTextView = (TextView) view.findViewById(R.id.tv_favor_nodata);
        mListView.setOnItemClickListener(this);
        return view;
    }

    public void refresh() {
        List<Cook> cookList = mCookDao.getFavors();
        if (cookList != null) {
            mListView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
            if (mCommonAdapter == null) {
                mCommonAdapter = new CommonAdapter<Cook>(cookList, R.layout.item_cook) {
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
                mListView.setAdapter(mCommonAdapter);
            } else {
                mCommonAdapter.setData(cookList);
            }
        } else {
            mTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCookClickable = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnCookClickable != null) {
            mOnCookClickable.getCook(((Cook) parent.getItemAtPosition(position)).getId());
        }
    }

    public interface onCookClickable {
        void getCook(int cookId);
    }

}
