package com.eguo.lullabyes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eguo.lullabyes.Adapters.BookFireAdapter;
import com.eguo.lullabyes.Content.AllContentDataBase;
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

import javax.annotation.Nullable;

public class OtherContentActivity extends AppCompatActivity {
    public FirebaseFirestore firebaseFirestore;
    private List<AllContentDataBase> allContentDataBases;
    private BookFireAdapter bookFireAdapter;
    ImageView imageView;
    Button button ;
    public FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisit;
    private Boolean isFirstPageFirstLoad = true;
    private String nameAutor;
    private String taileAllofthem;


    private String user_priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_content);

        allContentDataBases = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.list_autor_content);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bookFireAdapter = new BookFireAdapter(allContentDataBases, 1);
nameAutor = getIntent().getStringExtra("name_data");
        taileAllofthem = getIntent().getStringExtra("type_content");

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
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




if (taileAllofthem == null)
                        {
                            Query fiestQuery = firebaseFirestore.collection("Posts")
                                    //.orderBy("timestamp", Query.Direction.DESCENDING)
                                    .whereEqualTo("classautor", nameAutor)

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


                        }else if (taileAllofthem.equals("taile_allofthem")){
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
                        // if (isFirstPageFirstLoad) {
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
}else if (taileAllofthem.equals("audio_allofthem")) {
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
}else if (taileAllofthem.equals("kolibel_allofthem")){
    Query fiestQuery = firebaseFirestore.collection("Posts")
            //.orderBy("timestamp", Query.Direction.DESCENDING)
            .whereEqualTo("type", "Колыбельная")

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










                    }
                }});




        }
    }
    private void loadMorePost() {
        if (firebaseAuth.getCurrentUser() != null) {
  if (taileAllofthem == null) {


                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("classautor", nameAutor)

                        .startAfter(lastVisit)
                        .limit(4);
                niextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
            }else   if (taileAllofthem.equals("taile_allofthem")) {
                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("type", "photo")

                        .startAfter(lastVisit)
                        .limit(4);
                niextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
            }else if (taileAllofthem.equals("audio_allofthem")){

                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("type", "audio")

                        .startAfter(lastVisit)
                        .limit(4);
                niextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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


            }else if (taileAllofthem.equals("kolibel_allofthem")){
                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")
                        .whereEqualTo("type", "Колыбельная")

                        .startAfter(lastVisit)
                        .limit(4);
                niextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
}
