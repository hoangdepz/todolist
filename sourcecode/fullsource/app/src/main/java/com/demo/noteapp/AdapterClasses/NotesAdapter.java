package com.demo.noteapp.AdapterClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.noteapp.ActivityArchived;
import com.demo.noteapp.AddNoteActivity;
import com.demo.noteapp.DatabaseClasses.MyHelperImage;
import com.demo.noteapp.Models.ImageModels;
import com.demo.noteapp.Models.Notes;
import com.demo.noteapp.R;
import com.demo.noteapp.TrashActivity;
import com.demo.noteapp.paramsClasses.Param;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public abstract class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    Context context;
    boolean istrue;
    List<Notes> list;
    int theme;

    public abstract void onArchived(Notes notes);

    public abstract void onDeleteButton(Notes notes);

    public abstract void onDeleteTrash(Notes notes);

    public NotesAdapter(List<Notes> list, Context context, boolean z) {
        this.list = list;
        this.istrue = z;
        this.context = context;
    }

    @Override 
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NoteViewHolder(LayoutInflater.from(this.context).inflate(R.layout.recycler_note__item, viewGroup, false));
    }

    private Bitmap convertBase64ToBitmap(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    private ImageModels getImageModelForNoteId(long j) {
        for (ImageModels imageModels : new MyHelperImage(this.context).getImagesByNoteId(j)) {
            if (imageModels.getNoteId() == j) {
                return imageModels;
            }
        }
        return null;
    }

    @Override 
    public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {
        final Notes notes = this.list.get(i);
        String fontName = notes.getFontName();
        noteViewHolder.settheme();
        this.context.getSharedPreferences("my_prefs", 0);
        if (fontName != null) {
            Typeface createFromAsset = Typeface.createFromAsset(this.context.getAssets(), "font/" + fontName + ".ttf");
            if (createFromAsset != null) {
                noteViewHolder.contentTextView.setTypeface(createFromAsset);
                noteViewHolder.titleTextView.setTypeface(createFromAsset);
                noteViewHolder.timesTamp.setTypeface(createFromAsset);
            }
        }
        int textColor = notes.getTextColor();
        if (textColor != 0 && textColor != -1) {
            noteViewHolder.contentTextView.setTextColor(textColor);
            noteViewHolder.titleTextView.setTextColor(textColor);
            noteViewHolder.timesTamp.setTextColor(textColor);
        } else if (this.theme == 5) {
            if (notes.getColor() == null) {
                noteViewHolder.contentTextView.setTextColor(-1);
                noteViewHolder.titleTextView.setTextColor(-1);
                noteViewHolder.timesTamp.setTextColor(-1);
            } else {
                noteViewHolder.contentTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                noteViewHolder.titleTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                noteViewHolder.timesTamp.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        } else {
            noteViewHolder.contentTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            noteViewHolder.titleTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            noteViewHolder.timesTamp.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        noteViewHolder.titleTextView.setText(notes.getTitle());
        noteViewHolder.contentTextView.setText(notes.getContent());
        if (notes.getReminderDate() > 0) {
            noteViewHolder.bell.setVisibility(View.VISIBLE);
        } else {
            noteViewHolder.bell.setVisibility(View.GONE);
        }
        if (notes.getTimestamp() > 0) {
            noteViewHolder.timesTamp.setText(notes.getFormattedTimestamp());
        } else {
            noteViewHolder.timesTamp.setText(new SimpleDateFormat("yyyy/MM/dd HH/mm/ss", Locale.ENGLISH).format(new Date()));
        }
        final String valueOf = String.valueOf(notes.getId());
        final long id = notes.getId();
        getImageModelForNoteId(id);
        if (notes.getColor() == null) {
            if (this.theme == 5) {
                noteViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#3A3A3A"));
            } else {
                noteViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FBFBFB"));
            }
            noteViewHolder.backgroundImg.setVisibility(View.GONE);
        } else if (notes.getColor() != null) {
            Bitmap convertBase64ToBitmap = convertBase64ToBitmap(notes.getColor());
            noteViewHolder.backgroundImg.setVisibility(View.VISIBLE);
            noteViewHolder.backgroundImg.setImageBitmap(convertBase64ToBitmap);
        }
        if (this.istrue) {
            noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Intent intent = new Intent(NotesAdapter.this.context, (Class<?>) AddNoteActivity.class);
                    intent.putExtra(Param.KEY_TITLE, notes.getTitle());
                    intent.putExtra(Param.KEY_CONTENT, notes.getContent());
                    intent.putExtra("docId", valueOf);
                    intent.putExtra("noteId", id);
                    intent.putExtra("color", notes.getColor());
                    intent.putExtra("font", notes.getFontName());
                    intent.putExtra("textColor", notes.getTextColor());
                    intent.putExtra(Param.GRAVITY, notes.getGravity());
                    intent.putExtra("time", notes.getTimestamp());
                    intent.putExtra("reminder", notes.getReminderDate());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    NotesAdapter.this.context.startActivity(intent);
                }
            });
            noteViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { 
                @Override 
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(NotesAdapter.this.context).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            NotesAdapter.this.onDeleteButton(notes);
                            dialogInterface.dismiss();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
            return;
        }
        noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Toast.makeText(NotesAdapter.this.context, NotesAdapter.this.context.getResources().getString(R.string.please_long_click_to_restore), Toast.LENGTH_SHORT).show();
            }
        });
        if (this.context instanceof TrashActivity) {
            noteViewHolder.itemView.setOnLongClickListener(new AnonymousClass4(notes));
        }
        if (this.context instanceof ActivityArchived) {
            noteViewHolder.itemView.setOnLongClickListener(new AnonymousClass5(notes));
        }
    }

    
    
    
    public class AnonymousClass4 implements View.OnLongClickListener {
        final  Notes val$note;

        AnonymousClass4(Notes notes) {
            this.val$note = notes;
        }

        @Override 
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NotesAdapter.this.context);
            View inflate = LayoutInflater.from(NotesAdapter.this.context).inflate(R.layout.custom_alert_dialog, (ViewGroup) null);
            builder.setView(inflate);
            final AlertDialog create = builder.create();
            TextView textView = (TextView) inflate.findViewById(R.id.restoreAD);
            TextView textView2 = (TextView) inflate.findViewById(R.id.deleteAD);
            textView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    NotesAdapter.this.onDeleteButton(AnonymousClass4.this.val$note);
                    create.dismiss();
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    new AlertDialog.Builder(NotesAdapter.this.context).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (NotesAdapter.this.context instanceof TrashActivity) {
                                NotesAdapter.this.onDeleteTrash(AnonymousClass4.this.val$note);
                                create.dismiss();
                            }
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
            });
            create.show();
            return true;
        }
    }

    
    
    
    public class AnonymousClass5 implements View.OnLongClickListener {
        final  Notes val$note;

        AnonymousClass5(Notes notes) {
            this.val$note = notes;
        }

        @Override 
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NotesAdapter.this.context);
            View inflate = LayoutInflater.from(NotesAdapter.this.context).inflate(R.layout.custom_alert_dialog, (ViewGroup) null);
            builder.setView(inflate);
            final AlertDialog create = builder.create();
            TextView textView = (TextView) inflate.findViewById(R.id.restoreAD);
            TextView textView2 = (TextView) inflate.findViewById(R.id.deleteAD);
            textView.setText(R.string.unarchived);
            textView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    NotesAdapter.this.onDeleteButton(AnonymousClass5.this.val$note);
                    create.dismiss();
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    new AlertDialog.Builder(NotesAdapter.this.context).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (NotesAdapter.this.context instanceof ActivityArchived) {
                                NotesAdapter.this.onDeleteTrash(AnonymousClass5.this.val$note);
                                create.dismiss();
                            }
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
            });
            create.show();
            return true;
        }
    }

    @Override 
    public int getItemCount() {
        List<Notes> list = this.list;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setNotes(List<Notes> list) {
        this.list = list;
    }

    
    
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroundImg;
        ImageView bell;
        CardView cardView;
        TextView contentTextView;
        ImageView gallery;
        ImageView imageView;
        ImageView mic;
        TextView timesTamp;
        TextView titleTextView;

        public NoteViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.note_title_text_view);
            this.contentTextView = (TextView) view.findViewById(R.id.note_content_text_view);
            this.timesTamp = (TextView) view.findViewById(R.id.note_Timestamp_text_view);
            this.cardView = (CardView) view.findViewById(R.id.notelayout);
            this.backgroundImg = (ImageView) view.findViewById(R.id.backgroundImage);
            this.mic = (ImageView) view.findViewById(R.id.voice);
            this.gallery = (ImageView) view.findViewById(R.id.gallery);
            this.bell = (ImageView) view.findViewById(R.id.bellnote);
        }

        public void settheme() {
            Context context = NotesAdapter.this.context;
            Context context2 = NotesAdapter.this.context;
            SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", 0);
            NotesAdapter.this.theme = sharedPreferences.getInt("my_key", 1);
            if (NotesAdapter.this.theme == 1) {
                addButton(R.color.green);
                return;
            }
            if (NotesAdapter.this.theme == 2) {
                addButton(R.color.pink);
                return;
            }
            if (NotesAdapter.this.theme == 3) {
                addButton(R.color.blue);
                return;
            }
            if (NotesAdapter.this.theme == 4) {
                addButton(R.color.purple);
                return;
            }
            if (NotesAdapter.this.theme == 5) {
                addButton(R.color.purple);
                return;
            }
            if (NotesAdapter.this.theme == 6) {
                addButton(R.color.parrot);
                return;
            }
            if (NotesAdapter.this.theme == 7) {
                addButton(R.color.themedark7);
                return;
            }
            if (NotesAdapter.this.theme == 8) {
                addButton(R.color.themedark8);
                return;
            }
            if (NotesAdapter.this.theme == 9) {
                addButton(R.color.themedark9);
                return;
            }
            if (NotesAdapter.this.theme == 10) {
                addButton(R.color.themedark10);
                return;
            }
            if (NotesAdapter.this.theme == 11) {
                addButton(R.color.themedark11);
                return;
            }
            if (NotesAdapter.this.theme == 12) {
                addButton(R.color.themedark12);
                return;
            }
            if (NotesAdapter.this.theme == 13) {
                addButton(R.color.themedark13);
                return;
            }
            if (NotesAdapter.this.theme == 14) {
                addButton(R.color.themedark14);
                return;
            }
            if (NotesAdapter.this.theme == 15) {
                addButton(R.color.themedark15);
            } else if (NotesAdapter.this.theme == 16) {
                addButton(R.color.themedark16);
            } else if (NotesAdapter.this.theme == 17) {
                addButton(R.color.themedark17);
            }
        }

        public void addButton(int i) {
            this.mic.setImageTintList(ColorStateList.valueOf(NotesAdapter.this.context.getResources().getColor(i)));
            this.gallery.setImageTintList(ColorStateList.valueOf(NotesAdapter.this.context.getResources().getColor(i)));
            this.bell.setImageTintList(ColorStateList.valueOf(NotesAdapter.this.context.getResources().getColor(i)));
        }
    }
}
