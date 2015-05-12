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

/**
 * Created by richardalbertleddy on 5/10/15.
 */


public class layout_Kefir extends RelativeLayout {

    private Context mContext;
    private LayoutInflater layoutInflater;



    private Switch _sw_mixOnOff;
    private Switch _sw_mixDirection;
    private SeekBar _sk_mixSeek;
    private Switch _sw_growOnOff;
    private SeekBar _sk_growSeek;
    private Switch _sw_recircOnOff;




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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'RecircValve', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'RecircValve', 'state' : 'false' }");
                }
            }
        });

        _sw_mixDirection.setChecked(true);
        _sw_mixDirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'mixDirection', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'mixDirection', 'state' : 'false' }");
                }
            }
        });

        _sk_mixSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar mixSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'mixSpeed', 'value' : '" + progstr + "' }");
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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'growOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'growOnOff', 'state' : 'false' }");
                }
            }
        });

        _sk_growSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar growSeek, int progress, boolean fromUser) {
                String progstr = (new Integer(progress)).toString();
                arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'growSeek', 'value' : '" + progstr + "' }");
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
                if (isChecked) {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'recircOnOff', 'state' : 'true' }");
                } else {
                    arkeon.appMainActivity.socket_emission("{ 'deviceID' : 'kefir', 'module' : 'recircOnOff', 'state' : 'false' }");
                }
            }
        });

    }


}
