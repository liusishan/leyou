package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/5/14 23:50
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "ly.filter")
public class FilterProperites {
    private List<String> allowPaths;
}
