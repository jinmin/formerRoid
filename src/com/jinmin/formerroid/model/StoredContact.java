package com.jinmin.formerroid.model;

import android.content.ContentValues;

public class StoredContact {
   private int id;
   private String name;
   private String tel;
   private int ring_mode;
   private int ring_mode_option;

   public StoredContact(String name, String tel, int ring_mode, int ring_mode_option) {
      this.name = name;
      this.tel = tel;
      this.ring_mode = ring_mode;
      this.ring_mode_option = ring_mode_option;
   }

   public StoredContact(int id, String name, String tel, int ring_mode, int ring_mode_option) {
      this.id = id;
      this.name = name;
      this.tel = tel;
      this.ring_mode = ring_mode;
      this.ring_mode_option = ring_mode_option;
   }

   public ContentValues toContentValues() {
      ContentValues contentValues = new ContentValues();
      // contentValues.put("id", "");
      contentValues.put("name", name);
      contentValues.put("tel", tel);
      contentValues.put("ring_mode", ring_mode);
      contentValues.put("ring_mode_option", ring_mode_option);
      return contentValues;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTel() {
      return tel;
   }

   public void setTel(String tel) {
      this.tel = tel;
   }

   public int getRing_mode() {
      return ring_mode;
   }

   public void setRing_mode(int ring_mode) {
      this.ring_mode = ring_mode;
   }

   public int getRing_mode_option() {
      return ring_mode_option;
   }

   public void setRing_mode_option(int ring_mode_option) {
      this.ring_mode_option = ring_mode_option;
   }

}
