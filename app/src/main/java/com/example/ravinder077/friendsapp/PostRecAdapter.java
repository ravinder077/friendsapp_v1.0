package com.example.ravinder077.friendsapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chugh on 8/3/2017.
 */

public class PostRecAdapter  extends RecyclerView.Adapter<PostRecAdapter.MyViewHolder> {


    private List<WallData> postlist;
    private Context mcontext;

    public PostRecAdapter(List<WallData> postlist) {
        this.postlist = postlist;
    }


    public PostRecAdapter(List<WallData> postlist,Context context) {

        this.mcontext=context;
        this.postlist = postlist;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallcard, parent, false);

        return new PostRecAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final WallData wallData = postlist.get(position);


       // holder.profilepic.setImageBitmap(wallData.getBitprofilepic());

        Glide.with(holder.profilepic.getContext()).load(wallData.getProfilepic())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profilepic);

        holder.profilename.setText(wallData.getProfilename());
        holder.poststatus.setText(wallData.getPoststatus());

        System.err.println("walldatavdo"+wallData.getPostvdo());



        System.err.println("image and vodeo starts");

        System.err.println("video "+wallData.getPostvdo());
        System.err.println("image "+wallData.getPostpic());
        System.err.println("image and vodeo ends");


        if(wallData.getPostpic()!=null) {

            holder.postvdo.setVisibility(View.INVISIBLE);


            //holder.postpic.setImageBitmap(wallData.getBitpostpic());
            Glide.with(holder.postpic.getContext()).load(wallData.getPostpic())
                    .thumbnail(0.5f)
                    .crossFade()
                    .placeholder(R.drawable.pagecoverphoto)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.postpic);


        }
        else if(wallData.getPostvdo()!=null && wallData.getPostvdo()!="")
        {
            holder.postpic.setVisibility(View.INVISIBLE);
            //Toast.makeText(holder.postvdo.getContext() , "hello", Toast.LENGTH_SHORT).show();

            MediaController mediacontroller = new MediaController(
                    holder.postvdo.getContext());
            mediacontroller.setAnchorView( holder.postvdo);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(wallData.getPostvdo());
            holder.postvdo.setMediaController(mediacontroller);
            holder.postvdo.setVideoURI(video);
        }
        else
        {
            holder.postvdo.setVisibility(View.INVISIBLE);
            holder.postpic.setVisibility(View.INVISIBLE);
        }

        final String postid=wallData.getPostid();
  /*      holder.likelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.err.println("Like Post value = " + holder.liketext.getText());

                boolean a = holder.likelayout.getId()==(R.id.likeicon1);
                System.err.println("Image resource on like touch ="+a);

                if(holder.likelayout.getId()==(R.id.likeicon1))
              {

                  holder.likelayout.setId(R.id.likeicon2);
                  System.err.println("i am in if likethumb");
                  //System.err.println("Like Post in if" + holder.liketext.getText());
                  holder.likelayout.setImageResource(R.drawable.likethumb);
                 // holder.liketext.setText("Unlike");
                  OtpGen otpgen=new OtpGen();
                  otpgen.execute("http://omtii.com/mile/updatelike.php?id="+postid+"&status=1");
                  try {
                      holder.postlikes.setText(otpgen.get());
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  } catch (ExecutionException e) {
                      e.printStackTrace();
                  }
                  System.err.println("Post like count " + wallData.getPostlikes());
                  System.err.println("else part "+"http://omtii.com/mile/updatelike.php?id="+postid+"&status=1");
              }
              else
                {

                    System.err.println("i am in else likethumb");
                    holder.likelayout.setId(R.id.likeicon1);
                //    System.err.println("UnLike Post in else" + holder.liketext.getText());
                    holder.likelayout.setImageResource(R.drawable.likethumbstroke);
                    //holder.liketext.setText("Like");

                    OtpGen otpgen=new OtpGen();
                    otpgen.execute("http://omtii.com/mile/updatelike.php?id="+postid+"&status=0");

                    try {
                        holder.postlikes.setText(otpgen.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    System.err.println("Post like count " + wallData.getPostlikes());
                    System.err.println("else part "+"http://omtii.com/mile/updatelike.php?id="+postid+"&status=0");

                }

            }
        });

        holder.commentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.err.println("Like Post value = " + holder.liketext.getText());

                boolean a = holder.commentlayout.getId()==(R.id.commenticon1);
                System.err.println("Image resource on comment touch ="+a);

                if(holder.commentlayout.getId()==(R.id.commenticon1))
                {

                    holder.commentlayout.setId(R.id.commenticon2);
                    System.err.println("i am in if commenticon");
                    //System.err.println("Like Post in if" + holder.liketext.getText());
                    holder.commentlayout.setImageResource(R.drawable.comment);
                    // holder.liketext.setText("Unlike");
                   /* OtpGen otpgen=new OtpGen();
                    otpgen.execute("http://omtii.com/mile/updatelike.php?id="+postid+"&status=1");
                    try {
                        holder.postlikes.setText(otpgen.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.err.println("Post like count " + wallData.getPostlikes());
                    System.err.println("else part "+"http://omtii.com/mile/updatelike.php?id="+postid+"&status=1");*/
                }
                else
                {

                    System.err.println("i am in else commenticon");
                    holder.commentlayout.setId(R.id.commenticon1);
                    //    System.err.println("UnLike Post in else" + holder.liketext.getText());
                    holder.commentlayout.setImageResource(R.drawable.commentstroke);
                    //holder.liketext.setText("Like");
/*
                    OtpGen otpgen=new OtpGen();
                    otpgen.execute("http://omtii.com/mile/updatelike.php?id="+postid+"&status=0");

                    try {
                        holder.postlikes.setText(otpgen.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    System.err.println("Post like count " + wallData.getPostlikes());
                    System.err.println("else part "+"http://omtii.com/mile/updatelike.php?id="+postid+"&status=0");
                    */

                }

            }
        });*/
        System.err.println("Post id" + wallData.getPostid());

        holder.postlikes.setText(wallData.getPostlikes());
        holder.postcomments.setText(wallData.getPostcomments());
        holder.postshare.setText(wallData.getPostshare());
        holder.posttime.setText(wallData.getPosttime());






    }



    @Override
    public int getItemCount() {
        return postlist.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

       public CircleImageView profilepic;
        public TextView profilename;
        public TextView poststatus;
        public ImageView postpic;
        public VideoView postvdo;
        public TextView postlikes;
        public TextView postcomments;
        public TextView postshare;
        public TextView posttime;
        public ImageView commentlayout;
        //public TextView liketext;
        public ImageView likelayout;
        public ImageView likeicon;
        public MyViewHolder(View view) {
            super(view);
            profilepic = (CircleImageView) view.findViewById(R.id.img);
            profilename = (TextView) view.findViewById(R.id.ProfileName);
            poststatus = (TextView) view.findViewById(R.id.status);
            postpic=(ImageView) view.findViewById(R.id.mainimg);
            postvdo=(VideoView) view.findViewById(R.id.video);
            postlikes=(TextView) view.findViewById(R.id.likecount);
            postcomments=(TextView) view.findViewById(R.id.commentcount);
            postshare=(TextView) view.findViewById(R.id.sharecount);
            posttime=(TextView) view.findViewById(R.id.TimeStamp);
            likelayout=(ImageView) view.findViewById(R.id.likeicon1);
            commentlayout = (ImageView) view.findViewById(R.id.commenticon1);
           // liketext=(TextView) view.findViewById(R.id.liketext);
            //likeicon=(ImageView) view.findViewById(R.id.likeicon);

        }
    }

}

