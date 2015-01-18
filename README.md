# jokebot-service
お話ロボットサービスです。

ご利用には Docomo Developers support の apikey が必要です。

本アプリでは以下のapiを使用します。

・雑談対話

・知識Q&A


# とりあえず Debug Form で試してみたいとき

▼ gradle をインストールしている！

cd /d ${jokebot-service-master}

set DOCOMO_APIKEY=XXX

gradle clean jettyRun

▼ gradle をインストールしていない・・・

```
cd /d ${jokebot-service-master}
set DOCOMO_APIKEY=XXX
gradlew clean jettyRun
```
cd /d ${jokebot-service-master}

set DOCOMO_APIKEY=XXX

gradlew clean jettyRun

以下のように表示されたら

Building 80% > jettyRun > Running at http://localhost:8080/jokebot-service

ブラウザで 「http://localhost:8080/jokebot-service」 にアクセスすると Joke Bot Debug Form が開きます。

（！） お話を開始する前に、apikey を送信してください。


# eclipse で開発するとき

▼ gradle をインストールしている！

cd /d ${jokebot-service-master}

gradle cleanEclipse eclipse

▼ gradle をインストールしていない・・・

cd /d ${jokebot-service-master}

gradlew cleanEclipse eclipse

