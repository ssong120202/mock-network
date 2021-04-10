package com.example.mocknetwork.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mocknetwork.R;

public class NavigationUtil {
    private static NavigationUtil navigationUtil;
    private FragmentManager mFragmentManager;

    public static NavigationUtil getInstance() {
        if (navigationUtil == null) {
            navigationUtil = new NavigationUtil();
        }
        return navigationUtil;
    }

    public void pushOrPopFragment(Fragment fragment, int animation, boolean isPushFragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (animation != 0) {
            if (isPushFragment) {
                ft.setCustomAnimations(animation, 0);
            } else {
                ft.setCustomAnimations(0, animation);
            }
        }

        ft.replace(R.id.modal_container, fragment);
        ft.commitNow();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }
}
