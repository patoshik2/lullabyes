package com.eguo.lullabyes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eguo.lullabyes.Adapters.DeleteFireAdapter;
import com.eguo.lullabyes.Content.AllContentDataBase;
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

import javax.annotation.Nullable;

public class DeletePost extends AppCompatActivity {
    public FirebaseFirestore firebaseFirestore;
    private List<AllContentDataBase> allContentDataBases;
    private DeleteFireAdapter deleteFireAdapter;
    Button button ;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisit;
    private Boolean isFirstPageFirstLoad = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);
        allContentDataBases = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.delele_recycler);
        firebaseAuth = FirebaseAuth.getInstance();

        deleteFireAdapter = new DeleteFireAdapter(allContentDataBases);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(deleteFireAdapter);
        if (firebaseAuth.getCurrentUser() != null) {


            firebaseFirestore = FirebaseFirestore.getInstance();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    boolean reacheBottom = !
                            recyclerView.canScrollVertically(-1);
                    if (reacheBottom) {


                        loadMorePost();
                    }
                }
            });
            Query fiestQuery = firebaseFirestore.collection("Posts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .limit(10);

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
                                if (isFirstPageFirstLoad) {
                                    allContentDataBases.add(allContentDataBase);
                                } else {
                                    allContentDataBases.add(0, allContentDataBase);
                                }
                                deleteFireAdapter.notifyDataSetChanged();

                            }
                        }

                        isFirstPageFirstLoad = false;
                    }
                }
            });


        }
    }
    private void loadMorePost() {
        if (firebaseAuth.getCurrentUser() != null) {
            Query fiestQuery = firebaseFirestore.collection("Posts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .startAfter(lastVisit)
                    .limit(10);

            fiestQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);
                                allContentDataBases.add(allContentDataBase);
                                deleteFireAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                }
            });
        }
    }

}
