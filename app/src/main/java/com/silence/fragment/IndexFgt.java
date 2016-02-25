package com.silence.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.adapter.ViewPagerAdapter;
import com.silence.caipu.R;
import com.silence.dao.CookDao;
import com.silence.pojo.Cook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/1/30 0030.
 */
public class IndexFgt extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private onCookClickListener mOnCookClickListener;
    private List<View> mViews;
    private Cook[] mCooks;
    private int mCurIndex;
    private TextView mTextView;
    private ImageView[] mPoints;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCookClickListener) {
            mOnCookClickListener = (onCookClickListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initPagerViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mTextView = (TextView) view.findViewById(R.id.tv_index_name);
        mTextView.setText(mCooks[mCurIndex].getName());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_cook);
        ViewPagerAdapter adapter = new ViewPagerAdapter(mViews);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        mPoints = new ImageView[]{(ImageView) view.findViewById(R.id.iv_first_point),
                (ImageView) view.findViewById(R.id.iv_second_point), (ImageView) view.findViewById(R.id.iv_third_point)};
        mPoints[0].setEnabled(true);
        mPoints[1].setEnabled(false);
        mPoints[2].setEnabled(false);
        return view;
    }

    private void initData() {
        CookDao cookDao = new CookDao(getActivity());
        int count = cookDao.getCount();
        mCooks = new Cook[3];
        for (int i = 0; i < 3; i++) {
            int ranId = (int) (Math.random() * count + 1);
            Cook cook = cookDao.getCook(ranId);
            mCooks[i] = cook;
        }
    }

    private void initPagerViews() {
        mViews = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.image_cook, null);
            imageView.setImageBitmap(BitmapFactory.decodeFile(mCooks[i].getPath()));
            imageView.setOnClickListener(this);
            mViews.add(imageView);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCookClickListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        updateState(position);
    }

    private void updateState(int position) {
        mTextView.setText(mCooks[position].getName());
        mPoints[position].setEnabled(true);
        mPoints[mCurIndex].setEnabled(false);
        mCurIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (mOnCookClickListener != null) {
            mOnCookClickListener.getCook(mCooks[mCurIndex].getId());
        }
    }

    public interface onCookClickListener {
        void getCook(int cookId);
    }

}
