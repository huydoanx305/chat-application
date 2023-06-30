// import * as io from "socket.io-client";
//
// export let socket;
//
// export const initiateSocketConnection = () => {
//     socket = io("http://localhost:9090/", {
//         reconnection: false,
//     });
// }
//
// export const disconnectSocket = () => {
//     if (socket) socket.disconnect();
// }
//
// export const clientSendRoom = (roomId) => {
//     if (socket) {
//         socket.emit('client_send_room', roomId);
//     }
// }
//
