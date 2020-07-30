import React, { useEffect, useState } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity, Pressable, Button, Dimensions } from 'react-native';
import { LivePullManager, LivePushManager, LivePushView, LivePullView, LIVE_TYPE } from 'react-native-live';
const { width: sw, height: sh } = Dimensions.get('window');

const URL = "rtmp://bxdx-livepull.haxibiao.cn/live/u8l8"

export default function PullRoom() {

    const startPull = () => {
        LivePullManager.startPull(URL,LIVE_TYPE.RTMP)
    }

    return (
        <>
            <LivePullView  style={{height:sh,width:sw}}/>

            <Pressable
                style={{ position: 'absolute', bottom: sh * 0.15, right: 15 }}
                onPress={startPull}>

                <View style={{ paddingHorizontal: 10, paddingVertical: 5, borderRadius: 12, overflow: 'hidden', backgroundColor: '#ffffffcc' }}>
                    <Text>开始拉流</Text>
                </View>
            </Pressable>
        </>
    )
}