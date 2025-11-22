let stompClient = null;
let roomServer = null;
let roomCharacterId = null;

function connect() {
    roomServer = document.getElementById('chat-server').value;
    roomCharacterId = document.getElementById('chat-characterId').value;
    const roomId = `${roomServer}:${roomCharacterId}`;

    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('Connected to room:', roomServer, roomCharacterId);
        fetch(`/api/chat/messages?roomId=${encodeURIComponent(roomId)}&size=50`)
            .then(res => res.json())
            .then(messages => {
                 messages.reverse().forEach(m => addMessage(m.writer, m.content));
                  }
            );

        stompClient.subscribe(`/topic/chat/${roomServer}/${roomCharacterId}`, function (message) {
            const body = JSON.parse(message.body);
                addMessage(body.writer, body.content);
            }
        );
    });
}

function addMessage(writer, content) {
    const box = document.getElementById('chat-box');
    const line = document.createElement('div');
    line.classList.add('chat-line');

    const nick = document.createElement('span');
    nick.classList.add('chat-nick');
    nick.textContent = writer + ' :';

    const body = document.createElement('span');
    body.classList.add('chat-text');
    body.textContent = ' ' + content;

    line.appendChild(nick);
    line.appendChild(body);

    box.appendChild(line);
    box.scrollTop = box.scrollHeight;
}

function sendMessage() {
    const writer = document.getElementById('writer').value || '익명';
    const content = document.getElementById('message').value;
    if (!content.trim()) {
        return;
    }
    stompClient.send( `/app/chat/${roomServer}/${roomCharacterId}`, {},  JSON.stringify({writer, content}));

    document.getElementById('message').value = '';
}

document.addEventListener('DOMContentLoaded', function () {
    connect();

    const input = document.getElementById('message');
    if (!input) {
        return;
    }

    input.addEventListener('keydown', function (event) {
        if (event.isComposing || event.keyCode === 229) {
            return;
        }
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            sendMessage();
        }
    });
});
