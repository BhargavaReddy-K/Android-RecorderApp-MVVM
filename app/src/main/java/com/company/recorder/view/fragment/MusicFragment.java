package com.company.recorder.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company.recorder.R;
import com.company.recorder.model.adapter.RecordingsAdapter;
import com.company.recorder.model.dto.MusicModel;
import com.company.recorder.model.interfaces.IMessages;
import com.company.recorder.model.utils.CheckInternet;
import com.company.recorder.model.utils.Messages;
import com.company.recorder.viewmodel.MusicViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MusicFragment extends Fragment {

  private   List<MusicModel> audioArrayList=new ArrayList<>();
    private  RecyclerView recyclerView;
    private MediaPlayer mediaPlayer;
    private double current_pos;
    private TextView current, total, title;
    private ImageView prev, next, pause;
    private SeekBar seekBar;
    private LinearLayout viewLinearLayout;
    private int audio_index = 0;
    public static final int PERMISSION_READ = 0;

    private MusicViewModel musicViewModel;
    private IMessages messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicViewModel = ViewModelProviders.of(this).get(MusicViewModel.class);
        messages = new Messages(getActivity());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for news fragment
        return inflater.inflate(R.layout.fragment_music, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            if (checkPermission()) {
                recyclerView = view.findViewById(R.id.recycler_view);
                current = view.findViewById(R.id.current);
                total = view.findViewById(R.id.total);
                prev = view.findViewById(R.id.prev);
                next = view.findViewById(R.id.next);
                pause = view.findViewById(R.id.pause);
                title = view.findViewById(R.id.title);
                seekBar = view.findViewById(R.id.seekbar);
                viewLinearLayout = view.findViewById(R.id.linear_layoutView);
                viewLinearLayout.setVisibility(View.GONE);
                getData();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        try {
            CheckInternet checkInternet = new CheckInternet(getActivity());
            if (checkInternet.isInternetPresent()) {
                //initialize view model
                musicViewModel.initialize();
                musicViewModel.getMusicRepository().observe(this, new Observer<List<MusicModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MusicModel> musicModels) {
                        try {
                            if (musicModels != null) {
                                audioArrayList.clear();
                                audioArrayList.addAll(musicModels);
                                getRecordings();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                });

            } else {
                messages.toastInternet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getRecordings() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mediaPlayer = new MediaPlayer();
        getAudioRecordings();
        setPause();
        nextRecording();
        prevRecording();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                mediaPlayer.seekTo((int) current_pos);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audio_index++;
                if (audio_index < (audioArrayList.size())) {
                    playRecording(audio_index);
                } else {
                    audio_index = 0;
                    playRecording(audio_index);
                }
            }
        });

//        if (!audioArrayList.isEmpty()) {
//            playRecording(audio_index);
//            prevRecording();
//            nextRecording();
//            setPause();
//        }
    }

    public void playRecording(int pos) {
        try {
            mediaPlayer.reset();
            viewLinearLayout.setVisibility(View.VISIBLE);
            title.setText(audioArrayList.get(pos).getTitle());
            mediaPlayer.setDataSource(Objects.requireNonNull(getActivity()), audioArrayList.get(pos).getUri());
            mediaPlayer.prepare();
            mediaPlayer.start();
            pause.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            audio_index = pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAudioProgress();
    }

    public void setAudioProgress() {
        current_pos = mediaPlayer.getCurrentPosition();
        double total_duration = mediaPlayer.getDuration();

        total.setText(messages.timeConversion((long) total_duration));
        current.setText(messages.timeConversion((long) current_pos));
        seekBar.setMax((int) total_duration);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = mediaPlayer.getCurrentPosition();
                    current.setText(messages.timeConversion((long) current_pos));
                    seekBar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void prevRecording() {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audio_index > 0) {
                    audio_index--;
                    playRecording(audio_index);
                } else {
                    audio_index = audioArrayList.size() - 1;
                    playRecording(audio_index);
                }
            }
        });
    }

    public void nextRecording() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audio_index < (audioArrayList.size() - 1)) {
                    audio_index++;
                    playRecording(audio_index);
                } else {
                    audio_index = 0;
                    playRecording(audio_index);
                }
            }
        });
    }

    public void setPause() {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pause.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                } else {
                    mediaPlayer.start();
                    pause.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }
            }
        });
    }


    public void getAudioRecordings() {
        ContentResolver contentResolver = Objects.requireNonNull(getActivity()).getContentResolver();
        //creating content resolver and fetch audio files
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        @SuppressLint("Recycle")
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA + " like ?", new String[]{"%RecordApp%"}, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                MusicModel modelRecordings = new MusicModel();
                modelRecordings.setTitle(title);
                File file = new File(data);
                Date date = new Date(file.lastModified());
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

                modelRecordings.setDate(format.format(date));
                modelRecordings.setUri(Uri.parse(data));

                //fetch the audio duration using MediaMetadataRetriever class
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(data);

                modelRecordings.setDuration(messages.timeConversion(Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))));
                audioArrayList.add(modelRecordings);

            } while (cursor.moveToNext());
        }

        RecordingsAdapter adapter = new RecordingsAdapter(getActivity(), audioArrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecordingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                playRecording(pos);
            }
        });

    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(getActivity(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        getAudioRecordings();
                    }
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


}
