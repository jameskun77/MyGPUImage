cmake_minimum_required(VERSION 3.4.1)

add_library( gpuimage-library
             SHARED
             src/cpp/yuv-decoder.c)