package com.jinmin.formerroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

import com.jinmin.formerroid.adapters.ViewPagerAdapter;

public class FormerRoidActivity extends FragmentActivity implements ActionBar.TabListener
{

	ViewPagerAdapter viewPagerAdapter;

	ViewPager mViewPager;

	int currentPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.former_roid_layout);

		viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(viewPagerAdapter);

		// addChangeTabListener
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				currentPosition = position;
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// set tab name from viewPagerAdapter
		for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(viewPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		switch (requestCode) {
			case ListPageFragment.CONTACT_REQUEST_CODE:
				if (resultCode == Activity.RESULT_OK) {

					Cursor cursor = getContentResolver().query(intent.getData(), new String[]{ ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER }, null, null, null);
					cursor.moveToFirst();
					if (cursor != null) {
						ListPageFragment fragment = (ListPageFragment)viewPagerAdapter.getItem(currentPosition);
						Log.d("CONTACT NAME ==>", cursor.getString(0));
						Log.d("CONTACT Phone Number ==>", cursor.getString(1));
						String name = cursor.getString(0);
						String phoneNumber = cursor.getString(1);
						cursor.close();

						fragment.setContactData(name, phoneNumber);
					}
				}
				break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.former_roid_layout, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
	}
}
