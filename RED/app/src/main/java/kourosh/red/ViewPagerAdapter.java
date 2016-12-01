package kourosh.red;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 6;
    // Tab Titles
    private String tabtitles[] = new String[] { "Volenteers", "Executives","Meetings","Events","Presentations","Equipments", };
    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open Executives.java
            case 0:
                Volunteers fragmenttab1 = new Volunteers();
                return fragmenttab1;

            // Open Volunteers.java
            case 1:
                Executives fragmenttab2 = new Executives();
                return fragmenttab2;

            // Open Events.java
            case 2:
                Meetings fragmenttab3 = new Meetings();
                return fragmenttab3;

            case 3:
                Events fragmenttab4 = new Events();
                return fragmenttab4;
            case 4:
                Presentations fragmenttab5 = new Presentations();
                return fragmenttab5;
            case 5:
                Equipments fragmenttab6 = new Equipments();
                return fragmenttab6;

        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}