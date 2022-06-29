package com.eguo.lullabyes.FIreBaseFragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eguo.lullabyes.Adapters.BookFireAdapter;
import com.eguo.lullabyes.Content.AllContentDataBase;
import com.eguo.lullabyes.MediaPlayerContent.MediaPlayerContent;
import com.eguo.lullabyes.MediaPleyerFireBase.MediaPleyerFireBase;
import com.eguo.lullabyes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFireFragment extends Fragment {
public FirebaseFirestore firebaseFirestore;
    private List<AllContentDataBase> allContentDataBases;


    private BookFireAdapter bookFireAdapter;
Button button ;
public FirebaseAuth firebaseAuth;
private DocumentSnapshot lastVisit;
    private Boolean isFirstPageFirstLoad = true;
    private ImageView imageViewPlay;
    String user_priority;
    public BookFireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_fire, container, false);
        allContentDataBases = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.list_bookfire);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bookFireAdapter = new BookFireAdapter(allContentDataBases, 1);



        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        recyclerView.setAdapter(bookFireAdapter);







        if (firebaseAuth.getCurrentUser() != null) {


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    boolean reacheBottom = !recyclerView.canScrollVertically(1);
                    if (reacheBottom) {


                        loadMorePost();
                    }
                }
            });

            final String user_id = firebaseAuth.getCurrentUser().getUid();




            firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String user_mail = task.getResult().getString("mail");

                      //   user_priority = task.getResult().getString("priority");
                     //   Toast.makeText(getContext(), "Error " + user_priority, Toast.LENGTH_LONG).show();





                            Query fiestQuery = firebaseFirestore.collection("Posts")
                                    //.orderBy("timestamp", Query.Direction.DESCENDING)
                                    .whereEqualTo("type", "photo")

                                    .limit(8);

                            fiestQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (!queryDocumentSnapshots.isEmpty()) {

                                        if (isFirstPageFirstLoad) {

                                            lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                            allContentDataBases.clear();
                                      

                                        }
                                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                                String blogPostId = doc.getDocument().getId();
                                                AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                                    allContentDataBases.add(allContentDataBase);

                                                bookFireAdapter.notifyDataSetChanged();

                                            }
                                        }

                                       // isFirstPageFirstLoad = false;
                                    }
                                }
                            });













                    }
                }});




    }





        // Inflate the layout for this fragment
        return view;
    }
    public  void  setOnOff(Activity activity){

        if (MediaPleyerFireBase.instance().getMediaplayer().isPlaying()) {
            MediaPleyerFireBase.instance().pauseMediaPlayerContent();
            imageViewPlay.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_play_arrow_black_24dp));
        } else {
            MediaPlayerContent.instance().continueMediaPlayerContent();
            imageViewPlay.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.ic_pause_grean_24dp));
        }

    }
    private void loadMorePost() {
        if (firebaseAuth.getCurrentUser() != null) {




                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("type", "photo")

                        .startAfter(lastVisit)
                        .limit(4);

                niextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                        if (!queryDocumentSnapshots.isEmpty()) {

                            lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {


                                    String blogPostId = doc.getDocument().getId();
                                    AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);
                                    allContentDataBases.add(allContentDataBase);

                                    bookFireAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });



        }
    }
}
