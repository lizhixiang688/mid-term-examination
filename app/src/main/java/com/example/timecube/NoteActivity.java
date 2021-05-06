package com.example.timecube;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

   private TimePickerView pvTime,pvTime2;
   private EditText edit_note;
   private long remindtime,deadline;
   private SimpleDateFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        View decorview =getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        edit_note=(EditText)findViewById(R.id.edit_note);

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2021, 4, 5);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);
        pvTime=new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            TextView btn = (TextView) v;
            btn.setText(getTimes(date));
            remindtime=date.getTime();
        }
    }).setType(new boolean[]{true, true, true, true, true, true})
            .setLabel("年", "月", "日", "时", "分", "秒")
            .isCenterLabel(true)
            .setDividerColor(Color.DKGRAY)
            .setContentSize(16)//字号
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setDecorView(null)
            .build();

        pvTime2=new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                TextView btn = (TextView) v;
                btn.setText(getTimes(date));
                deadline=date.getTime();
            }
        }).setType(new boolean[]{true, true, false, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(16)//字号
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
    }

    private String getTimes(Date date) {//年月日时分秒格式
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void back(View view) {
        finish();
    }

    public void settime(View view) {
        if (pvTime!=null){
            pvTime.show(view);
        }
    }
    public void settime_2(View view) {
        if (pvTime2!=null){
            pvTime2.show(view);
        }
    }
    public void ok(View view) {
        String note=edit_note.getText().toString();
        if(note.isEmpty()||deadline==0||remindtime==0){
            Toast.makeText(this,"待办和时间不能为空哦",Toast.LENGTH_SHORT).show();
        }else{
            if(LitePal.getDatabase()==null){
                LitePal.getDatabase();
            }
          Note bean=new Note();
          bean.setNote(note);
          bean.setRemind_time(remindtime);
          bean.setDeadline(deadline);
          bean.save();
          Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();

           startService(new Intent(this,MyService.class));


           int hour=Integer.parseInt(format.format(remindtime).substring(11,13));
           int minute=Integer.parseInt(format.format(remindtime).substring(14,16));
           int second=Integer.parseInt(format.format(remindtime).substring(17,19));
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,second);
            Intent intent1 = new Intent();
            intent1.setAction("123");
            intent1.putExtra("message",bean.getNote());
            PendingIntent pi = PendingIntent.getBroadcast(this,0,intent1,0);

            

            am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);   //精准


          finish();
        }
    }



}