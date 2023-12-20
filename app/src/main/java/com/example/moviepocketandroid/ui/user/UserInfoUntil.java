package com.example.moviepocketandroid.ui.user;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.api.models.user.User;

public class UserInfoUntil {

    public static void setUserInfo(User user, Context context, ImageView imageViewAvatar){
        if (user.getAvatar() != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(user.getAvatar())
                    .apply(requestOptions)
                    .into(imageViewAvatar);
        }
    }

}
