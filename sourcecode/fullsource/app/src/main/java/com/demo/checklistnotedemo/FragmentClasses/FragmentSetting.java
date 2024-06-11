package com.demo.checklistnotedemo.FragmentClasses;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.demo.checklistnotedemo.ActivityMain;
import com.demo.checklistnotedemo.ActivityProfile;
import com.demo.checklistnotedemo.ActivityReminder;
import com.demo.checklistnotedemo.ActivityTheme;
import com.demo.checklistnotedemo.CategoryActivity;
import com.demo.checklistnotedemo.ClearData;
import com.demo.checklistnotedemo.Lock.PasswordOptionsActivity;
import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.databinding.SettingFragmentBinding;


public class FragmentSetting extends Fragment {
    SettingFragmentBinding binding;

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.binding = SettingFragmentBinding.inflate(layoutInflater, viewGroup, false);
        settheme();
        ShowImage();
        this.binding.firstcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) ActivityProfile.class));
            }
        });
        this.binding.secondcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) ActivityTheme.class));
            }
        });
        this.binding.categoryCard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) CategoryActivity.class));
            }
        });
        this.binding.lockcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) PasswordOptionsActivity.class));
            }
        });
        this.binding.thirdcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                new AlertDialog.Builder(FragmentSetting.this.getActivity()).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClearData.getInstance().clearApplicationData(FragmentSetting.this.getActivity());
                        FragmentSetting.this.removeData();
                        FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) ActivityMain.class));
                        FragmentSetting.this.getActivity().finishAffinity();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        this.binding.fourthcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentSetting.this.startActivity(new Intent(FragmentSetting.this.getActivity(), (Class<?>) ActivityReminder.class));
            }
        });
        this.binding.fifthcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                FragmentSetting.this.m360x1d3e2495(view);
            }
        });
        this.binding.sixthcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                FragmentSetting.this.m361x561e8534(view);
            }
        });
        this.binding.Eightcard.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                FragmentSetting.this.m362x8efee5d3(view);
            }
        });
        return this.binding.getRoot();
    }

    
    
    public  void m360x1d3e2495(View view) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "SUBJECT");
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName());
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_us)));
    }

    
    
    public  void m361x561e8534(View view) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + requireActivity().getPackageName())));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())));
        }
    }

    
    
    public  void m362x8efee5d3(View view) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://doc-hosting.flycricket.io/smart-tech-labs-ind/1d074628-53cd-4ebb-8776-cf7e591c2833/privacy")));
    }

    
    


    @Override 
    public void onDestroy() {
        super.onDestroy();
    }

    public void removeData() {
        SharedPreferences.Editor edit = getActivity().getSharedPreferences("my_prefs2", 0).edit();
        edit.putString("my_key2", null);
        edit.apply();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_prefs4", 0);
        SharedPreferences.Editor edit2 = getActivity().getSharedPreferences("my_prefs3", 0).edit();
        SharedPreferences.Editor edit3 = sharedPreferences.edit();
        edit2.putString("my_key3", "");
        edit3.putString("my_key4", "");
        edit2.apply();
        edit3.apply();
        SharedPreferences.Editor edit4 = getActivity().getSharedPreferences("my_prefs", 0).edit();
        edit4.putInt("my_key", 1);
        edit4.apply();
        SharedPreferences.Editor edit5 = getActivity().getSharedPreferences("my_prefs", 0).edit();
        edit5.putInt("last_selected_position", 0);
        edit5.apply();
    }

    public void ShowImage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_prefs2", 0);
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("my_prefs3", 0);
        SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences("my_prefs4", 0);
        sharedPreferences.getString("my_key2", "");
        String string = sharedPreferences2.getString("my_key3", "");
        sharedPreferences3.getString("my_key4", "");
        if (string.equals("")) {
            return;
        }
        this.binding.profiletxt.setText(string);
    }

    public void textImgcolor(int i) {
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.profiletxt.setTextColor(getResources().getColor(i));
        this.binding.themetxt.setTextColor(getResources().getColor(i));
        this.binding.deletetxt.setTextColor(getResources().getColor(i));
        this.binding.remindertxt.setTextColor(getResources().getColor(i));
        this.binding.sharetxt.setTextColor(getResources().getColor(i));
        this.binding.ratetxt.setTextColor(getResources().getColor(i));
        this.binding.privacytxt.setTextColor(getResources().getColor(i));
        this.binding.locktxt.setTextColor(getResources().getColor(i));
        this.binding.categorytxt.setTextColor(getResources().getColor(i));
        this.binding.profileimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.themeimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.deleteimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.reminderimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.shareimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.rateimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.privacyimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.lockimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.categoryimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void arrow_txt(int i, int i2) {
        this.binding.arrowProfile.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowTheme.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowLock.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowDelete.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowReminder.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowShare.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowRate.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowPrivacy.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.arrowCategory.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.mydata.setTextColor(getResources().getColor(i2));
        this.binding.reminders.setTextColor(getResources().getColor(i2));
        this.binding.others.setTextColor(getResources().getColor(i2));
        this.binding.personal.setTextColor(getResources().getColor(i2));
    }

    public void settheme() {
        FragmentActivity activity = getActivity();
        getActivity();
        int i = activity.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.green, R.color.green);
        } else if (i == 2) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.pink, R.color.pink);
        } else if (i == 3) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.blue, R.color.blue);
        } else if (i == 4) {
            arrow_txt(R.color.purple, R.color.purple);
            textImgcolor(R.color.black);
        } else if (i == 5) {
            textImgcolor(R.color.white);
            arrow_txt(R.color.purple, R.color.purple);
        } else if (i == 6) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.parrot, R.color.parrot);
        } else if (i == 7) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark7, R.color.black);
        } else if (i == 8) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark8, R.color.black);
        } else if (i == 9) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark9, R.color.black);
        } else if (i == 10) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark10, R.color.black);
        } else if (i == 11) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark11, R.color.black);
        } else if (i == 12) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark12, R.color.black);
        } else if (i == 13) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark13, R.color.black);
        } else if (i == 14) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark14, R.color.black);
        } else if (i == 15) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark15, R.color.white);
            this.binding.pageTitle.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 16) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark16, R.color.white);
            this.binding.pageTitle.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 17) {
            textImgcolor(R.color.black);
            arrow_txt(R.color.themedark17, R.color.white);
            this.binding.pageTitle.setTextColor(getResources().getColor(R.color.white));
        }
        if (i == 5) {
            cardBackground(R.color.light_black);
        } else {
            cardBackground(R.color.white);
        }
    }

    public void cardBackground(int i) {
        this.binding.firstcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.secondcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.lockcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.thirdcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.fourthcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.fifthcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.sixthcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.Eightcard.setCardBackgroundColor(getResources().getColor(i));
        this.binding.categoryCard.setCardBackgroundColor(getResources().getColor(i));
    }
}
