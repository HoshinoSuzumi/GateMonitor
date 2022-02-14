package moe.ibox.gatemonitor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;
import com.nle.mylibrary.forUse.zigbee.FourChannelValConvert;
import com.nle.mylibrary.forUse.zigbee.Zigbee;
import com.nle.mylibrary.transfer.DataBusFactory;

import java.util.Formatter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextureView monitorView;
    private Button btn_cam_stop, btn_cam_up, btn_cam_down, btn_cam_left, btn_cam_right;
    private Button btn_screenshot, btn_screenshots_list;
    private Button btn_door_open, btn_door_close;
    private TextView tv_temperature, tv_humidity, tv_co2, tv_noise;

//    TODO: IPCamera 库有些问题，搁置
//    private CameraManager cameraManager;

    private Modbus4150 modbus4150;
    private Zigbee zigbeeSerial;

    private Thread QuaChannelFetchingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initInstances();

//        cameraManager.setupInfo(monitorView, "admin", "12345678", "192.168.0.101", "channel1");
//        cameraManager.openCamera();

        modbus4150 = new Modbus4150(DataBusFactory.newSocketDataBus("172.16.11.15", 6001));
        zigbeeSerial = new Zigbee(DataBusFactory.newSocketDataBus("172.16.11.15", 6002));

        QuaChannelFetchingThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modbus4150.stopConnect();
        zigbeeSerial.stopConnect();
        QuaChannelFetchingThread = null;
    }

    private void initUI() {
        monitorView = findViewById(R.id.surfaceView_monitor);
        /* Camera control */
        btn_cam_stop = findViewById(R.id.button_cam_stop);
        btn_cam_up = findViewById(R.id.button_cam_up);
        btn_cam_down = findViewById(R.id.button_cam_down);
        btn_cam_left = findViewById(R.id.button_cam_left);
        btn_cam_right = findViewById(R.id.button_cam_right);
        btn_cam_stop.setOnClickListener(new OnCamButtonClickListener());
        btn_cam_up.setOnClickListener(new OnCamButtonClickListener());
        btn_cam_down.setOnClickListener(new OnCamButtonClickListener());
        btn_cam_left.setOnClickListener(new OnCamButtonClickListener());
        btn_cam_right.setOnClickListener(new OnCamButtonClickListener());
        /* Screenshot control */
        btn_screenshot = findViewById(R.id.button_screenshot);
        btn_screenshots_list = findViewById(R.id.button_screenshots_list);
        btn_screenshot.setOnClickListener(new OnDoorAndScreenshotClickListener());
        btn_screenshots_list.setOnClickListener(new OnDoorAndScreenshotClickListener());
        /* Doors control */
        btn_door_open = findViewById(R.id.button_door_open);
        btn_door_close = findViewById(R.id.button_door_close);
        btn_door_open.setOnClickListener(new OnDoorAndScreenshotClickListener());
        btn_door_close.setOnClickListener(new OnDoorAndScreenshotClickListener());
        /* Data fields */
        tv_temperature = findViewById(R.id.textView_temp);
        tv_humidity = findViewById(R.id.textView_Humi);
        tv_co2 = findViewById(R.id.textView_co2);
        tv_noise = findViewById(R.id.textView_noise);
    }

    private QuaChannelData fetchQuaChannelData() throws Exception {
        return new QuaChannelData(
                Double.parseDouble(new Formatter().format("%.2f", FourChannelValConvert.getTemperature(zigbeeSerial.getFourEnter()[0])).toString()),
                Double.parseDouble(new Formatter().format("%.2f", FourChannelValConvert.getHumidity(zigbeeSerial.getFourEnter()[1])).toString()),
                Double.parseDouble(new Formatter().format("%.2f", FourChannelValConvert.getCO2(zigbeeSerial.getFourEnter()[2])).toString()),
                Double.parseDouble(new Formatter().format("%.2f", FourChannelValConvert.getNoice(zigbeeSerial.getFourEnter()[3])).toString())
        );
    }

    private void updateQuaChannelDataOnUi(QuaChannelData quaChannelData) {
        runOnUiThread(() -> {
            tv_temperature.setText(String.valueOf(quaChannelData.getTemperature()));
            tv_humidity.setText(String.valueOf(quaChannelData.getHumidity()));
            tv_co2.setText(String.valueOf(quaChannelData.getCo2()));
            tv_noise.setText(String.valueOf(quaChannelData.getNoise()));
        });
    }

    @SuppressWarnings("BusyWait")
    private void initInstances() {
//        cameraManager = CameraManager.getInstance();
        QuaChannelFetchingThread = new Thread(() -> {
            while (true) {
                try {
                    QuaChannelData quaChannelData = fetchQuaChannelData();
//                    QuaChannelData quaChannelData = new QuaChannelData(
//                            Double.parseDouble(new Formatter().format("%.2f", new Random().nextDouble() * 100).toString()),
//                            Double.parseDouble(new Formatter().format("%.2f", new Random().nextDouble() * 100).toString()),
//                            Double.parseDouble(new Formatter().format("%.2f", new Random().nextDouble() * 100).toString()),
//                            Double.parseDouble(new Formatter().format("%.2f", new Random().nextDouble() * 100).toString())
//                    );
                    updateQuaChannelDataOnUi(quaChannelData);
                    /* Insert into database */
                    QuaRecordDao quaRecordDao = new QuaRecordDao(MainActivity.this);
                    quaRecordDao.insertRecord(quaChannelData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class OnCamButtonClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.button_cam_stop:
//                    cameraManager.controlDir(PTZ.Stop);
//                    break;
//                case R.id.button_cam_up:
//                    cameraManager.controlDir(PTZ.Up);
//                    break;
//                case R.id.button_cam_down:
//                    cameraManager.controlDir(PTZ.Down);
//                    break;
//                case R.id.button_cam_left:
//                    cameraManager.controlDir(PTZ.Left);
//                    break;
//                case R.id.button_cam_right:
//                    cameraManager.controlDir(PTZ.Right);
//                    break;
//                default:
//                    throw new IllegalStateException("Unexpected value: " + v.getId());
//            }
        }
    }

    private class OnDoorAndScreenshotClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_screenshot:
//                    cameraManager.capture(getFilesDir().getPath(), "test.png");
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, RecordsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_screenshots_list:
                    break;
                case R.id.button_door_open:
                    break;
                case R.id.button_door_close:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    }
}