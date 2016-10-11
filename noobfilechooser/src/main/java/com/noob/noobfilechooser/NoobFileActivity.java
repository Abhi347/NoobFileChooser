package com.noob.noobfilechooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.noob.noobfilechooser.fragments.NoobDrawerFragment;
import com.noob.noobfilechooser.fragments.NoobFileFragment;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.managers.NoobPermissionManager;
import com.noob.noobfilechooser.managers.NoobPrefsManager;
import com.noob.noobfilechooser.managers.NoobSAFManager;
import com.noob.noobfilechooser.models.NoobStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoobFileActivity extends AppCompatActivity {

    @BindView(R2.id.drawer_noob_activity)
    DrawerLayout mDrawerLayout;

    NoobFileFragment mNoobFileFragment;
    NoobDrawerFragment mNoobDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noob_file);
        ButterKnife.bind(this);

        setupFragments();

        NoobPrefsManager.getInstance().init(this);
        if (checkPermissions()) {
            showFileFragment();
        }
    }

    protected void setupFragments() {
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, getNoobFileFragment())
                .commitAllowingStateLoss();*/
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_container, getNoobDrawerFragment())
                .commitAllowingStateLoss();
        getNoobDrawerFragment().setStorageItemClickListener(new OnRecyclerViewItemClick<NoobStorage>() {
            @Override
            public void onClick(NoobStorage model, View view) {
                if (model != null)
                    getNoobFileFragment().load(model, true);
            }

            @Override
            public void onLongClick(NoobStorage model, View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mNoobFileFragment == null || !mNoobFileFragment.onBackPressed())
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            boolean result = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                result = NoobSAFManager.onActivityResult(this, requestCode, data);
                if (result && checkPermissions()) {
                    showFileFragment();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermissions()) {
            showFileFragment();
        }
    }

    protected void showFileFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container, getNoobFileFragment())
                            .commitAllowingStateLoss();

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

                loadStorage();
                //if (NoobPrefsManager.getInstance().getNoobStorageList().size() > 0)
                //getNoobFileFragment().buildAndLoad(NoobFileActivity.this, NoobPrefsManager.getInstance().getNoobStorageList().get(0));


            }
        });
    }

    //Update storage list in the Drawer Fragment
    public void loadStorage() {
        NoobPrefsManager.getInstance().loadStorage();
        getNoobDrawerFragment().loadStorage();
    }

    protected boolean checkPermissions() {
        return NoobPermissionManager.takeRunTimePermissions(this);
        /*) {
            Uri cardUri = NoobPrefsManager.getInstance().getSDCardUri();
            if (cardUri == null) {
                NoobSAFManager.takeCardUriPermission(this);
                return false;
            }
            return true;
        }
        return false;*/
    }

    protected NoobFileFragment getNoobFileFragment() {
        if (mNoobFileFragment == null)
            setNoobFileFragment(NoobFileFragment.newInstance());
        return mNoobFileFragment;
    }

    protected void setNoobFileFragment(NoobFileFragment noobFileFragment) {
        if (noobFileFragment != null) {
            mNoobFileFragment = noobFileFragment;
        }
    }

    protected NoobDrawerFragment getNoobDrawerFragment() {
        if (mNoobDrawerFragment == null)
            setNoobDrawerFragment(NoobDrawerFragment.newInstance());
        return mNoobDrawerFragment;
    }

    protected void setNoobDrawerFragment(NoobDrawerFragment noobDrawerFragmentParam) {
        mNoobDrawerFragment = noobDrawerFragmentParam;
    }

    public void onAddStorageClick(View view) {
        NoobSAFManager.takeCardUriPermission(this);
    }
}
