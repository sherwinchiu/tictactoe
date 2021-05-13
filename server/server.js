const net = require('net');

const hostname = '127.0.0.1';
const port = 3000;

net.createServer(function(sock){
    const REMOTE_ADDRESS = sock.remoteAddress;
    const REMOTE_PORT = sock.remotePort;
    console.log('CONNECTED: '+REMOTE_ADDRESS +':'+REMOTE_PORT);    
    sock.on('data', function(data){
        sock.write(data);
    });
    sock.on('close', function(data){
        console.log('CLOSED: '+REMOTE_ADDRESS+':'+REMOTE_PORT);
    }); 
}).listen(PORT, HOST);

  console.log(`Server running at http://${hostname}:${port}/`);