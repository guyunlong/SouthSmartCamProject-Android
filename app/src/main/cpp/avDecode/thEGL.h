#ifndef thEGL_h
#define thEGL_h
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <dlfcn.h>
#include <pthread.h>



#include <ffmpeg/libavutil/frame.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>
#include <android/native_window_jni.h>
#include <cm_types.h>

extern pthread_mutex_t th_mutex_lock;


void nativeRequestInitEGL(ANativeWindow * nativeWindow,u64 NetHandle);
void onRenderThreadRun(HANDLE NetHandle);
void nativeRequestSurfaceChangeEGL(ANativeWindow * nativeWindow);
void nativeRequestDestoryEGL();
int requestInitEGL(ANativeWindow * nativeWindow,int videoWidth,int videoHeight);
int requestEGLSurfaceChanged(ANativeWindow * nativeWindow,int videoWidth,int videoHeight);
int requestEGLRenderFrame(AVFrame* Frame422,int videoWidth,int videoHeight);
void eglSurfaceDestory();
#endif /* thEGL_h */