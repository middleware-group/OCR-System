from PIL import Image
from PIL import ImageEnhance
import sys

path=sys.argv[1]
img = Image.open(path)
# img.show()
# img=img.convert('L')
# 对比度增强
enh_con = ImageEnhance.Contrast(img)
contrast = 2
img_contrasted = enh_con.enhance(contrast)
#img_contrasted.show()
newpath=path[:len(path)-4]+"new"+".jpg"
img_contrasted.save(newpath)
# im_point = img_contrasted.point(lambda x:x*1.5)
# brightness = ImageEnhance.Brightness(img_contrasted)
# im_brightness = brightness.enhance(0.5)
# im_brightness.show()
# im_brightness.save("F:/大三下/中间件技术/大作业/Tencent_ocr/images_test3/TIM图片20190419213454_new.jpg")
# img_contrasted.save("F:/大三下/中间件技术/大作业/Tencent_ocr/images_test3/TIM图片20190419213444_new.jpg")