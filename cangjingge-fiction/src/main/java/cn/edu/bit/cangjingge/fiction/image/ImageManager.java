package cn.edu.bit.cangjingge.fiction.image;

import cn.edu.bit.cangjingge.common.util.FileUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

// 使用阿里云OSS管理图像
@Service
public class ImageManager {

    @Value("${aliyun.access-key.id}")
    private String accessKeyId;

    @Value("${aliyun.access-key.secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.endpoint}")
    private String ossEndpoint;

    @Value("${aliyun.sts.endpoint}")
    private String stsEndpoint;

    @Value("${aliyun.sts.write-only-role-arn}")
    private String writeOnlyRoleArn;

    // 存储文件并返回URL
    public String upload(
            final MultipartFile multipartFile,
            final Long id
    ) throws IOException, ClientException {
        AssumeRoleResponse.Credentials credentials = auth(writeOnlyRoleArn, id.toString());
        OSS oss = buildOss(credentials);

        String filename = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename != null) {
            filename += "." + FileUtil.getExtensionName(originalFilename);
        }

        oss.putObject(bucketName, filename, multipartFile.getInputStream());
        oss.shutdown();
        return filename;
    }

    private OSS buildOss(
            final AssumeRoleResponse.Credentials credentials
    ) {
        OSSClientBuilder builder = new OSSClientBuilder();
        String accessKeyId = credentials.getAccessKeyId();
        String accessKeySecret = credentials.getAccessKeySecret();
        String securityToken = credentials.getSecurityToken();
        return builder.build(ossEndpoint, accessKeyId, accessKeySecret, securityToken);
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
