attribute vec3 vPosition;
uniform mat4 uMVPMatrix;
attribute vec4 aColor;
varying vec4 vColor;

void main() {
    gl_Position = uMVPMatrix*vec4(vPosition, 1);
    vColor=aColor;
    gl_PointSize=10.0;
}
