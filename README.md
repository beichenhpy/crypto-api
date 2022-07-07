# crypto-api

Spring boot Api接口，请求参数解密，请求结果加密例子  
加密选择简单的AES加密。`AES/CBC/PKCS5Padding`  
主要针对 `@RequestParam` `@RequestBody` `@ResponseBody`进行实现。

## 实现

1. `@RequestBody` 使用了`RequestBodyAdviceAdapter`
2. `@ResponseBody` 使用了 `ResponseBodyAdvice`
3. `@RequestParam` 使用了 `HttpServletRequestWrapper` + `Filter`


测试例子见 `sample`包下。

使用需要配置文件，如下
1. 不同组的urls如果出现重复，默认以最后的为准 比如说demo2分组中出现了`/query`则`/query`就使用`demo2`的`aesKey`
2. 支持通配符 如demo2的 `/demo/*`
```yaml
crypto-api:
  apis:
    demo1:
      paths:
        - /demo/*
        - /demo/query
      aesKey: f5d830d77163a58f
```
