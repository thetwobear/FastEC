## FastEC 模拟电商项目
### x-core 核心模块 Android Module
#### 封装有Application中一次性初始化整个app相关配置；

 ```
  XCore.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/index/")
                .withLoaderDelayed(1000)
                .withWeChatAppId("")//微信AppId
                .withWeChatAppSecret("")//微信AppSecret
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
 ```
#### 封装有Retrofit+OkHttp/Retrofit+OkHttp+RxJava网络请求框架
```
RestClient.builder()
                .url("http://127.0.0.1/index/")
                .loader(getContext())
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        System.out.println("开始请求");
                    }

                    @Override
                    public void onRequestEnd() {
                        System.out.println("请求结束");
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("response", response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        System.out.println("请求失败");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        System.out.println(msg);
                    }
                })
                .build()
                .rxGet();
```
Retrofit+OkHttp 请求直接将.rxGet()/.rxPost()/.rxDelete()...替换.get()/.post()/.delete()/...
持续完善...
### x-annotations 注解Java Module
辅助完成微信登录及支付，通过注解生成微信需要的Activity、BroadCastReceiver
### x-compiler 编译Java Module
辅助完成微信登录及支付，通过注解生成微信需要的Activity、BroadCastReceiver
### x-ec 具体业务 Android Module
Database/SignIn/SignUp/Luancher...
