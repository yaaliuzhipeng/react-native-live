import React, { useEffect, useState } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity, Pressable, Button, Dimensions } from 'react-native';
import { LivePullManager, LivePushManager, LivePushView, LivePullView } from 'react-native-live';
const { width: sw, height: sh } = Dimensions.get('window');

import PermissionGrantView from './PermissionGrantView';
const PushURL = "rtmp://bxdx-livepush.haxibiao.cn/live/u8l5?txSecret=85d30af5133e222e92b3c8907db5b5df&txTime=5F212A44";

export default function PushRoom() {

    const [showPull, setshowpull] = useState(false);

    const __showPullCallback = () => {
        setshowpull(true)
        LivePushManager.preview()
    }

    const beauty = () => {

        LivePushManager.setBeautyMode("NATURAL")
        LivePushManager.setWhitenessLevel(9);
    }

    const startPush = () => {
        LivePushManager.startPush(PushURL)
    }

    return (
        <>
            {
                showPull ? (
                    <LivePushView style={{ height: sh, width: sw }} />
                ) : (
                        <PermissionGrantView callback={__showPullCallback} />
                    )
            }
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