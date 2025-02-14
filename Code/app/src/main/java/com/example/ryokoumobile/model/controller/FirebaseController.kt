package com.example.ryokoumobile.model.controller

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.ryokoumobile.model.entity.User
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

@SuppressLint("StaticFieldLeak")
object FirebaseController {
    val auth = FirebaseAuth.getInstance()

    val firestore = FirebaseFirestore.getInstance()

    private lateinit var credentialManager: CredentialManager

    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun SignIn(user: User): Task<AuthResult> {
        val newAccount = auth.createUserWithEmailAndPassword(user.email, user.password)
        newAccount.addOnSuccessListener { result ->
            val userWithId = user.copy(id = result.user!!.uid)
            DataController.user.value = userWithId
            firestore.collection("users").document(result.user!!.uid).set(userWithId)
        }
        return newAccount
    }

    fun LoginWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        val authResult = auth.signInWithEmailAndPassword(email, password)
        authResult.addOnCompleteListener {
            LoadDataUser()
        }
        return authResult
    }

    suspend fun LoginWithGoogleAccount(context: Context): Boolean {
        credentialManager = CredentialManager.create(context)
        if (isSignedIn()) {
            return true
        }
        try {
            val result = buildCredentialRequest(context)
            val handle = handleSignIn(result)
            if (auth.currentUser != null) {
                firestore.collection("users").document(auth.currentUser!!.uid).get()
                    .addOnSuccessListener { doc ->
                        if (!doc.exists()) {
                            val newUser = User(
                                id = auth.currentUser!!.uid,
                                fullName = auth.currentUser!!.displayName ?: "",
                                email = auth.currentUser!!.email ?: "",
                                numberPhone = auth.currentUser!!.phoneNumber ?: ""
                            )
                            firestore.collection("users").document(auth.currentUser!!.uid)
                                .set(newUser)
                        }
                    }
                LoadDataUser()
            }
            return handle
        } catch (e: Exception) {
            Log.e("HyuNie", "Error Login with GG: " + e.message)
            e.printStackTrace()
            if (e is CancellationException) throw e
            return false
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)

                val authResult = auth.signInWithCredential(authCredential).await()

                Log.d("HyuNie", "Email: " + authResult.user!!.email)

                return authResult.user != null
            } catch (e: GoogleIdTokenParsingException) {
                return false
            }
        } else {
            return false
        }
    }

    private suspend fun buildCredentialRequest(context: Context): GetCredentialResponse {
        val signInRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("118820860176-9kgsk4v5kklok0deii2nko95a71go2pg.apps.googleusercontent.com")
                    .setAutoSelectEnabled(false)
                    .build()
            ).build()
        return credentialManager.getCredential(context = context, request = signInRequest)
    }

    private fun LoadDataUser() {
        if (auth.currentUser != null) {
            firestore.collection("users").document(auth.currentUser!!.uid).get()
                .addOnSuccessListener { doc ->
                    DataController.user.value = doc.toObject(User::class.java)
                    DataController.user.update { it?.copy(id = auth.currentUser?.uid) }
                    DataController.loadInitDataBookedTour()
                }
        }
    }

    fun SignOut() {
        DataController.user.value = null
        auth.signOut()
    }
}