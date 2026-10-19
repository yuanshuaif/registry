package com.demo.registry.controller;

import com.demo.registry.domain.TccActionRegistration;
import com.demo.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册服务对外接口
 *
 *   POST /tcc/register  参与方自注册 TCC 动作（按 actionName 幂等覆盖）
 *   GET  /tcc/actions   查询全部已注册动作（发起方协调、后台恢复、人工排查用）
 */
@RestController
@RequestMapping("/tcc")
public class RegistryController {

    private static final Logger log = LoggerFactory.getLogger(RegistryController.class);

    private final RegistryService registryService;

    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody TccActionRegistration reg) {
        Map<String, Object> resp = new LinkedHashMap<>();
        if (isBlank(reg.getActionName()) || isBlank(reg.getServiceUrl())
                || isBlank(reg.getTryMethod()) || isBlank(reg.getConfirmMethod()) || isBlank(reg.getCancelMethod())) {
            log.warn("[Registry] 注册信息不完整：{}", reg.getActionName());
            resp.put("success", false);
            resp.put("message", "注册信息不完整：actionName/serviceUrl/三个方法名均必填");
            return resp;
        }
        if (isBlank(reg.getServiceName())) {
            reg.setServiceName("unknown");
        }
        registryService.register(reg);
        resp.put("success", true);
        resp.put("message", "动作 " + reg.getActionName() + " 注册成功（幂等覆盖）");
        return resp;
    }

    @GetMapping("/actions")
    public List<TccActionRegistration> actions() {
        return registryService.listAll();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
