package com.example.androidworks.ui.screens.profile;

import android.content.Context;
import com.example.androidworks.domain.repository.ProfileRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class EditProfileViewModel_Factory implements Factory<EditProfileViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  private final Provider<Context> contextProvider;

  private EditProfileViewModel_Factory(Provider<ProfileRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public EditProfileViewModel get() {
    return newInstance(repositoryProvider.get(), contextProvider.get());
  }

  public static EditProfileViewModel_Factory create(Provider<ProfileRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    return new EditProfileViewModel_Factory(repositoryProvider, contextProvider);
  }

  public static EditProfileViewModel newInstance(ProfileRepository repository, Context context) {
    return new EditProfileViewModel(repository, context);
  }
}
