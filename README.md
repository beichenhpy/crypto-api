# encrypt-decrypt-api-sample

Spring boot Api接口，请求参数解密，请求结果加密例子  
加密选择简单的AES加密。`AES/CBC/PKCS5Padding`  
主要针对 `@RequestParam` `@RequestBody` `@ResponseBody`进行实现。

## 实现

1. `@RequestBody` 使用了`RequestBodyAdviceAdapter`
2. `@ResponseBody` 使用了 `ResponseBodyAdvice`
3. `@RequestParam` 使用了 `HttpServletRequestWrapper` + `Filter`

由于`ResponseBodyAdvice`需要针对具体的返回值类进行定制化，因此没有封装成 starter,如果想要自己实现，只需要参考例子即可。

测试例子见 `cn.beichenhpy.encryptdecryptapisample.sample`包下。

使用需要配置文件，如下

```yaml
encrypt-decrypt-api:
  aes-key: f5d830d77163a58f #aes的秘钥
  urls: #需要进行加密解密的url
    - /user
    - /query
```
