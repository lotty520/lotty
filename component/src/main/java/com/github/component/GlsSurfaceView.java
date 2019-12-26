package com.github.component;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GlsSurfaceView extends GLSurfaceView {

  //以下坐标z都为0 创建一个平面的三角形
  private static float triangleCoords[] = {
      0.0f, 1.0f, 0.0f,  // top 屏幕顶端中心点
      -1.0f, -1.0f, 0.0f, // bottom left 屏幕底部左下角
      1.0f, -1.0f, 0.0f// bottom right 屏幕底部右下角
  };

  public GlsSurfaceView(Context context) {
    super(context);
    setEGLContextClientVersion(2);
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
  }

  public GlsSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setEGLContextClientVersion(2);
    setRenderer(GlsRender.create());
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
  }

  private static class GlsRender implements GLSurfaceView.Renderer {

    private static GlsRender create() {
      return new GlsRender();
    }

    @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
      gl.glViewport(0, 0, width, height);
    }

    @Override public void onDrawFrame(GL10 gl) {
      gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
  }
}
