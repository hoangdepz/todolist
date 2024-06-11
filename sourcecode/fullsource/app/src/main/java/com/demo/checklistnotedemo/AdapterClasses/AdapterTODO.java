package com.demo.checklistnotedemo.AdapterClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.checklistnotedemo.ActivityArchived;
import com.demo.checklistnotedemo.AddTaskActivity;
import com.demo.checklistnotedemo.DatabaseClasses.TaskHelper;
import com.demo.checklistnotedemo.Listeners.onTaskChanges;
import com.demo.checklistnotedemo.Models.TODOModels;
import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.ReminderClasses.ControlMusic;
import com.demo.checklistnotedemo.TrashActivity;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public abstract class AdapterTODO extends RecyclerView.Adapter<AdapterTODO.viewHolder> {
    Context context2;
    TaskHelper db;
    boolean isTodo;
    List<TODOModels> list;
    onTaskChanges taskChange;
    int theme = 0;

    private boolean toBoolean(int i) {
        return i != 0;
    }

    public abstract void onArchived(TODOModels tODOModels);

    public abstract void onDeleteButton(TODOModels tODOModels);

    public abstract void onDeleteTrash(TODOModels tODOModels);

    public AdapterTODO(TaskHelper taskHelper, Context context, List<TODOModels> list, onTaskChanges ontaskchanges, boolean z) {
        this.db = taskHelper;
        this.context2 = context;
        this.list = list;
        this.taskChange = ontaskchanges;
        this.isTodo = z;
    }

    @Override 
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_layout, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(final viewHolder viewholder, int i) {
        int color;
        int color2;
        int color3;
        int color4;
        final TODOModels tODOModels = this.list.get(i);
        viewholder.taskTxt.setText(tODOModels.getTask());
        viewholder.box.setChecked(toBoolean(tODOModels.getStatus()));
        if (tODOModels.getReminder() > 0) {
            viewholder.bell.setVisibility(View.VISIBLE);
            viewholder.reminder.setVisibility(View.VISIBLE);
            String format = new SimpleDateFormat("MMM dd,yyyy  hh:mm", Locale.getDefault()).format(Long.valueOf(tODOModels.getReminder()));
            if (viewholder.reminder != null) {
                viewholder.reminder.setText(format);
            }
        } else {
            viewholder.bell.setVisibility(View.GONE);
            viewholder.reminder.setVisibility(View.GONE);
        }
        if (tODOModels.getStatus() == 1) {
            viewholder.box.setBackgroundDrawable(this.context2.getDrawable(R.drawable.checked_icon));
            viewholder.unSelected();
            viewholder.taskTxt.setPaintFlags(viewholder.taskTxt.getPaintFlags() | 16);
            TextView textView = viewholder.taskTxt;
            color3 = ContextCompat.getColor(context2,R.color.light);
            textView.setTextColor(color3);
            TextView textView2 = viewholder.reminder;
            color4 = ContextCompat.getColor(context2,R.color.light);
            textView2.setTextColor(color4);
            viewholder.bell.setImageTintList(ColorStateList.valueOf(this.context2.getResources().getColor(R.color.light)));
        } else if (tODOModels.getStatus() == 0) {
            viewholder.taskTxt.setPaintFlags(viewholder.taskTxt.getPaintFlags() & (-17));
            TextView textView3 = viewholder.taskTxt;
            color = ContextCompat.getColor(context2,R.color.black);
            textView3.setTextColor(color);
            viewholder.box.setBackgroundDrawable(this.context2.getDrawable(R.drawable.uncheck_icon));
            viewholder.taskTxt.setPaintFlags(viewholder.taskTxt.getPaintFlags() & (-17));
            TextView textView4 = viewholder.taskTxt;
            color2 = ContextCompat.getColor(context2,R.color.black);
            textView4.setTextColor(color2);
            viewholder.unSelected();
        }
        viewholder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                int color5;
                int color6;
                int color7;
                if (viewholder.box.isPressed()) {
                    if (z) {
                        ControlMusic.getInstance(AdapterTODO.this.context2).playClickSound();
                        viewholder.box.animate().scaleX(0.6f).scaleY(0.6f).setDuration(300L).withEndAction(new Runnable() { 
                            @Override 
                            public void run() {
                                int color8;
                                int color9;
                                viewholder.box.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300L);
                                AdapterTODO.this.db.updateStatus(tODOModels.getId(), 1L);
                                viewholder.box.setBackgroundDrawable(AdapterTODO.this.context2.getDrawable(R.drawable.checked_icon));
                                viewholder.unSelected();
                                viewholder.taskTxt.setPaintFlags(viewholder.taskTxt.getPaintFlags() | 16);
                                TextView textView5 = viewholder.taskTxt;
                                color8 = ContextCompat.getColor(context2,R.color.light);
                                textView5.setTextColor(color8);
                                TextView textView6 = viewholder.reminder;
                                color9 = ContextCompat.getColor(context2,R.color.light);
                                textView6.setTextColor(color9);
                                viewholder.bell.setImageTintList(ColorStateList.valueOf(AdapterTODO.this.context2.getResources().getColor(R.color.light)));
                                if (AdapterTODO.this.taskChange != null) {
                                    AdapterTODO.this.taskChange.onTaskChange();
                                }
                            }
                        });
                        return;
                    }
                    AdapterTODO.this.db.updateStatus(tODOModels.getId(), 0L);
                    viewholder.box.setBackgroundDrawable(AdapterTODO.this.context2.getDrawable(R.drawable.uncheck_icon));
                    viewholder.taskTxt.setPaintFlags(viewholder.taskTxt.getPaintFlags() & (-17));
                    TextView textView5 = viewholder.taskTxt;
                    color5 = ContextCompat.getColor(context2,R.color.black);
                    textView5.setTextColor(color5);
                    ImageView imageView = viewholder.bell;
                    color6 =ContextCompat.getColor(viewholder.bell.getContext(), R.color.light);
                    imageView.setImageTintList(ColorStateList.valueOf(color6));
                    TextView textView6 = viewholder.reminder;
                    color7 = ContextCompat.getColor(viewholder.bell.getContext(),R.color.light);
                    textView6.setTextColor(color7);
                    viewholder.unSelected();
                    if (AdapterTODO.this.taskChange != null) {
                        AdapterTODO.this.taskChange.onTaskChange();
                    }
                }
            }
        });
        final String valueOf = String.valueOf(tODOModels.getId());
        if (this.isTodo) {
            viewholder.itemView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Intent intent = new Intent(AdapterTODO.this.context2, (Class<?>) AddTaskActivity.class);
                    intent.putExtra("docId", valueOf);
                    AdapterTODO.this.context2.startActivity(intent);
                }
            });
            viewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() { 
                @Override 
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(AdapterTODO.this.context2).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            AdapterTODO.this.onDeleteButton(tODOModels);
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
        viewholder.box.setEnabled(false);
        viewholder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Toast.makeText(AdapterTODO.this.context2, AdapterTODO.this.context2.getResources().getString(R.string.please_long_click_to_restore), Toast.LENGTH_SHORT).show();
            }
        });
        if (this.context2 instanceof TrashActivity) {
            viewholder.itemView.setOnLongClickListener(new AnonymousClass5(tODOModels));
        }
        if (this.context2 instanceof ActivityArchived) {
            viewholder.itemView.setOnLongClickListener(new AnonymousClass6(tODOModels));
        }
    }

    
    
    
    public class AnonymousClass5 implements View.OnLongClickListener {
        final  TODOModels val$model;

        AnonymousClass5(TODOModels tODOModels) {
            this.val$model = tODOModels;
        }

        @Override 
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdapterTODO.this.context2);
            View inflate = LayoutInflater.from(AdapterTODO.this.context2).inflate(R.layout.custom_alert_dialog, (ViewGroup) null);
            builder.setView(inflate);
            final AlertDialog create = builder.create();
            TextView textView = (TextView) inflate.findViewById(R.id.restoreAD);
            TextView textView2 = (TextView) inflate.findViewById(R.id.deleteAD);
            textView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    AdapterTODO.this.onDeleteButton(AnonymousClass5.this.val$model);
                    create.dismiss();
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    new AlertDialog.Builder(AdapterTODO.this.context2).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (AdapterTODO.this.context2 instanceof TrashActivity) {
                                AdapterTODO.this.onDeleteTrash(AnonymousClass5.this.val$model);
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

    
    
    
    public class AnonymousClass6 implements View.OnLongClickListener {
        final  TODOModels val$model;

        AnonymousClass6(TODOModels tODOModels) {
            this.val$model = tODOModels;
        }

        @Override 
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdapterTODO.this.context2);
            View inflate = LayoutInflater.from(AdapterTODO.this.context2).inflate(R.layout.custom_alert_dialog, (ViewGroup) null);
            builder.setView(inflate);
            final AlertDialog create = builder.create();
            TextView textView = (TextView) inflate.findViewById(R.id.restoreAD);
            TextView textView2 = (TextView) inflate.findViewById(R.id.deleteAD);
            textView.setText(R.string.unarchived);
            textView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    AdapterTODO.this.onDeleteButton(AnonymousClass6.this.val$model);
                    create.dismiss();
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    new AlertDialog.Builder(AdapterTODO.this.context2).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (AdapterTODO.this.context2 instanceof ActivityArchived) {
                                AdapterTODO.this.onDeleteTrash(AnonymousClass6.this.val$model);
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

    public void setList(List<TODOModels> list) {
        this.list = list;
    }

    @Override 
    public int getItemCount() {
        List<TODOModels> list = this.list;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setNotes(List<TODOModels> list) {
        this.list = list;
    }

    
    public static class viewHolder extends RecyclerView.ViewHolder {
        ImageView arrow;
        ImageView bell;
        ToggleButton box;
        CardView cardView;
        TextView reminder;
        TextView taskTxt;

        public viewHolder(View view) {
            super(view);
            this.box = (ToggleButton) view.findViewById(R.id.box);
            this.taskTxt = (TextView) view.findViewById(R.id.taskTxt);
            this.bell = (ImageView) view.findViewById(R.id.belltodo2);
            this.reminder = (TextView) view.findViewById(R.id.reminderDate2);
            this.cardView = (CardView) view.findViewById(R.id.cardView);
        }

        public void unSelected() {
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
            int color11;
            int color12;
            int color13;
            int color14;
            int color15;
            int color16;
            int color17;
            int color18;
            int color19;
            int color20;
            int color21;
            int color22;
            int color23;
            int color24;
            int color25;
            int color26;
            int color27;
            int color28;
            int color29;
            int color30;
            int color31;
            int color32;
            int color33;
            int color34;
            int color35;
            int color36;
            int i = this.box.getContext().getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
            if (i == 1) {
                TextView textView = this.taskTxt;
                color35 =ContextCompat.getColor(this.bell.getContext(),R.color.green);
                textView.setTextColor(color35);
                ToggleButton toggleButton = this.box;
                color36 = ContextCompat.getColor(this.bell.getContext(),R.color.green);
                toggleButton.setBackgroundTintList(ColorStateList.valueOf(color36));
            } else if (i == 2) {
                ToggleButton toggleButton2 = this.box;
                color31 = ContextCompat.getColor(this.bell.getContext(),R.color.pink);
                toggleButton2.setBackgroundTintList(ColorStateList.valueOf(color31));
                TextView textView2 = this.taskTxt;
                color32 = ContextCompat.getColor(this.bell.getContext(),R.color.pink);
                textView2.setTextColor(color32);
            } else if (i == 3) {
                ToggleButton toggleButton3 = this.box;
                color29 = ContextCompat.getColor(this.bell.getContext(),R.color.blue);
                toggleButton3.setBackgroundTintList(ColorStateList.valueOf(color29));
                TextView textView3 = this.taskTxt;
                color30 = ContextCompat.getColor(this.bell.getContext(),R.color.blue);
                textView3.setTextColor(color30);
            } else if (i == 4) {
                ToggleButton toggleButton4 = this.box;
                color27 = ContextCompat.getColor(this.bell.getContext(),R.color.purple);
                toggleButton4.setBackgroundTintList(ColorStateList.valueOf(color27));
                TextView textView4 = this.taskTxt;
                color28 = ContextCompat.getColor(this.bell.getContext(),R.color.purple);
                textView4.setTextColor(color28);
            } else if (i == 5) {
                ToggleButton toggleButton5 = this.box;
                color25 = ContextCompat.getColor(this.bell.getContext(),R.color.purple);
                toggleButton5.setBackgroundTintList(ColorStateList.valueOf(color25));
                TextView textView5 = this.taskTxt;
                color26 = ContextCompat.getColor(this.bell.getContext(),R.color.purple);
                textView5.setTextColor(color26);
            } else if (i == 6) {
                ToggleButton toggleButton6 = this.box;
                color23 = ContextCompat.getColor(this.bell.getContext(),R.color.parrot);
                toggleButton6.setBackgroundTintList(ColorStateList.valueOf(color23));
                TextView textView6 = this.taskTxt;
                color24  = ContextCompat.getColor(this.bell.getContext(),R.color.parrot);
                textView6.setTextColor(color24);
            } else if (i == 7) {
                ToggleButton toggleButton7 = this.box;
                color21 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark7);
                toggleButton7.setBackgroundTintList(ColorStateList.valueOf(color21));
                TextView textView7 = this.taskTxt;
                color22  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark7);
                textView7.setTextColor(color22);
            } else if (i == 8) {
                ToggleButton toggleButton8 = this.box;
                color19 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark8);
                toggleButton8.setBackgroundTintList(ColorStateList.valueOf(color19));
                TextView textView8 = this.taskTxt;
                color20  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark8);
                textView8.setTextColor(color20);
            } else if (i == 9) {
                ToggleButton toggleButton9 = this.box;
                color17 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark9);
                toggleButton9.setBackgroundTintList(ColorStateList.valueOf(color17));
                TextView textView9 = this.taskTxt;
                color18  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark9);
                textView9.setTextColor(color18);
            } else if (i == 10) {
                ToggleButton toggleButton10 = this.box;
                color15 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark10);
                toggleButton10.setBackgroundTintList(ColorStateList.valueOf(color15));
                TextView textView10 = this.taskTxt;
                color16  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark10);
                textView10.setTextColor(color16);
            } else if (i == 11) {
                ToggleButton toggleButton11 = this.box;
                color13 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark11);
                toggleButton11.setBackgroundTintList(ColorStateList.valueOf(color13));
                TextView textView11 = this.taskTxt;
                color14  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark11);
                textView11.setTextColor(color14);
            } else if (i == 12) {
                ToggleButton toggleButton12 = this.box;
                color11 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark12);
                toggleButton12.setBackgroundTintList(ColorStateList.valueOf(color11));
                TextView textView12 = this.taskTxt;
                color12  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark12);
                textView12.setTextColor(color12);
            } else if (i == 13) {
                ToggleButton toggleButton13 = this.box;
                color9 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark13);
                toggleButton13.setBackgroundTintList(ColorStateList.valueOf(color9));
                TextView textView13 = this.taskTxt;
                color10  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark13);
                textView13.setTextColor(color10);
            } else if (i == 14) {
                ToggleButton toggleButton14 = this.box;
                color7 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark14);
                toggleButton14.setBackgroundTintList(ColorStateList.valueOf(color7));
                TextView textView14 = this.taskTxt;
                color8  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark14);
                textView14.setTextColor(color8);
            } else if (i == 15) {
                ToggleButton toggleButton15 = this.box;
                color5 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark15);
                toggleButton15.setBackgroundTintList(ColorStateList.valueOf(color5));
                TextView textView15 = this.taskTxt;
                color6  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark15);
                textView15.setTextColor(color6);
            } else if (i == 16) {
                ToggleButton toggleButton16 = this.box;
                color3 = ContextCompat.getColor(this.bell.getContext(),R.color.themedark16);
                toggleButton16.setBackgroundTintList(ColorStateList.valueOf(color3));
                TextView textView16 = this.taskTxt;
                color4  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark16);
                textView16.setTextColor(color4);
            } else if (i == 17) {
                ToggleButton toggleButton17 = this.box;
                color = ContextCompat.getColor(this.bell.getContext(),R.color.themedark17);
                toggleButton17.setBackgroundTintList(ColorStateList.valueOf(color));
                TextView textView17 = this.taskTxt;
                color2  = ContextCompat.getColor(this.bell.getContext(),R.color.themedark17);
                textView17.setTextColor(color2);
            }
            if (i == 5) {
                this.cardView.setCardBackgroundColor(this.bell.getContext().getResources().getColor(R.color.light_black));
                CardView cardView = this.cardView;
                color34  = ContextCompat.getColor(this.bell.getContext(),R.color.light_black);
                cardView.setBackgroundTintList(ColorStateList.valueOf(color34));
                return;
            }
            this.cardView.setCardBackgroundColor(this.bell.getContext().getResources().getColor(R.color.white));
            CardView cardView2 = this.cardView;
            color33  = ContextCompat.getColor(this.bell.getContext(),R.color.white);
            cardView2.setBackgroundTintList(ColorStateList.valueOf(color33));
        }
    }
}
