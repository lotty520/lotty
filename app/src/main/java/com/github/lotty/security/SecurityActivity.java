package com.github.lotty.security;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.lotty.R;
import com.github.lotty.mnn.TextFilter;

public class SecurityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        TextFilter.init(this, "lotty");
    }

    public boolean check(View view) {
        String target = "\uD83C\uDF07澳门博监局将关闭线上博彩\n" +
                "【威尼斯人VIP集团】\n" +
                "官方通知：太阳城、金沙、葡京、银河、永利、威尼斯人普通版，将在7月15号全部关闭合并到｛威尼斯人VIP集团｝凡是在7月10前向客服提供在五大网站存款一万以上的记录，可领取存款额5%到35%彩金。彩金最高50万封顶。\n" +
                "\n" +
                "百万提款秒到.大额无忧！\n" +
                "\uD83C\uDD70️ APP:www.7822666.com\n" +
                "\uD83C\uDD71️ 网页版:www.9871666.com\n" +
                "✨✨✨✨✨\n" +
                "\uD83D\uDCB0注册即送6️⃣6️⃣6️⃣\n" +
                "✨✨✨✨✨\n" +
                "\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\n" +
                "❇️每周六会员日千万现金大派送\n" +
                "❇️积分商城.免费兑换奢华大礼\n" +
                "❇️诚邀代理.洗码返佣.不论输赢\n" +
                "❤️更多优惠.等你参与\n" +
                "\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\uD83D\uDD38\n" +
                "\uD83C\uDF81温馨提示:免费回复两条任意内容，即可点击网址领取6️⃣6️⃣6️⃣";
        Log.e("wh", "target:\n" + target);
        long begin = System.currentTimeMillis();
        Log.e("wh", "hit: " + TextFilter.check(target));
        Log.e("wh", "全检测耗时: " + (System.currentTimeMillis() - begin));
        return false;
    }
}
