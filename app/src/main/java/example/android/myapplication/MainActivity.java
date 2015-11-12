package example.android.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    // view(「view」オブジェクトを格納する変数)の宣言
    private View view;

    // ハンドラを作成
    private Handler handler = new Handler();
    // ビューの再描画間隔(ミリ秒)
    private final static long MSEC = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int recent_cell = 23;
        int cell = 25;

        // 「GameView」オブジェクト(ビュー)の作成
        view = new GameView(this,recent_cell,cell);

        // アクティビティにビューを組み込む
        setContentView(view);

        // ビュー再描画タイマー
        // タイマーを作成
        Timer timer = new Timer(false);
        // 「MSEC」ミリ秒おきにタスク(TimerTask)を実行
        timer.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        // ビューを再描画
                        view.invalidate();
                    }
                });
            }
        }, 0, MSEC);
    }
}
