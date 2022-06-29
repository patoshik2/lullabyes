package com.eguo.lullabyes.FIreBaseFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFireFragment extends Fragment {
private Toolbar toolbar;
    FirebaseFirestore firebaseFirestore;
    private RecyclerView search_of_list_fire;
    private List<AllContentDataBase> allContentDataBases;
    private BookFireAdapter bookFireAdapter;
    Button button ;
   // private SearchView searchView;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisit;
    private Boolean isFirstPageFirstLoad = true;
    private SearchView searchView;
    GridLayoutManager layoutManager;
    private String user_priority;
    private ProgressBar loginProgressBar;
   // private ProgressBar loginProgressBar;
    public SearchFireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view  = inflater.inflate(R.layout.fragment_search_fire, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_taile_search);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //searchView = (SearchView) view.findViewById(R.id.search_taile_fire);
        //loginProgressBar = (ProgressBar) view.findViewById(R.id.searc_progressbar);
        allContentDataBases = new ArrayList<>();
        search_of_list_fire =  view.findViewById(R.id.search_of_list_fire);
        firebaseAuth = FirebaseAuth.getInstance();
       searchView =  view.findViewById(R.id.search_taile_fire);
        bookFireAdapter = new BookFireAdapter(allContentDataBases, 1);
        loginProgressBar = (ProgressBar) view.findViewById(R.id.searc_progressbar_fire);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        search_of_list_fire.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        search_of_list_fire.setAdapter(bookFireAdapter);
        if (firebaseAuth.getCurrentUser() != null) {


            firebaseFirestore = FirebaseFirestore.getInstance();
            search_of_list_fire.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean reacheBottom = !recyclerView.canScrollVertically(1);
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





               Query fiestQuery = firebaseFirestore.collection("Posts")

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
                                   if (isFirstPageFirstLoad) {
                                       allContentDataBases.add(allContentDataBase);
                                   } else {
                                       allContentDataBases.add(0, allContentDataBase);
                                   }
                                   bookFireAdapter.notifyDataSetChanged();

                               }
                           }

                           isFirstPageFirstLoad = false;
                       }
                   }
               });


           }
       }
   });



        }




        // Inflate the layout for this fragment
        return view;
    }

    private void loadMorePost() {
        if (firebaseAuth.getCurrentUser() != null) {



                Query niextQuery = firebaseFirestore.collection("Posts")
                        // .orderBy("timestamp", Query.Direction.DESCENDING)
                        // .orderBy("type")


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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search, menu);
MenuItem item = menu.findItem(R.id.action_search);
//final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        searchData(s);
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//
//        return false;
//    }
//});
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
    private  void  searchData(String s){

        firebaseFirestore.collection("Posts")
                .orderBy("search")
                .startAt(s.toLowerCase())
                .endAt(s.toLowerCase() + "/uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        allContentDataBases.clear();
                        loginProgressBar.setVisibility(View.VISIBLE);
                        for(DocumentSnapshot documentSnapshot: task.getResult()){

//                            Model model  = new Model (documentSnapshot.getString("desc"),
//                                    documentSnapshot.getString("image_thumb"),
//                                   documentSnapshot.getString("image_url"),
//                                   documentSnapshot.getString("name"),
//                                   documentSnapshot.getString("timestamp"),
//                                    documentSnapshot.getString("user_id"));

                            String blogPostId = documentSnapshot.getId();
                            AllContentDataBase allContentDataBase = documentSnapshot.toObject(AllContentDataBase.class).withId(blogPostId);
                            if (isFirstPageFirstLoad) {
                                allContentDataBases.add(allContentDataBase);

                            } else {
                                allContentDataBases.add(0, allContentDataBase);
                            }
                            loginProgressBar.setVisibility(View.INVISIBLE);

                            bookFireAdapter.notifyDataSetChanged();
                        }


                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });


    }
}
