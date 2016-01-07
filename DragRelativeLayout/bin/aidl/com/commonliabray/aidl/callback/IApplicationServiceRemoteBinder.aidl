package com.commonliabray.aidl.callback;

import  com.commonliabray.aidl.callback.TimeServiceCallback;

interface IApplicationServiceRemoteBinder{

    void registerCallback(TimeServiceCallback callback);
    
    void unRegisterCallback(TimeServiceCallback callback);
}

