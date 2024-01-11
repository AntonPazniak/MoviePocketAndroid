/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.ui.review.newr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.api.models.review.Review;

public class NewReviewFragment extends Fragment {


    private EditText titleEditText;
    private MultiAutoCompleteTextView contentEditText;
    private Button publishButton;
    private TextView textViewHead, textViewTitle, textViewContent;

    public void setIdListEdit(int idListEdit) {
        Bundle args = new Bundle();
        args.putInt("idListEdit", idListEdit);
        setArguments(args);
    }

    public void setIdPostEdit(int idPostEdit) {
        Bundle args = new Bundle();
        args.putInt("idPostEdit", idPostEdit);
        setArguments(args);
    }

    public static NewReviewFragment newInstance() {
        return new NewReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_new, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditText = view.findViewById(R.id.editTextTitle);
        contentEditText = view.findViewById(R.id.multiTextContent);
        publishButton = view.findViewById(R.id.buttonPublish);
        textViewHead = view.findViewById(R.id.textViewHead);

        textViewTitle = view.findViewById(R.id.textView0);
        textViewContent = view.findViewById(R.id.textView1);

        textViewTitle.setText(R.string.title);
        textViewContent.setText(R.string.content);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie", -1);
            int idReview = args.getInt("idReview", -1);
            int idList = args.getInt("idList", -1);
            int idPost = args.getInt("idPost", -1);
            int idListEdit = args.getInt("idListEdit", -1);
            int idPostEdit = args.getInt("idPostEdit", -1);
            int newList = args.getInt("newList", -1);
            int idMovieNewPost = args.getInt("idMovieNewPost", -1);
            int idPersonNewPost = args.getInt("idPersonNewPost", -1);
            int idListNewPost = args.getInt("idListNewPost", -1);
            if (idMovie != -1) {
                newReviewMovie(idMovie);
            } else if (idReview != -1) {
                editReview(idReview);
            } else if (idList != -1) {
                newReviewList(idList);
            } else if (idPost != -1) {
                newReviewPost(idPost);
            } else if (idListEdit != -1) {
                editList(idListEdit);
            } else if (newList != -1) {
                newList();
            } else if (idMovieNewPost != -1) {
                newPostMovie(idMovieNewPost);
            } else if (idPostEdit != -1) {
                editPost(idPostEdit);
            } else if (idPersonNewPost != -1) {
                newPostPerson(idPersonNewPost);
            }

        }
    }

    private void newReviewMovie(int idMovie) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPReviewApi.postReviewMovie(idMovie, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void newReviewList(int idList) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPReviewApi.postReviewList(idList, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void newReviewPost(int idList) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPReviewApi.postReviewPost(idList, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editReview(int idReview) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Review review = MPReviewApi.getReviewById(idReview);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textViewHead.setText(R.string.edit_your_review);
                        titleEditText.setText(review.getTitle());
                        contentEditText.setText(review.getContent());
                    }
                });
            }
        }).start();

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPReviewApi.editReviewMovie(idReview, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editList(int idList) {
        textViewHead.setText(R.string.edit_your_list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieList list = MPListApi.getListById(idList);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            publishButton.setText(R.string.save);
                            titleEditText.setText(list.getTitle());
                            contentEditText.setText(list.getContent());
                        }
                    }
                });
            }
        }).start();

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean isSuccessful = MPListApi.editList(idList, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccessful)
                                        Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void newList() {
        textViewHead.setText(R.string.create_your_list);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MovieList list = MPListApi.newList(title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (list != null) {
                                        Bundle args = new Bundle();
                                        args.putInt("idList", list.getId());
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                        Bundle argss = getArguments();
                                        int feed = argss.getInt("feed", -1);
                                        if (feed != -1)
                                            navController.navigate(R.id.action_feedFragment_to_listFragment, args);
                                        else
                                            navController.navigate(R.id.action_movieFragment_to_listFragment, args);
                                    } else {
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editPost(int idPost) {
        textViewHead.setText(R.string.edit_your_post);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Post post = MPPostApi.getPostById(idPost);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (post != null) {
                            publishButton.setText(R.string.save);
                            titleEditText.setText(post.getTitle());
                            contentEditText.setText(post.getContent());
                        }
                    }
                });
            }
        }).start();

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean isSuccessful = MPPostApi.editPost(idPost, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccessful)
                                        Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void newPostMovie(int idMovie) {
        textViewHead.setText(R.string.create_your_post);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Post post = MPPostApi.newPostMovie(idMovie, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (post != null) {
                                        Bundle args = new Bundle();
                                        args.putInt("idPost", post.getId());
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                        navController.navigate(R.id.action_movieFragment_to_postFragment, args);
                                    } else {
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void newPostPerson(int idPerson) {
        textViewHead.setText(R.string.create_your_post);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                if (!title.isEmpty() && !content.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Post post = MPPostApi.newPostPerson(idPerson, title, content);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (post != null) {
                                        Bundle args = new Bundle();
                                        args.putInt("idPost", post.getId());
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                        navController.navigate(R.id.action_personFragment_to_postFragment, args);
                                    } else {
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "All input fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}