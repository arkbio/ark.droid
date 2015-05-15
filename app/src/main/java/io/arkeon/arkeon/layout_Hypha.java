package io.arkeon.arkeon;

import android.widget.CompoundButton;
import android.widget.EditText;
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

    private Context mContext;
    private LayoutInflater layoutInflater;


    protected arkeon mainActivity;


    private Switch _sw_mixOnOff;
    private Switch _sw_mixDirection;
    private SeekBar _sk_mixSeek;
    private Switch _sw_growOnOff;
    private SeekBar _sk_growSeek;
    private Switch _sw_recircOnOff;

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
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_hypha, this, true);
    }

    private void bindViews() {

        // bind all views here

        _sw_mixOnOff = (Switch)findViewById(R.id.switchMix);
        _sw_mixDirection = (Switch)findViewById(R.id.mixDirection);
        _sk_mixSeek  = (SeekBar)findViewById(R.id.seekMix);
        _sw_growOnOff = (Switch)findViewById(R.id.growOnOff);
        _sk_growSeek = (SeekBar)findViewById(R.id.seekGrow);
        _sw_recircOnOff = (Switch)findViewById(R.id.switchRecirc);

        _sw_mixOnOff.setChecked(true);
        _sw_mixOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'DosePump', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'DosePump', 'state' : 'false' }");
                }
            }
        });

        _sw_mixDirection.setChecked(true);
        _sw_mixDirection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'mixDirection', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'mixDirection', 'state' : 'false' }");
                }
            }
        });

        _sk_mixSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar mixSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'mixSeek', 'value' : '" + progstr + "' }");
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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'growOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'growOnOff', 'state' : 'false' }");
                }
            }
        });

        _sk_growSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar growSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'growSeek', 'value' : '" + progstr + "' }");
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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'RecircPump', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'hypha', 'module' : 'RecircPump', 'state' : 'false' }");
                }
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
    }

}
