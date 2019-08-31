package com.wesley.bigdata.log;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * UserAgent解析工具类
 * @author Created by Wesley on 2019/8/31
 */
@Slf4j
public class UserAgentUtils {

    private static UASparser sparser;

    static {
        try {
            sparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static UserAgentDTO parse(String userAgent) {
        UserAgentDTO result = new UserAgentDTO();

        try {
            UserAgentInfo parse = sparser.parse(userAgent);
            result.setBrowserName(parse.getUaFamily())
                  .setBrowserVersion(parse.getBrowserVersionInfo())
                  .setOsName(parse.getOsFamily())
                  .setOsVersion(parse.getOsName());
        } catch (IOException e) {
            log.error("解析UserAgent失败", e);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        UserAgentDTO dto = UserAgentUtils
                .parse("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        System.out.println(dto);
    }
}
