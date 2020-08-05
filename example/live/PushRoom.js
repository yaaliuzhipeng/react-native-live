import React, { useEffect, useState } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity, Pressable, Button, Dimensions } from 'react-native';
import { LivePullManager, LivePushManager, LivePushView, LivePullView } from 'react-native-live';
const { width: sw, height: sh } = Dimensions.get('window');

import PermissionGrantView from './PermissionGrantView';
const PushURL = "rtmp://bxdx-livepush.haxibiao.cn/live/u8l8?txSecret=3f16f36bab5e68b2d4f7f81d0c7be011&txTime=5F238752";

export default function PushRoom() {

    const [showPush, setshowpush] = useState(false);
    const [state, setstate] = useState("未开始");

    const __showPushCallback = () => {
        console.log("切换视图？？？")
        setshowpush(true)
        LivePushManager.preview()
    }

    const beauty = () => {

        LivePushManager.setBeautyMode("NATURAL")
        LivePushManager.setWhitenessLevel(9);
    }

    const startPush = () => {
        setstate("开始推流")
        setshowpush(true);
        //LivePushManager.startPush(PushURL)
    }

    return (
        <>
            {
                showPush ? (
                    <LivePushView style={{ height: sh, width: sw }} />
                ) : (
                        <PermissionGrantView callback={__showPushCallback} />
                    )
            }
            <View
                style={{ position: 'absolute', bottom: sh * 0.3, right: 15 }}
                onPress={beauty}>
                <View style={{ paddingHorizontal: 10, paddingVertical: 5, borderRadius: 12, overflow: 'hidden', backgroundColor: '#ffffffcc' }}>
                    <Text>{state}</Text>
                </View>

            </View>
            <Pressable
                style={{ position: 'absolute', bottom: sh * 0.1, right: 15 }}
                onPress={beauty}>
                <View style={{ paddingHorizontal: 10, paddingVertical: 5, borderRadius: 12, overflow: 'hidden', backgroundColor: '#ffffffcc' }}>
                    <Text>美颜</Text>
                </View>

            </Pressable>

            <Pressable
                style={{ position: 'absolute', bottom: sh * 0.15, right: 15 }}
                onPress={startPush}>

                <View style={{ paddingHorizontal: 10, paddingVertical: 5, borderRadius: 12, overflow: 'hidden', backgroundColor: '#ffffffcc' }}>
                    <Text>开始推流</Text>
                </View>
            </Pressable>
        </>
    )
}


