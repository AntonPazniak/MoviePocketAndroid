package com.example.moviepocketandroid.ui.list.movie;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.movie.Genre;
import com.example.moviepocketandroid.ui.until.AuthorAndRating;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class ListMovieFragment extends Fragment {

    int idList;
    private ListMovieViewModel mViewModel;
    private TextView textViewTitle, textOverview,textViewNickname;
    private RecyclerView recyclerViewList;
    private MovieList movieList;
    private ImageView imageBackMovie, imageViewAvatar;
    private Context context;
    private boolean isExpanded = false;
    private ChipGroup chipGroup;
    private View view;
    private Boolean isAuthentication;

    public ListMovieFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public ListMovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");

            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewNickname = view.findViewById(R.id.textViewNickname);
            recyclerViewList = view.findViewById(R.id.recyclerViewList);
            imageBackMovie = view.findViewById(R.id.imageBackMovie);
            imageViewAvatar= view.findViewById(R.id.imageViewAvatar);
            textOverview = view.findViewById(R.id.textOverview);
            chipGroup = view.findViewById(R.id.chipGroup);
            this.view = view;
            context = view.getContext();
            loadListInf();
        }

    }


    private void loadListInf() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isAuthentication = MPAuthenticationApi.checkAuth();
                movieList = MPListApi.getListById(idList);
                if (movieList != null) {
                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setListInf();
                            }
                        });
                    }
                }
            }
        }).start();

    }


    private void setListInf() {
        textViewTitle.setText(movieList.getTitle());
        textOverview.setText(movieList.getContent());
        AuthorAndRating authorAndRating = new AuthorAndRating(view, idList, movieList.getUser(), movieList.getLikeOrDis());
        authorAndRating.setListRatingButtons(isAuthentication);

        chipGroup.removeAllViews();

        for (Genre genre : movieList.getGenres()) {
            CardView cardView = new CardView(context);
            cardView.setRadius(getResources().getDimension(R.dimen.corner_radius));
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey)
            );

            MaterialTextView chip = new MaterialTextView(context);
            chip.setText(genre.getName());
            chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            chip.setPadding(8, 8, 8, 8);

            cardView.addView(chip);
            chipGroup.addView(cardView);
        }

        textOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    textOverview.setMaxLines(3);
                    textOverview.setEllipsize(TextUtils.TruncateAt.END);
                } else {
                    textOverview.setMaxLines(Integer.MAX_VALUE);
                    textOverview.setEllipsize(null);
                }
                isExpanded = !isExpanded;
            }
        });

        if (movieList.getMovies() != null && !movieList.getMovies().isEmpty()) {

            Random random = new Random();
            int randMovie = random.nextInt(movieList.getMovies().size() - 1);

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(movieList.getMovies().get(randMovie).getBackdropPath())
                    .apply(requestOptions)
                    .into(imageBackMovie);

            MovieAdapter movieAdapter = new MovieAdapter(movieList.getMovies());
            recyclerViewList.setAdapter(movieAdapter);

            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
            recyclerViewList.setLayoutManager(layoutManager);

            movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_listFragment_to_movieFragment, args);
                }
            });
        }

    }

}