package com.nomad.mybrainmemory.jigsawpuzzle.puzzle;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.jigsawpuzzle.adapter.PuzzleAdapter;
import com.nomad.mybrainmemory.jigsawpuzzle.models.Pieces;
import com.nomad.mybrainmemory.jigsawpuzzle.models.PuzzlePiece;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.CongratsScreen;
import com.nomad.mybrainmemory.play.CongratsScreenActivity;
import com.nomad.mybrainmemory.play.FailedScreen;
import com.nomad.mybrainmemory.play.RoundHard;
import com.nomad.mybrainmemory.play.RoundOne;
import com.nomad.mybrainmemory.util.StaticConstants;
import com.nomad.mybrainmemory.util.TimerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleActivity extends AppCompatActivity implements TimerUtils.TimerCallback {
    RelativeLayout relativeLayout;
    FrameLayout scrollView;
    ImageView imageView;
    Context context;
    List<Pieces> piecesModelListMain = new ArrayList<Pieces>();
    HashMap<String, Pieces> piecesModelHashMap = new HashMap<String, Pieces>();
    int countGrid = 0;
    ArrayList<PuzzlePiece> puzzlePiecesList = new ArrayList<PuzzlePiece>();
    private RecyclerView rvPuzzle;
    private RecyclerView.LayoutManager linearLayoutManager;
    private PuzzleAdapter puzzleListAdapter;
    Puzzle puzzle;

    boolean widthCheck = true;
    int widthFinal;
    int heightFinal;

    Bitmap sourceBitmap;

    int horizontalResolution = 4;
    int verticalResolution = 3;

    TimerUtils timerUtils;

    GameModel gameModel;

    InfoBox infoBox;

    TextView scoreTextView;

    FrameLayout fragmentContainer;

    int cScore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar (if it exists)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the activity to be fullscreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.puzzle_activity);

        context = this;
        infoBox = new InfoBox();
        imageView = (ImageView) findViewById(R.id.frameImage);
        scrollView = (FrameLayout) findViewById(R.id.scrollView);
        scrollView.setOnDragListener(new MyDragListener(null));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnDragListener(new MyDragListener(null));
        rvPuzzle = (RecyclerView) findViewById(R.id.listView2);
        rvPuzzle.setOnDragListener(new MyDragListener(null));
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPuzzle.setLayoutManager(linearLayoutManager);
        puzzle = new Puzzle();
        puzzlePiecesList.clear();

        fragmentContainer = findViewById(R.id.fragment_container);

// To show the fragment
//        fragmentContainer.setVisibility(View.VISIBLE);

// To hide the fragment
        fragmentContainer.setVisibility(View.GONE);


        TextView timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
//        TimerUtils timerUtils = new TimerUtils(30000,timerTextView);
//        timerUtils.startTimer();

        timerUtils =  new TimerUtils(200000,timerTextView,"Round Hard Puzzle", getApplicationContext(),gameModel);
        timerUtils.setTimerCallback(this);

        Intent intent = getIntent();

// Retrieve the arguments from the Intent using the same keys
        String difficultyLevel = intent.getStringExtra(StaticConstants.KEY_DIFFICULTY_LEVEL);


        gameModel = intent.getParcelableExtra(StaticConstants.KEY_GAME_SCORE);

        if(gameModel==null){
            gameModel = new GameModel();
            scoreTextView.setText("00");
        }else{
            cScore = gameModel.getScore();
            scoreTextView.setText(String.valueOf(cScore));
        }


        final ViewTreeObserver obs = scrollView.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {//width=lGrid.getWidth();
                if (widthCheck) {
                    widthFinal = scrollView.getWidth();
                    heightFinal = scrollView.getHeight();
                    if(difficultyLevel.equals(StaticConstants.LEVEL_EASY)){
                        Bitmap image = null;
                        try {
                            image = BitmapFactory.decodeResource(getResources(), R.drawable.tigerr);
                            sourceBitmap = Bitmap.createScaledBitmap(image, widthFinal, heightFinal, false);
                        } catch (OutOfMemoryError ex) {
                            ex.printStackTrace();
                        } finally {
                            image = null;
                        }
                        horizontalResolution = 3;
                        verticalResolution = 3;
                        timerUtils = new TimerUtils(120000,timerTextView);
                        timerUtils.startTimer();
                    }else if(difficultyLevel.equals(StaticConstants.LEVEL_MEDIUM)){
                        Bitmap image = null;
                        try {
                            image = BitmapFactory.decodeResource(getResources(), R.drawable.tigerr);
                            sourceBitmap = Bitmap.createScaledBitmap(image, widthFinal, heightFinal, false);
                        } catch (OutOfMemoryError ex) {
                            ex.printStackTrace();
                        } finally {
                            image = null;
                        }
                        horizontalResolution = 4;
                        verticalResolution = 4;

//                        timerUtils =  new TimerUtils(180000,timerTextView,"Round Hard Puzzle", getApplicationContext(),gameModel);
//                        timerUtils.setTimerCallback(this);

                        timerUtils.startTimer();
                    }else if(difficultyLevel.equals(StaticConstants.LEVEL_HARD)){
                        Bitmap image = null;
                        try {
                            image = BitmapFactory.decodeResource(getResources(), R.drawable.green);
                            sourceBitmap = Bitmap.createScaledBitmap(image, widthFinal, heightFinal, false);
                        } catch (OutOfMemoryError ex) {
                            ex.printStackTrace();
                        } finally {
                            image = null;
                        }
                        horizontalResolution = 5;
                        verticalResolution = 5;
                        timerUtils = new TimerUtils(240000,timerTextView);
                        timerUtils.startTimer();
                    }
                    puzzlePiecesList = puzzle.createPuzzlePieces(PuzzleActivity.this, sourceBitmap, widthFinal, heightFinal, imageView, "/puzzles/", horizontalResolution, verticalResolution);

                    getAdapter();
                    setPuzzleListAdapter();
                    widthCheck = false;
                }
            }
        });
//        Log.d("PuzzleActivity","----Set puzzle list adapter-----");
//        setPuzzleListAdapter();
    }

    public void getAdapter() {
        RelativeLayout.LayoutParams params;
        for (int i = 0; i < verticalResolution; i++) {
            for (int j = 0; j < horizontalResolution; j++) {
                PuzzlePiece piece = puzzlePiecesList.get(countGrid);
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                int dimX = piece.getAnchorPoint().x - piece.getCenterPoint().x;
                int dimY = piece.getAnchorPoint().y - piece.getCenterPoint().y;

                params.setMargins(dimX, dimY, 0, 0);
                final ImageView button2 = new ImageView(this);
                button2.setId(generateViewId());
                button2.setTag(i + "," + j);

                button2.setImageResource(R.drawable.ic_1);

                button2.setOnDragListener(new MyDragListener(button2));
                button2.setLayoutParams(params);
                relativeLayout.addView(button2);

                Pieces piecesModel = new Pieces();
                piecesModel.setpX(i);
                piecesModel.setpY(j);
                piecesModel.setPosition(countGrid);
                piecesModel.setOriginalResource(puzzlePiecesList.get(countGrid).getImage());
                piecesModelListMain.add(piecesModel);
                Collections.shuffle(piecesModelListMain);
                piecesModelHashMap.put(i + "," + j, piecesModel);
                piecesModel = null;

                countGrid++;

            }
        }
    }


    public int generateViewId() {
        final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void setPuzzleListAdapter() {
        if (puzzleListAdapter != null)
            puzzleListAdapter = null;
        Log.d("PuzzleActivity",piecesModelListMain.size() + "---------");
        puzzleListAdapter = new PuzzleAdapter(this, piecesModelListMain);
        rvPuzzle.setHasFixedSize(true);
        rvPuzzle.setAdapter(puzzleListAdapter);

        puzzleListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStop() {
        timerUtils.finishTimer();
        super.onStop();
    }

    @Override
    public void onTimerFinished() {

        fragmentContainer.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // need to reset gameModel to initial value so that in retry it shows the initial value
        gameModel.resetScore(cScore);
        FailedScreen fragment = new FailedScreen(gameModel, "Round Hard Puzzle"); // Replace with your fragment
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Optional, for back navigation

        transaction.commit();

    }

    static public class MyClickListener implements View.OnLongClickListener {

        // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            // TODO Auto-generated method stub

            // create it from the object's tag
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, //data to be dragged
                    shadowBuilder, //drag shadow
                    view, //local data about the drag and drop operation
                    0   //no needed flags
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    public class MyDragListener implements View.OnDragListener {

        final ImageView imageView;

        public MyDragListener(final ImageView imageView) {
            this.imageView = imageView;
        }


        private long touchStartTime = 0;
        private long touchEndTime = 0;
        private int countSuccessfulPass = 0;
        private  int countFailedPass = 0;
        private long reactionTime = 0;
        private long reactionTimeSuccess = 0;
        private long totalReactionTime = 0;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    touchStartTime = System.currentTimeMillis();
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundResource(R.drawable.target_shape);    //change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundResource(R.drawable.normal_shape);    //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    //v is the dynamic grid imageView, we accept the drag item
                    //view is listView imageView the dragged item
                    touchEndTime = System.currentTimeMillis();

                    reactionTime = touchEndTime - touchStartTime;
                    totalReactionTime = totalReactionTime + reactionTime;

                    if (v == imageView) {
                        View view = (View) event.getLocalState();

                        ViewGroup owner = (ViewGroup) v.getParent();
                        if (owner == relativeLayout) {
                            String selectedViewTag = view.getTag().toString();

                            Pieces piecesModel = piecesModelHashMap.get(v.getTag().toString());
                            String xy = piecesModel.getpX() + "," + piecesModel.getpY();

                            if (xy.equals(selectedViewTag)) {
                                ImageView imageView = (ImageView) v;
                                imageView.setImageBitmap(piecesModel.getOriginalResource());
                                piecesModelListMain.remove(piecesModel);
                                setPuzzleListAdapter();

                                countSuccessfulPass++;
                                reactionTimeSuccess = reactionTimeSuccess + reactionTime;

                                double score =  5;

                                if(reactionTime > 15000){

                                    score = 1;

                                }else if(reactionTime > 10000){

                                    score = 3;

                                }else if(reactionTime > 5000){

                                    score = 5;

                                }else{

                                    score = 6.25;

                                }

                                int scr = (int) score;

                                gameModel.setScore(scr);
                                int currentScore = gameModel.getScore();
                                Log.e("Puzzle Activity", "Current score is == " + currentScore);
                                scoreTextView.setText(String.valueOf(currentScore));
                                if (piecesModelListMain.size() == 0) {

                                    long averageReactionTime = (totalReactionTime/(countSuccessfulPass +countFailedPass));
                                    float accuracy = (float) countSuccessfulPass /(countSuccessfulPass +countFailedPass);
                                    long avrgReactionTimeSuccess = reactionTimeSuccess/4;

                                    gameModel.setAccuracy(accuracy);
                                    gameModel.setAverageReactionTime(averageReactionTime);
                                    gameModel.setAverageSuccessReactionTime(avrgReactionTimeSuccess);

                                    Log.e("Round Puzzle","averageReactionTime " + averageReactionTime + "----" +  "accuracy  " + accuracy  + "averageReactionTimeSuccess " + avrgReactionTimeSuccess);


                                    gameModel.setTimeSpent(timerUtils.getTimeSpent());

                                    gameModel.updateLevelStatus(3,1);

//                                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();


                                    Intent i = new Intent(PuzzleActivity.this, CongratsScreenActivity.class);
                                    i.putExtra(StaticConstants.KEY_GAME_SCORE,gameModel);
                                    startActivity(i);
                                    finish();

                                } else {
//                                    Toast.makeText(getApplicationContext(), "The correct Puzzle", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                countFailedPass++;
                                view.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Not the correct Puzzle", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else {
                            View view1 = (View) event.getLocalState();
                            view1.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else if (v == scrollView) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else if (v == rvPuzzle) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;
                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundResource(R.drawable.normal_shape);	//go back to normal shape
                default:
                    break;
            }
            return true;
        }
    }
}
