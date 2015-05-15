package io.arkeon.arkeon;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import android.widget.ViewFlipper;

import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;
import java.util.*;

import java.net.MalformedURLException;
import java.net.URI;

import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Text;

import io.socket.*;

/*Bluetooth*/
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.BluetoothProfile;


//public class arkeon extends Activity implements BluetoothAdapter.LeScanCallback {
public class arkeon extends Activity {


    public static arkeon appMainActivity;

//    private static final String TAG = "BluetoothGattActivity";
//    private static final String DEVICE_NAME = "Ark_BOARD";
//    private static final UUID Ark_SERVICE = UUID.fromString("11223344-5566-7788-9900-AABBCCDDEEFF");
//    private static final UUID Ark_READ_CHAR = UUID.fromString("01020304-0506-0708-0900-0A0B0C0D0E0F");
//    private static final UUID Ark_WRITE_CHAR = UUID.fromString("11121314-1516-1718-1910-1A1B1C1D1E1F");
//    private static final UUID Ark_CONFIG_CHAR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

//    private BluetoothAdapter mBluetoothAdapter = null;
//    private SparseArray<BluetoothDevice> bleDevices;
//    private BluetoothGatt mConnectedGatt;

    private static final String SOCK = "SocketIOActivity";
    private static final String known_host_url = "http://192.168.1.237:8088";

    private SparseArray serDevices;

    private SocketIO socket = null;


    private ViewFlipper viewFlipper;



//    public void changeHypha(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.arkeon);
        setProgressBarIndeterminate(true);



        appMainActivity = this;

        SocketIO socket = new SocketIO();

        //An array of Serial devices
        serDevices = new SparseArray<Object>();


        if ((socket != null) && socket.isConnected()) {
            Log.d(SOCK, "Connected too soon.");
        }

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        //Display Sensa in text fields
//        mSensor1 = (TextView) findViewById(R.id.text_sensor1);
//        mSensor2 = (TextView) findViewById(R.id.text_sensor2);
//        mSensor3 = (TextView) findViewById(R.id.text_sensor3);

        //Bluetooth in Android is accessed via the BluetoothManager
//        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
//        mBluetoothAdapter = manager.getAdapter();

        //An array for BLE devices
//        bleDevices = new SparseArray<BluetoothDevice>();

        //Progress dialog setup for when the connection process is taking place
//        mProgress = new ProgressDialog(this);
//        mProgress.setIndeterminate(true);
//        mProgress.setCancelable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.arkeon, menu);
        // Inflate the menu; this adds items to the action bar if it is present.//
//        getMenuInflater().inflate(R.menu.arkeon, menu);
//        menu.add(0, 0, 0, "Arkeon");
//        menu.add(1, 0, 0, "Hypha");
//        menu.add(2, 0, 0, "Zephyr");
        //Add any device elements we've discovered to the overflow menu
//        for (int i=0; i < serDevices.size(); i++) {
////            BluetoothDevice device = bleDevices.valueAt(i);
//            menu.add(0, serDevices.keyAt(i), 0, device.getName());
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_scan: {
                serDevices.clear();
                startSerialScan();
//                bleDevices.clear();
//                startBleScan();
                return true;
            }
            case R.id.kefir: {
                viewFlipper.setDisplayedChild(0);
//                setContentView(R.layout.activity_kefir);
                return true;
            }
            case R.id.hypha: {

                viewFlipper.setDisplayedChild( 1 );
//                setContentView(R.layout.activity_hypha);
                return true;
            }
            case R.id.zephyr: {

                viewFlipper.setDisplayedChild(2);
//                setContentView(R.layout.activity_zephyr);
                return true;
            }
            default:

                //Connect to Node Socket
                //ensureSocketConnection(known_host_url);

                //Obtain the discovered ble device to connect with
//                BluetoothDevice device = bleDevices.get(item.getItemId());
//                Log.i(TAG, "Connecting to "+device.getName());
//              //Connect with the device using LE-specific connectGatt() method, passing in a callback for GATT events
//                mConnectedGatt = device.connectGatt(this, false, mGattCallback);
                //Display progress UI
//                mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Connecting to "+device.getName()+"..."));

                return super.onOptionsItemSelected(item);
        }



    }

    private void startSerialScan(){

    }

    private void clearDisplayValues() {
        //mSensor1.setText("---");
//        mSensor2.setText("---");
//        mSensor3.setText("---");
    }


    private void ensureSocketConnection(String host_url) {

        try {

            if (socket == null) {
                socket = new SocketIO(host_url);
            }
            if (socket == null) return;
            if (!socket.isConnected()) {

                socket.connect( new IOCallback() {

                    @Override
                    public void on(String event, IOAcknowledge ack, Object... args) {
                        if ("echo back".equals(event) && args.length > 0) {
                            Log.d("SocketIO", "" + args[0]);
                        }

                        // ignore this special case
                        if(event.equals("sensor_reading")) return;

                        JSONObject jobj = (JSONObject)args[0];
                        try {
                            if(jobj.getString("deviceID").equals("Kefir")) {
                                layout_Kefir v = (layout_Kefir) viewFlipper.getChildAt(0);
                                if (v != null) v.handleSocketEvent(event, jobj);
                            }
                            else if(jobj.getString("deviceID").equals("Hypha")) {
                                layout_Hypha v = (layout_Hypha) viewFlipper.getChildAt(1);
                                if (v != null) v.handleSocketEvent(event, jobj);
                            }
                            else if(jobj.getString("deviceID").equals("Zephyr")) {
                                layout_Zephir v = (layout_Zephir) viewFlipper.getChildAt(2);
                                if (v != null) v.handleSocketEvent(event, jobj);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMessage(JSONObject json, IOAcknowledge ack) {
                        try {
                            System.out.println("Server said:" + json.toString(1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMessage(String data, IOAcknowledge ack) {
                        System.out.println("Server said:");
                    }

                    @Override
                    public void onDisconnect() {
                        Log.d(SOCK, "Disconnected");
                    }

                    @Override
                    public void onConnect() {
                        Log.d(SOCK, "Connected");
                    }

                    @Override
                    public void onError(SocketIOException e) {
                        Log.d(SOCK, "CONNECTION ERROR: " + e.getMessage());
                    }

                });

            }

        } catch (MalformedURLException mure) {
            Log.d(SOCK, "MalformedURLException.");
        }
    }

    private void socketDisconnect() {
        if (socket == null) return;
        if (socket.isConnected()) {
            socket.disconnect();
        }
        socket = null;
    }

    private double testSensa = 0.0;

//    private void socket_emission(double metric_quantity) {
//        //if ( testSensa < 1 ) testSensa = 2.54;
//        //else testSensa += 0.001;
//        if ((socket != null) && socket.isConnected()) {
//            socket.emit("Sensa1", metric_quantity);
//        }
//    }

    public void socket_emission(String message) {
        if ( ( socket != null ) && socket.isConnected() ) {
            try {
                JSONObject jobj = new JSONObject(message);
                socket.emit("command", jobj);
            } catch ( JSONException jexp ) {
                System.console().printf("json error");
            }
        }
    }

    public void socket_emission(JSONObject jobj) {
        if ( ( socket != null ) && socket.isConnected() ) {
            socket.emit("command", jobj);
        }
    }
    public void socket_emission_collection(JSONObject metric_quantity_map) {
        if ((socket != null) && socket.isConnected()) {
            socket.send(metric_quantity_map);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ensureSocketConnection(known_host_url);
    }

    @Override
    protected void onStop() {
        super.onStop();
        socketDisconnect();

//*BLUETOOTH-BLUETOOTH-BLUETOOTH*//

//        if ( mConnectedGatt != null ){
//            BluetoothGattCharacteristic characteristic;
//            Log.d(TAG, "Enabling Mu Board");
//            characteristic = gatt.getService(Mu_SERVICE).getCharacteristic(Mu_WRITE_CHAR);
//            characteristic.setValue(new byte[] {0x01});
//
//            gatt.writeCharacteristic(characteristic);
//            mHandler.sendEmptyMessage(MSG_DISMISS);

//            mConnectedGatt.disconnect();
//            mConnectedGatt = null;
//        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //First enforce Bluetooth is enabled, go to settings if not
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
//            //Bluetooth is disabled
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivity(enableBtIntent);
//            // finish();
//            return;
//        }
//
//        //Check for BLE Support
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
//            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        clearDisplayValues();
//
//    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        //Make sure dialog is hidden
//        mProgress.dismiss();
//        //Cancel any scans in progress
//        mHandler.removeCallbacks(mStopRunnable);
//        mHandler.removeCallbacks(mStartRunnable);
//        if ( mBluetoothAdapter != null ) mBluetoothAdapter.stopLeScan(this);
//    }


//    private Runnable mStopRunnable = new Runnable () {
//        @Override
//        public void run() { stopScan(); }
//    };
//
//    private Runnable mStartRunnable = new Runnable () {
//        @Override
//        public void run() { startBleScan(); }
//    };

//    private void startBleScan() {
//        if ( mBluetoothAdapter != null ) {
//            mBluetoothAdapter.startLeScan(this);
//            setProgressBarIndeterminateVisibility(true);
//            mHandler.postDelayed(mStopRunnable, 2500);
//        }
//    }
//
//    private void stopScan() {
//        if ( mBluetoothAdapter != null ) {
//            mBluetoothAdapter.stopLeScan(this);
//            setProgressBarIndeterminateVisibility(false);
//        }
//    }

    /* BluetoothAdapter.LeScanCallback */
//    @Override
//    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//        Log.i(TAG, "New LE Device: " + device.getName() + " @ " + rssi);
//
//        bleDevices.put(device.hashCode(), device);
//        invalidateOptionsMenu();
//    }

//* HERE IS THE GATT CALLBACK *//

//    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
//
//        /*
//         * Write an activation method that sends command to the sensor board.
//         */
//        private void activateSensorBoard(BluetoothGatt gatt) {
//            BluetoothGattCharacteristic characteristic;
//            Log.d(TAG, "Enabling Ark Board");
//            characteristic = gatt.getService(Ark_SERVICE).getCharacteristic(Ark_WRITE_CHAR);
//            characteristic.setValue(new byte[] {0x10});
//
//            gatt.writeCharacteristic(characteristic);
//            mHandler.sendEmptyMessage(MSG_DISMISS);
//        }
//
//        /*
//        * Read method for the characteristic's value for the sensor board
//        */
//        private void readSensorBoard(BluetoothGatt gatt) {
//            BluetoothGattCharacteristic characteristic;
//            Log.d(TAG, "Reading Ark");
//            characteristic = gatt.getService(Ark_SERVICE).getCharacteristic(Ark_READ_CHAR);
//
//            gatt.readCharacteristic(characteristic);
//        }
//
//        /*
//        * Enable notification of changes on the data/read characteristic for the sensor board
//        */
//        private void setNotifySensorBoard(BluetoothGatt gatt) {
//            BluetoothGattCharacteristic characteristic;
//
//            Log.d(TAG, "Set notify Ark");
//            characteristic = gatt.getService(Ark_SERVICE).getCharacteristic(Ark_READ_CHAR);
//
//            if (characteristic != null) {
//                List<BluetoothGattDescriptor> ll = characteristic.getDescriptors();
//                if (ll.isEmpty()){
//                    Log.d(TAG, "Empty Descriptors");
//                }
//
//                BluetoothGattDescriptor desc = characteristic.getDescriptor(Ark_CONFIG_CHAR);
//                if (desc !=null){
//
//                    //Enable local notifications
//                    gatt.setCharacteristicNotification(characteristic, true);
//
//                    //Enabled remote notifications
//                    desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                    gatt.writeDescriptor(desc);
//                } else {
//                    Log.d(TAG, "Ark receive config descriptor not found!");
//                }
//            } else {
//                Log.d(TAG, "Ark characteristic not found!");
//            }
//        }
//
//
//        //  /* Calibration method for an array of buttons for electrochemical sensors */
//
//        // private void calibrateSensorBoard(BluetoothGatt gatt, int sensor){
//        //     BluetoothGattCharacteristic characteristic;
//        //     Log.d(TAG, "Calibrating Ark Board: "+sensor);
//        //     characteristic = gatt.getService(Ark_SERVICE).getCharacteristic(Ark_WRITE_CHAR);
//        //     if (sensor == ph4) {                            //calibrates pH when in 4.00 solution
//        //       characteristic.setValue(new byte[] {0x20})
//        //     } else if (sensor == ph7) {                     //calibrates pH when in 7.00 solution
//        //       characteristic.setValue(new byte[] {0x21})
//        //     } else if (sensor == ph10) {                    //calibrates pH when in 10.00 solution
//        //       characteristic.setValue(new byte[] {0x22})
//        //     } else if (sensor == ecLow) {                   //calibrates EC when in Low Conductivity solution
//        //       characteristic.setValue(new byte[] {0x30})
//        //     } else if (sensor == ecHigh) {                  //calibrates EC when in High Conductivity solution
//        //       characteristic.setValue(new byte[] {0x31})
//        //     } else if (sensor == temp) {                    //calibrates the electrochemical sensors based on temperature
//        //       characteristic.setValue(new byte[] {0x40})
//        //     }
//        //     gatt.writeCharacteristic(characteristic);
//        //     mHandler.sendEmptyMessage(MSG_DISMISS);
//        // }
//
//
//        @Override
//        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState){
//            Log.d(TAG, "Connection State Change: "+status+" ->"+connectionState(newState));
//            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
//            /* Once connected, discover all services on device so we can read/write their characteristics.*/
//                gatt.discoverServices();
//                mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Discovering Services... "));
//            } else if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED){
//                // If we disconnect, send message to clear the values out of UI
//                mHandler.sendEmptyMessage(MSG_CLEAR);
//            } else if (status != BluetoothGatt.GATT_SUCCESS){
//                //If failure, disconnect
//                gatt.disconnect();
//            }
//        }
//
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            Log.d(TAG, "Services Discovered: " + status);
//            mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Enabling Sensors..."));
//
//            /* With services discovered, we are going to activate the Sensor Board*/
//            activateSensorBoard(gatt);
//        }
//
//        @Override
//        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            //After activating, next we read the initial value
//            readSensorBoard(gatt);
//        }
//
//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            //For each read, pass the data up to the UI thread to update the display
//            if (Ark_READ_CHAR.equals(characteristic.getUuid())) {
//                mHandler.sendMessage(Message.obtain(null, MSG_Ark, characteristic));
//            }
//            //After reading the initial value, we enable notifications
//            setNotifySensorBoard(gatt);
//        }
//
//        @Override
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//        /* After notifications are enabled, all updates from the device on characteristic value changes will be posted here.
//         * Similar to read, we hand these up to the UI thread to update the display.
//         */
//            if (Ark_READ_CHAR.equals(characteristic.getUuid())) {
//                mHandler.sendMessage(Message.obtain(null, MSG_Ark, characteristic));
//            }
//        }
//
//
//        @Override
//        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//            Log.d(TAG, "Remote RSSI: "+rssi);
//        }
//
//        private String connectionState(int status) {
//            switch (status) {
//                case BluetoothProfile.STATE_CONNECTED:
//                    return "Connected";
//                case BluetoothProfile.STATE_DISCONNECTED:
//                    return "Disconnected";
//                case BluetoothProfile.STATE_CONNECTING:
//                    return "Connecting";
//                case BluetoothProfile.STATE_DISCONNECTING:
//                    return "Disconnecting";
//                default:
//                    return String.valueOf(status);
//            }
//        }
//    };



//* A HANDLER TO PROCESS EVENT RESULTS ON THE MAIN THREAD *//

//    private static final int MSG_Ark = 101;
//    private static final int MSG_PROGRESS = 201;
//    private static final int MSG_DISMISS = 202;
//    private static final int MSG_CLEAR = 301;
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage (Message msg) {
//
//            BluetoothGattCharacteristic characteristic;
//
//            switch (msg.what) {
//                case MSG_Ark:
//                    characteristic = (BluetoothGattCharacteristic) msg.obj;
//                    if (characteristic.getValue() == null) {
//                        Log.w(TAG, "Error obtaining Ark values");
//                        return;
//                    }
//                    updateArkValues(characteristic);
//                    break;
//                case MSG_PROGRESS:
//                    mProgress.setMessage((String) msg.obj);
//                    if (!mProgress.isShowing()){
//                        mProgress.show();
//                    }
//                    break;
//                case MSG_DISMISS:
//                    mProgress.hide();
//                    break;
//                case MSG_CLEAR:
//                    clearDisplayValues();
//                    break;
//            }
//
//        }
//    };


//* OLD CODE FOR SENSOR BOARD CONVERSION *//

//    private int convertFromHex(char lchar) {
//        int hv = Character.digit(lchar,16);
//        if ( hv < 0 ) hv = 0;
//        return(hv);
//    }

//    public void updateArkValues(BluetoothGattCharacteristic characteristic) {
//        String sensorReport = "";
//        byte Sensor1[] = characteristic.getValue();
//
//        for (int i = 0; i < Sensor1.length; i++) {
//            sensorReport += Integer.toHexString(0xFF & Sensor1[i]).toUpperCase();
//        }
//
//        Log.d(TAG, sensorReport);
//
//        double Sensa1 = 0.0;
//        double Sensa2 = 0.0;
//        double Sensa3 = 0.0;
//
//        boolean start_string = true;
//        int n = sensorReport.length();
//        int len = 0;
//
//        String currentString = "";
//
//        int metricIndex = 0;
//
//        for ( int i = 0; i < n; i++ ) {
//
//            if ( start_string ) {
//                char lenchar = sensorReport.charAt(i);
//                len = convertFromHex(lenchar);
//                currentString = "";
//                if ( len != 0 ) {
//                    start_string = false;
//                }
//                metricIndex++;
//            } else {
//                char nextChar = sensorReport.charAt(i);
//                if ( nextChar != 'F' ) {
//                    currentString += nextChar;
//                    if  ( currentString.length() == len ) {
//                        currentString += ".";
//                    }
//                } else {
//                    start_string = true;
//                    double dtmp = (new Double(currentString)).doubleValue();
//                    if ( metricIndex == 1 ) Sensa1 = dtmp;
//                    if ( metricIndex == 2 ) Sensa2 = dtmp;
//                    if ( metricIndex == 3 ) Sensa3 = dtmp;
//                }
//            }
//        }
//
//        mSensor1.setText(String.format("%.3f", Sensa1));
////        mSensor2.setText(String.format("%.3f", Sensa2));
////        mSensor3.setText(String.format("%.3f", Sensa3));
//
//
//        socket_emission(Sensa1);
//
///*
//        JSONObject metric_quantity_map = new JSONObject();
//        metric_quantity_map.put("obj_class","Sensa");
//        metric_quantity_map.put("Sensa1",Sensa1);
//        metric_quantity_map.put("Sensa2",Sensa2);
//        metric_quantity_map.put("Sensa3",Sensa3);
//        socket_emission_collection(metric_quantity_map);
//*/
//
//    }



//*HTTP POST TO WEB SERVER */

/*HTTP POST*/
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import android.net.ConnectivityManager;
//import java.io.InputStream;
//import java.io.InputStreamReader;

//        // 1. create HttpClient
//        HttpClient httpclient = new DefaultHttpClient();
//
//        // 2. make POST request to the given URL
//        HttpPost httpPost = new HttpPost("http://arkreactor.com");
//
//        String json = "";
//
//        // 3. build jsonObject
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.accumulate("value", Sensa1);
//
//        // 4. convert JSONObject to JSON to String
//        json = jsonObject.toString();
//
//        // 5. set json to StringEntity
//        StringEntity se = new StringEntity(json);
//
//        // 6. set httpPost Entity
//        httpPost.setEntity(se);
//
//        // 7. Set some headers to inform server about the type of the content
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        // 8. Execute POST request to the given URL
//        HttpResponse httpResponse = httpclient.execute(httpPost);


}