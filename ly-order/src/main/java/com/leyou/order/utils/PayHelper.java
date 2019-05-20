package com.leyou.order.utils;

import com.github.wxpay.sdk.WXPay;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.order.config.PayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.github.wxpay.sdk.WXPayConstants.*;

/**
 * @Auther: lss
 * @Date: 2019/5/20 10:30
 * @Description:
 */
@Slf4j
@Component
public class PayHelper {

    @Autowired
    private WXPay wxPay;

    @Autowired
    private PayConfig payConfig;

    public String createOrder(Long orderId, Long totalPay, String desc) {
        try {
            Map<String, String> data = new HashMap<>();
            // 商品描述
            data.put("body", desc);
            // 订单编号
            data.put("out_trade_no", orderId.toString());
            // 金额，单位是分
            data.put("total_fee", totalPay.toString());
            // 调用微信支付的终端 ip
            data.put("spbill_create_ip", "127.0.0.1");
            // 回调地址
            data.put("notify_url", payConfig.getNotifyUrl());
            // 交易类型为扫码交易
            data.put("trade_type", "NATIVE");
            // 利用 wxPay 工具完成下单
            Map<String, String> result = wxPay.unifiedOrder(data);

            // 判断通信标示
            String returnCode = result.get("return_code");
            if (FAIL.equals(returnCode)) {
                // 通信失败
                log.error("[微信下单] 微信下单通信失败，失败原因：{}", result.get("return_msg"));
                throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
            }

            // 判断业务标示
            String resultCode = result.get("result_code");
            if (FAIL.equals(resultCode)) {
                // 业务失败
                log.error("[微信下单] 微信下单业务失败，错误码：{}，错误原因：{}", result.get("err_msg"), result.get("err_code_des"));
                throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
            }

            //下单成功
            String url = result.get("code_url");
            return url;
        } catch (Exception e) {
            log.error("[微信下单] 创建预交易订单异常失败", e);
            return null;
        }
    }

}
