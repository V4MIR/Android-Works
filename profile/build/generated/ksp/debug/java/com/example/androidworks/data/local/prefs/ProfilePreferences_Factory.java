package com.example.androidworks.data.local.prefs;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ProfilePreferences_Factory implements Factory<ProfilePreferences> {
  private final Provider<Context> contextProvider;

  private ProfilePreferences_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ProfilePreferences get() {
    return newInstance(contextProvider.get());
  }

  public static ProfilePreferences_Factory create(Provider<Context> contextProvider) {
    return new ProfilePreferences_Factory(contextProvider);
  }

  public static ProfilePreferences newInstance(Context context) {
    return new ProfilePreferences(context);
  }
}
