package fi.jklsirius.taivaanvahti;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
	 
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


	import android.view.MenuItem;

	 
	import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

	public class MainActivity extends ListActivity {

		JSONObject jo1;
		JSONArray ja;
		Context context;
	 	String[] values=null;
	 	boolean[] comment=null;
	 	boolean[] kuvat=null;
	 	 private LinearLayout linProgressBar;
	     
			
		 /** Called when the activity is first created. */
	    @SuppressWarnings({ "rawtypes", "unchecked" })
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        context = getApplicationContext();

	        setContentView(R.layout.activity_main);
	  //      Toast.makeText(context, "Ladataan havaintoja...", Toast.LENGTH_LONG).show();
	        
	        linProgressBar = (LinearLayout) findViewById(R.id.lin_progress_bar);
	        linProgressBar.setVisibility(View.VISIBLE);
	        
	   //     setListAdapter(new ArrayAdapter(this, R.layout.list_item, this.fetchObservations()));       
	            DownloadWebPageTask task = new DownloadWebPageTask();
	            task.execute(new String[] { "http://www.ursa.fi/~obsbase/search_2.php?format=json&limit=50" });
	        
	        ListView lv = getListView();
	        
	        // listening to single list item on click
	        lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	 
	              // selected item
//	              String product = ((TextView) view).getText().toString();
	 
	              // Launching new Activity on selecting single List Item
	              Intent i = new Intent(getApplicationContext(), HavaintoActivity.class);
	              // sending data to new activity
	              try {
					jo1 = (JSONObject) ja.get(position);
				
	              i.putExtra("id", ""+jo1.getString("id"));
	              } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					    Toast.makeText(context, "Virhe latauksessa!", Toast.LENGTH_LONG).show();
						
					}
	              startActivity(i);
	          }});
	 
	        }
	    
	    class IconicAdapter extends ArrayAdapter<String> {
	    	IconicAdapter() {
	    	super(MainActivity.this, R.layout.row, R.id.label, values);
	    	}
	    	@Override
	    	public View getView(int position, View convertView,
	    	ViewGroup parent) {
	    	View row=super.getView(position, convertView, parent);
	    	ImageView cameraicon=(ImageView)row.findViewById(R.id.icon);
	    	ImageView commenticon=(ImageView)row.findViewById(R.id.icon2);
	 	   // 	TextView com=(TextView)row.findViewById(R.id.comments);
	   // 	com.setText(comment[position]);
	     	if (kuvat[position]) {
		    	cameraicon.setImageResource(R.drawable.ico_camera_sm);
		    	}
		    	else {
		    	cameraicon.setImageResource(R.drawable.blankicon);
		    	}
	     	if (comment[position]) {
		    	commenticon.setImageResource(R.drawable.comment_bubble);
		    	}
		    	else {
		    	commenticon.setImageResource(R.drawable.blankicon);
		    	}
		    	return(row);
	    	}
	    	}
	  
	    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	          String response = "";
	          for (String url : urls) {
	            DefaultHttpClient client = new DefaultHttpClient();
	            HttpGet httpGet = new HttpGet(url);
	            try {
	              HttpResponse execute = client.execute(httpGet);
	              InputStream content = execute.getEntity().getContent();

	              BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	              String s = "";
	              while ((s = buffer.readLine()) != null) {
	                response += s;
	              }

	            } catch (Exception e) {
	              e.printStackTrace();
	            }
	          }
	          return response;
	        }

	        String fmtaika(String aika) throws ParseException {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = formatter.parse(aika);
                Date now = new Date();
                if(now.getDate()==date.getDate()&&now.getMonth()==date.getMonth()&&now.getYear()==date.getYear())
                    return ""+date.getHours()+":"+(date.getMinutes()<10?"0":"")+date.getMinutes();
                else
                    return ""+date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900);
                }
	        
	        
	        @Override
	        protected void onPostExecute(String result) {
	             	String json = result;
	             	String images="";
	             	String comments="";
	             	String aika="";
	             	String city="";
	                Time today = new Time(Time.getCurrentTimezone());
	             	int icount=0;
	        	
	             	if(!result.equals("")) {
	        	try {
	            JSONObject jo = new JSONObject(json);
	            ja = jo.getJSONArray("observation");
	            comment = new boolean[ja.length()];
	            values = new String[ja.length()];
		        kuvat = new boolean[ja.length()];
	            for (int i = 0; i < ja.length(); i++) {
	                  jo1 = (JSONObject) ja.get(i);
	                  images=jo1.getString("images");
	                  if(!images.equals("")) {
	                	  icount=images.split(",").length;
	                  } else
	                	  icount=0;
	                  today.setToNow();
	                  aika=fmtaika(jo1.getString("start"));
	                  if(!jo1.getString("city").equals("")) {
	                	  city = " - "+jo1.getString("city");
	                  } else
	                	  city="";
//	                  values[i]=jo1.getString("title")+"\n"+aika+jo1.getString("city")+"\n"+jo1.getString("user")+" "+jo1.getString("team")+" (kuvia "+icount+")";
	                  values[i]=aika+" "+jo1.getString("title")+"\n"+jo1.getString("user")+city;
	            	  kuvat[i]=icount>0;
	                  comments=jo1.getString("comments");
	                  if(comments.equals("0")) {
		            	  comment[i]=false;
	                  }else
	            	  comment[i]=true;
	            }
	            	  
	        	
	        	 } catch (JSONException e) {
	                 Toast.makeText(MainActivity.this, "JSONException", Toast.LENGTH_LONG).show(); 
	                 e.printStackTrace();
	             } catch (Exception e) {
	                 Toast.makeText(MainActivity.this, "Exception", Toast.LENGTH_LONG).show(); 
	             	e.printStackTrace();
	                 
	             }
	        	ListView lv = getListView();

//	            setListAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, values));       
	//            setListAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.row, R.id.label, values));       
	          	setListAdapter(new IconicAdapter());
	            linProgressBar.setVisibility(View.INVISIBLE);
	      	  
	             	}
	        }
	      }

	    
	    
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	  //      getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }
	    
	    
	}

