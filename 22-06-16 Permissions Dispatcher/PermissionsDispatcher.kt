// Image 1
package co.marvil.centr

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runCameraAppWithPermissionCheck()
    }

    @NeedsPermission(android.Manifest.permission.CAMERA)
    fun runCameraApp() {
        // your code to run with permission granted 
    }
}
//


// Image 2
@OnShowRationale(android.Manifest.permission.CAMERA)
fun showRationaleForCamera(request: PermissionRequest) {
    getSharedPreferences("settings", Context.MODE_PRIVATE).edit().putBoolean("hasShownCameraRationale", true).apply()
    AlertDialog.Builder(this, R.style.AlertDialogCentr)
        .setTitle(R.string.cameraRationale_title)
        .setMessage(R.string.cameraRationale_message)
        .setPositiveButton(R.string.allow) { _, _ -> request.proceed() }
        .setNeutralButton(R.string.cancel) { _, _ -> request.cancel() }.show()
}
//

// Image 3
@OnNeverAskAgain(android.Manifest.permission.CAMERA)
fun neverAskAgainForCamera() {
    AlertDialog.Builder(this, R.style.AlertDialogCentr)
        .setTitle(R.string.cameraNeverAskAgain_title)
        .setMessage(R.string.cameraNeverAskAgain_message)
        .setPositiveButton(R.string.allow) { _, _ ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        .setNeutralButton(R.string.cancel) { _, _ -> }.show()
}
//

// Image 4
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults); onRequestPermissionsResult(requestCode, grantResults)

    if ((grantResults.isEmpty()) || (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
        if (getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("hasShownCameraRationale", false)) onCameraNeverAskAgain() 
        else runCameraAppWithPermissionCheck() }
    else runCameraApp()
}
//

// POST CAPTION: PermissionsDispatcher makes it super easy to implement permissions in android apps. Here's a snippet showing you how. #coding #kotlin #AndroidDev 
// COMMENT CAPTION: What if the user denies the permission the first time they're asked? Optionally, you can add the OnShowRationale annotation to make a function that tells the user why the app needs the permission.
// COMMENT CAPTION: If the user denies the permission again, Android will no longer let the app launch the permission request menu. What do you do then? the OnNeverAskAgain annotation will run if this is the case. In this snippet, the dialog launches your app's settings page if the user clicks OK.
// COMMENT CAPTION: finally, an onRequestPermissionResult helps direct the app to the correct dialog.
// COMMENT CAPTION: Where do I use this library? Glad you asked! Head over to https://marvil.co/centr/ to get my free digital multitool. If you find any issues or have a suggestion, go to settings and click on the appropriate button. Thanks!