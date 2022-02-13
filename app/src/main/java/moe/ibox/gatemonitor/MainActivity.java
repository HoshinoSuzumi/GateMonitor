package moe.ibox.gatemonitor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import nledu.com.ipcamera.CameraManager;
import nledu.com.ipcamera.PTZ;

import androidx.appcompat.app.AppCompatActivity;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;
import com.nle.mylibrary.forUse.zigbee.Zigbee;
import com.nle.mylibrary.transfer.DataBusFactory;

public class MainActivity extends AppCompatActivity {


    private TextureView monitorView;
    private Button btn_cam_stop, btn_cam_up, btn_cam_down, btn_cam_left, btn_cam_right;
    private Button btn_screenshot, btn_screenshots_list;
    private Button btn_door_open, btn_door_close;

    private CameraManager cameraManager;

    private Modbus4150 modbus4150;
    private Zigbee zigbeeSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initInstances();

        cameraManager.setupInfo(monitorView, "admin", "12345678", "192.168.0.101", "channel1");
        cameraManager.openCamera();

        modbus4150 = new Modbus4150(DataBusFactory.newSocketDataBus("0.0.0.0", 6001));
        zigbeeSerial = new Zigbee(DataBusFactory.newSocketDataBus("0.0.0.0", 6004));

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
    }

    private void initInstances() {
        cameraManager = CameraManager.getInstance();
    }

    private class OnCamButtonClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_cam_stop:
                    cameraManager.controlDir(PTZ.Stop);
                    break;
                case R.id.button_cam_up:
                    cameraManager.controlDir(PTZ.Up);
                    break;
                case R.id.button_cam_down:
                    cameraManager.controlDir(PTZ.Down);
                    break;
                case R.id.button_cam_left:
                    cameraManager.controlDir(PTZ.Left);
                    break;
                case R.id.button_cam_right:
                    cameraManager.controlDir(PTZ.Right);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    }

    private class OnDoorAndScreenshotClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_screenshot:
                    cameraManager.capture(getFilesDir().getPath(), "test.png");
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