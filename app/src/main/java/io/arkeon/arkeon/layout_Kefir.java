package io.arkeon.arkeon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;


import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richardalbertleddy on 5/10/15.
 */


public class layout_Kefir extends RelativeLayout {

    public static layout_Kefir kefirOperations;

    private Context mContext;
    private LayoutInflater layoutInflater;


    private Switch  _sw_mixOnOff;
    private Switch  _sw_mixDirection;
    private Switch  _sw_growOnOff;
    private Switch  _sw_recircOnOff;

    private SeekBar _sk_growSeek;
    private SeekBar _sk_mixSeek;

    private int     _growLightLevel;
    private int     _mixSpeed;
    private boolean _mixDirection;

    private int     _led_Red;
    private int     _led_Green;
    private int     _led_Blue;

    private boolean _RecircPump;
    private boolean _DispenseValue;
    private boolean _RecircValve;
    private boolean _DrainValve;
    private boolean _growRelay;
    private boolean _AirPump;
    private boolean _Heater;
    private boolean _LoadPump;
    private boolean _DosePump;


    private String  _deviceId = "kefir";



    public layout_Kefir (Context context) {
        super(context);
        this.mContext = context;
        inflate();
        bindViews();

    }

    public layout_Kefir(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }

    public layout_Kefir(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }


    private void inflate() {
        kefirOperations = this;
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_kefir, this, true);
    }


    private void bindViews() {
        // bind all views here

        _growLightLevel = 50;

        _sw_mixOnOff = (Switch)findViewById(R.id.mixOnOff);
        _sw_mixDirection = (Switch)findViewById(R.id.mixDirection);
        _sk_mixSeek  = (SeekBar)findViewById(R.id.mixSeek);
        _sw_growOnOff = (Switch)findViewById(R.id.growOnOff);
        _sk_growSeek = (SeekBar)findViewById(R.id.growSeek);
        _sw_recircOnOff = (Switch)findViewById(R.id.recircOnOff);

        _sw_mixOnOff.setChecked(true);
        _sw_mixOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                _RecircValve = isChecked;
                arkeon.appMainActivity.transmit_switch(_deviceId, "RecircValve", isChecked);
            }
        });

        _sw_mixDirection.setChecked(true);
        _sw_mixDirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                _mixDirection = isChecked;
                arkeon.appMainActivity.transmit_switch(_deviceId, "mixDirection", isChecked);
            }
        });

        _sk_mixSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar mixSeek, int progress, boolean fromUser) {
                _mixSpeed = progress;
                String progstr = (new Integer(_mixSpeed)).toString();
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
        _sw_growOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                _growRelay = isChecked;
                String progstr = (new Integer(_growLightLevel)).toString();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", _deviceId);
                    jobj.put("module", "growLight");
                    jobj.put("value", progstr);
                    jobj.put("state", isChecked);
                    arkeon.appMainActivity.socket_emission(jobj);
                } catch (JSONException jexp) {

                }
            }
        });

        _sk_growSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
        _sw_recircOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                _RecircValve = isChecked;
                arkeon.appMainActivity.transmit_switch(_deviceId, "RecircPump", isChecked);
            }
        });

    }


    public void setOperationValues(JSONObject deviceState) {

        try {


            _growLightLevel = deviceState.getInt("growLight");
            _mixSpeed = deviceState.getInt("mix");
            _mixDirection = deviceState.getBoolean("mixDirection");

            _led_Red = deviceState.getInt("Red");
            _led_Green = deviceState.getInt("Green");
            _led_Blue = deviceState.getInt("Blue");

            _RecircPump = deviceState.getBoolean("RecircPump");
            _DispenseValue = deviceState.getBoolean("DispenseValue");
            _RecircValve = deviceState.getBoolean("RecircValve");
            _DrainValve = deviceState.getBoolean("DrainValve");
            _growRelay = deviceState.getBoolean("growRelay");
            _AirPump = deviceState.getBoolean("AirPump");
            _Heater = deviceState.getBoolean("Heater");
            _LoadPump = deviceState.getBoolean("LoadPump");
            _DosePump = deviceState.getBoolean("DosePump");

            _sw_mixOnOff.setChecked(_RecircValve);
            _sw_mixDirection.setChecked(_mixDirection);
            _sw_recircOnOff.setChecked(_RecircPump);

            _sw_growOnOff.setChecked( (_growLightLevel == 0) ? false : true );
            _sk_growSeek.setProgress(_growLightLevel);
            _sk_mixSeek.setProgress(_mixSpeed);

        } catch ( JSONException jexp ) {
        }
    }

}

