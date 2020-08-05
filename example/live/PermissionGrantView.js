import React, { useEffect, useState } from 'react';
import { View, Text, Image, StyleSheet, Dimensions, TouchableOpacity, Platform } from 'react-native';
const { width: sw, height: sh } = Dimensions.get('window');
import { check, request, PERMISSIONS, RESULTS } from 'react-native-permissions';

const GRANT = '已授权';
const CHECKING = '启用相机访问权限';
const CHECKING_MICRO = '启用麦克风访问权限';
const UNAVAILABLE = '该项不可用';
const BLOCK = '已禁用、请手动授予权限';
const DENY = '已拒绝';

const DEFAULT = '#32CE6D';
const GREEN = 'green';
const BAD = 'red';

const PermissionGrantView = (props) => {
    const [camerastatus, setcamerastatus] = useState(CHECKING);
    const [microstatus, setmicrostatus] = useState(CHECKING_MICRO);
    const [cameracolor, setcameracolor] = useState(DEFAULT);
    const [microcolor, setmicrocolor] = useState(DEFAULT);

    function CameraCheck(){
        if (Platform.OS == 'android') {
            check(PERMISSIONS.ANDROID.CAMERA).then((result) => {
                switch (result) {
                    case RESULTS.UNAVAILABLE:
                        setcamerastatus(UNAVAILABLE);
                        setcameracolor(BAD);
                        break;
                    case RESULTS.GRANTED:
                        setcamerastatus(GRANT);
                        setcameracolor(GREEN);
                        break;
                    case RESULTS.DENIED:
                        request(PERMISSIONS.ANDROID.CAMERA)
                            .then((result) => {
                                if (result == RESULTS.GRANTED) {
                                    setcamerastatus(GRANT);
                                    setcameracolor(GREEN);
                                } else if (result == RESULTS.DENIED) {
                                    setcamerastatus(DENY);
                                    setcameracolor(BAD);
                                } else if (result == RESULTS.BLOCKED) {
                                    setcamerastatus(BLOCK);
                                    setcameracolor(BAD);
                                }
                            })
                        break;
                    case RESULTS.BLOCKED:
                        setcamerastatus(BLOCK);
                        setcameracolor(BAD);
                        break;
                }
            });
        } else if (Platform.OS == 'ios') {
            check(PERMISSIONS.IOS.CAMERA).then((result) => {
                switch (result) {
                    case RESULTS.UNAVAILABLE:
                        setcamerastatus(UNAVAILABLE);
                        setcameracolor(BAD);
                        break;
                    case RESULTS.GRANTED:
                        setcamerastatus(GRANT);
                        setcameracolor(GREEN);
                        break;
                    case RESULTS.DENIED:
                        request(PERMISSIONS.IOS.CAMERA)
                            .then((result) => {
                                if (result == RESULTS.GRANTED) {
                                    setcamerastatus(GRANT);
                                    setcameracolor(GREEN);
                                } else if (result == RESULTS.DENIED) {
                                    setcamerastatus(DENY);
                                    setcameracolor(BAD);
                                } else if (result == RESULTS.BLOCKED) {
                                    setcamerastatus(BLOCK);
                                    setcameracolor(BAD);
                                }
                            })
                        break;
                    case RESULTS.BLOCKED:
                        setcamerastatus(BLOCK);
                        setcameracolor(BAD);
                        break;
                }
            });
        }
    }

    function MicroCheck() {
        if (Platform.OS == 'android') {
            check(PERMISSIONS.ANDROID.RECORD_AUDIO).then((result) => {
                switch (result) {
                    case RESULTS.UNAVAILABLE:
                        setmicrostatus(UNAVAILABLE);
                        setmicrocolor(BAD);
                        break;
                    case RESULTS.GRANTED:
                        setmicrostatus(GRANT);
                        setmicrocolor(GREEN);
                        console.log('grant??')
                        props.callback(); //关闭权限检查view
                        break;
                    case RESULTS.DENIED:
                        request(PERMISSIONS.ANDROID.RECORD_AUDIO)
                            .then((result) => {
                                if (result == RESULTS.GRANTED) {
                                    setmicrostatus(GRANT);
                                    setmicrocolor(GREEN);
                                } else if (result == RESULTS.DENIED) {
                                    setmicrostatus(DENY);
                                    setmicrocolor(BAD);
                                } else if (result == RESULTS.BLOCKED) {
                                    setmicrostatus(BLOCK);
                                    setmicrocolor(BAD);
                                }
                            })
                            .then(() => {
                                if (camerastatus == GRANT && microstatus == GRANT) {
                                    props.callback(); //关闭权限检查view
                                }
                            });
                        break;
                    case RESULTS.BLOCKED:
                        setmicrostatus(BLOCK);
                        setmicrocolor(BAD);
                        
                        if (camerastatus == GRANT && microstatus == GRANT) {
                            props.callback(); //关闭权限检查view
                        }
                        break;
                }
            });
        } else if (Platform.OS == 'ios') {
            check(PERMISSIONS.IOS.MICROPHONE).then((result) => {
                switch (result) {
                    case RESULTS.UNAVAILABLE:
                        setmicrostatus(UNAVAILABLE);
                        setmicrocolor(BAD);
                        
                        break;
                    case RESULTS.GRANTED:
                        setmicrostatus(GRANT);
                        setmicrocolor(GREEN);
                        props.callback(); //关闭权限检查view
                       
                        break;
                    case RESULTS.DENIED:
                        request(PERMISSIONS.IOS.MICROPHONE)
                            .then((result) => {
                                if (result == RESULTS.GRANTED) {
                                    setmicrostatus(GRANT);
                                    setmicrocolor(GREEN);
                                    
                                } else if (result == RESULTS.DENIED) {
                                    setmicrostatus(DENY);
                                    setmicrocolor(BAD);
                                } else if (result == RESULTS.BLOCKED) {
                                    setmicrostatus(BLOCK);
                                    setmicrocolor(BAD);
                                }
                            })
                            .then(() => {
                                
                                if (camerastatus == GRANT && microstatus == GRANT) {
                                    props.callback(); //关闭权限检查view
                                }
                            });
                        break;
                    case RESULTS.BLOCKED:
                        setmicrostatus(BLOCK);
                        setmicrocolor(BAD);
                        
                        if (camerastatus == GRANT && microstatus == GRANT) {
                            props.callback(); //关闭权限检查view
                        }
                        break;
                }
            });
        }
    }

    return (
        <View style={styles.container}>
            <TouchableOpacity 
            onPress={props.callback}
            style={styles.close}>
                <Text>关闭</Text>
            </TouchableOpacity>

            <Text style={styles.title}>直播、就现在</Text>
            <Text style={styles.subtitle}>允许访问即可开始你的直播</Text>
            <TouchableOpacity 
            onPress={CameraCheck}
            style={styles.row_item}>
                <Text style={{ color: cameracolor, fontSize: 17 }}>{camerastatus}</Text>
            </TouchableOpacity>
            <TouchableOpacity 
            onPress={MicroCheck}
            style={styles.row_item}>
                <Text style={{ color: microcolor, fontSize: 17 }}>{microstatus}</Text>
            </TouchableOpacity>
        </View>
    );
};
export default PermissionGrantView;

const styles = StyleSheet.create({
    container: {
        flex:1,
        justifyContent:'center',
        alignItems:'center',
        backgroundColor: '#111'
    },
    row_item: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        minHeight: 30,
        marginVertical: 10,
    },
    done_btn: {
        backgroundColor: '#f1f1f1',
        borderRadius: 5,
        paddingHorizontal: 10,
        paddingVertical: 12,
        justifyContent: 'center',
        alignItems: 'center',
    },
    title: {
        fontSize: 28,
        fontWeight: '500',
        color: '#CCCCCC',
        width: '100%',
        textAlign: 'center',
        paddingBottom: 15,
    },
    subtitle:{
        fontSize: 18,
        fontWeight: '400',
        color: '#888888',
        width: '100%',
        textAlign: 'center',
        paddingBottom: 35,
    },
    camera: {
        height: 25,
        width: 25,
        marginEnd: 5,
    },
    close: {
        position:'absolute',
        top: sh*0.08,
        left: 12,
        padding:6
    }
});
