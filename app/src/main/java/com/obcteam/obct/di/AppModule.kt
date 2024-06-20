package com.obcteam.obct.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.BuildConfig
import com.obcteam.obct.data.OBCTRemoteMediator
import com.obcteam.obct.data.local.models.OBCTDatabase
import com.obcteam.obct.data.local.models.PredictionHistoryEntity
import com.obcteam.obct.data.local.models.PredictionHistoryWithReccomendation
import com.obcteam.obct.data.remote.AuthInterceptor
import com.obcteam.obct.data.remote.OBCTService
import com.obcteam.obct.data.repository.FirebaseAuthRepository
import com.obcteam.obct.data.repository.OBCTRepositoryImpl
import com.obcteam.obct.domain.repository.AuthRepository
import com.obcteam.obct.domain.repository.OBCTRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOBCTDatabase(@ApplicationContext context: Context): OBCTDatabase {
        return Room.databaseBuilder(
            context, OBCTDatabase::class.java, "obct_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesOBCTService(authInterceptor: AuthInterceptor): OBCTService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(OBCTService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(
        firebaseAuth: FirebaseAuth,
        obctService: OBCTService
    ): AuthRepository {
        return FirebaseAuthRepository(firebaseAuth, obctService)
    }

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }


    @Provides
    @Singleton
    fun providesOBCTRepository(obctService: OBCTService): OBCTRepository {
        return OBCTRepositoryImpl(obctService)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideStoryPager(
        storyDatabase: OBCTDatabase, obctRepository: OBCTRepository
    ): Pager<Int, PredictionHistoryWithReccomendation> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = OBCTRemoteMediator(obctRepository, storyDatabase),
            pagingSourceFactory = { storyDatabase.dao.pagingSource() }
        )
    }

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "AIzaSyAw5l1Zstt0hK9X7WTO_gbW0jtgGrKfaSc",
            systemInstruction = Content(
                role = "system",
                parts = listOf(
                    com.google.ai.client.generativeai.type.TextPart(
                        text = "Hello! I'm your personal health assistant. How can I help you today?"
                    ),
                    com.google.ai.client.generativeai.type.TextPart(
                        text = "I can help you with your diet, exercise, and other health-related queries."
                    ),
                    com.google.ai.client.generativeai.type.TextPart(
                        text = "You can ask me anything!"
                    )
                )
            )
        )
    }

}