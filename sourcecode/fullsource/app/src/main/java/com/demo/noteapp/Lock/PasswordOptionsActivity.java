package com.demo.noteapp.Lock;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.noteapp.R;
import com.demo.noteapp.databinding.ActivityPasswordOptionsBinding;


public class PasswordOptionsActivity extends AppCompatActivity {
    ActivityPasswordOptionsBinding binding;
    int theme;
    private boolean showDialog = true;
    private int PASSCODE = 222;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPasswordOptionsBinding inflate = ActivityPasswordOptionsBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());



        settheme();
        this.binding.backArrow.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                PasswordOptionsActivity.this.onBackPressed();
            }
        });
        if (!SharedPrefrence.getPasswordSwitch(this)) {
            disableChangePassword();
        }
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty()) {
            this.binding.passwordSwitch.setChecked(true);
            setthemechangeSwitch();
        } else {
            disableChangePassword();
            this.binding.passwordSwitch.getTrackDrawable().setColorFilter(getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
        }
        if (SharedPrefrence.getFingerPrintEnabled(this) && !SharedPrefrence.getSavedPattern(this).isEmpty()) {
            this.binding.fingerprintswitch.setChecked(true);
            setthemefingerSwitch();
        } else {
            this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
        }

        final FingerprintManager m = (FingerprintManager)getSystemService(Context.FINGERPRINT_SERVICE);
//        final FingerprintManager m = TrashActivity$$ExternalSyntheticApiModelOutline0.m(getSystemService(Context.FINGERPRINT_SERVICE));
        this.binding.fingerprintswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                boolean isHardwareDetected;
                boolean hasEnrolledFingerprints;
                if (z) {
                    PasswordOptionsActivity.this.setthemefingerSwitch();
                    FingerprintManager fingerprintManager = m;
                    if (fingerprintManager == null) {
                        PasswordOptionsActivity passwordOptionsActivity = PasswordOptionsActivity.this;
                        Toast.makeText(passwordOptionsActivity, passwordOptionsActivity.getString(R.string.your_device_has_no_fingerprint_sensor), Toast.LENGTH_SHORT).show();
                        PasswordOptionsActivity.this.binding.fingerprintswitch.setChecked(false);
                        PasswordOptionsActivity.this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(PasswordOptionsActivity.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                    } else {
                        isHardwareDetected = fingerprintManager.isHardwareDetected();
                        if (!isHardwareDetected) {
                            PasswordOptionsActivity passwordOptionsActivity2 = PasswordOptionsActivity.this;
                            Toast.makeText(passwordOptionsActivity2, passwordOptionsActivity2.getString(R.string.your_device_has_no_fingerprint_sensor), Toast.LENGTH_SHORT).show();
                            PasswordOptionsActivity.this.binding.fingerprintswitch.setChecked(false);
                            PasswordOptionsActivity.this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(PasswordOptionsActivity.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                        } else {
                            hasEnrolledFingerprints = m.hasEnrolledFingerprints();
                            if (!hasEnrolledFingerprints) {
                                PasswordOptionsActivity passwordOptionsActivity3 = PasswordOptionsActivity.this;
                                Toast.makeText(passwordOptionsActivity3, passwordOptionsActivity3.getString(R.string.fingerprint_is_not_enabled), Toast.LENGTH_SHORT).show();
                                PasswordOptionsActivity.this.binding.fingerprintswitch.setChecked(false);
                                PasswordOptionsActivity.this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(PasswordOptionsActivity.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                    z = false;
                } else {
                    PasswordOptionsActivity.this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(PasswordOptionsActivity.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                }
                SharedPrefrence.setFingerprintEnabled(z, PasswordOptionsActivity.this);
            }
        });
        this.binding.passwordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    PasswordOptionsActivity.this.disableChangePassword();
                    PasswordOptionsActivity.this.binding.passwordSwitch.getTrackDrawable().setColorFilter(PasswordOptionsActivity.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                } else {
                    PasswordOptionsActivity.this.setthemechangeSwitch();
                    PasswordOptionsActivity.this.enableChangePassword();
                }
                if (!SharedPrefrence.getSavedPattern(PasswordOptionsActivity.this).isEmpty()) {
                    SharedPrefrence.setPasswordSwitch(z, PasswordOptionsActivity.this);
                } else {
                    PasswordOptionsActivity.this.binding.passwordSwitch.setChecked(false);
                    PasswordOptionsActivity.this.startActivityForResult(new Intent(PasswordOptionsActivity.this, (Class<?>) AddPasswordActivity.class), PasswordOptionsActivity.this.PASSCODE);
                }
            }
        });
        this.binding.changePasswordOption.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(PasswordOptionsActivity.this, (Class<?>) ShowPasscodeActivity.class);
                intent.putExtra("change", true);
                PasswordOptionsActivity passwordOptionsActivity = PasswordOptionsActivity.this;
                passwordOptionsActivity.startActivityForResult(intent, passwordOptionsActivity.PASSCODE);
            }
        });
        this.binding.thirdCard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                PasswordOptionsActivity.this.startActivity(new Intent(PasswordOptionsActivity.this, (Class<?>) ShowPasscodeActivity.class).putExtra("question", true));
            }
        });
    }

    private void setStatusBarTransparent() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(1280);
    }

    
    public void disableChangePassword() {
        this.binding.changePasswordOption.setEnabled(false);
        this.binding.fingerprintswitch.setEnabled(false);
        this.binding.thirdCard.setEnabled(false);
        this.binding.fingerprintswitch.setChecked(true);
        this.binding.SecondCard.setCardBackgroundColor(getResources().getColor(R.color.backgroundcolor));
        this.binding.thirdCard.setCardBackgroundColor(getResources().getColor(R.color.backgroundcolor));
        this.binding.fourthCard.setCardBackgroundColor(getResources().getColor(R.color.backgroundcolor));
    }

    
    public void enableChangePassword() {
        this.binding.changePasswordOption.setEnabled(true);
        this.binding.fingerprintswitch.setEnabled(true);
        if (this.theme == 5) {
            this.binding.SecondCard.setCardBackgroundColor(getResources().getColor(R.color.light_black));
            this.binding.thirdCard.setCardBackgroundColor(getResources().getColor(R.color.light_black));
            this.binding.fourthCard.setCardBackgroundColor(getResources().getColor(R.color.light_black));
        } else {
            this.binding.SecondCard.setCardBackgroundColor(getResources().getColor(R.color.white));
            this.binding.thirdCard.setCardBackgroundColor(getResources().getColor(R.color.white));
            this.binding.fourthCard.setCardBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty()) {
            this.binding.passwordSwitch.setChecked(true);
            setthemechangeSwitch();
        } else {
            disableChangePassword();
            this.binding.passwordSwitch.getTrackDrawable().setColorFilter(getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        this.isBackpressed = false;
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
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

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.showDialog = false;
    }

    public void cardBackground(int i) {
        this.binding.firstCard.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.SecondCard.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.thirdCard.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.fourthCard.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 1) {
            color6 = getColor(R.color.full_light_green);
            setbackgroundcolor(color6);
        } else if (i == 2) {
            color5 = getColor(R.color.full_light_pink);
            setbackgroundcolor(color5);
        } else if (i == 3) {
            color4 = getColor(R.color.full_light_blue);
            setbackgroundcolor(color4);
        } else if (i == 4) {
            color3 = getColor(R.color.full_light_purple);
            setbackgroundcolor(color3);
        } else if (i == 5) {
            color2 = getColor(R.color.black);
            setbackgroundcolor(color2);
        } else if (i == 6) {
            color = getColor(R.color.full_light_parrot);
            setbackgroundcolor(color);
        } else if (i == 7) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_7));
        } else if (i == 8) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_8));
        } else if (i == 9) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_9));
        } else if (i == 10) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_10));
        } else if (i == 11) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_11));
        } else if (i == 12) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_12));
        } else if (i == 13) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_13));
        } else if (i == 14) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_14));
        } else if (i == 15) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_15));
        } else if (i == 16) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_17));
        }
        if (this.theme == 5) {
            cardBackground(R.color.light_black);
        } else {
            cardBackground(R.color.white);
        }
        int i2 = this.theme;
        if (i2 == 5) {
            textcolors(R.color.white);
            return;
        }
        if (i2 == 15 || i2 == 16 || i2 == 17) {
            textcolors(R.color.black);
            this.binding.titleToolbarTv.setTextColor(getResources().getColor(R.color.white));
            this.binding.backArrow.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            return;
        }
        textcolors(R.color.black);
    }

    public void isSwitchColor(int i) {
        this.binding.fingerprintswitch.getTrackDrawable().setColorFilter(getResources().getColor(i), PorterDuff.Mode.SRC_IN);
    }

    public void isSwitchChangeColor(int i) {
        this.binding.passwordSwitch.getTrackDrawable().setColorFilter(getResources().getColor(i), PorterDuff.Mode.SRC_IN);
    }

    public void setthemefingerSwitch() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            isSwitchColor(R.color.green);
        }
        if (i == 1) {
            isSwitchColor(R.color.green);
            return;
        }
        if (i == 2) {
            isSwitchColor(R.color.pink);
            return;
        }
        if (i == 3) {
            isSwitchColor(R.color.blue);
            return;
        }
        if (i == 4) {
            isSwitchColor(R.color.purple);
            return;
        }
        if (i == 5) {
            isSwitchColor(R.color.purple);
            return;
        }
        if (i == 6) {
            isSwitchColor(R.color.parrot);
            return;
        }
        if (i == 7) {
            isSwitchColor(R.color.themedark7);
            return;
        }
        if (i == 8) {
            isSwitchColor(R.color.themedark8);
            return;
        }
        if (i == 9) {
            isSwitchColor(R.color.themedark9);
            return;
        }
        if (i == 10) {
            isSwitchColor(R.color.themedark10);
            return;
        }
        if (i == 11) {
            isSwitchColor(R.color.themedark11);
            return;
        }
        if (i == 12) {
            isSwitchColor(R.color.themedark12);
            return;
        }
        if (i == 13) {
            isSwitchColor(R.color.themedark13);
            return;
        }
        if (i == 14) {
            isSwitchColor(R.color.themedark14);
            return;
        }
        if (i == 15) {
            isSwitchColor(R.color.themedark15);
        } else if (i == 16) {
            isSwitchColor(R.color.themedark16);
        } else if (i == 17) {
            isSwitchColor(R.color.themedark17);
        }
    }

    public void setthemechangeSwitch() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            isSwitchChangeColor(R.color.green);
        }
        if (i == 1) {
            isSwitchChangeColor(R.color.green);
            return;
        }
        if (i == 2) {
            isSwitchChangeColor(R.color.pink);
            return;
        }
        if (i == 3) {
            isSwitchChangeColor(R.color.blue);
            return;
        }
        if (i == 4) {
            isSwitchChangeColor(R.color.purple);
            return;
        }
        if (i == 5) {
            isSwitchChangeColor(R.color.purple);
            return;
        }
        if (i == 6) {
            isSwitchChangeColor(R.color.parrot);
            return;
        }
        if (i == 7) {
            isSwitchChangeColor(R.color.themedark7);
            return;
        }
        if (i == 8) {
            isSwitchChangeColor(R.color.themedark8);
            return;
        }
        if (i == 9) {
            isSwitchChangeColor(R.color.themedark9);
            return;
        }
        if (i == 10) {
            isSwitchChangeColor(R.color.themedark10);
            return;
        }
        if (i == 11) {
            isSwitchChangeColor(R.color.themedark11);
            return;
        }
        if (i == 12) {
            isSwitchChangeColor(R.color.themedark12);
            return;
        }
        if (i == 13) {
            isSwitchChangeColor(R.color.themedark13);
            return;
        }
        if (i == 14) {
            isSwitchChangeColor(R.color.themedark14);
            return;
        }
        if (i == 15) {
            isSwitchChangeColor(R.color.themedark15);
        } else if (i == 16) {
            isSwitchChangeColor(R.color.themedark16);
        } else if (i == 17) {
            isSwitchChangeColor(R.color.themedark17);
        }
    }

    public void textcolors(int i) {
        this.binding.titleToolbarTv.setTextColor(getResources().getColor(i));
        this.binding.enablePas.setTextColor(getResources().getColor(i));
        this.binding.changepas.setTextColor(getResources().getColor(i));
        this.binding.biometric.setTextColor(getResources().getColor(i));
        this.binding.setSecurity.setTextColor(getResources().getColor(i));
        this.binding.backArrow.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.keyImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.lockImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.patternImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.biometericimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void setbackgroundcolor(int i) {
        this.binding.parentLayout.setBackgroundColor(i);
    }
}
