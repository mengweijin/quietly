package com.github.mengweijin.quietly.system.controller;

import cn.hutool.core.util.IdUtil;
import com.github.mengweijin.quickboot.framework.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * <p>
 * project info Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping
    public R getToken(String username, String password) {
        return R.success(IdUtil.fastSimpleUUID().toUpperCase(Locale.ROOT));
    }

}

