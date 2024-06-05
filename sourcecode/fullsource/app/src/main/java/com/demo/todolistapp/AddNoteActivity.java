package com.demo.todolistapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.aghajari.emojiview.view.AXEmojiEditText;
import com.aghajari.emojiview.view.AXEmojiPopup;
import com.aghajari.emojiview.view.AXEmojiView;
import com.demo.todolistapp.R;
import com.google.android.material.tabs.TabLayout;
import com.demo.todolistapp.AdapterClasses.AdapterColor;
import com.demo.todolistapp.AdapterClasses.AdapterFont;
import com.demo.todolistapp.AdapterClasses.AdapterSnackPager;
import com.demo.todolistapp.AdapterClasses.Recording_ImageAdapter;
import com.demo.todolistapp.AudioRecorder.Timer;
import com.demo.todolistapp.AudioRecorder.WaveformView;
import com.demo.todolistapp.DataHolderClasses.HolderDialog;
import com.demo.todolistapp.DataHolderClasses.ManagerData;
import com.demo.todolistapp.DatabaseClasses.MyHelperDb;
import com.demo.todolistapp.DatabaseClasses.MyHelperImage;
import com.demo.todolistapp.Listeners.ColorClickListeners;
import com.demo.todolistapp.Listeners.ColorsChange;
import com.demo.todolistapp.Listeners.EditTextClickListeners;
import com.demo.todolistapp.Listeners.FontClickListeners;
import com.demo.todolistapp.Lock.LockHolder;
import com.demo.todolistapp.Lock.PatternDialog;
import com.demo.todolistapp.Lock.SharedPrefrence;
import com.demo.todolistapp.Models.ImageModels;
import com.demo.todolistapp.Models.ListItems;
import com.demo.todolistapp.Models.Notes;
import com.demo.todolistapp.ReminderClasses.ControlMusic;
import com.demo.todolistapp.ReminderClasses.RemindersBroadcastReceiver;
import com.demo.todolistapp.databinding.ActivityAddNoteBinding;
import com.demo.todolistapp.paramsClasses.Param;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class AddNoteActivity extends AppCompatActivity implements EditTextClickListeners, Timer.OnTimeTickListener {
    private static final int CAMERA_REQUEST = 2;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREFS_NAME_EMOJI = "MyPrefsFileEmoji";
    private static final String RECORDING_PERMISSION_DENIED_KEY = "recordingPermissionDenied";
    private static final String RECORDING_PERMISSION_DENIED_KEY_EMOJI = "recordingPermissionDeniedEmoji";
    private static final int RECORD_AUDIO_PERMISSION_CODE = 141;
    private static final int REQUEST_BOTH_PERMISSIONS_CODE = 212;
    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int center = 1;
    private static final int end = 8388613;
    private static final int start = 8388611;
    private AXEmojiEditText activeEditText;
    String base64BackgroundImage;
    ActivityAddNoteBinding binding;
    RelativeLayout c;
    private Calendar calendar1;
    List<String> categoryTitles;
    ImageView centerGravity;
    String color;
    String content;
    MyHelperDb db;
    MyHelperImage dbImg;
    String docId;
    RelativeLayout e;
    AXEmojiPopup emojiPopup;
    ImageView endGravity;
    String folder;
    String fontName;
    int gravity;
    String id;
    Recording_ImageAdapter imageAdapter;
    List<ImageModels> imageModels;
    Uri imageUri;
    long l;
    private MediaRecorder mediaRecorder;
    long noteId;
    Timer recorderTimer;
    private String recordingFilePath;
    RecyclerView recyclerView;
    RecyclerView recyclerViewFont;
    RelativeLayout s;
    int select2;
    ImageView startGravity;
    TabLayout tabLayout;
    int textColor2;
    int theme;
    ImageView tick;
    long timesTamp;
    String title;
    WaveformView waveformView;
    private int selectedGravity = -1;
    int selectedColor = 0;
    private boolean isRecording = false;
    boolean isEditMode = false;
    private boolean isPaused = false;
    boolean isTrue = false;
    private long recordingStartTime = 0;
    private long pausedTime = 0;
    long reminderDateInMillis = 0;
    private Handler handler = new Handler();
    private Calendar calendar = Calendar.getInstance();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    List<ImageModels> list = new ArrayList();
    List<ListItems> listItems = new ArrayList();
    private boolean dateChanged = false;
    long calenderTime = 0;
    View.OnFocusChangeListener editTextFocusChangeListener = null;
    boolean showDialog = false;
    int select = 0;
    int textColor = -1;
    String selectedCategory = null;
    String defualtbackground = "Defualt";
    int isDark = -1;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    static  long access$1214(AddNoteActivity addNoteActivity, long j) {
        long j2 = addNoteActivity.recordingStartTime + j;
        addNoteActivity.recordingStartTime = j2;
        return j2;
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityAddNoteBinding inflate = ActivityAddNoteBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);

        this.waveformView = (WaveformView) findViewById(R.id.waveformView);
        ControlMusic.getInstance(this).stopMusic();
        this.recorderTimer = new Timer(this);
        this.calendar1 = Calendar.getInstance();
        this.theme = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            declaredField.setAccessible(true);
            declaredField.set(null, 209715200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        settheme();
        this.db = new MyHelperDb(this);
        this.binding.titleedittxt.requestFocus();
        this.activeEditText = this.binding.titleedittxt;
        this.dbImg = new MyHelperImage(this);
        this.binding.titleedittxt.setMaxLines(Integer.MAX_VALUE);
        this.binding.contentedittxt.setMaxLines(Integer.MAX_VALUE);
        this.title = getIntent().getStringExtra(Param.KEY_TITLE);
        this.content = getIntent().getStringExtra(Param.KEY_CONTENT);
        this.docId = getIntent().getStringExtra("docId");
        this.color = getIntent().getStringExtra("color");
        this.textColor2 = getIntent().getIntExtra("textColor", 0);
        String stringExtra = getIntent().getStringExtra("font");
        this.l = getIntent().getLongExtra("time", 0L);
        long longExtra = getIntent().getLongExtra("date", 0L);
        this.calenderTime = longExtra;
        if (longExtra != 0) {
            this.calendar1.setTimeInMillis(longExtra);
            updateButtonDate();
        }
        String str = this.docId;
        if (str != null && !str.isEmpty()) {
            this.isEditMode = true;
            this.binding.contentedittxt.requestFocus();
            this.activeEditText = this.binding.contentedittxt;
            this.textColor = this.textColor2;
            this.binding.contentedittxt.post(new Runnable() { 
                @Override 
                public void run() {
                    AddNoteActivity.this.binding.contentedittxt.setSelection(AddNoteActivity.this.binding.contentedittxt.getText().length());
                    AddNoteActivity.this.binding.contentedittxt.requestFocus();
                }
            });
            this.binding.titleedittxt.setText(this.title);
            this.binding.contentedittxt.setText(this.content);
            Notes noteById = this.db.getNoteById(Long.parseLong(this.docId));
            Calendar calendar = Calendar.getInstance();
            this.calendar1 = calendar;
            calendar.setTimeInMillis(this.l);
            updateButtonDate();
            fetchData(Long.parseLong(this.docId));
            this.select2 = noteById.getGravity();
            if (noteById.getGravity() == 0) {
                this.binding.titleedittxt.setGravity(8388611);
                this.binding.contentedittxt.setGravity(8388611);
                this.gravity = 8388611;
                if (this.imageAdapter != null) {
                    int gravity = noteById.getGravity();
                    this.selectedGravity = gravity;
                    this.imageAdapter.gravity(gravity);
                }
            } else if (noteById.getGravity() == 1) {
                this.binding.titleedittxt.setGravity(1);
                this.binding.contentedittxt.setGravity(1);
                this.gravity = 1;
                if (this.imageAdapter != null) {
                    int gravity2 = noteById.getGravity();
                    this.selectedGravity = gravity2;
                    this.imageAdapter.gravity(gravity2);
                }
            } else if (noteById.getGravity() == 2) {
                this.binding.titleedittxt.setGravity(8388613);
                this.binding.contentedittxt.setGravity(8388613);
                this.gravity = 8388613;
                if (this.imageAdapter != null) {
                    int gravity3 = noteById.getGravity();
                    this.selectedGravity = gravity3;
                    this.imageAdapter.gravity(gravity3);
                }
            }
            noteById.setFontName(stringExtra);
            if (noteById.getFontName() != null) {
                onFontClick(noteById);
            }
            int i = this.textColor2;
            if (i != 0 && i != -1) {
                onColorChange(i);
                if (noteById.getColor() == null) {
                    this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                } else {
                    this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                }
            } else if (this.theme == 5) {
                if (noteById.getColor() == null) {
                    onColorChange(-1);
                    this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                } else {
                    onColorChange(ViewCompat.MEASURED_STATE_MASK);
                    this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                }
            }
        } else {
            this.binding.titleedittxt.requestFocus();
            this.activeEditText = this.binding.titleedittxt;
        }
        String stringExtra2 = getIntent().getStringExtra("reminderId");
        this.id = stringExtra2;
        if (stringExtra2 != null && !stringExtra2.isEmpty()) {
            this.isEditMode = true;
            handleNotificationClick();
        }
        this.binding.saveNoteBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.saveNote();
                AddNoteActivity.this.closeKeyboard();
                AddNoteActivity.this.showDialog = true;
            }
        });
        this.binding.backhome.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.backhomeAlertDialog();
            }
        });
        this.binding.paint.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddNoteActivity.this.emojiPopup != null && AddNoteActivity.this.emojiPopup.isShowing()) {
                    AddNoteActivity.this.emojiPopup.dismiss();
                    AddNoteActivity.this.closeKeyboard();
                } else {
                    AddNoteActivity.this.showSnackBar();
                    AddNoteActivity.this.closeKeyboard();
                }
            }
        });
        this.binding.fonts.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddNoteActivity.this.emojiPopup == null || !AddNoteActivity.this.emojiPopup.isShowing()) {
                    AddNoteActivity.this.showFontSnackBar();
                    AddNoteActivity.this.closeKeyboard();
                } else {
                    AddNoteActivity.this.emojiPopup.dismiss();
                    AddNoteActivity.this.closeKeyboard();
                }
            }
        });
        this.binding.image.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                LockHolder.getInstance().setboolean(false);
                AddNoteActivity.this.ProfileSnackBar();
                AddNoteActivity.this.closeKeyboard();
            }
        });
        this.binding.bell.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddNoteActivity.this.areNotificationsEnabled()) {
                    AddNoteActivity.this.showDateAndTimePicker();
                    AddNoteActivity.this.closeKeyboard();
                } else {
                    AddNoteActivity.this.showPermissionDialog();
                }
            }
        });
        this.binding.mic.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.binding.transparentLayout.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= 31) {
                    AddNoteActivity.this.binding.relativeLayout.setAllowClickWhenDisabled(false);
                }
                AddNoteActivity.this.binding.relativeLayout.setEnabled(false);
                AddNoteActivity.this.disableButtons();
                AddNoteActivity.this.closeKeyboard();
            }
        });
        this.binding.recordst.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (!AddNoteActivity.this.isRecording) {
                    AddNoteActivity.this.startRecording();
                } else if (!AddNoteActivity.this.isPaused) {
                    AddNoteActivity.this.pauseRecording();
                } else {
                    AddNoteActivity.this.resumeRecording();
                }
            }
        });
        this.binding.closeButton.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddNoteActivity.this.isRecording) {
                    AddNoteActivity.this.stopRecording();
                    AddNoteActivity.this.waveformView.clear();
                    AddNoteActivity.this.recordingStartTime = 0L;
                    AddNoteActivity.this.binding.tvTime.setText("00:00:00");
                    AddNoteActivity.this.binding.tick.setVisibility(View.GONE);
                    AddNoteActivity.this.binding.closeButton.setVisibility(View.GONE);
                    AddNoteActivity.this.binding.recordst.setImageResource(R.drawable.recording_start);
                    AddNoteActivity.this.binding.tapstartTextView.setVisibility(View.VISIBLE);
                    AddNoteActivity.this.binding.transparentLayout.setVisibility(View.GONE);
                    AddNoteActivity.this.enableButtons();
                    AddNoteActivity addNoteActivity = AddNoteActivity.this;
                    Toast.makeText(addNoteActivity, addNoteActivity.getResources().getString(R.string.stop_recording), Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.binding.closerec.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.stopRecording();
                AddNoteActivity.this.recordingStartTime = 0L;
                AddNoteActivity.this.binding.tvTime.setText("00:00:00");
                AddNoteActivity.this.waveformView.clear();
                AddNoteActivity.this.binding.tick.setVisibility(View.GONE);
                AddNoteActivity.this.binding.closeButton.setVisibility(View.GONE);
                AddNoteActivity.this.binding.recordst.setImageResource(R.drawable.recording_start);
                AddNoteActivity.this.binding.tapstartTextView.setVisibility(View.VISIBLE);
                AddNoteActivity.this.binding.transparentLayout.setVisibility(View.GONE);
                AddNoteActivity.this.enableButtons();
            }
        });
        this.binding.tick.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddNoteActivity.this.isRecording) {
                    AddNoteActivity.this.saveRecording();
                    AddNoteActivity.this.stopRecording();
                    AddNoteActivity.this.recordingStartTime = 0L;
                    AddNoteActivity.this.binding.tvTime.setText("00:00:00");
                    AddNoteActivity.this.waveformView.clear();
                    AddNoteActivity.this.binding.tick.setVisibility(View.GONE);
                    AddNoteActivity.this.binding.closeButton.setVisibility(View.GONE);
                    AddNoteActivity.this.binding.recordst.setImageResource(R.drawable.recording_start);
                    AddNoteActivity.this.binding.tapstartTextView.setVisibility(View.VISIBLE);
                    AddNoteActivity.this.binding.transparentLayout.setVisibility(View.GONE);
                    AddNoteActivity.this.recordingFilePath = null;
                    AddNoteActivity.this.enableButtons();
                }
            }
        });
        focusView();
        this.binding.emoji.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Log.d("EmojiPopup", "Emoji Button Clicked");
                if (AddNoteActivity.this.activeEditText != null) {
                    if (!AddNoteActivity.this.isDeniedEmoji()) {
                        AddNoteActivity.this.closeKeyboard();
                        AddNoteActivity addNoteActivity = AddNoteActivity.this;
                        addNoteActivity.toggleEmojiPopupForEditText(addNoteActivity.activeEditText);
                        AddNoteActivity.this.markDeniedEmoji();
                    } else {
                        AddNoteActivity addNoteActivity2 = AddNoteActivity.this;
                        addNoteActivity2.toggleEmojiPopupForEditText(addNoteActivity2.activeEditText);
                    }
                    Log.d("EmojiPopup", "Active EditText: " + AddNoteActivity.this.activeEditText.getId());
                    return;
                }
                Log.d("EmojiPopup", "Active EditText is null");
                AddNoteActivity addNoteActivity3 = AddNoteActivity.this;
                Toast.makeText(addNoteActivity3, addNoteActivity3.getResources().getString(R.string.please_choose_position_to_insert_emoji), Toast.LENGTH_SHORT).show();
            }
        });
        this.binding.datePickerButton.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.showDatePicker();
                AddNoteActivity.this.closeKeyboard();
            }
        });
        String str2 = this.color;
        if (str2 != null) {
            getWindow().getDecorView().setBackground(new BitmapDrawable(getResources(), convertBase64ToBitmap(str2)));
        } else if (this.theme == 5) {
            getWindow().getDecorView().setBackgroundResource(R.drawable.black);
        } else {
            getWindow().getDecorView().setBackgroundResource(R.drawable.grey);
        }
        updateButtonDate();
    }

    
    public boolean areNotificationsEnabled() {
        return NotificationManagerCompat.from(this).areNotificationsEnabled();
    }

    
    public void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.enable_notifications);
        builder.setMessage(R.string.notifications_are_currently_disabled_do_you_want_to_enable_them);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                LockHolder.getInstance().setboolean(false);
                Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", AddNoteActivity.this.getPackageName());
                AddNoteActivity.this.startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    
    public void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void focusView() {
        this.editTextFocusChangeListener = new View.OnFocusChangeListener() { 
            @Override 
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    if (AddNoteActivity.this.emojiPopup != null && AddNoteActivity.this.emojiPopup.isShowing()) {
                        AddNoteActivity.this.emojiPopup.dismiss();
                    }
                    AddNoteActivity.this.activeEditText = (AXEmojiEditText) view;
                }
            }
        };
        this.binding.titleedittxt.setOnFocusChangeListener(this.editTextFocusChangeListener);
        this.binding.contentedittxt.setOnFocusChangeListener(this.editTextFocusChangeListener);
    }

    public void backhomeAlertDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.exit_confirmation).setMessage(R.string.are_you_sure_you_want_to_exist).setIcon(R.drawable.baseline_warning_24).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                AddNoteActivity.this.finish();
                LockHolder.getInstance().setboolean(false);
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public void onDismiss(DialogInterface dialogInterface) {
                LockHolder.getInstance().setboolean(false);
            }
        }).show();
    }

    private void updateButtonDate() {
        this.binding.monthyear.setText(new SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(this.calendar1.getTime()));
        this.binding.date.setText(new SimpleDateFormat("dd", Locale.getDefault()).format(this.calendar1.getTime()));
    }

    
    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                AddNoteActivity.this.m350x6015bab5(datePicker, i, i2, i3);
            }
        }, this.calendar1.get(1), this.calendar1.get(2), this.calendar1.get(5));
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public final void onDismiss(DialogInterface dialogInterface) {
                AddNoteActivity.this.m351x7a313954(dialogInterface);
            }
        });
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        datePickerDialog.show();
    }

    
    
    public  void m350x6015bab5(DatePicker datePicker, int i, int i2, int i3) {
        this.calendar1.set(1, i);
        this.calendar1.set(2, i2);
        this.calendar1.set(5, i3);
        this.dateChanged = true;
        this.timesTamp = this.calendar1.getTimeInMillis();
        updateButtonDate();
    }

    
    
    public  void m351x7a313954(DialogInterface dialogInterface) {
        if (this.dateChanged) {
            return;
        }
        this.dateChanged = false;
    }

    
    public Bitmap convertBase64ToBitmap(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    @Override 
    public void onEditTextClick(AXEmojiEditText aXEmojiEditText) {
        aXEmojiEditText.setOnFocusChangeListener(this.editTextFocusChangeListener);
        this.activeEditText = aXEmojiEditText;
        AXEmojiPopup aXEmojiPopup = this.emojiPopup;
        if (aXEmojiPopup != null && aXEmojiPopup.isShowing()) {
            this.emojiPopup.dismiss();
        }
        focusView();
    }

    
    public void toggleEmojiPopupForEditText(AXEmojiEditText aXEmojiEditText) {
        AXEmojiPopup aXEmojiPopup = this.emojiPopup;
        if (aXEmojiPopup != null && aXEmojiPopup.isShowing()) {
            this.emojiPopup.dismiss();
            this.emojiPopup = null;
            return;
        }
        focusView();
        AXEmojiView aXEmojiView = new AXEmojiView(this);
        aXEmojiView.setEditText(aXEmojiEditText);
        AXEmojiPopup aXEmojiPopup2 = new AXEmojiPopup(aXEmojiView);
        this.emojiPopup = aXEmojiPopup2;
        aXEmojiPopup2.toggle();
    }

    private void RecyclerViewRecording_Img(final List<ListItems> list) {
        int i = this.selectedGravity;
        if (i == 0) {
            this.gravity = 8388611;
        } else if (i == 1) {
            this.gravity = 1;
        } else if (i == 2) {
            this.gravity = 8388613;
        }
        this.imageAdapter = new Recording_ImageAdapter(list, this, this, this.gravity) { 
            @Override 
            public void onDeleteButton(ImageModels imageModels) {
                AddNoteActivity.this.dbImg.deleteImageById(imageModels.getId());
                if (list.indexOf(imageModels) != -1) {
                    list.remove(imageModels);
                    AddNoteActivity.this.list.remove(imageModels);
                    AddNoteActivity.this.imageAdapter.notifyDataSetChanged();
                }
            }

            @Override 
            public void DelteRecording(ImageModels imageModels) {
                AddNoteActivity.this.dbImg.deleteImageById(imageModels.getId());
                if (list.indexOf(imageModels) != -1) {
                    list.remove(imageModels);
                    AddNoteActivity.this.list.remove(imageModels);
                    AddNoteActivity.this.imageAdapter.notifyDataSetChanged();
                }
            }
        };
        this.binding.recyclerImageView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.binding.recyclerImageView.setAdapter(this.imageAdapter);
        this.imageAdapter.notifyDataSetChanged();
    }

    
    @Override 
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    
    public void disableButtons() {
        LinearLayout linearLayout = this.binding.bottomnavigation;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setEnabled(false);
        }
        RelativeLayout relativeLayout = this.binding.relativeTitleBarLayout;
        for (int i2 = 0; i2 < relativeLayout.getChildCount(); i2++) {
            relativeLayout.getChildAt(i2).setEnabled(false);
        }
        this.binding.contentedittxt.setEnabled(false);
        this.binding.titleedittxt.setEnabled(false);
    }

    
    public void enableButtons() {
        LinearLayout linearLayout = this.binding.bottomnavigation;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setEnabled(true);
        }
        RelativeLayout relativeLayout = this.binding.relativeTitleBarLayout;
        for (int i2 = 0; i2 < relativeLayout.getChildCount(); i2++) {
            relativeLayout.getChildAt(i2).setEnabled(true);
        }
        this.binding.contentedittxt.setEnabled(true);
        this.binding.titleedittxt.setEnabled(true);
    }

    private String formatTime(int i) {
        int i2 = i / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf((i2 % 360) / 60), Integer.valueOf(i2 % 60));
    }

    
    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        LockHolder.getInstance().setboolean(false);
        if (i == 1 && i2 == -1 && intent != null) {
            Uri data = intent.getData();
            this.imageUri = data;
            saveUrl(data.toString());
            return;
        }
        if (i != 2 || i2 != -1 || intent == null || intent == null || (bitmap = (Bitmap) intent.getExtras().get("data")) == null) {
            return;
        }
        String convertBitmapToBase64 = convertBitmapToBase64(bitmap);
        if (convertBitmapToBase64 != null) {
            ImageModels imageModels = new ImageModels();
            imageModels.setFontName(this.fontName);
            imageModels.setImage(convertBitmapToBase64);
            this.list.add(imageModels);
            this.listItems.add(imageModels);
            RecyclerViewRecording_Img(this.listItems);
            return;
        }
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }

    
    public void startRecording() {
        this.binding.recordst.setImageResource(R.drawable.pause);
        Log.d("RecordingStatus", "Start Recording called");
        if (checkRecordingAndStoragePermissions()) {
            Toast.makeText(this, getString(R.string.start), Toast.LENGTH_SHORT).show();
            if (!this.isRecording) {
                this.executorService.execute(new Runnable() { 
                    @Override 
                    public void run() {
                        AddNoteActivity.this.mediaRecorder = new MediaRecorder();
                        AddNoteActivity.this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        AddNoteActivity.this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        AddNoteActivity addNoteActivity = AddNoteActivity.this;
                        addNoteActivity.recordingFilePath = addNoteActivity.getRecordingFilePath();
                        AddNoteActivity.this.mediaRecorder.setOutputFile(AddNoteActivity.this.recordingFilePath);
                        AddNoteActivity.this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        try {
                            AddNoteActivity.this.mediaRecorder.prepare();
                            AddNoteActivity.this.mediaRecorder.start();
                            AddNoteActivity.this.isRecording = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("ERROR", "The error is :" + e.getMessage());
                        }
                        AddNoteActivity.this.runOnUiThread(new Runnable() { 
                            @Override 
                            public void run() {
                                AddNoteActivity.this.binding.tick.setVisibility(View.VISIBLE);
                                AddNoteActivity.this.binding.closeButton.setVisibility(View.VISIBLE);
                                AddNoteActivity.this.binding.tapstartTextView.setVisibility(View.GONE);
                                AddNoteActivity.this.recordingStartTime = System.currentTimeMillis();
                                AddNoteActivity.this.recorderTimer.start();
                            }
                        });
                    }
                });
                return;
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (isRecordingPermissionDenied()) {
            showPermissionDeniedSettings();
        } else {
            requestRecordingAndStoragePermissions();
            markRecordingPermissionDenied();
        }
        this.binding.recordst.setImageResource(R.drawable.recording_start);
    }

    private boolean isRecordingPermissionDenied() {
        return getSharedPreferences(PREFS_NAME, 0).getBoolean(RECORDING_PERMISSION_DENIED_KEY, false);
    }

    private void markRecordingPermissionDenied() {
        SharedPreferences.Editor edit = getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean(RECORDING_PERMISSION_DENIED_KEY, true);
        edit.apply();
    }

    
    public boolean isDeniedEmoji() {
        return getSharedPreferences(PREFS_NAME_EMOJI, 0).getBoolean(RECORDING_PERMISSION_DENIED_KEY_EMOJI, false);
    }

    
    public void markDeniedEmoji() {
        SharedPreferences.Editor edit = getSharedPreferences(PREFS_NAME_EMOJI, 0).edit();
        edit.putBoolean(RECORDING_PERMISSION_DENIED_KEY_EMOJI, true);
        edit.apply();
    }

    private void showPermissionDeniedSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    
    public void pauseRecording() {
        if (!this.isRecording || this.isPaused) {
            return;
        }
        this.executorService.execute(new Runnable() { 
            @Override 
            public void run() {
                if (Build.VERSION.SDK_INT >= 24) {
                    AddNoteActivity.this.mediaRecorder.pause();
                }
            }
        });
        runOnUiThread(new Runnable() { 
            @Override 
            public void run() {
                AddNoteActivity.this.isRecording = true;
                AddNoteActivity.this.binding.recordst.setImageResource(R.drawable.play);
                AddNoteActivity.this.pausedTime = System.currentTimeMillis();
                AddNoteActivity.this.isPaused = true;
                AddNoteActivity.this.recorderTimer.pause();
            }
        });
    }

    
    public void resumeRecording() {
        if (this.isRecording && this.isPaused) {
            this.executorService.execute(new Runnable() { 
                @Override 
                public void run() {
                    try {
                        if (Build.VERSION.SDK_INT >= 24) {
                            AddNoteActivity.this.mediaRecorder.resume();
                        }
                        AddNoteActivity.this.isRecording = true;
                        AddNoteActivity.this.runOnUiThread(new Runnable() { 
                            @Override 
                            public void run() {
                                AddNoteActivity.access$1214(AddNoteActivity.this, System.currentTimeMillis() - AddNoteActivity.this.pausedTime);
                                AddNoteActivity.this.recorderTimer.start();
                                AddNoteActivity.this.binding.recordst.setImageResource(R.drawable.pause);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("ERROR", "The error is :" + e.getMessage());
                    }
                }
            });
            runOnUiThread(new Runnable() { 
                @Override 
                public void run() {
                    AddNoteActivity.this.isPaused = false;
                }
            });
        }
    }

    
    public void stopRecording() {
        if (this.isRecording) {
            this.mediaRecorder.stop();
            this.mediaRecorder.release();
            this.mediaRecorder = null;
            this.isRecording = false;
            this.recorderTimer.stop();
            this.waveformView.clear();
        }
    }

    
    public String getRecordingFilePath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/Recording_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".mp3";
    }

    
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
        } else {
            startCameraIntent();
            this.showDialog = true;
        }
    }

    private void handleNotificationClick() {
        Notes noteById = this.db.getNoteById(Long.parseLong(this.id));
        if (noteById != null) {
            this.binding.titleedittxt.setText(noteById.getTitle());
            this.binding.contentedittxt.setText(noteById.getContent());
            Calendar calendar = Calendar.getInstance();
            this.calendar1 = calendar;
            calendar.setTimeInMillis(noteById.getTimestamp());
            updateButtonDate();
            fetchData(Long.parseLong(this.id));
            if (noteById.getColor() != null) {
                this.binding.noteDetailActivity.setBackground(new BitmapDrawable(getResources(), convertBase64ToBitmap(noteById.getColor())));
            }
            if (noteById.getGravity() == 0) {
                this.binding.titleedittxt.setGravity(8388611);
                this.binding.contentedittxt.setGravity(8388611);
                this.gravity = 8388611;
                if (this.imageAdapter != null) {
                    int gravity = noteById.getGravity();
                    this.selectedGravity = gravity;
                    this.imageAdapter.gravity(gravity);
                }
            } else if (noteById.getGravity() == 1) {
                this.binding.titleedittxt.setGravity(1);
                this.binding.contentedittxt.setGravity(1);
                this.gravity = 1;
                if (this.imageAdapter != null) {
                    int gravity2 = noteById.getGravity();
                    this.selectedGravity = gravity2;
                    this.imageAdapter.gravity(gravity2);
                }
            } else if (noteById.getGravity() == 2) {
                this.binding.titleedittxt.setGravity(8388613);
                this.binding.contentedittxt.setGravity(8388613);
                this.gravity = 8388613;
                if (this.imageAdapter != null) {
                    int gravity3 = noteById.getGravity();
                    this.selectedGravity = gravity3;
                    this.imageAdapter.gravity(gravity3);
                }
            }
            noteById.setFontName(noteById.getFontName());
            if (noteById.getFontName() != null) {
                onFontClick(noteById);
            }
            if (noteById.getTextColor() == 0 || noteById.getTextColor() == -1) {
                return;
            }
            onColorChange(noteById.getTextColor());
            return;
        }
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }

    
    public void showDateAndTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() { 
            @Override 
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                AddNoteActivity.this.m349x7aba855f(datePicker, i, i2, i3);
            }
        }, this.calendar.get(1), this.calendar.get(2), this.calendar.get(5));
        datePickerDialog.show();
        Button button = datePickerDialog.getButton(-1);
        button.setTextColor(getResources().getColor(R.color.black));
        button.setBackgroundColor(0);
        Button button2 = datePickerDialog.getButton(-2);
        button2.setTextColor(getResources().getColor(R.color.black));
        button2.setBackgroundColor(0);
    }

    
    
    public  void m349x7aba855f(DatePicker datePicker, int i, int i2, int i3) {
        this.calendar.set(1, i);
        this.calendar.set(2, i2);
        this.calendar.set(5, i3);
        showTimePicker();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() { 
            @Override 
            public final void onTimeSet(TimePicker timePicker, int i, int i2) {
                AddNoteActivity.this.m353xd47b37d3(timePicker, i, i2);
            }
        }, this.calendar.get(11), this.calendar.get(12), false);
        timePickerDialog.show();
        Button button = timePickerDialog.getButton(-1);
        button.setTextColor(getResources().getColor(R.color.black));
        button.setBackgroundColor(0);
        Button button2 = timePickerDialog.getButton(-2);
        button2.setTextColor(getResources().getColor(R.color.black));
        button2.setBackgroundColor(0);
    }

    
    
    public  void m353xd47b37d3(TimePicker timePicker, int i, int i2) {
        this.calendar.set(11, i);
        this.calendar.set(12, i2);
        this.calendar.set(13, 0);
        this.reminderDateInMillis = this.calendar.getTimeInMillis();
    }

    private void startCameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(this, getString(R.string.camera_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void requestRecordingAndStoragePermissions() {
        if (Build.VERSION.SDK_INT > 30) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, RECORD_AUDIO_PERMISSION_CODE);
                return;
            }
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO", "android.permission.MANAGE_EXTERNAL_STORAGE"}, REQUEST_BOTH_PERMISSIONS_CODE);
    }

    public boolean checkRecordingAndStoragePermissions() {
        boolean z = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") == 0;
        return Build.VERSION.SDK_INT > 30 ? z : z && (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0);
    }

    private void handleBothPermissionsResult(int[] iArr) {
        boolean z = iArr.length > 0 && iArr[0] == 0;
        boolean z2 = iArr.length > 1 && iArr[1] == 0;
        if (Build.VERSION.SDK_INT > 30) {
            if (z2) {
                startRecording();
                return;
            } else {
                requestRecordingAndStoragePermissions();
                return;
            }
        }
        if (z2 && z) {
            startRecording();
            return;
        }
        requestRecordingAndStoragePermissions();
        if (!z2) {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
        if (z) {
            return;
        }
        Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 2) {
            if (iArr.length > 0 && iArr[0] == 0) {
                startCameraIntent();
                return;
            } else {
                Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (i == REQUEST_BOTH_PERMISSIONS_CODE) {
            handleBothPermissionsResult(iArr);
        } else {
            if (Build.VERSION.SDK_INT <= 30 || i != RECORD_AUDIO_PERMISSION_CODE) {
                return;
            }
            handleBothPermissionsResult(iArr);
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    
    public void setReminder(long j, long j2, String str, String str2) {
        PendingIntent broadcast;
        int checkSelfPermission;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, (Class<?>) RemindersBroadcastReceiver.class);
        intent.putExtra("noteId", j);
        intent.putExtra(Param.KEY_TITLE, str);
        intent.putExtra(Param.KEY_CONTENT, str2);
        intent.putExtra("check", 2);
        if (Build.VERSION.SDK_INT >= 31) {
            checkSelfPermission = checkSelfPermission("android.permission.SCHEDULE_EXACT_ALARM");
            if (checkSelfPermission != 0) {
                return;
            } else {
                broadcast = PendingIntent.getBroadcast(this, (int) j, intent, PendingIntent.FLAG_MUTABLE);
            }
        } else {
            broadcast = PendingIntent.getBroadcast(this, (int) j, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j2, broadcast);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j2, broadcast);
        }
    }

    public void ProfileSnackBar() {
        View inflate = getLayoutInflater().inflate(R.layout.snackbar_profile, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, -1, getResources().getDisplayMetrics().heightPixels / 4);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.snackbg));
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.showAtLocation(findViewById(android.R.id.content), 80, 0, 0);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.galleryp);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.camerap);
        ((ImageView) inflate.findViewById(R.id.closep)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                AddNoteActivity.this.startActivityForResult(intent, 1);
                popupWindow.dismiss();
                AddNoteActivity.this.showDialog = true;
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.checkCameraPermission();
                popupWindow.dismiss();
                AddNoteActivity.this.showDialog = true;
            }
        });
    }

    public void showSnackBar() {
        View inflate = getLayoutInflater().inflate(R.layout.snackbar_custom, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, -1, getResources().getDisplayMetrics().heightPixels / 2);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.snackbg));
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.showAtLocation(findViewById(android.R.id.content), 80, 0, 0);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList = new ArrayList();
        if (this.theme == 5) {
            arrayList.add(Integer.valueOf(R.drawable.basic_black_screen));
        } else {
            arrayList.add(Integer.valueOf(R.drawable.image_0));
        }
        arrayList.add(Integer.valueOf(R.drawable.image_1));
        arrayList.add(Integer.valueOf(R.drawable.image_2));
        arrayList.add(Integer.valueOf(R.drawable.image_3));
        arrayList.add(Integer.valueOf(R.drawable.image_4));
        arrayList.add(Integer.valueOf(R.drawable.image_5));
        arrayList.add(Integer.valueOf(R.drawable.image_6));
        arrayList.add(Integer.valueOf(R.drawable.image_7));
        arrayList.add(Integer.valueOf(R.drawable.image_8));
        arrayList.add(Integer.valueOf(R.drawable.image_9));
        arrayList.add(Integer.valueOf(R.drawable.image_10));
        arrayList.add(Integer.valueOf(R.drawable.image_11));
        arrayList.add(Integer.valueOf(R.drawable.image_12));
        arrayList.add(Integer.valueOf(R.drawable.image_13));
        arrayList.add(Integer.valueOf(R.drawable.image_14));
        arrayList.add(Integer.valueOf(R.drawable.image_15));
        arrayList.add(Integer.valueOf(R.drawable.image_16));
        arrayList.add(Integer.valueOf(R.drawable.image_17));
        arrayList.add(Integer.valueOf(R.drawable.image_18));
        arrayList.add(Integer.valueOf(R.drawable.image_19));
        arrayList.add(Integer.valueOf(R.drawable.image_20));
        arrayList.add(Integer.valueOf(R.drawable.image_21));
        arrayList.add(Integer.valueOf(R.drawable.image_22));
        arrayList.add(Integer.valueOf(R.drawable.image_23));
        arrayList.add(Integer.valueOf(R.drawable.image_24));
        arrayList.add(Integer.valueOf(R.drawable.image_25));
        arrayList.add(Integer.valueOf(R.drawable.image_26));
        arrayList.add(Integer.valueOf(R.drawable.image_27));
        arrayList.add(Integer.valueOf(R.drawable.image_28));
        arrayList.add(Integer.valueOf(R.drawable.image_29));
        arrayList.add(Integer.valueOf(R.drawable.image_30));
        linkedHashMap.put(getString(R.string.all), arrayList);
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        this.tabLayout = (TabLayout) inflate.findViewById(R.id.tabLayout);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(R.drawable.image_1));
        arrayList2.add(Integer.valueOf(R.drawable.image_2));
        arrayList2.add(Integer.valueOf(R.drawable.image_3));
        arrayList2.add(Integer.valueOf(R.drawable.image_4));
        linkedHashMap.put(getString(R.string.solid), arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(Integer.valueOf(R.drawable.image_5));
        arrayList3.add(Integer.valueOf(R.drawable.image_6));
        arrayList3.add(Integer.valueOf(R.drawable.image_7));
        arrayList3.add(Integer.valueOf(R.drawable.image_8));
        arrayList3.add(Integer.valueOf(R.drawable.image_9));
        arrayList3.add(Integer.valueOf(R.drawable.image_10));
        arrayList3.add(Integer.valueOf(R.drawable.image_11));
        arrayList3.add(Integer.valueOf(R.drawable.image_12));
        linkedHashMap.put(getString(R.string.gradient), arrayList3);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(Integer.valueOf(R.drawable.image_13));
        arrayList4.add(Integer.valueOf(R.drawable.image_14));
        arrayList4.add(Integer.valueOf(R.drawable.image_15));
        arrayList4.add(Integer.valueOf(R.drawable.image_16));
        arrayList4.add(Integer.valueOf(R.drawable.image_17));
        arrayList4.add(Integer.valueOf(R.drawable.image_18));
        arrayList4.add(Integer.valueOf(R.drawable.image_19));
        arrayList4.add(Integer.valueOf(R.drawable.image_20));
        linkedHashMap.put(getString(R.string.simple), arrayList4);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(Integer.valueOf(R.drawable.image_21));
        arrayList5.add(Integer.valueOf(R.drawable.image_22));
        arrayList5.add(Integer.valueOf(R.drawable.image_23));
        arrayList5.add(Integer.valueOf(R.drawable.image_24));
        arrayList5.add(Integer.valueOf(R.drawable.image_25));
        arrayList5.add(Integer.valueOf(R.drawable.image_26));
        arrayList5.add(Integer.valueOf(R.drawable.image_27));
        linkedHashMap.put(getString(R.string.cute), arrayList5);
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(Integer.valueOf(R.drawable.image_28));
        arrayList6.add(Integer.valueOf(R.drawable.image_29));
        arrayList6.add(Integer.valueOf(R.drawable.image_30));
        linkedHashMap.put(getString(R.string.holiday), arrayList6);
        this.categoryTitles = new ArrayList(linkedHashMap.keySet());
        viewPager.setAdapter(new AdapterSnackPager(this, this.categoryTitles, linkedHashMap, new ColorClickListeners() { 
            @Override 
            public final void onColorClick(int i, int i2) {
                AddNoteActivity.this.m352x28bf5988(i, i2);
            }
        }));
        viewPager.setNestedScrollingEnabled(true);
        this.tabLayout.setupWithViewPager(viewPager);
        this.tick = (ImageView) inflate.findViewById(R.id.saveF);
        setThemeTick();
        if (this.tabLayout.getTabAt(0) != null) {
            this.selectedCategory = getString(R.string.all);
        }
        this.binding.noteDetailActivity.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.binding.middlelayout.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.binding.contentedittxt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.binding.titleedittxt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.tick.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void tick(int i) {
        this.tick.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    private void setThemeTick() {
        int i = this.theme;
        if (i == 1) {
            tick(R.color.green);
            return;
        }
        if (i == 2) {
            tick(R.color.pink);
            return;
        }
        if (i == 3) {
            tick(R.color.blue);
            return;
        }
        if (i == 4) {
            tick(R.color.purple);
            return;
        }
        if (i == 5) {
            tick(R.color.purple);
            return;
        }
        if (i == 6) {
            tick(R.color.parrot);
            return;
        }
        if (i == 7) {
            tick(R.color.themedark7);
            return;
        }
        if (i == 8) {
            tick(R.color.themedark8);
            return;
        }
        if (i == 9) {
            tick(R.color.themedark9);
            return;
        }
        if (i == 10) {
            tick(R.color.themedark10);
            return;
        }
        if (i == 11) {
            tick(R.color.themedark11);
            return;
        }
        if (i == 12) {
            tick(R.color.themedark12);
            return;
        }
        if (i == 13) {
            tick(R.color.themedark13);
            return;
        }
        if (i == 14) {
            tick(R.color.themedark14);
            return;
        }
        if (i == 15) {
            tick(R.color.themedark15);
        } else if (i == 16) {
            tick(R.color.themedark16);
        } else if (i == 17) {
            tick(R.color.themedark17);
        }
    }

    
    public void showFontSnackBar() {
        final View inflate = getLayoutInflater().inflate(R.layout.snackbar_custom_font, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, -1, getResources().getDisplayMetrics().heightPixels / 2);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.snackbg));
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.showAtLocation(findViewById(android.R.id.content), 80, 0, 0);
        this.recyclerViewFont = (RecyclerView) inflate.findViewById(R.id.recyclerViewSnackFont);
        this.startGravity = (ImageView) inflate.findViewById(R.id.start);
        this.centerGravity = (ImageView) inflate.findViewById(R.id.center);
        this.endGravity = (ImageView) inflate.findViewById(R.id.end);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerViewSnackColor);
        this.tick = (ImageView) inflate.findViewById(R.id.saved);
        setThemeTick();
        AssetManager assets = getAssets();
        ArrayList arrayList = new ArrayList();
        try {
            String[] list = assets.list("font");
            if (list != null && list.length > 0) {
                for (String str : list) {
                    if (str.endsWith(".ttf") || str.endsWith(".otf")) {
                        String substring = str.substring(0, str.lastIndexOf(46));
                        Notes notes = new Notes();
                        notes.setFontName(substring);
                        arrayList.add(notes);
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        AdapterFont adapterFont = new AdapterFont(this, arrayList, new FontClickListeners() { 
            @Override 
            public final void onFontClick(Notes notes2) {
                AddNoteActivity.this.onFontClick(notes2);
            }
        });
        String str2 = this.fontName;
        if (str2 != null) {
            adapterFont.selectFontByName(str2);
        }
        this.tick.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.recyclerViewFont.setAdapter(adapterFont);
        this.recyclerViewFont.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("#000000");
        arrayList2.add("#FF4874");
        arrayList2.add("#1AC0C6");
        arrayList2.add("#FFA800");
        arrayList2.add("#D527B7");
        arrayList2.add("#00FF85");
        arrayList2.add("#F782C2");
        arrayList2.add("#8A00D4");
        arrayList2.add("#E74645");
        arrayList2.add("#454D66");
        arrayList2.add("#A2C5ED");
        arrayList2.add("#847C8F");
        arrayList2.add("#27104E");
        arrayList2.add("#C6B0E5");
        arrayList2.add("#FF6150");
        arrayList2.add("#DCAECB");
        arrayList2.add("#00C2FF");
        arrayList2.add("#0029B9");
        arrayList2.add("#00B976");
        arrayList2.add("#B90000");
        AdapterColor adapterColor = new AdapterColor(new ColorsChange() { 
            @Override 
            public final void colorChange(int i) {
                AddNoteActivity.this.onColorChange(i);
            }
        }, this, arrayList2);
        recyclerView.setAdapter(adapterColor);
        this.s = (RelativeLayout) inflate.findViewById(R.id.s);
        this.c = (RelativeLayout) inflate.findViewById(R.id.c);
        this.e = (RelativeLayout) inflate.findViewById(R.id.e);
        int i = this.textColor;
        if (i != -1) {
            adapterColor.selectColorByValue(i);
        }
        if (this.isEditMode) {
            this.select = this.select2;
        }
        int i2 = this.select;
        if (i2 == 0) {
            this.s.setBackgroundResource(R.drawable.selectedgravity);
            gravityThemes(this.s);
            this.startGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.centerGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.c.setBackgroundResource(R.drawable.btn_drawalbe);
            this.endGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.e.setBackgroundResource(R.drawable.btn_drawalbe);
            this.e.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.c.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
        } else if (i2 == 1) {
            this.c.setBackgroundResource(R.drawable.selectedgravity);
            gravityThemes(this.c);
            this.centerGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.startGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.s.setBackgroundResource(R.drawable.btn_drawalbe);
            this.endGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.e.setBackgroundResource(R.drawable.btn_drawalbe);
            this.s.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.e.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
        } else if (i2 == 2) {
            this.e.setBackgroundResource(R.drawable.selectedgravity);
            gravityThemes(this.e);
            this.endGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.centerGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.c.setBackgroundResource(R.drawable.btn_drawalbe);
            this.startGravity.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            this.s.setBackgroundResource(R.drawable.btn_drawalbe);
            this.s.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
            this.c.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.card)));
        }
        this.s.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.selectedGravity = 0;
                AddNoteActivity.this.select = 0;
                AddNoteActivity.this.binding.titleedittxt.setGravity(8388611);
                AddNoteActivity.this.binding.contentedittxt.setGravity(8388611);
                if (AddNoteActivity.this.imageAdapter != null) {
                    AddNoteActivity.this.imageAdapter.updateGravity(AddNoteActivity.this.selectedGravity);
                }
                AddNoteActivity.this.s.setBackgroundResource(R.drawable.selectedgravity);
                AddNoteActivity addNoteActivity = AddNoteActivity.this;
                addNoteActivity.gravityThemes(addNoteActivity.s);
                AddNoteActivity.this.startGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.centerGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.c.setBackgroundResource(R.drawable.btn_drawalbe);
                AddNoteActivity.this.c.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.endGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.e.setBackgroundResource(R.drawable.btn_drawalbe);
                AddNoteActivity.this.e.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.selectedGravity = 1;
                AddNoteActivity.this.select = 1;
                AddNoteActivity.this.binding.titleedittxt.setGravity(1);
                AddNoteActivity.this.binding.contentedittxt.setGravity(1);
                if (AddNoteActivity.this.imageAdapter != null) {
                    AddNoteActivity.this.imageAdapter.updateGravity(AddNoteActivity.this.selectedGravity);
                }
                AddNoteActivity.this.c.setBackgroundResource(R.drawable.selectedgravity);
                AddNoteActivity addNoteActivity = AddNoteActivity.this;
                addNoteActivity.gravityThemes(addNoteActivity.c);
                AddNoteActivity.this.centerGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.startGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.s.setBackgroundResource(R.drawable.btn_drawalbe);
                AddNoteActivity.this.endGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.e.setBackgroundResource(R.drawable.btn_drawalbe);
                AddNoteActivity.this.s.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.e.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
            }
        });
        this.e.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddNoteActivity.this.selectedGravity = 2;
                AddNoteActivity.this.select = 2;
                AddNoteActivity.this.binding.titleedittxt.setGravity(8388613);
                AddNoteActivity.this.binding.contentedittxt.setGravity(8388613);
                if (AddNoteActivity.this.imageAdapter != null) {
                    AddNoteActivity.this.imageAdapter.updateGravity(AddNoteActivity.this.selectedGravity);
                }
                AddNoteActivity.this.e.setBackgroundResource(R.drawable.selectedgravity);
                AddNoteActivity addNoteActivity = AddNoteActivity.this;
                addNoteActivity.gravityThemes(addNoteActivity.e);
                AddNoteActivity.this.endGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.centerGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.c.setBackgroundResource(R.drawable.btn_drawalbe);
                AddNoteActivity.this.s.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.c.setBackgroundTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.card)));
                AddNoteActivity.this.startGravity.setImageTintList(ColorStateList.valueOf(AddNoteActivity.this.getResources().getColor(R.color.black)));
                AddNoteActivity.this.s.setBackgroundResource(R.drawable.btn_drawalbe);
            }
        });
        Iterator<ImageModels> it = this.list.iterator();
        while (it.hasNext()) {
            it.next().setGravity(this.selectedGravity);
        }
        this.binding.titleedittxt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        this.binding.contentedittxt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        inflate.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
            @Override 
            public boolean onPreDraw() {
                if (!AddNoteActivity.this.isKeyboardOpen()) {
                    return true;
                }
                popupWindow.dismiss();
                inflate.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    
    public void gravityThemes(RelativeLayout relativeLayout) {
        if (this.theme == 0) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        }
        int i = this.theme;
        if (i == 1) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            return;
        }
        if (i == 2) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
            return;
        }
        if (i == 3) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            return;
        }
        if (i == 4) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
            return;
        }
        if (i == 5) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
            return;
        }
        if (i == 6) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.parrot)));
            return;
        }
        if (i == 7) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark7)));
            return;
        }
        if (i == 8) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark8)));
            return;
        }
        if (i == 9) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark9)));
            return;
        }
        if (i == 10) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark10)));
            return;
        }
        if (i == 11) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark11)));
            return;
        }
        if (i == 12) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark12)));
            return;
        }
        if (i == 13) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark13)));
            return;
        }
        if (i == 14) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark14)));
            return;
        }
        if (i == 15) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark15)));
        } else if (i == 16) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark16)));
        } else if (i == 17) {
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themedark17)));
        }
    }

    
    public boolean isKeyboardOpen() {
        Rect rect = new Rect();
        View findViewById = findViewById(android.R.id.content);
        findViewById.getWindowVisibleDisplayFrame(rect);
        int height = findViewById.getHeight();
        return ((double) (height - rect.bottom)) > ((double) height) * 0.15d;
    }

    public void saveNote() {
        final String obj = this.binding.titleedittxt.getText().toString();
        final String obj2 = this.binding.contentedittxt.getText().toString();
        final String stringExtra = getIntent().getStringExtra("nameFolder");
        this.isTrue = getIntent().getBooleanExtra("true", false);
        this.binding.saveNoteBtn.setEnabled(false);
        final Notes notes = new Notes();
        notes.setTitle(obj);
        notes.setContent(obj2);
        notes.setFontName(this.fontName);
        notes.setTextColor(this.textColor);
        int i = this.isDark;
        if (i != -1) {
            notes.setIsDark(i);
        }
        long j = this.calenderTime;
        if (j != 0) {
            notes.setTimestamp(j);
        } else if (this.dateChanged) {
            notes.setTimestamp(this.timesTamp);
        } else {
            long j2 = this.l;
            if (j2 != 0) {
                notes.setTimestamp(j2);
            } else {
                notes.setTimestamp(System.currentTimeMillis());
            }
        }
        this.binding.animationlayout.setVisibility(View.VISIBLE);
        this.binding.animation.setAnimation("done.json");
        this.binding.animation.playAnimation();
        this.binding.animation.addAnimatorListener(new AnimatorListenerAdapter() { 
            @Override 
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (!AddNoteActivity.this.isEditMode) {
                    if (AddNoteActivity.this.selectedGravity != -1) {
                        notes.setGravity(AddNoteActivity.this.selectedGravity);
                    }
                    notes.setReminderDate(AddNoteActivity.this.reminderDateInMillis);
                    if (AddNoteActivity.this.defualtbackground == null) {
                        notes.setColor(null);
                    } else {
                        notes.setColor(AddNoteActivity.this.base64BackgroundImage);
                    }
                    notes.setFolderName(stringExtra);
                    AddNoteActivity addNoteActivity = AddNoteActivity.this;
                    addNoteActivity.noteId = addNoteActivity.db.addNotes(notes);
                    for (ImageModels imageModels : AddNoteActivity.this.list) {
                        imageModels.setNoteId(AddNoteActivity.this.noteId);
                        if (AddNoteActivity.this.isDark != -1) {
                            imageModels.setIsDark(AddNoteActivity.this.isDark);
                        }
                        AddNoteActivity.this.dbImg.addImageforNote(imageModels);
                    }
                    if (AddNoteActivity.this.reminderDateInMillis > 0) {
                        AddNoteActivity addNoteActivity2 = AddNoteActivity.this;
                        addNoteActivity2.setReminder(addNoteActivity2.noteId * 2, AddNoteActivity.this.reminderDateInMillis, obj, obj2);
                    }
                    HolderDialog.getInstance().setVariable(true);
                } else {
                    if (AddNoteActivity.this.defualtbackground == null) {
                        notes.setColor(null);
                    } else if (AddNoteActivity.this.base64BackgroundImage != null) {
                        notes.setColor(AddNoteActivity.this.base64BackgroundImage);
                    } else {
                        notes.setColor(AddNoteActivity.this.color);
                        AddNoteActivity addNoteActivity3 = AddNoteActivity.this;
                        AddNoteActivity.this.getWindow().getDecorView().setBackground(new BitmapDrawable(AddNoteActivity.this.getResources(), addNoteActivity3.convertBase64ToBitmap(addNoteActivity3.color)));
                    }
                    AddNoteActivity.this.folder = ManagerData.getInstance().getSharedVariable();
                    if (AddNoteActivity.this.folder != null) {
                        notes.setFolderName(AddNoteActivity.this.folder);
                    }
                    if (AddNoteActivity.this.docId != null) {
                        notes.setId(Long.parseLong(AddNoteActivity.this.docId));
                    }
                    if (AddNoteActivity.this.id != null) {
                        notes.setId(Long.parseLong(AddNoteActivity.this.id));
                    }
                    if (AddNoteActivity.this.selectedGravity != -1) {
                        notes.setGravity(AddNoteActivity.this.selectedGravity);
                    }
                    notes.setReminderDate(AddNoteActivity.this.reminderDateInMillis);
                    if (AddNoteActivity.this.reminderDateInMillis > 0) {
                        AddNoteActivity.this.setReminder(notes.getId(), AddNoteActivity.this.reminderDateInMillis, obj, obj2);
                    }
                    Log.d("Reminder", "" + AddNoteActivity.this.reminderDateInMillis);
                    if (AddNoteActivity.this.db.updateNote(notes) > 0) {
                        HashSet hashSet = new HashSet();
                        Iterator<ImageModels> it = AddNoteActivity.this.list.iterator();
                        while (it.hasNext()) {
                            hashSet.add(Long.valueOf(it.next().getId()));
                        }
                        for (ImageModels imageModels2 : AddNoteActivity.this.list) {
                            if (hashSet.contains(Long.valueOf(imageModels2.getId()))) {
                                if (AddNoteActivity.this.isDark != -1) {
                                    imageModels2.setIsDark(AddNoteActivity.this.isDark);
                                }
                                AddNoteActivity.this.dbImg.updateImage(imageModels2);
                            }
                        }
                        for (ImageModels imageModels3 : AddNoteActivity.this.list) {
                            if (imageModels3.getId() == 0) {
                                imageModels3.setNoteId(Long.parseLong(AddNoteActivity.this.docId));
                                imageModels3.setFontName(AddNoteActivity.this.fontName);
                                imageModels3.setTextcolor(AddNoteActivity.this.textColor);
                                imageModels3.setGravity(AddNoteActivity.this.selectedGravity);
                                if (AddNoteActivity.this.isDark != -1) {
                                    imageModels3.setIsDark(AddNoteActivity.this.isDark);
                                }
                                AddNoteActivity.this.dbImg.addImageforNote(imageModels3);
                            }
                        }
                    } else {
                        AddNoteActivity addNoteActivity4 = AddNoteActivity.this;
                        Toast.makeText(addNoteActivity4, addNoteActivity4.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                }
                AddNoteActivity.this.startActivity(new Intent(AddNoteActivity.this, (Class<?>) ActivityMain.class).putExtra("note1", 1));
                AddNoteActivity.this.finishAffinity();
            }
        });
    }

    
    public void saveRecording() {
        String str = this.recordingFilePath;
        if (str == null || str.isEmpty()) {
            return;
        }
        try {
            ImageModels imageModels = new ImageModels(this.recordingFilePath, 2);
            imageModels.setFontName(this.fontName);
            imageModels.setTextcolor(this.textColor);
            imageModels.setGravity(this.selectedGravity);
            if (this.list != null) {
                this.listItems.add(imageModels);
                RecyclerViewRecording_Img(this.listItems);
                this.imageAdapter.notifyItemInserted(this.listItems.size() - 1);
            }
            Recording_ImageAdapter recording_ImageAdapter = this.imageAdapter;
            if (recording_ImageAdapter != null) {
                recording_ImageAdapter.notifyDataSetChanged();
            }
            this.list.add(imageModels);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUrl(String str) {
        try {
            String convertBitmapToBase64 = convertBitmapToBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(str)));
            if (convertBitmapToBase64 != null) {
                ImageModels imageModels = new ImageModels();
                imageModels.setImage(convertBitmapToBase64);
                imageModels.setFontName(this.fontName);
                imageModels.setTextcolor(this.textColor);
                imageModels.setGravity(this.selectedGravity);
                this.listItems.add(imageModels);
                RecyclerViewRecording_Img(this.listItems);
                Recording_ImageAdapter recording_ImageAdapter = this.imageAdapter;
                if (recording_ImageAdapter != null) {
                    recording_ImageAdapter.notifyItemInserted(this.listItems.size() - 1);
                }
                this.list.add(imageModels);
            } else {
                Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR", "" + e.getMessage());
            Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
        Recording_ImageAdapter recording_ImageAdapter2 = this.imageAdapter;
        if (recording_ImageAdapter2 != null) {
            recording_ImageAdapter2.notifyDataSetChanged();
        }
    }

    private void fetchData(long j) {
        List<ImageModels> imagesByNoteId = this.dbImg.getImagesByNoteId(j);
        this.imageModels = imagesByNoteId;
        for (ImageModels imageModels : imagesByNoteId) {
            Log.d("List", "" + imageModels.getImage() + " " + imageModels.getFilepath());
        }
        ArrayList<ImageModels> arrayList = new ArrayList(this.imageModels);
        this.list = arrayList;
        for (ImageModels imageModels2 : arrayList) {
            imageModels2.setRecordingText(this.dbImg.getRecordingTextById(imageModels2.getId()));
            this.dbImg.updateImage(imageModels2);
        }
        for (ImageModels imageModels3 : this.list) {
            String imageTextById = this.dbImg.getImageTextById(imageModels3.getId());
            this.selectedGravity = imageModels3.getGravity();
            imageModels3.setFontName(imageModels3.getFontName());
            this.dbImg.updateImage(imageModels3);
            imageModels3.setImageText(imageTextById);
        }
        Iterator<ImageModels> it = this.list.iterator();
        while (it.hasNext()) {
            this.listItems.add(it.next());
        }
        RecyclerViewRecording_Img(this.listItems);
    }

    
    public void onFontClick(Notes notes) {
        AssetManager assets = getAssets();
        String fontName = notes.getFontName();
        this.fontName = fontName;
        if (fontName != null) {
            try {
                Typeface createFromAsset = Typeface.createFromAsset(assets, "font/" + this.fontName + ".ttf");
                this.binding.contentedittxt.setTypeface(createFromAsset);
                this.binding.titleedittxt.setTypeface(createFromAsset);
                this.binding.date.setTypeface(createFromAsset);
                this.binding.monthyear.setTypeface(createFromAsset);
                Iterator<ImageModels> it = this.list.iterator();
                while (it.hasNext()) {
                    it.next().setFontName(this.fontName);
                }
                Recording_ImageAdapter recording_ImageAdapter = this.imageAdapter;
                if (recording_ImageAdapter != null) {
                    recording_ImageAdapter.updateFontStyle(this.fontName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    public void onColorChange(int i) {
        this.textColor = i;
        if (i != 0) {
            this.binding.contentedittxt.setTextColor(i);
            this.binding.titleedittxt.setTextColor(i);
            this.binding.contentedittxt.setHintTextColor(i);
            this.binding.titleedittxt.setHintTextColor(i);
            this.binding.date.setTextColor(i);
            this.binding.monthyear.setTextColor(i);
            this.binding.view.setBackgroundColor(i);
            this.binding.dateBtn.setImageTintList(ColorStateList.valueOf(i));
            Iterator<ImageModels> it = this.list.iterator();
            while (it.hasNext()) {
                it.next().setTextcolor(this.textColor);
            }
        }
        Recording_ImageAdapter recording_ImageAdapter = this.imageAdapter;
        if (recording_ImageAdapter != null) {
            recording_ImageAdapter.updateFontColor(this.textColor);
        }
    }

    
    public void m352x28bf5988(int i, int i2) {
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { 
            @Override 
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override 
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override 
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                AddNoteActivity addNoteActivity = AddNoteActivity.this;
                addNoteActivity.selectedCategory = addNoteActivity.categoryTitles.get(position);
            }
        });
        String str = this.selectedCategory;
        if (str != null && str.equals(getString(R.string.all)) && i2 == 0) {
            if (this.theme == 5) {
                getWindow().getDecorView().setBackgroundResource(R.drawable.black);
                int i3 = this.textColor;
                if (i3 == -1 || i3 == -16777216) {
                    onColorChange(-1);
                    this.textColor = -1;
                }
                this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                this.isDark = 1;
            } else {
                getWindow().getDecorView().setBackgroundResource(R.drawable.grey);
            }
            this.defualtbackground = null;
            return;
        }
        if (this.theme == 5) {
            if (this.textColor == -1) {
                onColorChange(ViewCompat.MEASURED_STATE_MASK);
                this.textColor = -1;
            }
            this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
        this.isDark = 0;
        this.selectedColor = i;
        getWindow().getDecorView().setBackgroundResource(this.selectedColor);
        this.base64BackgroundImage = convertBitmapToBase64(BitmapFactory.decodeResource(getResources(), this.selectedColor));
        this.defualtbackground = "Defualt";
    }

    @Override 
    public void onBackPressed() {
        this.isBackpressed = false;
        if (this.binding.transparentLayout.getVisibility() == View.VISIBLE) {
            stopRecording();
            this.recordingStartTime = 0L;
            this.binding.tvTime.setText("00:00:00");
            this.waveformView.clear();
            this.binding.tick.setVisibility(View.GONE);
            this.binding.closeButton.setVisibility(View.GONE);
            this.binding.recordst.setImageResource(R.drawable.recording_start);
            this.binding.tapstartTextView.setVisibility(View.VISIBLE);
            this.binding.transparentLayout.setVisibility(View.GONE);
            enableButtons();
        } else {
            AXEmojiPopup aXEmojiPopup = this.emojiPopup;
            if (aXEmojiPopup != null) {
                aXEmojiPopup.dismiss();
                this.emojiPopup = null;
            } else {
                backhomeAlertDialog();
            }
        }
        LockHolder.getInstance().setboolean(false);
    }

    public void settheme() {
        int i = this.theme;
        if (i == 1) {
            addButton(R.color.green);
        } else if (i == 2) {
            addButton(R.color.pink);
        } else if (i == 3) {
            addButton(R.color.blue);
        } else if (i == 4) {
            addButton(R.color.purple);
        } else if (i == 5) {
            addButton(R.color.purple);
        } else if (i == 6) {
            addButton(R.color.parrot);
        } else if (i == 7) {
            addButton(R.color.themedark7);
        } else if (i == 8) {
            addButton(R.color.themedark8);
        } else if (i == 9) {
            addButton(R.color.themedark9);
        } else if (i == 10) {
            addButton(R.color.themedark10);
        } else if (i == 11) {
            addButton(R.color.themedark11);
        } else if (i == 12) {
            addButton(R.color.themedark12);
        } else if (i == 13) {
            addButton(R.color.themedark13);
        } else if (i == 14) {
            addButton(R.color.themedark14);
        } else if (i == 15) {
            addButton(R.color.themedark15);
        } else if (i == 16) {
            addButton(R.color.themedark16);
        } else if (i == 17) {
            addButton(R.color.themedark17);
        }
        if (this.theme == 5) {
            this.binding.date.setTextColor(getResources().getColor(R.color.white));
            this.binding.monthyear.setTextColor(getResources().getColor(R.color.white));
            this.binding.dateBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            this.binding.view.setBackgroundColor(getResources().getColor(R.color.white));
            this.binding.titleedittxt.setTextColor(getResources().getColor(R.color.white));
            this.binding.titleedittxt.setHintTextColor(getResources().getColor(R.color.text_hint_white));
            this.binding.contentedittxt.setTextColor(getResources().getColor(R.color.white));
            this.binding.contentedittxt.setHintTextColor(getResources().getColor(R.color.text_hint_white));
            this.binding.bottomnavigation.setBackgroundColor(getResources().getColor(R.color.light_black));
            return;
        }
        this.binding.date.setTextColor(getResources().getColor(R.color.black));
        this.binding.monthyear.setTextColor(getResources().getColor(R.color.black));
        this.binding.dateBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        this.binding.view.setBackgroundColor(getResources().getColor(R.color.black));
        this.binding.titleedittxt.setTextColor(getResources().getColor(R.color.black));
        this.binding.titleedittxt.setHintTextColor(getResources().getColor(R.color.text_hint));
        this.binding.contentedittxt.setTextColor(getResources().getColor(R.color.black));
        this.binding.contentedittxt.setHintTextColor(getResources().getColor(R.color.text_hint));
        this.binding.bottomnavigation.setBackgroundColor(getResources().getColor(R.color.bottomnav_white));
    }

    public void addButton(int i) {
        this.binding.saveNoteBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    @Override 
    public void onTimerTick(String str) {
        this.binding.tvTime.setText(str);
        this.waveformView.addAmplitude(this.mediaRecorder.getMaxAmplitude());
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
        }
        if (this.showDialog) {
            LockHolder.getInstance().setboolean(false);
            this.showDialog = !this.showDialog;
        }
    }

    
    @Override 
    public void onStart() {
        super.onStart();
        boolean z = LockHolder.getInstance().getboolean();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z && this.isActivityVisible) {
            new PatternDialog(this).showDialog();
        }
        LockHolder.getInstance().setboolean(true);
        this.isActivityVisible = false;
    }

    
    @Override 
    public void onStop() {
        super.onStop();
        this.isActivityVisible = true;
    }
}
