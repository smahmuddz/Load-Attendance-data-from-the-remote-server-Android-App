# Load Data from Remote Server

This Application Loads Data from remote server.

## Gradle File

Add the following Line In Build.gradle inside android

```bash
useLibrary 'org.apache.http.legacy'
```

## Manifest
Add following Lines in the manifest
```python
<uses-permission android:name="android.permission.INTERNET" />
```
```
<uses-library android:name="org.apache.http.legacy" android:required="false"/>
```
