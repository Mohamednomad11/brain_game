package com.nomad.mybrainmemory.jigsawpuzzle.cutter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.jigsawpuzzle.models.PuzzlePiece;
import com.nomad.mybrainmemory.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;


public class ImageCutter extends AppCompatActivity {

    private Bitmap originalImage;
    private CutMap cutMap;
    private int horizontalStep;
    private int verticalStep;
    private float horizontalScale;
    private float verticalScale;
    private Paint cutterPaint;
    private String path;

    public ImageCutter(Bitmap originalImage, CutMap cutMap) {
        this.originalImage = originalImage;
        this.cutMap = cutMap;
        this.horizontalStep = originalImage.getWidth() / cutMap.getHorizontalResolution();
        this.verticalStep = originalImage.getHeight() / cutMap.getVerticalResolution();
        this.horizontalScale = horizontalStep / 100f;
        this.verticalScale = verticalStep / 100f;

        this.cutterPaint = new Paint();
        cutterPaint.setColor(Color.RED);
        cutterPaint.setStrokeWidth(2);
        cutterPaint.setStyle(Paint.Style.STROKE);
    }

    public PuzzlePiece[][] cutImage(Activity mActivity) {
        clearExistingPuzzlePieces(mActivity);
        PuzzlePiece[][] puzzlePieces = new PuzzlePiece[cutMap.getHorizontalResolution()][cutMap.getVerticalResolution()];
        for (int i = 0; i < cutMap.getHorizontalResolution(); i++) {
            for (int j = 0; j < cutMap.getVerticalResolution(); j++) {
                PieceCurveSet pieceCurveSet = cutMap.getCurveSetForPiece(i, j);
                Bitmap pieceMask = drawSinglePuzzlePieceMask(i, j, pieceCurveSet);
                Bitmap maskedPiece = maskPuzzlePiece(pieceMask);
                Rect pieceBorder = PuzzlePieceBorderFinder.getBorders(pieceCurveSet,
                        new Point(i * horizontalStep, j * verticalStep), horizontalStep, verticalStep);
                Pair<Integer, Integer> pieceOffset = PuzzlePieceBorderFinder.getOffsets(pieceCurveSet, horizontalStep, verticalStep);
                Bitmap cuttedPiece = cutPuzzlePiece(maskedPiece, pieceBorder);

//                saveSDcard(i,j,cuttedPiece,mActivity);
                saveInternalStorage(i,j,cuttedPiece,mActivity);

                puzzlePieces[i][j] = new PuzzlePiece(
                        cuttedPiece,path,
                        new Point(i * horizontalStep + horizontalStep / 2, j * verticalStep + verticalStep/ 2),
                        new Point(horizontalStep / 2 + pieceOffset.first, verticalStep/ 2 + pieceOffset.second),
                        pieceCurveSet,
                        pieceBorder.width(),
                        pieceBorder.height());
            }
        }
        return puzzlePieces;
    }

    private void saveSDcard(int i, int j, Bitmap cuttedPiece, Activity mActivity)
    {

        ContentValues values = new ContentValues();

        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "sav"+i+j+".png");       //file name
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");        //file extension, will automatically add to file
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/puzzles/");     //end "/" is not mandatory

        String directoryPath = MediaStore.Files.getContentUri("external").getPath() + Environment.DIRECTORY_DOCUMENTS + "/puzzles";
        Log.e("ImageCutter",directoryPath);
        FileUtils.deleteFilesInDirectory(directoryPath);

        Uri uri = mActivity.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!




        try {

            OutputStream outputStream = mActivity.getContentResolver().openOutputStream(uri);

//            outputStream.write("This is menu category data.".getBytes());

            cuttedPiece.compress(Bitmap.CompressFormat.PNG,0,outputStream);

            outputStream.close();

            path=uri.getPath();

            System.out.println("finalpath"+path);

            System.out.println("finalpath"+path);
        } catch (Exception e) {
            e.printStackTrace();
        }




//        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/puzzles/sav"+i+j+".png");
//        try {
//            file.createNewFile();
//            FileOutputStream ostream = new FileOutputStream(file);
//            cuttedPiece.compress(Bitmap.CompressFormat.PNG,0,ostream);
//            ostream.close();
//            path=file.getAbsolutePath();
//
//            System.out.println("finalpath"+path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void saveInternalStorage(int i, int j, Bitmap cuttedPiece, Context context) {
        String fileName = "sav" + i + j + ".png";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            cuttedPiece.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.close();

            path = context.getFilesDir().getPath() + File.separator + fileName;
            System.out.println("finalpath: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearExistingPuzzlePieces(Context context){
        File[] files = context.getFilesDir().listFiles();
        if(files != null)
            for(File file : files) {
                file.delete();
                Log.e("Delete","All existing puzzle photos or files are deleted");
            }
    }


    public Bitmap drawPuzzleCuttingOverlay(Paint paint) {
        Bitmap originalWithOverlay = Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas overlayCanvas = new Canvas(originalWithOverlay);
        Paint imagePaint = new Paint();
        imagePaint.setColor(0x44000000);
        overlayCanvas.drawBitmap(originalImage, 0, 0, imagePaint);

        HorizontalCurve[][] horizontalCurves = cutMap.getHorizontalCurveArray();
        for (int i = 0; i < cutMap.getHorizontalResolution(); i++) {
            for (int j = 1; j < cutMap.getVerticalResolution(); j++) {
                Point startPoint = new Point(i * horizontalStep, j * verticalStep);
                HorizontalCurve horizontalCurve = horizontalCurves[i][j - 1];
                CurveDrawer.drawHorizontalCurve(horizontalCurve, overlayCanvas, startPoint, horizontalScale, verticalScale, paint);
            }
        }

        VerticalCurve[][] verticalCurves = cutMap.getVerticalCurveArray();
        for (int i = 1; i < cutMap.getHorizontalResolution(); i++) {
            for (int j = 0; j < cutMap.getVerticalResolution(); j++) {
                Point startPoint = new Point(i * horizontalStep, j * verticalStep);
                VerticalCurve verticalCurve = verticalCurves[i - 1][j];
                CurveDrawer.drawVerticalCurve(verticalCurve, overlayCanvas, startPoint, horizontalScale, verticalScale, paint);
            }
        }
        return originalWithOverlay;
    }

    public Bitmap drawSinglePuzzlePieceMask(int xIndex, int yIndex, PieceCurveSet pieceCurveSet) {
        Bitmap maskLayer = Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(maskLayer);
        Point startPoint = new Point(xIndex * horizontalStep, yIndex * verticalStep);
        int currentHorizontalStep = xIndex == cutMap.getHorizontalResolution() - 1 ?
                maskLayer.getWidth() - startPoint.x :
                horizontalStep;
        int currentVerticalStep = yIndex == cutMap.getVerticalResolution() - 1 ?
                maskLayer.getHeight() - startPoint.y :
                verticalStep;

        CurveDrawer.drawPuzzlePiece(canvas, pieceCurveSet, startPoint, currentHorizontalStep, currentVerticalStep, cutterPaint);
        floodFill(maskLayer, new Point(startPoint.x + horizontalStep / 2, startPoint.y + verticalStep / 2), Color.TRANSPARENT, Color.GREEN);
        if (xIndex == 0 && yIndex == 0) {
            floodFill(maskLayer, new Point(startPoint.x + horizontalStep, startPoint.y + verticalStep), Color.RED, Color.TRANSPARENT);
        } else {
            floodFill(maskLayer, startPoint, Color.RED, Color.TRANSPARENT);
        }
        return maskLayer;
    }

    private Bitmap maskPuzzlePiece(Bitmap mask) {
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(originalImage, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    private Bitmap cutPuzzlePiece(Bitmap original, Rect border) {
        return Bitmap.createBitmap(original, border.left, border.top, border.width(), border.height());
    }

    private static void floodFill(Bitmap image, Point node, int targetColor, int replacementColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (targetColor != replacementColor) {
            Queue<Point> queue = new LinkedList<>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == targetColor) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == targetColor) {
                    image.setPixel(x, y, replacementColor);
                    if (!spanUp && y > 0 && image.getPixel(x, y - 1) == targetColor) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0
                            && image.getPixel(x, y - 1) != targetColor) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1
                            && image.getPixel(x, y + 1) == targetColor) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1
                            && image.getPixel(x, y + 1) != targetColor) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.poll()) != null);
        }
    }
}
