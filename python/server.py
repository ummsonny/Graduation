import socket
import io
from PIL import Image,ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

def get_bytes_stream(sock,length):
    buf =b''
    try:
        step = length
        while True:
            data = sock.recv(step)
            buf += data
            if len(buf) == length:
                break
            elif len(buf) <length:
                step = length - len(buf)
    except Exception as e:
        print(e)
    return buf[:length]

IP = '127.0.0.1'
PORT = 9999
ADDR = (IP,PORT)

i=0
while(True):
    print('기다리는 중')
    print(i)
    server_sock = socket.socket(socket.AF_INET)
    server_sock.bind(ADDR)
    server_sock.listen(1)
    client_sock,addr = server_sock.accept()
    len_bytes_string = bytearray(client_sock.recv(1024))[2:]
    len_bytes = len_bytes_string.decode("utf-8")
    length = int(len_bytes)
    print(length)
    img_bytes = get_bytes_stream(client_sock,length)
    i+=1
    img_path = "./"+str(i)+".png"
    data_io = io.BytesIO(img_bytes)
    img = Image.open(data_io)
    img.save(img_path)
    client_sock.close()
    server_sock.close()

