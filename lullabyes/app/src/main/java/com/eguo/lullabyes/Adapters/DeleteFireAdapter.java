package com.eguo.lullabyes.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eguo.lullabyes.Content.AllContentDataBase;
import com.eguo.lullabyes.R;
import com.eguo.lullabyes.TaileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class DeleteFireAdapter extends RecyclerView.Adapter<DeleteFireAdapter.ViewHolder> {
    public List<AllContentDataBase> allContentDataBases;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public  DeleteFireAdapter(List<AllContentDataBase> allContentDataBases){
        this.allContentDataBases = allContentDataBases;
    }

    @NonNull
    @Override
    public DeleteFireAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_for_delete_post, viewGroup, false);
        context = viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new DeleteFireAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeleteFireAdapter.ViewHolder holder, final int i) {
        holder.setIsRecyclable(false);
        String typeAudio = allContentDataBases.get(i).getType();
        holder.setType(typeAudio);
        final String blogPostId =allContentDataBases.get(i).TailePostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();
        String name_data = allContentDataBases.get(i).getName();
        holder.setNameText(name_data);
        String image_url = allContentDataBases.get(i).getImage_url();
        holder.setImage(image_url);

        String user_id = allContentDataBases.get(i).getUser_id();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String user_mail = task.getResult().getString("mail");
                    String user_priority  = task.getResult().getString("priority");
                }else {

                }


                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            int count = queryDocumentSnapshots.size();
                          //  holder.updateLikesCount(count);
                        }
                        else {
                           // holder.updateLikesCount(0);
                        }
                    }
                });
                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.exists()){
                         //   holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.ic_launcherred));

                        }else{
                          //  holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.ic_launchergrey));

                        }
                    }
                });




            }
        });
       //holder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
       //    @Override
       //    public void onClick(View v) {
       //        firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
       //            @Override
       //            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
       //                if (!task.getResult().exists()){
       //                    Map<String, Object> likeMap = new HashMap<>();
       //                    likeMap.put("timestamp", FieldValue.serverTimestamp());

       //                    firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).set(likeMap);

       //                }else {
       //                    firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).delete();

       //                }
       //            }
       //        });


       //    }
       //});

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaileActivity.class);
                intent.putExtra("blog_post_id", blogPostId);

                context.startActivity(intent);
            }
        });
 holder.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebaseFirestore.collection("Posts").document(blogPostId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        allContentDataBases.remove(i);

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return allContentDataBases.size();
    }



    public  class  ViewHolder extends  RecyclerView.ViewHolder{
        private TextView textName;
        private TextView typetaile;

        private  View view;
        private ImageView imageView;
        private  Button deletePost;

    //    private  TextView blogLikeCount;
    //    private  ImageView blogLikeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view= itemView;
            deletePost = view.findViewById(R.id.button_delete_post);
         //  blogLikeBtn = view.findViewById(R.id.imageView2);
         //  blogLikeCount =view.findViewById(R.id.count_like);
        }

        public  void  setNameText(String text){
            textName= view.findViewById(R.id.delete_post);
            textName.setText(text);
        }





        public  void  setImage(String download){
            imageView =  view.findViewById(R.id.image_from_delete);
            Glide.with(context).load(download).into(imageView);
        }
        public  void setUserData(String name, String priority){

        }
      //public void updateLikesCount(int count){

      //    blogLikeCount = view.findViewById(R.id.count_like);
      //    blogLikeCount.setText(count + " Likes");

      //}
        public void setType(String string){
            typetaile = view.findViewById(R.id.delete_model);
            if (string.equals("audio")){
                typetaile.setText("Аудио");
            }else{
                typetaile.setText("Сказка");
            }
        }

    }
}