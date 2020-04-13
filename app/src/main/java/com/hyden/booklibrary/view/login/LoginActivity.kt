package com.hyden.booklibrary.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityLoginBinding
import com.hyden.booklibrary.util.setUserNickName
import com.hyden.booklibrary.view.MainActivity
import com.hyden.ext.moveToActivity
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE
import com.hyden.util.LogUtil.LogW
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModel<LoginViewModel>()
    private val googleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient by lazy { GoogleSignIn.getClient(this,googleSignInOptions) }
    private val googleAuth by lazy { FirebaseAuth.getInstance() }
    private val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initBind() {
        if(googleAuth.currentUser != null) { goMain() }
        binding.apply {
            btnGoogleLogin.setOnClickListener {
                startActivityForResult(googleSignInClient.signInIntent,RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    googleAuthWithGoogle(account!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    LogW("Google sign in failed : $e")
                    Toast.makeText(this@LoginActivity, "google sign fail", Toast.LENGTH_SHORT).show()
                    // ...
                }
            }
        }
    }

    private fun googleAuthWithGoogle(acct: GoogleSignInAccount) {
        LogD("googleAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        googleAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    loginViewModel.googleSignIn()
                    goMain()
                    LogD("signInWithCredential:success")
                } else {
                    LogE("signInWithCredential:failure")
                }

                // ...
            }
    }

    private fun goMain() {
        loginViewModel.saveUser()
        moveToActivity(Intent(this@LoginActivity,MainActivity::class.java))
        finish()
    }
}