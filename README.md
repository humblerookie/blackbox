# Blackbox

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


	dependencies {
		implementation 'com.github.humblerookie:blackbox:0.0.1'
	}

### Feature Additions
- Allow multiple crash log storage
- Provide an activity to view all crash logs

### Demo
![Demo](assets/demo.gif)