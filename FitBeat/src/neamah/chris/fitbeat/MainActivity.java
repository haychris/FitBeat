package neamah.chris.fitbeat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity  {

// Songs list
public ArrayList<HashMap<String,String>> songsList = new ArrayList<HashMap<String, String>>();
ListView musiclist;
 Cursor mCursor;
 int songTitle;
 int count;
 int songPath;

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	ArrayList<HashMap<String, String>> tempSong = new ArrayList<HashMap<String, String>>();


	SongsManager plm = new SongsManager();


    // get all songs from sdcard
    this.songsList = plm.getPlayList(this);

    // looping through playlist
    for (int i = 0; i < songsList.size(); i++) {
        // creating new HashMap
        HashMap<String, String> song = songsList.get(i);

        // adding HashList to ArrayList
        tempSong.add(song);

    }
 // selecting single ListView item
    ListView lv = (ListView) findViewById(android.R.id.list);
    // Adding menuItems to ListView
   ListAdapter adapter = new SimpleAdapter(this, tempSong,
                     android.R.layout.simple_list_item_1, new String[] {      "songPath", "songTitle" }, new int[] {
                     android.R.id.text1});

   lv.setAdapter(adapter);


    

    // listening to single listitem click
    lv.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {

            // getting listitem index
            int songIndex = position;
            // Starting new intent
            Intent in = new Intent(getApplicationContext(),
                   MainActivity.class);
            Log.d("TAG","onItemClick");
            // Sending songIndex to PlayerActivity
            in.putExtra("songPath", songIndex);
            setResult(100, in);
            // Closing PlayListView
            finish();
        }
 
    });
	}
	public class SongsManager {
		private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		
		
		public ArrayList<HashMap<String, String>> getPlayList(Context c) {
		
		
		
		
		    final Cursor mCursor = c.getContentResolver().query(
		            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		            new String[] { MediaColumns.TITLE, MediaColumns.DATA, AudioColumns.ALBUM }, null, null,
		            "LOWER(" + MediaColumns.TITLE + ") ASC");
		    final Cursor mCursor2 = c.getContentResolver().query(
		            MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
		            new String[] { MediaColumns.TITLE, MediaColumns.DATA, AudioColumns.ALBUM }, null, null,
		            "LOWER(" + MediaColumns.TITLE + ") ASC");
		
		    String songTitle = "";
		    String songPath = "";	
		
		    /* run through all the columns we got back and save the data we need into the arraylist for our listview*/
		    if (mCursor.moveToFirst()) {
		        do {
		
		
		            songTitle = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaColumns.TITLE));
		
		            songPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaColumns.DATA));
		            if (songPath.contains(".mp3")) {
			            HashMap<String, String> song = new HashMap<String, String>();
			
			            song.put("songTitle", songTitle);
			            song.put("songPath", songPath);
			
			
			            songsList.add(song);
		            }
		
		        } while (mCursor.moveToNext());
		
		    }   
		
		    mCursor.close(); //cursor has been consumed so close it
		    
		    if (mCursor2.moveToFirst()) {
		        do {
		
		
		            songTitle = mCursor2.getString(mCursor2.getColumnIndexOrThrow(MediaColumns.TITLE));
		
		            songPath = mCursor2.getString(mCursor2.getColumnIndexOrThrow(MediaColumns.DATA));
		            if (songPath.contains(".mp3")) {
			            HashMap<String, String> song = new HashMap<String, String>();
			
			            song.put("songTitle", songTitle);
			            song.put("songPath", songPath);
			
			
			            songsList.add(song);
		            }
		
		        } while (mCursor2.moveToNext());
		
		    }
		    mCursor2.close();
		    return songsList;
		}	
	}
}