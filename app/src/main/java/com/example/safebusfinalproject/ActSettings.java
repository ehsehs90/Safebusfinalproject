package com.example.safebusfinalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class ActSettings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,
                        new MyPreferenceFragment()).commit();
    }

    // PreferenceFragment 클래스 사용
    public static class MyPreferenceFragment extends
            PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs
                    .registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                        public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
                            Log.d("tag","클릭된 Preference의 key는 "+key);
                        }
                    });
        }
        }
    }


    }
