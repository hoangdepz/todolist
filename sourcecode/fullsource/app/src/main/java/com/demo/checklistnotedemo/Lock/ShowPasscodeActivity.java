package com.demo.checklistnotedemo.Lock;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.itsxtt.patternlock.PatternLockView;
import com.demo.checklistnotedemo.ActivityMain;
import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.databinding.ActivityShowPasscodeBinding;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class ShowPasscodeActivity extends FragmentActivity {
    private static final String KEY_NAME = "your_key_name";
    ActivityShowPasscodeBinding binding;
    private Cipher cipher;
    private FingerprintManager fingerprintManager;
    boolean finishAffiniy;
    private KeyStore keyStore;
    int theme;
    private boolean confirmation = false;
    public String passcode = "";


    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityShowPasscodeBinding inflate = ActivityShowPasscodeBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());


        settheme();
        boolean booleanExtra = getIntent().getBooleanExtra("change", false);
        this.finishAffiniy = getIntent().getBooleanExtra("passcode", false);
        this.confirmation = booleanExtra;
        final boolean booleanExtra2 = getIntent().getBooleanExtra("question", false);
        if (booleanExtra) {
            this.binding.headingTv.setText(R.string.enter_previous_password);
        }
        if (SharedPrefrence.getFingerPrintEnabled(this)) {
            fingerPrint();
        } else {
            this.binding.finger.setVisibility(View.GONE);
        }
        if (SharedPrefrence.getSavedPattern(this).equals("") || !SharedPrefrence.getPasswordSwitch(this)) {
            startActivity(new Intent(this, (Class<?>) ActivityMain.class));
            finish();
        }
        this.binding.forgotPasswordTv.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(ShowPasscodeActivity.this, (Class<?>) PasswordQuestionsActivity.class);
                intent.putExtra("change", ShowPasscodeActivity.this.confirmation);
                if (ShowPasscodeActivity.this.getIntent().getBooleanExtra("question", false)) {
                    intent.putExtra("question", booleanExtra2);
                }
                ShowPasscodeActivity.this.startActivity(intent);
                if (ShowPasscodeActivity.this.getIntent().getBooleanExtra("question", false)) {
                    ShowPasscodeActivity.this.finish();
                }
            }
        });
        this.binding.patternLockView.setOnPatternListener(new PatternLockView.OnPatternListener() {
            @Override 
            public void onProgress(ArrayList<Integer> arrayList) {
            }

            @Override 
            public void onStarted() {
                ShowPasscodeActivity.this.binding.headingTv.setText(R.string.draw_your_unlock_pattern);
                ShowPasscodeActivity.this.binding.headingTv.setTextColor(-1);
            }

            @Override 
            public boolean onComplete(ArrayList<Integer> arrayList) {
                ShowPasscodeActivity showPasscodeActivity = ShowPasscodeActivity.this;
                showPasscodeActivity.passcode = showPasscodeActivity.patternToString(arrayList);
                if (!ShowPasscodeActivity.this.passcode.equals(SharedPrefrence.getSavedPattern(ShowPasscodeActivity.this))) {
                    ShowPasscodeActivity.this.passcode = "";
                    ShowPasscodeActivity.this.binding.headingTv.setText(R.string.pattern_do_not_match);
                    ShowPasscodeActivity.this.binding.headingTv.setTextColor(ShowPasscodeActivity.this.getResources().getColor(R.color.redcolor));
                    new Handler().postDelayed(new Runnable() { 
                        @Override 
                        public void run() {
                            ShowPasscodeActivity.this.binding.patternLockView.clearFocus();
                        }
                    }, 1000L);
                } else if (!ShowPasscodeActivity.this.getIntent().getBooleanExtra("passcode", false)) {
                    LockHolder.getInstance().setboolean(false);
                    if (!ShowPasscodeActivity.this.getIntent().getBooleanExtra("question", false)) {
                        if (!ShowPasscodeActivity.this.confirmation) {
                            ShowPasscodeActivity.this.startActivity(new Intent(ShowPasscodeActivity.this, (Class<?>) ActivityMain.class));
                            ShowPasscodeActivity.this.binding.patternLockView.setFocusable(true);
                        } else {
                            ShowPasscodeActivity.this.startActivity(new Intent(ShowPasscodeActivity.this, (Class<?>) AddPasswordActivity.class));
                        }
                    } else {
                        Intent intent = new Intent(ShowPasscodeActivity.this, (Class<?>) PasswordQuestionsActivity.class);
                        intent.putExtra("edit", true);
                        intent.putExtra("changeQuestion", true);
                        ShowPasscodeActivity.this.startActivity(intent);
                    }
                    ShowPasscodeActivity.this.finish();
                } else {
                    ShowPasscodeActivity.this.setResult(-1, new Intent());
                    LockHolder.getInstance().setboolean(false);
                    ShowPasscodeActivity.this.finish();
                }
                return true;
            }
        });
    }

    public void fingerPrint() {
        this.binding.finger.setVisibility(View.VISIBLE);
        generateKey();
        initCipher();
        startFingerprintAuthentication();
    }


    @Override 
    public void onResume() {
        super.onResume();
        if (SharedPrefrence.getFingerPrintEnabled(this)) {
            fingerPrint();
        } else {
            this.binding.finger.setVisibility(View.GONE);
        }
    }

    private void startAuth(FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(this, "android.permission.USE_FINGERPRINT") != 0) {
            return;
        }
        this.fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new  FingerprintManager.AuthenticationCallback() {
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                super.onAuthenticationSucceeded(authenticationResult);
                if (!ShowPasscodeActivity.this.getIntent().getBooleanExtra("passcode", false)) {
                    if (!ShowPasscodeActivity.this.getIntent().getBooleanExtra("question", false)) {
                        if (!ShowPasscodeActivity.this.confirmation) {
                            ShowPasscodeActivity.this.startActivity(new Intent(ShowPasscodeActivity.this, (Class<?>) ActivityMain.class));
                        } else {
                            ShowPasscodeActivity.this.startActivity(new Intent(ShowPasscodeActivity.this, (Class<?>) AddPasswordActivity.class));
                        }
                    } else {
                        Intent intent = new Intent(ShowPasscodeActivity.this, (Class<?>) PasswordQuestionsActivity.class);
                        intent.putExtra("edit", true);
                        intent.putExtra("changeQuestion", true);
                        ShowPasscodeActivity.this.startActivity(intent);
                    }
                    ShowPasscodeActivity.this.finish();
                } else {
                    ShowPasscodeActivity.this.setResult(-1, new Intent());
                    LockHolder.getInstance().setboolean(false);
                    ShowPasscodeActivity.this.finish();
                }
                ShowPasscodeActivity.this.binding.finger.setImageTintList(ColorStateList.valueOf(ShowPasscodeActivity.this.getResources().getColor(R.color.white)));
            }

            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                ShowPasscodeActivity.this.binding.finger.setImageTintList(ColorStateList.valueOf(ShowPasscodeActivity.this.getResources().getColor(R.color.redcolor)));
            }
        }, null);
    }

    private void startFingerprintAuthentication() {
        boolean isHardwareDetected;
        boolean hasEnrolledFingerprints;

        this.fingerprintManager = (android.hardware.fingerprint.FingerprintManager) ContextCompat.getSystemService(this, FingerprintManager.class);
        if (this.fingerprintManager != null) {
            isHardwareDetected = this.fingerprintManager.isHardwareDetected();
            if (isHardwareDetected) {
                hasEnrolledFingerprints = this.fingerprintManager.hasEnrolledFingerprints();
                if (hasEnrolledFingerprints) {
                    startAuth(new FingerprintManager.CryptoObject(cipher));
                    return;
                }
            }
        }
        Toast.makeText(this, getString(R.string.your_device_has_no_fingerprint_sensor), Toast.LENGTH_SHORT).show();
    }

    private void generateKey() {
        KeyGenParameterSpec.Builder blockModes;
        KeyGenParameterSpec.Builder userAuthenticationRequired;
        KeyGenParameterSpec.Builder encryptionPaddings;
        KeyGenParameterSpec build;
        try {
            this.keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            this.keyStore.load(null);
            blockModes = new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC);
            userAuthenticationRequired = blockModes.setUserAuthenticationRequired(true);
            encryptionPaddings = userAuthenticationRequired.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            build = encryptionPaddings.build();
            keyGenerator.init(build);
            keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCipher() {
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            this.keyStore.load(null);
            this.cipher.init(1, (SecretKey) this.keyStore.getKey(KEY_NAME, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String patternToString(ArrayList<Integer> arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }

    @Override 
    public void onBackPressed() {
        if (this.finishAffiniy) {
            finishAffinity();
            return;
        }
        setResult(-1, new Intent());
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    public void settheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 1 || i == 0) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 2) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 3) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 4) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 5) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 6) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 7) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_7));
            return;
        }
        if (i == 8) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_8));
            return;
        }
        if (i == 9) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_9));
            return;
        }
        if (i == 10) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_10));
            return;
        }
        if (i == 11) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_11));
            return;
        }
        if (i == 12) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_12));
            return;
        }
        if (i == 13) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_13));
            return;
        }
        if (i == 14) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_14));
            return;
        }
        if (i == 15) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_15));
        } else if (i == 16) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_17));
        }
    }

    private void setbackgroundcolor(int i) {
        this.binding.mainActivity.setBackgroundColor(i);
    }
}
