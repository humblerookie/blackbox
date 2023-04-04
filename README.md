# Blackbox ![Latest Version](https://img.shields.io/nexus/r/dev.anvith.blackbox/blackbox?server=https%3A%2F%2Foss.sonatype.org)

Blackbox is a tool that enables your QA team to share the crash logs with you. It displays a
notification on restarting the app allowing copy and share options.

## Usage

**Step 1:**  Add the dependency

```
implementation 'dev.anvith.blackbox:blackbox:<latest-version>'
```

For release versions there is a no-op version available.

```
implementation 'dev.anvith.blackbox:blackbox-no-op:<latest-version>'
```

**Step 2:**
In your application class add the following code

	override fun onCreate(){
		 Blackbox
	            .context(this)
	            .init()
	}

**Step 3 (Android 13 Support):**

If your app targets android 13 and above you'll have to request
the [runtime notification permission](https://developer.android.com/develop/ui/views/notifications/notification-permission#declare)

	 if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU 
            && ContextCompat.checkSelfPermission(
                this, 
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
     ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
            )
    }

Check [the sample](sample/src/main/java/dev/anvith/blackbox/sample/MainActivity.kt) for complete
example


License
=======

    Copyright 2020 Anvith Bhat

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

### Demo

![Demo](assets/demo.gif)

