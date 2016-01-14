package com.commonliabray.model;

import java.io.Serializable;

/**
 * @author vision
 * @function 极光推送消息实体，包含所有的数据字段。
 */
public class PushMessage implements Serializable {

	// 消息类型
	public String messageType = null;
	// 连接
	public String messageUrl = null;
	// 详情内容
	public String messageContent = null;
}
