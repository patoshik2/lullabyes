package com.eguo.lullabyes.FIreBaseFragments;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eguo.lullabyes.Adapters.BookFireAdapter;
import com.eguo.lullabyes.Content.AllContentDataBase;
import com.eguo.lullabyes.OtherContentActivity;
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
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    MultiSnapRecyclerView multiSnapRecyclerViewouoular;
    MultiSnapRecyclerView multiSnapRecyclerViewouoularadd;
    MultiSnapRecyclerView multiSnapRecyclerViewouoularAutor;
    MultiSnapRecyclerView multiSnapRecyclerViewouoularTaile;
    MultiSnapRecyclerView multiSnapRecyclerViewouoularAudio;
    MultiSnapRecyclerView multiSnapRecyclerViewouoularkolabel;



    private DocumentSnapshot lastVisit;
    public FirebaseFirestore firebaseFirestore;
    private List<AllContentDataBase>  allContentDataBasesPopular;
     private List<AllContentDataBase> allContentDataBasesadd;
     private List<AllContentDataBase> allContentDataBasesAutor;
     private List<AllContentDataBase> allContentDataBasesTaile;
     private List<AllContentDataBase> allContentDataBasesAudio;
     private List<AllContentDataBase> allContentDataBaseskolabel;

   // private PagerAdapter  pagerAdapter;
    private BookFireAdapter  bookFireAdapterPopular;
     private BookFireAdapter bookFireAdapteradd;
     private BookFireAdapter bookFireAdapterAutor;
     private BookFireAdapter bookFireAdapterTaile;
     private BookFireAdapter bookFireAdapterAudio;
     private BookFireAdapter bookFireAdapterkolabel;
private TextView taile_allofthem, audio_allofthem, kolibel_allofthem;






    public FirebaseAuth firebaseAuth;
    private Boolean isFirstPageFirstLoad = true;

    ArgbEvaluator argbEvaluator;



    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, final  ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
allContentDataBasesPopular = new ArrayList<>();
 allContentDataBasesadd= new ArrayList<>();
 allContentDataBasesAutor= new ArrayList<>();
 allContentDataBasesTaile= new ArrayList<>();
 allContentDataBasesAudio= new ArrayList<>();
 allContentDataBaseskolabel= new ArrayList<>();

        taile_allofthem = (TextView) view.findViewById(R.id.taile_allofthem);
        audio_allofthem=  (TextView) view.findViewById(R.id.audio_allofthem);
        kolibel_allofthem = (TextView) view.findViewById(R.id.kolibel_allofthem);
        kolibel_allofthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), OtherContentActivity.class);
                //intent.putExtra("blog_post_id", blogPostId);
                intent.putExtra("type_content", "kolibel_allofthem");

                startActivity(intent);
            }
        });
        audio_allofthem.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent intent = new Intent(getContext(), OtherContentActivity.class);
                                                   //intent.putExtra("blog_post_id", blogPostId);
                                                   intent.putExtra("type_content", "audio_allofthem");

                                                   startActivity(intent);
                                               }
                                           });
                taile_allofthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), OtherContentActivity.class);
                        //intent.putExtra("blog_post_id", blogPostId);
                        intent.putExtra("type_content", "taile_allofthem");

                        startActivity(intent);
                    }
                });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bookFireAdapterPopular = new BookFireAdapter(allContentDataBasesPopular, 2);
        bookFireAdapteradd   = new BookFireAdapter(allContentDataBasesadd , 2 );
        bookFireAdapterAutor  = new BookFireAdapter(allContentDataBasesAutor, 3);
        bookFireAdapterTaile  = new BookFireAdapter(allContentDataBasesTaile, 2);
        bookFireAdapterAudio  = new BookFireAdapter(allContentDataBasesAudio, 2);
        bookFireAdapterkolabel  = new BookFireAdapter(allContentDataBaseskolabel, 2);

        multiSnapRecyclerViewouoular = view.findViewById(R.id.popular_horiz_item);
        multiSnapRecyclerViewouoularadd = view.findViewById(R.id.add_horiz_item);
        multiSnapRecyclerViewouoularAutor = view.findViewById(R.id.autor_horiz_item);
        multiSnapRecyclerViewouoularTaile = view.findViewById(R.id.taile_horiz_item);
        multiSnapRecyclerViewouoularAudio = view.findViewById(R.id.audio_horiz_item);
        multiSnapRecyclerViewouoularkolabel = view.findViewById(R.id.kolibel_horiz_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoular.setLayoutManager(layoutManager);
        multiSnapRecyclerViewouoular.setAdapter(bookFireAdapterPopular);
 LinearLayoutManager layoutManageradd = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoularadd.setLayoutManager(layoutManageradd);
        multiSnapRecyclerViewouoularadd.setAdapter(bookFireAdapteradd);
 LinearLayoutManager layoutManagerAutor = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoularAutor.setLayoutManager(layoutManagerAutor);
        multiSnapRecyclerViewouoularAutor.setAdapter(bookFireAdapterAutor);
 LinearLayoutManager layoutManagerTaile = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoularTaile.setLayoutManager(layoutManagerTaile);
        multiSnapRecyclerViewouoularTaile.setAdapter(bookFireAdapterTaile);
 LinearLayoutManager layoutManagerAudio = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoularAudio.setLayoutManager(layoutManagerAudio);
        multiSnapRecyclerViewouoularAudio.setAdapter(bookFireAdapterAudio);
 LinearLayoutManager layoutManagerkolabel = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerViewouoularkolabel.setLayoutManager(layoutManagerkolabel);
        multiSnapRecyclerViewouoularkolabel.setAdapter(bookFireAdapterkolabel);


    if (firebaseAuth.getCurrentUser() != null) {


         /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    boolean reacheBottom = !recyclerView.canScrollVertically(1);
                    if (reacheBottom) {


                        loadMorePost();
                    }
                }
            });*/

            final String user_id = firebaseAuth.getCurrentUser().getUid();




            firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String user_mail = task.getResult().getString("mail");

                        //   user_priority = task.getResult().getString("priority");
                        //   Toast.makeText(getContext(), "Error " + user_priority, Toast.LENGTH_LONG).show();





                        Query fiestQuery = firebaseFirestore.collection("Posts")
                                .whereGreaterThan("like", 13)


                                .limit(15);

                        fiestQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBasesPopular.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                            allContentDataBasesPopular.add(allContentDataBase);

                                            bookFireAdapterPopular.notifyDataSetChanged();

                                        }
                                    }

                                    // isFirstPageFirstLoad = false;
                                }
                            }
                        });

                        Query fiestQueryadd = firebaseFirestore.collection("Posts")
                                .orderBy("timestamp", Query.Direction.DESCENDING)


                                .limit(10);

                        fiestQueryadd.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBasesadd.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);
                                           /* firebaseFirestore.collection("Posts")
                                                    .document(blogPostId).update("classautor", "Русские народные сказки " );*/
                                            allContentDataBasesadd.add(allContentDataBase);

                                            bookFireAdapteradd.notifyDataSetChanged();

                                        }
                                    }

                                    // isFirstPageFirstLoad = false;
                                }
                            }
                        });




                        Query fiestQuerytaile = firebaseFirestore.collection("Posts")
                                .whereEqualTo("type", "photo")



                                .limit(12);

                        fiestQuerytaile.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBaseskolabel.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                            allContentDataBasesTaile.add(allContentDataBase);

                                            bookFireAdapterTaile.notifyDataSetChanged();

                                        }
                                    }

                                    // isFirstPageFirstLoad = false;
                                }
                            }
                        });


 Query fiestQueryaudio = firebaseFirestore.collection("Posts")
                                .whereEqualTo("type", "audio")



                                .limit(12);

                        fiestQueryaudio.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBasesAudio.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                            allContentDataBasesAudio.add(allContentDataBase);

                                            bookFireAdapterAudio.notifyDataSetChanged();

                                        }
                                    }

                                    // isFirstPageFirstLoad = false;
                                }
                            }
                        });


Query fiestQuerykolibel = firebaseFirestore.collection("Posts")
                                .whereEqualTo("type", "Колыбельная")



                                .limit(12);

                        fiestQuerykolibel.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBaseskolabel.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                            allContentDataBaseskolabel.add(allContentDataBase);

                                            bookFireAdapterkolabel.notifyDataSetChanged();

                                        }
                                    }

                                    // isFirstPageFirstLoad = false;
                                }
                            }
                        });


                        Query fiestQueryAutor = firebaseFirestore.collection("Autor")

                                .limit(12);

                        fiestQueryAutor.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    if (isFirstPageFirstLoad) {

                                        lastVisit = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                                        allContentDataBasesAutor.clear();


                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String blogPostId = doc.getDocument().getId();
                                            AllContentDataBase allContentDataBase = doc.getDocument().toObject(AllContentDataBase.class).withId(blogPostId);

                                            allContentDataBasesAutor.add(allContentDataBase);

                                            bookFireAdapterAutor.notifyDataSetChanged();

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
}
