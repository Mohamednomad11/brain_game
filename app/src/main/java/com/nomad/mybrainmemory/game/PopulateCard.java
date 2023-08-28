package com.nomad.mybrainmemory.game;


import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.model.CardModel;

import java.util.ArrayList;
import java.util.Collections;

public class PopulateCard {

    int numCards;
    int numGroup;

    public PopulateCard(int numCards,int numGroup){
        this.numCards = numCards;
        this.numGroup = numGroup;
    }

    public ArrayList<CardModel> singleAnimal(){
        ArrayList<CardModel> arrayList = new ArrayList<>();
        if(numGroup==1){
            arrayList.add(new CardModel("monkey", R.drawable.star, R.drawable.monkey));
            arrayList.add(new CardModel("elephant", R.drawable.star, R.drawable.elephant));
            arrayList.add(new CardModel("giraffe", R.drawable.star, R.drawable.giraffe));
            arrayList.add(new CardModel("koala", R.drawable.star, R.drawable.koala));
        }else if(numGroup ==2){
            arrayList.add(new CardModel("s1", R.drawable.star, R.drawable.s1));
            arrayList.add(new CardModel("s2", R.drawable.star, R.drawable.s2));
            arrayList.add(new CardModel("s3", R.drawable.star, R.drawable.s3));
            arrayList.add(new CardModel("s4", R.drawable.star, R.drawable.s4));
            arrayList.add(new CardModel("s5", R.drawable.star, R.drawable.s5));
            arrayList.add(new CardModel("s6", R.drawable.star, R.drawable.s6));
            arrayList.add(new CardModel("s7", R.drawable.star, R.drawable.s7));
            arrayList.add(new CardModel("s8", R.drawable.star, R.drawable.s8));
            arrayList.add(new CardModel("s9", R.drawable.star, R.drawable.s9));
            arrayList.add(new CardModel("s10", R.drawable.star, R.drawable.s10));
            arrayList.add(new CardModel("s11", R.drawable.star, R.drawable.s11));
            arrayList.add(new CardModel("s12", R.drawable.star, R.drawable.s12));
            arrayList.add(new CardModel("s13", R.drawable.star, R.drawable.s13));
            arrayList.add(new CardModel("s14", R.drawable.star, R.drawable.s14));
            arrayList.add(new CardModel("s15", R.drawable.star, R.drawable.s15));
            arrayList.add(new CardModel("s16", R.drawable.star, R.drawable.s16));
        }else if(numGroup ==3){
            arrayList.add(new CardModel("monkey", R.drawable.star, R.drawable.monkey));
            arrayList.add(new CardModel("elephant", R.drawable.star, R.drawable.elephant));
            arrayList.add(new CardModel("giraffe", R.drawable.star, R.drawable.giraffe));
            arrayList.add(new CardModel("koala", R.drawable.star, R.drawable.koala));
            arrayList.add(new CardModel("rhino", R.drawable.star, R.drawable.rhino));
            arrayList.add(new CardModel("tiger", R.drawable.star, R.drawable.tiger));
            arrayList.add(new CardModel("lemon", R.drawable.star, R.drawable.lemon2));
            arrayList.add(new CardModel("orange", R.drawable.star, R.drawable.orange));
            arrayList.add(new CardModel("pear", R.drawable.star, R.drawable.pear));
            arrayList.add(new CardModel("s1", R.drawable.star, R.drawable.s1));
            arrayList.add(new CardModel("s2", R.drawable.star, R.drawable.s2));
            arrayList.add(new CardModel("s3", R.drawable.star, R.drawable.s3));
            arrayList.add(new CardModel("s4", R.drawable.star, R.drawable.s4));
            arrayList.add(new CardModel("s5", R.drawable.star, R.drawable.s5));
            arrayList.add(new CardModel("s6", R.drawable.star, R.drawable.s6));
            arrayList.add(new CardModel("s7", R.drawable.star, R.drawable.s7));
            arrayList.add(new CardModel("s8", R.drawable.star, R.drawable.s8));
            arrayList.add(new CardModel("s9", R.drawable.star, R.drawable.s9));
            arrayList.add(new CardModel("s10", R.drawable.star, R.drawable.s10));
            arrayList.add(new CardModel("s11", R.drawable.star, R.drawable.s11));
            arrayList.add(new CardModel("s12", R.drawable.star, R.drawable.s12));
            arrayList.add(new CardModel("s13", R.drawable.star, R.drawable.s13));
            arrayList.add(new CardModel("s14", R.drawable.star, R.drawable.s14));
            arrayList.add(new CardModel("s15", R.drawable.star, R.drawable.s15));
            arrayList.add(new CardModel("s16", R.drawable.star, R.drawable.s16));
            arrayList.add(new CardModel("strawberry", R.drawable.star, R.drawable.strawberry));
            arrayList.add(new CardModel("yellowfruit", R.drawable.star, R.drawable.yellowfruit));
            arrayList.add(new CardModel("wetpear", R.drawable.star, R.drawable.wetpear));
        }



        if (arrayList.size() > numCards/2) {
            arrayList.subList(numCards/2, arrayList.size()).clear();
        }

        return arrayList;
    }

    public ArrayList<CardModel> shuffleDuplicateAnimals(ArrayList<CardModel> originalList) {
        ArrayList<CardModel> duplicatedList = new ArrayList<>();

        for (CardModel animal : originalList) {
            duplicatedList.add(animal);
            duplicatedList.add(new CardModel(animal.getName(), animal.getBack_img(), animal.getFront_img()));
        }

        Collections.shuffle(duplicatedList);
        return duplicatedList;
    }

    public int getTotalAnimals(){
        return singleAnimal().size();
    }

    public ArrayList<CardModel> populateCard(){
        return shuffleDuplicateAnimals(singleAnimal());
    }
}
