package com.eguo.lullabyes.FIreBaseFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eguo.lullabyes.Adapters.BookFireAdapter;
import com.eguo.lullabyes.Content.AllContentDataBase;
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
public class AudioFireFragment extends Fragment {

    public FirebaseFirestore firebaseFirestore;
    private List<AllContentDataBase> allContentDataBases;
    private BookFireAdapter bookFireAdapter;
    Button button ;
    public FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisit;
    private Boolean isFirstPageFirstLoad = true;
    private ImageView imageViewPlay;
    private String user_priority;
    public AudioFireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_fire, container, false);
        allContentDataBases = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.list_audiofire);
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

                        user_priority = task.getResult().getString("priority");






                            Query fiestQuery = firebaseFirestore.collection("Posts")
                                    //.orderBy("timestamp", Query.Direction.DESCENDING)
                                    .whereEqualTo("type", "audio")

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
                                                // if (isFirstPageFirstLoad) {
                                               /* firebaseFirestore.collection("Posts")

                                                        .document(blogPostId).update("classautor", "Русские народные сказки " );*/
                                                        //.orderBy("timestamp", Query.Direction.DESCENDING)



                                                allContentDataBases.add(allContentDataBase);
                                                // } else {
                                                //  allContentDataBases.add(0, allContentDataBase);
                                                // }
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
        // Inflate the layout for this fragment

    }
    private void loadMorePost() {
        if (firebaseAuth.getCurrentUser() != null) {



                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("type", "audio")

                        .startAfter(lastVisit)
                        .limit(4);

                niextQuery.addSnapshotListener(Objects.requireNonNull(getActivity()), new EventListener<QuerySnapshot>() {
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
