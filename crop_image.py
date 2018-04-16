import sys
from PIL import Image
from resizeimage import resizeimage

def crop(image, output, w, h):
    fd_img = open(image, 'r')
    img = Image.open(fd_img)
    width, height = img.size    
    scale = max(w/float(width), h/float(height)) + 0.1
    print scale
    img = img.resize((int(width*scale), int(height*scale)), Image.BILINEAR)    
    half_the_width = img.size[0] / 2
    half_the_height = img.size[1] / 2
    img = img.crop(
      (
        half_the_width - w/2,
        half_the_height - h/2,
        half_the_width + w/2,
        half_the_height + h/2
      )
    )
    img.save(output, img.format)
    fd_img.close()

srcPath = sys.argv[0]
destPath = sys.argv[1]
crop(srcPath, destPath + 'icon.png', 512, 512)
crop(srcPath, destPath + 'banner.png', 1024, 500)