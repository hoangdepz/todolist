package com.demo.checklistnotedemo.AdapterClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.aghajari.emojiview.view.AXEmojiEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.checklistnotedemo.activity.ActivityViewPager;
import com.demo.checklistnotedemo.DataHolderClasses.HolderData;
import com.demo.checklistnotedemo.Listeners.EditTextClickListeners;
import com.demo.checklistnotedemo.Models.ImageModels;
import com.demo.checklistnotedemo.Models.ListItems;
import com.demo.checklistnotedemo.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class Recording_ImageAdapter extends RecyclerView.Adapter {
    private static final int Image_Item = 0;
    private static final int Recording_Item = 1;
    private static final int center = 1;
    private static final int end = 8388613;
    private static final int start = 8388611;
    int color;
    private Context context;
    private int currentposition;
    String font;
    String fontName;
    int gravity;
    ImageModels imageModel;
    private List<ListItems> items;
    EditTextClickListeners listener;
    private MediaPlayer mediaPlayer;
    List<ImageModels> list = new ArrayList();
    int selectedGravity = -1;
    List<EditText> edittextList = new ArrayList();
    List<LinearLayout> layoutGravity = new ArrayList();

    public abstract void DelteRecording(ImageModels imageModels);

    public abstract void onDeleteButton(ImageModels imageModels);

    @Override 
    public int getItemViewType(int i) {
        ImageModels imageModels = (ImageModels) this.items.get(i);
        this.imageModel = imageModels;
        if (imageModels.getImage() == null) {
            return 1;
        }
        if (this.imageModel.getFilepath() == null) {
            return 0;
        }
        return super.getItemViewType(i);
    }

    public Recording_ImageAdapter(List<ListItems> list, Context context, EditTextClickListeners editTextClickListeners, int i) {
        this.listener = editTextClickListeners;
        this.context = context;
        this.items = list;
        this.gravity = i;
    }

    @Override 
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_image, viewGroup, false));
        }
        if (i == 1) {
            return new RecoridngViewHolder(LayoutInflater.from(this.context).inflate(R.layout.recording_item, viewGroup, false));
        }
        return null;
    }

    private Bitmap convertBase64ToBitmap(String str) {
        if (str == null || str.isEmpty()) {
            Context context = this.context;
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            return null;
        }
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public void updateFontStyle(String str) {
        Iterator<EditText> it = this.edittextList.iterator();
        while (it.hasNext()) {
            it.next().setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/" + str + ".ttf"));
        }
    }

    public void gravity(int i) {
        this.selectedGravity = i;
        notifyDataSetChanged();
    }

    public void updateGravity(int i) {
        for (LinearLayout linearLayout : this.layoutGravity) {
            int i2 = this.gravity;
            if (i2 != 0) {
                linearLayout.setGravity(i2);
            }
            if (linearLayout != null) {
                if (i == 0) {
                    linearLayout.setGravity(8388611);
                } else if (i == 1) {
                    linearLayout.setGravity(1);
                } else if (i == 2) {
                    linearLayout.setGravity(8388613);
                }
            }
        }
    }

    public void updateFontColor(int i) {
        for (EditText editText : this.edittextList) {
            editText.setTextColor(i);
            editText.setHintTextColor(i);
        }
    }

    @Override 
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int color7;
        int color8;
        int color9;
        int color10;
        final ListItems listItems = this.items.get(i);
        int i2 = this.context.getSharedPreferences("my_prefs", 0).getInt("my_key", 0);
        if (viewHolder instanceof ImageViewHolder) {
            final ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            final ImageModels imageModels = (ImageModels) listItems;
            imageViewHolder.imageEditText.requestFocus();
            this.listener.onEditTextClick(imageViewHolder.imageEditText);
            if (this.gravity != 0) {
                imageViewHolder.gravityLayout.setGravity(this.gravity);
            }
            if (this.selectedGravity == -1) {
                this.selectedGravity = imageModels.getGravity();
            }
            this.layoutGravity.add(imageViewHolder.gravityLayout);
            this.edittextList.add(imageViewHolder.imageEditText);
            int i3 = this.selectedGravity;
            if (i3 == 0) {
                imageViewHolder.gravityLayout.setGravity(8388611);
            } else if (i3 == 1) {
                imageViewHolder.gravityLayout.setGravity(1);
            } else if (i3 == 2) {
                imageViewHolder.gravityLayout.setGravity(8388613);
            }
            if (this.fontName == null) {
                this.fontName = imageModels.getFontName();
            }
            if (this.fontName != null) {
                imageViewHolder.imageEditText.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/" + this.fontName + ".ttf"));
            }
            if (this.color == 0) {
                this.color = imageModels.getTextcolor();
            }
            int i4 = this.color;
            if (i4 != 0 && i4 != -1) {
                imageViewHolder.imageEditText.setTextColor(this.color);
                imageViewHolder.imageEditText.setHintTextColor(this.color);
            } else if (i2 == 5) {
                if (imageModels.getIsDark() == 1) {
                    AXEmojiEditText aXEmojiEditText = imageViewHolder.imageEditText;
                    color9 = this.context.getColor(R.color.black);
                    aXEmojiEditText.setTextColor(color9);
                    AXEmojiEditText aXEmojiEditText2 = imageViewHolder.imageEditText;
                    color10 = this.context.getColor(R.color.black);
                    aXEmojiEditText2.setHintTextColor(color10);
                } else {
                    AXEmojiEditText aXEmojiEditText3 = imageViewHolder.imageEditText;
                    color7 = this.context.getColor(R.color.white);
                    aXEmojiEditText3.setTextColor(color7);
                    AXEmojiEditText aXEmojiEditText4 = imageViewHolder.imageEditText;
                    color8 = this.context.getColor(R.color.text_hint_white);
                    aXEmojiEditText4.setHintTextColor(color8);
                }
            } else {
                AXEmojiEditText aXEmojiEditText5 = imageViewHolder.imageEditText;
                color5 = this.context.getColor(R.color.black);
                aXEmojiEditText5.setTextColor(color5);
                AXEmojiEditText aXEmojiEditText6 = imageViewHolder.imageEditText;
                color6 = this.context.getColor(R.color.text_hint);
                aXEmojiEditText6.setHintTextColor(color6);
            }
            if (imageModels == null || imageModels.getImage() == null) {
                return;
            }
            Bitmap convertBase64ToBitmap = convertBase64ToBitmap(imageModels.getImage());
            if (convertBase64ToBitmap != null) {
                Glide.with(this.context).load(convertBase64ToBitmap).placeholder(R.drawable.placeholder).format(DecodeFormat.PREFER_RGB_565).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewHolder.imageView);
            }
            this.list.add(new ImageModels(imageModels.getImage()));
            imageViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() { 
                @Override 
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(Recording_ImageAdapter.this.context).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i5) {
                            Recording_ImageAdapter.this.onDeleteButton(imageModels);
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i5) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
            imageViewHolder.imageView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    HolderData.getInstance().setImageList(new ArrayList(Recording_ImageAdapter.this.list));
                    Intent intent = new Intent(Recording_ImageAdapter.this.context, (Class<?>) ActivityViewPager.class);
                    intent.putExtra("imagePosition", i);
                    Recording_ImageAdapter.this.context.startActivity(intent);
                }
            });
            imageViewHolder.imageEditText.setText(imageModels.getImageText());
            imageViewHolder.imageEditText.addTextChangedListener(new TextWatcher() { 
                @Override 
                public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                }

                @Override 
                public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                }

                @Override 
                public void afterTextChanged(Editable editable) {
                    imageModels.setImageText(editable.toString());
                }
            });
            imageViewHolder.imageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { 
                @Override 
                public void onFocusChange(View view, boolean z) {
                    if (z) {
                        Recording_ImageAdapter.this.listener.onEditTextClick(imageViewHolder.imageEditText);
                    }
                }
            });
            return;
        }
        final ImageModels imageModels2 = (ImageModels) listItems;
        final RecoridngViewHolder recoridngViewHolder = (RecoridngViewHolder) viewHolder;
        recoridngViewHolder.bind(imageModels2, i, listItems, recoridngViewHolder);
        recoridngViewHolder.recordinget.requestFocus();
        this.listener.onEditTextClick(recoridngViewHolder.recordinget);
        if (this.fontName == null) {
            this.fontName = imageModels2.getFontName();
        }
        if (this.fontName != null) {
            recoridngViewHolder.recordinget.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/" + this.fontName + ".ttf"));
        }
        if (this.color == 0) {
            this.color = this.imageModel.getTextcolor();
        }
        int i5 = this.color;
        if (i5 != 0 && i5 != -1) {
            recoridngViewHolder.recordinget.setTextColor(this.color);
            recoridngViewHolder.recordinget.setHintTextColor(this.color);
        } else if (i2 == 5) {
            AXEmojiEditText aXEmojiEditText7 = recoridngViewHolder.recordinget;
            color3 = this.context.getColor(R.color.white);
            aXEmojiEditText7.setTextColor(color3);
            AXEmojiEditText aXEmojiEditText8 = recoridngViewHolder.recordinget;
            color4 = this.context.getColor(R.color.text_hint_white);
            aXEmojiEditText8.setHintTextColor(color4);
        } else {
            AXEmojiEditText aXEmojiEditText9 = recoridngViewHolder.recordinget;
            color = this.context.getColor(R.color.black);
            aXEmojiEditText9.setTextColor(color);
            AXEmojiEditText aXEmojiEditText10 = recoridngViewHolder.recordinget;
            color2 = this.context.getColor(R.color.text_hint);
            aXEmojiEditText10.setHintTextColor(color2);
        }
        recoridngViewHolder.recordinget.setText(imageModels2.getRecordingText());
        recoridngViewHolder.recordinget.addTextChangedListener(new TextWatcher() { 
            @Override 
            public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }

            @Override 
            public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }

            @Override 
            public void afterTextChanged(Editable editable) {
                imageModels2.setRecordingText(editable.toString());
            }
        });
        recoridngViewHolder.recordinget.setOnFocusChangeListener(new View.OnFocusChangeListener() { 
            @Override 
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    Recording_ImageAdapter.this.listener.onEditTextClick(recoridngViewHolder.recordinget);
                }
            }
        });
        this.edittextList.add(recoridngViewHolder.recordinget);
        recoridngViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { 
            @Override 
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(Recording_ImageAdapter.this.context).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i6) {
                        Recording_ImageAdapter.this.DelteRecording(imageModels2);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i6) {
                        dialogInterface.dismiss();
                    }
                }).show();
                return true;
            }
        });
        recoridngViewHolder.btnplay.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                RecoridngViewHolder recoridngViewHolder2 = recoridngViewHolder;
                recoridngViewHolder2.togglePlayback(recoridngViewHolder2, recoridngViewHolder2.getAdapterPosition(), listItems);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.items.size();
    }

    
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout gravityLayout;
        AXEmojiEditText imageEditText;
        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.noteImage);
            this.imageEditText = (AXEmojiEditText) view.findViewById(R.id.imageedittxt);
            this.gravityLayout = (LinearLayout) view.findViewById(R.id.gravityLayout);
        }
    }

    
    public class RecoridngViewHolder extends RecyclerView.ViewHolder {
        private ImageView btnplay;
        private boolean isplaying;
        AXEmojiEditText recordinget;
        private SeekBar seekBar;
        private TextView txtStartTime;
        private TextView txtStop;

        public RecoridngViewHolder(View view) {
            super(view);
            this.isplaying = false;
            this.txtStartTime = (TextView) view.findViewById(R.id.txtstarttime);
            this.seekBar = (SeekBar) view.findViewById(R.id.seekbar);
            this.txtStop = (TextView) view.findViewById(R.id.txtTotalTime);
            this.btnplay = (ImageView) view.findViewById(R.id.play);
            this.recordinget = (AXEmojiEditText) view.findViewById(R.id.recordingedittxt);
        }

        
        public void togglePlayback(RecoridngViewHolder recoridngViewHolder, int i, ListItems listItems) {
            if (Recording_ImageAdapter.this.mediaPlayer != null) {
                if (Recording_ImageAdapter.this.mediaPlayer.isPlaying()) {
                    Recording_ImageAdapter.this.mediaPlayer.pause();
                    recoridngViewHolder.btnplay.setImageResource(R.drawable.play_rec);
                    recoridngViewHolder.isplaying = false;
                    return;
                }
                ImageModels imageModels = (ImageModels) listItems;
                if (Recording_ImageAdapter.this.mediaPlayer.isPlaying()) {
                    Recording_ImageAdapter.this.mediaPlayer.stop();
                    Recording_ImageAdapter.this.mediaPlayer.release();
                }
                Recording_ImageAdapter recording_ImageAdapter = Recording_ImageAdapter.this;
                recording_ImageAdapter.mediaPlayer = MediaPlayer.create(recording_ImageAdapter.context, Uri.parse(imageModels.getFilepath()));
                Recording_ImageAdapter.this.mediaPlayer.start();
                recoridngViewHolder.btnplay.setImageResource(R.drawable.pause_rec);
                updateSeekbar(recoridngViewHolder);
                recoridngViewHolder.isplaying = true;
                updateTxtStartTime(recoridngViewHolder);
            }
        }

        
        public void updateTxtStartTime(final RecoridngViewHolder recoridngViewHolder) {
            if (Recording_ImageAdapter.this.mediaPlayer != null) {
                recoridngViewHolder.seekBar.setMax(Recording_ImageAdapter.this.mediaPlayer.getDuration());
                new Thread(new Runnable() { 
                    @Override 
                    public void run() {
                        while (Recording_ImageAdapter.this.mediaPlayer.isPlaying() && recoridngViewHolder.isplaying) {
                            try {
                                Thread.sleep(500L);
                                ((Activity) Recording_ImageAdapter.this.context).runOnUiThread(new Runnable() { 
                                    @Override 
                                    public void run() {
                                        int currentPosition = Recording_ImageAdapter.this.mediaPlayer.getCurrentPosition();
                                        recoridngViewHolder.seekBar.setProgress(currentPosition);
                                        recoridngViewHolder.txtStartTime.setText(Recording_ImageAdapter.this.createTime(currentPosition));
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (Recording_ImageAdapter.this.mediaPlayer.isPlaying()) {
                            return;
                        }
                        ((Activity) Recording_ImageAdapter.this.context).runOnUiThread(new Runnable() { 
                            @Override 
                            public void run() {
                                recoridngViewHolder.seekBar.setProgress(0);
                                recoridngViewHolder.txtStartTime.setText("0:00");
                                recoridngViewHolder.btnplay.setImageResource(R.drawable.play_rec);
                            }
                        });
                    }
                }).start();
            }
        }

        private void updateSeekbar(final RecoridngViewHolder recoridngViewHolder) {
            if (Recording_ImageAdapter.this.mediaPlayer != null) {
                recoridngViewHolder.seekBar.setMax(Recording_ImageAdapter.this.mediaPlayer.getDuration());
                new Thread(new Runnable() { 
                    @Override 
                    public void run() {
                        while (Recording_ImageAdapter.this.mediaPlayer.isPlaying() && recoridngViewHolder.isplaying) {
                            try {
                                Thread.sleep(500L);
                                ((Activity) Recording_ImageAdapter.this.context).runOnUiThread(new Runnable() { 
                                    @Override 
                                    public void run() {
                                        recoridngViewHolder.seekBar.setProgress(Recording_ImageAdapter.this.mediaPlayer.getCurrentPosition());
                                        RecoridngViewHolder.this.updateTxtStartTime(recoridngViewHolder);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (Recording_ImageAdapter.this.mediaPlayer.isPlaying()) {
                            return;
                        }
                        ((Activity) Recording_ImageAdapter.this.context).runOnUiThread(new Runnable() { 
                            @Override 
                            public void run() {
                                recoridngViewHolder.seekBar.setProgress(0);
                                recoridngViewHolder.txtStartTime.setText("0:00");
                                recoridngViewHolder.btnplay.setImageResource(R.drawable.play_rec);
                            }
                        });
                    }
                }).start();
            }
        }

        public void bind(ImageModels imageModels, int i, ListItems listItems, RecoridngViewHolder recoridngViewHolder) {
            Recording_ImageAdapter.this.currentposition = i;
            if (Recording_ImageAdapter.this.mediaPlayer != null) {
                Recording_ImageAdapter.this.mediaPlayer.stop();
                Recording_ImageAdapter.this.mediaPlayer.release();
            }
            if (i == Recording_ImageAdapter.this.currentposition && this.isplaying) {
                this.btnplay.setImageResource(R.drawable.play_rec);
                updateSeekbar(recoridngViewHolder);
            }
            ImageModels imageModels2 = (ImageModels) listItems;
            if (Recording_ImageAdapter.this.mediaPlayer == null) {
                Recording_ImageAdapter recording_ImageAdapter = Recording_ImageAdapter.this;
                recording_ImageAdapter.mediaPlayer = MediaPlayer.create(recording_ImageAdapter.context, Uri.parse(imageModels2.getFilepath()));
                Recording_ImageAdapter.this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { 
                    @Override 
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        RecoridngViewHolder.this.btnplay.setImageResource(R.drawable.play_rec);
                        RecoridngViewHolder.this.isplaying = false;
                    }
                });
            }
            Recording_ImageAdapter recording_ImageAdapter2 = Recording_ImageAdapter.this;
            recording_ImageAdapter2.mediaPlayer = MediaPlayer.create(recording_ImageAdapter2.context, Uri.parse(imageModels2.getFilepath()));
            this.seekBar.setMax(Recording_ImageAdapter.this.mediaPlayer.getDuration());
            this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
                @Override 
                public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                }

                @Override 
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override 
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Recording_ImageAdapter.this.mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
            Recording_ImageAdapter recording_ImageAdapter3 = Recording_ImageAdapter.this;
            this.txtStop.setText(recording_ImageAdapter3.createTime(recording_ImageAdapter3.mediaPlayer.getDuration()));
            new Handler();
        }
    }

    public String createTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String str = "" + i3 + ":";
        if (i4 < 10) {
            str = str + "0";
        }
        return str + i4;
    }
}
