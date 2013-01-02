package com.jinmin.formerroid.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jinmin.formerroid.DummyFragment;
import com.jinmin.formerroid.ListPageFragment;
import com.jinmin.formerroid.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

   private Context _context;

   public ViewPagerAdapter(Context context, FragmentManager fm) {
      super(fm);
      _context = context;
   }

   @Override
   public Fragment getItem(int position) {
      Fragment f = new Fragment();
      Bundle args = new Bundle();
      switch (position) {
         case 0:
            f = ListPageFragment.newInstance(_context);
            break;
         case 1:
            // f = MapLayerFragment.newInstance(_context);
            // break;
         case 2:
            f = new DummyFragment();
            args.putInt(DummyFragment.ARG_SECTION_NUMBER, position + 1);
            f.setArguments(args);
            break;
      }
      return f;

   }

   @Override
   public int getCount() {
      return 3;
   }

   @Override
   public CharSequence getPageTitle(int position) {
      switch (position) {
         case 0:
            return _context.getString(R.string.title_section1).toUpperCase();
         case 1:
            return _context.getString(R.string.title_section2).toUpperCase();
         case 2:
            return _context.getString(R.string.title_section3).toUpperCase();
      }
      return null;
   }
}
