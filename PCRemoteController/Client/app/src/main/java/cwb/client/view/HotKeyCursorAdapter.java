package cwb.client.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.pc_chen.myapplication_kcsj_cwb.R;

import cwb.client.database.DataBaseHelper;

/**
 * Created by PC_chen on 2019/3/27.
 */

public class HotKeyCursorAdapter extends CursorAdapter {
    Context context;
    TextView tv_key;
    TextView tv_value;

    public HotKeyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_lv_layout,null,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tv_key = view.findViewById(R.id.tv_key);
        tv_value = view.findViewById(R.id.tv_value);
        String key = cursor.getString(cursor.getColumnIndex(DataBaseHelper.HOT_KEY));
        String value = cursor.getString(cursor.getColumnIndex(DataBaseHelper.HOT_VALUE));

        tv_key.setText(key);
        tv_value.setText(value);
    }
}
