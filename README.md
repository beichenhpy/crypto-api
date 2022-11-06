# crypto-api

Spring boot Api接口，请求参数解密，请求结果加密例子  
加密选择简单的AES加密。`AES/CBC/PKCS5Padding`  
主要针对  `@RequestBody` `@ResponseBody`进行实现。

**暂不支持@RequestParam的解密**

## 实现

1. `@RequestBody` 使用了`RequestBodyAdviceAdapter`
2. `@ResponseBody` 使用了 `ResponseBodyAdvice`

测试例子见 `sample`包下。

需要对要加密的方法上添加`@EncryptApi(handler="")` handler为自己实现的实现加密实现方式  
需要对要解密的方法上添加`@DecryptApi(handler="")` handler为自己实现的实现解密实现方式  
默认提供了`AES`的handler

使用需要配置文件，如下

1. 不同组的urls如果出现重复，默认以最后的为准 比如说demo2分组中出现了`/query`则`/query`就使用`demo2`的`aesKey`
2. 支持通配符 如demo2的 `/demo/*`

```yaml
crypto-api:
  extension:
    aes:
      encrypt:
        enable: true
        apis:
          user:
            aes-key: f5d830d77163a58f
            paths:
              - /demo/encrypt/**
      decrypt:
        enable: true
        apis:
          user:
            aes-key: f5d830d77163a58f
            paths:
              - /demo/decrypt/**
```
