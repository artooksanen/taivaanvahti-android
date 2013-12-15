package fi.jklsirius.taivaanvahti;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HavaintoActivity extends Activity {
    /** Called when the activity is first created. */
    
    String[] image;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.havainto);
        //setContentView(R.layout.image);
       image1 = (ImageView) findViewById(R.id.imageView1);
       image2 = (ImageView) findViewById(R.id.imageView2);
       image3 = (ImageView) findViewById(R.id.imageView3);
       image4 = (ImageView) findViewById(R.id.imageView4);
              // ImageView image3 = (ImageView) findViewById(R.id.imageView3);
       Intent i = getIntent();
       // getting attached intent data
       String id = i.getStringExtra("id");
       // displaying selected product name
       TextView txtPosition = (TextView) findViewById(R.id.txtPosition);
        txtPosition.setText("http://www.taivaanvahti.fi/observations/show/"+id);
       
        //http://www.ursa.fi/~obsbase/search.php?format=json&id=7105
        
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { "http://www.ursa.fi/~obsbase/search.php?format=json&id="+id });
        DownloadCommentsTask comments = new DownloadCommentsTask();
        comments.execute(new String[] { "http://www.ursa.fi/~obsbase/comment_search.php?format=json&observation="+id });
      }
    
    public void imageClick1(View view) {  
    	  //Implement image click function  
    	
      	 // selected item
          String url = image[0];

          // Launching new Activity on selecting single List Item
          Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
          // sending data to new activity
          i.putExtra("url", url);
          startActivity(i);
      	
      	} 
    
    public void imageClick2(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = image[1];

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 

    public void imageClick3(View view) {  
    	  //Implement image click function  
    	
      	 // selected item
          String url = image[2];

          // Launching new Activity on selecting single List Item
          Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
          // sending data to new activity
          i.putExtra("url", url);
          startActivity(i);
      	
      	} 
    
    public void imageClick4(View view) {  
    	  //Implement image click function  
    	
      	 // selected item
          String url = image[3];

          // Launching new Activity on selecting single List Item
          Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
          // sending data to new activity
          i.putExtra("url", url);
          startActivity(i);
      	
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

        @Override
        protected void onPostExecute(String result) {
	           	String[] values = new String[10];
             	String json = result;
        	
	      URLConnection tc;
        	        String line;
        	        JSONObject jo;
        	        JSONObject jo1=null;
        	        JSONArray ja;
        	        String thumbnails="";
        	        String images="";
        	        
        			try {
        			     jo = new JSONObject(result);
        			
        	             ja = jo.getJSONArray("observation");
        	             jo1 = (JSONObject) ja.get(0);
        	   //       listItems.add( jo1.getString("title")+"\n"+jo1.getString("start")+" "+jo1.getString("city")+"\n"+jo1.getString("user")+" "+jo1.getString("team") );
        	   
        	             TextView otsikko = (TextView) findViewById(R.id.otsikko);
        	             otsikko.setText(jo1.getString("title"));
        	             TextView havaitsija = (TextView) findViewById(R.id.havaitsija);
        	             String team=jo1.getString("team");
        	             if (!team.equals("")) team=", "+team;
        	             havaitsija.setText(jo1.getString("user")+team);
        	             TextView havainto = (TextView) findViewById(R.id.havainto);
        	             havainto.setText(jo1.getString("description"));
        	             TextView ttiedot = (TextView) findViewById(R.id.ttiedot);
        	             ttiedot.setText(jo1.getString("equipment"));
        	             TextView copyright = (TextView) findViewById(R.id.copyright);
        	             copyright.setText("(c): "+jo1.getString("user"));
        	             TextView aikajapaikka = (TextView) findViewById(R.id.aikajapaikka);
        	             aikajapaikka.setText(jo1.getString("start")+" "+jo1.getString("city"));
        			
        	             thumbnails= jo1.getString("thumbnails");
        	             images= jo1.getString("images");
        	             
        			} catch (JSONException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (Exception e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          
        	                         	       
        		       String[] th;
        		       String delimiter = ",";
        		       /* given string will be split by the argument delimiter provided. */
        		       th = thumbnails.split(delimiter);
        		       
        		       /* given string will be split by the argument delimiter provided. */
        		       image = images.split(delimiter);
        		       
        		       URL url1 = null;
        		       URL url2 = null;
        		       URL url3 = null;
        		       URL url4 = null;
         				
        		       	try {
        		       		if (th.length==1) {
       						if(!th[0].equals("")) {
       							url1 = new URL(th[0]);
       							Log.v("url1", th[0]);

       							new DownloadFilesTask().execute(url1);	
       						 	}
      						}
        		       		if (th.length==2) {
        		       			Log.v("url1", th[0]);
               		    		url1 = new URL(th[0]);
               		    		Log.v("url2", th[1]);
               		    		url2 = new URL(th[1]);
          						new DownloadFilesTask().execute(url1,url2);
                 		       	 } 
        		       		if (th.length==3) {
        		       			Log.v("url1", th[0]);
               		    		url1 = new URL(th[0]);
               		    		Log.v("url2", th[1]);
               		    		url2 = new URL(th[1]);
               		    		Log.v("url3", th[2]);
                   		    	url3 = new URL(th[2]);
          							new DownloadFilesTask().execute(url1,url2,url3);
                 		       	 } 
        		       		if (th.length>3) {
        		       			Log.v("url1", th[0]);
               		    		url1 = new URL(th[0]);
               		    		Log.v("url2", th[1]);
               		    		url2 = new URL(th[1]);
               		    		Log.v("url3", th[2]);
                   		    	url3 = new URL(th[2]);
                   		    	Log.v("url4", th[3]);
                   		    	url4 = new URL(th[3]);
          							new DownloadFilesTask().execute(url1,url2,url3,url4);
                 		       	 } 
             		       	 
        		       	 
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		       
          
        }
      
    
    
    }

    
    
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    	Bitmap[] bitmap;
        
        protected Long doInBackground(URL... urls) {
            
        	int count = urls.length;
            long totalSize = 0;
            bitmap = new Bitmap[count];
            for (int i = 0; i < count; i++) {
            	try {
            		Log.v("downloading", urls[i].toString());
            		bitmap[i] = BitmapFactory.decodeStream(new FlushedInputStream((InputStream)urls[i].getContent()));
//					bitmap[i] = BitmapFactory.decodeStream((InputStream)urls[i].getContent());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             	// Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return (long) count;
        }

      
        protected void onPostExecute(Long result) {
      //      showDialog("Downloaded " + result + " bytes");
        	if (result>0) {
        		image1.setImageBitmap(bitmap[0]);
        		image1.setClickable(true);
        	}
        	if (result>1) {
        		image2.setImageBitmap(bitmap[1]);
        		image2.setClickable(true);
        	}
        	if (result>2) {
        		image3.setImageBitmap(bitmap[2]);
        		image3.setClickable(true);
        	}
        	if (result>3) {
        		image4.setImageBitmap(bitmap[3]);
        		image4.setClickable(true);
        	}
        	
        	
    		
        }
    }
    
    
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                      int byteValue = read();
                      if (byteValue < 0) {
                          break;  // we reached EOF
                      } else {
                          bytesSkipped = 1; // we read one byte
                      }
               }
               totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    private class DownloadCommentsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
          String response = "";
          for (String url : urls) {
        	Log.v("download commentsURL", url);   
        	    
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

        @Override
        protected void onPostExecute(String result) {
	           	String[] values = new String[10];
             	String json = result;
        	
//	      URLConnection tc;
        	        String line;
        	        JSONObject jo;
        	        JSONObject jo1=null;
        	        JSONArray ja;
        	        String thumbnails="";
        	        String images="";
        	        
        			try {
       			         if(result.startsWith("{\"comment\":")) {
       			        	 Log.v("comments", result);
       			        	 jo = new JSONObject(result);
       			         	 ja = jo.getJSONArray("comment");
       			         	 String kommentit="";
       			         	 for (int i = ja.length(); i > 0; i--) {
       			         		jo1 = (JSONObject) ja.get(i-1);
       			         		kommentit+=jo1.getString("user")+": "+jo1.getString("text")+"\n\n";
       			         		}
       			         	 TextView otsikko = (TextView) findViewById(R.id.kommentit);
       			         	 otsikko.setText(kommentit);
       			      }
        			        
        			} catch (JSONException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (Exception e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          
        	                         	       
        }
        		                 
        }



}
