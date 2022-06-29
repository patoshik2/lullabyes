package com.eguo.lullabyes.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eguo.lullabyes.AudioActivity;
import com.eguo.lullabyes.Content.AllContentDataBase;
import com.eguo.lullabyes.FIreBaseFragments.BookFireFragment;
import com.eguo.lullabyes.MainActivity;
import com.eguo.lullabyes.MediaPleyerFireBase.MediaPleyerFireBase;
import com.eguo.lullabyes.OtherContentActivity;
import com.eguo.lullabyes.R;
import com.eguo.lullabyes.TaileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class BookFireAdapter extends RecyclerView.Adapter<BookFireAdapter.ViewHolder> {
    private List<AllContentDataBase> allContentDataBases;
public Context context;
MediaPlayer mediaPlayer;
    public ResyclerItemClickListener listener;
private  Activity activity;
    public FirebaseFirestore firebaseFirestore;
    public  ImageView audioBtn;
    public FirebaseAuth firebaseAuth;
MainActivity mainActivity;

int tipe;
    private int forfound;
BookFireFragment bookFireFragment;

    public BookFireAdapter(List<AllContentDataBase> allContentDataBases, int tipe) {
    this.allContentDataBases = allContentDataBases;
    this.tipe = tipe;
        MediaPleyerFireBase.instance().setMediapleyer(allContentDataBases, forfound);

        new AudioActivity().setAudioThings(allContentDataBases, forfound);


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view = null;
        if (tipe== 1){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_for_tales, viewGroup, false);

        }else  if (tipe ==2){

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_for_tales, viewGroup, false);
            ViewGroup.LayoutParams params  =view.getLayoutParams();
            params.width = 350;

            view.setLayoutParams(params);
        }else  if (tipe ==3){

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_actor, viewGroup, false);

        }





context = viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        forfound = i;
        holder.setIsRecyclable(false);
        if (tipe == 1 || tipe == 2) {
            String classifikautor = allContentDataBases.get(i).getClassautor();
            final String type = allContentDataBases.get(i).getType();
            holder.setType(type);
            final int count = allContentDataBases.get(i).getLike();
            holder.updateLikesCount(count);

            final String blogPostId = allContentDataBases.get(i).TailePostId;
            final String blogpostpriority = allContentDataBases.get(i).getPriority();
            final String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            String name_data = allContentDataBases.get(i).getName();
            holder.setNameText(name_data);
            final String user_id = allContentDataBases.get(i).getUser_id();
            String image_url = allContentDataBases.get(i).getImage_url();
            holder.setImage(image_url);


            final String urlAudio = allContentDataBases.get(i).getAudio_url();



        if (firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String user_mail = Objects.requireNonNull(task.getResult()).getString("mail");
                        final String user_priority = task.getResult().getString("priority");


                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                assert user_priority != null;
                                if (Integer.parseInt(user_priority) >= Integer.parseInt(blogpostpriority)) {


                                    //Toast.makeText(context, "Error " + urlAudio, Toast.LENGTH_LONG).show();
                                    if (type.equals("audio")) {
                                        Intent intent = new Intent(context, AudioActivity.class);
                                        intent.putExtra("blog_post_id", blogPostId);

                                        context.startActivity(intent);

                                        // MediaPlayer mediaPlayer = new MediaPlayer();
                                        // if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                                        //     MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                                        // }else {
                                        //     MediaPleyerFireBase.instance().stopMediaPlayerContent();
                                        //     MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                                        // }
                                        //if (MediaPleyerFireBase.instance().getMediaplayer().isPlaying()) {
                                        //MediaPleyerFireBase.instance().pauseMediaPlayerContent();
                                        //} else {
                                        //MediaPleyerFireBase.instance().continueMediaPlayerContent();
                                        //}
                                    } else if (type.equals("Колыбельная")) {
                                        Intent intent = new Intent(context, AudioActivity.class);
                                        intent.putExtra("blog_post_id", blogPostId);

                                        context.startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(context, TaileActivity.class);
                                        intent.putExtra("blog_post_id", blogPostId);

                                        context.startActivity(intent);
                                    }

                                } else {
                                    Toast.makeText(context, "Для просмотра данного контента необходима VIP подписка", Toast.LENGTH_LONG).show();

                                }


                            }

                        });


                    }


                 /* firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            assert queryDocumentSnapshots != null;
                            if (!queryDocumentSnapshots.isEmpty()) {
                                int count = queryDocumentSnapshots.size();
                          firebaseFirestore.collection("Posts").document(blogPostId).update("like", count);

                                holder.updateLikesCount(count);
                            } else {
                                holder.updateLikesCount(0);
                            }
                        }
                    });*/
                    firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            assert documentSnapshot != null;
                            if (documentSnapshot.exists()) {
                                holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.ic_launcherred));

                            } else {
                                holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.ic_launchergrey));

                            }
                        }
                    });

                }
            });
        }


    }

        else {
            final String blogPostId = allContentDataBases.get(i).TailePostId;
            final String blogpostpriority = allContentDataBases.get(i).getPriority();
            final String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            String name_data = allContentDataBases.get(i).getName();
            holder.setNameTextAutor(name_data);
            final String user_id = allContentDataBases.get(i).getUser_id();
            String image_url = allContentDataBases.get(i).getImage_url();
            holder.setImageAutor(image_url);
            final String type = allContentDataBases.get(i).getType();
            if (firebaseAuth.getCurrentUser() != null) {

                firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String user_mail = Objects.requireNonNull(task.getResult()).getString("mail");
                            final String user_priority = task.getResult().getString("priority");


                            holder.imageViewAutor.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    assert user_priority != null;
                                    if (Integer.parseInt(user_priority) >= Integer.parseInt(blogpostpriority)) {


                                        Intent intent = new Intent(context, OtherContentActivity.class);
                                        //intent.putExtra("blog_post_id", blogPostId);
                                        intent.putExtra("name_data", name_data);

                                        context.startActivity(intent);

                                        //Toast.makeText(context, "Error " + urlAudio, Toast.LENGTH_LONG).show();
                                     //   if (type.equals("Колыбельная")) {
                                            /*AutorFragment autorFragment = new AutorFragment(name_data);
                                            FragmentManager manageraunt =  ((AppCompatActivity)context).getSupportFragmentManager();;
                                            FragmentTransaction transaction = manageraunt.beginTransaction();
                                            transaction.setCustomAnimations(R.anim.enter_to_right, R.anim.exit_to_right);
                                            transaction.addToBackStack(null);
                                            transaction.add(R.id.relativ,autorFragment,autorFragment.getTag()).commit();*/
                                         /*   Intent intent = new Intent(context, AudioActivity.class);
                                            intent.putExtra("blog_post_id", blogPostId);

                                            context.startActivity(intent);*/

                                            // MediaPlayer mediaPlayer = new MediaPlayer();
                                            // if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                                            //     MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                                            // }else {
                                            //     MediaPleyerFireBase.instance().stopMediaPlayerContent();
                                            //     MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                                            // }
                                            //if (MediaPleyerFireBase.instance().getMediaplayer().isPlaying()) {
                                            //MediaPleyerFireBase.instance().pauseMediaPlayerContent();
                                            //} else {
                                            //MediaPleyerFireBase.instance().continueMediaPlayerContent();
                                            //}
                                     //   }

                                    } else {
                                        Toast.makeText(context, "Для просмотра данного контента необходима VIP подписка", Toast.LENGTH_LONG).show();

                                    }


                                }

                            });


                        }



                    }
                });
            }



        }
  /*     holder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (!task.getResult().exists()){
                           Map<String, Object> likeMap = new HashMap<>();
                           likeMap.put("timestamp", FieldValue.serverTimestamp());

                           firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).set(likeMap);

                       }else {
                           firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).delete();

                       }
                   }
               });


           }
       });*/



    }

    @Override
    public int getItemCount() {
        return allContentDataBases.size();
    }



    public  class  ViewHolder extends  RecyclerView.ViewHolder{

        private  View view;
private ImageView imageView,imageViewAutor;
        private  TextView blogLikeCount;
private  ImageView blogLikeBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view= itemView;
            ImageView imageViewPlay = view.findViewById(R.id.play_audio_button);
            blogLikeBtn = view.findViewById(R.id.imageView2);
            blogLikeCount =view.findViewById(R.id.count_like);
        }

        void  setNameText(String text){
            TextView textName = view.findViewById(R.id.nameTailes);
            textName.setText(text);
        }
      void   setNameTextAutor(String text){
          TextView textName = view.findViewById(R.id.autor_name_item);
          textName.setText(text);
      }




        void  setImage(String download){
imageView =  view.findViewById(R.id.tailesImage);
          Glide.with(context).load(download).into(imageView);
        }



       void  setImageAutor(String download){
imageViewAutor =  view.findViewById(R.id.circleImageView);
          Glide.with(context).load(download).into(imageViewAutor);
        }




         public  void setUserData(String name, String priority){

         }
        void updateLikesCount(int count){

            blogLikeCount = view.findViewById(R.id.count_like);
            blogLikeCount.setText(count + " ");

        }
 public void setType(String string){

           if (string==null){

           }else if (string.equals("audio")){
               TextView typetaile = view.findViewById(R.id.typetaile);

               typetaile.setText("Аудио");
           }else if (string.equals("photo")) {
               TextView typetaile = view.findViewById(R.id.typetaile);
               typetaile.setText("Сказка");
           }else if (string.equals("autor")) {
               TextView typetaile = view.findViewById(R.id.typetaile);
               typetaile.setText("Автор");
           }else {
               TextView typetaile = view.findViewById(R.id.typetaile);
               typetaile.setText("Колыбельная");
           }
        }
        public  void  bind (final AllContentDataBase audioContent, final BookFireAdapter.ResyclerItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(audioContent,getLayoutPosition());
                }
            });
        }
    }

    public interface ResyclerItemClickListener{
        void  onClickListener(AllContentDataBase audioContent, int position);

    }
public void forwardMiuzik(){





    }


}