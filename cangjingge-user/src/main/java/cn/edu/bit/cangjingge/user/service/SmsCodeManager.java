package cn.edu.bit.cangjingge.user.service;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.user.dao.SmsCodeDao;
import cn.edu.bit.cangjingge.user.entity.SmsCode;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

// 使用阿里云进行短信发送
@Service
public class SmsCodeManager {

    @Resource
    private SmsCodeDao smsCodeDao;

    @Value("${sms.template-code}")
    private String templateCode;

    @Value("${aliyun.access-key.id}")
    private String accessKeyId;

    @Value("${aliyun.access-key.secret}")
    private String accessKeySecret;

    @Value("${sms.random-code-bound}")
    private int RANDOM_CODE_BOUND;

    @Value("${sms.retry-seconds}")
    private int retrySeconds;

    @Value("${sms.expiration-seconds}")
    private int expirationSeconds;

    private static final String signName = "藏经阁";
    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";

    public void checkSmsCode(
            final String phoneNumber,
            final String smsCode
    ) throws BusinessException {
        boolean smsCodeExpired = hasSmsCodeExpired(phoneNumber, smsCode);
        if (smsCodeExpired) {
            throw new BusinessException(ResponseStatusEnum.SMS_CODE_INVALID);
        }
    }

    public boolean hasSmsCodeExpired(
            final String phoneNumber,
            final String smsCode
    ) {
        SmsCode code = smsCodeDao.findSmsCodeByPhoneNumberAndSmsCode(phoneNumber, smsCode);
        return (code == null) || code.getExpirationTime().before(new Date());
    }

    public void sendRandomCode(
            final String phoneNumber
    ) throws BusinessException, ClientException {
        SmsCode smsCode = smsCodeDao.findSmsCodeByPhoneNumber(phoneNumber);
        Date currentTime = new Date();
        if (smsCode != null && smsCode.getNextRetryTime().after(currentTime)) {
            throw new BusinessException(ResponseStatusEnum.TOO_FREQUENTLY_REQUEST);
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        final int number = random.nextInt(RANDOM_CODE_BOUND);
        String code = String.format("%04d", number);
        send(phoneNumber, code);

        if (smsCode == null) {
            smsCode = new SmsCode();
        }
        smsCode.setPhoneNumber(phoneNumber);
        smsCode.setSmsCode(code);
        Date nextSendingTime = new Date(System.currentTimeMillis() + 1000 * retrySeconds);
        smsCode.setNextRetryTime(nextSendingTime);
        Date expirationTime = new Date(System.currentTimeMillis() + 1000 * expirationSeconds);
        smsCode.setExpirationTime(expirationTime);
        smsCodeDao.save(smsCode);
    }

    private void send(
            final String phoneNumber,
            final String code
    ) throws BusinessException, ClientException {
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phoneNumber);
        // 短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        String responseCode = sendSmsResponse.getCode();
        if (responseCode != null) {
            if (responseCode.equals("isv.MOBILE_NUMBER_ILLEGAL")) {
                throw new BusinessException(ResponseStatusEnum.MOBILE_NUMBER_ILLEGAL);
            }
        }
    }

}
