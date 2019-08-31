package com.wesley.bigdata.log;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Created by Wesley on 2019/8/31
 */
@Data
@Accessors(chain = true)
@ToString
public class UserAgentDTO {

    /**
     * 浏览器名称
     */
    private String browserName;

    private String browserVersion;

    private String osName;

    private String osVersion;
}
