package com.yupi.yupicture.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 配置验证器
 * 用于验证敏感配置是否正确加载
 */
@Component
@Slf4j
public class ConfigurationValidator implements CommandLineRunner {

    @Value("${cos.client.host:}")
    private String cosHost;

    @Value("${cos.client.secretId:}")
    private String cosSecretId;

    @Value("${cos.client.secretKey:}")
    private String cosSecretKey;

    @Value("${cos.client.bucket:}")
    private String cosBucket;

    @Value("${aliYunAi.apiKey:}")
    private String aliYunAiApiKey;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== 配置验证开始 ===");
        
        // 验证COS配置
        validateCosConfig();
        
        // 验证阿里云AI配置
        validateAliYunAiConfig();
        
        log.info("=== 配置验证完成 ===");
    }

    private void validateCosConfig() {
        log.info("验证腾讯云COS配置...");
        
        if (cosHost.isEmpty()) {
            log.error("❌ COS Host 未配置");
        } else {
            log.info("✅ COS Host: {}", cosHost);
        }
        
        if (cosSecretId.isEmpty()) {
            log.error("❌ COS SecretId 未配置");
        } else {
            log.info("✅ COS SecretId: {}", maskSecret(cosSecretId));
        }
        
        if (cosSecretKey.isEmpty()) {
            log.error("❌ COS SecretKey 未配置");
        } else {
            log.info("✅ COS SecretKey: {}", maskSecret(cosSecretKey));
        }
        
        if (cosBucket.isEmpty()) {
            log.error("❌ COS Bucket 未配置");
        } else {
            log.info("✅ COS Bucket: {}", cosBucket);
        }
    }

    private void validateAliYunAiConfig() {
        log.info("验证阿里云AI配置...");
        
        if (aliYunAiApiKey.isEmpty()) {
            log.error("❌ 阿里云AI API Key 未配置");
        } else {
            log.info("✅ 阿里云AI API Key: {}", maskSecret(aliYunAiApiKey));
        }
    }

    /**
     * 掩码敏感信息
     */
    private String maskSecret(String secret) {
        if (secret == null || secret.length() <= 8) {
            return "***";
        }
        return secret.substring(0, 4) + "****" + secret.substring(secret.length() - 4);
    }
}
