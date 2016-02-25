package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.caipu.R;
import com.silence.pojo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/1/30 0030.
 */
public class CategoryFgt extends ListFragment {

    private Context mContext;
    private onCategoryListener mOnCategoryListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCategoryListener) {
            mOnCategoryListener = (onCategoryListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        List<Category> categories = initData();
        CommonAdapter commonAdapter = new CommonAdapter<Category>(categories, R.layout.item_category) {
            @Override
            public void bindView(ViewHolder holder, Category obj) {
                holder.setText(R.id.tv_cat_title, obj.getTitle());
                holder.setText(R.id.tv_cat_desc, obj.getDescription());
            }
        };
        setListAdapter(commonAdapter);
    }

    private List<Category> initData() {
        String[] titles = mContext.getResources().getStringArray(R.array.category_title);
        String[] descriptions = mContext.getResources().getStringArray(R.array.category_description);
        List<Category> categories = new ArrayList<>(titles.length);
        for (int i = 0; i < titles.length; i++) {
            categories.add(new Category(i + 1, titles[i], descriptions[i]));
        }
        return categories;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mOnCategoryListener != null) {
            Category category = (Category) getListAdapter().getItem(position);
            mOnCategoryListener.getCategory(category);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCategoryListener = null;
    }

    public interface onCategoryListener {
        void getCategory(Category category);
    }
}
