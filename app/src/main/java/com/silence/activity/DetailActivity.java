package com.silence.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.silence.caipu.R;
import com.silence.dao.CookDao;
import com.silence.fragment.DetailFgt;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

public class DetailActivity extends AppCompatActivity {

    private boolean isFavorite;
    private int mCookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mCookId = getIntent().getIntExtra(Const.COOK_KEY, 0);
        CookDao cookDao = new CookDao(this);
        Cook cook = cookDao.getCook(mCookId);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(cook != null ? cook.getName() : null);
        isFavorite = cook != null && cook.isFavorite();
        if (savedInstanceState == null) {
            DetailFgt detailFgt = DetailFgt.newInstance(cook);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_content, detailFgt).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favor, menu);
        if (isFavorite) {
            menu.getItem(0).setIcon(R.mipmap.favorite);
            menu.getItem(0).setTitle(getString(R.string.cancel_favor));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_item_favor) {
            CookDao cookDao = new CookDao(this);
            if (cookDao.doFavor(mCookId, isFavorite)) {
                if (isFavorite) {
                    item.setIcon(R.mipmap.not_favorite);
                    item.setTitle(R.string.cancel_favor);
                } else {
                    item.setIcon(R.mipmap.favorite);
                    item.setTitle(getString(R.string.favor));
                    Toast.makeText(this, R.string.favor_succeed, Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;
            }
        }
        return true;
    }
}