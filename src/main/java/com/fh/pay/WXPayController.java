package com.fh.pay;

import com.fh.common.ServerResponse;
import com.fh.sdk.MyWXPayConfig;
import com.fh.sdk.WXPay;
import com.fh.sdk.WXPayUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("pays")
@RestController
public class WXPayController {


    @RequestMapping("queryCode")
    public ServerResponse pays(String orderNum, BigDecimal totalPrice) {
        MyWXPayConfig config = new MyWXPayConfig();
        try {
            WXPay wxPay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐电商项目微信支付测试");
            data.put("out_trade_no", orderNum);
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");
            data.put("total_fee", "1");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", "12");

            //过期时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(DateUtils.addMinutes(new Date(), 2));
            data.put("time_expire", format);

            Map<String, String> res = wxPay.unifiedOrder(data);
            System.out.println(res);
            //返回状态码
            if (!res.get("return_code").equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error("微信支付错误：" + res.get("return_msg"));
            }

            //业务结果
            if (!res.get("result_code").equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error("微信支付错误：" + res.get("err_code_des"));
            }
            String url = res.get("code_url");
            return ServerResponse.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }


    //查看订单状态
    @RequestMapping("queryOrderStatus")
    public ServerResponse queryOrderStatus(String orderNum) {

        try {
            MyWXPayConfig config = new MyWXPayConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", orderNum);

            //for死循环，为该请求一直在发送
            Integer count = 0;
            for(;;){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);

                //返回状态码
                if (!resp.get("return_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + resp.get("return_msg"));
                }

                //业务结果
                if (!resp.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + resp.get("err_code_des"));
                }

                //交易判断
                if (resp.get("trade_state").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.success();
                }

                count++;
                //休眠时间
                Thread.sleep(3000);
                if (count > 30){
                    return ServerResponse.error("支付超时");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(e.getMessage());
        }

    }



}
