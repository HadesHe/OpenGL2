attribute vec3 vPosition;
uniform mat4 uMVPMatrix;
attribute vec2 vCoordinate;//贴图顶点坐标
varying vec2 aCoordinate;//贴图顶点坐标--片元变量
void main() {
    gl_Position = uMVPMatrix*vec4(vPosition, 1.0);
    aCoordinate=vCoordinate;
}
