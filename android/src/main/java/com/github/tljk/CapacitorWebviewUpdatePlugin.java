package com.github.tljk;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.Logger;

import com.norman.webviewup.lib.UpgradeCallback;
import com.norman.webviewup.lib.WebViewUpgrade;
import com.norman.webviewup.lib.source.UpgradePackageSource;
import com.norman.webviewup.lib.source.UpgradeAssetSource;
import com.norman.webviewup.lib.source.UpgradeSource;
import com.norman.webviewup.lib.source.download.UpgradeDownloadSource;
import com.norman.webviewup.lib.util.ProcessUtils;
import com.norman.webviewup.lib.util.VersionUtils;

@CapacitorPlugin(name = "CapacitorWebviewUpdate")
public class CapacitorWebviewUpdatePlugin extends Plugin implements UpgradeCallback {

    private CapacitorWebviewUpdate implementation = new CapacitorWebviewUpdate();

    @Override
    public void onUpgradeProcess(float percent) {
        Logger.debug("WebViewUpgrade", "webView upgrade: " + percent);
    }

    @Override
    public void onUpgradeComplete() {
        Logger.debug("WebViewUpgrade", "webView upgrade success");
    }

    @Override
    public void onUpgradeError(Throwable throwable) {
        Logger.debug("WebViewUpgrade", "webView upgrade error: " + throwable.getMessage());
    }

    @Override
    public void load() {
        super.load();

        WebViewUpgrade.addUpgradeCallback(this);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("webview", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("packageName", getConfig().getString("packageName"));
        editor.putString("versionName", getConfig().getString("versionName"));
        editor.putString("url", getConfig().getString(ProcessUtils.getCurrentInstruction()));
        editor.apply();
        this.apply(getContext());
    }

    public void apply(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("webview", 0);
        String packageName = sharedPreferences.getString("packageName", null);
        String versionName = sharedPreferences.getString("versionName", null);
        String url = sharedPreferences.getString("url", null);

        if (packageName != null && versionName != null && url != null
                && WebViewUpgrade.getSystemWebViewPackageName() != null
                && VersionUtils.compareVersion( WebViewUpgrade.getSystemWebViewPackageVersion(), versionName) < 0) {
            UpgradeSource upgradeSource = new UpgradeDownloadSource(
                    context,
                    url,
                    new File(context.getFilesDir(), packageName + "/" + versionName + ".apk")
            );
            WebViewUpgrade.upgrade(upgradeSource);
        }
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
