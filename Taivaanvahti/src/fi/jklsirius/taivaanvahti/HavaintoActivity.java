package fi.jklsirius.taivaanvahti;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    ImageView image5;
    ImageView image6;
    ImageView image7;
    ImageView image8;

   // URL url1 = null;
    String url1 = null;
    String url2 = null;
    String url3 = null;
    String url4 = null;
    String url5 = null;
    String url6 = null;
    String url7 = null;
    String url8 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.havainto);
        //setContentView(R.layout.image);
       image1 = (ImageView) findViewById(R.id.imageView1);
       image2 = (ImageView) findViewById(R.id.imageView2);
       image3 = (ImageView) findViewById(R.id.imageView3);
       image4 = (ImageView) findViewById(R.id.imageView4);
       image5 = (ImageView) findViewById(R.id.imageView5);
       image6 = (ImageView) findViewById(R.id.imageView6);
       image7 = (ImageView) findViewById(R.id.imageView7);
       image8 = (ImageView) findViewById(R.id.imageView8);
              // ImageView image3 = (ImageView) findViewById(R.id.imageView3);
       Intent i = getIntent();
       // getting attached intent data
       String id = i.getStringExtra("id");
       // displaying selected product name
       TextView txtPosition = (TextView) findViewById(R.id.txtPosition);
        txtPosition.setText("http://www.taivaanvahti.fi/observations/show/"+id);
       
        //http://www.ursa.fi/~obsbase/search.php?format=json&id=7105
        
        DownloadWebPageTask task = new DownloadWebPageTask();
//        task.execute(new String[] { "http://www.ursa.fi/~obsbase/search.php?format=json&id="+id });
        task.execute(new String[] { "http://www.taivaanvahti.fi/app/api/search.php?format=json&id="+id });
        DownloadCommentsTask comments = new DownloadCommentsTask();
//        comments.execute(new String[] { "http://www.ursa.fi/~obsbase/comment_search.php?format=json&observation="+id });
        comments.execute(new String[] { "http://www.taivaanvahti.fi/app/api/comment_search.php?format=json&observation="+id });
      }
    
    public void imageClick1(View view) {  
    	  //Implement image click function  
    	
      	 // selected item
          String url = url1.toString();

          // Launching new Activity on selecting single List Item
          Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
          // sending data to new activity
          i.putExtra("url", url);
          startActivity(i);
      	
      	} 
    
    public void imageClick2(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url2.toString();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 

    public void imageClick3(View view) {  
    	  //Implement image click function  
    	
      	 // selected item
          String url = url3.toString();

          // Launching new Activity on selecting single List Item
          Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
          // sending data to new activity
          i.putExtra("url", url);
          startActivity(i);
      	
      	} 
    
    public void imageClick4(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url4.toString();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 
  
    public void imageClick5(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url4.toString();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 
  
    public void imageClick6(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url6.toString();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 
  
    public void imageClick7(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url7.toString();

        // Launching new Activity on selecting single List Item
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        // sending data to new activity
        i.putExtra("url", url);
        startActivity(i);
    	
    	} 
  
    public void imageClick8(View view) {  
  	  //Implement image click function  
  	
    	 // selected item
        String url = url8.toString();

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
        	        JSONArray images=null;
        	        
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
        	             havaitsija.setText(jo1.getJSONArray("user").getString(0)+team);
        	             TextView havainto = (TextView) findViewById(R.id.havainto);
        	             havainto.setText(jo1.getString("description"));
        	             TextView ttiedot = (TextView) findViewById(R.id.ttiedot);
        	             ttiedot.setText(jo1.getString("equipment"));
        	             TextView copyright = (TextView) findViewById(R.id.copyright);
        	             copyright.setText("(c): "+jo1.getJSONArray("user").getString(0));
        	             TextView aikajapaikka = (TextView) findViewById(R.id.aikajapaikka);
        	             aikajapaikka.setText(jo1.getString("start")+" "+jo1.getString("city"));
        			
        	             thumbnails= jo1.getString("thumbnails");
        	             images= jo1.getJSONArray("images");
        	             
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
        		      // th = thumbnails.split(delimiter);
        		     //th = images.split(delimiter.toString());
        		     
          		       
        		       /* given string will be split by the argument delimiter provided. */
        		      // image = images.split(delimiter);
        		       
        				
        		       	try {
        		       		if (images.length()>0) {
   							    url1 = images.optString(0);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url1).into(image1);
            		       		} 
        		       		if (images.length()>1) {
   							    url2 = images.optString(1);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url2).into(image2);
            		       		} 
        		       		if (images.length()>2) {
   							    url3 = images.optString(2);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url3).into(image3);
            		       		} 
        		       		if (images.length()>3) {
   							    url4 = images.optString(3);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url4).into(image4);
            		       		} 
        		       		if (images.length()>4) {
   							    url5 = images.optString(4);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url5).into(image5);
            		       		} 
        		       		if (images.length()>5) {
   							    url6 = images.optString(5);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url6).into(image6);
            		       		} 
        		       		if (images.length()>6) {
   							    url7 = images.optString(6);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url7).into(image7);
            		       		} 
        		       		if (images.length()>7) {
   							    url8 = images.optString(7);
//   						        new DownloadFilesTask().execute(url1);
         				          Picasso.with(getApplicationContext()).load(url8).into(image8);
            		       		} 
            		       	 
					} catch (Exception e) {
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
         	if (result>4) {
        		image5.setImageBitmap(bitmap[4]);
        		image5.setClickable(true);
        	}
         	if (result>5) {
        		image6.setImageBitmap(bitmap[5]);
        		image6.setClickable(true);
        	}
         	if (result>6) {
        		image7.setImageBitmap(bitmap[6]);
        		image7.setClickable(true);
        	}
         	if (result>7) {
        		image8.setImageBitmap(bitmap[7]);
        		image8.setClickable(true);
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
        	        JSONArray ja,ju;
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
     // 			         		ju=jo1.getJSONArray("user");
     //  			         		kommentit+=ju.get(0)+": "+jo1.getString("text")+"\n\n";
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
