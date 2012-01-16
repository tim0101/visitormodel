package com.hailang.visitormodel.activity;

import com.hailang.visitormodel.R;
import com.hailang.visitormodel.R.layout;
import com.hailang.visitormodel.adapter.AppListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class VisitorModelActivity extends Activity {
    protected ListView appListView;
    protected AppListAdapter appListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        appListView=(ListView)findViewById(R.id.lt_applist);
        appListAdapter=new AppListAdapter(this);
        appListView.setAdapter(appListAdapter);
    }
}