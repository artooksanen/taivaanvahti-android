package fi.jklsirius.taivaanvahti;

//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;

import android.app.Activity;
import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.widget.ImageView;
import android.webkit.WebView;

public class ImagesActivity extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	
      super.onCreate(savedInstanceState);
    //  setContentView(R.layout.main);
      setContentView(R.layout.image);
//      ImageView image3 = (ImageView) findViewById(R.id.imageView3);
      WebView image3 = (WebView) findViewById(R.id.imageView3);
     try {
      	// "http://www.taivaanvahti.fi/images/uploads/201208/7073_031df07b6f7d8e22c4cd3d86b92bf36a.jpg"
      	Intent i = getIntent();
          // getting attached intent data
          String url = i.getStringExtra("url");
          //Bitmap bitmap3 = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
          //image3.setImageBitmap(bitmap3);
          image3.getSettings().setLoadWithOverviewMode(true);
          image3.getSettings().setUseWideViewPort(true);

          image3.loadUrl(url);
          
    //  "http://www.taivaanvahti.fi/images/uploads/201208/thumbnails/6909_ed510046ffd79709b00519a9c4f60182.png";
//       	} catch (MalformedURLException e) {
//   	  e.printStackTrace();
 	} catch (Exception e) {
  	  e.printStackTrace();
  	}

      
  }
}