package example.android.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by N.Shintaku on 2015/11/07.
 */
public class GameView extends View {

    // 背景画像を格納する変数を宣言
    private Bitmap bgImage;

    // アニメーションのフレーム数
    private int frameIndex = 0;

    // プレイヤーのX座標
    private int playerX;
    // プレイヤーのY座標
    private int playerY;

    // 画面(Canvas)中央のX座標
    private int canvasCX;
    // 画面(Canvas)中央のY座標
    private int canvasCY;

    private Bitmap rosen;

    int x=0;
    // コンストラクタ
    public GameView(Context context) {
        super(context);
        // リソースオブジェクトを作成
        Resources res = this.getContext().getResources();

        // 背景画像「bg.jpg」をビットマップに変換して変数「bgImage」に入れる
        bgImage = BitmapFactory.decodeResource(res, R.drawable.test_long);

        rosen = BitmapFactory.decodeResource(res, R.drawable.test_long);
    }

    // スパークラス(継承元)の「onDraw」メソッドをオーバーライドする
    @Override
    public void onDraw(Canvas canvas){
        // 画面(Canvas)中央のX座標を取得
        canvasCX = canvas.getWidth() / 2;
        // 画面(Canvas)中央のY座標を取得
        canvasCY = canvas.getHeight() / 2;

        // 画面(Canvas)サイズに応じて背景画像を拡大する
        bgImage = Bitmap.createScaledBitmap(bgImage,
                canvas.getWidth() * 2, canvas.getHeight(), true);

        // 「playScene」メソッドを実行
        playScene(canvas);
    }

    // 「playScene」メソッド
    public void playScene(Canvas canvas){
        // 画面(Canvas)に背景画像を描画
        canvas.drawBitmap(bgImage, 0, 0, null);

        if(x>-500)
            x-=15;
         rosen = Bitmap.createScaledBitmap(rosen, 4000, 500, true);

        canvas.drawBitmap(rosen,x,canvas.getHeight()-rosen.getHeight(),null);
    }
}
