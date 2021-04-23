package ru.samsung.itschool.mdev.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EncryptedPreferenceDataStore prefs = null;
        try {
            prefs = EncryptedPreferenceDataStore.getInstance(this);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("RRR", prefs.getString("signature", ""));
        Log.d("RRR", prefs.getString("reply", ""));
        Log.d("RRR", Boolean.toString(prefs.getBoolean("sync", true)));
        Log.d("RRR", Boolean.toString(prefs.getBoolean("attachment", true)));

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            PreferenceManager preferenceManager = getPreferenceManager();
            try {
                preferenceManager.setPreferenceDataStore(EncryptedPreferenceDataStore.getInstance(getContext()));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}