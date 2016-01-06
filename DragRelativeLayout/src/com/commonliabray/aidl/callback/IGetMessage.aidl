package com.commonliabray.aidl.callback;

import com.commonliabray.aidl.callback.Message;
import com.commonliabray.aidl.callback.User;

interface IGetMessage{
   
   List<Message> getMessage(in User user);
}