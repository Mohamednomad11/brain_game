package com.nomad.mybrainmemory.dragDrop;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;

import java.util.List;

public class DragListener implements View.OnDragListener {

    private boolean isDropped = false;
    private DListener listener;

    DragListener(DListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:

                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
//                final int tvEmptyListTop = R.id.tvEmptyListTop;
//                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.rvTop;
                final int rvBottom = R.id.rvBottom;

                Log.e("DragL","rvTop === " + rvTop + " rvBottom === " + rvBottom  + "  "  + viewId);

                if(viewId == rvBottom){
                    RecyclerView target = (RecyclerView) v.getRootView().findViewById(rvBottom);;// = (RecyclerView) v.getParent();
                    Log.e("DragL","viewId == rvBottom");
//                    target = (RecyclerView) v.getRootView().findViewById(rvBottom);
 /*                   if(viewId == rvTop){
                        Log.e("DragL","viewId == rvTop");
                        target = (RecyclerView) v.getRootView().findViewById(rvTop);
                    }else if(viewId == rvBottom){
                        Log.e("DragL","viewId == rvBottom");
                        target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                    }
                *//*    else if(viewId == tvEmptyListTop || viewId == tvEmptyListBottom){

                    }*//*else{
                        Log.e("DragL","set target to parent");
                        target = (RecyclerView) v.getParent();
                        positionTarget = (int) v.getTag();
                    }*/
                    if (viewSource != null) {
                        RecyclerView source = (RecyclerView) viewSource.getParent();

                        ListAdapterDrag adapterSource = (ListAdapterDrag) source.getAdapter();
                        int positionSource = (int) viewSource.getTag();
                        int sourceId = source.getId();

                        String list = adapterSource.getList().get(positionSource);
                        List<String> listSource = adapterSource.getList();

                        listSource.remove(positionSource);
                        adapterSource.updateList(listSource);
                        adapterSource.notifyDataSetChanged();

                        ListAdapterDrag adapterTarget = (ListAdapterDrag) target.getAdapter();
                        List<String> customListTarget = adapterTarget.getList();
                        if (positionTarget >= 0) {
                            customListTarget.add(positionTarget, list);
                        } else {
                            customListTarget.add(list);
                        }
                        adapterTarget.updateList(customListTarget);
                        adapterTarget.notifyDataSetChanged();

                        if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                            listener.setEmptyListBottom(true);
                        }
     /*                   if (viewId == tvEmptyListBottom) {
                            listener.setEmptyListBottom(false);
                        }*/
                        if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
                            listener.setEmptyListTop(true);
                        }
/*                        if (viewId == tvEmptyListTop) {
                            listener.setEmptyListTop(false);
                        }*/
                    }
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }
}