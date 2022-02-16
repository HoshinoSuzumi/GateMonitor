package moe.ibox.gatemonitor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Objects;

public class RecordsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private EditText editText_from, editText_to;
//    private CheckBox checkBox_temp, checkBox_humi, checkBox_co2, checkBox_noise;

    private final ArrayList<String> filteredColumns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        editText_from = findViewById(R.id.editTextDate_from);
        editText_to = findViewById(R.id.editTextDate_to);
        editText_from.setOnClickListener(new DatePickOnClickListener());
        editText_from.setOnFocusChangeListener(new MyFocusChangeListener());
        editText_to.setOnClickListener(new DatePickOnClickListener());
        editText_to.setOnFocusChangeListener(new MyFocusChangeListener());
//        checkBox_temp = findViewById(R.id.checkBox_temp);
//        checkBox_humi = findViewById(R.id.checkBox_humi);
//        checkBox_co2 = findViewById(R.id.checkBox_co2);
//        checkBox_noise = findViewById(R.id.checkBox_noise);

        filteredColumns.add("temperature");
        filteredColumns.add("humidity");
        filteredColumns.add("co2");
        filteredColumns.add("noise");

//        checkBox_temp.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        checkBox_humi.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        checkBox_co2.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        checkBox_noise.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        Objects.requireNonNull(getSupportActionBar()).setTitle("历史数据");

        recyclerView = findViewById(R.id.recyclerView_records);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateData();
    }

    private void updateData(String[] columns, String from, String to) {
        recyclerView.setAdapter(new RecordsAdapter(new QuaRecordDao(RecordsActivity.this).queryQuaChannelData(columns, from, to)));
    }

    private void updateData(String from, String to) {
        recyclerView.setAdapter(new RecordsAdapter(new QuaRecordDao(RecordsActivity.this).queryQuaChannelData(from, to)));
    }

    private void updateData() {
        recyclerView.setAdapter(new RecordsAdapter(new QuaRecordDao(RecordsActivity.this).queryQuaChannelData()));
    }

    public void buttonQueryClick(View view) {
        if (!editText_from.getText().toString().equals("") && !editText_to.getText().toString().equals("")) {
            updateData(editText_from.getText().toString(), editText_to.getText().toString());
        }
    }

    class MyFocusChangeListener implements View.OnFocusChangeListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ((EditText) v).setInputType(InputType.TYPE_NULL);
                switch (v.getId()) {
                    case R.id.editTextDate_from:
                        new DatePickOnClickListener().datePick(editText_from);
                        break;
                    case R.id.editTextDate_to:
                        new DatePickOnClickListener().datePick(editText_to);
                        break;
                }
            }
        }
    }

    class DatePickOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            datePick((EditText) v);
        }

        @SuppressLint("SetTextI18n")
        public void datePick(EditText elem) {
            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
            new DatePickerDialog(
                    RecordsActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        elem.setText(year + "-" + new Formatter().format("%02d", (month + 1)) + "-" + new Formatter().format("%02d", dayOfMonth));
                        new TimePickerDialog(
                                RecordsActivity.this,
                                (view1, hourOfDay, minute) -> {
                                    elem.append(" " + new Formatter().format("%02d", hourOfDay) + ":" + new Formatter().format("%02d", minute) + ":00");
//                                    updateData();
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                        ).show();
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

//    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
//        @SuppressLint("NonConstantResourceId")
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            switch (buttonView.getId()) {
//                case R.id.checkBox_temp:
//                    if (isChecked) {
//                        if (!filteredColumns.contains("temperature"))
//                            filteredColumns.add("temperature");
//                    } else
//                        filteredColumns.remove("temperature");
//                    break;
//                case R.id.checkBox_humi:
//                    if (isChecked) {
//                        if (!filteredColumns.contains("humidity")) filteredColumns.add("humidity");
//                    } else
//                        filteredColumns.remove("humidity");
//                    break;
//                case R.id.checkBox_co2:
//                    if (isChecked) {
//                        if (!filteredColumns.contains("co2")) filteredColumns.add("co2");
//                    } else
//                        filteredColumns.remove("co2");
//                    break;
//                case R.id.checkBox_noise:
//                    if (isChecked) {
//                        if (!filteredColumns.contains("noise")) filteredColumns.add("noise");
//                    } else
//                        filteredColumns.remove("noise");
//                    break;
//            }
//            System.out.println(Arrays.toString(filteredColumns.toArray()));
//        }
//    }
}