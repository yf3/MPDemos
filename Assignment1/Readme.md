# Assignment 1: Halftone Dithering

## Ideas
+ Make use of opencv libraries to perform image processing.
+ MVC architecture to separate the image processing part and GUI part of the program.
+ By applying the strategy design pattern and setting the hierarchy of the strategy classes, the program is pretty desirable in scalability -- new dithering or color-to-grayscale algorithms can be added easily.

## Test Results

> Test image: [https://www.pinterest.com/pin/330662797610913353/](https://www.pinterest.com/pin/330662797610913353/)

> Figure.1 to Figure.3 shows the result of normal grayscale-to-binary ordered dithering with Bayer matrices (threshold maps)

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig1.png?raw=true)

__Fig.1__ The grayscale image vs binary image produced with 2x2 Bayer matrix

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig2.png?raw=true)

__Fig.2__ 2x2 vs 4x4 Ordered dithering

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig3.png?raw=true)

__Fig.3__ 4x4 vs 8x8 Ordered dithering

> Figure.5 to Figure.7 compare the previous results with the results using the algorithm described by Figure.4 to make output image remain uniform size.

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig4.png?raw=true)

__Fig.4__ Algorithm to produce a dithered image with the same size

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig5.png?raw=true)

__Fig.5__ Normal vs uniform: 2x2

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig6.png?raw=true)
__Fig.6__ Normal vs uniform: 4x4

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig7.png?raw=true)
__Fig.7__ Normal vs uniform: 8x8


> Figure.8 shows result of Floyd-Steinberg dithering, compared with ordered dithering.

![](https://github.com/yf3/MMS_Assignments/blob/master/Assignment1/figures/fig8.png?raw=true)

__Fig.8__ Floyd-Steinberg dithering vs Ordered dithering with 4x4

## Known Issues

+ The result of Floyd-Steinberg dithering shown above actually use a weird standard to distinguish black and white color: by using value 240 as a separator instead of 128. But it gets far better result than using 128.
+ Image showed in javafx "ImageView" class sometimes looks different from the real result. To see the accurate result, use the export button to see the image file.
+ Very large pictures sometimes cannot be loaded properly.

## Something I learn from the assignment

+ Setting up the opencv developing environment for Java language and Java IDEs.
+ Understand multiple color-to-grayscale and dithering algorithms and how to implement them.
+ Getting more familiar with basic git instructions.