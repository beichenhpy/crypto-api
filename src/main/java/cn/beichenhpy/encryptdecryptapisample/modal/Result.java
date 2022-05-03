/*
 * Copyright [2022] [韩鹏宇]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.beichenhpy.encryptdecryptapisample.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *    请求返回值
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 22:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private Boolean success;
    private T data;
    private String msg;


    public static <T> Result<T> ok(T data, String msg) {
        return new Result<>(200, true, data, msg);
    }


    public static <T> Result<T> ok(T data) {
        return new Result<>(200, true, data, "请求成功");
    }
}
