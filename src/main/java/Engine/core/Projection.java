package Engine.core;

import org.joml.Matrix4f;

public class Projection {

    public static Matrix4f perspective(float left, float right, float bottom, float top, float near, float far){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.m00(2*near/(right-left));
        matrix.m11(2*near/(top-bottom));
        matrix.m22(-(far+near)/(far-near));
        matrix.m23(-1);
        matrix.m32(-2*far*near/(far-near));
        matrix.m20((right+left)/(right-left));
        matrix.m21((top+bottom)/(top-bottom));
        matrix.m33(0);

        return matrix;
    }

    public static Matrix4f orthogonal(float left, float right, float bottom, float top, float near, float far){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.m00(2/(right-left));
        matrix.m11(2/(top-bottom));
        matrix.m22(-2/(far-near));
        matrix.m32((far+near)/(far-near));
        matrix.m30((right+left)/(right-left));
        matrix.m31((top+bottom)/(top-bottom));

        return matrix;
    }

}
