import cv2
import numpy as np
import glob

img_array = []

filst = sorted(glob.glob('*.jpg'))
for filename in filst:
    print(filename)

    img = cv2.imread(filename)
    height, width, layers = img.shape
    size = (width,height)
    img_array.append(img)


out = cv2.VideoWriter('project1.mp4',cv2.VideoWriter_fourcc(*'XVID'), 5, size)
 
for i in range(len(img_array)):
    out.write(img_array[i])
out.release()
