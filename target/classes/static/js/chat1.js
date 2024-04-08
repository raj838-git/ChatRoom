'use strict';
var chatRoom = document.querySelector('#general');
var chatPage = document.querySelector('#chatpage');
var messageInput = document.querySelector('#coremessage');
var messages = document.querySelector('.messages');
var messageForm = document.querySelector('#messageForm');
var photourl = document.getElementById("profilephoto").getAttribute('src');

var stompClient = null;
var username = null;


function connect(event) {
	chatPage.classList.remove('hidden');

	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, onError);
	event.preventDefault();
}

function onConnected(frame) {
	console.log('Connected Connection details : ' + frame.headers["user-name"]);
	username = frame.headers["user-name"];
	// Subscribe to the Public Topic
	stompClient.subscribe('/topic/public', onMessageReceived);

	// Tell your username to the server
	stompClient.send("/app/chat.addUser",
		{},
		JSON.stringify({ sender: username, type: 'JOIN' })
	)
}

function onError(error) {
	console.log("Something wrong in websocket connection .."+error);
}

function sendMessage(event) {
	var messageContent = messageInput.value.trim();
	if (messageContent && stompClient) {
		var chatMessage = {
			sender: username,
			content: messageInput.value,
			type: 'CHAT',
			profilephoto : photourl 
		};
		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}

function onMessageReceived(payload) {
	try {
		var data = JSON.parse(payload.body);
	} catch (error) {
		console.error("Error parsing JSON:", error);
	}
	var messageElement = document.createElement('div');
	if (data.type === 'JOIN') {
		messageElement.classList.add('message');
		messageElement.innerHTML = `<div class="message-info"><span class="username">${data.sender} </span><span class="timestamp">${new Date().toString()} : Connected</span></div>`;
		messages.appendChild(messageElement);
		messages.scrollTop = messages.scrollHeight;
	} else if (data.type === 'LEAVE') {
		messageElement.classList.add('message');
		messageElement.innerHTML = `<div class="message-info"><span class="username">${data.sender} </span><span class="timestamp">${new Date().toString()} : Disconnected</span></div>`;
		messages.appendChild(messageElement);
		messages.scrollTop = messages.scrollHeight;
	} else {
		messageElement.classList.add('message');
		messageElement.innerHTML = `<img src="${data.profilephoto}" alt="User Avatar">
		<div class="message-info">
		<span class="username">${data.sender} </span><span class="timestamp">${new Date().toLocaleTimeString()} : </span>
		</div>
		<div id="message-tex" class="message-text">${data.content}</div>`;
		messages.appendChild(messageElement);
		messages.scrollTop = messages.scrollHeight;
	}
}

document.addEventListener("DOMContentLoaded", function() {
	const channels = document.querySelectorAll(".channel");

	channels.forEach(function(channel) {
		channel.addEventListener("click", function() {
			// Example: Invoke a custom function when a channel is clicked
			console.log("Channel clicked:", channel.innerText);

			// Example: Change the HTML of the clicked channel
			if (username == null) {
				channel.innerHTML += " (clicked)";
				connect();
			}
		});
	});
});
messageForm.addEventListener('submit', sendMessage, true);
