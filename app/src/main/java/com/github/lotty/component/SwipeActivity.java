package com.github.lotty.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.github.component.SwipeLayout;
import com.github.lotty.R;
import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {

  private static List<String> sList = new ArrayList<>();

  static {
    sList.add("风急天高猿啸哀");
    sList.add("渚清沙白鸟飞回");
    sList.add("无边落木萧萧下");
    sList.add("不尽长江滚滚来");
    sList.add("万里悲秋常作客");
    sList.add("百年多病独登台");
    sList.add("艰难苦恨繁霜鬓");
    sList.add("潦倒新停浊酒杯");
    sList.add("朝辞白帝彩云间");
    sList.add("千里江陵一日还");
    sList.add("两岸猿声啼不住");
    sList.add("轻舟已过万重山");
    sList.add("风急天高猿啸哀");
    sList.add("渚清沙白鸟飞回");
    sList.add("无边落木萧萧下");
    sList.add("不尽长江滚滚来");
    sList.add("万里悲秋常作客");
    sList.add("百年多病独登台");
    sList.add("艰难苦恨繁霜鬓");
    sList.add("潦倒新停浊酒杯");
    sList.add("朝辞白帝彩云间");
    sList.add("千里江陵一日还");
    sList.add("两岸猿声啼不住");
    sList.add("轻舟已过万重山");
    sList.add("风急天高猿啸哀");
    sList.add("渚清沙白鸟飞回");
    sList.add("无边落木萧萧下");
    sList.add("不尽长江滚滚来");
    sList.add("万里悲秋常作客");
    sList.add("百年多病独登台");
    sList.add("艰难苦恨繁霜鬓");
    sList.add("潦倒新停浊酒杯");
    sList.add("朝辞白帝彩云间");
    sList.add("千里江陵一日还");
    sList.add("两岸猿声啼不住");
    sList.add("轻舟已过万重山");
  }

  private SwipeLayout swipeLayout;
  private SwipeLayout.SwipeListener swipeListener = new SwipeLayout.SwipeListener() {
    @Override
    public void onRefreshing() {
      swipeLayout.postDelayed(new Runnable() {
        @Override
        public void run() {
          swipeLayout.notifySwipeFinished();
        }
      }, 3000);
      Log.e("swipe", "onRefreshing");
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swipe);
    swipeLayout = findViewById(R.id.swipe);
    findViewById(R.id.start).setOnClickListener(this);
    findViewById(R.id.finish).setOnClickListener(this);
    swipeLayout.setSwipeListener(swipeListener);
    SwipeAdapter adapter = new SwipeAdapter(this);
    androidx.recyclerview.widget.RecyclerView body = (RecyclerView) swipeLayout.getBody();
    body.setAdapter(adapter);

    adapter.addData(sList);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start:
        swipeLayout.notifySwipeStarted();
        break;
      case R.id.finish:
        swipeLayout.notifySwipeFinished();
        break;
      default:
        break;
    }
  }

  private static class SwipeAdapter extends RecyclerView.Adapter<SwipeHolder> {

    private List<String> dataList = new ArrayList<>();

    private Context ctx;

    public SwipeAdapter(Context ctx) {
      this.ctx = ctx;
    }

    public void addData(List<String> data) {
      dataList.addAll(data);
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = View.inflate(ctx, R.layout.body_item_layout, null);
      return new SwipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeHolder holder, final int position) {
      holder.content.setText(dataList.get(position));
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(ctx, dataList.get(position), Toast.LENGTH_SHORT).show();
        }
      });
    }

    @Override
    public int getItemCount() {
      return dataList.size();
    }
  }

  private static class SwipeHolder extends RecyclerView.ViewHolder {
    TextView content;

    public SwipeHolder(@NonNull View itemView) {
      super(itemView);
      content = itemView.findViewById(R.id.content);
    }
  }
}
