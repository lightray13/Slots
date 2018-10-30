package aaa.bbb.ccc84;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPlay, btnSettings, btnRules;
    int volume = 20;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        btnRules = (Button) findViewById(R.id.btn_rules);

        btnPlay.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnRules.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                Intent intent = new Intent(MenuActivity.this, SlotsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_settings:
                final Dialog yourDialog = new Dialog(this);
                LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.seek_dialog, (ViewGroup)findViewById(R.id.your_dialog_root_element));
                yourDialog.setContentView(layout);
                Button yourDialogButton = (Button)layout.findViewById(R.id.your_dialog_button);
                SeekBar yourDialogSeekBar = (SeekBar)layout.findViewById(R.id.your_dialog_seekbar);
                yourDialogSeekBar.setProgress(20);
                int color = ContextCompat.getColor(MenuActivity.this, R.color.button);
                yourDialogSeekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                yourDialogSeekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                SeekBar.OnSeekBarChangeListener yourSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
                        volume = progress;
                    }
                };
                yourDialogSeekBar.setOnSeekBarChangeListener(yourSeekBarListener);
                yourDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                        Log.d("myLogs", "volume: " + volume);
                        yourDialog.cancel();
                    }
                });
                yourDialog.show();
                break;
            case R.id.btn_rules:
                final Dialog yourDialog2 = new Dialog(this);
                LayoutInflater inflater2 = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout2 = inflater2.inflate(R.layout.rule_dialog, (ViewGroup)findViewById(R.id.your_dialog_root_element));
                yourDialog2.setContentView(layout2);
                Button yourDialogButton2 = (Button)layout2.findViewById(R.id.your_dialog_button);
                yourDialogButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yourDialog2.cancel();
                    }
                });
                yourDialog2.show();
                break;
        }
    }
}
