package com.demo.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TCC 注册服务：独立部署的"注册表"。
 *
 * TCC 参与方（RM）启动时把自己声明的 @TccAction 动作（try/confirm/cancel 方法信息）
 * 推送到这里；事务发起方（TM）在注册分支时从这里查询动作信息，按信息通用调用，
 * 不再与任何具体参与方硬编码耦合。
 */
@SpringBootApplication
public class TccRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccRegistryApplication.class, args);
    }
}
