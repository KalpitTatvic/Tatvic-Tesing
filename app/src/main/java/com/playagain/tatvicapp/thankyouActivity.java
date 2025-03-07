package com.playagain.tatvicapp;

import android.content.Intent;
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
import com.playagain.tatvicapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

public class thankyouActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    String app_instance_id;
    Button thankYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thankyou);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        thankYou = findViewById(R.id.thankYou);


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

        thankYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle params = new Bundle();
                params.putString("cta_text", "thankYou");
                params.putString("client_id_event", app_instance_id);
                mFirebaseAnalytics.logEvent("ThankYouCLick", params);
                Intent i = new Intent(thankyouActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "thankyouScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}