package example.android.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by N.Shintaku on 2015/11/07.
 */
public class GameView extends View {

    String station[] = {"新青森","七戸十和田","八戸","いわて沼宮内","盛岡","新花巻","北上","水沢江刺","一ノ関","くりこま高原","古川","仙台","白石蔵王","福島","郡山","新白河","那須塩原","宇都宮","小山","大宮","上野","東京"};

    // 背景画像を格納する変数を宣言
    private Bitmap bgImage,bgImageEnd;

    // アニメーションのフレーム数
    private int frameIndex = 0;

    // プレイヤーのX座標
    private int playerX;
    // プレイヤーのY座標
    private int playerY;

    private Bitmap train;
    private Paint paint = new Paint();
    int x=0;
    int trainY = 490;
    int cell = 5;
    // コンストラクタ
    public GameView(Context context) {
        super(context);
        // リソースオブジェクトを作成
        Resources res = this.getContext().getResources();

        // 背景画像をビットマップに変換して変数「bgImage」に入れる
        bgImage = BitmapFactory.decodeResource(res, R.drawable.background);
        bgImageEnd = BitmapFactory.decodeResource(res, R.drawable.background_end);

        train = BitmapFactory.decodeResource(res, R.drawable.hayabusa);

    }

    // スパークラス(継承元)の「onDraw」メソッドをオーバーライドする
    @Override
    public void onDraw(Canvas canvas){
        // 画面(Canvas)中央のX座標を取得
        // 画面(Canvas)中央のY座標を取得

        // 画面(Canvas)サイズに応じて背景画像を拡大する
        bgImage = Bitmap.createScaledBitmap(bgImage,
                canvas.getWidth(), canvas.getHeight(), true);
        bgImageEnd = Bitmap.createScaledBitmap(bgImageEnd,
                canvas.getWidth(), canvas.getHeight(), true);
        train = Bitmap.createScaledBitmap(train,120,60,true);
        // 「playScene」メソッドを実行
        playScene(canvas);
    }

    // 「playScene」メソッド
    public void playScene(Canvas canvas){
        int width = (int) canvas.getWidth();
        // 画面(Canvas)に背景画像を描画
        int distance = cell * (width/4);
        if(x<distance)
            x+=30;
        for(int i=0;i<2;i++) {
            canvas.drawBitmap(bgImage, width * i - x, 0, null);
            canvas.drawBitmap(bgImageEnd, width * (i + 1) - x, 0, null);

        }
        for(int i=0;i<station.length;i++){
            canvas.drawText(station[i],+(width/4)*i-x, 410,paint);
        }
        paint.setTextSize(24);
        if(trainY == 490 )
            trainY = 485;
        else
            trainY = 490;
        canvas.drawBitmap(train,20,trainY,null);
    }
}
