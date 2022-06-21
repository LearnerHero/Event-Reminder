package anup.birthdayreminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class RemindScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper dbHelper;
    private Button dateButton, timeButton;
    private CheckBox checkBox;
    private EditText phoneNumberField, reminderNote;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private long currentTime;
    private Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_action_layout);

    }

    public void addReminder(View view) {

        setContentView(R.layout.reminder_add_layout);
        phoneNumberField = (EditText) findViewById(R.id.phoneNumberField);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        reminderNote = (EditText) findViewById(R.id.editNote);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        checkBox.setOnClickListener(this);

        dbHelper = new DatabaseHelper(getBaseContext());
    }


    public void viewReminder(View view) {
        Intent intent = new Intent(this, ListViewReminder.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClick(View view) {
        if (view == dateButton) {

            cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int year,
                                              int monthOfYear, int dayOfMonth) {
                            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                            cal.set(Calendar.MONTH, datePicker.getMonth());
                            cal.set(Calendar.YEAR, datePicker.getYear());

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if(view == timeButton) {

            cal = Calendar.getInstance();
            mHour = cal.get(Calendar.HOUR_OF_DAY);
            mMinute = cal.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay,
                                              int minute) {

                            cal.set(Calendar.HOUR, timePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());


                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }

        else if(view == checkBox) {
            phoneNumberField.setVisibility(View.VISIBLE);

        }
    }

    public void saveData(View view) {
         dbHelper = new DatabaseHelper(this);
         if(phoneNumberField == null) {
             dbHelper.insertData(reminderNote.getText().toString(), String.valueOf(cal.getTimeInMillis()), "");
             Toast.makeText(this,"Reminder Added!", Toast.LENGTH_LONG).show();
             setReminder(cal.getTimeInMillis());
             Intent intent = new Intent(this, ListViewReminder.class);
             startActivity(intent);

         } else {
             dbHelper.insertData(reminderNote.getText().toString(), String.valueOf(cal.getTimeInMillis()), phoneNumberField.getText().toString());
             Toast.makeText(this,"Reminder Added!", Toast.LENGTH_LONG).show();
             setReminder(cal.getTimeInMillis());
             Intent intent = new Intent(this, ListViewReminder.class);
             startActivity(intent);

         }
    }

    public void setReminder(long time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReciever.class);
        intent.putExtra("notificationContent", reminderNote.getText().toString());

        if(phoneNumberField.getText().toString() != null)
            intent.putExtra("phoneNumber", phoneNumberField.getText().toString());


        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);

    }

}

