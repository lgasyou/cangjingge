package cn.edu.bit.cangjingge.authorization.service;

import cn.edu.bit.cangjingge.common.dto.AliyunOssAuthorization;
import cn.edu.bit.cangjingge.common.util.TokenUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliyunOssServiceImpl {

    @Value("${aliyun.access-key.id}")
    private String accessKeyId;

    @Value("${aliyun.access-key.secret}")
    private String accessKeySecret;

    @Value("${aliyun.sts.endpoint}")
    private String stsEndpoint;

    @Value("${aliyun.sts.read-only-role-arn}")
    private String readOnlyRoleArn;

    public AliyunOssAuthorization getOssAuthorization() throws ClientException {
        String phoneNumber = TokenUtil.getUsernameFromHeaders();
        AssumeRoleResponse.Credentials credentials = auth(readOnlyRoleArn, phoneNumber);
        String accessKeyId = credentials.getAccessKeyId();
        String accessKeySecret = credentials.getAccessKeySecret();
        String securityToken = credentials.getSecurityToken();
        return new AliyunOssAuthorization(accessKeyId, accessKeySecret, securityToken);
    }

    private AssumeRoleResponse.Credentials auth(
            final String roleArn,
            final String roleSessionName
    ) throws ClientException {
        // Init ACS Client
        DefaultProfile.addEndpoint("", "", "Sts", stsEndpoint);
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        final AssumeRoleResponse response = client.getAcsResponse(request);
        return response.getCredentials();
    }

}
