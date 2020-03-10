package com.example.bmicalc;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.text.DecimalFormat;

public class MainActivity<coordinatorLayout> extends AppCompatActivity {

    EditText h,w;
    String s1,bmessage;
    Button button;
    TextSwitcher mTextSwitcher;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h =findViewById(R.id.height);
        w = findViewById(R.id.weight);
        button = findViewById(R.id.button);
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);

        mTextSwitcher = findViewById(R.id.result);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                TextView t = new TextView(MainActivity.this);
                t.setGravity(Gravity.CENTER);
                return t;
            }
        });

        mTextSwitcher.setInAnimation(in);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h.length() != 0 || w.length() != 0) {
                    double height = Double.parseDouble(h.getText().toString());
                    double weight = Double.parseDouble(w.getText().toString());


                    double bmi = weight / (height*height);
                    bmi = bmi * 10000;
                    DecimalFormat f = new DecimalFormat("##.00");
                    s1 = f.format(bmi);
                    if(bmi<18.5){
                        bmessage = "am under Weight!!";
                    }else if(bmi<25){
                        bmessage = "am healthy.";
                    }else if(bmi<30){
                        bmessage = "have to lose some weight.";
                    }else{
                        bmessage = "am over Weight!!";
                    }
                    Toast.makeText(MainActivity.this, "I "+bmessage, Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout,
                                    "Wann share your BMI with friends...", Snackbar.LENGTH_LONG)
                            .setAction("Share", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    String shareBody = "Hey!! My BMI is " + s1 + "& I "+ bmessage+"\n What's yours? \n Check your's by installing app:";
                                    String shareSub = "https://www.amazon.com/Devil-47-Fly-Me/dp/B07TYZQT9F/ref=sr_1_1?keywords=Fly+Me+2d+game&qid=1562526629&s=gateway&sr=8-1";
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody + " " +shareSub);
                                    startActivity(Intent.createChooser(sharingIntent, "Share via:"));
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();

                    mTextSwitcher.setText(s1);
                }else{
                    Toast.makeText(MainActivity.this, "Enter Height and Weight!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mTextSwitcher.setOutAnimation(out);
    }
}
