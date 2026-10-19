package com.demo.registry.domain;

/**
 * 一次 TCC 动作注册（参与方 -> 注册服务）
 *
 * actionName    ：业务动作句柄（如 deductStorage），发起方用它注册分支
 * serviceName   ：参与方服务名
 * serviceUrl    ：参与方基地址（协调器向 {serviceUrl}/tcc/invoke 发起通用调用）
 * tryMethod 等  ：参与方自己声明的三阶段方法名（记录审计用，真正的方法定位在参与方内部）
 */
public class TccActionRegistration {

    private String actionName;
    private String serviceName;
    private String serviceUrl;
    private String tryMethod;
    private String confirmMethod;
    private String cancelMethod;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getTryMethod() {
        return tryMethod;
    }

    public void setTryMethod(String tryMethod) {
        this.tryMethod = tryMethod;
    }

    public String getConfirmMethod() {
        return confirmMethod;
    }

    public void setConfirmMethod(String confirmMethod) {
        this.confirmMethod = confirmMethod;
    }

    public String getCancelMethod() {
        return cancelMethod;
    }

    public void setCancelMethod(String cancelMethod) {
        this.cancelMethod = cancelMethod;
    }
}
