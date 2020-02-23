uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
varying vec3 vPosition;
uniform vec4 uAmbient;
varying vec4 vAmbient;

void main() {
    gl_Position = uMVPMatrix*vec4(aPosition, 1);
    vPosition=aPosition;
    vAmbient=vec4(uAmbient);
}
