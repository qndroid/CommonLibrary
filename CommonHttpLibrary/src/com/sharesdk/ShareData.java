package com.sharesdk;

import cn.sharesdk.framework.Platform.ShareParams;

import com.sharesdk.ShareManager.PlatofrmType;

/**
 * @author 要分享的数据实体
 *
 */
public class ShareData {

	/**
	 * 要分享到的平台
	 */
	public PlatofrmType mPlatformType;

	/**
	 * 要分享到的平台的参数
	 */
	public ShareParams mShareParams;
}
