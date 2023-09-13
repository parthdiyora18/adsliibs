-keep class androidx.appcompat.widget.** { *; }
-keep class androidx.fragment.app.Fragment { *; }
-keep class io.plaidapp.data.api.dribbble.model.** { *; }
-repackageclasses ''
-allowaccessmodification
-adaptresourcefilenames    **.properties,**.gif,**.jpg
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF
-adaptresourcefilenames **.xsd,**.wsdl,**.xml,**.properties,**.gif,**.jpg,**.png
-adaptresourcefilecontents **.xml

-dontwarn com.alexvasilkov.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn rx.**
-keepclasseswithmembernames class * {
    native <methods>;
}
-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepattributes LocalVariableTable, LocalVariableTypeTable

-keepattributes *Annotation*, Signature, Exception

 #### -- OkHttp --
 -dontwarn com.squareup.okhttp.internal.**

 #### -- Apache Commons --
 -dontwarn org.apache.commons.logging.**
 -dontwarn org.apache.**

# startapp rule
-keep class org.apache.commons.logging.**
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-keep class com.google.android.gms.internal.** { *; }
-ignorewarnings