package com.demo.checklistnotedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.Lock.LockHolder;
import com.demo.checklistnotedemo.Lock.PatternDialog;
import com.demo.checklistnotedemo.Lock.SharedPrefrence;
import com.demo.checklistnotedemo.databinding.ProfileActivityBinding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ActivityProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 2;
    private static final int REQUEST_PICK_IMAGE = 1;
    String base64;
    ProfileActivityBinding binding;
    Uri imageUri;
    int theme;
    boolean isBackpressed = true;
    boolean showDialog = false;
    private boolean isActivityVisible = false;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ProfileActivityBinding inflate = ProfileActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());



        settheme();
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs4", 0);
        String string = getSharedPreferences("my_prefs3", 0).getString("my_key3", "");
        String string2 = sharedPreferences.getString("my_key4", "");
        getWindow().setSoftInputMode(32);
        this.binding.image.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                LockHolder.getInstance().setboolean(false);
                ActivityProfile.this.ProfileSnackBar();
                ActivityProfile.this.closeKeyboard();
            }
        });
        this.binding.activity.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityProfile.this.closeKeyboard();
            }
        });
        this.binding.textView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityProfile.this.closeKeyboard();
            }
        });
        this.binding.nameet.setText(string);
        this.binding.statuset.setText(string2);
        if (this.base64 == null) {
            ShowImage();
        }
        this.binding.camera.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                LockHolder.getInstance().setboolean(false);
                ActivityProfile.this.ProfileSnackBar();
                ActivityProfile.this.closeKeyboard();
            }
        });
        this.binding.saveNoteBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityProfile.this.showDialog = true;
                ActivityProfile.this.startActivity(new Intent(ActivityProfile.this, (Class<?>) ActivityMain.class).putExtra( "profile", 2));
                if (ActivityProfile.this.base64 != null) {
                    ActivityProfile activityProfile = ActivityProfile.this;
                    activityProfile.sharedPreference(activityProfile.base64);
                }
                SharedPreferences sharedPreferences2 = ActivityProfile.this.getSharedPreferences("my_prefs4", 0);
                SharedPreferences.Editor edit = ActivityProfile.this.getSharedPreferences("my_prefs3", 0).edit();
                SharedPreferences.Editor edit2 = sharedPreferences2.edit();
                edit.putString("my_key3", ActivityProfile.this.binding.nameet.getText().toString());
                edit2.putString("my_key4", ActivityProfile.this.binding.statuset.getText().toString());
                edit.apply();
                edit2.apply();
                ActivityProfile.this.finishAffinity();
            }
        });
        this.binding.backhome.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityProfile.this.onBackPressed();
            }
        });
    }

    private void setStatusBarTransparentWhite() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(1280);
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void ShowImage() {
        String string = getSharedPreferences("my_prefs2", 0).getString("my_key2", "");
        if (string.equals("")) {
            return;
        }
        this.binding.image.setImageBitmap(convertBase64ToBitmap(string));
    }

    
    public void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private Bitmap convertBase64ToBitmap(String str) {
        if (str == null || str.isEmpty()) {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            return null;
        }
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public void ProfileSnackBar() {
        View inflate = getLayoutInflater().inflate(R.layout.snackbar_profile, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, -1, getResources().getDisplayMetrics().heightPixels / 4);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.snackbg));
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
//        popupWindow.setAnimationStyle(android.R.style.Animation);
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
                ActivityProfile.this.startActivityForResult(intent, 1);
                popupWindow.dismiss();
                LockHolder.getInstance().setboolean(false);
                ActivityProfile.this.showDialog = true;
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityProfile.this.checkCameraPermission();
                LockHolder.getInstance().setboolean(false);
                ActivityProfile.this.showDialog = true;
                popupWindow.dismiss();
            }
        });
    }

    
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
        } else {
            startCameraIntent();
        }
    }

    private void startCameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(this, getString(R.string.camera_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        LockHolder.getInstance().setboolean(false);
        if (i == 1 && i2 == -1 && intent != null) {
            Uri data = intent.getData();
            this.imageUri = data;
            try {
                String convertBitmapToBase64 = convertBitmapToBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(data.toString())));
                this.base64 = convertBitmapToBase64;
                if (convertBitmapToBase64 != null) {
                    this.binding.image.setImageBitmap(convertBase64ToBitmap(convertBitmapToBase64));
                    return;
                }
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (i != 2 || i2 != -1 || intent == null || intent == null || (bitmap = (Bitmap) intent.getExtras().get("data")) == null) {
            return;
        }
        String convertBitmapToBase642 = convertBitmapToBase64(bitmap);
        this.base64 = convertBitmapToBase642;
        if (convertBitmapToBase642 != null) {
            this.binding.image.setImageBitmap(convertBase64ToBitmap(convertBitmapToBase642));
        }
    }

    public void sharedPreference(String str) {
        SharedPreferences.Editor edit = getSharedPreferences("my_prefs2", 0).edit();
        edit.putString("my_key2", str);
        edit.apply();
        ShowImage();
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 2) {
            if (iArr.length > 0 && iArr[0] == 0) {
                startCameraIntent();
            } else {
                Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cardBackground(int i) {
        this.binding.cardView2.setCardBackgroundColor(getResources().getColor(i));
        this.binding.cardView3.setCardBackgroundColor(getResources().getColor(i));
    }

    public void settheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            ischeckedColor(R.color.green);
        }
        if (i == 1) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_green));
            ischeckedColor(R.color.green);
        } else if (i == 2) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_pink));
            ischeckedColor(R.color.pink);
        } else if (i == 3) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_blue));
            ischeckedColor(R.color.blue);
        } else if (i == 4) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_purple));
            ischeckedColor(R.color.purple);
        } else if (i == 5) {
            setbackgroundcolor(getResources().getColor(R.color.black));
            ischeckedColor(R.color.purple);
        } else if (i == 6) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_parrot));
            ischeckedColor(R.color.parrot);
        } else if (i == 7) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_7));
            ischeckedColor(R.color.themedark7);
        } else if (i == 8) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_8));
            ischeckedColor(R.color.themedark8);
        } else if (i == 9) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_9));
            ischeckedColor(R.color.themedark9);
        } else if (i == 10) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_10));
            ischeckedColor(R.color.themedark10);
        } else if (i == 11) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_11));
            ischeckedColor(R.color.themedark11);
        } else if (i == 12) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_12));
            ischeckedColor(R.color.themedark12);
        } else if (i == 13) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_13));
            ischeckedColor(R.color.themedark13);
        } else if (i == 14) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_14));
            ischeckedColor(R.color.themedark14);
        } else if (i == 15) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_15));
            ischeckedColor(R.color.themedark15);
        } else if (i == 16) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_16));
            ischeckedColor(R.color.themedark16);
        } else if (i == 17) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_17));
            ischeckedColor(R.color.themedark17);
        }
        if (i == 5) {
            cardBackground(R.color.light_black);
        } else {
            cardBackground(R.color.white);
        }
        if (i == 5) {
            textcolors(R.color.white);
            if (Build.VERSION.SDK_INT >= 23) {
                setStatusBarTransparentBlack();
                return;
            }
            return;
        }
        if (i == 15 || i == 16 || i == 17) {
            textcolors(R.color.black);
            this.binding.textView2.setTextColor(getResources().getColor(R.color.white));
            this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            setStatusBarTransparentWhite();
            return;
        }
        textcolors(R.color.black);
        if (Build.VERSION.SDK_INT >= 23) {
            setStatusBarTransparentBlack();
        }
    }

    private void ischeckedColor(int i) {
        this.binding.saveNoteBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.camera.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void textcolors(int i) {
        this.binding.textView2.setTextColor(getResources().getColor(i));
        this.binding.backhome.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.textView4.setTextColor(getResources().getColor(i));
        this.binding.text.setTextColor(getResources().getColor(i));
        this.binding.nameet.setTextColor(getResources().getColor(i));
        this.binding.nameet.setHintTextColor(getResources().getColor(i));
        this.binding.statuset.setTextColor(getResources().getColor(i));
        this.binding.statuset.setHintTextColor(getResources().getColor(i));
    }

    public void setbackgroundcolor(int i) {
        this.binding.activity.setBackgroundColor(i);
    }

    @Override 
    public void onBackPressed() {
        this.isBackpressed = false;
        setResult(-1, new Intent());
        LockHolder.getInstance().setboolean(false);
        finish();
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
