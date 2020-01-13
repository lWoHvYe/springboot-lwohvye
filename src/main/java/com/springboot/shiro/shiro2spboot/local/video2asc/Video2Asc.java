package com.springboot.shiro.shiro2spboot.local.video2asc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.local.video2asc
 * @className Video2Asc
 * @description 将视频转化为Asc码输出，通过分析每一帧的图像，根据颜色设置对应的Asc码，达到转换的效果，使用OpenCV相关类
 * 需要将src/main/resources/dll/x64/opencv_java410.dll手动引入，实际项目中，推荐将该dll放入jdk的bin目录中
 * @date 2019/9/26 08:50
 */

public class Video2Asc {

    static {
//        need to add '-Djava.library.path=E:\Workspaces\OpenCV\opencv\build\java\x64' to jvm

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String ascii = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\\\"^`'.";
//        打开视频文件
        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open("E:\\Workspaces\\change_vedio_to_txt\\vedio.mp4");

        if (!videoCapture.isOpened()) {
            System.out.println("could not load video data...");
            return;
        }

//        int frame_width = (int) videoCapture.get(3);
//        int frame_height = (int) videoCapture.get(4);
////      将视频显示类实例化
//        ImageGUI gui = new ImageGUI();
//        gui.createWin("show", new Dimension(frame_width, frame_height));

        Mat mat = new Mat();

        Mat img_mat = new Mat();

        while (true) {
            boolean hava = videoCapture.read(mat);
//          将图片灰度化
            Imgproc.cvtColor(mat, img_mat, Imgproc.COLOR_RGB2GRAY);

//            Core.flip(mat,mat,1);
            if (!hava)
                break;
//            if (!mat.empty()) {
////              将Mat转换为BufferedImage
//                BufferedImage image = conver2Image(img_mat);
////                显示视频
//                gui.imshow(image);
//                gui.repaint();
//            }

            if (!mat.empty()) {
                System.out.println("\033c");

                StringBuilder result = new StringBuilder();
//                将灰度化的图片与给出的Asc字符对应
                for (int i = 0; i < img_mat.rows(); i += 7) {
                    for (int j = 0; j < img_mat.cols(); j += 7) {
//                        获取指定格的灰度
                        int gray = (int) img_mat.get(i, j)[0];
//                        根据灰度，配置对应的字符的索引
                        int index = Math.round(gray * (ascii.length() + 1) / 255);
//                        对于灰度过高的统一用指定字符表示
                        result.append(index >= ascii.length() ? "." : (ascii.charAt(index)));
//                        额外添加一个字符，双倍行宽，适用于idea窗口输出
                        result.append(index >= ascii.length() ? "." : (ascii.charAt(index)));
                    }
//                    一行结束，换行
                    result.append("\n");
                }
//                  一帧结束，
                System.out.println("\033c");
//                向控制台输出字符，可根据需要更改输出方式
                System.out.println(result);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    private static BufferedImage conver2Image(Mat mat) {
//        int width = mat.cols();
//        int height = mat.rows();
//        int dims = mat.channels();
//        int[] pixels = new int[width * height];
//        byte[] rgbdata = new byte[width * height * dims];
//
//        mat.get(0, 0, rgbdata);
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        int index = 0;
//        int r = 0, g = 0, b = 0;
//        for (int row = 0; row < height; row++) {
//            for (int col = 0; col < width; col++) {
//                if (dims == 3) {
//                    index = row * width * dims + col * dims;
//                    b = rgbdata[index] & 0xff;
//                    g = rgbdata[index + 1] & 0xff;
//                    r = rgbdata[index + 2] & 0xff;
//                    pixels[row * width + col] =
//                            ((255 & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | b & 0xff;
//                }
//                if (dims == 1) {
//                    index = row * width + col;
//                    b = rgbdata[index] & 0xff;
//                    pixels[row * width + col] =
//                            ((255 & 0xff) << 24) | ((b & 0xff) << 16) | ((b & 0xff) << 8) | b & 0xff;
//                }
//            }
//        }
//        setRGB(image, 0, 0, width, height, pixels);
//        return image;
//    }

//    private static void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
//        int type = image.getType();
//        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
//            image.getRaster().setDataElements(x, y, width, height, pixels);
//        else
//            image.setRGB(x, y, width, height, pixels, 0, width);
//    }

}
