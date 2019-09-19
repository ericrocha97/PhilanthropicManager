package cf.ericrocha.philanthropicmanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chirag on 30-Jul-17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mNoOfTabs = behavior;
    }



    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                return new cashier();
            case 1:
                return new institution();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
