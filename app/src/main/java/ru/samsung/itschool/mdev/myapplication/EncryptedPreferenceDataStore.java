package ru.samsung.itschool.mdev.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;
import androidx.security.crypto.EncryptedSharedPreferences;

import java.util.Set;

public class EncryptedPreferenceDataStore extends PreferenceDataStore {

    private static final String CONFIG_FILE_NAME = "SecureSharedPreferences";
    private static final String CONFIG_MASTER_KEY_ALIAS = "KeyAlias";
    private static EncryptedPreferenceDataStore mInstance;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private EncryptedPreferenceDataStore(Context context) {
        try {
            mContext = context;
            mSharedPreferences = EncryptedSharedPreferences.create(
                    CONFIG_FILE_NAME,
                    CONFIG_MASTER_KEY_ALIAS,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, //for encrypting Keys
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM ////for encrypting Values
            );
        } catch (Exception e) {
            // Fallback
            mSharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        }
    }

    public static EncryptedPreferenceDataStore getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new EncryptedPreferenceDataStore(ctx);
        }
        return mInstance;
    }

    @Override
    public void putString(String key, @Nullable String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void putStringSet(String key, @Nullable Set<String> values) {
        mSharedPreferences.edit().putStringSet(key, values).apply();
    }

    @Override
    public void putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public void putLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public void putFloat(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public void putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return mSharedPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }


}