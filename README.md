CushyHttp
===============
Easy Android Http Client



Gradle
------------
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    compile 'com.github.EdgeJH:CushyHttp:1.0.1'
}
```

Usage
--------

English

CushyHttp Library is simple httpclient for restful api.
this support request method get,post.
You can customize header field.
Generics is ResponseClass. 
CushyHttp support Gson Library. So You can get response to custom class.

Korean

쿠시Http 라이브러리는 restful api 를 위해 간단히 사용가능한 http 통신 라이브러리입니다.
기본적으로 get과 post 메소드를 지원하고 
헤더를 커스텀할 수 있습니다.
제너릭스 형태는 리스폰을 받고 싶은 형태로 받을 수 있으며
Gson 매핑을 지원하기 때문에 커스텀 클래스로 받을 수 있습니다.

```java
CushyHttp<JSONObject> cushyHttp = new CushyHttp.Builder<>(JSONObject.class)
                .setRequestMethod(CushyField.GET)
                .setContentsType(CushyField.JSON)
                .setUrl(" http://url")
                .setRequestHeader("appKey","123123")
                .build();
        cushyHttp.startConnection(new OnHttpListener<JSONObject>() {
            @Override
            public void onResonpse(JSONObject responseData, int responseCode) {
                
            }
            @Override
            public void onFailure(int code, String message) {

            }
        });
```

License
--------
```
Copyright 2017 EdgeJH


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
