package org.apache.cordova.scanqrcode;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanQRCodePlugin extends CordovaPlugin {
  private String methodName;
  private CallbackContext callbackContext;
  private JSONObject arg;

  public JSONObject getArg() {
    return arg;
  }

  public CallbackContext getCallbackContext() {
    return callbackContext;
  }

  public String getMethodName() {
    return methodName;
  }

  @Override
  public Boolean shouldAllowBridgeAccess(String url) {
    return true;
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.methodName = action;
    this.callbackContext = callbackContext;
    this.arg = args.getJSONObject(0);
    cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        switch (getMethodName()) {
          case "openScanCodeView":
            openScanCodeView();
            break;
          default:
            Log.e("Caution", "execute: " + getMethodName());
            break;
        }
      }
    });
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (resultCode == Activity.RESULT_OK) {
      String spot = intent.getStringExtra("result");
      callbackContext.success(spot);
    }
  }

  private void openScanCodeView() {
    Intent intent = new Intent(cordova.getActivity(), ScanCodeComponent.class);
    cordova.startActivityForResult(this, intent, 0);
  }
}
