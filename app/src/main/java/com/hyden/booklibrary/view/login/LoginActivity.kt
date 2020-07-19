package com.hyden.booklibrary.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityLoginBinding
import com.hyden.booklibrary.util.getInitPreferences
import com.hyden.booklibrary.util.setInitPreferences
import com.hyden.booklibrary.view.main.MainActivity
import com.hyden.booklibrary.view.splash.SplashActivity.Companion.LOGIN_START
import com.hyden.ext.moveToActivity
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE
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

    override fun onPause() {
        super.onPause()
    }

    override fun initBind() {
        if(googleAuth.currentUser != null) { goMain() }
        binding.apply {
            vm = loginViewModel
            btnLoginGoogle.setOnClickListener {
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
                    LogE("Google sign in failed : $e")
                    Toast.makeText(this@LoginActivity, String.format(getString(R.string.login_fail),getString(R.string.google)), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun googleAuthWithGoogle(acct: GoogleSignInAccount) {
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
            }
    }



    private fun goMain() {
        if(!getInitPreferences()) {
            setInitPreferences(true)
            loginViewModel.saveUser()
            loginViewModel.myBook()
            loginViewModel.succesInit.observe(this@LoginActivity, Observer {
                move()
            })
        } else {
            move()
        }

    }
    private fun move() {
        moveToActivity(Intent(this@LoginActivity, MainActivity::class.java))
        setResult(LOGIN_START)
        finish()
    }
}