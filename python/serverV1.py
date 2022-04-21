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
    server_sock = socket.socket(socket.AF_INET)
    server_sock.bind(ADDR)
    server_sock.listen(1)
    client_sock,addr = server_sock.accept()
    len_bytes_string = bytearray(client_sock.recv(1024))[2:]
    len_bytes = len_bytes_string.decode("utf-8")
    each_len = list(map(int,len_bytes.split(',')))
    sum_len = sum(each_len)
    img_bytes = get_bytes_stream(client_sock,sum_len)
    each_byte = []
    offset = [0]+[i for i in each_len]
    offset.pop()
    for i in range(len(each_len)):
        if i ==0:
            offset[i]=0
        else:
            offset[i] += offset[i-1]
    for i in range(len(each_len)):
        if i == len(each_len)-1:
            each_byte.append(img_bytes[offset[i]:])
        else:
            each_byte.append(img_bytes[offset[i]:offset[i+1]])
    for i in range(len(each_len)):
        img_path = "./"+str(i)+".png"
        data_io = io.BytesIO(each_byte[i])
        img = Image.open(data_io)
        img.save(img_path)
    client_sock.close()
server_sock.close()

