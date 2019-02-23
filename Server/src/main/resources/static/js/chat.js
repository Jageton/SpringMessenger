var loginApi = Vue.resource('/login')
var token = null
var login = null

Vue.component('loginForm', {
    data: function() {
        return {
            text: ''
        }
    },
    template:
    '<div>' +
        '<label> Login </label>' +
        '<input type="text" value="Login" v-model="text"/>' +
        '<input type="button" value="Continue" @click="sendLogin"/>' +
    '</div>',
    methods: {
        sendLogin: function() {
            var message = { login: this.text }
            login = this.text;
            loginApi.save({}, login).then(result =>
                result.json().then(data => {
                    token = data.token;
                    connect(data.token);
                })
            )
        }
    }
})

var stompClient = null;
var handler = null;

function connect(token){
    var socket = new SockJS('/spring_server/web')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/activity', messages => {
            var messags = JSON.parse(messages.body)
            messags.forEach(message =>
                handler(message))
        })
        auth(token)
    })
    console.log("OK")
}

function disconnect() {
    if (stompClient !== null){
        stompClient.disconnect()
    }
}

function auth(token) {
    stompClient.send("/app/history", {}, JSON.stringify({token: token}));
}

function sendMessage(message) {
    stompClient.send("/app/send_message", {}, JSON.stringify(message));
}


Vue.component('message-row', {
    props: ['message', 'view'],
    template:
        '<div>' +
        '{{ message.from }}: {{ message.message }}' +
        '</div>'
});

Vue.component('message-rows', {
    props: ['messages'],
    template:
        '<layer style=" width: 70vw; height: 60vh; border: 3px solid #000000; display: inline-block; overflow: auto;"> ' +
        '<message-row v-for="message in messages" :key="message.id" :message="message"/>' +
        '</layer>'
});

var chatComponent = Vue.component('bottomPanel', {
    props: ['messages'],
    data: function() {
        return {
            text: '',
        }
    },
    template:
        '<div>'+
        '<input type="text" value="Message" v-model="text"/> ' +
        '<input type="button" value="Send" @click="send"/>' +
        '</div>',
    methods: {
        send: function() {
            if(this.text !== ""){
                var message = { message: this.text, from: login, to: "Server"}
                this.text = ''
                this.addToList(message)
                sendMessage(message)
            }
        },
        addToList: function(message){
            this.messages.push(message)
        }
    },
    created: function() {
        handler = this.addToList;
    }

});

var app = new Vue({
    el: '#chat',
    template: '<div>' +
                '<loginForm></loginForm>' +
                '<message-rows :messages="messages"/>' +
                '<bottomPanel :messages="messages" />' +
              '</div>',
    data: {
        messages: [],
        view: 'chat',
    },
    components: {
        'login' : {
            template:
                '<div> ' +
                    '<loginForm></loginForm> ' +
                '</div>'
        },
        'chat' : {
            template:
            '<div>' +
                '<message-rows :messages="messages"/>' +
                '<bottomPanel :messages="messages" />' +
            '</div>'
        }
    }
});

