/**
 * Sample React Native App
 *
 * adapted from App.js generated by the following command:
 *
 * react-native init example
 *
 * https://github.com/facebook/react-native
 */

const LicenseKey =  "f6a65ffa0a5e6b39451e5e4cb666e4ee";
const LicenseUrl = "http://license.vod2.myqcloud.com/license/v1/d7f838e6d9a8f966bf740eda738a1511/TXLiveSDK.licence";



import React, { useState,useEffect } from 'react';
import { Platform, StyleSheet, Text, View,Button } from 'react-native';
import {LivePullManager,LivePushManager,LivePushView,LivePullView} from 'react-native-live';

//Test View
import PushRoom from './live/PushRoom';
import PullRoom from './live/PullRoom';

function App(){
  const [value,setvalue] = useState("")

  useEffect(() => {
    //设置license
    LivePullManager.setLicence(LicenseUrl,LicenseKey)
  },[])

  const tapHandler = () => {
    // LivePullManager.liveSetupLicence
  }

  return <PullRoom />

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>☆Live example☆</Text>
      <Text style={styles.instructions}>STATUS:  {value}</Text>
      <Text style={styles.welcome}>☆NATIVE CALLBACK MESSAGE☆</Text>
      <Text style={styles.instructions}></Text>
      <Button onPress={tapHandler} title={"开始直播"}></Button>
    </View>
  );
}

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});