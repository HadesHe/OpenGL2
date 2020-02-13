attribute vec3 vPosition;
//uniform mat4 uMVPMatrix;//变换矩阵
attribute vec4 aColor;//顶点颜色
varying vec4 vColor;//片元颜色
void main(){
    gl_Position=vec4(vPosition, 1);
    vColor=aColor;
}