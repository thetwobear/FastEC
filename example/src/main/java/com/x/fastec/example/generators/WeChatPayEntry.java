package com.x.fastec.example.generators;

import com.example.annotation.PayEntryGenerator;
import com.x.x_core.wechat.template.WPayEntryTemplate;

/**
 * Created by 熊猿猿 on 2017/8/16/016.
 */
@PayEntryGenerator(packageName = "com.x.fastec.example", payEntryTemplete = WPayEntryTemplate.class)
public interface WeChatPayEntry {

}
