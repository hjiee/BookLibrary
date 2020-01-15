package com.hyden.booklibrary.view.login

import android.content.Intent
import android.os.Bundle
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
import com.hyden.booklibrary.view.MainActivity
import com.hyden.ext.moveToActivity
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogW
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by inject<LoginViewModel>()
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
            btnLogin.setOnClickListener {
                loginViewModel.loing()
            }
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
                    LogW("Google sign in failed")
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
                    // If sign in fails, display a message to the user.
                    LogW("signInWithCredential:failure")
//                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    private fun goMain() {
        moveToActivity(Intent(this@LoginActivity,MainActivity::class.java))
        finish()
    }
}