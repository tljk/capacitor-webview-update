package com.github.tljk;

import java.io.File;
import java.util.Objects;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.norman.webviewup.lib.UpgradeCallback;
import com.norman.webviewup.lib.WebViewUpgrade;
import com.norman.webviewup.lib.source.UpgradePackageSource;
import com.norman.webviewup.lib.source.UpgradeAssetSource;
import com.norman.webviewup.lib.source.UpgradeSource;
import com.norman.webviewup.lib.source.download.UpgradeDownloadSource;
import com.norman.webviewup.lib.util.ProcessUtils;
import com.norman.webviewup.lib.util.VersionUtils;

import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(name = "CapacitorWebviewUpdate")
public class CapacitorWebviewUpdatePlugin extends Plugin implements UpgradeCallback {

    private UpgradeSource upgradeSource;
    private String sourceType;
    private String packageName;
    private String versionName;
    private String url;

    @Override
    public void onUpgradeProcess(float percent) {
        JSObject ret = new JSObject();
        ret.put("percent", percent);
        notifyListeners("upgradeProcess", ret);
    }

    @Override
    public void onUpgradeComplete() {
        JSObject ret = new JSObject();
        notifyListeners("upgradeComplete", ret);
    }

    @Override
    public void onUpgradeError(Throwable throwable) {
        JSObject ret = new JSObject();
        ret.put("message", throwable.getMessage());
        notifyListeners("upgradeError", ret);
    }

    @Override
    public void load() {
        super.load();

        WebViewUpgrade.addUpgradeCallback(this);
        this.sourceType = getConfig().getString("sourceType");
        this.packageName = getConfig().getString("packageName");
        this.versionName = getConfig().getString("versionName");
        this.url = getConfig().getString(ProcessUtils.getCurrentInstruction());
    }

    public void initWebview(Context context) { // call before webview init
        SharedPreferences sharedPreferences = context.getSharedPreferences("webview", 0);
        this.sourceType = sharedPreferences.getString("sourceType", null);
        this.packageName = sharedPreferences.getString("packageName", null);
        this.versionName = sharedPreferences.getString("versionName", null);
        this.url = sharedPreferences.getString("url", null);

        if (Objects.equals(sourceType, "remote") && this.url != null && this.packageName != null && this.versionName != null) {
            this.upgradeSource = new UpgradeDownloadSource(
                context,
                this.url,
                new File(context.getFilesDir(), this.packageName + "/" + this.versionName + ".apk")
            );
        } else if (Objects.equals(sourceType, "local") && this.url != null && this.packageName != null && this.versionName != null) {
            this.upgradeSource = new UpgradeAssetSource(
                context,
                this.url,
                new File(context.getFilesDir(), this.packageName + "/" + this.versionName + ".apk")
            );
        } else if (Objects.equals(sourceType, "package") && this.packageName != null) {
            this.upgradeSource = new UpgradePackageSource(
                context,
                this.packageName
            );
        } else {
            return;
        }
        WebViewUpgrade.upgrade(this.upgradeSource);
    }

    @PluginMethod
    public void getSystemWebView(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("packageName", WebViewUpgrade.getSystemWebViewPackageName());
        ret.put("versionName", WebViewUpgrade.getSystemWebViewPackageVersion());
        call.resolve(ret);
    }

    @PluginMethod
    public void getUpgradeWebView(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("packageName", WebViewUpgrade.getUpgradeWebViewPackageName());
        ret.put("versionName", WebViewUpgrade.getUpgradeWebViewVersion());
        call.resolve(ret);
    }

    @PluginMethod
    public void getCurrentInstruction(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("instruction", ProcessUtils.getCurrentInstruction());
        call.resolve(ret);
    }

    @PluginMethod
    public void compareVersion(PluginCall call) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("webview", 0);
        String currentVersionName = sharedPreferences.getString("versionName", null);
        String versionName = call.getString("versionName");
        if (currentVersionName == null) {
            currentVersionName = WebViewUpgrade.getSystemWebViewPackageVersion();
            if (currentVersionName == null) {
                call.reject("Invalid current version");
                return;
            }
        }
        if (versionName == null) {
            versionName = this.versionName;
            if (versionName == null) {
                call.reject("Invalid version");
                return;
            }
        }
        Number result = VersionUtils.compareVersion( currentVersionName, versionName);
        JSObject ret = new JSObject();
        ret.put("result", result);
        call.resolve(ret);
    }

    @PluginMethod
    public void upgradeWebView(PluginCall call) {
        String sourceType = call.getString("sourceType");
        JSONObject source = call.getObject("source");
        if (sourceType != null) {
            this.sourceType = sourceType;
        }
        if (source != null){
            try {
                this.packageName = source.getString("packageName");
                this.versionName = source.getString("versionName");
                this.url = source.getString("url");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.equals(this.sourceType, "remote")
                && this.url != null && this.packageName != null && this.versionName != null) {
            this.upgradeSource = new UpgradeDownloadSource(
                getContext(),
                this.url,
                new File(getContext().getFilesDir(), this.packageName + "/" + this.versionName + ".apk")
            );
        } else if (Objects.equals(this.sourceType, "local")
                && this.url != null && this.packageName != null && this.versionName != null) {
            this.upgradeSource = new UpgradeAssetSource(
                getContext(),
                this.url,
                new File(getContext().getFilesDir(), this.packageName + "/" + this.versionName + ".apk")
            );
        } else if (Objects.equals(this.sourceType, "package")
                && this.packageName != null) {
            this.upgradeSource = new UpgradePackageSource(
                getContext(),
                this.packageName
            );
        } else {
            call.reject("Invalid source type or source");
            return;
        }
        WebViewUpgrade.upgrade(this.upgradeSource);
        call.resolve();
    }

    @PluginMethod
    public void deleteWebView(PluginCall call) {
        call.reject("Not implemented");
    }

    @PluginMethod
    public void applyWebView(PluginCall call) {
        if (this.sourceType == null || this.packageName == null || this.versionName == null || this.url == null) {
            call.reject("Invalid source type or source");
            return;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("webview", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sourceType", this.sourceType);
        editor.putString("packageName", this.packageName);
        editor.putString("versionName", this.versionName);
        editor.putString("url", this.url);
        editor.apply();
        call.resolve();
    }
}
