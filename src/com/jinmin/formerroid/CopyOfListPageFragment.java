package com.jinmin.formerroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CopyOfListPageFragment extends Fragment {

   public static final String ARG_SECTION_NUMBER = "section_number";

   private Context _context;

   public CopyOfListPageFragment() {
   }

   public CopyOfListPageFragment(Context _context) {
      this._context = _context;
   }

   public static Fragment newInstance(Context context) {
      CopyOfListPageFragment f = new CopyOfListPageFragment(context);
      return f;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.listview_layout, null);
      return viewGroup;
   }
}
