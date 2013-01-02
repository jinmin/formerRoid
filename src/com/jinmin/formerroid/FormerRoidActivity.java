package com.jinmin.formerroid;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.jinmin.formerroid.adapters.ViewPagerAdapter;

public class FormerRoidActivity extends FragmentActivity implements ActionBar.TabListener {

   ViewPagerAdapter viewPagerAdapter;

   ViewPager mViewPager;

   @Override
   public void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);
      setContentView(R.layout.former_roid_layout);

      viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

      // Set up the action bar.
      final ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

      // Set up the ViewPager with the sections adapter.
      mViewPager = (ViewPager) findViewById(R.id.pager);
      mViewPager.setAdapter(viewPagerAdapter);

      mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
         @Override
         public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
         }
      });

      for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
         actionBar.addTab(actionBar.newTab().setText(viewPagerAdapter.getPageTitle(i)).setTabListener(this));
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.former_roid_layout, menu);
      return true;
   }

   @Override
   public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
   }

   @Override
   public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
      mViewPager.setCurrentItem(tab.getPosition());
   }

   @Override
   public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
   }
}
