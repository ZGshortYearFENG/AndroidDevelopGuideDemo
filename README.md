# Deep links
  Deep links是将用户直接带到您应用程序中特定内容的URL。在Android中，您可以通过添加意图过滤器并从传入的意图中提取数据来建立深层链接，以将用户带入正确的活动。 但是，如果用户设备上安装的其他应用程序可以处理相同的意图，则用户可能不会直接进入您的应用程序。例如，单击来自银行的电子邮件中的URL可能会导致一个对话框，询问用户是使用浏览器还是使用银行自己的应用程序打开链接。而Android App Link是基于DeepLink，Android 6.0（API级别23）及更高版本上的Android App链接允许应用程序将自身指定为给定类型的链接的默认处理程序。如果用户不希望该应用程序成为默认处理程序，则可以从其设备的系统设置中覆盖此行为。
  Android App Links的好处就是，安全且特定：Android应用程序链接使用HTTP URL链接到您拥有的网站域，因此没有其他应用程序可以使用您的链接。 Android App链接的要求之一是，您可以通过我们的网站关联方法之一来验证您对域的所有权。无缝的用户体验：由于Android应用程序链接使用单个HTTP URL来访问您的网站和应用程序中的相同内容，因此未安装该应用程序的用户只需访问您的网站而不是该应用程序即可。

学习一个知识需要了解背后的知识背景，移动用户86%的时间 （Flurry 2015数据）都是在各个APP中。然而，各个移动App就像大海中的一座座岛屿，虽然都生活在一个海洋中（Android系统或iOS)，但是他们之间通常是老死不相往来。举例来说，在微信应用中，用户基本上就没有机会打开第三方应用APP，只能通过Web/浏览器方式提供受限的互通。
每次在百度搜好吃的，搜到点评的结果后，怎么就不能直接跳到app里呢。现在从全家桶、Google搜索关于我们自己app的内容，往往只能搜到一些相关介绍和下载的链接，然后我们就中断了。而在Web世界，搜索后我们可以直接打开网页查看内容，相比起来体验实在是差了太多。难道我们就不能直接点击跳到手机上已经安装的app上吗？或者干脆直接跳到某个页面？
真实的用户需求是什么样的呢？ 例如，用户在朋友圈中，看到关于一个饭店文章的时候，用户可以很方便打开大众点评应用看评论，直接打开美团查看折扣券，直接打开叫车软件前往该地点。 在信息流看到推荐的商品，能够直接打开淘宝/京东App查看宝贝详情。但是很不幸，目前这些应用孤岛之间都是通过Web进行连接的，通过浏览器的WebView，进行内容跳转，缺少原生App体验。
deeplinks的出现就能让用户点击页面的时候判断app是否被安装，当用户点击链接的时候判断APP是否安装，如果用户没有安装时，引导用户跳转到应用商店下载应用，如果用户已经安装，当用户点击链接时和用户启动APP时，直接打开相应的指定页面。

## 创建Deeplinks步骤：

### 1/ 添加intent-filter

action: 指定ACTION_VIEW

data: 添加一个或多个<data>标记，每个标记代表一种解析为活动的URI格式。至少，<data>标签必须包含android：scheme属性。 您可以添加更多属性，以进一步优化活动接受的URI类型。例如，您可能有多个活动接受相似的URI，但仅基于路径名而有所不同。在这种情况下，请使用android：path属性或其pathPattern或pathPrefix变体来区分系统应针对不同URI路径打开的活动

category: BROWSABLE，为了使intent-filter可以从Web浏览器访问，这是必需的。没有它，单击浏览器中的链接将无法解析到您的应用。 DEFAULT，这使您的应用可以响应隐式意图。否则，仅在意图指定您的应用程序组件名称时才能启动活动。

````
<activity ...>
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="http"
              android:host="www.example.com"
              android:pathPrefix="/gizmos" />
        <!-- note that the leading "/" is required for pathPrefix-->
    </intent-filter>
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="example"
              android:host="gizmos" />
    </intent-filter>
</activity>
````
ps：两个意图过滤器的区别仅在于data元素。尽管有可能在同一个过滤器中包含多个data元素，但是当您要声明唯一的URL（例如方案和主机的特定组合）时，创建单独的过滤器很重要，因为实际上，同一意图过滤器会合并在一起，以说明其组合属性的所有变化。例如，考虑以下内容：
```
<intent-filter>
  ...
  <data android:scheme="https" android:host="www.example.com" />
  <data android:scheme="app" android:host="open.my.app" />
</intent-filter>
```
似乎这仅支持`https://www.example.com`和`app://open.my.app`。但是，它实际上支持这两个，以及以下两个：`app://www.example.com`和`https://open.my.app`。 将带有活动内容的URI的Intent过滤器添加到应用清单后，Android便可以在运行时将所有具有匹配URI的Intent路由到您的应用。

### 2/ 从Intent中获取数据
````
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    val action: String? = intent?.action
    val data: Uri? = intent?.data
}
````

## 创建Android App Links步骤
Android App链接是一种特殊的深层链接，它允许您的网站URL立即打开Android应用程序中的相应内容（无需用户选择应用程序）。 要将Android应用程序链接添加到您的应用程序，请定义意图过滤器，这些意图过滤器使用HTTP URL打开您的应用程序内容（如创建应用程序内容的深层链接中所述），并验证您是否拥有您的应用程序和网站URL（如本指南中所述） ）。如果系统成功验证您拥有这些URL，则系统会自动将这些URL意图路由到您的应用程序。

### 1/ 请求应用链接验证
要为您的应用开启链接处理验证，在应用清单中包含android.intent.action.VIEW和android.intent.category.BROWSABLE，设置android：autoVerify =“ true”

```
<activity ...>

    <intent-filter android:autoVerify="true">
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="http" android:host="www.example.com" />
        <data android:scheme="https" />
    </intent-filter>

</activity>
```
android:autoVerify="true"的作用系统检查所有意图过滤器，包括

action: android.intent.action.VIEW

category: android.intent.category.BROWSABLE and android.intent.category.DEFAULT

data scheme: http or https

```
<activity ...>

    <intent-filter android:autoVerify="true">
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="http" android:host="www.example.com" />
        <data android:scheme="https" />
    </intent-filter>

</activity>
```
对于在上述意图过滤器中找到的每个唯一主机名，Android会在https://hostname/.well-known/assetlinks.json的相应网站上查询Digital Asset Links文件。

#### 支持多host
```
<application>

  <activity android:name=”MainActivity”>
    <intent-filter android:autoVerify="true">
      <action android:name="android.intent.action.VIEW" />
      <category android:name="android.intent.category.DEFAULT" />
      <category android:name="android.intent.category.BROWSABLE" />
      <data android:scheme="http" android:host="www.example.com" />
      <data android:scheme="https" />
    </intent-filter>
  </activity>
  <activity android:name=”SecondActivity”>
    <intent-filter>
      <action android:name="android.intent.action.VIEW" />
      <category android:name="android.intent.category.DEFAULT" />
      <category android:name="android.intent.category.BROWSABLE" />
      <data android:scheme="https" android:host="www.example.net" />
    </intent-filter>
  </activity>

</application>
```
同一意图过滤器中的所有data元素都合并在一起以说明其组合属性的所有变化。例如，上面的第一个intent过滤器包含一个data元素，该元素仅声明HTTPS方案。但是它与其他data元素组合在一起，因此意图过滤器同时支持`http://www.example.com`和`https://www.example.com`。因此，当您要定义URI方案和域的特定组合时，必须创建单独的意图过滤器。

#### 支持多子域名
Digital Asset Links协议将您的意图过滤器中的子域视为唯一的独立主机。因此，如果您的意图过滤器列出了具有不同子域的多个主机，则必须在每个域上发布一个有效的assetlinks.json。例如，以下意图过滤器包括`www.example.com`和`mobile.example.com`作为接受的意图URL主机。因此，必须在`https://www.example.com/.well-known/assetlinks.json`和`https://mobile.example.com/.well-known/assetlinks.json`上发布有效的assetlinks.json。
```
<application>
  <activity android:name=”MainActivity”>
    <intent-filter android:autoVerify="true">
      <action android:name="android.intent.action.VIEW" />
      <category android:name="android.intent.category.DEFAULT" />
      <category android:name="android.intent.category.BROWSABLE" />
      <data android:scheme="https" android:host="www.example.com" />
      <data android:scheme="https" android:host="mobile.example.com" />
    </intent-filter>
  </activity>
</application>
```
或者，如果用通配符声明主机名（例如* .example.com），则必须在根主机名（example.com）上发布assetlinks.json文件。例如，只要assetlinks.json文件发布在`https://example.com/`，具有以下意图过滤器的应用将通过example.com的任何子名称（例如foo.example.com）的验证。
```
<application>
  <activity android:name=”MainActivity”>
    <intent-filter android:autoVerify="true">
      <action android:name="android.intent.action.VIEW" />
      <category android:name="android.intent.category.DEFAULT" />
      <category android:name="android.intent.category.BROWSABLE" />
      <data android:scheme="https" android:host="*.example.com" />
    </intent-filter>
  </activity>
</application>
```

### 2/ 声明网站关联
必须在您的网站上发布Digital Asset Links JSON文件，以指示与该网站关联的Android应用程序并验证该应用程序的URL意图。 JSON文件使用以下字段来标识关联的应用程序：

package_name 包名

sha256_cert_fingerprints 应用程序的签名证书的SHA256指纹。

您可以使用以下命令通过Java密钥工具生成指纹：
```
$ keytool -list -v -keystore my-release-key.keystore
```

以下示例assetlinks.json文件向com.example Android应用授予链接打开权限：
```
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.example",
    "sha256_cert_fingerprints":
    ["14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5"]
  }
}]
```

#### 一个网站连接多个app

网站可以在同一assetlinks.json文件中声明与多个应用程序的关联。以下文件清单显示了一个声明文件的示例，该文件分别声明与两个应用程序的关联，并且位于`https://www.example.com/.well-known/assetlinks.json`：
```
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.example.puppies.app",
    "sha256_cert_fingerprints":
    ["14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5"]
  }
  },
  {
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.example.monkeys.app",
    "sha256_cert_fingerprints":
    ["14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5"]
  }
}]
```
不同的应用程序可以处理同一Web主机下不同资源的链接。例如，app1可以声明`https://example.com/articles`的意图过滤器，而app2可以声明`https://example.com/videos`的意图过滤器。

#### 多个网站连接一个app

多个网站可以在各自的assetlinks.json文件中声明与同一应用程序的关联。以下文件列表显示了如何声明example.com和example.net与app1的关联的示例。第一个清单显示example.com与app1的关联：
```
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.mycompany.app1",
    "sha256_cert_fingerprints":
    ["14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5"]
  }
}]
```
下一个清单显示example.net与app1的关联。只有这些文件的托管位置（.com和.net）不同：
```
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.mycompany.app1",
    "sha256_cert_fingerprints":
    ["14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B2:3F:CF:44:E5"]
  }
}]
```

## 总结
Deeplink和Android app links都需要在AndroidManifest中声明`action android:name="android.intent.action.VIEW"`，`category android:name="android.intent.category.DEFAULT"`，`category android:name="android.intent.category.BROWSABLE"`
Android app links还需要多添加一个`android:autoVerify="true"`，它的作用就是让Android系统在安装app的时候去检查，对应host路径是否符合要求，成功验证后之后可快速安全的通过URI跳转到app当中，不用多余的对话框

下面简单来描述下点击URI到跳转到app的实现过程

## Deep links大概的实现原理
DeepLink用到的核心技术就是：URL SCHEMES
URL，我们都很清楚，www.apple.com 就是个 URL，也叫它链接或网址；
Schemes，表示的是一个 URL 中的一个位置——最初始的位置，即 ://之前的那段字符。比如 www.apple.com 这个网址的 Schemes 是 http。
我们可以像定位一个网页一样，用一种特殊的 URL 来定位一个应用甚至应用里某个具体的功能。而定位这个应用的，就应该这个应用的 URL 的 Schemes 部分，也就是开头儿那部分。但是需要注意的是应用的URL Schemes 并不唯一，也就是说一个应用可以“起多个名“，不同应用的URL Schemes也可能因为名字一样发生冲突。

Android系统级应用，有一些已经定义了URL Schemes，比如短信是 sms:、通话是tel:、email是mailto:，在定义自己APP的URL Schemes的时候要避免跟系统应用名称一样。

浏览器点击URI之后，启动相应app，其实关键在 WebView 的 WebViewClient 的 shouldOverrideUrlLoading 方法，当webview加载一个url的时候，会回调shouldOverrideUrlLoading方法，截取关键代码如下：
```
boolean shouldOverrideUrlLoading(Tab tab, WebView view, String url) {
    ...
    // The "about:" schemes are internal to the browser; don't want these to
    // be dispatched to other apps.
    if (url.startsWith("about:")) {
        return false;
    }
    ...
    if (startActivityForUrl(tab, url)) {
        return true;
    }
    if (handleMenuClick(tab, url)) {
        return true;
    }
    return false;
}
```
从上面代码中可以看到scheme为about是原生浏览器内部用的，唤起app的关键在startActivityForUrl方法。
```
boolean startActivityForUrl(Tab tab, String url) {
    Intent intent;
    // perform generic parsing of the URI to turn it into an Intent.
    try {
        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
    } catch (URISyntaxException ex) {
        Log.w("Browser", "Bad URI " + url + ": " + ex.getMessage());
        return false;
    }
    // check whether the intent can be resolved. If not, we will see
    // whether we can download it from the Market.
    ResolveInfo r = null;
    try {
        r = mActivity.getPackageManager().resolveActivity(intent, 0);
    } catch (Exception e) {
        return false;
    }
    ...
    // sanitize the Intent, ensuring web pages can not bypass browser
    // security (only access to BROWSABLE activities).
    intent.addCategory(Intent.CATEGORY_BROWSABLE);
    intent.setComponent(null);
    Intent selector = intent.getSelector();
    if (selector != null) {
    selector.addCategory(Intent.CATEGORY_BROWSABLE);
    selector.setComponent(null);
    }
    ...
    try {
        intent.putExtra(BrowserActivity.EXTRA_DISABLE_URL_OVERRIDE, true);
    if (mActivity.startActivityIfNeeded(intent, -1)) { // 唤起 app 的最终代码在这里
        // before leaving BrowserActivity, close the empty child tab.
        // If a new tab is created through JavaScript open to load this
        // url, we would like to close it as we will load this url in a
        // different Activity.
        mController.closeEmptyTab();
            return true;
        }
    } catch (ActivityNotFoundException ex) {
        // ignore the error. If no application can handle the URL,
        // eg about:blank, assume the browser can handle it.
    }
    return false;
}
```
上面intent.addCategory(Intent.CATEGORY_BROWSABLE)；也可以看出我们之前在加<category android:name="android.intent.category.BROWSABLE" />的原因。显然上面的intent就是启动我们目标activity的intent
而如果第三方的浏览器在这个地方对scheme屏蔽，就可以让web唤起app失效。




