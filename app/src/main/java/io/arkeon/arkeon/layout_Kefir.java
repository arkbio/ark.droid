package io.arkeon.arkeon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.EditText;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richardalbertleddy on 5/10/15.
 */


public class layout_Kefir extends RelativeLayout {

    private final String deviceID = "Kefir";

    private Context mContext;
    private LayoutInflater layoutInflater;

    private Switch _sw_mixDirection;
    private SeekBar _sk_mixSeek;
    private Switch _sw_LEDs;
    private SeekBar _sk_LEDs;
    private SeekBar _sk_growSeek;
    private Switch _sw_recirc;
    private Switch _sw_drain;
    private Switch _sw_load;
    private Switch _sw_dispense;
    private Switch _sw_sensors;

    //private int     _ledProgress;

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
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_kefir, this, true);
    }

    private void bindViews() {
        // bind all views here

        //_ledProgress = 0;

        _sw_mixDirection = (Switch)findViewById(R.id.switchMix);
        _sw_mixDirection.setChecked(true);
        _sw_mixDirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("mixDirection", isChecked);
            }
        });

        _sk_mixSeek  = (SeekBar)findViewById(R.id.seekMix);
        _sk_mixSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int progress, boolean fromUser) {
                sendModuleMessage("mixSpeed", progress != 0, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar mixSeek) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar mixSeek) {
            }
        });

        _sw_LEDs = (Switch)findViewById(R.id.switchLEDs);
        _sw_LEDs.setChecked(false);
        _sw_LEDs.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", deviceID);
                    jobj.put("module", "lights");
                    if(isChecked) {
                        int progress = _sk_LEDs.getProgress();
                        jobj.put("values", rgbHelper(progressToRGB(progress)));
                    }
                    else {
                        jobj.put("values", rgbHelper(0, 0, 0));
                    }
                    arkeon.appMainActivity.socket_emission(jobj);
                } catch (JSONException jexp) {

                }
            }
        });

        _sk_LEDs  = (SeekBar)findViewById(R.id.seekLEDs);
        _sk_LEDs.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int progress, boolean fromUser) {
                //_ledProgress = progress;
                _sw_LEDs.setChecked(true);
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("deviceID", deviceID);
                    jobj.put("module", "lights");
                    jobj.put("values", rgbHelper(progressToRGB(progress)));
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

        _sk_growSeek = (SeekBar)findViewById(R.id.seekGrow);
        _sk_growSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                sendModuleMessage("growLight", progress != 0, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar growSeek) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar growSeek) {

            }
        });

        _sw_recirc = (Switch)findViewById(R.id.switchRecirc);
        _sw_recirc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("RecircPump", isChecked);
            }
        });

        _sw_load = (Switch)findViewById(R.id.switchLoad);
        _sw_load.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("LoadPump", isChecked);
            }
        });

        _sw_drain = (Switch)findViewById(R.id.switchDrain);
        _sw_drain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("DrainValve", isChecked);
            }
        });

        _sw_dispense = (Switch)findViewById(R.id.switchDispense);
        _sw_dispense.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("DispenseValve", isChecked);
            }
        });

        _sw_sensors = (Switch)findViewById(R.id.switchSensors);
        _sw_sensors.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sendModuleMessage("sensors", isChecked);
            }
        });
    }

    public void handleSocketEvent(String event, JSONObject payload) throws JSONException {

        if(event == "sensor_report") {
            JSONObject measurement = (JSONObject)payload.get("measurement");
            String label = measurement.getString("label");
            EditText editText;

            if(label == "temp") editText = (EditText)findViewById(R.id.temp);
            else if(label == "pH") editText = (EditText)findViewById(R.id.pH);
            else if(label == "DO") editText = (EditText)findViewById(R.id.DO);
            else if(label == "optical") editText = (EditText)findViewById(R.id.optical);
            else if(label == "flow") editText = (EditText)findViewById(R.id.flow);
            else {
                System.out.println("sensor label not founnd: " + label); // error
                return;
            }

            String value = measurement.getString("datum");
            editText.setText(value);
        }
        else if (event == "status_report") {
            if (!payload.has(deviceID)) return;
            JSONObject status = (JSONObject)payload.get(deviceID);

            if(status.has("DispenseValue")) _sw_dispense.setChecked(status.getBoolean("DispenseValue"));
            if(status.has("DrainValve")) _sw_drain.setChecked(status.getBoolean("DrainValve"));
            //if(status.has("RecircValve")) _sw_.setChecked(status.getBoolean("RecircValve"));

            if(status.has("RecircPump")) _sw_recirc.setChecked(status.getBoolean("RecircPump"));
            if(status.has("LoadPump")) _sw_load.setChecked(status.getBoolean("LoadPump"));
            //if(status.has("DosePump")) _sw_dose.setChecked(status.getBoolean("DosePump"));
            //if(status.has("AirPump")) _sw_air.setChecked(status.getBoolean("AirPump"));
            //if(status.has("Heater")) _sw_heater.setChecked(status.getBoolean("Heater"));

            if(status.has("mixSpeed")) _sk_mixSeek.setProgress(status.getInt("mixSpeed"));
            if(status.has("mixDirection")) _sw_mixDirection.setChecked(status.getBoolean("mixDirection"));

            if(status.has("growLight")) _sk_growSeek.setProgress(status.getInt("growLight"));
            //if(status.has("growRelay")) _sk_growSeek.setProgress(status.getBoolean("growStatus"));

            int[] rgb = new int[3];
            if(status.has("ledRed") || status.has("ledGreen") || status.has("ledBlue")) {
                // invert calculation to find slider position
                rgb[0] = status.optInt("ledRed");
                rgb[1] = status.optInt("ledGreen");
                rgb[2] = status.optInt("ledBlue");
                for(int i = 0; i < 5*255+1; i++) {
                    if (rgb == progressToRGB(i)) {
                        _sk_LEDs.setProgress(i);
                        break;
                    }
                }
            }
        }
    }

    private void sendModuleMessage(String module, boolean state) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("deviceID", deviceID);
            jobj.put("module", module);
            jobj.put("state", state);
            arkeon.appMainActivity.socket_emission(jobj);
        } catch (JSONException ex) {
        }
    }

    private void sendModuleMessage(String module, boolean state, int value) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("deviceID", deviceID);
            jobj.put("module", module);
            jobj.put("state", state);
            jobj.put("value", value);
            arkeon.appMainActivity.socket_emission(jobj);
        } catch (JSONException ex) {
        }
    }

    private JSONObject rgbHelper(int red, int green, int blue) throws JSONException {
        JSONObject values = new JSONObject();
        values.put("red", red);
        values.put("green", green);
        values.put("blue", blue);
        return values;
    }

    private JSONObject rgbHelper(int [] rgb) throws JSONException {
        return rgbHelper(rgb[0], rgb[1], rgb[2]);
    }

    private int[] progressToRGB(int i) {
        int[] rgb = new int[3];

        if (i <= 255) {
            rgb[0] = 255;
            rgb[1] = i;
            rgb[2] = 0;
        }
        else if (i <= 255*2) {
            rgb[0] = 255*2 - i;
            rgb[1] = 255;
            rgb[2] = 0;
        }
        else if (i <= 255*3) {
            rgb[0] = 0;
            rgb[1] = 255;
            rgb[2] = i - 255*2;
        }
        else if (i <= 255*4) {
            rgb[0] = 0;
            rgb[1] = 255*4 - i;
            rgb[2] = 255;
        }
        else {
            rgb[0] = i - 255*4;
            rgb[1] = 0;
            rgb[2] = 255;
        }
        return rgb;
    }
}
