package com.example.mechfuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mechfuse.Room.RoomResult;
import com.example.mechfuse.ui.home.HomeFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.List;
import java.util.Locale;

public class Movie_DetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titleView;
    TextView voteView;
    TextView overViewText;
    Button fav;
    String tag;

    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__details);

        getSupportActionBar().hide();

        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);



        share = findViewById(R.id.share_btn);
        imageView = findViewById(R.id.detailsImage);
        titleView = findViewById(R.id.title);
        voteView = findViewById(R.id.averageVote);
        overViewText = findViewById(R.id.overview);

        fav = findViewById(R.id.detailsFavBtn);

        Intent intent = getIntent();

        final String id = intent.getStringExtra("id");
        final String url = intent.getStringExtra("url");
        final String title = intent.getStringExtra("title");
        final String vote = intent.getStringExtra("vote");
        final String overview = intent.getStringExtra("overview");
         tag = intent.getStringExtra("tag");

        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+url).into(imageView);
        titleView.setText(title);
        voteView.setText("Average Vote : "+vote);
        overViewText.setText(overview);

        if(Locale.getDefault().getLanguage().equals("de"))
        setLanguage();


        if(tag.equals("0"))
        {
            fav.setBackgroundResource(R.drawable.ic_favorite_grey_24dp);
            fav.setTag(String.valueOf("0"));
        }
        else {
            fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            fav.setTag(String.valueOf("1"));
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt((String) fav.getTag()) == 0)
                {
                    fav.setTag(String.valueOf("1"));
                    RoomResult roomResult = new RoomResult(Integer.valueOf(id),url,title,overview,
                            Double.parseDouble(vote));
                    HomeFragment.myappDatabse.mydao().addFavMovie(roomResult);
                    fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    Toast.makeText(getApplicationContext(), "Movie added", Toast.LENGTH_SHORT).show();
                }
                else {
                    RoomResult roomResult = new RoomResult(Integer.valueOf(id),url,title,overview,
                            Double.parseDouble(vote));
                    HomeFragment.myappDatabse.mydao().deleteResult(roomResult);
                    fav.setTag(String.valueOf("0"));
                    fav.setBackgroundResource(R.drawable.ic_favorite_grey_24dp);
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this Movie at: https://www.themoviedb.org/tv/"+id+"-"+title+"?language=en-US" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    private void setLanguage() {

        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(FirebaseTranslateLanguage.EN)
                .setTargetLanguage(FirebaseTranslateLanguage.DE)
                .build();

        final FirebaseTranslator englishGermanTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);


        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                englishGermanTranslator.translate(overViewText.getText().toString())
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        overViewText.setText(translatedText);
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Movie_DetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        //overViewText.setText(e.getMessage());
                                                    }
                                                });
                                englishGermanTranslator.translate(titleView.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                            @Override
                                            public void onSuccess(String s) {
                                                titleView.setText(s.toString());
                                            }
                                        });
                                englishGermanTranslator.translate(voteView.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                            @Override
                                            public void onSuccess(String s) {
                                                voteView.setText(s.toString());
                                            }
                                        });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Movie_DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
//    annotationProcessor "android.arch.persistence.room:compiler:1.1.1"
//implementation 'com.google.cloud:google-cloud-translate:1.12.0'
//    annotationProcessor 'com.google.auto.value:auto-value:1.5.2'