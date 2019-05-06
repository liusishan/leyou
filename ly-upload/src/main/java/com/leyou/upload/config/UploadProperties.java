package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/4/24 09:33
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {

    private String baseUrl;
    private List<String> allowTypes;

}
