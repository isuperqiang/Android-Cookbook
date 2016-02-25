package com.silence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.silence.caipu.R;
import com.silence.dao.CookDao;
import com.silence.fragment.CategoryFgt;
import com.silence.fragment.CookFgt;
import com.silence.fragment.FavorFgt;
import com.silence.fragment.IndexFgt;
import com.silence.fragment.MoreFgt;
import com.silence.fragment.SearchFgt;
import com.silence.pojo.Category;
import com.silence.pojo.Cook;
import com.silence.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        CategoryFgt.onCategoryListener, SearchView.OnQueryTextListener, SearchFgt.onCookClickListener,
        CookFgt.onCookClickListener, FavorFgt.onCookClickable, IndexFgt.onCookClickListener {

    private CategoryFgt mCategoryFgt;
    private FavorFgt mFavorFgt;
    private IndexFgt mIndexFgt;
    private MoreFgt mMoreFgt;
    private SearchFgt mSearchFgt;
    private CookFgt mCookFgt;
    private List<Fragment> mFragments;
    private SearchView mSearchView;
    private RadioGroup mRadioGroup;
    private ActionBar mActionBar;
    private CookDao mCookDao;
    private FragmentManager mFragmentManager;
    private String mTitle;
    private int mCurIndex = 1;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initViews();
    }

    private void initViews() {
        mFragments = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            mFragments.add(null);
        }
        mActionBar = getSupportActionBar();
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_gp_tab);
        mRadioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
    }

    private void hideAllFgt(FragmentTransaction transaction) {
        for (int i = 0; i < 4; i++) {
            Fragment fragment = mFragments.get(i);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
        if (mCookFgt != null) {
            transaction.detach(mCookFgt);
        }
        if (mSearchFgt != null && mSearchFgt.isVisible()) {
            transaction.hide(mSearchFgt);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mSearchView != null && mSearchView.getVisibility() == View.INVISIBLE) {
            mSearchView.setVisibility(View.VISIBLE);
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFgt(transaction);
        switch (checkedId) {
            case R.id.radio_btn_index:
                mTitle = ((RadioButton) group.getChildAt(0)).getText().toString();
                if (mIndexFgt == null) {
                    mIndexFgt = new IndexFgt();
                    mFragments.set(0, mIndexFgt);
                    transaction.add(R.id.main_content, mIndexFgt);
                } else {
                    transaction.show(mIndexFgt);
                }
                mCurIndex = 0;
                break;
            case R.id.radio_btn_cat:
                mTitle = ((RadioButton) group.getChildAt(1)).getText().toString();
                if (mCategoryFgt == null) {
                    mCategoryFgt = new CategoryFgt();
                    mFragments.set(1, mCategoryFgt);
                    transaction.add(R.id.main_content, mCategoryFgt);
                } else {
                    transaction.show(mCategoryFgt);
                }
                mCurIndex = 1;
                break;
            case R.id.radio_btn_fav:
                mTitle = ((RadioButton) group.getChildAt(2)).getText().toString();
                if (mFavorFgt == null) {
                    mFavorFgt = new FavorFgt();
                    mFragments.set(2, mFavorFgt);
                    transaction.add(R.id.main_content, mFavorFgt);
                } else {
                    transaction.show(mFavorFgt);
                    mFavorFgt.refresh();
                }
                mCurIndex = 2;
                break;
            case R.id.radio_btn_more:
                mTitle = ((RadioButton) group.getChildAt(3)).getText().toString();
                if (mMoreFgt == null) {
                    mMoreFgt = new MoreFgt();
                    mFragments.set(3, mMoreFgt);
                    transaction.add(R.id.main_content, mMoreFgt);
                } else {
                    transaction.show(mMoreFgt);
                }
                mCurIndex = 3;
                break;
        }
        transaction.commit();
        setTitle(mTitle);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) item.getActionView();
        if (mSearchView != null) {
            mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
            mSearchView.setSubmitButtonEnabled(false);
            mSearchView.setQueryHint(getString(R.string.search_hint));
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRadioGroup.setVisibility(View.GONE);
                    mActionBar.setDisplayHomeAsUpEnabled(true);
                    mCookDao = new CookDao(MainActivity.this);
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    hideAllFgt(transaction);
                    if (mSearchFgt == null) {
                        mSearchFgt = new SearchFgt();
                        transaction.add(R.id.main_content, mSearchFgt);
                    } else {
                        transaction.show(mSearchFgt);
                    }
                    transaction.commit();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (mSearchFgt != null && mSearchFgt.isVisible()) {
                hideSearchView(transaction);
            } else if (mCookFgt != null && mCookFgt.isVisible()) {
                hideCookFgt(transaction);
            }
            setTitle(mTitle);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideCookFgt(FragmentTransaction transaction) {
        mSearchView.setVisibility(View.VISIBLE);
        transaction.detach(mCookFgt);
        transaction.show(mCategoryFgt);
    }

    private void hideSearchView(FragmentTransaction transaction) {
        mRadioGroup.setVisibility(View.VISIBLE);
        mSearchView.clearFocus();
        mSearchView.setQuery(null, false);
        mSearchView.setIconified(true);
        transaction.hide(mSearchFgt);
        transaction.show(mFragments.get(mCurIndex));
    }

    @Override
    public void getCategory(Category category) {
        mSearchView.setVisibility(View.INVISIBLE);
        setTitle(category.getTitle());
        mActionBar.setDisplayHomeAsUpEnabled(true);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFgt(transaction);
        if (mCookFgt != null) {
            mCookFgt.refresh(category);
            transaction.attach(mCookFgt);
        } else {
            mCookFgt = CookFgt.newInstance(category);
            transaction.add(R.id.main_content, mCookFgt);
        }
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Cook> cookList = null;
        if (!TextUtils.isEmpty(newText)) {
            cookList = mCookDao.findCooks(newText);
        }
        mSearchFgt.refresh(cookList);
        return true;
    }

    @Override
    public void getCook(int cookId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.COOK_KEY, cookId);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (mSearchFgt != null && mSearchFgt.isVisible()) {
                hideSearchView(transaction);
                setTitle(mTitle);
                mActionBar.setDisplayHomeAsUpEnabled(false);
            } else if (mCookFgt != null && mCookFgt.isVisible()) {
                hideCookFgt(transaction);
                mActionBar.setDisplayHomeAsUpEnabled(false);
            } else if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.exit_hint, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            transaction.commit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}