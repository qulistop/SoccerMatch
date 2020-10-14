package org.aplas.soccermatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PlayActivity extends AppCompatActivity {
    private ArrayList<String> eventList = new ArrayList<>();
    private ArrayList<String> homePlayer, awayPlayer;
    final String separator = "@";

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Hours, Seconds, Minutes;
    Handler handler;

    private TextView timerTxt;
    private TextView homeTeamTxt, awayTeamTxt, homeScoreTxt, awayScoreTxt;
    private ImageView homeLogo,awayLogo;
    private Button startMatch, finishMatch;
    private ImageButton homeScoreBtn, homeYellowBtn, homeRedBtn, awayScoreBtn, awayYellowBtn, awayRedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        timerTxt = (TextView) findViewById(R.id.timerShow);
        homeTeamTxt = (TextView) findViewById(R.id.homeTeam);
        awayTeamTxt = (TextView) findViewById(R.id.awayTeam);
        homeScoreTxt = (TextView) findViewById(R.id.homeScore);
        awayScoreTxt = (TextView) findViewById(R.id.awayScore);
        homeLogo = (ImageView) findViewById(R.id.homeLogo);
        awayLogo = (ImageView) findViewById(R.id.awayLogo);
        homeScoreBtn = (ImageButton)findViewById(R.id.addHomeScore);
        homeYellowBtn = (ImageButton)findViewById(R.id.addHomeYellow);
        homeRedBtn = (ImageButton)findViewById(R.id.addHomeRed);
        awayScoreBtn = (ImageButton)findViewById(R.id.addAwayScore);
        awayYellowBtn = (ImageButton)findViewById(R.id.addAwayYellow);
        awayRedBtn = (ImageButton)findViewById(R.id.addAwayRed);
        startMatch =  (Button) findViewById(R.id.matchStartBtn);
        finishMatch = (Button)findViewById(R.id.matchFinishBtn);

        handler = new Handler() ;

        startMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtnClick();
            }
        });

        homeScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(homePlayer,homeScoreBtn);
            }
        });

        homeYellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(homePlayer,homeYellowBtn);
            }
        });

        homeRedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(homePlayer,homeRedBtn);
            }
        });

        awayScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(awayPlayer,awayScoreBtn);
            }
        });

        awayYellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(awayPlayer,awayYellowBtn);
            }
        });

        awayRedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(awayPlayer,awayRedBtn);
            }
        });


        finishMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTeamData();
        showFooter();
    }

    private void loadTeamData() {
        Intent intent = getIntent();
        String homeTeam = intent.getStringExtra("HOME_TEAM_NAME");
        if (!homeTeam.isEmpty()) homeTeamTxt.setText(homeTeam);

        String awayTeam = intent.getStringExtra("AWAY_TEAM_NAME");
        if (!awayTeam.isEmpty()) awayTeamTxt.setText(awayTeam);

        homePlayer = intent.getStringArrayListExtra("HOME_TEAM_PLAYER");
        awayPlayer = intent.getStringArrayListExtra("AWAY_TEAM_PLAYER");

        try {
            String homeImgPath = intent.getStringExtra("HOME_IMG_URI");
            if (!homeImgPath.isEmpty()) {
                Uri homeUri = Uri.parse(homeImgPath);
                InputStream imageStream1 = getContentResolver().openInputStream(homeUri);
                Bitmap selectedImage1 = BitmapFactory.decodeStream(imageStream1);
                homeLogo.setImageBitmap(selectedImage1);
            }
            String awayImgPath = intent.getStringExtra("AWAY_IMG_URI");
            if (!awayImgPath.isEmpty()) {
                Uri awayUri = Uri.parse(awayImgPath);
                InputStream imageStream2 = getContentResolver().openInputStream(awayUri);
                Bitmap selectedImage2 = BitmapFactory.decodeStream(imageStream2);
                awayLogo.setImageBitmap(selectedImage2);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(PlayActivity.this, "Can't load image", Toast.LENGTH_LONG).show();
        }
    }

    private void startBtnClick() {
        if (startMatch.getText().equals("Start")) {
            /*
            //Start the Timer
            if (lastStopTime == 0) { //First time
                timerTxt.setBase(SystemClock.elapsedRealtime());
            } else {
                long intervalOnPause = (SystemClock.elapsedRealtime() - lastStopTime);
                timerTxt.setBase(timerTxt.getBase() + intervalOnPause);
            }
            timerTxt.start();
            */

            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 1000);
            //handler.post(runnable);

            //Setup the buttons
            startMatch.setText("Stop");
            setEventButtonClickable(true);
            finishMatch.setEnabled(false);
        } else if (startMatch.getText().equals("Stop")) {
            /*
            //Stop the Countdown
            timerTxt.stop();
            lastStopTime = SystemClock.elapsedRealtime();
            */
            TimeBuff += MillisecondTime;
            handler.removeCallbacks(runnable);

            //Setup the buttons
            startMatch.setText("Start");
            setEventButtonClickable(false);
            finishMatch.setEnabled(true);
        }
    }

    private void setEventButtonClickable(boolean status) {
        homeScoreBtn.setEnabled(status);
        homeYellowBtn.setEnabled(status);
        homeRedBtn.setEnabled(status);
        awayScoreBtn.setEnabled(status);
        awayYellowBtn.setEnabled(status);
        awayRedBtn.setEnabled(status);
    }

    private void openLogActivity() {

        String matchResult, scoreResult;
        int homeScore = Integer.parseInt(homeScoreTxt.getText().toString());
        int awayScore = Integer.parseInt(awayScoreTxt.getText().toString());
        if (homeScore==awayScore) {
            matchResult = "Draw";
            scoreResult = homeScore+"-"+awayScore;
        } else if (homeScore>awayScore) {
            matchResult = homeTeamTxt.getText().toString()+" Win!!";
            scoreResult = homeScore+"-"+awayScore;
        } else {
            matchResult = awayTeamTxt.getText().toString()+" Win!!";
            scoreResult = awayScore+"-"+homeScore;
        }
        Intent log = new Intent(getApplicationContext(),LogActivity.class);
        log.putExtra("MATCH_RESULT", matchResult);
        log.putExtra("MATCH_SCORE", scoreResult);
        log.putStringArrayListExtra("MATCH_EVENT", eventList);

        startActivity(log);
    }

    private void addEvent(String eventName, String playerName, String teamName, String currTime) {
        String data = eventName+separator+playerName+separator+teamName+separator+currTime;
        eventList.add(data);
        //String eventMsg = (eventName.equals("Goal"))?eventName:eventName+" card";

        Fragment footerFragment = getSupportFragmentManager().findFragmentByTag("footer");
        View footerView = footerFragment.getView();
        TextView news = footerView.findViewById(R.id.newsTxt);
        String newsText = eventName+" for "+teamName+" by "+playerName+" at "+currTime;
        news.setText(newsText,TextView.BufferType.EDITABLE);
    }

    private void addScore(TextView scoreTxt) {
        int score = Integer.parseInt(scoreTxt.getText().toString())+1;
        scoreTxt.setText(""+score,TextView.BufferType.EDITABLE);
    }

    private void showFooter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FooterFragment footer = new FooterFragment();
        fragmentTransaction.add(R.id.footer, footer, "footer");
        fragmentTransaction.commit();
    }

    private void showPopupMenu(ArrayList<String> list, View anchor) {
        //Creating the instance of PopupMenu
        Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, anchor);
        for (int i=0; i<list.size(); i++) {
            popup.getMenu().add(list.get(i));
        }
        String currTime = timerTxt.getText().toString();
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(PlayActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                switch (anchor.getId()) {
                    case R.id.addHomeScore: {
                        addEvent("Goal", item.getTitle().toString(), homeTeamTxt.getText().toString(), currTime);
                        addScore(homeScoreTxt);
                        break;
                    }
                    case R.id.addHomeYellow: addEvent("Yellow Card", item.getTitle().toString(), homeTeamTxt.getText().toString(), currTime);
                        break;
                    case R.id.addHomeRed: addEvent("Red Card",item.getTitle().toString(),homeTeamTxt.getText().toString(),currTime);
                        break;
                    case R.id.addAwayScore: {
                        addEvent("Goal",item.getTitle().toString(),awayTeamTxt.getText().toString(),currTime);
                        addScore(awayScoreTxt);
                        break;
                    }
                    case R.id.addAwayYellow: addEvent("Yellow Card",item.getTitle().toString(),awayTeamTxt.getText().toString(),currTime);
                        break;
                    case R.id.addAwayRed: addEvent("Red Card",item.getTitle().toString(),awayTeamTxt.getText().toString(),currTime);
                        break;
                    default: break;
                }

                return true;
            }
        });
        popup.show();
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Hours = Seconds/3600;
            Seconds = Seconds % 3600;
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            //MilliSeconds = (int) (UpdateTime % 1000);
            timerTxt.setText("" + String.format("%02d", Hours) + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }

    };
}
