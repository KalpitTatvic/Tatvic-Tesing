package com.playagain.tatvicapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.playagain.tatvicapp.R;
import com.playagain.tatvicapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

public class categoryActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout car1Layout,car2Layout,car3Layout;
    String app_instance_id;
    private Uri uri;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        car1Layout = findViewById(R.id.linearLayout);
        car2Layout=findViewById(R.id.linearLayout2);
        car3Layout = findViewById(R.id.linearLayout3);

//dynamic link code

//dynamic link code

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    app_instance_id = task.getResult();
                    Log.d("App instance ID Demo ",app_instance_id);
                }
            }
        });




        uri = getIntent().getData();

        if (uri != null){

            path = uri.toString();
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            Log.d("DeepLink", "Deep link URL: " + uri);

// You can process the deep link parameters here
            String path = uri.getPath();  // e.g., "/app"
            Log.d("DeepLink", "Path: " + path);

            // Optionally, extract query parameters
            String utmSource = uri.getQueryParameter("utm_source");
            Log.d("DeepLink", "utm_source: " + utmSource);

        }
//================================================================
        // Get the Intent that started the activity
        Intent intent = getIntent();

        // Check if the Intent has a URI
        Uri deepLinkUri = intent.getData();

        if (deepLinkUri != null) {
            // The deep link URL is available in the deepLinkUri
            String deepLinkUrl = deepLinkUri.toString();

            // Do something with the deep link URL
            Log.d("deepLink", "deepLink URL: "+deepLinkUrl);
        } else {
            System.out.println("No deep link URL found");
            Log.d("deepLink", "No deep link URL found");
        }

//        Bundle campaignParams = new Bundle();
//        campaignParams.putString(FirebaseAnalytics.Param.SOURCE, "tatvic_source");
//        campaignParams.putString(FirebaseAnalytics.Param.MEDIUM, "tatvic_medium");
//        campaignParams.putString(FirebaseAnalytics.Param.CAMPAIGN, "tatvic_campaign");
//        campaignParams.putString(FirebaseAnalytics.Param.TERM, "tatvic_term");
//        campaignParams.putString(FirebaseAnalytics.Param.CONTENT, "tatvic_content");
//        campaignParams.putString("client_id_event", app_instance_id);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS,campaignParams);

//========================================================================
        car1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString("cta_text", "car_1_click");
                params.putString("client_id_event", app_instance_id);
                mFirebaseAnalytics.logEvent("car_click", params);
                Intent i = new Intent(categoryActivity.this,thankyouActivity.class);
                startActivity(i);
            }
        });

        car2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString("cta_text", "car_2_click");
                params.putString("client_id_event", app_instance_id);
                mFirebaseAnalytics.logEvent("car_click", params);
                Intent i = new Intent(categoryActivity.this,thankyouActivity.class);
                startActivity(i);
            }
        });

        car3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString("cta_text", "car_3_click");
                params.putString("client_id_event", app_instance_id);
                mFirebaseAnalytics.logEvent("car_click", params);
                Intent i = new Intent(categoryActivity.this,thankyouActivity.class);
                startActivity(i);
            }
        });
    }

//dynamic link code********************************************

//    public String createDynamicLink(String deepLinkPath, String referralParam) {
//        String packageName = getPackageName();
//        String deepLinkUrl = "https://deep-link-demo-dd1fc.web.app" + deepLinkPath;
//
//        if (referralParam != null && !referralParam.isEmpty()) {
//            deepLinkUrl += "?referral=" + referralParam;
//        }
//
//        // Create the final URL with required parameters
//        Uri.Builder builder = Uri.parse("https://deep-link-demo-dd1fc.web.app").buildUpon()
//                .appendQueryParameter("apn", packageName)
//                .appendQueryParameter("link", deepLinkUrl);
//
//        return builder.build().toString();
//    }

//dynamic link code********************************************

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "categoryScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);


    }

}


