package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.caipu.R;
import com.silence.dao.CookDao;
import com.silence.pojo.Category;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

import java.util.List;

/**
 * Created by Silence on 2016/2/6 0006.
 */
public class CookFgt extends ListFragment {
    private onCookClickListener mOnCookClickListener;
    private Category mCategory;
    private CommonAdapter mCommonAdapter;
    private CookDao mCookDao;

    public static CookFgt newInstance(Category category) {
        CookFgt cookFgt = new CookFgt();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.CATEGORY_KEY, category);
        cookFgt.setArguments(bundle);
        return cookFgt;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCookClickListener) {
            mOnCookClickListener = (onCookClickListener) context;
        }
        mCategory = getArguments().getParcelable(Const.CATEGORY_KEY);
        mCookDao = new CookDao(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getListView().setSelection(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Cook> cookList = mCookDao.getCookByType(mCategory != null ? mCategory.getId() : 1);
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
            setListAdapter(mCommonAdapter);
        } else {
            mCommonAdapter.setData(cookList);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCookClickListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mOnCookClickListener != null) {
            mOnCookClickListener.getCook(((Cook) l.getItemAtPosition(position)).getId());
        }
    }

    public void refresh(Category category) {
        mCategory = category;
    }

    public interface onCookClickListener {
        void getCook(int cookId);
    }

}
