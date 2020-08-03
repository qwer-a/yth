package com.fh.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.util.MessageUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
@CrossOrigin
public class SendMessage {

    //获取短信验证
    @RequestMapping("getCode")
    @Ignore
    public ServerResponse getCode(String phone){
        String code = MessageUtils.getNewcode();
        try {
            SendSmsResponse sendSmsResponse = MessageUtils.sendSms(phone, code);
            if (sendSmsResponse != null && "OK".equals(sendSmsResponse.getCode())){
                RedisUtil.expireTime(phone,code, SystemConstant.REDIS_EXPIRY_TIME);
                return ServerResponse.success();
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return ServerResponse.error(e.getErrMsg());
        }

        return null;
    }
}
