# Blackbox ![Version](https://jitpack.io/v/humblerookie/blackbox.svg)

Blackbox is a tool that enables your QA team to share the crash logs with you. It displays a notification on restarting the app allowing copy and share options.

## Usage
**Step 1:** Add jitpack in your root build.gradle at the end of repositories:


    allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }

**Step 2:**  Add the dependency

`implementation 'com.github.humblerookie.blackbox:blackbox-main:<version>'`


For release versions there is a no-op version available as well

`implementation 'com.github.humblerookie.blackbox:blackbox-no-op:<version>'`

**Step 3:**
In your application class add the following code

	override fun onCreate(){
		 Blackbox
	            .context(this)
	            .init()
	}



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

