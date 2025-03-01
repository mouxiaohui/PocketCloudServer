package com.xiaohui.pocket.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.system.mapper.LogMapper;
import com.xiaohui.pocket.system.model.entity.Log;
import com.xiaohui.pocket.system.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author xiaohui
 * @since 2025/2/21
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
}
