package io.arkeon.arkeon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

/**
 * Created by richardalbertleddy on 5/10/15.
 */


public class layout_Zephir extends RelativeLayout {

    private Context mContext;
    private LayoutInflater layoutInflater;

    private Switch _sw_heatOnOff;
    private Switch _sw_airOnOff;
    private Switch _sw_recircOnOff;


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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'heatOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'heatOnOff', 'state' : 'false' }");
                }
            }
        });


        _sw_airOnOff.setChecked(true);
        _sw_airOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'airOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'airOnOff', 'state' : 'false' }");
                }
            }
        });

        _sw_recircOnOff.setChecked(true);
        _sw_recircOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'recircOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'zephyr', 'module' : 'recircOnOff', 'state' : 'false' }");
                }
            }
        });

    }


}