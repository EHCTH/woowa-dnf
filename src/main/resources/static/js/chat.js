let stompClient = null;

function connect() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('Connected');

        fetch('/api/chat/messages')
                    .then(res => res.json())
                    .then(messages => {
                        messages.forEach(m => addMessage(m.writer, m.content));
                    });

        stompClient.subscribe('/topic/chat', function (message) {
            const body = JSON.parse(message.body);
            addMessage(body.writer, body.content);
        });
    });
}

function addMessage(writer, content) {
    const box = document.getElementById('chat-box');
    const line = document.createElement('div');
    line.textContent = writer + ' : ' + content;
    box.appendChild(line);
    box.scrollTop = box.scrollHeight;
}

function sendMessage() {
    const writer = document.getElementById('writer').value || '익명';
    const content = document.getElementById('message').value;
    if (!content.trim()) return;

    stompClient.send('/app/chat.send', {}, JSON.stringify({writer, content}));
    document.getElementById('message').value = '';
}

connect();
