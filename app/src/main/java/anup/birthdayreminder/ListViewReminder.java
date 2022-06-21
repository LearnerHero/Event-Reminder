package anup.birthdayreminder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewReminder extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper;

    List<Reminder> reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reminder_list_view);
        reminder = new ArrayList<Reminder>();
        databaseHelper = new DatabaseHelper(this);
        Cursor c = databaseHelper.getAllData();

        while(c.moveToNext()) {
            reminder.add(new Reminder(c.getInt(0), c.getString(1),c.getInt(2),c.getString(3)));
        }

        listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), reminder);
        listView.setAdapter(customAdapter);
    }

    public static  class ViewHolder {
        TextView textView;
        Button editButton;
        Button deleteButton;
    }

    class CustomAdapter extends ArrayAdapter {
        List<Reminder> reminderObject;
        Context context;

        public CustomAdapter(Context context, List<Reminder> reminder) {
            super(context, R.layout.listview_individual_item_layout);
            this.context = context;
            this.reminderObject = reminder;

        }

        @Override
        public int getCount() {
            return reminder.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RecyclerView.ViewHolder holder;
            final int counter = i;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listview_individual_item_layout, viewGroup, false);

            Button edit = (Button) view.findViewById(R.id.editReminder);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,RemindScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);






                }
            });

            Button delete = (Button) view.findViewById(R.id.deleteReminder);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseHelper.deleteRecord(reminder.get(counter).getId());
                    Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
                    recreate();

                }
            });



            TextView textView = (TextView) view.findViewById(R.id.reminderText);
            textView.setText(reminder.get(i).getReminder());

            return view;
        }
    }
}


