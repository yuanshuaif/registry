package com.demo.registry.service;

import com.demo.registry.domain.TccActionRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注册表服务：t_branch_registry 表的读写。
 *
 * 注册按 actionName 幂等（MERGE INTO 覆盖式），参与方重复注册/重启后重新注册都安全。
 */
@Service
public class RegistryService {

    private static final Logger log = LoggerFactory.getLogger(RegistryService.class);

    private final JdbcTemplate jdbcTemplate;

    public RegistryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 注册（或覆盖）一个 TCC 动作 */
    public void register(TccActionRegistration reg) {
        jdbcTemplate.update(
                "MERGE INTO t_branch_registry " +
                        "(action_name, service_name, service_url, try_method, confirm_method, cancel_method) " +
                        "KEY(action_name) VALUES (?, ?, ?, ?, ?, ?)",
                reg.getActionName(), reg.getServiceName(), reg.getServiceUrl(),
                reg.getTryMethod(), reg.getConfirmMethod(), reg.getCancelMethod());
        log.info("[Registry] 注册/刷新 TCC 动作：{} -> {} ({})",
                reg.getActionName(), reg.getServiceUrl(), reg.getServiceName());
    }

    /** 全部已注册动作 */
    public List<TccActionRegistration> listAll() {
        return jdbcTemplate.query(
                "SELECT action_name, service_name, service_url, try_method, confirm_method, cancel_method " +
                        "FROM t_branch_registry ORDER BY action_name",
                (rs, i) -> {
                    TccActionRegistration reg = new TccActionRegistration();
                    reg.setActionName(rs.getString("action_name"));
                    reg.setServiceName(rs.getString("service_name"));
                    reg.setServiceUrl(rs.getString("service_url"));
                    reg.setTryMethod(rs.getString("try_method"));
                    reg.setConfirmMethod(rs.getString("confirm_method"));
                    reg.setCancelMethod(rs.getString("cancel_method"));
                    return reg;
                });
    }
}
