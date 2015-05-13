package io.arkeon.arkeon;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import android.view.LayoutInflater;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richardalbertleddy on 5/10/15.
 */

public class layout_Hypha extends RelativeLayout {


    public static layout_Hypha hyphaOperations;


    private Context mContext;
    private LayoutInflater layoutInflater;


    protected arkeon mainActivity;

    private String  _deviceId = "hypha";
    private int     _growLightLevel;


    private Switch  _sw_mixOnOff;
    private Switch  _sw_mixDirection;
    private SeekBar _sk_mixSeek;
    private Switch  _sw_growOnOff;
    private SeekBar _sk_growSeek;
    private Switch  _sw_recircOnOff;



    public layout_Hypha (Context context) {
        super(context);
        this.mContext = context;
        inflate();
        bindViews();

    }

    public layout_Hypha(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }

    public layout_Hypha(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }



    private void inflate() {
        hyphaOperations = this;
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_hypha, this, true);
    }

    private void bindViews() {

        // bind all views here

        _sw_mixOnOff = (Switch)findViewById(R.id.mixOnOff);
        _sw_mixDirection = (Switch)findViewById(R.id.mixDirection);
        _sk_mixSeek  = (SeekBar)findViewById(R.id.mixSeek);
        _sw_growOnOff = (Switch)findViewById(R.id.growOnOff);
        _sk_growSeek = (SeekBar)findViewById(R.id.growSeek);
        _sw_recircOnOff = (Switch)findViewById(R.id.recircOnOff);

        _sw_mixOnOff.setChecked(true);
        _sw_mixOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                arkeon.appMainActivity.transmit_switch(_deviceId, "DosePump", isChecked);
            }
        });

        _sw_mixDirection.setChecked(true);
        _sw_mixDirection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                arkeon.appMainActivity.transmit_switch(_deviceId, "mixDirection", isChecked);
            }
        });

        _sk_mixSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar mixSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", _deviceId);
                    jobj.put("module", "mixSpeed");
                    jobj.put("state", true);
                    jobj.put("value", progstr);
                    arkeon.appMainActivity.socket_emission(jobj);
                } catch (JSONException jexp) {
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar mixSeek) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar mixSeek) {
            }
        });

        _sw_growOnOff.setChecked(true);
        _sw_growOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", _deviceId);
                    jobj.put("module", "growLight");
                    jobj.put("state", isChecked);
                    arkeon.appMainActivity.socket_emission(jobj);
                } catch (JSONException jexp) {
                }
            }
        });

        _sk_growSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar growSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                _growLightLevel = progress;
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", _deviceId);
                    jobj.put("module", "growLight");
                    jobj.put("state", true);
                    jobj.put("value", progstr);
                    arkeon.appMainActivity.socket_emission(jobj);
                } catch (JSONException jexp) {

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar growSeek) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar growSeek) {
            }
        });


        _sw_recircOnOff.setChecked(true);
        _sw_recircOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                arkeon.appMainActivity.transmit_switch(_deviceId, "RecircPump", isChecked);
            }
        });

    }



    public void setOperationValues(JSONObject deviceState) {

        try {

            _sw_mixOnOff.setChecked(deviceState.getBoolean("RecircValve"));
            _sw_mixDirection.setChecked(deviceState.getBoolean("mixDirection"));
            _sw_growOnOff.setChecked( (deviceState.getInt("growLight") == 0) ? false : true );
            _sw_recircOnOff.setChecked(deviceState.getBoolean("RecircPump"));

            _sk_growSeek.setProgress(deviceState.getInt("growLight"));
            _sk_mixSeek.setProgress(deviceState.getInt("mix"));

        } catch ( JSONException jexp ) {
        }
    }



}
