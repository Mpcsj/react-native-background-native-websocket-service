import React,{useState} from 'react'
import {
    SafeAreaView,
    StatusBar,
    Pressable,
    Text,
    TextInput
} from 'react-native'
import ToastModule from '../../util/toast.module'
import styles from './main.styles'
const MainPage = ()=>{
    const [message,setMessage] = useState('')
    return(
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="dark-content" />
            <Text style={styles.title}>Tela principal</Text>
            <TextInput
                style={styles.input}
                onChangeText={text => setMessage(text)}
                value={message}
            />
            <Pressable
                onPress={()=>{
                    ToastModule.show(message,ToastModule.GRANDE_LERAS)
                    setMessage('')
                }}
                android_disableSound={false}
                android_ripple={{
                    color:'red',
                    radius:1,
                    borderless:true
                }}
                style={styles.button}>
                <Text style={styles.buttonText}>Clicable</Text>
            </Pressable>
        </SafeAreaView>
    )
}

export default MainPage