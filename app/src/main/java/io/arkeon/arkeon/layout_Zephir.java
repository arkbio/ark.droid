package io.arkeon.arkeon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richardalbertleddy on 5/10/15.
 */


public class layout_Zephir extends RelativeLayout {

    public static layout_Zephir zephirOperations;

    private Context mContext;
    private LayoutInflater layoutInflater;

    private Switch _sw_heatOnOff;
    private Switch _sw_airOnOff;
    private Switch _sw_recircOnOff;

    private String  _deviceId = "zephyr";

    public layout_Zephir (Context context) {
        super(context);
        this.mContext = context;
        inflate();
        bindViews();

    }

    public layout_Zephir(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }

    public layout_Zephir(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        inflate();
        bindViews();
    }



    private void inflate() {
        zephirOperations = this;
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_zephyr, this, true);
    }

    private void bindViews() {
        // bind all views here
        _sw_heatOnOff = (Switch)findViewById(R.id.heatOnOff);
        _sw_airOnOff = (Switch)findViewById(R.id.airOnOff);
        _sw_recircOnOff = (Switch)findViewById(R.id.recircOnOff);


        _sw_heatOnOff.setChecked(true);
        _sw_heatOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                arkeon.appMainActivity.transmit_switch(_deviceId, "Heat", isChecked);
            }
        });


        _sw_airOnOff.setChecked(true);
        _sw_airOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                arkeon.appMainActivity.transmit_switch(_deviceId, "Air", isChecked);
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
            _sw_heatOnOff.setChecked(deviceState.getBoolean("Heat"));
            _sw_airOnOff.setChecked(deviceState.getBoolean("Air"));
            _sw_recircOnOff.setChecked(deviceState.getBoolean("Recirc"));
        } catch ( JSONException jexp ) {
        }
    }


}
