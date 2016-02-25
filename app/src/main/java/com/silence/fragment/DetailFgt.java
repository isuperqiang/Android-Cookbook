package com.silence.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.caipu.R;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

/**
 * Created by Silence on 2016/2/11 0011.
 */
public class DetailFgt extends Fragment {

    public static DetailFgt newInstance(Cook cook) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.COOK_KEY, cook);
        DetailFgt detailFgt = new DetailFgt();
        detailFgt.setArguments(bundle);
        return detailFgt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tvMaterial = (TextView) view.findViewById(R.id.tv_detail_material);
        TextView tvMethod = (TextView) view.findViewById(R.id.tv_detail_method);
        ImageView mImageView = (ImageView) view.findViewById(R.id.iv_detail);
        Cook cook = getArguments().getParcelable(Const.COOK_KEY);
        if (cook != null) {
            tvMethod.setText(cook.getMethod());
            tvMaterial.setText(cook.getMaterial());
            mImageView.setImageBitmap(BitmapFactory.decodeFile(cook.getPath()));
        }
        return view;
    }
}
