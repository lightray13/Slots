package aaa.bbb.ccc84;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SlotsActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvScore;
    TextView tvLive;
    int score = 0;
    int live = 0;
    String one;
    String two;
    String three;
    String four;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvLive = (TextView) findViewById(R.id.tv_live);

        tvScore.setText(String.valueOf(score));
        tvLive.setText(String.valueOf(live));

        one = getResources().getDrawable(R.drawable.one).getConstantState().toString();
        two = getResources().getDrawable(R.drawable.two).getConstantState().toString();
        three = getResources().getDrawable(R.drawable.three).getConstantState().toString();
        four = getResources().getDrawable(R.drawable.four).getConstantState().toString();

        final SlotsBuilder.Builder builder = SlotsBuilder.builder(this);
        final SlotsBuilder slots = builder
                .addSlots(R.id.slot_one, R.id.slot_two, R.id.slot_three, R.id.slot_four, R.id.slot_five)
                .addDrawables(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)
                .setScrollTimePerInch(1f)
                .setDockingTimePerInch(0f)
                .setScrollTime(2500 + new SecureRandom().nextInt(1500))
                .setChildIncTime(1000)
                .setOnFinishListener(new Callback() {
                    @Override
                    public void OnFinishListener() {
                        List<LinearLayoutManager> layoutManagers = getLayoutManagers();
                        List<Drawable> drawables = new ArrayList<>();

                        for (int i = 0; i < 5; i++) {
                            drawables.add(((ImageView) layoutManagers.get(i).findViewByPosition(
                                    layoutManagers.get(i).findFirstVisibleItemPosition()+2))
                                    .getDrawable()
                                    .getCurrent());
                        }

                        int count = 0;
                        for(int i = 0; i < 4; i++) {
                            if (((drawables.get(i) == drawables.get(i+1)))) {
                                count = count + 1;
                                Drawable drawable = drawables.get(i);
                                if (drawable.getConstantState().toString().equals(one)){
                                    score = score + 30;
                                } else if (drawable.getConstantState().toString().equals(two)) {
                                    score = score + 10;
                                } else if (drawable.getConstantState().toString().equals(three)) {
                                    live = live + 30;
                                } else if (drawable.getConstantState().toString().equals(four)) {
                                    live = live + 10;
                                }
                            }
                        }

                        tvScore.setText(String.valueOf(score));
                        tvLive.setText(String.valueOf(live));

                        if (score >= 100) {
                            mediaPlayer = MediaPlayer.create(SlotsActivity.this, R.raw.one);
                            mediaPlayer.start();
                            final Dialog yourDialog2 = new Dialog(SlotsActivity.this);
                            LayoutInflater inflater2 = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                            View layout2 = inflater2.inflate(R.layout.win_dialog, (ViewGroup)findViewById(R.id.your_dialog_root_element));
                            yourDialog2.setContentView(layout2);
                            Button yourDialogButton2 = (Button)layout2.findViewById(R.id.your_dialog_button);
                            Button yourDialogButtonCancel2 = (Button)layout2.findViewById(R.id.your_dialog_button_cancel);
                            yourDialogButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    live = 0;
                                    score = 0;
                                    tvScore.setText(String.valueOf(score));
                                    tvLive.setText(String.valueOf(live));
                                    yourDialog2.cancel();
                                }
                            });
                            yourDialogButtonCancel2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onBackPressed();
                                }
                            });
                            yourDialog2.show();
                        }

                        if (live >= 100) {
                            mediaPlayer = MediaPlayer.create(SlotsActivity.this, R.raw.two);
                            mediaPlayer.start();
                            final Dialog yourDialog2 = new Dialog(SlotsActivity.this);
                            LayoutInflater inflater2 = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                            View layout2 = inflater2.inflate(R.layout.lose_dialog, (ViewGroup)findViewById(R.id.your_dialog_root_element));
                            yourDialog2.setContentView(layout2);
                            Button yourDialogButton2 = (Button)layout2.findViewById(R.id.your_dialog_button);
                            Button yourDialogButtonCancel2 = (Button)layout2.findViewById(R.id.your_dialog_button_cancel);
                            yourDialogButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    live = 0;
                                    score = 0;
                                    tvScore.setText(String.valueOf(score));
                                    tvLive.setText(String.valueOf(live));
                                    yourDialog2.cancel();
                                }
                            });
                            yourDialogButtonCancel2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onBackPressed();
                                }
                            });
                            yourDialog2.show();
                        }

                    }
                })
                .build();

        findViewById(R.id.btn_twist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slots.start();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

