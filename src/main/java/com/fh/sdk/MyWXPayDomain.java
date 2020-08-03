package com.fh.sdk;

public class MyWXPayDomain implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    // 返回微信的域名
    @Override
    public DomainInfo getDomain(WXPayConfig config) {

        /**
         *  1.微信支付的域名
         *  2.是否为主域名
         */
        DomainInfo domainInfo = new DomainInfo("api.mch.weixin.qq.com",true);
        return domainInfo;
    }
}
