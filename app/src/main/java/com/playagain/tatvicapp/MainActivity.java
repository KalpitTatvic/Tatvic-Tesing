package com.playagain.tatvicapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.playagain.tatvicapp.R;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.installations.FirebaseInstallations;
import com.playagain.tatvicapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    Button carCategory;
    String app_instance_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carCategory = findViewById(R.id.carCategory);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Retrieve Client ID
        Task<String> clientIdTask = FirebaseInstallations.getInstance().getId();
        clientIdTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String clientId = task.getResult();
                Log.d("CLIENT_ID", "Client ID: " + clientId);

                // You can send this client ID to GA4 as a user property
                Bundle bundle = new Bundle();
                bundle.putString("client_id", clientId);
                mFirebaseAnalytics.logEvent("client_id_event", bundle);
            }
        });

        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    app_instance_id = task.getResult();
                    Log.d("App instance ID Demo ",app_instance_id);
                }
            }
        });

        mFirebaseAnalytics.setUserProperty("user_client_id", app_instance_id);

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

        Bundle params1 = new Bundle();
        params1.putString(FirebaseAnalytics.Param.SOURCE, "attribution_source");
        params1.putString(FirebaseAnalytics.Param.MEDIUM, "attribution_medium");
        params1.putString(FirebaseAnalytics.Param.CAMPAIGN, "attribution_campaign");
        params1.putString(FirebaseAnalytics.Param.TERM, "attribution_term");
        params1.putString(FirebaseAnalytics.Param.CONTENT, "attribution_content");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS, params1);


        carCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString("cta_text", String.valueOf(carCategory.getText()));
                params.putString("client_id_event", app_instance_id);
                mFirebaseAnalytics.logEvent("car_category_click", params);
                Intent i = new Intent(MainActivity.this,categoryActivity.class);
                startActivity(i);
            }
        });

// ********** Start: Play Install Referrer Tracking Code **********

        // Initialize Install Referrer Client
        InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this).build();


        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        try {
                            // Retrieve the referrer details
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            String referrerUrl = response.getInstallReferrer();
                            String decodedReferrerUrl = URLDecoder.decode(referrerUrl, "UTF-8");

                            // Log the referrer URL
                            Log.d("InstallReferrer", "Referrer URL: " + decodedReferrerUrl);

                            // Extract the utm_source parameter
                            String utmSource = getUrlParameter(decodedReferrerUrl, "utm_source");

                            if (utmSource != null) {
                                Log.d("InstallReferrer", "utm_source: " + utmSource);
                            } else {
                                Log.d("InstallReferrer", "utm_source not found");
                            }

                            // Extract the utm_medium parameter
                            String utmMedium = getUrlParameter(decodedReferrerUrl, "utm_medium");

                            if (utmMedium != null) {
                                Log.d("InstallReferrer", "utm_medium: " + utmMedium);
                            } else {
                                Log.d("InstallReferrer", "utm_medium not found");
                            }

                            // End the connection
                            referrerClient.endConnection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        Log.e("InstallReferrer", "Install referrer unavailable");
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                Log.e("InstallReferrer", "Service disconnected");
            }
        });

// ********** End: Play Install Referrer Tracking Code **********


//=================================== campaign event ====================================
//        Bundle campaignParams = new Bundle();
//        campaignParams.putString(FirebaseAnalytics.Param.SOURCE, "tatvic_source");
//        campaignParams.putString(FirebaseAnalytics.Param.MEDIUM, "tatvic_medium");
//        campaignParams.putString(FirebaseAnalytics.Param.CAMPAIGN, "tatvic_campaign");
//        campaignParams.putString(FirebaseAnalytics.Param.TERM, "tatvic_term");
//        campaignParams.putString(FirebaseAnalytics.Param.CONTENT, "tatvic_content");
//        campaignParams.putString("client_id_event", app_instance_id);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS,campaignParams);
    }
//=========================================================================================


    // Helper method to extract URL parameters
    private String getUrlParameter(String url, String parameterName) {
        String[] params = url.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(parameterName)) {
                return keyValue[1];
            }
        }
        return null; // Parameter not found
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "home_screen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}

