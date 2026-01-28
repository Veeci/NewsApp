package com.example.newsapp.compose.screens.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.newsapp.R
import com.example.newsapp.util.WebviewUIState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(url: String) {
    val context = LocalContext.current
    var uiState by remember { mutableStateOf<WebviewUIState>(WebviewUIState.Loading) }

    AndroidView(
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        uiState = WebviewUIState.Loading
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        uiState = WebviewUIState.Success
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.e("CustomWebviewClient", "Error loading URL: ${error?.description}")
                        uiState = WebviewUIState.Error(error?.description.toString())
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: WebResourceResponse?
                    ) {
                        super.onReceivedHttpError(view, request, errorResponse)
                        Log.e(
                            "CustomWebviewClient",
                            "Http error loading URL: ${errorResponse?.reasonPhrase}"
                        )
                        uiState = WebviewUIState.Error(errorResponse?.reasonPhrase.toString())
                    }

                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        super.onReceivedSslError(view, handler, error)
                        Log.e("CustomWebviewClient", "Ssl error loading URL: ${error?.url}")
                        uiState = WebviewUIState.Error(error?.url.toString())
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString().orEmpty()
                        return !url.startsWith("http")
                    }
                }
            }
        },
        update = { webview -> webview.loadUrl(url) },
    )

    Crossfade(
        targetState = uiState,
        label = "WebViewStateCrossfade"
    ) { state ->
        when (state) {
            WebviewUIState.Loading -> LoadingWebviewUI()
            is WebviewUIState.Error -> ErrorWebviewUI()
            WebviewUIState.Success -> Unit
        }
    }

}

@Composable
private fun LoadingWebviewUI() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_loading_webview),
        onRetry = { failCount, previousException ->
            Log.e("LottieAnimation", "Error loading animation: $previousException")
            failCount < 3
        }
    )

    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
    )
}

@Composable
private fun ErrorWebviewUI() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_error_webview),
        onRetry = { failCount, previousException ->
            Log.e("LottieAnimation", "Error loading animation: $previousException")
            failCount < 3
        }
    )
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
    )
}