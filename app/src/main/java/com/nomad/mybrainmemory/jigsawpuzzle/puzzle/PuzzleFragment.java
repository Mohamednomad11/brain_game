package com.nomad.mybrainmemory.jigsawpuzzle.puzzle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.jigsawpuzzle.adapter.PuzzleAdapter;
import com.nomad.mybrainmemory.jigsawpuzzle.models.Pieces;
import com.nomad.mybrainmemory.jigsawpuzzle.models.PuzzlePiece;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.CongratsScreen;
import com.nomad.mybrainmemory.util.StaticConstants;
import com.nomad.mybrainmemory.util.TimerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleFragment extends Fragment {
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


    public PuzzleFragment(GameModel gameModel){
        this.gameModel = gameModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.puzzle_activity, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("PuzzleActivity","On create");

        imageView = (ImageView) view.findViewById(R.id.frameImage);
        scrollView = (FrameLayout) view.findViewById(R.id.scrollView);
        scrollView.setOnDragListener(new MyDragListener(null));
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        relativeLayout.setOnDragListener(new MyDragListener(null));
        rvPuzzle = (RecyclerView) view.findViewById(R.id.listView2);
        rvPuzzle.setOnDragListener(new MyDragListener(null));
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPuzzle.setLayoutManager(linearLayoutManager);
        puzzle = new Puzzle();
        puzzlePiecesList.clear();


        TextView timerTextView = view.findViewById(R.id.timerTextView);
//        TimerUtils timerUtils = new TimerUtils(30000,timerTextView);
//        timerUtils.startTimer();

//        Intent intent = getIntent();

// Retrieve the arguments from the Intent using the same keys
//        String difficultyLevel = intent.getStringExtra(StaticConstants.KEY_DIFFICULTY_LEVEL);
//
//
//        gameModel = intent.getParcelableExtra(StaticConstants.KEY_GAME_SCORE);

        String difficultyLevel = StaticConstants.LEVEL_MEDIUM;

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
                            image = BitmapFactory.decodeResource(getResources(), R.drawable.autumn);
                            sourceBitmap = Bitmap.createScaledBitmap(image, widthFinal, heightFinal, false);
                        } catch (OutOfMemoryError ex) {
                            ex.printStackTrace();
                        } finally {
                            image = null;
                        }
                        horizontalResolution = 4;
                        verticalResolution = 4;

                        timerUtils =  new TimerUtils(180000,timerTextView,"Round Hard Puzzle", getContext(),gameModel);
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
                    puzzlePiecesList = puzzle.createPuzzlePieces(getActivity(), sourceBitmap, widthFinal, heightFinal, imageView, "/puzzles/", horizontalResolution, verticalResolution);

                    getAdapter();
                    setPuzzleListAdapter();
                    widthCheck = false;
                }
            }
        });
//        Log.d("PuzzleActivity","----Set puzzle list adapter-----");
//        setPuzzleListAdapter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Set the hosting activity to landscape orientation
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Restore the hosting activity's orientation to portrait
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
                final ImageView button2 = new ImageView(getContext());
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
        puzzleListAdapter = new PuzzleAdapter(getContext(), piecesModelListMain);
        rvPuzzle.setHasFixedSize(true);
        rvPuzzle.setAdapter(puzzleListAdapter);

        puzzleListAdapter.notifyDataSetChanged();
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


        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
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
                                gameModel.setScore(gameModel.getScore() + 10);
                                piecesModel = null;
                                if (piecesModelListMain.size() == 0) {
                                    gameModel.setTimeSpent(timerUtils.getTimeSpent());
                                    Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_LONG).show();
//                                    finish();
//                                    infoBox.createPauseDialog(this,timerUtils,RoundOne.this,gameModel,"Round 1");
                                    getParentFragmentManager().beginTransaction().replace(R.id.scrollView,  new CongratsScreen(gameModel, "Round Hard Puzzle")).commit();

                                } else {
                                    Toast.makeText(getContext(), "The correct Puzzle", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                piecesModel = null;
                                view.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Not the correct Puzzle", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else {
                            View view1 = (View) event.getLocalState();
                            view1.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else if (v == scrollView) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else if (v == rvPuzzle) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
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
